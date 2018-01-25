import java.util.HashMap;
import java.util.concurrent.atomic.LongAdder;

public class WordCounter {
    private final HashMap<String, LongAdder> wordsCounter;

    public WordCounter() {
        wordsCounter = new HashMap<>();
    }

    public static boolean accept(char c) {
        return 'А' <= c && c <= 'я' ||
                c == 'ё' || c == 'Ё';
    }

    public static boolean ignore(char c) {
        return '0' <= c && c <= '9' ||
                " .,?!-;:\"'«»()\n\r".contains(String.valueOf(c));
    }

    public synchronized void addWord(String word) {
        wordsCounter.computeIfAbsent(word, k -> new LongAdder()).increment();
        System.out.println(wordsCounter);
    }
}
