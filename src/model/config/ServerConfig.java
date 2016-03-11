package model.config;

import com.hahazql.util.exception.BaseException;
import model.entity.ConfigDataEntity;
import model.threader.DaemonThreadFactory;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by zql on 2015/10/13.
 */
public class ServerConfig
{
    /**
     * 项目保存的根目录
     */
    public static String rootDir;
    public static String serverName;

    static ThreadFactory _threadFactory = new DaemonThreadFactory();
    public static Executor executor = Executors.newCachedThreadPool(_threadFactory);

    public final static int DOWN_TYPE_EXCEL = 1;
    public final static int DOWN_TYPE_XML = 2;

    public static String getRootDir() {
        return rootDir;
    }

    public static String getJarSaveDir(){
        return rootDir + File.separator + "jar";
    }

    public static String getServerName() {
        return serverName;
    }

    public static void setServerName(String serverName) {
        ServerConfig.serverName = serverName;
    }

    public static void setRootDir(String rootDir) {
        ServerConfig.rootDir = rootDir;
    }

    public void init()
    {
    }

}
