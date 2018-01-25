public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TextParserManager manager = new TextParserManager(args);
        manager.runThreads();
        System.out.printf("Finished in %s ms%n", (System.currentTimeMillis() - startTime));
    }
}
