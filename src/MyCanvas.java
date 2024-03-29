import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MyCanvas extends Canvas implements KeyListener, MouseListener, MouseMotionListener  {
    private static final int MARGIN = 40;
    private static final long serialVersionUID = 1L;
    private ArrayList<Point3D> vectices = new ArrayList<>();
    private ArrayList<int[]> edgeList = new ArrayList<>();
    private Scene scene;
    private View view;
    private Tranforamtions tranforamtions;
    private int screenHeight,screenWidth,origScreenHeight,origScreenWidth;
    private Point3D center;
    private Matrix VM1, P, CT, TT, T1, T2, AT, VM2,FT;
    private boolean clip=false;
    private double px,py; //x and y after mouse press
    private double dx,dy; //x and y after mouse drag
    private char currentOper;
    private char axisRotate;
    Frame frame;

    Point pStart, pEnd;
    boolean bFlag = false;

    public MyCanvas(Frame frame) {
        try {
            // view and scene
            this.view = new View();
            this.view.readViw(new File("ex1.viw"));
            this.scene = new Scene();
            this.scene.readScn(new File("ex1.scn"));
            this.tranforamtions = new Tranforamtions(view);
            origScreenWidth = view.getScreenWidth();
            origScreenHeight = view.getScreenHeight();
            this.frame = frame;
            load(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Component comp = e.getComponent();
                    Dimension dim = comp.getSize();
                    screenWidth = dim.width - 40;
                    screenHeight = dim.height - 40;
                    center.setX((screenWidth / 2) + MARGIN / 2);
                    center.setY((screenHeight / 2) + MARGIN / 2);
                    view.setScreenWidth(screenWidth);
                    view.setScreenHeight(screenHeight);
                    tranforamtions.setView(view);
                    reloadChanges();
                }
            });
            this.center = new Point3D((screenWidth/2)+MARGIN/2,(screenHeight/2)+MARGIN/2,0);
            this.axisRotate = 'z';
            this.clip = false;
            reloadChanges();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void load(boolean is_reset){
        this.CT = new Matrix(4,4);
        this.TT = new Matrix(4,4);
        this.vectices= scene.getVerticeList();
        this.edgeList = scene.getEdgeList();
        if(!is_reset) {
            screenWidth = view.getScreenWidth();
            screenHeight = view.getScreenHeight();
        }else{
            screenWidth = origScreenWidth;
            screenHeight = origScreenHeight;
        }
        Dimension d = new Dimension();
        d.width = screenWidth+MARGIN;
        d.height = screenHeight+MARGIN;
        this.setPreferredSize(d);
        frame.pack();
    }
    public void paint(Graphics g) {
        this.requestFocus();
        g.drawRect(MARGIN/2, MARGIN/2, screenWidth, screenHeight);
        this.FT = VM2.multiply(this.P).multiply(CT).multiply(this.TT).multiply(this.VM1);
        ArrayList<Point2Di> vertexesTag = new ArrayList<>();
        for (Point3D p : vectices) {
            Vector vec = Tranforamtions.matrixToVector(Matrix.multiply(FT,Tranforamtions.vectorToMatrix
                    (Tranforamtions.vertexToVector(p))));
            vertexesTag.add(new Point2Di(vec.getX(),vec.getY()));
        }

        for (int[] p : edgeList) {
            Point2Di startLine = vertexesTag.get(p[0]);
            Point2Di endLine = vertexesTag.get(p[1]);
            Line2D.Double line = new Line2D.Double();
            line.setLine(startLine.getX(),startLine.getY(),endLine.getX(),endLine.getY());
            if (clip) {
                if (clipping(line)) {
                    g.drawLine((int)line.x1, (int)line.y1,(int) line.x2, (int)line.y2);
                }
            } else {
                g.drawLine((int)line.x1, (int)line.y1,(int) line.x2, (int)line.y2);
            }
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
        } else if((x >= (screenWidth / 3)+20) && x <= 2 * screenWidth / 3 + 20 &&
                y >= screenHeight / 3 +20 && y < 2 * screenHeight / 3 + 20) {
            return 't';
        } else if((x < (screenWidth / 3) +20 || x > (2 * screenWidth) / 3 + 20) &&
                (y < (screenHeight / 3) + 20 || y> (2 * screenHeight) / 3 +20)) {
            return 'r';
        } else{
            return 's';
        }
    }

    /**
     * By where pressed , do an action
     */
    private void oper(){
        switch(this.currentOper){
            case 't':
                doTranslation();
                break;
            case 'r':
                doRotation();
                break;
            case 's':
                doScale();
                break;
            default:
                break;
        }
    }

    private void doTranslation() {
        CT = tranforamtions.Translation((dx - px) * ((view.getRightBound() - view.getLeftBound()) / screenWidth),
                (dy - py) * (-((view.getTopBound() - view.getBottomBound()) / screenHeight)),0);
    }
    private void doScale(){
        double distStart = Math.hypot(px-center.getX(), py-center.getY());
        double distEnd = Math.hypot(dx-center.getX(),dy-center.getY());
        double scaleParameter = distEnd / distStart;
        CT = tranforamtions.Scale(scaleParameter, scaleParameter, scaleParameter);
        createCT();
    }


    private void doRotation(){
        Vector startVec = new Vector(px-center.getX(),py-center.getY(),0);
        Vector endVec = new Vector(dx-center.getX(),dy-center.getY(),0);
        double angleStart = Vector.angle(startVec);
        double angleEnd = Vector.angle(endVec);
        double angleFinish = angleStart - angleEnd;
        CT = tranforamtions.Rotation(angleFinish, axisRotate);
        createCT();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char key = Character.toLowerCase(e.getKeyChar());

        switch (key) {
            case 'c':
                this.clip = !this.clip;
                this.repaint();
                break;
            case 'l':
                JFileChooser jfc = new JFileChooser();
                String workingDir = System.getProperty("user.dir");
                jfc.setCurrentDirectory(new File(workingDir));
                int returnValue = jfc.showOpenDialog(jfc.getParent());
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String path = jfc.getSelectedFile().getAbsolutePath();
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
                            origScreenWidth = view.getScreenWidth();
                            origScreenHeight = view.getScreenHeight();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                load(true);
                reloadChanges();
                repaint();
                break;
            case 'r':
                load(true);
                reloadChanges();
                this.clip = false;
                this.axisRotate = 'z';
                repaint();
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
    /**
     * get x and y after drag and find which operation to do
     */
    public void mouseDragged(MouseEvent e) {
        dx = e.getX();
        dy = e.getY();
        oper();
        this.repaint();
    }

    /**
     * make all Matrixes again with current data
     */
    private void reloadChanges(){
        VM1 = tranforamtions.mv1(view.getCameraPos(),view.getCameraLookAt(),view.getCameraUpDirection());
        P = tranforamtions.projection();
        T1 = tranforamtions.t1();
        T2 = tranforamtions.t2();
        VM2 = Matrix.multiply(T2,Matrix.multiply(tranforamtions.s(),T1));
    }

    /**
     * line clipping by cohen's algorithm
     * @param line the line to check if clips
     * @return clip or not
     */
    private boolean clipping(Line2D.Double line) {
        int[] bitsS = initBits(line.x1,line.y1);
        int[] bitsE = initBits(line.x2,line.y2);
        int[] bitsResultAnd = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            bitsResultAnd[i] = ((bitsS[i]!=0) && (bitsE[i]!=0))? 1:0;
        }
        if (checkBits(bitsResultAnd) != 0) {
            return false;
        }
        int[] bitsResultOr = {0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            bitsResultOr[i] = ((bitsS[i]!=0) || (bitsE[i]!=0))? 1:0;
        }
        if (checkBits(bitsResultOr) == 0) {
            return true;
        } else {

            return fixLine(line,bitsS,bitsE);
        }
    }

    private int checkBits(int[] bitsResult) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += bitsResult[i];
        }
        return sum;
    }

    private int[] initBits(double x,double y) {
        int[] bits = {0, 0, 0, 0};
        double xMin = 20, xMax = screenWidth + 20, yMin = 20, yMax = screenHeight + 20;
        if (y < yMin) {
            bits[0] = 1;
        }
        if (x > xMax) {
            bits[1] = 1;
        }
        if (y > yMax) {
            bits[2] = 1;
        }
        if (x < xMin) {
            bits[3] = 1;
        }
        return bits;
    }

    /**
     * check if need to fix the line
     */
    private boolean fixLine(Line2D.Double line ,int[] bitsS,int[] bitsE) {
        Point2Di dL = new Point2Di(20,20);
        Point2Di uL = new Point2Di(20,20+screenHeight);
        Point2Di uR = new Point2Di( 20+screenWidth,20+screenHeight);
        Point2Di dR = new Point2Di(screenWidth + 20,20);
        Point2Di[] lines={dL,dR,uR,uL};
        while (checkBits(bitsS) != 0) {
            for (int i = 0; i < 4; i++) {
                if (bitsS[i] == 1) {
                    Point2Di new_p = findIntersection(new Point2Di((int)line.x1,(int)line.y1),
                            new Point2Di((int)line.x2,(int)line.y2),lines[i],lines[(i+1)%4]);
                    line.setLine(new_p.getX(),new_p.getY(),line.x2,line.y2);
                    bitsS = initBits(line.x1,line.y1);
                    if (checkBits(bitsS)!=0){
                        return false;
                    }
                }
            }
        }
        while (checkBits(bitsE) != 0) {
            for (int i = 0; i < 4; i++) {
                if (bitsE[i] == 1) {
                    Point2Di new_p = findIntersection(new Point2Di((int)line.x1,(int)line.y1),
                            new Point2Di((int)line.x2,(int)line.y2),lines[i],lines[(i+1)%4]);
                    line.setLine(line.x1,line.y1,new_p.getX(),new_p.getY());
                    bitsE = initBits(line.x2,line.y2);
                    if (checkBits(bitsE)!=0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Point2Di findIntersection(Point2Di p1x,Point2Di p1y,Point2Di p2x,Point2Di p2y) {
        double a1 = p1y.getY() - p1x.getY();
        double b1 = p1x.getX() - p1y.getX();
        double c1 = a1 * (p1x.getX()) + b1 * (p1x.getY());
        double a2 = p2y.getY() - p2x.getY();
        double b2 = p2x.getX() - p2y.getX();
        double c2 = a2 * (p2x.getX()) + b2 * (p2x.getY());
        double determinant = a1 * b2 - a2 * b1;
        double x = (b2 * c1 - b1 * c2) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;
        return new Point2Di((int) x, (int) y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        px = e.getX();
        py = e.getY();
        currentOper = wherePressed(px, py);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dx = e.getX();
        dy = e.getY();
        TT = Matrix.multiply(CT, TT);
        CT = new Matrix(4,4);
        this.repaint();
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

    /**
     * creates net CT matrix by values
     */
    private void createCT() {
        Point3D p_look = view.getCameraLookAt();
        Point3D p_pos = view.getCameraPos();
        Vector LookAt = new Vector(p_look.getX(), p_look.getY(), p_look.getZ());
        Vector Position = new Vector(p_pos.getX(), p_pos.getY(), p_pos.getZ());
        Vector sub = Vector.sub(LookAt, Position);
        double d = Vector.vecDist(Vector.dotProduct(sub,sub));
        Matrix tmp = new Matrix(4,4);
        tmp.assignElement(d,2,3);
        CT = Matrix.multiply(CT, tmp);
        tmp.assignElement(-d,2,3);
        CT = Matrix.multiply(tmp, CT);
    }
}