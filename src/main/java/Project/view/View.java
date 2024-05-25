package Project.view;

import Project.controller.Controller;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class View extends BorderPane {
    private final Buttons buttons = new Buttons();
    private final TopBar topBar = new TopBar();
    private final SplitPane splitPane = new SplitPane();
    public final DrawingPane drawingPane = new DrawingPane();

    public View() {
        //this.setStyle("-fx-background-color: cyan;");
        buttons.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(buttons, drawingPane);
        splitPane.setDividerPosition(0, 0);
        this.setTop(topBar);
        this.setCenter(splitPane);
    }

    public void registerController(Controller controller) {
        drawingPane.registerController(controller);
        buttons.registerController(controller);
        topBar.registerController(controller);
    }
}