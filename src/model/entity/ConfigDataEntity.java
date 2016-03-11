package model.entity;

import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoConfigUtil;
import com.hahazql.util.auto.config.AutoExcel;
import com.hahazql.util.clazz.PackageUtil;
import com.hahazql.util.clazz.PackageUtilNew;
import com.hahazql.util.exception.BaseException;
import model.bean.ConfigData;
import model.bean.ProjectData;
import model.config.ServerContent;
import model.process.AutoConfigL;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zql on 2015/10/12.
 */
public class ConfigDataEntity
{
    private static ConcurrentHashMap<String,Integer> _ix_projectName = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer,ProjectData> _ix_projectID = new ConcurrentHashMap<Integer,ProjectData>();
    private static ConcurrentHashMap<Integer,ConfigData> _ix_configID = new ConcurrentHashMap<Integer,ConfigData>();
    private static ConcurrentHashMap<Integer,ConcurrentHashMap<Integer,Boolean>> _ix_project = new ConcurrentHashMap<Integer,ConcurrentHashMap<Integer,Boolean>>();
    private static ConcurrentHashMap<String,Integer> _ix_config_code = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer,ConcurrentHashMap<Comparable,Boolean>> _config_List = new ConcurrentHashMap<>();

    private static boolean _instance = false;
    
    public static void putConfigList(Integer configID,List<Comparable> list)
    {
    	ConcurrentHashMap<Comparable,Boolean> map = _config_List.get(configID);
    	if(map==null)
    	{
    		map = new ConcurrentHashMap<>();
    		_config_List.put(configID, map);
    	}
    	for(Comparable data : list)
    		map.put(data, true);
    }
    
    public static void clearConfigList(Integer configID)
    {
    	Map<Comparable,Boolean> map = _config_List.get(configID);
    	if(map!=null)
    		map.clear();
    }
    
    public static ConfigData getConfigData(Integer projectID,String clazzName)
    {
    	String code = getCode(projectID, clazzName);
    	Integer id = _ix_config_code.get(code);
    	if(id == null)
    		return null;
    	return _ix_configID.get(id);
    }

    public static ConfigData getConfigData(Integer configID)
    {
        return _ix_configID.get(configID);
    }
    
    public static ProjectData getProject(Integer projectID) throws BaseException
    {
    	init();
    	return _ix_projectID.get(projectID);
    }

    public static Collection<ProjectData> getProjects() throws BaseException {
        init();
        return _ix_projectID.values();
    }

    public static List<? extends Comparable> getConfigList(Integer configID)
    {
        Map<Comparable,Boolean> map = _config_List.get(configID);
        if(map == null)
            return new ArrayList<Comparable>();
        Collection<Comparable> collection =  map.keySet();
        List<Comparable> list = new ArrayList<Comparable>();
        list.addAll(collection);
        Collections.sort(list);
        return list;
    }


    public static Collection<ConfigData> getConfigDataList(Integer projectID) throws BaseException {
        init();
        List<ConfigData> list = new ArrayList<>();
        Map<Integer,Boolean> map = _ix_project.get(projectID);
        if(map == null)
            return new ArrayList<ConfigData>();
        for(Integer id : map.keySet())
        {
            ConfigData configData = _ix_configID.get(id);
            if(configData!=null)
                list.add(configData);
        }
        return list;
    }


    public static String getCode(ConfigData data) throws BaseException {
        return getCode(data.getProjectID(),data.getClazzName());
    }

    public static String getCode(Integer projectID,String clazzName)
    {
        String code = projectID.toString() + "#" + clazzName;
        return  code;
    }

    public static void init() throws BaseException {
        if(_instance)
            return;
        try {
            List<ProjectData> allProject = ServerContent.projectDataDao.selectAll();
            List<ConfigData> allConfig = ServerContent.configDataDao.selectAll();
            for(ProjectData projectData : allProject)
            {
                putToMem(projectData);
            }
            for(ConfigData configData : allConfig)
            {
                putToMem(configData);
            }
            for(ProjectData projectData : allProject)
            {
                String jarPath = projectData.getJarPath();
                if(jarPath.isEmpty())
                    continue;
                ClassLoader classLoader = PackageUtilNew.getClassLoader(jarPath);
                projectData.setClassLoader(classLoader);
                putToMem(projectData);
                List<AutoConfig> list = AutoConfigUtil.loaderFromExternJar(projectData.getConfigDataPath(),classLoader);
                for(AutoConfig autoConfig : list)
                {
                    AutoConfig<AutoExcel> autoExcelAutoConfig = AutoConfigUtil.instanceExcelNoFile(autoConfig);
                    String code = getCode(projectData.getId(),autoConfig.getClazz().getName());
                    if(!_ix_config_code.containsKey(code))
                        continue;
                    Integer id = _ix_config_code.get(code);
                    ConfigData configData = _ix_configID.get(id);
                    if(configData == null)
                        continue;
                    configData.setAutoConfig(autoExcelAutoConfig);
                    initConfig(configData,classLoader);
                }
            }
            _instance = true;

        } catch (Exception e) {
           throw(new BaseException("初始化配置缓存失败",e.getCause()) );
        }
    }

    public static int getPorjectIDByName(String projectName)
    {
        Integer id =  _ix_projectName.get(projectName);
        if(id == null)
            id = -1;
        return id;
    }

    public static ProjectData put(ProjectData data) throws BaseException {
        init();
        Integer id = data.getId();
        if(id <= 0)
        {
            id = getPorjectIDByName(data.getName());
            if(id <= 0) {
                id = insert(data);
                if (id <= 0)
                    throw (new BaseException("保存项目配置失败!Name:" + data.getName()));
            }
            data.setId(id);
            putToMem(data);
        }
        else
        {
            putToMem(data);
            putToDb(data);
        }
        return data;
    }

    public static int put(ConfigData data) throws BaseException {
        init();
        Integer id = data.getId();
        String code = getCode(data);
        if(_ix_config_code.containsKey(code))
            id = _ix_config_code.get(code);
        if(id <= 0) {
            id = insert(data);
            if (id < 0)
                throw (new BaseException("保存Config失败!Name:" + data.getName()));
            data.setId(id);
            putToMem(data);
        }
        else
        {
            putToMem(data);
            putToDb(data);
        }
        return id;
    }


    private static void putToMem(ProjectData data)
    {
        _ix_projectID.put(data.getId(),data);
        if(!_ix_project.containsKey(data.getId())) {
            _ix_project.put(data.getId(), new ConcurrentHashMap<Integer, Boolean>());
            System.out.println("初始化了Project");
        }
        _ix_projectName.put(data.getName(),data.getId());
    }

    private static void putToMem(ConfigData data) throws BaseException {
        _ix_configID.put(data.getId(),data);
        _ix_config_code.put(getCode(data),data.getId());
        Map<Integer,Boolean> map = _ix_project.get(data.getProjectID());
        if(map == null)
            throw(new BaseException("缺少对应工程,ProjectID:"+data.getProjectID()));
        map.put(data.getId(),true);
        System.out.println(map.size());
    }


    private static void putToDb(ProjectData data)
    {
        try {
            ServerContent.projectDataDao.updateByPrimaryKey(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void putToDb(ConfigData data)
    {
        try {
            ServerContent.configDataDao.updateByPrimaryKey(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int insert(ProjectData data)
    {
        try
        {
            return ServerContent.projectDataDao.insert(data);
        } catch (SQLException e) {
            return -1;
        }
    }


    private static int insert(ConfigData data)
    {
       try
       {
           return ServerContent.configDataDao.insert(data);
       } catch (SQLException e) {
           return -1;
       }
    }

    private static ConcurrentHashMap<Comparable,Boolean> initConfig(ConfigData data,ClassLoader loader)
    {
        ConcurrentHashMap<Comparable,Boolean> configList = new ConcurrentHashMap<>();
        _config_List.put(data.getId(),configList);
        try {
            List<Comparable> t = AutoConfigL.getConfigFromXML(data,data.getAutoConfig().getClazz(),loader);
          for(Comparable obj: t)
          {
              configList.put(obj,true);
              System.out.println("Loader Config : " + obj);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configList;
    }


    public static boolean putConfigList(ConfigData data,List<? extends Comparable> list,StringBuffer sb)
    {
        ConcurrentHashMap<Comparable,Boolean> configList = _config_List.get(data.getId());
        try {
            if(configList == null) {
                configList = new ConcurrentHashMap<>();
                _config_List.put(data.getId(),configList);
            }
            configList.clear();
            for (Comparable obj : list) {
                configList.put(obj, true);
                sb.append("Loader Config : " + obj);
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
