package Project.controller;

import Project.model.GeometricShape;
import Project.model.Plane2D;
import javafx.scene.Group;
import javafx.scene.Parent;

import java.util.ArrayList;

public class BasicController {
    private Plane2D plane = new Plane2D();
    private Transformation transformation = new Transformation();
    private ArrayList<GeometricShapeBuilder> builders = new ArrayList<>();
    private int selectedBuilderId = 0;//nie jestem pewny czy to jest konieczne
    private GeometricShapeBuilder selectedBuilder;
    private Group viewGroup;

    public BasicController(Group viewGroup){
        this.viewGroup = viewGroup;
        //tu jak na razie trzeba dodawac buildery. Mozna rozwazyc czy nie zrobic jakiejs wspolnej metody na dodawanie builderow razem z przyciskami
        builders.add(new FreePointBuilder());
        selectedBuilder = builders.get(selectedBuilderId);
    }

    public void changeBuilder(int id){
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
            selectedBuilder.build(plane, transformation, viewGroup, planeX, planeY);
            selectedBuilder.reset();
        }
    }
}
