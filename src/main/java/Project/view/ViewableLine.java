package Project.view;

import Project.controller.Transformation;
import Project.model.BasicLine;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class ViewableLine extends ViewableShape{
    protected final Line drawableShape;
    protected final Line drawableHub;
    protected final BasicLine line;
    private static final double D = 5000;// jak daleko wyznaczane są końce na Plane2D

    static {
        hub = 4;
    }

    public ViewableLine(String name, Transformation transformation, BasicLine line) {
        super(name, transformation);
        this.line = line;
        drawableShape = new Line();
        drawableHub = new Line();
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
        if (Math.abs(line.A) > Math.abs(line.B)) {
            Y1 = transformation.toPlaneY(-D);
            Y2 = transformation.toPlaneY(D);
            X1 = -(line.B / line.A) * Y1 - line.C / line.A;
            X2 = -(line.B / line.A) * Y2 - line.C / line.A;
        }
        else {
            X1 = transformation.toPlaneX(-D);
            X2 = transformation.toPlaneX(D);
            Y1 = -(line.A / line.B) * X1 - line.C / line.B;
            Y2 = -(line.A / line.B) * X2 - line.C / line.B;
        }

        drawableShape.setStartX(transformation.toScreenX(X1));
        drawableShape.setStartY(transformation.toScreenY(Y1));
        drawableShape.setEndX(transformation.toScreenX(X2));
        drawableShape.setEndY(transformation.toScreenY(Y2));
        drawableHub.setStartX(drawableShape.getStartX());
        drawableHub.setStartY(drawableShape.getStartY());
        drawableHub.setEndX(drawableShape.getEndX());
        drawableHub.setEndY(drawableShape.getEndY());
    }
}
