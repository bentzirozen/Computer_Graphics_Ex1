import java.awt.*;
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

    Scene(){
        verticeList = new ArrayList<>();
        edgeList = new ArrayList<>();

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
            x = new Point(Integer.parseInt(cordinates[0]), Integer.parseInt(cordinates[1]));
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
    public static void main(String[] args)throws Exception{
        Scene scene = new Scene();
        File file = new File(args[0]);
        scene.readScn(file);
    }
}
