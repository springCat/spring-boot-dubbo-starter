package org.springcat.dubbo.starter.commands;

import org.springcat.dubbo.starter.lifecycle.ShutdownLatch;
import org.springframework.boot.CommandLineRunner;

public class DubboServiceLatchCommandLineRunner implements CommandLineRunner {

    private String domain = "org.springcat.lifecycles";

    public void run(String... args) throws Exception {
        ShutdownLatch latch = new ShutdownLatch(getDomain());
        latch.await();
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
