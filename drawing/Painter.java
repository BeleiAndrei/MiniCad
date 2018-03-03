package drawing;
import shapes.Circle;
import shapes.Diamond;
import shapes.Line;
import shapes.Polygon;
import shapes.Rectangle;
import shapes.Square;
import shapes.Triangle;

public interface Painter {
    void paint(Line line);
    void paint(Circle circle);
    void paint(Diamond diamond);
    void paint(Polygon polygon);
    void paint(Rectangle rectangle);
    void paint(Square square);
    void paint(Triangle triangle);

}
