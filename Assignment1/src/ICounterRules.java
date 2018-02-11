public interface ICounterRules {

    boolean accept(char c);

    boolean ignore(char c);

    default boolean shouldCount(String word) {
        return true;
    }
}
