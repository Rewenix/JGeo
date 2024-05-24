package Project.controller.builders.transforms.symmetry.point;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

public class LineReflectionAboutPointBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricPoint reflectionPoint = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            if (line == null) {
                line = ll;
                return true;
            }
        }
        else if (shape instanceof GeometricPoint p) {
            if (reflectionPoint == null) {
                reflectionPoint = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return line != null && reflectionPoint != null;
    }

    @Override
    public void reset() {
        line = null;
        reflectionPoint = null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine reflectedLine = new GeometricLine("Odbita prosta");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricPoint pReflectionPoint = reflectionPoint;

            @Override
            public void update() {
                setLine(reflectedLine, pLine, pReflectionPoint);
            }
        };
        reflectedLine.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedLine, viewablePlane);
    }

    public static void setLine(GeometricLine line, BasicLine pLine, BasicPoint pReflectionPoint) {
        line.setCoordinates(getLine(pLine, pReflectionPoint));
    }

    public static void setLine(GeometricLine line, GeometricLine pLine, GeometricPoint pReflectionPoint) {
        setLine(line, pLine.line, pReflectionPoint.point);
    }

    public static void setLine(GeometricLine line, GeometricPoint pReflectionPoint, GeometricLine pLine) {
        setLine(line, pLine.line, pReflectionPoint.point);
    }

    public static BasicLine getLine(BasicLine line, BasicPoint reflectionPoint) {
        return new BasicLine(line.A, line.B, -(2 * line.A * reflectionPoint.x + 2 * line.B * reflectionPoint.y + line.C));
    }
}
