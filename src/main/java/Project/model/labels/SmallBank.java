package Project.model.labels;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

class SmallBank {
    private Iterator<Pair<Character, Integer>> iterator;
    private final TreeSet<Pair<Character, Integer>> returned = new TreeSet<>(comparator); // Returned and available
    private final TreeSet<Pair<Character, Integer>> taken = new TreeSet<>(comparator); // Taken out of order
    private final Map<String, Character> delabelMap = new HashMap<>();
    private final Function<Character, String> lambda;
    private Pair<Character, Integer> lastPair;   // Last returned label from the usual iterator

    private static Iterator<Pair<Character, Integer>> getStandardIterator() {
        return Stream.iterate(new Pair<>('A', 0), p -> {
            char c = p.getKey();
            int num = p.getValue();
            if (c == 'Z') {
                c = 'A';
                num++;
            }
            else {
                c++;
            }
            return new Pair<>(c, num);
        }).iterator();
    }

    private void initialize() {
        iterator = getStandardIterator();
        lastPair = new Pair<>('.', -1);
    }

    SmallBank(Function<Character, String> lambda) {
        initialize();

        this.lambda = lambda;
        for (char c = 'A'; c <= 'Z'; c++) {
            String str = lambda.apply(c);
            if (delabelMap.containsKey(str))
                System.err.println("Lambda is not injective.");
            delabelMap.put(str, c);
        }
    }

    void acceptReturned(Pair<String, Integer> label) {
        if(delabelMap.containsKey(label.getKey())) {
            Pair<Character, Integer> pair = new Pair<>(delabelMap.get(label.getKey()), label.getValue());
            if (comparator.compare(pair, lastPair) <= 0)
                returned.add(pair);
            taken.remove(pair);
        }
    }

    void acceptTaken(Pair<String, Integer> label) {
        if(delabelMap.containsKey(label.getKey())) {
            Pair<Character, Integer> pair = new Pair<>(delabelMap.get(label.getKey()), label.getValue());
            if (comparator.compare(pair, lastPair) > 0)
                taken.add(pair);
            returned.remove(pair);
        }
    }

    String getLabel() {
        Pair<Character, Integer> pair;
        if (!returned.isEmpty())
            pair = returned.pollFirst();
        else {
            do {
                pair = iterator.next();
            } while (taken.contains(pair));
            lastPair = pair;
        }
        if(pair.getValue() > 0)
            return lambda.apply(pair.getKey()) + pair.getValue();
        return lambda.apply(pair.getKey());
    }

    private static final Comparator<Pair<Character, Integer>> comparator = (a, b) -> {
        int aNum = a.getValue();
        int bNum = b.getValue();
        if (aNum != bNum) {
            return aNum - bNum;
        }
        return a.getKey() - b.getKey();
    };

    void reset() {
        initialize();
        returned.clear();
        taken.clear();
    }
}
