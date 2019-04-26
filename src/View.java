import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class View {
    private ArrayList<Point> originVerticeList;
    private ArrayList<Line> originEdgeList;
    private ArrayList<Point>newVerticeList;
    private ArrayList<Line>newEdgeList;
    private Point3D cameraPos;
    private Point3D cameraLookAt;
    private Point3D cameraUpDirection;
    private double leftBound;
    private double rightBound;
    private double bottomBound;
    private double topBound;
    private int screenWidth;
    private int screenHeight;

    public void readViw(File viwFile)throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(viwFile));
        String[] cordinates = {};
        String st = null;
        while ((st=br.readLine())!=null) {
            cordinates = st.split(" ");
            switch (cordinates[0]) {
                case "Position":
                    this.cameraPos = new Point3D(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
                            Double.parseDouble(cordinates[3]));
                    break;
                case "LookAt":
                    this.cameraLookAt = new Point3D(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
                            Double.parseDouble(cordinates[3]));
                    break;
                case "Up":
                    this.cameraUpDirection = new Point3D(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
                            Double.parseDouble(cordinates[3]));
                    break;
                case "Window":
                    this.leftBound = Double.parseDouble(cordinates[1]);
                    this.rightBound = Double.parseDouble(cordinates[2]);
                    this.bottomBound = Double.parseDouble(cordinates[3]);
                    this.topBound = Double.parseDouble(cordinates[4]);
                    break;
                case "Viewport":
                    this.screenWidth = Integer.parseInt(cordinates[1]);
                    this.screenHeight = Integer.parseInt(cordinates[2]);
                    break;
                default:
                    throw new IOException("this setting dont exist in viw file!\n");
            }
        }
    }
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }



    public Point3D getCameraPos() {
        return cameraPos;
    }

    public Point3D getCameraLookAt() {
        return cameraLookAt;
    }

    public Point3D getCameraUpDirection() {
        return cameraUpDirection;
    }

    public double getBottomBound() {
        return bottomBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public double getTopBound() {
        return topBound;
    }
    public void setScreenWidth(int width){
        this.screenWidth = width;
    }
    public void setScreenHeight(int height){
        this.screenHeight = height;
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Scene scene = new Scene();
        scene.readScn(new File("example3d.scn"));
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


}
