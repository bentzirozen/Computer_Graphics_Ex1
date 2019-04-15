import javafx.util.Pair;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Point3D> vertexList = new ArrayList<>();
        List<Line> edgeList = new ArrayList<>();
        Scene scene = new Scene();
        scene.readViw(new File(args[1]));
        scene.readScn(new File(args[0]));
        Frame myFrame = new Frame("EX1");
        MyCanvas myCanvas = new MyCanvas(scene);
        myFrame.add(myCanvas);

        WindowAdapter myWindowAdapter = new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        myFrame.addWindowListener(myWindowAdapter);
        myFrame.pack();
        myFrame.setVisible(true);
    }


}
