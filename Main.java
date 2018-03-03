import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import drawing.Image;
import shapes.Shape;
import shapes.ShapeFactory;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) throws IOException {

        FileReader in = null;
        BufferedReader br = null;
        int n, i;
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        String line;
        String[] strs;
        Shape s;
        Image img = Image.getInstance();

        try {
            in = new FileReader(args[0]);
            br = new BufferedReader(in);
            line = br.readLine();
            n = Integer.parseInt(line);

            for (i = 0; i < n; i++) {
                // Citirea se face linie cu linie
                // Vectorul de stringuri "strs" pastreaza fiecare
                // cuvant sau numar din linia citita si le trimite
                // in final clasei Image pt a fi procesate
                line = br.readLine();
                strs = line.trim().split("\\s+");
                img.getArgs(strs);
                if (strs[0].equals("CANVAS")) {
                    img.setCanvas();
                } else {
                    s = shapeFactory.getShape(strs[0]);
                    s.accept(img);
                }
            }

        } catch (IOException e) {
            System.out.println("Wrong file input");
        } finally {
            if (in != null) {
                in.close();
            }
            if (br != null) {
                br.close();
            }
            img.save();
        }
    }
}
