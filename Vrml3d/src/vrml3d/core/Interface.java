/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import java.awt.KeyEventPostProcessor;
import java.awt.event.MouseAdapter;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4d;

/**
 *
 * @author Marcel
 */
public class Interface extends MouseAdapter implements KeyListener, KeyEventPostProcessor {

    My_VRML core;
    private int x;
    private int y;
    private boolean click = false, visao = false;
    private Plug move;
    final PickCanvas pickCanvas;

    public Interface(My_VRML ex) {
        core = ex;
        pickCanvas = new PickCanvas(core.getCanvas3D(), core.getPrincipal().getBranchGroup());
        core.getCanvas3D().addMouseListener(this);
        core.getCanvas3D().addMouseMotionListener(this);
        core.getCanvas3D().addMouseWheelListener(this);
        core.getCanvas3D().addKeyListener(this);

    }

    public void mouseClicked(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (!core.getCam().isPessoa() && core.getCam().isColocando_plug()) {
            if (e.getButton() == 1) {
                pickCanvas.setMode(pickCanvas.GEOMETRY);
                pickCanvas.setShapeLocation(e);
                PickResult result = pickCanvas.pickClosest();
                Node temp = null;
                if (result == null) {
//                    System.out.println("Nothing picked");
                } else {
                    for (temp = result.getObject(); !(temp instanceof Objeto); temp = temp.getParent()) {
                    }
                    if (temp.getName().equalsIgnoreCase("plug")) {
                        move = (Plug) temp;
                        click = true;
                        x = e.getX();
                        y = e.getY();
                    }
                }
            }

            if (e.getButton() == 3) {
                click = false;
            }
        }



    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

        if (move != null) {
            move = null;
        }
        if (visao) {
            core.getCam().SetRotacao(0, 0);
            visao = false;
        }
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");

        if (click && !core.getCam().isPessoa()) {

            try {

                double yP = core.getCanvas3D().getHeight() * core.getCanvas3D().getPhysicalHeight();
                double xP = core.getCanvas3D().getWidth() * core.getCanvas3D().getPhysicalWidth();

//                         System.out.println(Tela.getPhysicalHeight());
//                         System.out.println(Tela.getPhysicalWidth());

                if (!core.getCam().isColocando_plug()) {
                    move.setXYZ(move.getX() + ((e.getX() - x) / xP), move.getY() - ((e.getY() - y) / yP) / core.getCanvas3D().getPhysicalHeight() / 37.5, move.getZ());
                } else {
                    move.setXYZ(move.getX() + ((e.getX() - x) / xP), move.getY(), move.getZ() + ((e.getY() - y) / yP) / core.getCanvas3D().getPhysicalHeight() / 37.5);
                }

                x = e.getX();
                y = e.getY();

//                System.out.println("x 11:" + move.getX() + " y:" + move.getY() + "z :" + move.getZ());


//                }
//                        System.out.println("Heere2");
            } catch (NullPointerException ex) {
            }
        }
        if (core.getCam().isPessoa() && visao) {



            core.getCam().SetRotacao(core.getCam().getRotY() + ((e.getX() - x)), core.getCam().getRotX() - (e.getY() - y));


            x = e.getX();
            y = e.getY();



        }



    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        try {
//                throw new UnsupportedOperationException("Not supported yet.");
            if (core.getCam().isColocando_plug()) {
                if (e.getUnitsToScroll() > 0) {
                    move.setXYZ(move.getX(), move.getY() + 0.02, move.getZ());
                } else {
                    move.setXYZ(move.getX(), move.getY() - 0.02, move.getZ());
//            
                }
            } else {
                if (e.getUnitsToScroll() > 0) {
                    move.setXYZ(move.getX(), move.getY(), move.getZ() + 0.5);
                } else {
                    move.setXYZ(move.getX(), move.getY(), move.getZ() - 0.5);
                }
            }
//        System.out.println("x 22:" + move.getX() + " y:" + move.getY() + "z :" + move.getZ());
        } catch (NullPointerException ex) {
        }
    }

    public void keyTyped(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyPressed(KeyEvent e) {
        try {
//            System.out.println("tecla: " + e.getKeyText(e.getKeyCode()));
//                    System.out.println("Objeto:" + move.getName());
            if (core.getCam().isPessoa()) {

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("right")) {

                    core.getCam().setX(core.getCam().getX() + 0.1);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("left")) {
                    core.getCam().setX(core.getCam().getX() - 0.1);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("down")) {

                    core.getCam().setZ(core.getCam().getZ() + 0.1);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("up")) {
                    core.getCam().setZ(core.getCam().getZ() - 0.1);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("d")) {

                    core.getCam().setRotY(core.getCam().getRotY() + 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("a")) {
                    core.getCam().setRotY(core.getCam().getRotY() - 2);
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("w")) {

                    core.getCam().setRotX(core.getCam().getRotX() + 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("s")) {

                    core.getCam().setRotX(core.getCam().getRotX() - 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("escape")) {

                    core.getCam().SetRotacao(0, 0);
                    visao = false;
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("ctrl")) {
                    if (visao == false) {
                        visao = true;
                    } else {
                        visao = false;
                    }

                }


                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("x")) {
//                            System.out.println(core.getCam().getName());
                    System.out.println("x:" + core.getCam().getX());
                    System.out.println("y:" + core.getCam().getY());
                    System.out.println("z:" + core.getCam().getZ());
                    System.out.println("rot x:" + core.getCam().getRotX());
                    System.out.println("rot y:" + core.getCam().getRotY());
                }




                Transform3D temp = new Transform3D();
                core.getCam().getTransform(temp);
                core.getCam().setTransformView(temp);

            } else {

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("right")) {
//                System.out.println("Entra");
                    move.setX(move.getX() + 0.01);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("left")) {
                    move.setX(move.getX() - 0.01);
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("up")) {
                    move.setY(move.getY() + 0.01);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("down")) {
                    move.setY(move.getY() - 0.01);
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("page up")) {
                    move.setZ(move.getZ() - 0.01);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("page down")) {

                    move.setZ(move.getZ() + 0.01);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("esc")) {
                    move.setZ(move.getZ() + 0.01);
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("d")) {
                    move.setRotZ(move.getRotZ() - 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("a")) {
                    move.setRotZ(move.getRotZ() + 2);
                }

                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("w")) {

                    move.setRotX(move.getRotX() + 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("s")) {

                    move.setRotX(move.getRotX() - 2);
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("escape")) {
                    move.setCollidable(false);
                    move.setXYZ(core.getApp().getPaciente().getX(), core.getApp().getPaciente().getY() + 0.3, core.getApp().getPaciente().getZ() - 0.5);
                    move.setRotX(0);
                    move.setRotY(0);
                    move.setRotZ(0);
                    move.setCollidable(true);
                    move = null;
                }
                if (e.getKeyText(e.getKeyCode()).equalsIgnoreCase("x")) {
                    System.out.println(move.getName());
                    System.out.println("Bounds:" + (BoundingBox) move.getCollisionBounds());
                    System.out.println("Pos:" + move.getXYZ());
                    System.out.println("Rot: " + move.getRotXYZ());
                    System.out.println("Posicao:" + move.getLocal());


                }



            }

        } catch (NullPointerException ex) {
            move = null;
        }



//                throw new UnsupportedOperationException("Not supported yet.");

    }

    public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean postProcessKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_F2: {
                    core.getCam().setPadrao(1);
                    return true;
                }
                case KeyEvent.VK_F3: {
                    core.getCam().setPadrao(2);
                    return true;
                }
                case KeyEvent.VK_F4: {
                    core.getCam().setPadrao(0);
                    return true;
                }
                default:
                    keyPressed(e);
            }
            return true;


        }
        return true;
    }
}

