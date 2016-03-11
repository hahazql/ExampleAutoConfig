package model.process;

import com.hahazql.util.auto.config.AutoConfigUtil;
import model.bean.ConfigData;
import model.entity.ConfigDataEntity;
import model.io.IOTinyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

/**
 * Created by zql on 2015/10/19.
 */
public class DownFileL
{
    public static boolean downFile(Integer id,AutoConfigL.ConfigType type,final HttpServletResponse respons)
    {
        ConfigData configData = ConfigDataEntity.getConfigData(id);
        if(configData == null)
            return false;
        List<? extends Comparable> configList = ConfigDataEntity.getConfigList(id);
        try {
            if(type== AutoConfigL.ConfigType.xml)
            {
                AutoConfigUtil.createXML(configData.getAutoConfig(), configList,configData.getXmlPath(),true);
                return downFile(configData.getXmlPath(), respons);
            }
            else if(type == AutoConfigL.ConfigType.excel)
            {
                AutoConfigUtil.writeExcel(configList,configData.getAutoConfig().getClazz(),configData.getAutoConfig(),configData.getExcelPath(),true);
                return downFile(configData.getExcelPath(), respons);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static boolean downFile(String filePath,final HttpServletResponse response)
    {
        File file = new File(filePath);
        if(!file.exists())
            return false;
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=" + file.getName()));
        response.setContentLength((int) file.length());

        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            IOTinyUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
