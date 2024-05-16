package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class CenterOrMidpointBuilder implements GeometricShapeBuilder {
    private final CircleCenterBuilder circleCenterBuilder = new CircleCenterBuilder();
    private final MidpointBuilder midpointBuilder = new MidpointBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        return circleCenterBuilder.acceptArgument(shape) || midpointBuilder.acceptArgument(shape);
    }

    @Override
    public boolean isReady() {
        return circleCenterBuilder.isReady() || midpointBuilder.isReady();
    }

    @Override
    public void reset() {
        circleCenterBuilder.reset();
        midpointBuilder.reset();
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        if (circleCenterBuilder.isReady()) {
            circleCenterBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
        else if (midpointBuilder.isReady()) {
            midpointBuilder.build(plane, transformation, viewPane, planeX, planeY);
        }
    }
}
