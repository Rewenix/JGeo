package Project.view;

import Project.controller.BasicController;
import Project.controller.Buttons;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class TestView extends GridPane {
    public Buttons buttons = new Buttons();
    public Pane drawingPane = new Pane();

    public TestView(){
        GridPane.setRowIndex(buttons, 0);
        GridPane.setColumnIndex(buttons, 0);
        GridPane.setRowIndex(drawingPane, 1);
        GridPane.setColumnIndex(drawingPane, 0);
        drawingPane.setStyle("-fx-background-color: white;");
        this.setStyle("-fx-background-color: cyan;");

        //kod zeby punkty i proste nie renderowaly sie poza Panem, bo to psulo przyciski i zle wygladalo
        Rectangle rect = new Rectangle(drawingPane.widthProperty().get(), drawingPane.heightProperty().get());
        drawingPane.setClip(rect);
        drawingPane.widthProperty().addListener((obs, oldVal, newVal) ->{
            rect.setWidth((Double) newVal);
        });
        drawingPane.heightProperty().addListener((obs, oldVal, newVal) ->{
            rect.setHeight((Double)newVal);
        });

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

        //Group group = new Group(line, circle, touchingPoint);  //VBox psuje współrzędne.
        //drawingPane.getChildren().addAll(line, circle, touchingPoint);


        // Wciśnięcie myszy dodaje punkt na scenie.
        /*drawingPane.setOnMousePressed(event -> {
            Circle point = new Circle(event.getX(), event.getY(), 3);
            drawingPane.getChildren().add(point);
        });*/
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

    public void registerController(BasicController controller){
        drawingPane.setOnMousePressed(event ->{
            controller.handleNormalClick(event.getX(), event.getY());
        });

        drawingPane.setOnMouseDragged(event -> {
            controller.handleDragged(event.getX(), event.getY());
        });

        buttons.registerController(controller);
    }
}
