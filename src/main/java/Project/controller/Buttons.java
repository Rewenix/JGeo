package Project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;

public class Buttons extends HBox {
    private final ToggleGroup group = new ToggleGroup();

    public Buttons() {
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }

    public void registerController(BasicController controller){
        // Jak nic nie jest włączone, to nic się nie dzieje.
        // Nie działa :(.
        /*group.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == null) {
                controller.changeBuilder(null); }});*/
        //Dodajemy przyciski
        registerButton(event -> { System.out.println("'Point' button pressed"); controller.changeActor(new FreePointBuilder()); },
                "Point",
                "Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        registerButton(event -> { System.out.println("'Line' button pressed"); controller.changeActor(new LineThroughPointsBuilder()); },
                "Line",
                "Draw a line through two selected points. Points need to be drawn first with another method.");
        registerButton(event -> { System.out.println("'Shifter' button pressed"); controller.changeActor(new Shifter(controller.getPlane())); },
                "Shifter",
                "Shifts a point or a line. If a point is selected, it will be moved to the selected place.");
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