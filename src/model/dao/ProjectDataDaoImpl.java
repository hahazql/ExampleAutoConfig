package model.dao;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import model.bean.ProjectData;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by zql on 2015/10/14.
 */
public class ProjectDataDaoImpl extends SqlMapClientDaoSupport
{
    public String _stmt = "projectData.";
    public ProjectDataDaoImpl() {
        super();
    }

    @SuppressWarnings("unchecked")
    public List<ProjectData> selectAll() throws SQLException {
        String stmt = _stmt + "selectAll";
        return getSqlMapClientTemplate().queryForList(stmt);

    }

    public Integer insert(ProjectData projectData) throws SQLException {
        String stmt = _stmt + "insert";
        Object newKey = getSqlMapClientTemplate().insert(stmt, projectData);
        return (Integer) newKey;
    }

    public int deleteByPrimaryKey(int id) throws SQLException {
        String stmt = _stmt + "deleteByPrimaryKey";
        return getSqlMapClientTemplate().delete(stmt, id);
    }

    public Integer updateByPrimaryKey(ProjectData record) throws SQLException {

        String stmt = _stmt + "updateByPrimaryKey";
        int rows = getSqlMapClientTemplate().update(stmt, record);
        return rows;

    }

    public ProjectData selectByPrimaryKey(int id) throws SQLException {
        String stmt = _stmt + "selectByPrimaryKey";
        ProjectData record = (ProjectData) getSqlMapClientTemplate().queryForObject(stmt, id);
        return record;
    }

    // 批量处理
    public void insertBatch(final Collection<ProjectData> list) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) {
                try {
                    executor.startBatch();
                    String stmt = _stmt + "insert";

                    for (ProjectData v : list) {
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
