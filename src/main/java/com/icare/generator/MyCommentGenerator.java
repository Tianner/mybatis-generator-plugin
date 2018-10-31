package com.icare.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author thb
 */
public class MyCommentGenerator implements CommentGenerator {

    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private boolean addRemarkComments;
    private String currentDateStr;
    private String author;
    private SimpleDateFormat dateFormat;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
        systemPro = System.getProperties();
    }


    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     *
     * @param xmlElement
     *            the xml element
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }

        systemPro = System.getProperties();
        currentDateStr = (new SimpleDateFormat(properties.getProperty("dateFormat"))).format(new Date());
    }



    /**
     * Returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        addClassComment(innerClass,introspectedTable,false);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        if (addRemarkComments) {
            StringBuilder sb = new StringBuilder();
            innerClass.addJavaDocLine("/**");
            sb.append(" * @description:");
            sb.append(introspectedTable.getFullyQualifiedTable()+"表的实体类");
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
            sb.append(" * @version:  ");
            sb.append(properties.getProperty("version"));
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
            sb.append(" * @author:  ");
            sb.append(properties.getProperty("author"));
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            sb.setLength(0);
            sb.append(" * @createTime: ");
            sb.append(currentDateStr);
            innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
            innerClass.addJavaDocLine(" */");
        }
    }

    /**
     * 添加类导入
     * @param innerClass
     */
    private void addClassImport(TopLevelClass innerClass){
//        innerClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        boolean createSetGet = Boolean.parseBoolean(properties.getProperty("createSetGet"));
        if(!createSetGet){
            innerClass.addImportedType("lombok.Data");
        }
    }

    /**
     * 添加类注解
     * @param innerClass
     */
    private void addClassAnnotaion(TopLevelClass innerClass){
        boolean createSetGet = Boolean.parseBoolean(properties.getProperty("createSetGet"));
        if(!createSetGet) {
            innerClass.addAnnotation("@Data");
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        addClassImport(topLevelClass);
        addClassAnnotaion(topLevelClass);
        addClassComment(topLevelClass,introspectedTable,false);

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        String remarks = null;
        if(introspectedColumn!=null){
            remarks = introspectedColumn.getRemarks();
        }else{
            remarks =  introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        }
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            StringBuilder sb = new StringBuilder();
            field.addJavaDocLine("/**");
            sb.append(" * ");
            sb.append(remarks);
            field.addJavaDocLine(sb.toString().replace("\n", " "));
            field.addJavaDocLine(" */");
            addFieldAnnotation(field,remarks);
        }
    }

    /**
     * 添加属性注解
     * @param field
     * @param remarks
     */
    private void addFieldAnnotation(Field field,String remarks){
//        field.addAnnotation("@ApiModelProperty(\""+remarks+"\")");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        addFieldComment(field,introspectedTable,null);
    }

    @Override
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

    }
}
