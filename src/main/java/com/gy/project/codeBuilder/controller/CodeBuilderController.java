package com.gy.project.codeBuilder.controller;

import com.gy.project.codeBuilder.util.CodeBuilderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @Author: GY
 * @Date: 2018/11/7 10:01
 */
@Controller
@RequestMapping("/code")
public class CodeBuilderController {
    //获取数据库连接信息
    @Value("${spring.datasource.driver-class-name}")
    String driverName ;
    @Value("${spring.datasource.username}")
    String name ;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.url}")
    String url;

    CodeBuilderUtil codeBuilderUtil = new CodeBuilderUtil();
    @RequestMapping("/view")
    public String view(){
        return "/codeBuilder/codeBuilder.html";
    }

    @RequestMapping("/list")
    public Object getList(){
        return null;
    }

    @RequestMapping("/createFile")
    public void createFile(){
        //获取系统信息
        Properties properties = System.getProperties();
        //通过系统信息获得主机名称
        String author = properties.getProperty("user.name");
        //获取后台文件生成路径
        String javaPath = properties.getProperty("user.dir")+ "/src/main/java";
        //获取前台文件生成路径
        String htmlPath = properties.getProperty("user.dir")+ "/src/main/resources/views/";

        //生成文件的表名
        String tableName ="t_company";
        //表前缀
        String tablePrefix = "t_";

        Map<String,String> map = new HashMap();
        map.put("driverName",driverName);
        map.put("name",name);
        map.put("password",password);
        map.put("url",url);
        map.put("author",author);
        map.put("javaPath",javaPath);
        map.put("tableName",tableName);
        map.put("tablePrefix",tablePrefix);
        map.put("htmlPath",htmlPath);

        //调用
        codeBuilderUtil.a(map);
    }
}
