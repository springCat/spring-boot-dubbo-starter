package org.springcat.dubbo.starter.lifecycle;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShutdownLatch implements ShutdownLatchMBean {
    protected AtomicBoolean running = new AtomicBoolean(false);

    public long checkIntervalInSeconds = 10;

    private String domain = "org.springcat.lifecycles";

    public ShutdownLatch() {
    }

    public ShutdownLatch(String domain) {
        this.domain = domain;
    }

    public void await() throws Exception {
        if (running.compareAndSet(false, true)) {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.registerMBean(this, new ObjectName(domain, "name", "ShutdownLatch"));
            while (running.get()) {
                TimeUnit.SECONDS.sleep(checkIntervalInSeconds);
            }
        }
    }

    public String shutdown() {
        if (running.compareAndSet(true, false)) {
            return "shutdown signal sent, shutting down..";
        } else {
            return "shutdown signal had been sent, no need again and again and again...";
        }
    }

}
