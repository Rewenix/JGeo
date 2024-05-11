package Project.model;

import Project.controller.Transformation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class GeometricLine extends GeometricShape {
    private static final double D = 5000;// jak daleko rysowane sa konce
    public double A = 1, B = 1, C = 0; // wspolrzedne w postaci ogolnej (Ax + By + C = 0) zeby proste mogly byc tez
                                       // pionowe
    protected Line drawableLine;
    protected Line drawableHub;

    public GeometricLine(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableLine = new Line();// zeby sie wyróżniała na tle punktu
        drawableHub = new Line();
        drawableHub.setStroke(Color.TRANSPARENT);
        drawableHub.setStrokeWidth(plane.hitbox / 2);
    }

    @Override
    public void updateDrawable() {
        // TODO rysowanie musi uwzgledniac transformacje aby nie dalo sie zobaczyc
        // koncow prostej po zoomie/przesunieciu
        // bedziemy chcieli dzielic przez wieksze z A/B bo to chyba daje lepsza
        // dokladnosc(unikamy dzielenia przez cos bliskie zero)
        double X1, Y1, X2, Y2;
        if (Math.abs(A) > Math.abs(B)) {
            Y1 = transformation.toPlaneY(-D);
            Y2 = transformation.toPlaneY(D);
            X1 = -(B / A) * Y1 - C / A;
            X2 = -(B / A) * Y2 - C / A;
        } else {
            X1 = transformation.toPlaneX(-D);
            X2 = transformation.toPlaneX(D);
            Y1 = -(A / B) * X1 - C / B;
            Y2 = -(A / B) * X2 - C / B;
        }
        // System.out.println("Obliczone wspolrzedne (X1, Y1)=" + X1 +" " + Y1 + " (X2,
        // Y2)" + X2 + " " + Y2);
        drawableLine.setStartX(transformation.toScreenX(X1));
        drawableLine.setStartY(transformation.toScreenY(Y1));
        drawableLine.setEndX(transformation.toScreenX(X2));
        drawableLine.setEndY(transformation.toScreenY(Y2));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableLine;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public void setOnClicked() {
        drawableHub.setStartX(drawableLine.getStartX());
        drawableHub.setStartY(drawableLine.getStartY());
        drawableHub.setEndX(drawableLine.getEndX());
        drawableHub.setEndY(drawableLine.getEndY());
        drawableHub.setStroke(Color.CYAN);
    }

    @Override
    public void unclick() {
        drawableHub.setStroke(Color.TRANSPARENT);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = Math.abs(A * planeX + B * planeY + C) / Math.sqrt(A * A + B * B);
        return d / transformation.scale <= plane.hitbox;
    }

    public void setEquation(double aX, double aY, double bX, double bY) { // line through two points
        A = aY - bY;
        B = bX - aX;
        C = aX * bY - bX * aY;
    }

    public void setEquation(GeometricPoint a, GeometricPoint b) { // line through two points
        setEquation(a.x, a.y, b.x, b.y);
    }

    public void setEquation(double a, double b, double c) { // Ax + By + C = 0
        A = a;
        B = b;
        C = c;
    }

    public void setEquation(double a, double b) { // y = ax + b -> Ax + By + C = 0
        A = -a;
        B = 1;
        C = -b;
    }
}
