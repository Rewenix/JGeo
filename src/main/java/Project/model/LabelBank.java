package Project.model;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LabelBank {
    private final Function<GeometricShape, Integer> bankChoice;
    private final List<SmallBank> banks;

    // Lambdas should be reasonable
    // Namely, they should be injective on capital letters
    public LabelBank(Function<GeometricShape, Integer> bankChoice, List<Function<Character, String>> lambdas) {
        this.bankChoice = bankChoice;
        this.banks = lambdas.stream()
                .map(SmallBank::new)
                .collect(Collectors.toList());
    }

    // To be invoked upon clearing plane
    public void reset() {
        for(SmallBank bank : banks)
            bank.reset();
    }

    public void assignLabel(GeometricShape shape) {
        shape.setName(banks.get(bankChoice.apply(shape)).getLabel());
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

    public void returnLabel(GeometricShape shape) {
        banks.get(bankChoice.apply(shape)).acceptReturned(parseLabel(shape.getName()));
    }

    public void takeLabel(GeometricShape shape) {
        banks.get(bankChoice.apply(shape)).acceptTaken(parseLabel(shape.getName()));
    }

    private static class SmallBank {
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
}
