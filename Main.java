public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();

        dataManager.registerDataProcessor(new FilterProcessor());
        dataManager.registerDataProcessor(new TransformProcessor());
        dataManager.registerDataProcessor(new AggregateProcessor());

        dataManager.loadData("source.txt");

        dataManager.processData();

        dataManager.saveData("destination.txt");
    }
}