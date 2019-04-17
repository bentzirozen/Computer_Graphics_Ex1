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
    private boolean clip=false;

    Point pStart, pEnd;
    boolean bFlag = false;

    public MyCanvas() {
        try {


            // view and scene
            this.view = new View();
            this.view.readViw(new File("ex1.viw"));
            this.scene = new Scene();
            this.scene.readScn(new File("ex1.scn"));

            setSize(view.getScreenWidth()+ MARGIN, view.getScreenHeight() + MARGIN);
            addMouseListener(this);
            addMouseMotionListener(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void paint(Graphics g) {
        this.scene.from3Dto2D();
        List<Line> edgeList = this.scene.getEdgeList();
        g.setColor(Color.blue);

        // none sense values

        for (Line e : edgeList) {
            g.drawLine(50 * (int) e.getStart().getX(), 70 * (int) e.getStart().getY(),
                    100 * (int) e.getEnd().getX(), 60 * (int) e.getEnd().getY());
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

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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