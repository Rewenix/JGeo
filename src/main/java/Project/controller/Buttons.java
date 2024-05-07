package Project.controller;

import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;

public class Buttons extends HBox {
    public Buttons(Scene scene) {
        ToggleGroup group = new ToggleGroup();

        ToggleButton addPoint = new ToggleButton("Point");
        addPoint.setOnAction(event -> {
            scene.setOnMouseClicked(mouseEvent -> {
                //TODO: add point through model
            });
        });
        addPoint.setToggleGroup(group);
        Tooltip addPointTip = new Tooltip("Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        Tooltip.install(addPoint, addPointTip);

        ToggleButton drawLine = new ToggleButton("Line");
        drawLine.setOnAction(event -> {
            scene.setOnMouseClicked(mouseEvent -> {
                //TODO: add a line through two points
            });
        });
        drawLine.setToggleGroup(group);
        Tooltip drawLineTip = new Tooltip("Draw a line through two selected points. Points need to be drawn first with another method.");
        Tooltip.install(drawLine, drawLineTip);

        getChildren().addAll(addPoint, drawLine);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }
}