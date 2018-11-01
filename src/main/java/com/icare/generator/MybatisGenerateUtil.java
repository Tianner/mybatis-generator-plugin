package com.icare.generator;

import com.icare.generator.plugins.ExtMapperPluginAdapter;
import com.icare.generator.plugins.NoMethodPluginAdapter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * 代码生成器! 需要在maven中引入如下jar包即可用 <dependency>
 * <groupId>org.mybatis.generator</groupId>
 * <artifactId>mybatis-generator-core</artifactId>
 * <version>1.3.2</version>
 * </dependency>
 * <p>
 * 使用方法： 1. 填写好表名 2. 如果是模块划分再填写模块名 3. 直接运行main搞定
 *
 * @author liujian
 * @version 2016/6/7 0007
 */
public class MybatisGenerateUtil {

    /**
     * 开发人员设置
     */
    // 当前项目路径
    private static String projectPath = new File(MybatisGenerateUtil.class.getResource("/").getPath()).getAbsolutePath().replace("\\target\\classes","");
    private static String SPOT=".";

    /************************用户自定义-start********************************/
    // 需要生成的代码的表,可以多个，用英文逗号“,”分割，例如：incubator_industry,incubator_magazine_citys
    private static String tableName = "t_bracelet_red_packet";
    //实体名,多个时，用逗号分开，并且和tableName顺序对应
    private static String entity="RedPacket";

    // 加上service包的上层目录路径，例如：com.wy.imc.base
    private static String javaRootPath = "com.icare.bracelet";
    //实体类上级路径文件夹
    private static String modelPackageName="domain";
    //mapper接口上级路径文件夹
    private static String mapperPackageName="mapper";
    // mapper对应xml文件生成的路径。默认在resources，如果配置mybatis，标识生成到resources/mybatis下
    private static String mapTargetPackage = "mapper";

    // 如果是模块划分，则需要加上模块名字
    private static String module = "";
    /************************用户自定义-end********************************/

    /**
     * 配置管理员设置
     */
    // 默认mysql，如果是mysql可以不用设置这个变量
    private static String driverClass = "com.mysql.jdbc.Driver";
    // 数据库访问地址
    private static String dbUrl = "127.0.0.1:3306";
    private static String dbName = "t_bracelet";
    private static String connectionURL = "jdbc:mysql://"+dbUrl+"/"+dbName+"?useSSL=false";
    // 数据库用户名
    private static String userId = "root";
    // 数据库密码
    private static String password = "123456";
    // Java模型生成的路径，例如：com.easyrong.domain
    private static String modelTargetPackage = javaRootPath+SPOT+modelPackageName;
    // dao文件生成的路径，例如：com.easyrong.dao
    private static String iDaoTargetPackage = javaRootPath +SPOT+ mapperPackageName;

    //创建配置
//    private static Boolean suppressDate = true;
    //是否去除自动生成的注释 true：是 ： false:否
    private static Boolean suppressAllComments = false;
    private static Boolean addRemarkComments = true;
    private static Boolean createSetGet = true;
    private static Boolean generateExtMapper = false;

    private static Boolean insertStatementEnabled = true;
    private static Boolean selectByPrimaryKeyStatementEnabled = true;
    private static Boolean updateByPrimaryKeyStatementEnabled = true;
    private static Boolean deleteByPrimaryKeyStatementEnabled = true;

    private static Boolean selectByExampleStatementEnabled = false;
    private static Boolean updateByExampleStatementEnabled = false;
    private static Boolean deleteByExampleStatementEnabled = false;
    private static Boolean countByExampleStatementEnabled = false;
    //作者
    private static String author = "admin";
    private static String version = "1.0";

    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";


    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void doGenerateCode() {
        try {
            Configuration config = new Configuration();
            Context context = new Context(null);
            context.setId("MysqlTables");
            context.setTargetRuntime("MyBatis3");
            // 设置公共的配置
            doCommentGenerator(context);
            // 设置数据库
            doJdbcConnection(context);
            // 设置Java类型解析器
            doJavaTypeResolver(context);
            // 设置Java模型
            doJavaModelGenerator(context);
            // 设置Map文件
            doSqlMapGenerator(context);
            // 设置dao
            doJavaClientGenerator(context);
            // 设置table
            doTable(context);
            //设置不生成domain的get和set方法
            doCreateGeterAndSetter(context,createSetGet);
            //设置生成extmapper
            doCreateExtFile(context,generateExtMapper);
            config.addContext(context);
            // overwrite
            DefaultShellCallback shellCallback = new DefaultShellCallback(true);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, new ArrayList<>());
            myBatisGenerator.generate(null, new HashSet(), new HashSet());

            System.out.println("表：" + tableName + ",自动生成代码成功~!");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    private static void doCreateExtFile(Context context,boolean isCreate) {
        if(!isCreate){
            return;
        }
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(ExtMapperPluginAdapter.class.getName());
        context.addPluginConfiguration(pluginConfiguration);
    }

    private static void doCreateGeterAndSetter(Context context,boolean isCreate) {
        if(suppressAllComments || isCreate){
            return;
        }
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType(NoMethodPluginAdapter.class.getName());
        context.addPluginConfiguration(pluginConfiguration);
    }

    private static void doJavaTypeResolver(Context context) {
        JavaTypeResolverConfiguration resolver = new JavaTypeResolverConfiguration();
        resolver.getProperties().setProperty("forceBigDecimals", "false");
        context.setJavaTypeResolverConfiguration(resolver);
    }

    private static void doCommentGenerator(Context context) {
        CommentGeneratorConfiguration common = new CommentGeneratorConfiguration();
        Properties properties = common.getProperties();
        //div Comment class
        common.setConfigurationType(MyCommentGenerator.class.getName());
//        properties.setProperty("suppressDate", suppressDate.toString());
        // 是否去除自动生成的注释 true：是 ： false:否
        properties.setProperty("suppressAllComments", suppressAllComments.toString());
        // 添加注释
        properties.setProperty("createSetGet", createSetGet.toString());
        properties.setProperty("addRemarkComments", addRemarkComments.toString());
        properties.setProperty("author", author);
        properties.setProperty("version", version);
        properties.setProperty("dateFormat", dateFormat);

        context.setCommentGeneratorConfiguration(common);
    }

    private static void doTable(Context context) {
        List<TableConfiguration> list = context.getTableConfigurations();
        list.clear();
        if (tableName != null && !"".equals(tableName)) {
            String[] tableArr = tableName.split(",");
            String[] entityArr = null;
            if(entity!=null && !"".equals(entity)){
                entityArr = entity.split(",");
            }
            if(entityArr!=null&&entityArr.length!=tableArr.length){
                System.err.println("用户配置信息异常，请检查entity和tableName数据是否对应!");
                return;
            }
            int count = 0;
            for (String tableName : tableArr) {
                TableConfiguration tableConfiguration = new TableConfiguration(context);
                tableConfiguration.setTableName(tableName);
                String bean = (entityArr!=null
                        && entityArr.length>=count+1
                        && !"".equals(entityArr[count]))
                        ?entityArr[count]:tableToBean(tableName);
                tableConfiguration.setDomainObjectName(bean);
                tableConfiguration.setInsertStatementEnabled(insertStatementEnabled);
                tableConfiguration.setSelectByPrimaryKeyStatementEnabled(selectByPrimaryKeyStatementEnabled);
                tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(updateByPrimaryKeyStatementEnabled);
                tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(deleteByPrimaryKeyStatementEnabled);

                tableConfiguration.setCountByExampleStatementEnabled(countByExampleStatementEnabled);
                tableConfiguration.setUpdateByExampleStatementEnabled(updateByExampleStatementEnabled);
                tableConfiguration.setDeleteByExampleStatementEnabled(deleteByExampleStatementEnabled);
                tableConfiguration.setSelectByExampleStatementEnabled(selectByExampleStatementEnabled);
                list.add(tableConfiguration);
                count++;
            }

        }
    }

    private static String tableToBean(String tableName) {
        int flag = tableName.indexOf("_");
        if (flag > 0) {
            tableName = tableName.toLowerCase();
            String[] tableStr = tableName.split("_");
            String result = "";
            int index = 0;
            for (String tableTemp : tableStr) {
                index++;
                if (1 == index) {
                    continue;
                }
                result += tableTemp.substring(0, 1).toUpperCase() + tableTemp.substring(1);
            }
            return result;
        } else {
            return tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
        }
    }

    private static void doJavaClientGenerator(Context context) {
        JavaClientGeneratorConfiguration dao = new JavaClientGeneratorConfiguration();
        if (module != null && !"".equals(module)) {
            dao.setTargetPackage(iDaoTargetPackage + "." + module);
        } else {
            dao.setTargetPackage(iDaoTargetPackage);
        }
        dao.setConfigurationType("XMLMAPPER");
        if (projectPath != null && !"".equals(projectPath)) {
            dao.setTargetProject(projectPath + "/src/main/java");
        } else {
            dao.setTargetProject("src/main/java");
        }
        dao.getProperties().setProperty("enableSubPackages", "true");
        context.setJavaClientGeneratorConfiguration(dao);
    }

    private static void doSqlMapGenerator(Context context) {
        SqlMapGeneratorConfiguration sql = new SqlMapGeneratorConfiguration();
        sql.setTargetPackage(mapTargetPackage);
        if (projectPath != null && !"".equals(projectPath)) {
            sql.setTargetProject(projectPath + "/src/main/resources");
        } else {
            sql.setTargetProject("src/main/resources");
        }
        sql.getProperties().setProperty("enableSubPackages", "true");
        context.setSqlMapGeneratorConfiguration(sql);
    }

    private static void doJavaModelGenerator(Context context) {
        JavaModelGeneratorConfiguration java = new JavaModelGeneratorConfiguration();
        if (module != null && !"".equals(module)) {
            java.setTargetPackage(modelTargetPackage + "." + module);
        } else {
            java.setTargetPackage(modelTargetPackage);
        }
        if (projectPath != null && !"".equals(projectPath)) {
            java.setTargetProject(projectPath + "/src/main/java");
        } else {
            java.setTargetProject("src/main/java");
        }
        java.getProperties().setProperty("enableSubPackages", "true");
        java.getProperties().setProperty("trimStrings", "true");
        context.setJavaModelGeneratorConfiguration(java);
    }

    private static void doJdbcConnection(Context context) {
        JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
        if (connectionURL != null && !"".equals(connectionURL)) {
            jdbc.setConnectionURL(connectionURL);
        }
        if (driverClass != null && !"".equals(driverClass)) {
            jdbc.setDriverClass(driverClass);
        }
        if (password != null && !"".equals(password)) {
            jdbc.setPassword(password);
        }
        if (userId != null && !"".equals(userId)) {
            jdbc.setUserId(userId);
        }
        context.setJdbcConnectionConfiguration(jdbc);
    }

    /**
     * 设置要读取得数据库表名，如：t_user，(当有多个表需要生成时，表名之间以英文逗号隔开集合，注意表名顺序和实体类名顺序的对应)
     * @param tableName
     */
    public static void setTableName(String tableName) {
        MybatisGenerateUtil.tableName = tableName;
    }

    /**
     * 设置生成的数据库表对应的实体类的名称，如：User (当有多个表需要生成时，实体类名之间以英文逗号隔开，注意表名顺序和实体类名顺序的对应)
     * @param entity
     */
    public static void setEntity(String entity) {
        MybatisGenerateUtil.entity = entity;
    }

    /**
     * 设置生成java代码的包路径，如：com.icare.bracelet
     * @param javaRootPath
     */
    public static void setJavaRootPath(String javaRootPath) {
        MybatisGenerateUtil.javaRootPath = javaRootPath;
        MybatisGenerateUtil.modelTargetPackage = javaRootPath+SPOT+modelPackageName;
        MybatisGenerateUtil.iDaoTargetPackage = javaRootPath+SPOT+mapperPackageName;
    }

    /**
     * 设置数据库服务地址，如：127.0.0.1:3306
     * @param dbUrl
     */
    public static void setDbUrl(String dbUrl) {
        MybatisGenerateUtil.dbUrl = dbUrl;
    }

    /**
     * 设置要连接的数据库名称，如:ecm_prod
     * @param dbName
     */
    public static void setDbName(String dbName) {
        MybatisGenerateUtil.dbName = dbName;
    }

    /**
     * 设置数据库连接用户名(可读即可)，如：admin
     * @param userId
     */
    public static void setUserId(String userId) {
        MybatisGenerateUtil.userId = userId;
    }

    /**
     * 设置数据库连接密码，如：123456
     * @param password
     */
    public static void setPassword(String password) {
        MybatisGenerateUtil.password = password;
    }

    /**
     * 是否生成domain的类和属性上的注释 true-生成，false-不生成
     * @param addRemarkComments
     */
    public static void setAddRemarkComments(Boolean addRemarkComments) {
        MybatisGenerateUtil.addRemarkComments = addRemarkComments;
    }

    /**
     * 设置生成代码中的注释中的创建者信息，如：admin
     * @param author
     */
    public static void setAuthor(String author) {
        MybatisGenerateUtil.author = author;
    }

    /**
     * 设置生成代码中的注释中的版本号，如：1.0
     * @param version
     */
    public static void setVersion(String version) {
        MybatisGenerateUtil.version = version;
    }

    /**
     * 是否生成set和get方法
     * @param createSetGet
     */
    public static void setGenerateSetGet(Boolean createSetGet) {
        MybatisGenerateUtil.createSetGet = createSetGet;
    }

    /**
     * 是否生成ExtMapper方法，如：UserExtMapper.java和UserExtMapper.xml
     * xml文件会生成在recourse/mapper目录下
     * @param generateExtMapper
     */
    public static void setGenerateExtMapper(Boolean generateExtMapper) {
        MybatisGenerateUtil.generateExtMapper = generateExtMapper;
    }

    /**
     * 不添加所有注释，包括导入，包括注解 true-不添加，false-添加
     * @param suppressAllComments
     */
    public static void setSuppressAllComments(Boolean suppressAllComments) {
        MybatisGenerateUtil.suppressAllComments = suppressAllComments;
    }

    /**
     * 设置是否生成insert方法
     * @param insertStatementEnabled
     */
    public static void setInsertStatementEnabled(Boolean insertStatementEnabled) {
        MybatisGenerateUtil.insertStatementEnabled = insertStatementEnabled;
    }

    /**
     *SelectByPrimaryKey方法
     * @param selectByPrimaryKeyStatementEnabled
     */
    public static void setSelectByPrimaryKeyStatementEnabled(Boolean selectByPrimaryKeyStatementEnabled) {
        MybatisGenerateUtil.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
    }

    /**
     * UpdateByPrimaryKey方法
     * @param updateByPrimaryKeyStatementEnabled
     */
    public static void setUpdateByPrimaryKeyStatementEnabled(Boolean updateByPrimaryKeyStatementEnabled) {
        MybatisGenerateUtil.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
    }

    /**
     * DeleteByPrimaryKey方法
     * @param deleteByPrimaryKeyStatementEnabled
     */
    public static void setDeleteByPrimaryKeyStatementEnabled(Boolean deleteByPrimaryKeyStatementEnabled) {
        MybatisGenerateUtil.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
    }

    /**
     * SelectByExample方法
     * @param selectByExampleStatementEnabled
     */
    public static void setSelectByExampleStatementEnabled(Boolean selectByExampleStatementEnabled) {
        MybatisGenerateUtil.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
    }

    /**
     * UpdateByExample方法
     * @param updateByExampleStatementEnabled
     */
    public static void setUpdateByExampleStatementEnabled(Boolean updateByExampleStatementEnabled) {
        MybatisGenerateUtil.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
    }

    /**
     * DeleteByExample方法
     * @param deleteByExampleStatementEnabled
     */
    public static void setDeleteByExampleStatementEnabled(Boolean deleteByExampleStatementEnabled) {
        MybatisGenerateUtil.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
    }

    /**
     * CountByExample方法
     * @param countByExampleStatementEnabled
     */
    public static void setCountByExampleStatementEnabled(Boolean countByExampleStatementEnabled) {
        MybatisGenerateUtil.countByExampleStatementEnabled = countByExampleStatementEnabled;
    }
}

