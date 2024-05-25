package Project.model;

import Project.controller.builders.BuilderUtils;
import Project.controller.builders.points.PointProjectionOntoLineBuilder;

public class GeometricSegment extends GeometricLine {

    public final BasicSegment segment = new BasicSegment();

    public void setCoordinates(BasicSegment segment) {
        this.segment.setCoordinates(segment);
        this.line.setCoordinates(segment);
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        BasicPoint p = PointProjectionOntoLineBuilder.getPoint(point, line); // rzut punktu na prosta
        double d1 = segment.p1.distance(p);
        double d2 = segment.p2.distance(p);
        double d3 = BasicPoint.distance(segment.p1, segment.p2);
        if (d1 + d2 <= d3 + BuilderUtils.EPSILON) return p; // rzut punktu na prosta lezy na odcinku
        if (d1 < d2) p.setCoordinates(segment.p1); // rzut punktu na prosta lezy przed odcinkiem
        else p.setCoordinates(segment.p2); // rzut punktu na prosta lezy za odcinkiem
        return p;
    }

    @Override
    public double distance(BasicPoint point) {
        return point.distance(projection(point));
    }
}
