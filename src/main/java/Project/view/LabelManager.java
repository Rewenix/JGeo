package Project.view;

import Project.model.GeometricShape;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

// (Point)LabelManager
public class LabelManager {
    private final Pane viewPane;
    private final Map<GeometricShape, PointLabel> labels = new HashMap<>();

    public LabelManager(Pane viewPane) {
        this.viewPane = viewPane;
    }

    public void addPointLabel(ViewablePoint point) {
        PointLabel pointLabel = new PointLabel(point.getDrawableShape(), point.getGeometricShape());
        labels.put(point.getGeometricShape(), pointLabel);
        viewPane.getChildren().add(pointLabel.getLabel());
    }

    public void removePointLabel(ViewablePoint point) {
        PointLabel pointLabel = labels.remove(point.getGeometricShape());
        viewPane.getChildren().remove(pointLabel.getLabel());
    }
}
