package Project.model.labels;

import Project.model.GeometricShape;
import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class LabelBank {
    private final LinkedHashMap<Predicate<GeometricShape>, SmallBank> banksMap;

    // Keys determine which bank to use for a shape
    // Values should determine choice of label generator functions
    // They should be injective on capital letters

    // The point of using LinkedHashMap is to preserve order of insertion
    public LabelBank(LinkedHashMap<Predicate<GeometricShape>, Function<Character, String>> lambdaMap) {
        this.banksMap = new LinkedHashMap<>();
        lambdaMap.forEach((predicate, lambda) -> banksMap.put(predicate, new SmallBank(lambda)));
    }

    // To be invoked upon clearing plane
    public void reset() {
        for (SmallBank bank : banksMap.values())
            bank.reset();
    }

    private SmallBank getBankOrNull(GeometricShape shape) {
        for (Map.Entry<Predicate<GeometricShape>, SmallBank> entry : banksMap.entrySet()) {
            if (entry.getKey().test(shape))
                return entry.getValue();
        }
        return null;
    }

    private SmallBank getBank(GeometricShape shape) {
        SmallBank lastBank = null;
        for (Map.Entry<Predicate<GeometricShape>, SmallBank> entry : banksMap.entrySet()) {
            lastBank = entry.getValue();
            if (entry.getKey().test(shape))
                return lastBank;
        }
        System.err.println("No bank found for shape. Defaulting to last bank.");
        return lastBank;
    }

    public void assignLabel(GeometricShape shape) {
        shape.setName(getBank(shape).getLabel());
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
        SmallBank bank = getBankOrNull(shape);
        if (bank != null)
            bank.acceptReturned(parseLabel(shape.getName()));
    }

    public void takeLabel(GeometricShape shape) {
        SmallBank bank = getBankOrNull(shape);
        if (bank != null)
            bank.acceptTaken(parseLabel(shape.getName()));
    }
}
