### Mybatis反向生成工具，插件增强

    使用mybatis官方开发的插件包，便捷的进行数据库文件反向生成，为了更好更便捷的使用它，
    自己对插件包做了一些功能增强，做了封装，简化配置和使用

###### 1、增强功能介绍：

    1、增加插件，控制生成domain类是否生成get和set方法，不生成get和set方法时，可以生成lombok简洁模式
    2、增加插件，控制生成ExtMapper接口文件和对应的xml文件，可以做到更改数据库结构，mybatis相关代码基本不做改动
        
###### 2、使用说明：
    
    1、引入依赖
  
```
在项目的maven依赖中加入依赖
	<dependency>
			<groupId>com.github.Tianner</groupId>
			<artifactId>mybatis-generator-plugin</artifactId>
			<version>1.5</version>
	</dependency>
	
    <repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>

```
    2、拷贝执行示例代码
    在项目中任意类中创建main函数，拷贝依赖包中的com.icare.generator.Test类中的demo函数中的示例代码
    如下：
     
```
最简单配置：
    public static void main(String[] args) {
        MybatisGenerateUtil.setDbUrl("127.0.0.1:3306");
        MybatisGenerateUtil.setUserId("root");
        MybatisGenerateUtil.setPassword("123456");
        MybatisGenerateUtil.setDbName("t_bracelet");
        MybatisGenerateUtil.setTableName("t_bracelet_red_packet_water");
        MybatisGenerateUtil.setEntity("RedPacketWater");
        MybatisGenerateUtil.setJavaRootPath("com.icare.bracelet");
        MybatisGenerateUtil.doGenerateCode();
    }
    
自定义配置:
    public static void main(String[] args) {
        //数据库信息
        MybatisGenerateUtil.setDbUrl("127.0.0.1:3306");
        //数据库连接账号( 只读即可)
        MybatisGenerateUtil.setUserId("root");
        //数据库连接密码
        MybatisGenerateUtil.setPassword("123456");
        //数据库名
        MybatisGenerateUtil.setDbName("t_bracelet");
        //要反向生成的表名
        MybatisGenerateUtil.setTableName("t_bracelet_red_packet_water");
        //反向生成的表对应的实体类名
        MybatisGenerateUtil.setEntity("RedPacketWater");
        //配置生成的java类的包路径
        MybatisGenerateUtil.setJavaRootPath("com.icare.bracelet.test");
        //配置注释中创建者信息
        MybatisGenerateUtil.setAuthor("田海波");
        //配置注释中代码版本信息
        MybatisGenerateUtil.setVersion("1.5");
        //配置是否要生成注释
        MybatisGenerateUtil.setAddRemarkComments(true);
        //配置是否抑制所有注释包括注解信息的生成
        MybatisGenerateUtil.setSuppressAllComments(false);
        //配置是否要生成get和set方法
        MybatisGenerateUtil.setGenerateSetGet(false);
        //配置是否要生成ExtMaper文件
        MybatisGenerateUtil.setGenerateExtMapper(true);
        //配置相关方法是否要生成，默认不生成Example方法
        MybatisGenerateUtil.setInsertStatementEnabled(true);
        MybatisGenerateUtil.setSelectByPrimaryKeyStatementEnabled(true);
        MybatisGenerateUtil.setUpdateByPrimaryKeyStatementEnabled(true);
        MybatisGenerateUtil.setDeleteByPrimaryKeyStatementEnabled(true);
        MybatisGenerateUtil.setSelectByExampleStatementEnabled(false);
        MybatisGenerateUtil.setUpdateByExampleStatementEnabled(false);
        MybatisGenerateUtil.setCountByExampleStatementEnabled(false);
        MybatisGenerateUtil.setCountByExampleStatementEnabled(false);
        //执行生成
        MybatisGenerateUtil.doGenerateCode();
    }
```

生成后，可以在项目下看到有指定的包路径文件和文件夹被创建出来

**注意xml文件默认生成在项目下resources文件夹下mapper文件夹下，如果这个文件夹不存在，那么xml文件不会被创建出来**

代码地址：https://github.com/Tianner/mybatis-generator-plugin