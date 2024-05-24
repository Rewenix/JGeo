package Project.view;

import Project.controller.Controller;
import Project.controller.Shifter;
import Project.controller.builders.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.HashMap;

public class Buttons extends VBox {
    private final ScrollPane scrollPane = new ScrollPane();
    private final ToggleGroup group = new ToggleGroup();
    private final VBox buttonsBox = new VBox();
    private final HashMap<String, ButtonBase> buttonMap = new HashMap<>();

    public Buttons() {
        setAlignment(Pos.TOP_LEFT); // Ustawienie przycisków w lewym górnym rogu.
        scrollPane.setContent(buttonsBox);
        scrollPane.setPannable(true);   // Możliwość przewijania myszką.
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);    // Wyłączenie pasków przewijania
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1 0 1 0px;");   // Jak nie dawałem cyan, w którymś to się robiło szare całe albo na brzegach.
        buttonsBox.setStyle("-fx-background-color: transparent;");

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            double deltaY = event.getDeltaY() * 1.5; // Szybkość scrollowania - większy współczynnik tym szybciej.
            double height = scrollPane.getContent().getBoundsInLocal().getHeight();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue - deltaY / height);
            event.consume();
        });
    }

    public void registerController(Controller controller) {
        // Dodajemy przyciski
        registerButton(event -> {
                    System.out.println("'Undo' button pressed");
                    controller.removeLastShape();
                },
                "Undo",
                "Removes the last drawn shape.");
        registerButton(event -> {
                    System.out.println("'Clear' button pressed");
                    controller.clearShapes();
                },
                "Clear",
                "Clears the drawing pane.");

        registerToggleButton(event -> {
                    System.out.println("'Shifter' button pressed");
                    controller.changeActor(new Shifter(controller.getViewablePlane()));
                },
                "Shifter",
                "Shifts a point or the plane. If a point is selected, it will be moved to the selected place.");
        registerToggleButton(event -> {
                    System.out.println("'Point' button pressed");
                    controller.changeActor(new PointBuilder());
                },
                "Point",
                "Draws a point where selected.");
        registerToggleButton(event -> {
                    System.out.println("'Line' button pressed");
                    controller.changeActor(new LineThroughPointsBuilder());
                },
                "Line",
                "Draw a line through two selected points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Segment' button pressed");
                    controller.changeActor(new SegmentThroughPointsBuilder());
                },
                "Segment",
                "Draw a segment through two selected points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Circle' button pressed");
                    controller.changeActor(new CircleWithCenterAndPointBuilder());
                },
                "Circle(Center, Point)",
                "Draws a circle with a center and a point. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Midpoint' button pressed");
                    controller.changeActor(new CenterOrMidpointBuilder());
                },
                "Midpoint or Center",
                "Draws a midpoint of two points or a circle center. Points and circle need to be drawn first with 'Point' or 'Circle' method.");
        registerToggleButton(event -> {
                    System.out.println("'Circle' button pressed");
                    controller.changeActor(new CircleThroughThreePointsBuilder());
                },
                "Circle(Point, Point, Point)",
                "Draws a circle through three points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Perpendicular' button pressed");
                    controller.changeActor(new PerpendicularLineBuilder());
                },
                "Perpendicular Line(Line, Point)",
                "Draws a perpendicular line to a selected line through a selected point. Points and lines need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Parallel' button pressed");
                    controller.changeActor(new ParallelLineBuilder());
                },
                "Parallel Line(Line, Point)",
                "Draws a parallel line to a selected line through a selected point. Points and lines need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Perpendicular Bisector' button pressed");
                    controller.changeActor(new PerpendicularBisectorBuilder());
                },
                "Perpendicular Bisector",
                "Draws a perpendicular bisector of two points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Angle Bisector' button pressed");
                    controller.changeActor(new AngleBisectorThreePointsBuilder());
                },
                "Angle Bisector",
                "Draws an angle bisector of three points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Tangent' button pressed");
                    controller.changeActor(new TangentsFromPointBuilder());
                },
                "Tangents(Point, Circle)",
                "Draws tangents to a circle through a selected point. Points and a circle need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    System.out.println("'Intersection' button pressed");
                    controller.changeActor(new IntersectionBuilder());
                },
                "Intersection",
                "Draws an intersection of two shapes. shapes need to be drawn first with their method.");
        registerToggleButton(event -> {
                    System.out.println("'Reflection about Point' button pressed");
                    controller.changeActor(new ReflectionAboutPointBuilder());
                },
                "Reflection about Point",
                "Reflects a shape about a selected point. Shape and point need to be drawn first with their method.");
        registerToggleButton(event -> {
                    System.out.println("'Reflection about Line' button pressed");
                    controller.changeActor(new ReflectionAboutLineBuilder());
                },
                "Reflection about Line",
                "Reflects a shape about a selected line. Shape and line need to be drawn first with their method.");
        registerToggleButton(event -> {
                    System.out.println("'Projection onto Line' button pressed");
                    controller.changeActor(new PointProjectionOntoLineBuilder());
                },
                "Projection onto Line",
                "Projects a point onto a selected line. Point and line need to be drawn first with their method.");
        registerToggleButton(event -> {
                    System.out.println("'Pole or Polar' button pressed");
                    controller.changeActor(new PoleOrPolarBuilder());
                },
                "Pole or Polar",
                "Draws a pole or a polar of a point with respect to a circle. Point and circle need to be drawn first with their method.");

        registerLayout();
    }

    private void registerToggleButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        ToggleButton button = new ToggleButton(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        tip.setShowDelay(Duration.millis(100));
        Tooltip.install(button, tip);
        button.setToggleGroup(group);
        buttonMap.put(buttonName, button);
    }

    private void registerButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        tip.setShowDelay(Duration.millis(100));
        Tooltip.install(button, tip);
        buttonMap.put(buttonName, button);
    }

    // Wybór podziału na grupy
    private void registerLayout() {
        getChildren().addAll(buttonMap.get("Undo"), buttonMap.get("Clear"));

        // Miejsce przerwy między przyciskami
        VBox.setMargin(scrollPane, new Insets(20, 0, 0, 0));

        getChildren().add(scrollPane);

        createButtonGroup("Basic", "Shifter", "Point", "Line", "Segment", "Circle(Center, Point)", "Circle(Point, Point, Point)", "Intersection");

        createButtonGroup("Construct", "Midpoint or Center", "Perpendicular Line(Line, Point)", "Parallel Line(Line, Point)", "Perpendicular Bisector", "Angle Bisector", "Tangents(Point, Circle)", "Projection onto Line");

        createButtonGroup("Transform", "Reflection about Point", "Reflection about Line", "Pole or Polar");
    }

    private void createButtonGroup(String groupName, String... buttonNames) {
        VBox groupVbox = new VBox();

        Label groupLabel = new Label(groupName);
        groupVbox.getChildren().add(groupLabel);
        groupVbox.setStyle("-fx-background-color: transparent;");

        for (String buttonName : buttonNames) {
            groupVbox.getChildren().add(buttonMap.get(buttonName));
        }

        buttonsBox.getChildren().add(groupVbox);
    }
}
