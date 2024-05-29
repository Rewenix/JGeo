package Project.controller.builders.points;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

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
    public boolean awaitsPoint() {
        return circleCenterBuilder.awaitsPoint() || midpointBuilder.awaitsPoint();
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (circleCenterBuilder.isReady()) {
            circleCenterBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (midpointBuilder.isReady()) {
            midpointBuilder.build(viewablePlane, planeX, planeY);
        }
    }
}
