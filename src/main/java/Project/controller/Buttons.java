package Project.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Buttons extends HBox {
    public Buttons(Scene scene) {
        ArrayList<Button> list = new ArrayList<>();
        Button addPoint = new Button("Point");
        addPoint.setOnAction(event -> {
            scene.setOnMouseClicked(mouseEvent -> {
                //TODO: add point through model
            });
        });
        Tooltip addPointTip = new Tooltip("Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        Tooltip.install(addPoint, addPointTip);
        list.add(addPoint);
        Button drawLine = new Button("Line");
        drawLine.setOnAction(event -> {
            scene.setOnMouseClicked(mouseEvent -> {
                //TODO: add a line through two points
            });
        });
        Tooltip drawLineTip = new Tooltip("Draw a line through two selected points. Points need to be drawn first with another method.");
        Tooltip.install(drawLine, drawLineTip);
        list.add(drawLine);
        getChildren().addAll(list);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }
}
