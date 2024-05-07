package Project.controller;

import Project.model.GeometricShape;
import Project.model.Plane2D;
import Project.model.Point;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BasicController {
    private Plane2D plane = new Plane2D();
    private Transformation transformation = new Transformation();
    private ArrayList<GeometricShapeBuilder> builders = new ArrayList<>();
    private int selectedBuilderId = 0;//nie jestem pewny czy to jest konieczne
    private GeometricShapeBuilder selectedBuilder;
    private Pane viewPane;

    public BasicController(Pane viewPane){
        this.viewPane = viewPane;
        //tu jak na razie trzeba dodawac buildery. Mozna rozwazyc czy nie zrobic jakiejs wspolnej metody na dodawanie builderow razem z przyciskami
        builders.add(new FreePointBuilder());
        builders.add(new LineThroughPointsBuilder());
        selectedBuilder = builders.get(selectedBuilderId);
    }

    public void changeBuilder(int id){
        System.out.println("Change builder with id=" + id);
        selectedBuilder.reset();
        selectedBuilderId = id;
        selectedBuilder = builders.get(id);
    }

    public void handleNormalClick(double screenX, double screenY){
        double planeX = transformation.toPlaneX(screenX);
        double planeY = transformation.toPlaneY(screenY);
        GeometricShape clickedShape = plane.getClickedShape(planeX, planeY);
        selectedBuilder.acceptArgument(clickedShape);
        if(selectedBuilder.isReady()){
            System.out.println("Building shape with builderId=" + selectedBuilderId);
            selectedBuilder.build(plane, transformation, viewPane, planeX, planeY);
            selectedBuilder.reset();
        }
    }

    //TODO to jest do wywalenia ale chcialem pokazac ze updatowanie zaleznosci dziala
    //w szczegolnosci to jest absurdalnie zabugowane i w losowych momentach powstaja losowe bledy, czasem sie nawet graf psuje
    public void demoAnimation(){
        builders.get(0).build(plane, transformation, viewPane, 200, 200);
        builders.get(0).build(plane, transformation, viewPane, 400, 200);
        builders.get(0).build(plane, transformation, viewPane, 200, 400);
        builders.get(0).build(plane, transformation, viewPane, 500, 500);
        Point a = (Point)plane.getClickedShape(200, 200);
        Point b = (Point)plane.getClickedShape(400, 200);
        Point c = (Point)plane.getClickedShape(200, 400);
        Point d = (Point)plane.getClickedShape(500, 500);
        GeometricShapeBuilder builder = builders.get(1);
        builder.acceptArgument(d); builder.acceptArgument(a);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        builder.acceptArgument(d); builder.acceptArgument(b);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        builder.acceptArgument(d); builder.acceptArgument(c);
        builder.build(plane, transformation, viewPane, 0, 0);
        builder.reset();
        Thread thread = new Thread(new Runnable() {
            double angle = 0;
            double lastTime = System.currentTimeMillis() * 0.001;
            double initTime = System.currentTimeMillis() * 0.001;
            @Override
            public void run() {
                while(true) {
                    double time = System.currentTimeMillis() * 0.001;
                    double deltaTime = time - lastTime;
                    angle += deltaTime;
                    d.x = 500 + 100 * Math.cos(angle);
                    d.y = 500 + 100 * Math.sin(angle);
                    lastTime = time;
                    plane.update();
                    if(time - initTime > 10)break;//koniec po 10 sekundach zeby przetestowac normalne dzialanie programu
                }
            }
        });
        thread.start();
    }
}
