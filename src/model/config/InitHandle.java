package model.config;

import com.hahazql.util.exception.BaseException;
import model.entity.ConfigDataEntity;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by zql on 2015/10/15.
 */
public class InitHandle implements ServletContextListener, HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Server startup!");
        System.out.println("Loader spring");
        ServerContent.serverConfigDao.init();
        System.out.println("Spring loader success");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        System.out.println("Loader Config");
        try {
            ConfigDataEntity.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Config loader success");
    }
}
