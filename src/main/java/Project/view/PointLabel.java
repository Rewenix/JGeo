package Project.view;

import Project.Config;
import Project.model.GeometricShape;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

// ViewablePoint does not need to hold a reference to the PointLabel
// It is enough to construct it correctly upon adding to ViewablePlane

// This will be updated automatically upon shifting

// One just needs to be careful so that shifting of Label is possible only when Shifter is pressed

// Deleting performed like GenCircle deletion:
// if last GeoPoint is deleted, drop last Label
// Deletion based on GeometricPoint is also possible. We may keep a map of GeometricPoint to PointLabel
public class PointLabel {
    private final Label label;
    private final Circle circle;
    private final GeometricShape point; // So that name can be updated
    private double xOffset;
    private double yOffset;

    public PointLabel(Shape shape, GeometricShape point) {
        if (!(shape instanceof Circle tmpCircle))
            throw new IllegalArgumentException("PointLabel must be constructed with a ViewablePoint");
        this.circle = tmpCircle;
        this.point = point;
        label = new Label(point.getName());
        label.setStyle("-fx-font-size: 12px;");

        BooleanBinding isNaNBinding = Bindings.createBooleanBinding(() ->
                        Double.isNaN(circle.centerXProperty().getValue()) || Double.isNaN(circle.centerYProperty().getValue()) || Double.isNaN(circle.radiusProperty().getValue()),
                circle.centerXProperty(), circle.centerYProperty(), circle.radiusProperty());

        NumberBinding safeXBinding = Bindings
                .when(isNaNBinding)
                .then(Double.POSITIVE_INFINITY)
                .otherwise(circle.centerXProperty().add(circle.radiusProperty().add(Config.LABEL_DEFAULT_X_OFFSET)));
        label.layoutXProperty().bind(safeXBinding);

        NumberBinding safeYBinding = Bindings
                .when(isNaNBinding)
                .then(Double.POSITIVE_INFINITY)
                .otherwise(circle.centerYProperty().subtract(circle.radiusProperty().add(Config.LABEL_DEFAULT_Y_OFFSET)));
        label.layoutYProperty().bind(safeYBinding);
    }

    public Label getLabel() {
        return label;
    }
}
