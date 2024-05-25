package Project.view;

import Project.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TopBar extends HBox {

    public TopBar(){
        setAlignment(Pos.TOP_LEFT);
        setMinHeight(50);
        this.setStyle("-fx-background-color: cyan");
    }

    public void registerController(Controller controller){
        registerButton(event -> {
                    System.out.println("'Undo' button pressed");
                    controller.removeLastShape();
                },
                "Undo",
                "Removes the last drawn shape.");
        registerButton(event -> {
                    System.out.println("'Clear' button pressed");
                    controller.clearShapes();
                },
                "Clear",
                "Clears the drawing pane.");
    }

    private void registerButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        tip.setShowDelay(Duration.millis(100));
        Tooltip.install(button, tip);
        this.getChildren().add(button);
    }

}
