package Project.controller.builders.intersections;

import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.viewable.ViewablePlane;

import java.util.List;

public class GenCircleIntersectionBuilder implements GeometricIntersectionBuilder {
    private GeometricGenCircle a = null;
    private GeometricGenCircle b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricGenCircle g) {
            if (a == null) {
                a = g;
                return true;
            }
            else if (g != a) {
                b = g;
                return true;
            }
        }
        if (shape instanceof GeometricCircle c)
            return acceptArgument(new GeometricGenCircle(c));
        if (shape instanceof GeometricLine l)
            return acceptArgument(new GeometricGenCircle(l));
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        for (GeometricPoint i : getIntersections())
            BuilderUtils.addToPlane(i, viewablePlane);
    }


    @Override
    public List<GeometricPoint> getIntersections() {
        GeometricPoint i1 = new GeometricPoint();
        GeometricPoint i2 = new GeometricPoint();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricGenCircle gA = a;
            private final GeometricGenCircle gB = b;

            @Override
            public void update() {
                if (!gA.isLine()) {
                    if (!gB.isLine())
                        CircleIntersectionBuilder.setPoints(i1, i2, gA.circle, gB.circle);
                    else
                        LineAndCircleIntersectionBuilder.setPoints(i1, i2, gB.line, gA.circle);
                }
                else {
                    if (!gB.isLine())
                        LineAndCircleIntersectionBuilder.setPoints(i1, i2, gA.line, gB.circle);
                    else {
                        LineIntersectionBuilder.setPoint(i1, gA.line, gB.line);
                        LineIntersectionBuilder.setPoint(i2, gA.line, gB.line);
                    }
                }
            }
        };
        i1.setUpdater(updater);
        i2.setUpdater(updater);
        i1.update();
        i2.update();
        reset();
        return List.of(i1, i2);
    }
}
