package Project;

import Project.controller.Controller;
import Project.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JGeo");

        View view = new View();
        Controller controller = new Controller(view.drawingPane);
        view.registerController(controller);

        //BetterView view = new BetterView();
        Scene scene = new Scene(view, 800, 600);
        scene.getStylesheets().add("style.css");

        stage.setScene(scene);
        // stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
