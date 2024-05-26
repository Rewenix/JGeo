package Project.view;

import Project.controller.Controller;
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
                "Shifts a point or the plane. If a point is selected, it will be moved to the selected place.");
        registerToggleButton(event -> {
                    controller.changeActor(new PointBuilder());
                },
                "Point", "point.png",
                "Draws a point where selected.");
        registerToggleButton(event -> {
                    controller.changeActor(new LineThroughPointsBuilder());
                },
                "Line", "line.png",
                "Draw a line through two selected points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new SegmentThroughPointsBuilder());
                },
                "Segment", "segment.png",
                "Draw a segment through two selected points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new CircleWithCenterAndPointBuilder());
                },
                "Circle(Center, Point)", "circleCenter.png",
                "Draws a circle with a center and a point. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new CenterOrMidpointBuilder());
                },
                "Midpoint or Center", "midpoint.png",
                "Draws a midpoint of two points or a circle center. Points and circle need to be drawn first with 'Point' or 'Circle' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new CircleThroughThreePointsBuilder());
                },
                "Circle(Point, Point, Point)", "circlePoints.png",
                "Draws a circle through three points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new PerpendicularLineBuilder());
                },
                "Perpendicular Line(Line, Point)", "perpendicularLine.png",
                "Draws a perpendicular line to a selected line through a selected point. Points and lines need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new ParallelLineBuilder());
                },
                "Parallel Line(Line, Point)", "parallelLine.png",
                "Draws a parallel line to a selected line through a selected point. Points and lines need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new PerpendicularBisectorBuilder());
                },
                "Perpendicular Bisector", "perpendicularBisector.png",
                "Draws a perpendicular bisector of two points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new AngleBisectorThreePointsBuilder());
                },
                "Angle Bisector", "angleBisector.png",
                "Draws an angle bisector of three points. Points need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new TangentsFromPointBuilder());
                },
                "Tangents(Point, Circle)", "tangents.png",
                "Draws tangents to a circle through a selected point. Points and a circle need to be drawn first with 'Point' method.");
        registerToggleButton(event -> {
                    controller.changeActor(new IntersectionBuilder());
                },
                "Intersection", "intersection.png",
                "Draws an intersection of two shapes. shapes need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new ReflectionAboutPointBuilder());
                },
                "Reflection about Point", "reflectionPoint.png",
                "Reflects a shape about a selected point. Shape and point need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new ReflectionAboutLineBuilder());
                },
                "Reflection about Line", "reflectionLine.png",
                "Reflects a shape about a selected line. Shape and line need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new PointProjectionOntoLineBuilder());
                },
                "Projection onto Line", "projection.png",
                "Projects a point onto a selected line. Point and line need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new PoleOrPolarBuilder());
                },
                "Pole or Polar", "polar.png",
                "Draws a pole or a polar of a point with respect to a circle. Point and circle need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new InversionBuilder());
                },
                "Inversion", "inversion.png",
                "Inverts a shape with respect to a circle. Shape and circle need to be drawn first with their method.");
        registerToggleButton(event -> {
                    controller.changeActor(new IncircleBuilder());
                },
                "Incircle", "incircle.png",
                "Draws an incircle of a triangle. Triangle needs to be drawn first with 'Point' method.");

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

        createButtonGroup("Basic", "Shifter", "Point", "Line", "Segment",
                "Circle(Center, Point)", "Circle(Point, Point, Point)", "Intersection");

        createButtonGroup("Construct", "Midpoint or Center", "Perpendicular Line(Line, Point)", "Parallel Line(Line, Point)",
                "Perpendicular Bisector", "Angle Bisector", "Tangents(Point, Circle)", "Projection onto Line", "Incircle");

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
