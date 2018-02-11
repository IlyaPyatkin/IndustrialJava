import java.lang.reflect.Proxy;

public class TextParserManager {
    private final String[] files;
    public volatile boolean shouldRun;
    public WordCounter counter;

    public TextParserManager(String[] files) {
        this.files = files;
    }

    public void runThreads() {
        shouldRun = true;
        CounterRulesInvHandler invHandler = new CounterRulesInvHandler("RegularCounterRules");
        ICounterRules counterRules =
                (ICounterRules) Proxy.newProxyInstance(
                        ICounterRules.class.getClassLoader(),
                        new Class[]{ICounterRules.class},
                        invHandler
                );
        counter = new WordCounter(counterRules);
        Thread[] threads = new Thread[files.length];
        for (int i = 0; i < files.length; i++) {
            threads[i] = new Thread(new TextParserThread(files[i], this));
            threads[i].start();
        }

        try {
            Thread.sleep(200);
            invHandler.counterRulesClassName = "ModifiedCounterRules";
            System.out.println("=========== Switched counter rules! ===========");
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException x) {
            System.err.format("InterruptedException: %s%n", x);
        }
    }
}
