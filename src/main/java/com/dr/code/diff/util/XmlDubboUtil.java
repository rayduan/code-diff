package com.dr.code.diff.util;

import com.dr.code.diff.common.log.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
    private static JAXPXPathEngine xpathEngine = new JAXPXPathEngine();
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static String getDubboService(InputStream inputStream) {
        String className = "";
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> {
                System.out.println("Ignoring " + publicId + ", " + systemId);
                return new InputSource(new StringReader(""));
            });
            Document document = builder.parse(inputStream);
            //先获取对比差异
            Iterable<Node> dubboMatches = xpathEngine.selectNodes("/beans/dubbo:service", document.getDocumentElement());
            if (!dubboMatches.iterator().hasNext()) {
                return StringUtils.EMPTY;
            } else {
                Node service = dubboMatches.iterator().next();
                //获取dubbo的class
                className = service.getAttributes().getNamedItem("interface").getNodeValue();
                className = className.replace(".", "/");
            }
            return className;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            LoggerUtil.error(log, "非dubbo xml文件", e.getMessage());
            return "";
        }
    }


    public static List<String> scanDubboService() throws IOException {
        List<String> dubboServices = new ArrayList<String>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:/**/*.xml");
        for (Resource resource : resources) {
            // 处理 resource
            // 例如，可以使用 resource.getInputStream() 来读取 XML 内容
            String dubboService = getDubboService(resource.getInputStream());
            dubboServices.add(dubboService);
        }
        return dubboServices;
    }

}
