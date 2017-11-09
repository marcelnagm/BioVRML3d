package Testes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcel
 */

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.vrml97.VrmlLoader;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.picking.*;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.*;
import javax.vecmath.*;

import vrml3d.core.Objeto;

public class CollisionEx extends Applet {
 private Objeto move;

    public BranchGroup createSceneGraph(final Canvas3D canvas) {
    // Create the root of the branch graph
    BranchGroup objRoot = new BranchGroup();

      // Create a Transformgroup to scale all objects so they
      // appear in the scene.
      TransformGroup objScale = new TransformGroup();
      Transform3D t3d = new Transform3D();
      t3d.setScale(1.0);
      objScale.setTransform(t3d);
      objRoot.addChild(objScale);

    // Create a bounds for the background and behaviors
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10.0);

    // Set up the background
    Color3f bgColor = new Color3f(0.5f, 0.5f, 1.0f);
    Background bg = new Background(bgColor);
    bg.setApplicationBounds(bounds);
     objScale.addChild(bg);

    // Now create a pair of rectangular boxes, each with a collision
    // detection behavior attached.  The behavior will highlight the
    // object when it is in a state of collision.
      Transform3D rotate = new Transform3D();
      Transform3D temp = new Transform3D();
      rotate.rotX(Math.PI/8.0d);
      temp.rotY(-Math.PI/9.0d);
      rotate.mul(temp);
      TransformGroup objView = new TransformGroup(rotate);
      objView.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      objScale.addChild(objView);

      final BranchGroup objTop = new BranchGroup();
      objTop.setCapability(Group.ALLOW_CHILDREN_EXTEND);
      objTop.setCapability(Group.ALLOW_CHILDREN_WRITE);
      objTop.setCapability(Group.ALLOW_CHILDREN_READ);
      objTop.setCapability(BranchGroup.ALLOW_DETACH);
      objView.addChild(objTop);


      Objeto box1 = Gera("C:\\Modelagens TCC\\EMG\\EMG Completo.WRL","EMG");
      box1.setScale(0.5);
      box1.setXYZ(-1, 0, 0);
      Objeto box2 = Gera("C:\\Modelagens TCC\\Avatars\\homem.wrl","Homem");
      box2.setScale(0.5);

      Objeto box3 = Gera("C:\\Modelagens TCC\\Plug.wrl","PLug");
//      box3.setScale(0.5);
      box3.setXYZ(0.5, 0, 0);

//      box1.setBoundsAutoCompute(true);
//      box2.setBoundsAutoCompute(true);
//
//      box1.setCollidable(true);
//      box2.setCollidable(true);
//
//      CollisionDetector cd = new CollisionDetector(box3,box3.getBounds(),box3.getName());
//      box3.addChild(cd);
////
//
//      cd = new CollisionDetector(box2,box2.getBounds(),box2.getName());
//      box2.addChild(cd);


        canvas.addMouseListener(new MouseListener() {

            final PickCanvas pickCanvas = new PickCanvas(canvas, objTop);


            public void mouseClicked(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mousePressed(MouseEvent e) {
//                System.out.println("CLick: " + click);
//                throw new UnsupportedOperationException("Not supported yet.");
                if (e.getButton() == 1 ) {
                    pickCanvas.setMode(pickCanvas.GEOMETRY);
                    pickCanvas.setShapeLocation(e);
                    PickResult result = pickCanvas.pickClosest();
                    Node temp = null;
                    if (result == null) {
//                    move = principal;

                        System.out.println("Nothing picked");
                    } else {
                        for (temp = result.getObject(); !(temp instanceof Objeto); temp = temp.getParent()) {
//                     System.out.println(temp.getClass().getName());
                        }
                        System.out.println(""+temp.getName());
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });


    objTop.addChild(box1);
    objTop.addChild(box2);
    objTop.addChild(box3);
    

      // Behaviors
//  PickRotateBehavior behavior = new PickRotateBehavior(objRoot, canvas, bounds, PickObject.USE_GEOMETRY);
//      objTop.addChild(behavior);
      PickZoomBehavior behavior2 = new PickZoomBehavior(objRoot, canvas,bounds, PickObject.USE_GEOMETRY);
      
      objTop.addChild(behavior2);

      PickTranslateBehavior behavior3 = new PickTranslateBehavior(objRoot, canvas, bounds, PickObject.USE_GEOMETRY);
      objTop.addChild(behavior3);

      // Have Java 3D perform optimizations on this scene graph.
      objRoot.compile();

    return objRoot;
   }


   public CollisionEx() {
    setLayout(new BorderLayout());
    Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    add("Center", c);

    // Create a simple scene and attach it to the virtual universe
    BranchGroup scene = createSceneGraph(c);
    SimpleUniverse u = new SimpleUniverse(c);

      // This will move the ViewPlatform back a bit so the
      // objects in the scene can be viewed.
      u.getViewingPlatform().setNominalViewingTransform();

    u.addBranchGraph(scene);
   }

    public BranchGroup loadFile(String file) //carrega um arquivo de um objeto VRML externo
    {

        System.out.println(file);
        VrmlLoader loader = new VrmlLoader();


        BranchGroup sceneGroup = null;
        Scene scene = null;
        try {
            scene = loader.load(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CollisionEx.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(CollisionEx.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(CollisionEx.class.getName()).log(Level.SEVERE, null, ex);
        }

        sceneGroup = scene.getSceneGroup();

        return sceneGroup;
    }


    public Objeto Gera(String in,String nome) {

        Objeto obj = new Objeto(nome);

        obj.setCapability(obj.ALLOW_CHILDREN_READ);
        obj.setCapability(obj.ALLOW_CHILDREN_WRITE);
        obj.setCapability(obj.ALLOW_PARENT_READ);
        obj.setCapability(obj.ALLOW_TRANSFORM_READ);
        obj.setCapability(obj.ALLOW_TRANSFORM_WRITE);
        obj.setCapability(obj.ALLOW_COLLISION_BOUNDS_READ);
        obj.setCapability(obj.ALLOW_COLLISION_BOUNDS_WRITE);
        obj.setCapability(obj.ALLOW_COLLIDABLE_READ);
        obj.setCapability(obj.ALLOW_COLLIDABLE_WRITE);
        obj.setCapability(obj.ALLOW_AUTO_COMPUTE_BOUNDS_READ);
        obj.setCapability(obj.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        obj.setCapability(obj.ENABLE_COLLISION_REPORTING);
        obj.setCapability(obj.ENABLE_PICK_REPORTING);

        BranchGroup temp = loadFile(in);

        obj.setBranchGroup(temp);
        obj.addChild(temp);



        return obj;
    }


   //
   // The following allows TickTockCollision to be run as an application

   // as well as an applet
   //
   public static void main(String[] args) {
    new MainFrame(new CollisionEx(), 700, 600);
   }
}