package shapes;

import drawing.Image;

public class Rectangle implements Shape {

    @Override
    public final void accept(final Image image) {
        image.paint(this);

    }

}
