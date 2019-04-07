import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class View {
    private ArrayList<Point> originVerticeList;
    private ArrayList<Line> originEdgeList;
    private ArrayList<Point>newVerticeList;
    private ArrayList<Line>newEdgeList;
    private static Scene myScene;

    View(Scene scene){
        myScene = scene;
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Scene scene = new Scene();
        scene.readScn(new File("ex1.scn"));
        scene.readViw(new File("ex1.viw"));
        Frame myFrame = new Frame("Exercise1");
        WindowAdapter myWindowAdapter = new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        myFrame.addWindowListener(myWindowAdapter);
        myFrame.pack();
        myFrame.setVisible(true);
    }
    public void setScene(Scene scene){
        myScene = scene;
    }

}
