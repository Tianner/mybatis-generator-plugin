package com.icare.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.XmlFileMergerJaxp;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : Tianhaibo
 *    email   : tianhaibo@jshuii.com
 *    time    : 2018/10/29   20:04
 *    desc    :
 *    version : v1.0
 * </pre>
 */

public class ExtMapperPluginAdapter extends PluginAdapter {

    public static final String EXT_MAPPER = "ExtMapper";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        List<GeneratedJavaFile> list = new ArrayList<>();
        JavaClientGeneratorConfiguration configuration = context.getJavaClientGeneratorConfiguration();
        List<TableConfiguration> tableConfigurations = this.getContext().getTableConfigurations();
        tableConfigurations.stream().forEach(o->{
            String referenceName = configuration.getTargetPackage().concat(".").concat(o.getDomainObjectName()).concat(EXT_MAPPER);
            if(checkJavaFileExists(context,referenceName)){
                return;
            }
            Interface anInterface = new Interface(referenceName);
            anInterface.setVisibility(JavaVisibility.PUBLIC);
            GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(anInterface, configuration.getTargetProject(), context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING), context.getJavaFormatter());
            list.add(generatedJavaFile);
        });
        return list;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {

            List<GeneratedXmlFile> list = new ArrayList<>();
            SqlMapGeneratorConfiguration configuration = context.getSqlMapGeneratorConfiguration();
            JavaClientGeneratorConfiguration clientConfiguration = context.getJavaClientGeneratorConfiguration();
            JavaModelGeneratorConfiguration domainConfiguration = context.getJavaModelGeneratorConfiguration();
            List<TableConfiguration> tableConfigurations = this.getContext().getTableConfigurations();
            tableConfigurations.stream().forEach(o->{
                try {
                    //进行xml文件创建操作
                    XmlElement xmlElement = new XmlElement("mapper");
                    String referenceName = clientConfiguration.getTargetPackage().concat(".").concat(o.getDomainObjectName()).concat(EXT_MAPPER);
                    xmlElement.addAttribute(new Attribute("namespace",referenceName));
                    XmlElement element = new XmlElement("resultMap");
                    element.addAttribute(new Attribute("id","BaseResultMap"));
                    element.addAttribute(new Attribute("extends", clientConfiguration.getTargetPackage().concat(".").concat(o.getDomainObjectName()).concat("Mapper").concat(".BaseResultMap")));
                    element.addAttribute(new Attribute("type",domainConfiguration.getTargetPackage().concat(".").concat(o.getDomainObjectName())));
                    xmlElement.addElement(element);
                    XmlElement element2 = new XmlElement("sql");
                    element2.addAttribute(new Attribute("id","Base_Column_List"));
                    xmlElement.addElement(element2);
                    XmlElement element3 = new XmlElement("include");
                    element3.addAttribute(new Attribute("refid", clientConfiguration.getTargetPackage().concat(".").concat(o.getDomainObjectName()).concat("Mapper").concat(".Base_Column_List")));
                    element3.getAttributes().sort(Attribute::compareTo);
                    element2.addElement(element3);
                    Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
                    document.setRootElement(xmlElement);
                    String xmlFile = checkXmlFileExists(context, o.getDomainObjectName());
                    if(xmlFile!=null){
                        //进行xml文件节点增加操作
                        InputSource newSource = new InputSource(new InputStreamReader(new ByteArrayInputStream(document.getFormattedContent().getBytes()), "UTF-8"));
                        InputSource existSource = new InputSource(new InputStreamReader(new FileInputStream(xmlFile), "UTF-8"));
                        String source = XmlFileMergerJaxp.getMergedSource(newSource, existSource, null);
                        FileWriter fileWriter = new FileWriter(xmlFile);
                        fileWriter.write(source);
                        fileWriter.close();
                        return;
                    }else{
                        GeneratedXmlFile file = new GeneratedXmlFile(document, o.getDomainObjectName().concat(EXT_MAPPER).concat(".xml"), configuration.getTargetPackage(), context.getSqlMapGeneratorConfiguration().getTargetProject(), true, context.getXmlFormatter());
                        list.add(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return list;
    }

    private static boolean checkJavaFileExists(Context context,String referenceName){
        try {
            String project = context.getJavaClientGeneratorConfiguration().getTargetProject();
            if(!project.endsWith("/") && !project.endsWith("\\")){
                project = project.concat("\\");
            }
            String concat = project.concat(referenceName);
            concat = concat.replaceAll("[.]","\\\\");
            concat = concat.replaceAll("/","\\\\");
            concat = concat.concat(".java");
            File file = new File(concat);
            if(file.exists()){
                System.out.println("文件".concat(referenceName).concat("已经存在,不再创建"));
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private static String checkXmlFileExists(Context context,String referenceName){
        String concat = null;
        try {
            String project = context.getSqlMapGeneratorConfiguration().getTargetProject();
            if(!project.endsWith("/") && !project.endsWith("\\")){
                project = project.concat("\\");
            }
            concat = project.concat("mapper\\").concat(referenceName).concat(EXT_MAPPER);
            concat = concat.replaceAll("[.]","\\\\");
            concat = concat.replaceAll("/","\\\\");
            concat = concat.concat(".xml");
            File file = new File(concat);
            if(file.exists()){
                System.out.println("文件".concat(referenceName).concat(EXT_MAPPER).concat(".xml").concat("已经存在,执行append"));
                return concat;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return concat;
        }
    }
}