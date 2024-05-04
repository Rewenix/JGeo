package Project;

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
        /*Text text = new Text("Hello JGeo");
        text.setFill(Color.BLUE);
        VBox root = new VBox(text);
        root.setAlignment(Pos.CENTER);*/

        // Okręgi na szczęście same dobrze się zachowują przy zmianie rozmiarów okna.
        // Pozostaje jedynie przybliżanie...
        Circle circle = new Circle(400, 300, 100);      //Zcentrowany okrąg o promieniu 100.
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);

        // Proste są niestety tylko odcinkami.
        // Albo musimy od nowa liczyć gdzie się przecina prosta z obwodem sceny,
        // albo rysować "możliwie długi" odcinek.
        // Tylko to tak naprawdę sprowadzi się do liczenia przecięć z maksymalnym zakresem Double.
        Line line = new Line();
        line.setStartX(-2000);
        line.setStartY(circle.getCenterY()-100);    //Współrzędne są na odwrót w tej JavieFX z jakiegoś powodu. Początek układu jest z urzędu w lewym górnym rogu.
        line.setEndX(2000);
        line.setEndY(circle.getCenterY()-100);

        Circle touchingPoint = new Circle(400, 200, 3);     //Nie znalazłem lepszego sposobu. Trzeba troszkę się zastanowić przy skalowaniu chyba...?

        Group group = new Group(line, circle, touchingPoint);  //VBox psuje współrzędne.

        Scene scene = new Scene(group, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){launch(args);}
}
