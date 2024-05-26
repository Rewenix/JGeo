package Project.view;

import Project.model.GeometricShape;
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

    // Those values should always be positive.
    // Choice of subtraction or addition is done below and takes into account radiusProperty().
    static double defaultXOffset = 5;
    static double defaultYOffset = 15;

    public PointLabel(Shape shape, GeometricShape point) {
        if(!(shape instanceof Circle tmpCircle))
            throw new IllegalArgumentException("PointLabel must be constructed with a ViewablePoint");
        this.circle = tmpCircle;
        this.point = point;
        label = new Label(point.getName());
        label.setStyle("-fx-font-size: 12px;");
        label.layoutXProperty().bind(circle.centerXProperty().add(circle.radiusProperty().add(defaultXOffset)));
        label.layoutYProperty().bind(circle.centerYProperty().subtract(circle.radiusProperty().add(defaultYOffset)));
    }

    public Label getLabel() {
        return label;
    }
}
