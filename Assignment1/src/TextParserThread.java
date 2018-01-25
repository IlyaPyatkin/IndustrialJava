import java.io.*;

public class TextParserThread implements Runnable {
    private final String filename;
    private final TextParserManager manager;

    public TextParserThread(String filename, TextParserManager manager) {
        this.filename = filename;
        this.manager = manager;
    }

    @Override
    public void run() {
        try (InputStream in = new FileInputStream(filename);
             Reader reader = new InputStreamReader(in);
             Reader buffer = new BufferedReader(reader)) {
            StringBuilder word = new StringBuilder();
            int r;
            while ((r = buffer.read()) != -1 && manager.shouldRun) {
                char ch = (char) r;
                if (WordCounter.accept(ch)) word.append(ch);
                else if (WordCounter.ignore(ch)) {
                    if (word.length() > 0) {
                            manager.counter.addWord(word.toString().toLowerCase());
                        word = new StringBuilder();
                    }
                } else {
                    manager.shouldRun = false;
                    System.out.printf("Invalid character: %s%n", ch);
                    System.out.printf("Encountered in: %s%n", filename);
                }
            }
            if (r == -1 && word.length() > 0)
                manager.counter.addWord(word.toString().toLowerCase());
        } catch (FileNotFoundException x) {
            System.err.format("File not found: %s%n", filename);
        } catch (IOException x) {
            manager.shouldRun = false;
            System.err.format("IOException: %s%n", x);
        }
    }

}