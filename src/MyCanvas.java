import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MyCanvas extends Canvas implements KeyListener, MouseListener, MouseMotionListener {
    private boolean clip;
    private Scene scene;

    public MyCanvas(Scene scene) {
        setSize(420, 420);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.scene = scene;
        this.clip = false;
    }

    @Override
    public void paint(Graphics g) {
        List<Line> newVL = scene.getEdgeList();
        for(int i=0;i<newVL.size();i++){
            Line line = newVL.get(i);
            Point3D p1 = line.getStart();
            Point3D p2 = line.getEnd();
            g.drawLine((int)p1.getX()*50, (int)p1.getY()*50,(int)p2.getX()*50,(int)p2.getY()*50);
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
                        this.scene.readViw(new File(path));
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