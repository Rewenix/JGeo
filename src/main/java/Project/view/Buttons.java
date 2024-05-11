package Project.view;

import Project.controller.builders.AngleBisectorThreePointsBuilder;
import Project.controller.Controller;
import Project.controller.builders.CircleThroughThreePointsBuilder;
import Project.controller.builders.CircleWithCenterAndPointBuilder;
import Project.controller.builders.FreePointBuilder;
import Project.controller.builders.LineThroughPointsBuilder;
import Project.controller.builders.MidpointBuilder;
import Project.controller.builders.ParallelLineBuilder;
import Project.controller.builders.PerpendicularBisectorBuilder;
import Project.controller.builders.PerpendicularLineBuilder;
import Project.controller.builders.SegmentThroughPointsBuilder;
import Project.controller.Shifter;
import Project.controller.builders.TangentsFromPointBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Buttons extends HBox {
    private final ToggleGroup group = new ToggleGroup();

    public Buttons() {
        setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    }

    public void registerController(Controller controller) {
        // Dodajemy przyciski
        registerToggleButton(event -> {
            System.out.println("'Shifter' button pressed");
            controller.changeActor(new Shifter(controller.getPlane(), controller.getTransformation()));
        },
                "Shifter",
                "Shifts a point or a line. If a point is selected, it will be moved to the selected place.");
        registerToggleButton(event -> {
            System.out.println("'Point' button pressed");
            controller.changeActor(new FreePointBuilder());
        },
                "Point",
                "Draws a point where selected. A point will be dragged to a shape if it is drawn near to it.");
        registerToggleButton(event -> {
            System.out.println("'Line' button pressed");
            controller.changeActor(new LineThroughPointsBuilder());
        },
                "Line",
                "Draw a line through two selected points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Segment' button pressed");
            controller.changeActor(new SegmentThroughPointsBuilder());
        },
                "Segment",
                "Draw a segment through two selected points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Circle' button pressed");
            controller.changeActor(new CircleWithCenterAndPointBuilder());
        },
                "Circle(Center, Point)",
                "Draws a circle with a center and a point. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Midpoint' button pressed");
            controller.changeActor(new MidpointBuilder());
        },
                "Midpoint",
                "Draws a point between two points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Circle' button pressed");
            controller.changeActor(new CircleThroughThreePointsBuilder());
        },
                "Circle(Point, Point, Point)",
                "Draws a circle through three points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Perpendicular' button pressed");
            controller.changeActor(new PerpendicularLineBuilder());
        },
                "Perpendicular Line(Line, Point)",
                "Draws a perpendicular line to a selected line through a selected point. Points and lines need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Parallel' button pressed");
            controller.changeActor(new ParallelLineBuilder());
        },
                "Parallel Line(Line, Point)",
                "Draws a parallel line to a selected line through a selected point. Points and lines need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Perpendicular Bisector' button pressed");
            controller.changeActor(new PerpendicularBisectorBuilder());
        },
                "Perpendicular Bisector",
                "Draws a perpendicular bisector of two points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Angle Bisector' button pressed");
            controller.changeActor(new AngleBisectorThreePointsBuilder());
        },
                "Angle Bisector",
                "Draws an angle bisector of three points. Points need to be drawn first with another method.");
        registerToggleButton(event -> {
            System.out.println("'Tangent' button pressed");
            controller.changeActor(new TangentsFromPointBuilder());
        },
                "Tangents(Point, Circle)",
                "Draws tangents to a circle through a selected point. Points and a circle need to be drawn first with another method.");

        // Zepchnięcie następujących przycisków na prawo.
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().addAll(spacer);

        registerButton(event -> {
            System.out.println("'Undo' button pressed");
            controller.getPlane().removeLastShape();
        },
                "Undo",
                "Removes the last drawn shape.");
        registerButton(event -> {
            System.out.println("'Clear' button pressed");
            controller.getPlane().clear();
        },
                "Clear",
                "Clears the drawing pane.");
    }

    private void registerToggleButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        ToggleButton button = new ToggleButton(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        Tooltip.install(button, tip);
        button.setToggleGroup(group);
        getChildren().add(button);
    }

    private void registerButton(EventHandler<ActionEvent> eventHandler, String buttonName, String description) {
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);
        Tooltip tip = new Tooltip(description);
        Tooltip.install(button, tip);
        getChildren().add(button);
    }
}