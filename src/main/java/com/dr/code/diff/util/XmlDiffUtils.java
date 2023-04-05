package com.dr.code.diff.util;


import com.dr.code.diff.dto.MethodInfoResult;
import com.dr.code.diff.common.log.LoggerUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.transform.Source;
import java.util.List;
import java.util.Set;


/**
 * @Package: com.dr.code.diff.util
 * @Description: java类作用描述
 * @Author: rayduan
 * @CreateDate: 2023/3/1 15:37
 * @Version: 1.0
 * <p>
 */
@Slf4j
public class XmlDiffUtils {


    private static final JAXPXPathEngine xpathEngine = new JAXPXPathEngine();


    /**
     * xml diff
     * xml diff
     *
     * @param oldXmlPath 老xml路径
     * @param newXmlPath 新xml路径
     * @param list       列表
     */
    public static void getXmlDiffMethod(String oldXmlPath, String newXmlPath, Set<MethodInfoResult> list) {
        Source source = Input.fromFile(oldXmlPath).build();
        Source target = Input.fromFile(newXmlPath).build();
        Diff diff = DiffBuilder.compare(source)
                .withTest(target)
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes))
                .build();
        //先获取对比差异
        Iterable<Difference> differences = diff.getDifferences();
        if(!differences.iterator().hasNext()){
            return;
        }
        differences.forEach(
                e -> {
                    String xPath = e.getComparison().getControlDetails().getXPath();
                    String pathValid = getPathValid(xPath);
                    if (StringUtils.isBlank(pathValid)) {
                        return;
                    }
                    Iterable<Node> methodNode = xpathEngine.selectNodes(pathValid, target);
                    if (!methodNode.iterator().hasNext()) {
                        return;
                    }
                    Node targetNode = methodNode.iterator().next();
                    //获取方法名
                    String methodName = targetNode.getAttributes().getNamedItem("id").getNodeValue();
                    //获取方法参数
                    String paramType = "";
                    String parameterType = targetNode.getAttributes().getNamedItem("parameterType").getNodeValue();
                    if (!StringUtils.isBlank(parameterType)) {
                        paramType = StringUtil.getSplitLast(parameterType, ".");
                    }
                    MethodInfoResult methodInfoResult = MethodInfoResult.builder().methodName(methodName).parameters(Lists.newArrayList(paramType)).build();
                    list.add(methodInfoResult);
                }
        );
    }


    /**
     * xml diff类名
     *
     * @param newXmlPath 新xml路径
     * @return {@link String}
     */
    public static String getXmlDiffClassName(String newXmlPath) {
        String namespace = "";
        Source target = Input.fromFile(newXmlPath).build();
        //先获取对比差异
        Iterable<Node> mapperMatches = xpathEngine.selectNodes("/mapper", target);
        if (!mapperMatches.iterator().hasNext()) {
            return StringUtils.EMPTY;
        } else {
            Node mapper = mapperMatches.iterator().next();
            //获取mapper的namespace
            namespace = mapper.getAttributes().getNamedItem("namespace").getNodeValue();
            namespace = namespace.replace(".", "/");
        }
        return namespace;
    }


    /**
     * 获取路径有效,因为xml的格式一般为/mapper[1]/insert[1]/trim[1]格式，我们截取前两个就好了
     *
     * @param xPath x路径
     * @return {@link Boolean}
     */
    public static String getPathValid(String xPath) {
        try {
            if (StringUtils.isBlank(xPath)) {
                return StringUtils.EMPTY;
            }
            List<String> paths = Splitter.on("/").omitEmptyStrings().splitToList(xPath);
            List<String> subList = paths.subList(0, 2);
            return "/" + String.join("/", subList);
        } catch (Exception e) {
            LoggerUtil.error(log, "非sql变更，忽略");
            return StringUtils.EMPTY;
        }
    }

}
