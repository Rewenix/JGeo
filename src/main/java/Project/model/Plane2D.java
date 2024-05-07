package Project.model;

import java.util.ArrayList;

public class Plane2D {
    private ArrayList<GeometricShape> shapes = new ArrayList<>();

    public void addGeometricShape(GeometricShape shape){
        shapes.add(shape);
    }

    public void update(){
        for(GeometricShape shape : shapes)shape.update();
    }

    public GeometricShape getClickedShape(double planeX, double planeY){
        for(GeometricShape shape : shapes){
            if(shape.hasPoint(planeX, planeY)){
                return shape;
            }
        }
        return null;
    }
}
