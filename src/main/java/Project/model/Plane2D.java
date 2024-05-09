package Project.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Plane2D {
    private ArrayList<GeometricShape> shapes = new ArrayList<>();

    public void addGeometricShape(GeometricShape shape){
        shapes.add(shape);
    }

    public void removeLastShape(){
        if(!shapes.isEmpty()) {
            Shape shape = shapes.remove(shapes.size() - 1).getDrawableShape();
            shape.setFill(Color.TRANSPARENT);
            shape.setStroke(Color.TRANSPARENT);
        }
    }

    public void clear() {
        while(!shapes.isEmpty()) {
            removeLastShape();
        }
    }

    public void update(){
        for(GeometricShape shape : shapes)shape.update();
    }

    public GeometricShape getClickedShape(double planeX, double planeY){
        for(GeometricShape shape : shapes){
            if(shape instanceof Point point && point.hasPoint(planeX, planeY)){
                return shape;
            }
        }
        for(GeometricShape shape : shapes){
            if(shape.hasPoint(planeX, planeY)){
                return shape;
            }
        }
        return null;
    }
}
