package Project;

import Project.view.TestView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JGeo");
        TestView view = new TestView();
        Scene scene = new Scene(view, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){launch(args);}
}
