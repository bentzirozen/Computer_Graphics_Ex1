
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scene {
/*
    Scene class , create the vertice and edge list
 */
    private ArrayList<Point3D> verticeList;
    private ArrayList<int[]> edgeList;

    private View view;

    Scene(){
        verticeList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    public void readScn(File scnFile) throws IOException {
        Point3D x;
        Line line;
        BufferedReader br = new BufferedReader(new FileReader(scnFile));
        String[] cordinates;
        String st;
        st = br.readLine();
        int index = Integer.parseInt(st);
        for (int i=0;i<index;i++) {
            st=br.readLine();
            cordinates = st.split(" ");
            x = new Point3D(Double.parseDouble(cordinates[0]), Double.parseDouble(cordinates[1]),
                    Double.parseDouble(cordinates[2]));
            verticeList.add(x);
        }
        st=br.readLine();
        index = Integer.parseInt(st);
        for (int i=0;i<index;i++){
            st=br.readLine();
            cordinates = st.split(" ");
            int[]edges={Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1])};
            edgeList.add(edges);
        }
    }
    public void from3Dto2D() {
        ArrayList<Point3D> newList = new ArrayList<>(this.verticeList.size());
        for (Point3D p : this.verticeList) {
            Point3D p1 = Tranforamtions.orthographicProjection(p);
            newList.add(p1);
        }

        this.verticeList = newList;
    }

    public static void main(String[] args)throws Exception{
        Scene scene = new Scene();
        scene.readScn(new File("ex1.scn"));
    }

    public ArrayList<Point3D> getVerticeList() {
        return verticeList;
    }

    public ArrayList<int[]> getEdgeList() {
        return edgeList;
    }

    public View getView() {
        return view;
    }
}
