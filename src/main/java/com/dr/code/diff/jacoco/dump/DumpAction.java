package com.dr.code.diff.jacoco.dump;

import com.dr.code.diff.config.CustomizeConfig;
import com.dr.code.diff.jacoco.bean.RemoteJacocoServer;
import org.jacoco.cli.internal.core.data.ExecutionDataWriter;
import org.jacoco.cli.internal.core.runtime.RemoteControlReader;
import org.jacoco.cli.internal.core.runtime.RemoteControlWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @ProjectName: code-diff-parent
 * @Package: com.dr.code.diff.jacoco.dump
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2023/4/15 19:25
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2023
 */
@Service
public class DumpAction {

    @Resource(name = "asyncExecutor")
    private Executor executor;


    @Autowired
    private CustomizeConfig customizeConfig;


    /**
     * 批量dump
     *
     * @param serverList 服务器列表
     * @return {@link List}<{@link String}>
     */
    public List<String> dumpExecs(List<RemoteJacocoServer> serverList) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }
        List<CompletableFuture<String>> priceFuture = serverList.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> dumpExecData(item),
                        executor
                ))
                .collect(Collectors.toList());
        CompletableFuture.allOf(priceFuture.toArray(new CompletableFuture[0])).join();
        return priceFuture.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
    }


    /**
     * 转储执行数据
     *
     * @param server 服务器
     */
    public String dumpExecData(RemoteJacocoServer server) {
        if (null == server) {
            return null;
        }
        final FileOutputStream localFile;
        Socket socket = null;
        String execFile = customizeConfig.getJacocoRootPath()  + server.getExecFileName();
        try {
            localFile = new FileOutputStream(execFile);
            final ExecutionDataWriter localWriter = new ExecutionDataWriter(
                    localFile);

            // Open a socket to the coverage agent:
            socket = new Socket(InetAddress.getByName(server.getHost()), server.getPort());
            final RemoteControlWriter writer = new RemoteControlWriter(
                    socket.getOutputStream());
            final RemoteControlReader reader = new RemoteControlReader(
                    socket.getInputStream());
            reader.setSessionInfoVisitor(localWriter);
            reader.setExecutionDataVisitor(localWriter);

            // Send a dump command and read the response:
            writer.visitDumpCommand(true, false);
            if (!reader.read()) {
                throw new IOException("Socket closed unexpectedly.");
            }
            socket.close();
            localFile.close();
            return execFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
