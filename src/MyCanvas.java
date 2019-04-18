import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MyCanvas extends Canvas implements KeyListener, MouseListener, MouseMotionListener {
    private static final int MARGIN = 40;
    private static final long serialVersionUID = 1L;

    private Scene scene;
    private View view;
    private Tranforamtions tranforamtions;
    private int screenWidth,screenHeight;
    private Point3D center;
    private Matrix VM1, P, CT, TT, T1, T2, AT, VM2;
    private boolean clip=false;
    private double px,py; //x and y after mouse press
    private double dx,dy; //x and y after mouse drag
    private char currentOper;

    Point pStart, pEnd;
    boolean bFlag = false;

    public MyCanvas() {
        try {


            // view and scene
            this.view = new View();
            this.view.readViw(new File("ex1.viw"));
            this.scene = new Scene();
            this.scene.readScn(new File("ex1.scn"));
            this.tranforamtions = new Tranforamtions();
            setSize(view.getScreenWidth()+ MARGIN, view.getScreenHeight() + MARGIN);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            this.center = new Point3D((screenWidth/2)+20,(screenHeight/2)+20,0);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void paint(Graphics g) {
        this.scene.from3Dto2D();
        List<Line> edgeList = this.scene.getEdgeList();
        g.setColor(Color.blue);
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
                CT = tranforamtions.Translation(dx - px, dy - py,0);
                break;
            case 'R':
                //TODO rotation
                break;
            case 'S':
                //TODO scale
                break;
            default:
                break;
        }
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
                break;
            case 'y':
                break;
            case 'z':
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