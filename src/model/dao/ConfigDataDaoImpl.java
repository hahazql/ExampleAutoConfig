package model.dao;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import model.bean.ConfigData;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by zql on 2015/10/12.
 */
public class ConfigDataDaoImpl extends SqlMapClientDaoSupport
{
    public String _stmt = "configData.";
    public ConfigDataDaoImpl() {
        super();
    }

    @SuppressWarnings("unchecked")
    public List<ConfigData> selectAll() throws SQLException {
        String stmt = _stmt + "selectAll";
        return getSqlMapClientTemplate().queryForList(stmt);

    }

    public Integer insert(ConfigData projectData) throws SQLException {
        String stmt = _stmt + "insert";
        Object newKey = getSqlMapClientTemplate().insert(stmt, projectData);
        return (Integer) newKey;
    }

    public int deleteByPrimaryKey(int id) throws SQLException {
        String stmt = _stmt + "deleteByPrimaryKey";
        return getSqlMapClientTemplate().delete(stmt, id);
    }

    public Integer updateByPrimaryKey(ConfigData record) throws SQLException {

        String stmt = _stmt + "updateByPrimaryKey";
        int rows = getSqlMapClientTemplate().update(stmt, record);
        return rows;

    }

    public ConfigData selectByPrimaryKey(int id) throws SQLException {
        String stmt = _stmt + "selectByPrimaryKey";
        ConfigData record = (ConfigData) getSqlMapClientTemplate().queryForObject(stmt, id);
        return record;
    }

    public List<ConfigData> selectByProjectID(int id)
    {
        String stmt = _stmt + "selectByProjectId";
        List<ConfigData> record = (List<ConfigData>) getSqlMapClientTemplate().queryForList(stmt,id);
        return record;
    }

    // 批量处理
    public void insertBatch(final Collection<ConfigData> list) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) {
                try {
                    executor.startBatch();
                    String stmt = _stmt + "insert";

                    for (ConfigData v : list) {
                        executor.insert(stmt, v);
                    }// for
                    executor.executeBatch();
                    return null;
                } catch (Exception ex) {
                    return null;
                }
            }
        });
    }

}
