package org.springcat.dubbo.starter.lifecycle;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicReference;

public class ShutdownHook implements ShutdownHookMBean {

    protected String domain;

    protected AtomicReference<Runnable> shutdownHookHolder = new AtomicReference<Runnable>();

    public ShutdownHook() {
        this("org.springcat.lifecycles");
    }

    public ShutdownHook(String domainName) {
        this.domain = domainName;
    }

    public void attach(Runnable shutdownHook) throws Exception {
        shutdownHookHolder.set(shutdownHook);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(this, new ObjectName(domain, "name", "ShutdownHook"));
    }

    public void shutdown() {
        Runnable hook = shutdownHookHolder.get();
        if (hook != null) hook.run();
    }

}
