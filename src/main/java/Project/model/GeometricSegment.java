package Project.model;

import Project.controller.Transformation;

public class GeometricSegment extends GeometricLine {
    private double x1, y1, x2, y2;

    public GeometricSegment(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableHub.setStrokeLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
    }

    public void setPoints(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        A = y1 - y2;
        B = x2 - x1;
        C = x1 * y2 - x2 * y1;
    }

    @Override
    public void updateDrawable() {
        double X1 = transformation.toScreenX(x1);
        double Y1 = transformation.toScreenY(y1);
        double X2 = transformation.toScreenX(x2);
        double Y2 = transformation.toScreenY(y2);
        drawableLine.setStartX(X1);
        drawableLine.setStartY(Y1);
        drawableLine.setEndX(X2);
        drawableLine.setEndY(Y2);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = Math.abs(A * planeX + B * planeY + C) / Math.sqrt(A * A + B * B); // odleglosc punktu od prostej
        double d1 = Math.sqrt((planeX - x1) * (planeX - x1) + (planeY - y1) * (planeY - y1)); // odleglosc punktu od
                                                                                              // poczatku odcinka
        double d2 = Math.sqrt((planeX - x2) * (planeX - x2) + (planeY - y2) * (planeY - y2)); // odleglosc punktu od
                                                                                              // konca odcinka
        double d3 = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); // dlugosc odcinka
        return d / transformation.scale <= plane.hitbox && d1 + d2 <= d3 + 2 * plane.hitbox; // punkt lezy na prostej i
                                                                                             // wewnatrz odcinka
    }
}
