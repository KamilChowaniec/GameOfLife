import graphics.Input.MouseHandler;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class ClipLib {
    ArrayList<Clipboard> clips;
    ArrayList<Button> copy;
    ArrayList<Button> del;


    public ClipLib() {
        clips = new ArrayList<>();
        copy = new ArrayList<>();
        del = new ArrayList<>();
    }

    public ClipLib(String filename) {
        this();
        loadFromFile(filename);
    }

    public void addClip(Clipboard clip, ButtonHandler copyHandler, ButtonHandler deleteHandler) {
        Clipboard c = new Clipboard(clip);
        clips.add(c);
        copy.add(new Button(Area.clipboard.getX(), Area.clipboard.getY() + clips.size()*20, (int)(0.8*Area.clipboard.getWidth()), 20, "",
                copyHandler, 0, 1, 1));
        del.add(new Button(Area.clipboard.getX() + (int)(0.8*Area.clipboard.getWidth()), Area.clipboard.getY() + clips.size()*20, (int)(0.2*Area.clipboard.getWidth()), 20, "",
                deleteHandler, 1, 0, 0));

    }

    public void displayButtons() {
        for (Button c : copy) {
            c.display();
        }
        for (Button c : del) {
            c.display();
        }
    }

    public void handleButtons(){
        for (Button c : copy) {
            if(c.isFocused((int)MouseHandler.xPos(),(int)MouseHandler.yPos()))
                c.press();
        }
        for (Button c : del) {
            if(c.isFocused((int)MouseHandler.xPos(),(int)MouseHandler.yPos()))
                c.press();
        }
    }

    public void deleteClip(int index) {
        if (index < 0 || index > clips.size() - 1) return;
        clips.remove(index);
    }

    public Clipboard getClipboard(int index) {
        return clips.get(index);
    }

    public void loadFromFile(String filename) {
        File data = new File(filename);
        try {

            Scanner scan = new Scanner(data);

            Clipboard clip = new Clipboard();

            while (scan.hasNextInt()) {
                clip.readFromFile(scan);
                clips.add(clip);
            }
            scan.close();
        } catch (IOException e) {
        }
    }

    public void saveToFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(filename))));
            for (int i = 0; i < clips.size(); i++) {
                clips.get(i).saveToFile(writer);
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public int size(){
        return clips.size();
    }
}
