package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.controller.builders.lines.LineThroughPointsBuilder;
import Project.model.*;
import Project.view.ViewablePlane;

public class LineReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricLine reflectionLine = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                return true;
            }
            else if (l != line) {
                reflectionLine = l;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle l && l.nowIAm() instanceof GeometricLine ll) {
            if (line == null) {
                line = ll;
                return true;
            }
            else if (ll != line) {
                reflectionLine = ll;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return line != null && reflectionLine != null;
    }

    @Override
    public void reset() {
        line = null;
        reflectionLine = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine reflectedLine = new GeometricLine("Odbita prosta");
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine pLine = line;
            private GeometricLine pReflectionLine = reflectionLine;

            @Override
            public void update() {
                setLine(reflectedLine, pLine, pReflectionLine);
            }
        };
        reflectedLine.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedLine, viewablePlane);
    }

    public static void setLine(GeometricLine reflectedLine, BasicLine line, BasicLine reflectionLine) {
        reflectedLine.setCoordinates(getLine(line, reflectionLine));
    }

    public static void setLine(GeometricLine reflectedLine, GeometricLine line, GeometricLine reflectionLine) {
        setLine(reflectedLine, line.line, reflectionLine.line);
    }

    public static BasicLine getLine(BasicLine line, BasicLine reflectionLine) { // nie znalazlem jakiegos normalnego wzoru wiec taka łopatologia
        BasicPoint p1 = new BasicPoint(1, 1);
        BasicPoint p2 = new BasicPoint(-1, -1);
        if (Math.abs(line.B) < BuilderUtils.EPSILON) {
            p1.x = -(line.C + line.B * p1.y) / line.A;
            p2.x = -(line.C + line.B * p2.y) / line.A;
        }
        else {
            p1.y = -(line.C + line.A * p1.x) / line.B;
            p2.y = -(line.C + line.A * p2.x) / line.B;
        }
        BasicPoint p1Reflection = PointReflectionAboutLineBuilder.getPoint(p1, reflectionLine);
        BasicPoint p2Reflection = PointReflectionAboutLineBuilder.getPoint(p2, reflectionLine);
        return LineThroughPointsBuilder.getLine(p1Reflection, p2Reflection);
    }
}
