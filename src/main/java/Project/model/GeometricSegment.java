package Project.model;

import Project.controller.Transformation;

public class GeometricSegment extends GeometricLine {
    @Override
    public int getPriority() {
        return 1;
    }

    public BasicSegment segment = new BasicSegment();

    public GeometricSegment(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableHub.setStrokeLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
    }

    public void setCoordinates(BasicSegment segment) {
        this.segment = segment;
        this.line = (BasicLine) segment;
    }

    @Override
    public void updateDrawable() {
        double X1 = transformation.toScreenX(segment.p1.x);
        double Y1 = transformation.toScreenY(segment.p1.y);
        double X2 = transformation.toScreenX(segment.p2.x);
        double Y2 = transformation.toScreenY(segment.p2.y);
        drawableLine.setStartX(X1);
        drawableLine.setStartY(Y1);
        drawableLine.setEndX(X2);
        drawableLine.setEndY(Y2);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        BasicPoint p = new BasicPoint(planeX, planeY);
        double d = segment.distance(p); // odleglosc punktu od prostej
        double d1 = segment.p1.distance(p); // odleglosc punktu od poczatku odcinka
        double d2 = segment.p2.distance(p); // odleglosc punktu od konca odcinka
        double d3 = BasicPoint.distance(segment.p1, segment.p2); // dlugosc odcinka
        return d / transformation.scale <= plane.hitbox && d1 + d2 <= d3 + 2 * plane.hitbox; // punkt lezy na prostej i wewnatrz odcinka
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        double x0 = (line.B * (line.B * point.x - line.A * point.y) - line.A * line.C) / (line.A * line.A + line.B * line.B);
        double y0 = (line.A * (-line.B * point.x + line.A * point.y) - line.B * line.C) / (line.A * line.A + line.B * line.B);
        BasicPoint p = new BasicPoint(x0, y0);
        double d1 = segment.p1.distance(p);
        double d2 = segment.p2.distance(p);
        double d3 = BasicPoint.distance(segment.p1, segment.p2);
        if (d1 + d2 <= d3) return p; // rzut punktu na prosta lezy na odcinku
        if (d1 < d2) p.setCoordinates(segment.p1); // rzut punktu na prosta lezy przed odcinkiem
        else p.setCoordinates(segment.p2); // rzut punktu na prosta lezy za odcinkiem
        return p;
    }
}
