package com.dr.code.diff.util;

import cn.hutool.core.io.FileUtil;
import com.dr.code.diff.common.log.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2023/5/2 17:45
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2023
 */
@Slf4j
public class XmlDubboUtil {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static List<String> getDubboService(File inputStream) {
        List<String> classNames = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> {
//                System.out.println("Ignoring " + publicId + ", " + systemId);
                return new InputSource(new StringReader(""));
            });
            Document document = builder.parse(inputStream);
            //获取dubbo的service
            NodeList serviceList = document.getElementsByTagName("dubbo:service");
            for (int i = 0; i < serviceList.getLength(); i++) {
                Element serviceElement = (Element) serviceList.item(i);
                String className = serviceElement.getAttribute("interface");
                className = className.replace(".", "/");
                classNames.add(className);
            }
            return classNames;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            LoggerUtil.error(log, "非dubbo xml文件", e.getMessage());
            return null;
        }
    }


    /**
     * 扫描dubbo xml
     *
     * @param resourcePath 资源路径
     * @return {@link List}<{@link String}>
     */
    public static List<String> scanDubboService(String resourcePath) {
        List<File> xmlFiles = FileUtil.loopFiles(new File(resourcePath), pathname -> pathname.getName().endsWith(".xml"));
        List<String> dubboServices = new ArrayList<String>();
        xmlFiles.forEach(file -> {
            List<String> dubboService = getDubboService(file);
            dubboServices.addAll(dubboService);
        });
        return dubboServices;
    }


}
