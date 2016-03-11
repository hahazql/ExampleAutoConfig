package db;

import model.bean.ConfigData;
import model.config.ServerConfig;
import model.config.ServerContent;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zql on 2015/10/12.
 */
public class TestConnect
{
    @Test
    public void connection()
    {
        try {
            List<ConfigData> list =  ServerContent.configDataDao.selectAll();
            System.out.println(ServerConfig.getRootDir());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
