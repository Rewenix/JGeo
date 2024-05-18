package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class PointReflectionAboutLineBuilder implements GeometricShapeBuilder {
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
        GeometricPoint reflectedPoint = new GeometricPoint("Punkt odbity", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pPoint = point;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setPoint(reflectedPoint, pPoint, pLine);
            }
        };
        BuilderUtils.setUpdaterAndAdd(reflectedPoint, updater, viewPane, plane);
    }

    public static void setPoint(GeometricPoint point, BasicPoint pPoint, BasicLine pLine) {
        point.setCoordinates(getPoint(pPoint, pLine));
    }

    public static void setPoint(GeometricPoint point, GeometricPoint pPoint, GeometricLine pLine) {
        setPoint(point, pPoint.point, pLine.line);
    }

    public static void setPoint(GeometricPoint point, GeometricLine pLine, GeometricPoint pPoint) {
        setPoint(point, pPoint.point, pLine.line);
    }

    public static BasicPoint getPoint(BasicPoint pPoint, BasicLine pLine) { // TODO: check if it works (probably not)
        BasicPoint projection = PointProjectionOntoLineBuilder.getPoint(pPoint, pLine);
        return new BasicPoint(2 * projection.x - pPoint.x, 2 * projection.y - pPoint.y);
    }
}
