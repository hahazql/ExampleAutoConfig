package model.bean;

/**
 * Created by zql on 2015/10/14.
 */
public class ProjectData
{
    private int id = -1;
    private String name;
    private String projectDesc;
    private String jarPath;
    private String configDataPath;
    private ClassLoader classLoader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.replaceAll(" ","_");
        this.name = name;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getConfigDataPath() {
        return configDataPath;
    }

    public void setConfigDataPath(String configDataPath) {
        this.configDataPath = configDataPath;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
