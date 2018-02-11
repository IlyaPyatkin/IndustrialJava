import java.util.HashMap;
import java.util.concurrent.atomic.LongAdder;

public class WordCounter {
    public final ICounterRules rules;
    private final HashMap<String, LongAdder> wordsCounter;

    public WordCounter(ICounterRules rules) {
        wordsCounter = new HashMap<>();
        this.rules = rules;
    }

    public synchronized void addWord(String word) {
        if (rules.shouldCount(word)) {
            wordsCounter.computeIfAbsent(word, k -> new LongAdder()).increment();
            System.out.println(wordsCounter);
        }
    }
}
