package Project.view;

import Project.controller.Controller;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class View extends BorderPane {
    private final Buttons buttons = new Buttons();
    public final Pane drawingPane = new Pane();

    public View() {
        drawingPane.setStyle("-fx-background-color: white;");
        this.setStyle("-fx-background-color: cyan;");
        buttons.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 0 1 0 0;");

        Rectangle rect = new Rectangle(drawingPane.widthProperty().get(), drawingPane.heightProperty().get());
        drawingPane.setClip(rect);
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            rect.setWidth((Double) newVal);
        });
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            rect.setHeight((Double) newVal);
        });

        this.setLeft(buttons);
        this.setCenter(drawingPane);
    }

    public void registerController(Controller controller) {
        drawingPane.setOnMousePressed(event -> {
            controller.handleNormalClick(event.getX(), event.getY());
        });

        drawingPane.setOnMouseDragged(event -> {
            controller.handleDragged(event.getX(), event.getY());
        });

        drawingPane.setOnScroll(event -> {
            controller.handleScrolled(event.getX(), event.getY(), event.getDeltaY());
        });

        buttons.registerController(controller);
    }
}