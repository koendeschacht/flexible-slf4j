package be.bagofwords.logging;

import be.bagofwords.AnotherClass;
import org.junit.Assert;
import org.junit.Test;

public class TestFlexibleLogging {

    private String logStatement;
    private String logger;

    @Test
    public void testLogging() {
        Log.i("Hoi");
        logStatement = null;
        FlexibleSl4jLogFactory.INSTANCE.setLoggerImplementation(Thread.currentThread(), new LogImpl() {
            @Override
            public void log(int level, String logger, String message, Throwable throwable) {
                logStatement = message;
                TestFlexibleLogging.this.logger = logger;
            }
        });
        AnotherClass.printSomething();
        Assert.assertEquals("Bye!", logStatement);
        Assert.assertEquals(AnotherClass.class.getName(), logger);
    }

}
