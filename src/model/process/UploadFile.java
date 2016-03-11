package model.process;

import com.hahazql.util.exception.BaseException;
import model.bean.ConfigData;
import model.bean.ProjectData;
import model.config.ServerConfig;
import model.io.IOTinyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zql on 2015/10/19.
 */
public class UploadFile
{
    public static void uploadFile(MultipartFile file,String filePath) throws BaseException {
        if (file!=null&&!file.isEmpty()) {
            try {
                IOTinyUtils.createFile(filePath);
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                buffStream.write(bytes);
                buffStream.close();
            } catch (Exception e) {
                throw(new BaseException("上传文件失败！",e.getCause()));
            }
        }
        else
        {
            throw(new BaseException("上传文件不能为空!"));
        }
    }

    public static ProjectData uploadFile(MultipartFile file,String projectName,String projectDesc,AutoConfigL.ConfigType type,ProjectData data) throws BaseException {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                if(data == null)
                    data = AutoConfigL.createPorjectData(projectName, projectDesc, "", "");
                String filePath = AutoConfigL.getUpLoadPath(fileName,data.getId(), type);
                IOTinyUtils.createFile(filePath);
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                buffStream.write(bytes);
                buffStream.close();
                switch (type)
                {
                    case jar:
                        data.setJarPath(filePath);
                        break;
                    case configData:
                        data.setConfigDataPath(filePath);
                        break;
                }
                return data;
            } catch (Exception e) {
               throw(new BaseException("上传文件失败！",e.getCause()));
            }
        }
        else
        {
            throw(new BaseException("上传文件不能为空!"));
        }
    }
}
