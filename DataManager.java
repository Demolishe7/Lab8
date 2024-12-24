import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataManager {
    private final List<Object> processors = new ArrayList<>();
    private List<String> data;

    public void registerDataProcessor(Object processor) {
        processors.add(processor);
    }

    public void loadData(String source) {
        data = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
    }

    public void processData() {
        ExecutorService executorService = Executors.newFixedThreadPool(processors.size());
        List<Future<List<String>>> futures = new ArrayList<>();

        for (Object processor : processors) {
            for (Method method : processor.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(DataProcessor.class)) {
                    futures.add(executorService.submit(() -> {
                        List<String> processedData = data.stream()
                                .map(item -> {
                                    try {
                                        return (String) method.invoke(processor, item);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                })
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                        return processedData;
                    }));
                }
            }
        }

        data = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Collections.<String>emptyList();
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        executorService.shutdown();
    }

    public void saveData(String destination) {
        System.out.println("Saving data to " + destination + ": " + data);
    }
}