package Project.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;

public class Buttons extends HBox {
    private ToggleGroup group = new ToggleGroup();
    private BasicController controller;

    public Buttons() {
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }

    public void registerController(BasicController controller){
        this.controller = controller;
        registerButton(event -> { System.out.println("'Point' button pressed"); controller.changeBuilder(controller.builders.get(0)); },
                "Point",
                "Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        registerButton(event -> { System.out.println("'Line' button pressed"); controller.changeBuilder(controller.builders.get(1)); },
                "Line",
                "Draw a line through two selected points. Points need to be drawn first with another method.");
    }

    private void registerButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        ToggleButton button = new ToggleButton(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        Tooltip.install(button, tip);
        button.setToggleGroup(group);
        getChildren().add(button);
    }
}