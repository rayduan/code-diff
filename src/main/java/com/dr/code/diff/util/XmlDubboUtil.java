package com.dr.code.diff.util;

import com.dr.code.diff.common.log.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
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

    public static List<String> getDubboService(InputStream inputStream) {
        List<String> classNames = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> {
                System.out.println("Ignoring " + publicId + ", " + systemId);
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


    public static List<String> scanDubboService(String dubboXmlPath) {
        if(StringUtils.isBlank(dubboXmlPath)){
            return null;
        }
        try {
            List<String> dubboServices = new ArrayList<String>();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(dubboXmlPath);
            for (Resource resource : resources) {
                // 处理 resource
                // 例如，可以使用 resource.getInputStream() 来读取 XML 内容
                List<String> dubboService = getDubboService(resource.getInputStream());
                if (CollectionUtils.isEmpty(dubboService)) {
                    continue;
                }
                dubboServices.addAll(dubboService);
            }
            return dubboServices;
        } catch (Exception e) {
            log.error("获取dubbo xml失败", e);
            return null;
        }
    }


}
