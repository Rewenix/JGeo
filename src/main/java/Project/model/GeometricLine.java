package Project.model;

import Project.controller.Transformation;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class GeometricLine extends GeometricShape{
    private static final double D = 2000;//jak daleko rysowane sa konce
    public double A = 1, B = 1, C = 0; //wspolrzedne w postaci ogolnej (Ax + By + C = 0) zeby proste mogly byc tez pionowe
    private Line drawableLine;

    public GeometricLine(String name, Plane2D plane, Transformation transformation){
        super(name, plane, transformation);
        drawableLine = new Line();//zeby sie wyróżniała na tle punktu
    }

    @Override
    public void updateDrawable() {
        //TODO rysowanie musi uwzgledniac transformacje aby nie dalo sie zobaczyc koncow prostej po zoomie/przesunieciu
        //bedziemy chcieli dzielic przez wieksze z A/B bo to chyba daje lepsza dokladnosc(unikamy dzielenia przez cos bliskie zero)
        double X1, Y1, X2, Y2;
        if(A > B){
            Y1 = -D; Y2 = D;
            X1 = - (B/A) * Y1 - C/A;
            X2 = - (B/A) * Y2 - C/A;
        }else{
            X1 = -D; X2 = D;
            Y1 = - (A/B) * X1 - C/B;
            Y2 = - (A/B) * X2 - C/B;
        }
        //System.out.println("Obliczone wspolrzedne (X1, Y1)=" + X1 +" " + Y1 + "   (X2, Y2)" + X2 + " " + Y2);
        drawableLine.setStartX(X1);
        drawableLine.setStartY(Y1);
        drawableLine.setEndX(X2);
        drawableLine.setEndY(Y2);
    }

    @Override
    public Shape getDrawableShape() {
        return drawableLine;
    }

    @Override
    public boolean hasPoint(double planeX, double planeY) {
        //TODO tu trzeba zaimplementowac klikanie. Może przydałoby się gdzieś zdefiniować funkcje matematyczne??
        return false;
    }
}
