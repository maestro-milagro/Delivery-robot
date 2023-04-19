import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        RCounter("RLRFR", 100, 1000);
        printMaxRep();

    }

    public static String generateRoute(String letters, int length) {

        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void RCounter(String letters, int length, int repNumber) {

        for (int i = 0; i < repNumber; i++) {
            Thread myThread = new Thread(() -> {
                int rCounter = 0;
                String rout = generateRoute(letters, length);
                for (char c : rout.toCharArray()) {
                    if (c == 'R') {
                        rCounter++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(rCounter)) {
                        sizeToFreq.put(rCounter, 1);
                    } else {
                        sizeToFreq.replace(rCounter, sizeToFreq.get(rCounter) + 1);

                    }
                }
            });
            myThread.start();
        }
    }

    public static void printMaxRep() {
        int maxKey = 0;
        int maxValue = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        System.out.printf("Самое частое количество повторений %s (встретилось %s раз)\n", maxKey, maxValue);
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() == maxKey) {
                continue;
            }
            System.out.printf("%s - (%s раз)\n", entry.getKey(), entry.getValue());

        }


    }
}
