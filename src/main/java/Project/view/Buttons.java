package Project.view;

import Project.controller.Controller;
import Project.controller.GeometricShapeRemover;
import Project.controller.Shifter;
import Project.controller.builders.circles.CircleThroughThreePointsBuilder;
import Project.controller.builders.circles.CircleWithCenterAndPointBuilder;
import Project.controller.builders.circles.IncircleBuilder;
import Project.controller.builders.intersections.IntersectionBuilder;
import Project.controller.builders.lines.*;
import Project.controller.builders.points.CenterOrMidpointBuilder;
import Project.controller.builders.points.PointBuilder;
import Project.controller.builders.points.PointProjectionOntoLineBuilder;
import Project.controller.builders.transforms.inversion.InversionBuilder;
import Project.controller.builders.transforms.polars.PoleOrPolarBuilder;
import Project.controller.builders.transforms.symmetry.line.ReflectionAboutLineBuilder;
import Project.controller.builders.transforms.symmetry.point.ReflectionAboutPointBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Priority;
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
        scrollPane.setFitToWidth(true);
        this.setMinWidth(250);
        this.setMaxWidth(730);
        this.setMaxHeight(Double.MAX_VALUE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        setVgrow(scrollPane, Priority.ALWAYS);
        buttonsBox.setMaxHeight(Double.MAX_VALUE);

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
        registerToggleButton(event -> {
                    controller.changeActor(new Shifter(controller.getViewablePlane()));
                },
                "Shifter", "shifter.png",
                "Shift shape or plane.");
        registerToggleButton(event -> {
                    controller.changeActor(new PointBuilder());
                },
                "Point", "point.png",
                "Select position or shape.");
        registerToggleButton(event -> {
                    controller.changeActor(new LineThroughPointsBuilder());
                },
                "Line", "line.png",
                "Select two points or positions.");
        registerToggleButton(event -> {
                    controller.changeActor(new SegmentThroughPointsBuilder());
                },
                "Segment", "segment.png",
                "Select two points or positions.");
        registerToggleButton(event -> {
                    controller.changeActor(new CircleWithCenterAndPointBuilder());
                },
                "Circle with center", "circleCenter.png",
                "Select center point, then point on circle.");
        registerToggleButton(event -> {
                    controller.changeActor(new CenterOrMidpointBuilder());
                },
                "Midpoint or Center", "midpoint.png",
                "Select two points or a circle.");
        registerToggleButton(event -> {
                    controller.changeActor(new CircleThroughThreePointsBuilder());
                },
                "Circle through 3 points", "circlePoints.png",
                "Select three points.");
        registerToggleButton(event -> {
                    controller.changeActor(new PerpendicularLineBuilder());
                },
                "Perpendicular Line", "perpendicularLine.png",
                "Select perpendicular line and point.");
        registerToggleButton(event -> {
                    controller.changeActor(new ParallelLineBuilder());
                },
                "Parallel Line", "parallelLine.png",
                "Select parallel line and point.");
        registerToggleButton(event -> {
                    controller.changeActor(new PerpendicularBisectorBuilder());
                },
                "Perpendicular Bisector", "perpendicularBisector.png",
                "Select two points.");
        registerToggleButton(event -> {
                    controller.changeActor(new AngleBisectorThreePointsBuilder());
                },
                "Angle Bisector", "angleBisector.png",
                "Select three points.");
        registerToggleButton(event -> {
                    controller.changeActor(new TangentsFromPointBuilder());
                },
                "Tangents", "tangents.png",
                "Select point and circle.");
        registerToggleButton(event -> {
                    controller.changeActor(new IntersectionBuilder());
                },
                "Intersection", "intersection.png",
                "Select two shapes.");
        registerToggleButton(event -> {
                    controller.changeActor(new ReflectionAboutPointBuilder());
                },
                "Reflection about Point", "reflectionPoint.png",
                "Select shape and point");
        registerToggleButton(event -> {
                    controller.changeActor(new ReflectionAboutLineBuilder());
                },
                "Reflection about Line", "reflectionLine.png",
                "Select shape and line.");
        registerToggleButton(event -> {
                    controller.changeActor(new PointProjectionOntoLineBuilder());
                },
                "Projection onto Line", "projection.png",
                "Select point and line.");
        registerToggleButton(event -> {
                    controller.changeActor(new PoleOrPolarBuilder());
                },
                "Pole or Polar", "polar.png",
                "Select point or line and circle");
        registerToggleButton(event -> {
                    controller.changeActor(new InversionBuilder());
                },
                "Inversion", "inversion.png",
                "Select shape and circle");
        registerToggleButton(event -> {
                    controller.changeActor(new IncircleBuilder());
                },
                "Incircle", "incircle.png",
                "Select three points");
        registerToggleButton(event -> {
                    controller.changeActor(new GeometricShapeRemover());
                },
                "Delete", "awesomeface.png",
                "Select shpe to delete");

        registerLayout();
    }

    private void registerToggleButton(EventHandler<ActionEvent> eventHandler, String buttonName, String imagePath, String description) {
        //ToggleButton button = new ToggleButton(buttonName);
        ActorButton button = new ActorButton(buttonName, imagePath);
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
        getChildren().add(scrollPane);

        createButtonGroup("Basic", "Shifter", "Delete", "Point", "Line", "Segment",
                "Circle with center", "Circle through 3 points", "Intersection");

        createButtonGroup("Construct", "Midpoint or Center", "Perpendicular Line", "Parallel Line",
                "Perpendicular Bisector", "Angle Bisector", "Tangents", "Projection onto Line", "Incircle");

        createButtonGroup("Transform", "Reflection about Point", "Reflection about Line", "Pole or Polar", "Inversion");
    }

    private void createButtonGroup(String groupName, String... buttonNames) {
        ActorButtonGroup buttonGroup = new ActorButtonGroup(groupName);

        for (String buttonName : buttonNames) {
            buttonGroup.addButton(buttonMap.get(buttonName));
        }

        buttonsBox.getChildren().add(buttonGroup);
    }
}
