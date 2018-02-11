public class RegularCounterRules implements ICounterRules {
    public boolean accept(char c) {
        return 'А' <= c && c <= 'я' ||
                c == 'ё' || c == 'Ё';
    }

    public boolean ignore(char c) {
        return '0' <= c && c <= '9' ||
                " .,?!-;:\"'«»()\n\r".contains(String.valueOf(c));
    }
}
