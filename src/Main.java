import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoConfigUtil;
import model.cache.ConfigLoaderCache;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Created by zql on 2015/10/16.
 */
public class Main
{
    public static void main(String[] args)
    {
        String jarPath ="C:/JAR/LoaderJarTest.jar";
        try {
            System.out.println(jarPath);
            List<AutoConfig> list = AutoConfigUtil.loadConfig(jarPath);
            for(AutoConfig autoConfig : list)
            {
                String print = String.format("ClassName: %s, ConfigName: %s,ConfigDirName: %s",autoConfig.getClazz().getName(),autoConfig.getConfigName(),autoConfig.getConfigDirName());
                System.out.println(print);
            }
            System.out.println("OK!处理完成!List Size: " + list.size());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
