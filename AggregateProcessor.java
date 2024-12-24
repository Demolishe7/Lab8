public class AggregateProcessor {
    @DataProcessor
    public String prefixWithNumber(String word) {
        return (word.length() + 1) + "-" + word;
    }
}