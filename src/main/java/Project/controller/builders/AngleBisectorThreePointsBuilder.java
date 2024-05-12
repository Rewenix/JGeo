/**
 * A builder class for creating an angle bisector geometric shape using three points.
 */
package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class AngleBisectorThreePointsBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b, c;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                a.setOnClicked();
                return true;
            } else if (b == null) {
                b = p;
                b.setOnClicked();
                return true;
            } else if (c == null) {
                c = p;
                c.setOnClicked();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null && c != null;
    }

    @Override
    public void reset() {
        if (a != null)
            a.unclick();
        a = null;
        if (b != null)
            b.unclick();
        b = null;
        if (c != null)
            c.unclick();
        c = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Dwusieczna", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                double BC = Math.sqrt(Math.pow(pB.x - pC.x, 2) + Math.pow(pB.y - pC.y, 2));
                double AB = Math.sqrt(Math.pow(pA.x - pB.x, 2) + Math.pow(pA.y - pB.y, 2));

                double x = (BC * pA.x + AB * pC.x) / (AB + BC);
                double y = (BC * pA.y + AB * pC.y) / (AB + BC);

                LineThroughPointsBuilder.setLine(line, pB.x, pB.y, x, y);
            }
        };
        line.setUpdater(updater);
        line.update();
        line.setViewPane(viewPane);
        plane.addGeometricShape(line);
    }

}
