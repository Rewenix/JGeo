package Project.view;

import Project.controller.Transformation;
import Project.model.BasicLine;
import Project.model.GeometricLine;
import Project.model.GeometricShape;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class ViewableLine extends ViewableShape{
    protected final Line drawableShape;
    protected final Line drawableHub;
    protected final GeometricLine geoLine;
    private static final double D = 5000;// jak daleko wyznaczane są końce na Plane2D

    public ViewableLine(String name, ViewablePlane viewablePlane, GeometricLine geoLine) {
        super(name, viewablePlane);
        this.geoLine = geoLine;
        drawableShape = new Line();
        drawableHub = new Line();
    }

    @Override
    public double getHub() {
        return 4;
    }

    @Override
    public Shape getDrawableShape() {
        return drawableShape;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public void updateDrawable() {
        double X1, Y1, X2, Y2;
        BasicLine line = geoLine.line;
        if (Math.abs(line.A) > Math.abs(line.B)) {
            Y1 = viewablePlane.transformation.toPlaneY(-D);
            Y2 = viewablePlane.transformation.toPlaneY(D);
            X1 = -(line.B / line.A) * Y1 - line.C / line.A;
            X2 = -(line.B / line.A) * Y2 - line.C / line.A;
        }
        else {
            X1 = viewablePlane.transformation.toPlaneX(-D);
            X2 = viewablePlane.transformation.toPlaneX(D);
            Y1 = -(line.A / line.B) * X1 - line.C / line.B;
            Y2 = -(line.A / line.B) * X2 - line.C / line.B;
        }

        drawableShape.setStartX(viewablePlane.transformation.toScreenX(X1));
        drawableShape.setStartY(viewablePlane.transformation.toScreenY(Y1));
        drawableShape.setEndX(viewablePlane.transformation.toScreenX(X2));
        drawableShape.setEndY(viewablePlane.transformation.toScreenY(Y2));
        drawableHub.setStartX(drawableShape.getStartX());
        drawableHub.setStartY(drawableShape.getStartY());
        drawableHub.setEndX(drawableShape.getEndX());
        drawableHub.setEndY(drawableShape.getEndY());
    }

    @Override
    public GeometricShape getGeometricShape() {
        return geoLine;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
