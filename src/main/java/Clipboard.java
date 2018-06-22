import java.io.PrintWriter;
import java.util.Scanner;

public class Clipboard {
    boolean[][] data;
    int width, height;

    public Clipboard() {
        data = new boolean[1][1];
        width = 1;
        height = 1;
    }

    public Clipboard(Clipboard clip) {
        width = clip.width;
        height = clip.height;
        data = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = clip.data[i][j];
            }
        }
    }

    public Clipboard(boolean[][] clip) {
        setClipboard(clip);
    }

    public void setClipboard(boolean[][] clip) {
        width = clip.length;
        height = clip[0].length;

        data = new boolean[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = clip[i][j];
            }
        }
    }

    public void setClipboard(Clipboard clip) {
        width = clip.width;
        height = clip.height;
        data = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = clip.data[i][j];
            }
        }
    }

    public boolean isCellAlive(int i, int j) {
        return data[i][j];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void readFromFile(Scanner scan) {
        width = scan.nextInt();
        height = scan.nextInt();
        data = new boolean[width][height];
        boolean state = scan.nextInt() == 0;
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (count == 0) {
                    count = scan.nextInt();
                    state = !state;
                }
                data[i][j] = state;
                count--;

            }
        }
    }

    public void saveToFile(PrintWriter printer) {
        printer.print(width + " " + height + " ");
        boolean state = data[0][0];
        int count = 0;
        printer.print((state ? 1 : 0) + " ");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (data[i][j] == state) {
                    count++;
                } else {
                    printer.print(count + " ");
                    count = 1;
                    state = !state;
                }
            }
        }
        printer.print(count);
    }

}
