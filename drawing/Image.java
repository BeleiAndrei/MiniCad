package drawing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import shapes.Circle;
import shapes.Diamond;
import shapes.Line;
import shapes.Polygon;
import shapes.Rectangle;
import shapes.Square;
import shapes.Triangle;

public final class Image implements Painter {

    private static Image instance = new Image();
    private BufferedImage img;
    private int width;
    private int height;
    private String[] strs;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    class Pixel {
        private int x;
        private int y;

        Pixel(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Image() {
    }

    public static Image getInstance() {
        return instance;
    }

    public void setCanvas() {
        height = Integer.parseInt(strs[1]);
        width = Integer.parseInt(strs[2]);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] rgb = Utils.rgb(strs[THREE]);
        int a = Integer.parseInt(strs[FOUR]);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], Math.round(a));
        floodFill(width / 2, height / 2, fill, fill);
    }

    // Salvam imaginea
    public void save() {
        try {
            if (ImageIO.write(img, "png", new File("./drawing.png"))) {
                System.out.println("-- saved");
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    // Primim argumentele pt fiecare figura
    public void getArgs(final String[] args) {
        strs = args;
    }

    @Override
    public void paint(final Line line) {
        int x1 = Integer.parseInt(strs[1]);
        int y1 = Integer.parseInt(strs[2]);
        int x2 = Integer.parseInt(strs[THREE]);
        int y2 = Integer.parseInt(strs[FOUR]);
        int[] rgb = Utils.rgb(strs[FOUR + 1]);
        int a = Math.round(Integer.parseInt(strs[THREE * 2]));
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        drawLine(x1, y1, x2, y2, fill);

    }

    @Override
    public void paint(final Circle circle) {
        int xc = Integer.parseInt(strs[1]);
        int yc = Integer.parseInt(strs[2]);
        int r = Integer.parseInt(strs[THREE]);
        int[] crgb = Utils.rgb(strs[FOUR]);
        int ca = Math.round(Integer.parseInt(strs[FOUR + 1]));
        int[] rgb = Utils.rgb(strs[THREE * 2]);
        int a = Math.round(Integer.parseInt(strs[FOUR * 2 - 1]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], ca);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        int x = 0, y = r;
        int d = THREE - 2 * r;
        // d este parametrul de decizie
        while (y >= x) {
            drawArcs(xc, yc, x, y, cont);
            x++;

            if (d > 0) {
                y--;
                d = d + FOUR * (x - y) + FOUR + THREE * 2;
            } else {
                d = d + FOUR * x + THREE * 2;
            }
            drawArcs(xc, yc, x, y, cont);
        }

        if (xc >= width) {
            xc = width - 1;
        }
        if (yc >= height) {
            yc = height - 1;
        }

        floodFill(xc, yc, cont, fill);

    }

    void drawArcs(final int xc, final int yc, final int x, final int y, final Color cont) {

        if (xc + x < width) {
            if (yc + y < height) {
                img.setRGB(xc + x, yc + y, cont.getRGB());
            }
            if (yc - y >= 0 && yc - y < height) {
                img.setRGB(xc + x, yc - y, cont.getRGB());
            }
        }
        if (xc - x >= 0 && xc - x < width) {
            if (yc + y < height) {
                img.setRGB(xc - x, yc + y, cont.getRGB());
            }
            if (yc - y >= 0 && yc - y < height) {
                img.setRGB(xc - x, yc - y, cont.getRGB());
            }
        }
        if (xc + y < width) {
            if (yc + x < height) {
                img.setRGB(xc + y, yc + x, cont.getRGB());
            }
            if (yc - x >= 0 && yc - x < height) {
                img.setRGB(xc + y, yc - x, cont.getRGB());
            }
        }
        if (xc - y >= 0 && xc - y < width) {
            if (yc + x < height) {
                img.setRGB(xc - y, yc + x, cont.getRGB());
            }
            if (yc - x >= 0 && yc - x < height) {
                img.setRGB(xc - y, yc - x, cont.getRGB());
            }
        }
    }

    @Override
    public void paint(final Diamond diamond) {
        int x = Integer.parseInt(strs[1]);
        int y = Integer.parseInt(strs[2]);
        int horz = Math.round(Integer.parseInt(strs[THREE]) / 2);
        int vert = Math.round(Integer.parseInt(strs[FOUR]) / 2);
        int[] crgb = Utils.rgb(strs[FOUR + 1]);
        int ca = Math.round(Integer.parseInt(strs[THREE * 2]));
        int[] rgb = Utils.rgb(strs[FOUR * 2 - 1]);
        int a = Math.round(Integer.parseInt(strs[FOUR * 2]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], ca);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        drawLine(x, y - vert, x + horz, y, cont);
        drawLine(x, y - vert, x - horz, y, cont);
        drawLine(x, y + vert, x - horz, y, cont);
        drawLine(x, y + vert, x + horz, y, cont);

        if (x >= width) {
            x = width - 1;
        }
        if (y >= height) {
            y = height - 1;
        }

        floodFill(x, y, cont, fill);

    }

    @Override
    public void paint(final Polygon polygon) {
        int n = Integer.parseInt(strs[1]);
        LinkedList<Pixel> p = new LinkedList<Pixel>();
        int[] crgb = Utils.rgb(strs[2 * (n + 1)]);
        int ca = Math.round(Integer.parseInt(strs[2 * (n + 1) + 1]));
        int[] rgb = Utils.rgb(strs[2 * (n + 1) + 2]);
        int a = Math.round(Integer.parseInt(strs[2 * (n + 1) + THREE]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], ca);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);
        int i, x, y, centrux = 0, centruy = 0;

        // Citim si retinem pozitia pixelilor intr-un vector
        for (i = 2; i <= 2 * n + 1; i += 2) {
            x = Integer.parseInt(strs[i]);
            y = Integer.parseInt(strs[i + 1]);
            p.add(new Pixel(x, y));
            centrux += x;
            centruy += y;
        }

        // Parcurgem lista de pixeli si trasam linile in ordinea in care i-am primit
        for (i = 0; i < p.size() - 1; i++) {
            drawLine(p.get(i).x, p.get(i).y, p.get(i + 1).x, p.get(i + 1).y, cont);
        }
        drawLine(p.get(i).x, p.get(i).y, p.get(0).x, p.get(0).y, cont);

        centrux /= n;
        centruy /= n;

        if (centrux >= width) {
            centrux = width - 1;
        }
        if (centruy >= height) {
            centruy = height - 1;
        }

        floodFill(centrux, centruy, cont, fill);

    }

    @Override
    public void paint(final Rectangle rectangle) {
        int x = Integer.parseInt(strs[1]);
        int y = Integer.parseInt(strs[2]);
        int rheight = Integer.parseInt(strs[THREE]);
        int rwidth = Integer.parseInt(strs[FOUR]);
        int[] crgb = Utils.rgb(strs[FOUR + 1]);
        int ca = Math.round(Integer.parseInt(strs[THREE * 2]));
        int[] rgb = Utils.rgb(strs[FOUR * 2 - 1]);
        int a = Math.round(Integer.parseInt(strs[FOUR * 2]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], ca);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        rwidth--;
        rheight--;
        drawLine(x, y, x + rwidth, y, cont);
        drawLine(x + rwidth, y, x + rwidth, y + rheight, cont);
        drawLine(x + rwidth, y + rheight, x, y + rheight, cont);
        drawLine(x, y + rheight, x, y, cont);

        if (x + rwidth / 2 >= width) {
            x = width - 1;
        } else {
            x += rwidth / 2;
        }
        if (y + rheight / 2 >= height) {
            y = height - 1;
        } else {
            y += rheight / 2;
        }
        floodFill(x, y, cont, fill);

    }

    @Override
    public void paint(final Square square) {

        int x = Integer.parseInt(strs[1]);
        int y = Integer.parseInt(strs[2]);
        int len = Integer.parseInt(strs[THREE]);
        int[] crgb = Utils.rgb(strs[FOUR]);
        int ca = Math.round(Integer.parseInt(strs[FOUR + 1]));
        int[] rgb = Utils.rgb(strs[THREE * 2]);
        int a = Math.round(Integer.parseInt(strs[FOUR * 2 - 1]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], ca);
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        len--;
        drawLine(x, y, x + len, y, cont);
        drawLine(x, y, x, y + len, cont);
        drawLine(x + len, y, x + len, y + len, cont);
        drawLine(x, y + len, x + len, y + len, cont);

        if (x + len / 2 >= width) {
            x = width - 1;
        } else {
            x += len / 2;
        }
        if (y + len / 2 >= height) {
            y = height - 1;
        } else {
            y += len / 2;
        }
        floodFill(x, y, cont, fill);
    }

    @Override
    public void paint(final Triangle triangle) {

        int[] crgb = Utils.rgb(strs[FOUR * 2 - 1]);
        int a = Math.round(Integer.parseInt(strs[FOUR * 2]));
        Color cont = new Color(crgb[0], crgb[1], crgb[2], a);
        int[] rgb = Utils.rgb(strs[FOUR * 2 + 1]);
        a = Math.round(Integer.parseInt(strs[FOUR * 2 + 2]));
        Color fill = new Color(rgb[0], rgb[1], rgb[2], a);

        int x1 = Integer.parseInt(strs[1]);
        int y1 = Integer.parseInt(strs[2]);
        int x2 = Integer.parseInt(strs[THREE]);
        int y2 = Integer.parseInt(strs[FOUR]);
        int x3 = Integer.parseInt(strs[FOUR + 1]);
        int y3 = Integer.parseInt(strs[THREE * 2]);

        drawLine(x1, y1, x2, y2, cont);
        drawLine(x2, y2, x3, y3, cont);
        drawLine(x3, y3, x1, y1, cont);
        int x = (x1 + x2 + x3) / THREE;
        int y = (y1 + y2 + y3) / THREE;

        if (x >= width) {
            x = width - 1;
        }
        if (y >= height) {
            y = height - 1;
        }

        floodFill(x, y, cont, fill);

    }

    public void drawLine(final int x1, final int y1, final int x2,
            final int y2, final Color fill) {

        int x = x1;
        int y = y1;

        int deltax = Math.abs(x2 - x1);
        int deltay = Math.abs(y2 - y1);
        int s1 = x1 <= x2 ? 1 : -1;
        int s2 = y1 <= y2 ? 1 : -1;
        int aux;
        boolean interchanged;

        if (deltay > deltax) {
            aux = deltax;
            deltax = deltay;
            deltay = aux;
            interchanged = true;
        } else {
            interchanged = false;
        }

        int error = 2 * deltay - deltax;

        for (int i = 0; i <= deltax; i++) {
            if (x >= 0 && x < width && y >= 0 && y < height) {
                img.setRGB(x, y, fill.getRGB());
            }

            while (error > 0) {
                if (interchanged) {
                    x = x + s1;
                } else {
                    y = y + s2;
                }
                error -= 2 * deltax;
            }

            if (interchanged) {
                y = y + s2;
            } else {
                x = x + s1;
            }

            error += 2 * deltay;
        }
    }

    // Umplerea contururilor
    public void floodFill(final int xstart, final int ystart,
            final Color cont, final Color fill) {

        Queue<Pixel> q = new LinkedList<Pixel>();
        Pixel p;
        int x, y;

        // Adaugam primul pixel in coada si o parcurgem pana devine goala
        q.add(new Pixel(xstart, ystart));
        while (!q.isEmpty()) {
            p = q.remove();
            x = p.x;
            y = p.y;

            // Verificam pixelul daca respecta regulile
            // Daca da adaugam vecinii in coada
            if (x >= 0 && x < width && y >= 0 && y < height) {
                if (img.getRGB(x, y) != fill.getRGB()
                        && img.getRGB(x, y) != cont.getRGB()) {
                        img.setRGB(x, y, fill.getRGB());

                        q.add(new Pixel(x - 1, y));
                        q.add(new Pixel(x + 1, y));
                        q.add(new Pixel(x, y - 1));
                        q.add(new Pixel(x, y + 1));
                }
            }
        }
    }
}
