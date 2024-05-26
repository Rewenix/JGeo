package Project.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Stream;

public class LabelBank {
    protected static final Comparator<String> comparator = (a, b) -> {
        int aNum = !a.substring(1).isEmpty() ? Integer.parseInt(a.substring(1)) : 0;
        int bNum = !b.substring(1).isEmpty() ? Integer.parseInt(b.substring(1)) : 0;
        if (aNum != bNum) {
            return aNum - bNum;
        }
        return a.charAt(0) - b.charAt(0);
    };

    private static PointOrShapeBank pointBank = new PointOrShapeBank(true);
    private static PointOrShapeBank shapeBank = new PointOrShapeBank(false);

    // To be invoked upon clearing plane
    public static void reset() {
        pointBank = new PointOrShapeBank(true);
        shapeBank = new PointOrShapeBank(false);
    }

    public static String getPointLabel() {
        return pointBank.getLabel();
    }

    public static String getShapeLabel() {
        return shapeBank.getLabel();
    }

    private static PointOrShapeBank whoseResponsibility(String label) {
        try {
            if (label.substring(1).isEmpty() || label.substring(1).matches("[1-9][0-9]*")) {
                if (Character.isUpperCase(label.charAt(0)))
                    return pointBank;
                else if (Character.isLowerCase(label.charAt(0)))
                    return shapeBank;
            }
        }
        catch (Exception e) {}
        return null;
    }

    public static void returnPointLabel(String label) {
        if (whoseResponsibility(label) == pointBank)
            pointBank.acceptReturned(label);
    }

    public static void returnShapeLabel(String label) {
        if (whoseResponsibility(label) == shapeBank)
            shapeBank.acceptReturned(label);
    }

    public static void takePointLabel(String label) {
        if (whoseResponsibility(label) == pointBank)
            pointBank.acceptTaken(label);
    }

    public static void takeShapeLabel(String label) {
        if (whoseResponsibility(label) == shapeBank)
            shapeBank.acceptTaken(label);
    }

    private static class PointOrShapeBank {
        private final Iterator<String> iterator;
        private final TreeSet<String> returned = new TreeSet<>(comparator); // Returned and available
        private final TreeSet<String> taken = new TreeSet<>(comparator); // Taken out of order
        private String lastLabel = ".-1";   // Last returned label from the usual iterator

        PointOrShapeBank(boolean isPoint) {
            this.iterator = Stream.iterate(new String[] { isPoint ? "A" : "a", "0" }, p -> {
                char c = p[0].charAt(0);
                String num = p[1];
                if (c == (isPoint ? 'Z' : 'z')) {
                    c = isPoint ? 'A' : 'a';
                    num = String.valueOf(Integer.parseInt(num) + 1);
                }
                else {
                    c++;
                }
                return new String[] { String.valueOf(c), num };
            }).map(p -> p[0] + (Integer.parseInt(p[1]) > 0 ? p[1] : "")).iterator();
        }

        void acceptReturned(String label) {
            if (comparator.compare(label, lastLabel) <= 0)
                returned.add(label);
            taken.remove(label);
        }

        void acceptTaken(String label) {
            if (comparator.compare(label, lastLabel) > 0)
                taken.add(label);
            returned.remove(label);
        }

        String getLabel() {
            String label;
            if (!returned.isEmpty())
                label = returned.pollFirst();
            else {
                do {
                    label = iterator.next();
                } while (taken.contains(label));
                lastLabel = label;
            }
            return label;
        }
    }

    /*public static void main(String[] args) {
        for(int i = 0; i < 30; i++) {
            System.out.println(getShapeLabel());
        }
        System.out.println();
        returnShapeLabel("a1");
        System.out.println(getShapeLabel());
        takeShapeLabel("e1");
        // returnShapeLabel("e1");
        System.out.println(getShapeLabel());
    }*/
}
