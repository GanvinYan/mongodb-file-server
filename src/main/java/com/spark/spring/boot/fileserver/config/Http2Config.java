package com.spark.spring.boot.fileserver.config;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author gang
 */
@Configuration
public class Http2Config {



    /**
     * 采用Undertow作为服务器。
     * Undertow是一个用java编写的、灵活的、高性能的Web服务器，提供基于NIO的阻塞和非阻塞API，特点：
     * 非常轻量级，Undertow核心瓶子在1Mb以下。它在运行时也是轻量级的，有一个简单的嵌入式服务器使用少于4Mb的堆空间。
     * 支持HTTP升级，允许多个协议通过HTTP端口进行多路复用。
     * 提供对Web套接字的全面支持，包括JSR-356支持。
     * 提供对Servlet 3.1的支持，包括对嵌入式servlet的支持。还可以在同一部署中混合Servlet和本机Undertow非阻塞处理程序。
     * 可以嵌入在应用程序中或独立运行，只需几行代码。
     * 通过将处理程序链接在一起来配置Undertow服务器。它可以对各种功能进行配置，方便灵活。
     */
    @Bean
    public UndertowServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addBuilderCustomizers((Undertow.Builder builder) -> {
            // 开启HTTP2  服务器推送
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                    .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true);
        });

        //这段可以增加http重定向，如果只需要http2的话下面的代码可以去掉
        factory.addBuilderCustomizers((Undertow.Builder builder) -> {
            builder.addHttpListener(8080, "0.0.0.0");
        });

        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            // 开启HTTP自动跳转至HTTPS
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> 8443);
        });
        return factory;
    }

}
