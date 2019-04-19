import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MyCanvas extends Canvas implements KeyListener, MouseListener, MouseMotionListener {
    private static final int MARGIN = 40;
    private static final long serialVersionUID = 1L;
    private ArrayList<Point3D> vectices = new ArrayList<>();
    private ArrayList<int[]> edgeList = new ArrayList<>();
    private Scene scene;
    private View view;
    private Tranforamtions tranforamtions;
    private int screenWidth,screenHeight;
    private Point3D center;
    private Matrix VM1, P, CT, TT, T1, T2, AT, VM2,TrM;
    private boolean clip=false;
    private double px,py; //x and y after mouse press
    private double dx,dy; //x and y after mouse drag
    private char currentOper;
    private char axisRotate;

    Point pStart, pEnd;
    boolean bFlag = false;

    public MyCanvas() {
        try {


            // view and scene
            this.view = new View();
            this.view.readViw(new File("ex1.viw"));
            this.scene = new Scene();
            this.scene.readScn(new File("ex1.scn"));
            this.tranforamtions = new Tranforamtions(view);
            this.vectices= scene.getVerticeList();
            this.edgeList = scene.getEdgeList();
            screenWidth = view.getScreenWidth();
            screenHeight = view.getScreenHeight();
            setSize(screenWidth+ MARGIN, screenHeight + MARGIN);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            this.center = new Point3D((screenWidth/2)+20,(screenHeight/2)+20,0);
            this.axisRotate = 'z';
            this.reloadChanges();

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void paint(Graphics g) {
        g.drawRect(20, 20, screenWidth, screenHeight);
        TrM = Matrix.multiply(CT, TT);
        ArrayList<Point2Di> vertexesTag = new ArrayList<>();
        for (Point3D p : vectices) {
            Vector vec = Tranforamtions.matrixToVector(Matrix.multiply(TrM,Tranforamtions.vectorToMatrix
                    (Tranforamtions.vertexToVector(p))));
            vertexesTag.add(new Point2Di(vec.getX(),vec.getY()));
        }

        for (int[] p : edgeList) {
            Point2Di startLine = vertexesTag.get(p[0]);
            Point2Di endLine = vertexesTag.get(p[1]);
            g.drawLine((int)startLine.getX(), (int)startLine.getY(), (int)endLine.getX(), (int)endLine.getY());
        }
    }

    /**
     * returns on which square you pressed , rotate , translate etc.
     * @param x x pos of mouse
     * @param y y pos of mouse
     * @return
     */
    private char wherePressed(double x,double y){
        //not on borders
        if (x < 20 || y < 20 || x > screenWidth + 20 || y > screenHeight + 20) {
            return '0';
        } else if(x >= screenWidth / 3 +20 && x <= 2 * screenWidth / 3 + 20 && y >= screenHeight / 3 +20 && y < 2 * screenHeight / 3 + 20) {
            return 't';
        } else if((x < screenWidth / 3 +20 || x > 2 * screenWidth / 3 + 20) && (y < screenHeight / 3 + 20 || y> 2 * screenHeight / 3 +20)) {
            return 'r';
        } else{
            return 's';
        }
    }
    private void oper(){
        switch(this.currentOper){
            case 't':
                doTranslation();
                break;
            case 'R':
                //TODO rotation
                break;
            case 'S':
                doScale();
                break;
            default:
                break;
        }
    }

    private void doTranslation() {
        CT = tranforamtions.Translation(dx - px, dy - py,0);
    }
    private void doScale(){
        double radiusPStart = Math.hypot(px-center.getX(), py-center.getY());
        double radiusPEnd = Math.hypot(dx-center.getX(),dy-center.getY());
        double scaleParameter = radiusPEnd / radiusPStart;
        CT = tranforamtions.Scale(scaleParameter, scaleParameter, scaleParameter);
        CT = Matrix.multiply(tranforamtions.Translation(center.getX(), center.getY(), 0),
                Matrix.multiply(CT, tranforamtions.Translation(-center.getX(), -center.getY(), 0)));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = Character.toLowerCase(e.getKeyChar());

        switch (key) {
            case 'c':
                this.clip = true;
                this.repaint();
                break;
            case 'l':
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                String path = chooser.getSelectedFile().getAbsolutePath();
                String extension = path.substring(path.lastIndexOf('.') + 1);
                if (extension.equals("scn")) {
                    try {
                        this.scene.readScn(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if (extension.equals("viw")) {
                    try {
                        this.view.readViw(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                break;
            case 'r':
                break;
            case 'x':
                this.axisRotate = 'x';
                break;
            case 'y':
                this.axisRotate = 'y';
                break;
            case 'z':
                this.axisRotate = 'z';
                break;
            case 'q':
                exit(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged");
        dx = e.getX();
        dy = e.getY();
        oper();
        this.repaint();
    }
    private void reloadChanges(){
        this.CT = new Matrix(4,4);
        this.TT = new Matrix(4,4);
        VM1 = tranforamtions.mv1(view.getCameraPos(),view.getCameraLookAt(),view.getCameraUpDirection());
        P = tranforamtions.projection();
        T1 = tranforamtions.t1();
        T2 = tranforamtions.t2();
        VM2 = Matrix.multiply(T2,Matrix.multiply(tranforamtions.s(),T1));
        this.TrM = Matrix.multiply(VM2, (Matrix.multiply(P, Matrix.multiply(CT, (Matrix.multiply(TT, VM1))))));
        TT = TrM;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        px = e.getX();
        py = e.getY();
        currentOper = wherePressed(px, py);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}