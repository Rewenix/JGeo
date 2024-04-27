package Project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JGeo");
        Text text = new Text("Hello JGeo");
        text.setFill(Color.BLUE);
        VBox root = new VBox(text);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){launch(args);}
}
