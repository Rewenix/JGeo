package Project.view;

import Project.controller.Controller;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class View extends GridPane {
    private final Buttons buttons = new Buttons();
    public final Pane drawingPane = new Pane();

    public View() {
        GridPane.setRowIndex(buttons, 0);
        GridPane.setColumnIndex(buttons, 0);
        GridPane.setRowIndex(drawingPane, 1);
        GridPane.setColumnIndex(drawingPane, 0);
        drawingPane.setStyle("-fx-background-color: white;");
        this.setStyle("-fx-background-color: cyan;");

        Rectangle rect = new Rectangle(drawingPane.widthProperty().get(), drawingPane.heightProperty().get());
        drawingPane.setClip(rect);
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            rect.setWidth((Double) newVal);
        });
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            rect.setHeight((Double) newVal);
        });

        this.getChildren().addAll(buttons, drawingPane);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        this.getColumnConstraints().add(column);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(200);
        row2.setVgrow(Priority.ALWAYS);
        this.getRowConstraints().addAll(row1, row2);
        this.setGridLinesVisible(true);
    }

    public void registerController(Controller controller) {
        drawingPane.setOnMousePressed(event -> {
            controller.handleNormalClick(event.getX(), event.getY());
        });

        drawingPane.setOnMouseDragged(event -> {
            controller.handleDragged(event.getX(), event.getY());
        });

        buttons.registerController(controller);
    }
}
