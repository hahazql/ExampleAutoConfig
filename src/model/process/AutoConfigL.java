package model.process;

import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoConfigUtil;
import com.hahazql.util.auto.config.AutoExcel;
import com.hahazql.util.exception.BaseException;
import com.hahazql.util.helper.StringUtils;
import com.hahazql.util.io.IOTinyUtils;
import model.bean.ConfigData;
import model.bean.ProjectData;
import model.config.ServerConfig;
import model.entity.ConfigDataEntity;
import model.exception.JarLoadException;
import org.apache.poi.util.StringUtil;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zql on 2015/10/13.
 */
public class AutoConfigL
{
    public static enum ConfigType
    {
        xml(1,"XML",".xml"),
        excel(2,"EXCEL",".xls"),
        configData(3,"ConfigData",".xml"),
        jar(4,"jar",".jar");

        private int type; //类型
        private String name; //类型名
        private String suffix;//文件后缀

        private ConfigType(int _type,String _name,String _suffix)
        {
            type = _type;
            name = _name;
            suffix = _suffix;
        }

        public int getType()
        {
            return type;
        }

        public String getName()
        {
            return name;
        }

        public String getSuffix()
        {
            return suffix;
        }
    }


    public static String getPath(String path,Integer projectId,String configName,ConfigType type)
    {
        String fileName = configName + type.getSuffix();
        return getUpLoadPath(fileName,projectId,type);
    }

    public static String getUpLoadPath(String fileName,Integer projectID,ConfigType type)
    {
        String path = ServerConfig.getJarSaveDir() + File.separator + projectID + File.separator + type.getName() + File.separator  + fileName;
        return path;
    }


    public static ProjectData createPorjectData(String projectName,String projectDesc,String jarPath,String configDataPath) throws BaseException {
        ProjectData data = new ProjectData();
        data.setName(projectName);
        data.setProjectDesc(projectDesc);
        data.setJarPath(jarPath);
        data.setConfigDataPath(configDataPath);
        return ConfigDataEntity.put(data);
    }

    public static void createConfigFromJar(Integer projectID,String jarPath,StringBuffer message) throws BaseException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<AutoConfig> list = getAutoConfig(jarPath);
        for(AutoConfig autoConfig : list)
        {
            String print = String.format("Loader ClassName: %s, ConfigName: %s,ConfigDirName: %s /n",autoConfig.getClazz().getName(),autoConfig.getConfigName(),autoConfig.getConfigDirName());
            message.append(print);
        }
        createConfigData(projectID,list,message);
    }

    public static void createConfigFromJar(Integer projectID,ClassLoader classLoader,String configDataPath,StringBuffer message) throws BaseException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<AutoConfig> list = getAutoConfig(configDataPath,classLoader);
        for(AutoConfig autoConfig : list)
        {
            String print = String.format("Loader ClassName: %s, ConfigName: %s,ConfigDirName: %s /n",autoConfig.getClazz().getName(),autoConfig.getConfigName(),autoConfig.getConfigDirName());
            message.append(print);
        }
        createConfigData(projectID,list,message);
    }

    public static  void createConfigFromJar(ProjectData data,StringBuffer message) throws BaseException, IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<AutoConfig> list = getAutoConfig(data.getConfigDataPath(),data.getClassLoader());
        createConfigData(data.getId(),list,message);
    }

    public static void createConfigData(Integer projectId, List<AutoConfig> list, StringBuffer error) throws IOException, BaseException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        for(AutoConfig autoConfig : list )
        {
            createConfigData(projectId, autoConfig, error);
        }
    }
    
    public static void createConfigData(Integer projectId,AutoConfig autoConfig,StringBuffer sb) throws IOException, NoSuchMethodException, BaseException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    	ConfigData data = new ConfigData();
        data.setName(autoConfig.getConfigName());
        data.setProjectID(projectId);
        data.setClazzName(autoConfig.getClazz().getName());
        String path = getPath(ServerConfig.getRootDir(),projectId,autoConfig.getConfigName(),ConfigType.excel);
        IOTinyUtils.createFile(path);
        data.setExcelPath(path);
        AutoConfig<AutoExcel> excelAutoConfig = AutoConfigUtil.instanceExcelNoFile(autoConfig);
        Class clazz = excelAutoConfig.getClazz();
        path = getPath(ServerConfig.getRootDir(),projectId,autoConfig.getConfigName(),ConfigType.xml);
        IOTinyUtils.createFile(path);
        data.setXmlPath(path);
        AutoConfigUtil.createXML(autoConfig,new ArrayList<Comparable>(),path,true);
        data.setAutoConfig(autoConfig);
        try {
            ConfigDataEntity.put(data);
        } catch (BaseException e) {
            sb.append("Error Message : " + e.getMessage() + "/n");
            e.printStackTrace();
        }
    }


    private static List<AutoConfig> getAutoConfig(String jarPath) throws JarLoadException {
        List<AutoConfig> list = new ArrayList<>();
        try {
            List<AutoConfig> configList = AutoConfigUtil.loadConfig(jarPath);
            for(AutoConfig autoConfig : configList)
            {
                list.add(autoConfig);
            }
        } catch (NoSuchFileException e) {
            throw(new JarLoadException("Jar包不存在无法导入",e.getCause()));
        } catch (Exception e) {
            throw(new JarLoadException("JAR包导入失败",e.getCause()));
        }
        return list;
    }

    @SuppressWarnings("rawtypes")
    public static List<AutoConfig> getAutoConfig(String configDataPath,ClassLoader classLoader) throws JarLoadException {
        List<AutoConfig> list = new ArrayList<>();
        try {

			List<AutoConfig> configList = AutoConfigUtil.loaderFromExternJar(configDataPath,classLoader);
            for(AutoConfig autoConfig : configList)
            {
                list.add(autoConfig);
            }
        } catch (NoSuchFileException e) {
            throw(new JarLoadException("Jar包或ConfigData不存在",e.getCause()));
        } catch (Exception e) {
            e.printStackTrace();
            throw(new JarLoadException("JAR包导入失败",e.getCause()));
        }
        return list;
    }

    /**
     * 从XML配置文件中读取配置
     * @param configData
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws DocumentException
     * @throws BaseException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    public static <T extends Comparable> List<Comparable> getConfigFromXML(ConfigData configData,Class<T> clazz,ClassLoader classLoader) throws IOException, NoSuchMethodException, DocumentException, BaseException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        String xml = IOTinyUtils.read(configData.getXmlPath());
        List<Comparable> list = new ArrayList<>();
        if(xml==null||StringUtils.isEmpty(xml))
            return list;
        list = AutoConfigUtil.readXml(configData.getAutoConfig(),configData.getAutoConfig().getClazz(),xml,true,classLoader);
        return list;
    }

}
