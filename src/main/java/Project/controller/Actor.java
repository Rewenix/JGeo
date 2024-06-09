package Project.controller;

import Project.view.viewable.ViewablePlane;

public interface Actor {
    void handleClick(ViewablePlane viewablePlane, double screenX, double screenY);
}
