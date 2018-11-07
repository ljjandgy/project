package com.gy.project.codeBuilder;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * @Author: GY
 * @Date: 2018/11/7 14:00
 */
public class CodeBuilderUtil {

    /**
     * 后台代码生成
     */
    public void a(Map<String,String> map) {
        AutoGenerator mpg = new AutoGenerator();

        //接收从Controller传值
        String dirPath = map.get("dirPath");//文件生成位置
        String author = map.get("author");//JavaDoc author信息
        //数据库信息
        String driverName = map.get("driverName");
        String name = map.get("name");
        String psd = map.get("password");
        String url = map.get("url");
        String tableName = map.get("tableName");
        String tableNames[] = tableName.split(";");
        String tablePrefix = map.get("tablePrefix");
        String tablePrefixs[] = tablePrefix.split(";");
        String packageName = NamingStrategy.removePrefix(tableName,tablePrefix);//包名
        packageName = NamingStrategy.underlineToCamel(packageName);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dirPath);//生成路径
        gc.setAuthor(author);//JavaDoc author
        gc.setFileOverride(true); //是否覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false,一个实体类对应数据库一个表
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML 返回值
        gc.setBaseColumnList(true);// XML columList

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        /*dsc.setDbType(DbType.MYSQL);*/
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName(driverName);
        dsc.setUsername(name);
        dsc.setPassword(psd);
        dsc.setUrl(url);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /*strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意*/
        strategy.setTablePrefix(tablePrefixs);// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tableNames); // 需要生成的表，若整个数据库都全部生成，则注释本句即可
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        strategy.setEntityBuilderModel(true);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("");//父文件夹
        pc.setModuleName(packageName);//包名
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("dao.mapper");

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        /* TemplateConfig tc = new TemplateConfig();
        tc.setController("...");
        tc.setEntity("...");
        tc.setMapper("...");
        tc.setXml("...");
        tc.setService("...");
        tc.setServiceImpl("...");*/

        //全局配置
        mpg.setGlobalConfig(gc);
        //数据库配置
        mpg.setDataSource(dsc);
        //策略配置
        mpg.setStrategy(strategy);
        //包配置
        mpg.setPackageInfo(pc);
/*        //模板生成配置
        mpg.setTemplate(tc);*/

        // 执行生成
        mpg.execute();
    }

}
