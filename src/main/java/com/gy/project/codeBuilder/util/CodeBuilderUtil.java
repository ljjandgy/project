package com.gy.project.codeBuilder.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
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
        String javaPath = map.get("javaPath");//后台文件生成位置
        String htmlPath = map.get("htmlPath");//前台文件生成位置
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
        gc.setOutputDir(javaPath);//后台生成路径
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
        strategy.setEntityBuilderModel(true);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.gy.project");//父文件夹
        pc.setModuleName(packageName);//包名
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("dao.mapper");

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义 xxx.html 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/list.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //判断前台文件夹是否存在
                File file=new File(htmlPath + tableInfo.getEntityPath());
                if(!file.exists()){//如果文件夹不存在
                    file.mkdir();//创建文件夹
                }

                // 自定义输入文件名称
                return htmlPath + tableInfo.getEntityPath() + "/" + tableInfo.getEntityPath() + ".html";
            }
        });

        // 自定义  xxxAdd.html 生成
        focList.add(new FileOutConfig("/templates/add.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //判断前台文件夹是否存在
                File file=new File(htmlPath + tableInfo.getEntityPath());
                if(!file.exists()){//如果文件夹不存在
                    file.mkdir();//创建文件夹
                }

                // 自定义输入文件名称
                return htmlPath + tableInfo.getEntityPath() + "/" + tableInfo.getEntityPath() + "Add.html";
            }
        });

       // 自定义  xxxUpdate.html 生成
        focList.add(new FileOutConfig("/templates/update.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //判断前台文件夹是否存在
                File file=new File(htmlPath + tableInfo.getEntityPath());
                if(!file.exists()){//如果文件夹不存在
                    file.mkdir();//创建文件夹
                }

                // 自定义输入文件名称
                return htmlPath + tableInfo.getEntityPath() + "/" + tableInfo.getEntityPath() + "Update.html";
            }
        });

        cfg.setFileOutConfigList(focList);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller.java.vm");
     /*   tc.setEntity("...");
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
        //前台html生成配置
        mpg.setCfg(cfg);
        //模板生成配置
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
    }

}
