package Project.model;

import Project.controller.Transformation;
import Project.controller.builders.PointProjectionOntoLineBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class GeometricLine extends GeometricShape {
    private static final double D = 5000;// jak daleko rysowane sa konce
    public BasicLine line = new BasicLine();
    private static final double hub = 4;
    protected final Line drawableLine;
    protected final Line drawableHub;

    @Override
    public int getPriority() {
        return 2;
    }

    public GeometricLine(String name, Plane2D plane, Transformation transformation) {
        super(name, plane, transformation);
        drawableLine = new Line();
        drawableHub = new Line();
        drawableHub.setStroke(Color.TRANSPARENT);
        drawableHub.setStrokeWidth(hub);
    }

    public void setCoordinates(BasicLine line) {
        this.line = line;
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
        drawableLine.setStartX(transformation.toScreenX(X1));
        drawableLine.setStartY(transformation.toScreenY(Y1));
        drawableLine.setEndX(transformation.toScreenX(X2));
        drawableLine.setEndY(transformation.toScreenY(Y2));
    }

    @Override
    public Shape getDrawableShape() {
        return drawableLine;
    }

    @Override
    public Shape getDrawableHub() {
        return drawableHub;
    }

    @Override
    public void setOnClicked() {
        drawableHub.setStartX(drawableLine.getStartX());
        drawableHub.setStartY(drawableLine.getStartY());
        drawableHub.setEndX(drawableLine.getEndX());
        drawableHub.setEndY(drawableLine.getEndY());
        drawableHub.setStroke(Color.CYAN);
    }

    @Override
    public void unclick() {
        drawableHub.setStroke(Color.TRANSPARENT);
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        double d = BasicLine.distance(new BasicPoint(planeX, planeY), line);
        return d / transformation.scale <= plane.hitbox;
    }

    @Override
    public BasicPoint projection(BasicPoint point) {
        return PointProjectionOntoLineBuilder.getPoint(point, line);
    }

    public static double distance(GeometricPoint p, GeometricLine l) {
        return BasicLine.distance(p.point, l.line);
    }
}
