package Project.model;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class LabelBank {
    private static final Map<String, SmallBank> owners = new HashMap<>();

    private static SmallBank pointBank;
    private static SmallBank shapeBank;

    static { reset(); }

    // To be invoked upon clearing plane
    public static void reset() {
        pointBank = new SmallBank(c -> Character.toString(c));
        shapeBank = new SmallBank(c -> Character.toString(Character.toLowerCase(c)));
    }

    public static void assignLabel(GeometricShape shape) {
        if (shape instanceof GeometricPoint)
            shape.setName(pointBank.getLabel());
        else
            shape.setName(shapeBank.getLabel());
    }

    private static Pair<String, Integer> parseLabel(String label) {
        // Find last letter and this should be the start of the number
        int i = label.length() - 1;
        while (i >= 0 && Character.isDigit(label.charAt(i))) {
            i--;
        }
        i++;
        // Take into account the case where there is no number
        if (i == label.length())
            return new Pair<>(label, 0);
        return new Pair<>(label.substring(0, i), Integer.parseInt(label.substring(i)));
    }

    public static void returnLabel(String label) {
        Pair<String, Integer> pair = parseLabel(label);
        if(owners.containsKey(pair.getKey()))
            owners.get(pair.getKey()).acceptReturned(pair);
    }

    public static void takeLabel(String label) {
        Pair<String, Integer> pair = parseLabel(label);
        if(owners.containsKey(pair.getKey()))
            owners.get(pair.getKey()).acceptTaken(pair);
    }

    private static class SmallBank {
        private final Iterator<Pair<Character, Integer>> iterator;
        private final TreeSet<Pair<Character, Integer>> returned = new TreeSet<>(comparator); // Returned and available
        private final TreeSet<Pair<Character, Integer>> taken = new TreeSet<>(comparator); // Taken out of order
        private final Map<String, Character> delabelMap = new HashMap<>();
        private final Function<Character, String> lambda;
        private Pair<Character, Integer> lastPair = new Pair<>('.', -1);   // Last returned label from the usual iterator

        // Lambda functions should be reasonable
        // Namely, their images should be pairwise disjoint, they should be injective on capital letters
        SmallBank(Function<Character, String> labelChoice) {
            this.lambda = labelChoice;
            this.iterator = Stream.iterate(new Pair<>('A', 0), p -> {
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

            for (char c = 'A'; c <= 'Z'; c++) {
                String str = lambda.apply(c);
                delabelMap.put(str, c);
                owners.put(str, this);
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
    }

    /*public static void main(String[] args) {
        for(int i = 0; i < 30; i++) {
            System.out.println(pointBank.getLabel());
        }
        System.out.println();
        returnLabel("A1");
        System.out.println(pointBank.getLabel());
        takeLabel("E1");
        returnLabel("E1");
        System.out.println(pointBank.getLabel());
    }*/
}
