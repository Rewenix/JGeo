package Project;

import Project.controller.BasicController;
import Project.view.TestView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JGeo");

        TestView view = new TestView();
        BasicController controller = new BasicController(view.drawingPane);
        view.registerController(controller);
        Scene scene = new Scene(view, 1400, 900);

        stage.setScene(scene);
        stage.show();
        // wylacz to jak nie chcesz zeby sie punkty krecily
        // controller.demoAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
