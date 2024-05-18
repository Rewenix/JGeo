package Project.model;

import Project.controller.Transformation;
import Project.view.ViewableSegment;

public class GeometricSegment extends GeometricLine {
    @Override
    public int getPriority() {
        return 1;
    }

    public final BasicSegment segment = new BasicSegment();

    public GeometricSegment(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        viewableShape = new ViewableSegment(name, transformation, segment);
    }

    public void setCoordinates(BasicSegment segment) {
        this.segment.setCoordinates(segment);
        this.line.setCoordinates(segment);
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
