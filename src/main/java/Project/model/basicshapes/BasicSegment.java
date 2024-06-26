package Project.model.basicshapes;

public class BasicSegment extends BasicLine {
    public BasicPoint p1, p2;

    public BasicSegment(BasicPoint p1, BasicPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
        setABC();
    }

    public BasicSegment() {
        this(new BasicPoint(), new BasicPoint());
    }

    public void setCoordinates(BasicPoint p1, BasicPoint p2) {
        this.p1.setCoordinates(p1);
        this.p2.setCoordinates(p2);
        setABC();
    }

    private void setABC() {
        A = p1.y - p2.y;
        B = p2.x - p1.x;
        C = p1.x * p2.y - p2.x * p1.y;
    }

    public void setCoordinates(BasicSegment s) {
        setCoordinates(s.p1, s.p2);
    }
}
