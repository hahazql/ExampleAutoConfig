package controller;

import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoConfigUtil;
import com.hahazql.util.clazz.PackageUtilNew;
import com.hahazql.util.exception.BaseException;
import com.hahazql.util.io.IOTinyUtils;

import model.bean.ConfigData;
import model.bean.ProjectData;
import model.config.ServerConfig;
import model.entity.ConfigDataEntity;
import model.process.AutoConfigL;
import model.process.DownFileL;
import model.process.UploadFile;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zql on 2015/10/10.
 */
@Controller
@RequestMapping("/config")
public class GameConfigController
{
    @RequestMapping(value = "/downFile")
    public void downFile(Integer id,Integer type,HttpServletResponse response)
    {
        boolean isSuccess = true;
        switch (type)
        {
            case ServerConfig.DOWN_TYPE_EXCEL:
                isSuccess =  DownFileL.downFile(id, AutoConfigL.ConfigType.excel,response);
            case ServerConfig.DOWN_TYPE_XML:
                isSuccess = DownFileL.downFile(id, AutoConfigL.ConfigType.xml,response);
        }
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadConfigFile",method = RequestMethod.POST)
    public @ResponseBody String uploadConfigFile(MultipartFile configFile,Integer id,String indexSheetName,Integer startRow,Integer endRow)
    {
        StringBuffer buffer = new StringBuffer();

        ConfigData data = ConfigDataEntity.getConfigData(id);
        if(data == null)
           return "NO FOUND CONFIG";

        try {
            UploadFile.uploadFile(configFile,data.getExcelPath());
            List list = AutoConfigUtil.readExcel(data.getAutoConfig(), data.getAutoConfig().getClazz(), data.getExcelPath(), indexSheetName, startRow, endRow);
            AutoConfigUtil.createXML(data.getAutoConfig(),list,data.getXmlPath());
            ConfigDataEntity.putConfigList(data,list,buffer);
            buffer.append("Upload Success!/n");
        } catch (Exception e) {
            buffer.append("Error Message : " + e.getMessage() + "/n");
            e.printStackTrace();
        }
        return buffer.toString();
    }


    @RequestMapping(value="/uploadConfig/{id}")
    public ModelAndView uploadConfig(@PathVariable Integer id,ModelAndView map)
    {
        map.setViewName("uploadConfig");
        map.addObject("ID",id);
        return map;
    }

    @RequestMapping(value = "/downFileChose/{id}")
    public ModelAndView downFileChose(@PathVariable Integer id,ModelAndView map) {
        map.setViewName("downFileChose");
        map.addObject("ID",id);
        return map;
    }


    @RequestMapping(value = "/showConfigs/{id}")
    public ModelAndView getConfigs(@PathVariable Integer id)
    {
        ModelAndView map = new ModelAndView("showConfig");
        Collection<ConfigData> list = null;
        try {
            list = ConfigDataEntity.getConfigDataList(id);
            map.addObject("configList",list);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return map;
    }



    @RequestMapping(value = "/showProject")
    public ModelAndView showProject()
    {
        ModelAndView map = new ModelAndView("showProject");
        Collection<ProjectData> list = null;
        try {
            list = ConfigDataEntity.getProjects();
            map.addObject("ProjectList",list);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        return map;
    }


    @RequestMapping(value = "/uploadJar",method = RequestMethod.POST)
    public @ResponseBody String saveJar(@RequestParam("file") MultipartFile jarFile,@RequestParam("configData") MultipartFile configDataFile,String projectName,String projectDesc)
    {
        StringBuffer buffer = new StringBuffer();
        if(projectName == null)
            return "ProjectName is NULL/n";
        try {
            ProjectData data = UploadFile.uploadFile(jarFile,projectName,projectDesc, AutoConfigL.ConfigType.jar,null);
            data = UploadFile.uploadFile(configDataFile,projectName,projectDesc, AutoConfigL.ConfigType.configData,data);
            data.setClassLoader(PackageUtilNew.getClassLoader(data.getJarPath()));
            AutoConfigL.createConfigFromJar(data,buffer);
            ConfigDataEntity.put(data);
            buffer.append("Upload Success!/n");
        } catch (Exception e) {
           buffer.append("Error Message : " + e.getMessage() + "/n");
            e.printStackTrace();
        }
        return buffer.toString();
    }
    
    @RequestMapping(value="/updateJarShow/{projectID}",method = RequestMethod.GET)
    public ModelAndView updateJarShow(@PathVariable Integer projectID,ModelAndView map)
    {
    	map.setViewName("updateJarShow");
    	try {
			ProjectData date = ConfigDataEntity.getProject(projectID);
			map.addObject("id", date.getId());
			map.addObject("name", date.getName());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return map;
    }
    
    
    
    @RequestMapping(value = "/updateJar",method = RequestMethod.POST)
    public @ResponseBody String updateJar(MultipartFile jarFile,MultipartFile configDataFile,Integer projectID)
    {
    	StringBuffer sb = new StringBuffer();
    	try {
			ProjectData data = ConfigDataEntity.getProject(projectID);
			UploadFile.uploadFile(jarFile, data.getJarPath());
			UploadFile.uploadFile(configDataFile, data.getConfigDataPath());
			ClassLoader classLoader = PackageUtilNew.getClassLoader(data.getJarPath());
            data.setClassLoader(classLoader);
            ConfigDataEntity.put(data);
			List<AutoConfig> list  =AutoConfigL.getAutoConfig(data.getConfigDataPath(),classLoader);
			
			for(ConfigData configData : ConfigDataEntity.getConfigDataList(data.getId()))
			{
				int index = list.indexOf(configData.getAutoConfig());
				if(index>-1)
				{
			        String xml = IOTinyUtils.read(configData.getXmlPath());
					AutoConfig autoConfig = list.get(index);
					List<Comparable> configList = new ArrayList<>();
					try
					{
		              configList  = AutoConfigUtil.readXml(autoConfig,autoConfig.getClazz(), xml,false,classLoader);
					}
					catch(Exception e)
					{
                        e.printStackTrace();
						continue;
					}
		            ConfigDataEntity.clearConfigList(configData.getId());
		            ConfigDataEntity.putConfigList(configData.getId(),configList);
		            AutoConfigUtil.createXML(autoConfig,configList,configData.getXmlPath(),true);
		            list.remove(configData.getAutoConfig());
                    configData.setAutoConfig(autoConfig);
                    ConfigDataEntity.put(configData);
				}	
				else
				{
					/** 已经删除了的配置类是否要保存   （暂时未处理）**/
					
				}
			}
			
			
			for(AutoConfig autoConfig : list)
			{
				AutoConfigL.createConfigData(data.getId(), autoConfig, sb);
			}
			sb.append("Update Success!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sb.append(e.getMessage());
			e.printStackTrace();
		} 
    	return sb.toString();
    }
    

    @RequestMapping("/createProject")
    public String createProject()
    {
        return "createProject";
    }

}
