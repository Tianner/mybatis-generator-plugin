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
        MybatisGenerateUtil.setUserId("root");
        MybatisGenerateUtil.setPassword("123456");
        MybatisGenerateUtil.setDbName("t_bracelet");
        MybatisGenerateUtil.setTableName("t_bracelet_red_packet_water");
        MybatisGenerateUtil.setEntity("RedPacket1Water");
        MybatisGenerateUtil.setJavaRootPath("com.icare.bracelet");
        MybatisGenerateUtil.setAuthor("田海波");
        MybatisGenerateUtil.setVersion("1.5");
        MybatisGenerateUtil.setAddRemarkComments(true);
        MybatisGenerateUtil.setSuppressAllComments(false);
        MybatisGenerateUtil.setGenerateSetGet(false);
        MybatisGenerateUtil.setGenerateExtMapper(true);

        MybatisGenerateUtil.setInsertStatementEnabled(true);
        MybatisGenerateUtil.setSelectByPrimaryKeyStatementEnabled(true);
        MybatisGenerateUtil.setUpdateByPrimaryKeyStatementEnabled(true);
        MybatisGenerateUtil.setDeleteByPrimaryKeyStatementEnabled(true);

        MybatisGenerateUtil.setSelectByExampleStatementEnabled(false);
        MybatisGenerateUtil.setUpdateByExampleStatementEnabled(false);
        MybatisGenerateUtil.setCountByExampleStatementEnabled(false);
        MybatisGenerateUtil.setCountByExampleStatementEnabled(false);
        MybatisGenerateUtil.doGenerateCode();
    }

    public static void min() {
        MybatisGenerateUtil.setDbUrl("127.0.0.1:3306");
        MybatisGenerateUtil.setUserId("root");
        MybatisGenerateUtil.setPassword("123456");
        MybatisGenerateUtil.setDbName("t_bracelet");
        MybatisGenerateUtil.setTableName("t_bracelet_red_packet_water");
        MybatisGenerateUtil.setEntity("RedPacket1Water");
        MybatisGenerateUtil.setJavaRootPath("com.icare.bracelet");
        MybatisGenerateUtil.setAuthor("田海波");
        MybatisGenerateUtil.setVersion("1.5");
        MybatisGenerateUtil.setGenerateSetGet(false);
        MybatisGenerateUtil.setGenerateExtMapper(true);
        MybatisGenerateUtil.doGenerateCode();
    }
}
