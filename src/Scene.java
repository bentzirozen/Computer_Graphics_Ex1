
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scene {
/*
    Scene class , create the vertice and edge list
 */
    private ArrayList<Point> verticeList;
    private ArrayList<Line> edgeList;
    private Point cameraPos;
    private Point cameraLookAt;
    private Point cameraUpDirection;
    private double leftBound;
    private double rightBound;
    private double bottomBound;
    private double topBound;
    private int screenWidth;
    private int screenHeight;
    private View view;

    Scene(){
        verticeList = new ArrayList<>();
        edgeList = new ArrayList<>();
        view = new View(this);
    }

    public void readScn(File scnFile) throws IOException {
        Point x;
        Line line;
        BufferedReader br = new BufferedReader(new FileReader(scnFile));
        String[] cordinates;
        String st;
        st = br.readLine();
        int index = Integer.parseInt(st);
        for (int i=0;i<index;i++) {
            st=br.readLine();
            cordinates = st.split(" ");
            x = new Point(Double.parseDouble(cordinates[0]), Double.parseDouble(cordinates[1]),
                    Double.parseDouble(cordinates[2]));
            verticeList.add(x);
        }
        st=br.readLine();
        index = Integer.parseInt(st);
        for (int i=0;i<index;i++){
            st=br.readLine();
            cordinates = st.split(" ");
            Point point1=verticeList.get(Integer.parseInt(cordinates[0]));
            Point point2=verticeList.get(Integer.parseInt(cordinates[1]));
            line = new Line(point1,point2);
            edgeList.add(line);
        }
    }

    public void readViw(File viwFile)throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(viwFile));
        String[] cordinates = {};
        String st = null;
        while ((st=br.readLine())!=null) {
            cordinates = st.split(" ");
            switch (cordinates[0]) {
                case "Position":
                    this.cameraPos = new Point(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
                            Double.parseDouble(cordinates[3]));
                    break;
                case "LookAt":
                    this.cameraLookAt = new Point(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
                            Double.parseDouble(cordinates[3]));
                    break;
                case "Up":
                    this.cameraUpDirection = new Point(Double.parseDouble(cordinates[1]), Double.parseDouble(cordinates[2]),
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
                    throw new Exception("this setting dont exist in viw file!\n");
            }
        }
    }
    public static void main(String[] args)throws Exception{
        Scene scene = new Scene();
        scene.readScn(new File("ex1.scn"));
        scene.readViw(new File("ex1.viw"));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public ArrayList<Point> getVerticeList() {
        return verticeList;
    }

    public ArrayList<Line> getEdgeList() {
        return edgeList;
    }

    public Point getCameraPos() {
        return cameraPos;
    }

    public Point getCameraLookAt() {
        return cameraLookAt;
    }

    public Point getCameraUpDirection() {
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

    public View getView() {
        return view;
    }
}
