package shapes;

public final class ShapeFactory {

    private static ShapeFactory instance = new ShapeFactory();

    private ShapeFactory() {
    }

    public static ShapeFactory getInstance() {
        return instance;
    }

    public Shape getShape(final String shapeType) {
        switch (shapeType) {
            case "SQUARE" :
                return new Square();
            case "TRIANGLE" :
                return new Triangle();
            case "LINE" :
                return new Line();
            case "CIRCLE" :
                return new Circle();
            case "DIAMOND" :
                return new Diamond();
            case "RECTANGLE" :
                return new Rectangle();
            case "POLYGON" :
                return new Polygon();
            default :
                System.out.println("Oof");
                return null;
        }
    }

}
