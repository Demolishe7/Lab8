public class FilterProcessor {
    @DataProcessor
    public String filterLongWords(String word) {
        return word.length() > 5 ? word : null;
    }
}