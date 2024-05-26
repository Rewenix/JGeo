package Project.view;

import Project.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TopBar extends HBox {

    public TopBar(){
        setAlignment(Pos.CENTER_LEFT);
        setMinHeight(50);
        this.setStyle("-fx-background-color: cyan");
    }

    public void registerController(Controller controller){
        registerButton(event -> {
                    controller.removeLastShape();
                },
                "Undo",
                "Removes the last drawn shape.");
        registerButton(event -> {
                    controller.clearShapes();
                },
                "Clear",
                "Clears the drawing pane.");
    }

    private void registerButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        Button button = new Button(buttonName);
        button.getStyleClass().add("topbar-button");
        button.setFont(new Font(16));
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        tip.setShowDelay(Duration.millis(100));
        Tooltip.install(button, tip);
        this.getChildren().add(button);
    }

}
