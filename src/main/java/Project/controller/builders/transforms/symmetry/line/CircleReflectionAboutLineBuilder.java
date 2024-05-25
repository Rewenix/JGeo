package Project.controller.builders.transforms.symmetry.line;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

public class CircleReflectionAboutLineBuilder implements GeometricShapeBuilder {
    private GeometricCircle circle = null;
    private GeometricLine line = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                return true;
            }
        }
        else if (shape instanceof GeometricGenCircle c && c.nowIAm() instanceof GeometricCircle cc) {
            if (circle == null) {
                circle = cc;
                return true;
            }
        }
        else if (shape instanceof GeometricLine l) {
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
        return false;
    }

    @Override
    public boolean isReady() {
        return circle != null && line != null;
    }

    @Override
    public void reset() {
        circle = null;
        line = null;
    }

    @Override
    public boolean awaitsPoint() {
        return false;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricCircle reflectedCircle = new GeometricCircle();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle pCircle = circle;
            private GeometricLine pLine = line;

            @Override
            public void update() {
                setCircle(reflectedCircle, pCircle, pLine);
            }
        };
        reflectedCircle.setUpdater(updater);
        BuilderUtils.addToPlane(reflectedCircle, viewablePlane);
    }

    public static void setCircle(GeometricCircle reflectedCircle, BasicCircle pCircle, BasicLine reflectionLine) {
        reflectedCircle.setCoordinates(getCircle(pCircle, reflectionLine));
    }

    public static void setCircle(GeometricCircle reflectedCircle, GeometricCircle pCircle, GeometricLine pLine) {
        setCircle(reflectedCircle, pCircle.circle, pLine.line);
    }

    public static BasicCircle getCircle(BasicCircle pCircle, BasicLine pLine) {
        BasicPoint center = PointReflectionAboutLineBuilder.getPoint(pCircle.center, pLine);
        return new BasicCircle(center, pCircle.radius);
    }
}
