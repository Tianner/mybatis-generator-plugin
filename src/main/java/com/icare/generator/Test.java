package com.icare.generator;

/**
 * <pre>
 *    author  : Administrator
 *    email   : tianhaibo@jshuii.com
 *    time    : 2018/10/31   18:53
 *    desc    :
 *    version : v1.0
 * </pre>
 */

public class Test {

    public static void main(String[] args) {
        MybatisGenerateUtil.setDbUrl("127.0.0.1:3306");
        MybatisGenerateUtil.setDbName("t_bracelet");
        MybatisGenerateUtil.setTableName("t_bracelet_red_packet_water");
        MybatisGenerateUtil.setUserId("root");
        MybatisGenerateUtil.setPassword("123456");
        //可缺省
        MybatisGenerateUtil.setAuthor("田海波");
        //可缺省
        MybatisGenerateUtil.setVersion("1.5");
        MybatisGenerateUtil.setEntity("RedPacketWater");
        MybatisGenerateUtil.setJavaRootPath("com.icare.bracelet");
        //可缺省
        MybatisGenerateUtil.setAddRemarkComments(true);
        //可缺省
        MybatisGenerateUtil.setSuppressAllComments(false);
        //可缺省
        MybatisGenerateUtil.setGenerateSetGet(false);
        //可缺省
        MybatisGenerateUtil.setGenerateExtMapper(true);
        MybatisGenerateUtil.doGenerateCode();
    }
}
