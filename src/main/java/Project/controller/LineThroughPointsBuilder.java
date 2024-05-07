package Project.controller;

import Project.model.*;
import javafx.scene.layout.Pane;

public class LineThroughPointsBuilder implements GeometricShapeBuilder{
    private Point a = null;
    private Point b = null;

    @Override
    public void acceptArgument(GeometricShape shape) {
        if(shape instanceof Point p){
            if(a == null){
                a = p;
                System.out.println("Accepting point");
            }else if(p != a){
                b = p;
                System.out.println("Accepting point");
            }
        }
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Prosta", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private Point pA = a;
            private Point pB = b;
            @Override
            public void update() {
                double x1 = pA.x; double y1 = pA.y;
                double x2 = pB.x; double y2 = pB.y;
                line.A = y2 - y1;
                line.B = x1 - x2;
                line.C = x2 * y1 - y2 * x1;
            }
        };
        line.setUpdater(updater);
        line.update();
        viewPane.getChildren().add(line.getDrawableShape());
        plane.addGeometricShape(line);
        //System.out.println("Stworzono prostą A" + line.A +" B=" + line.B + " C=" + line.C);
    }
}
