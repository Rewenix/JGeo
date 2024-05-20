package Project.model;

import Project.controller.Transformation;
import javafx.scene.layout.Pane;

// Nie trzeba się przejmować, kto się wyświetla, bo to w modelu będzie ustalane na jakieś Infinity
// Czyli chyba w ogóle ViewableGenCircle nie jest potrzebne

// Zróbmy przestawianie ViewableShape, żeby działało wyświetlanie.
public class GeometricGenCircle extends GeometricShape {
    public final GeometricLine line;
    public final GeometricCircle circle;
    // Boolean isLine;

    public GeometricGenCircle(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        this.line = new GeometricLine(name, plane, transformation);
        this.circle = new GeometricCircle(name, plane, transformation);
        // isLine = false;
        viewableShape = circle.viewableShape;
    }

    public GeometricGenCircle(String name, Plane2D plane, Transformation transformation, GeometricLine line, GeometricCircle geometricCircle) {
        super(name, plane, transformation);
        this.line = line;
        this.circle = geometricCircle;
        // isLine = false;
        viewableShape = geometricCircle.viewableShape;
    }

    public GeometricShape nowIAm() {
        return circle.isDefined() ? circle : line;
    }

    public void setViewable() {
        viewableShape = nowIAm().viewableShape;
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        return nowIAm().hasPoint(planeX, planeY);
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return nowIAm().projection(point);
    }

    @Override
    public int getPriority() {
        return 2;
    }


    // Krytyczne metody, żeby i prosta i okrąg się mogły wyświetlać
    // Pozostałe będą mam nadzieję działać dzięki Infinity
    @Override
    public void setViewPane(Pane viewPane) {
        line.setViewPane(viewPane);
        circle.setViewPane(viewPane);
    }

    @Override
    public void updateDrawable() {
        line.updateDrawable();
        circle.updateDrawable();
    }

    @Override
    public void dropViewable() {
        line.dropViewable();
        circle.dropViewable();
    }
}
