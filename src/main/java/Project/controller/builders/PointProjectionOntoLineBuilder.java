package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class PointProjectionOntoLineBuilder implements GeometricShapeBuilder {
    private GeometricPoint point = null;
    private GeometricLine line = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (point == null) {
                point = p;
                point.setOnClicked();
                System.out.println("Accepting point");
                return true;
            }
        }
        else if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return point != null && line != null;
    }

    @Override
    public void reset() {
        point = null;
        line = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint projectedPoint = new GeometricPoint("Punkt rzutowany", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setPoint(projectedPoint, pPoint, pLine);
            }
        };
        BuilderUtils.setUpdaterAndAdd(projectedPoint, updater, viewPane, plane);
    }

    public static void setPoint(GeometricPoint point, BasicPoint pPoint, BasicLine pLine) {
        point.setCoordinates(getPoint(pPoint, pLine));
    }

    public static void setPoint(GeometricPoint point, GeometricPoint pPoint, GeometricLine pLine) {
        setPoint(point, pPoint.point, pLine.line);
    }

    public static BasicPoint getPoint(BasicPoint point, BasicLine line) {
        double x1 = point.x;
        double y1 = point.y;
        double a = line.A;
        double b = line.B;
        double c = line.C;

        double x0 = (b * (b * x1 - a * y1) - a * c) / (a * a + b * b);
        double y0 = (a * (-b * x1 + a * y1) - b * c) / (a * a + b * b);

        return new BasicPoint(x0, y0);
    }
}
