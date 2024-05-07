package Project.controller;

import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;

public class Buttons extends HBox {
    private ToggleButton addPoint = new ToggleButton("Point");
    private ToggleButton drawLine = new ToggleButton("Line");

    public Buttons() {
        ToggleGroup group = new ToggleGroup();

        addPoint.setToggleGroup(group);
        Tooltip addPointTip = new Tooltip("Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        Tooltip.install(addPoint, addPointTip);

        drawLine.setToggleGroup(group);
        Tooltip drawLineTip = new Tooltip("Draw a line through two selected points. Points need to be drawn first with another method.");
        Tooltip.install(drawLine, drawLineTip);

        getChildren().addAll(addPoint, drawLine);
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }

    public void registerController(BasicController controller){
        addPoint.setOnAction(event ->{
            System.out.println("Point button pressed");
            controller.changeBuilder(0);
        });
        drawLine.setOnAction(event -> {
            System.out.println("Line button pressed");
            controller.changeBuilder(1);
        });
    }
}