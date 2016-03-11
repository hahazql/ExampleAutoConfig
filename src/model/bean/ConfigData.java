package model.bean;


import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoExcel;
import com.hahazql.util.clazz.PackageUtil;
import com.hahazql.util.exception.BaseException;
import com.hahazql.util.format.HexBin;
import com.hahazql.util.format.SerializeUtil;
import com.sun.jmx.snmp.ThreadContext;

/**
 * Created by zql on 2015/10/12.
 */
public class ConfigData
{
    /**
     * 项目ID
     */
    private Integer id = -1;
    /**
     * 项目ID
     */
    private Integer projectID = -1;
    /**
     * 配置名
     */
    private String name;
    /**
     * 类名
     */
    private String clazzName;
    /**
     * XML配置保存路径
     */
    private String xmlPath;
    /**
     * Excel保存路径
     */
    private String excelPath;

    private AutoConfig<AutoExcel> autoConfig;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ConfigData) {
            AutoConfig<AutoExcel> thisAutoConfig = null;
            return (this.getClazzName().equals(((ConfigData) obj).getClazzName()) && this.projectID == ((ConfigData) obj).getProjectID());
        }
        return (this==obj);
    }

    public AutoConfig<AutoExcel> getAutoConfig() {
        return autoConfig;
    }

    public void setAutoConfig(AutoConfig<AutoExcel> autoConfig) {
        this.autoConfig = autoConfig;
    }
}
