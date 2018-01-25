public class TextParserManager {
    private final String[] files;
    public volatile boolean shouldRun;
    public WordCounter counter;

    public TextParserManager(String[] files) {
        this.files = files;
    }

    public void runThreads() {
        shouldRun = true;
        counter = new WordCounter();

        Thread[] threads = new Thread[files.length];
        for (int i = 0; i < files.length; i++) {
            threads[i] = new Thread(new TextParserThread(files[i], this));
            threads[i].start();
        }

        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException x) {
            System.err.format("InterruptedException: %s%n", x);
        }
    }


}
