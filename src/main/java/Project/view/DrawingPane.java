package Project.view;

import Project.controller.Controller;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class DrawingPane extends Pane {

    public DrawingPane(){
        this.setStyle("-fx-background-color: white");
        Rectangle rect = new Rectangle(this.widthProperty().get(), this.heightProperty().get());
        this.setClip(rect);
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            rect.setWidth((Double) newVal);
        });
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            rect.setHeight((Double) newVal);
        });
    }

    public void registerController(Controller controller){
        this.setOnMousePressed(event -> {
            controller.handleNormalClick(event.getX(), event.getY());
        });

        this.setOnMouseDragged(event -> {
            controller.handleDragged(event.getX(), event.getY());
        });

        this.setOnScroll(event -> {
            controller.handleScrolled(event.getX(), event.getY(), event.getDeltaY());
        });
    }
}
