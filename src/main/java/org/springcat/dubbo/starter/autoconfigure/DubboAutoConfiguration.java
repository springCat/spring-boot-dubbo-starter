package org.springcat.dubbo.starter.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springcat.dubbo.starter.commands.DubboServiceLatchCommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order
public class DubboAutoConfiguration {

    protected Logger logger = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    @Value("${shutdown.latch.domain.name:org.springcat.lifecycles}")
    private String shutdownLatchDomainName;

    @Bean
    @ConditionalOnClass(name = "com.alibaba.dubbo.rpc.Exporter")
    public DubboServiceLatchCommandLineRunner configureDubboServiceLatchCommandLineRunner() {
        logger.info("DubboAutoConfiguration enabled by adding DubboServiceLatchCommandLineRunner.");
        DubboServiceLatchCommandLineRunner runner = new DubboServiceLatchCommandLineRunner();
        runner.setDomain(shutdownLatchDomainName);
        return runner;
    }
}

