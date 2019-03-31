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
        MyCanvas myCanvas = new MyCanvas(myScene);
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
    public void setScene(Scene scene){
        myScene = scene;
    }

}

class MyCanvas extends Canvas implements MouseListener,  MouseMotionListener {
    private Scene scene;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MyCanvas(Scene scene) {
        setSize(scene.getScreenWidth()+40, scene.getScreenHeight()+40);
        this.scene = scene;
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(0,20,this.scene.getScreenWidth()-40,this.scene.getScreenHeight()-40);
    }

    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //


    }

    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}