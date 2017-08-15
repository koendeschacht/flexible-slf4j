package be.bagofwords.logging;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class FlexibleSl4jLogFactory implements ILoggerFactory {

    public final static FlexibleSl4jLogFactory INSTANCE = new FlexibleSl4jLogFactory();

    private final Map<String, Logger> loggerMap = new HashMap<>();
    private final WeakHashMap<Thread, LogImpl> logImplementationsMap = new WeakHashMap<>();
    private LogImpl defaultLogImplementation = new StdOutLogImpl();

    @Override
    public synchronized Logger getLogger(String name) {
        if (!this.loggerMap.containsKey(name)) {
            this.loggerMap.put(name, new FlexibleSlf4jLogger(name));
        }
        return this.loggerMap.get(name);
    }

    public LogImpl getLogImpl() {
        LogImpl impl = logImplementationsMap.get(Thread.currentThread());
        if (impl == null) {
            return defaultLogImplementation;
        } else {
            return impl;
        }
    }

    public void setLoggerImplementation(Thread thread, LogImpl log) {
        logImplementationsMap.put(thread, log);
    }
}
