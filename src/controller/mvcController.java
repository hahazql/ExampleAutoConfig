package controller;

import com.hahazql.util.auto.config.AutoConfig;
import com.hahazql.util.auto.config.AutoConfigUtil;
import com.hahazql.util.exception.BaseException;
import model.bean.ConfigData;
import model.config.ServerConfig;
import model.config.ServerContent;
import model.exception.JarLoadException;
import model.process.AutoConfigL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class mvcController {

    @RequestMapping("/")
    public String index(ModelMap model) {
        System.out.println("Project : "+ ServerConfig.getServerName());
        model.addAttribute("project", ServerConfig.getServerName());

        return "index";
    }

    @RequestMapping("/example")
    public String hello(){        
        return "example";
    }

    /**
     * 获取左侧导航栏
     * @return
     */
    @RequestMapping("/nav/{name}")
    public @ResponseBody String getNav()
    {
        return "OK!";
    }

    @RequestMapping("/testNav")
    public String testNav()
    {
        return "testNav";
    }
}
