package Core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SimStats
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static Map<Integer,ArrayList<Double>> allStats = new TreeMap<>();
    public static Map<Double,ArrayList<Integer>> checkouts = new TreeMap<>();
    public static double lastEventTime = 0.0d;

    public static void show() throws IOException {
        AtomicReference<Double> avgTimeCheckoutFirst = new AtomicReference<>(0.0d);
        AtomicReference<Double> avgTimeServiceFirst = new AtomicReference<>(0.0d);
        AtomicInteger avgTimeCheckoutFirstSum = new AtomicInteger();
        AtomicInteger avgTimeServiceFirstSum = new AtomicInteger();
        AtomicReference<Double> avgTimeCheckoutSec = new AtomicReference<>(0.0d);
        AtomicReference<Double> avgTimeServiceSec = new AtomicReference<>(0.0d);
        AtomicInteger avgTimeCheckoutSecSum = new AtomicInteger();
        AtomicInteger avgTimeServiceSecSum = new AtomicInteger();
        AtomicReference<Double> avgTimeCheckout = new AtomicReference<>(0.0d);
        AtomicReference<Double> avgTimeService = new AtomicReference<>(0.0d);
        AtomicInteger avgTimeCheckoutSum = new AtomicInteger();
        AtomicInteger avgTimeServiceSum = new AtomicInteger();
        AtomicReference<Double> avgTimeInShop = new AtomicReference<>(0.0d);
        AtomicInteger avgTimeInShopSum = new AtomicInteger();

        AtomicInteger impatienceSum = new AtomicInteger();
        Collection<ArrayList<Double>> values = allStats.values();

        values.forEach( value -> {
            if(value.get(1) > 0.0d) {
                avgTimeCheckoutFirst.updateAndGet(v -> v + (value.get(1) - value.get(0)));
                avgTimeCheckout.updateAndGet(v -> v + (value.get(1) - value.get(0)));
                avgTimeCheckoutFirstSum.addAndGet(1);
            }

            if(value.get(2) > 0.0d) {
                avgTimeServiceFirst.updateAndGet(v -> v + (value.get(2) - value.get(1)));
                avgTimeService.updateAndGet(v -> v + (value.get(2) - value.get(1)));
                avgTimeServiceFirstSum.addAndGet(1);
            }

            if(value.get(3) > 0.0d) {
                avgTimeCheckoutSec.updateAndGet(v -> v + (value.get(3) - value.get(2)));
                avgTimeCheckout.updateAndGet(v -> v + (value.get(3) - value.get(2)));
                avgTimeCheckoutSecSum.addAndGet(1);
            }

            if(value.get(4) > 0.0d) {
                avgTimeServiceSec.updateAndGet(v -> v + (value.get(4) - value.get(3)));
                avgTimeService.updateAndGet(v -> v + (value.get(4) - value.get(3)));
                avgTimeServiceSecSum.addAndGet(1);
            }

            if(value.get(2) > 0.0d || value.get(4) > 0.0d) avgTimeServiceSum.addAndGet(1);
            avgTimeCheckoutSum.addAndGet(1);

            if(value.get(5) > 0.0d) {
                impatienceSum.addAndGet(1);
                avgTimeInShopSum.addAndGet(1);
                avgTimeInShop.updateAndGet(v -> v + (value.get(5) - value.get(0)));
            } else if (value.get(4) > 0.0d){
                avgTimeInShopSum.addAndGet(1);
                avgTimeInShop.updateAndGet(v -> v + (value.get(4) - value.get(0)));
            }


        });

        System.out.println("\nSIMULATION STATISTICS:");
        System.out.println("Sredni czas stania klientow w kolejce do stanowiska nr 1 - " + (avgTimeCheckoutFirst.get() / avgTimeCheckoutFirstSum.get()));
        System.out.println("Sredni czas stania klientow w kolejce do stanowiska nr 2 - " + (avgTimeCheckoutSec.get() / avgTimeCheckoutSecSum.get()));
        System.out.println("Sredni czas stania klientow w kolejkach - " + (avgTimeCheckout.get() / avgTimeCheckoutSum.get()));
        System.out.println("Sredni czas obslugi na stanowisku nr 1 - " + (avgTimeServiceFirst.get() / avgTimeServiceFirstSum.get()));
        System.out.println("Sredni czas obslugi na stanowisku nr 2 - " + (avgTimeServiceSec.get() / avgTimeServiceSecSum.get()));
        System.out.println("Sredni czas obslugi klienta w sklepie - " + (avgTimeService.get() / avgTimeServiceSum.get()));
        System.out.println("Sredni czas przebywania klienta w sklepie - " + (avgTimeInShop.get() / avgTimeInShopSum.get()));

        System.out.println("Ilosc klientow, ktorzy weszli do sklepu - " + allStats.size());
        System.out.println("Ilosc klientow obsluzonych na stanowisku 1 - " + avgTimeServiceFirstSum);
        System.out.println("Ilosc klientow obsluzonych na stanowisku 2 - " + avgTimeServiceSecSum);
        System.out.println("Zniecierpliwieni klienci - " + impatienceSum);

        System.out.println("Ilosc klientow, ktorzy nadal stoja w kolejce 1 - " + checkouts.get(lastEventTime).get(0));
        System.out.println("Ilosc klientow, ktorzy nadal stoja w kolejce 2 - " + checkouts.get(lastEventTime).get(1));

        toCsv(convert(checkouts), "time,firstCheckout,secCheckout", "stats.csv");
    }

    private static ArrayList<ArrayList<String>> convert(Map<Double,ArrayList<Integer>> m) {
        ArrayList<ArrayList<String>> c = new ArrayList<>();

        Set<Double> keys = m.keySet();
        keys.forEach( key -> {
            ArrayList<String> s = new ArrayList<>();
            s.add(String.valueOf(key));
            ArrayList<Integer> x = m.get(key);
            s.add(String.valueOf(x.get(0)));
            s.add(String.valueOf(x.get(1)));
            c.add(s);
        });
        return c;
    }

    private static void toCsv(ArrayList<ArrayList<String>> t, String title, String filename) throws IOException {
        ArrayList<ArrayList<String>> test = t;
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append(title);
        csvWriter.append("\n");
        for (ArrayList<String> rowData : test) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}