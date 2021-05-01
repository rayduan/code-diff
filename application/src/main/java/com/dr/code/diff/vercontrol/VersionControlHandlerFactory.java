package com.dr.code.diff.vercontrol;

import com.dr.code.diff.dto.ClassInfoResult;
import com.dr.code.diff.dto.VersionControlDto;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: cmdb
 * @Package: com.dr.cmdb.application.filedcheck
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2021/3/30 10:10
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2021
 */
@Component
public class VersionControlHandlerFactory implements CommandLineRunner, ApplicationContextAware {
    private volatile ApplicationContext applicationContext;


    private static List<AbstractVersionControl> handlers;


    /**
     * 拼接变种责任链
     * @param args
     */
    @Override
    public void run(String... args) {
        Collection<AbstractVersionControl> checkHandlers = this.applicationContext.getBeansOfType(AbstractVersionControl.class).values();
        handlers = Lists.newArrayList(checkHandlers);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 执行方法校验
     * @param versionControlDto
     */
    public static List<ClassInfoResult> processHandler(VersionControlDto versionControlDto) {
        List<ClassInfoResult> result = null;
        for (int i = 0; i < handlers.size(); i++) {
            if(versionControlDto.getCodeManageTypeEnum().equals(handlers.get(i).getType())){
                result = handlers.get(i).handler(versionControlDto);
            }
        }
       return result;

    }


}
