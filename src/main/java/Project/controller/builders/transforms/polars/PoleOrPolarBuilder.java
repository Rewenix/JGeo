package Project.controller.builders.transforms.polars;

import Project.controller.GeometricShapeBuilder;
import Project.model.GeometricShape;
import Project.view.viewable.ViewablePlane;

public class PoleOrPolarBuilder implements GeometricShapeBuilder {
    private final PolarBuilder polarBuilder = new PolarBuilder();
    private final PoleBuilder poleBuilder = new PoleBuilder();

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        boolean accepted = false;
        if (polarBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        if (poleBuilder.acceptArgument(shape)) {
            accepted = true;
        }
        return accepted;
    }

    @Override
    public boolean isReady() {
        return polarBuilder.isReady() || poleBuilder.isReady();
    }

    @Override
    public void reset() {
        polarBuilder.reset();
        poleBuilder.reset();
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        if (polarBuilder.isReady()) {
            polarBuilder.build(viewablePlane, planeX, planeY);
        }
        else if (poleBuilder.isReady()) {
            poleBuilder.build(viewablePlane, planeX, planeY);
        }
    }

    @Override
    public boolean awaitsPoint() {
        return polarBuilder.awaitsPoint() || poleBuilder.awaitsPoint();
    }
}
