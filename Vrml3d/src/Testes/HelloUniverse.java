/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Testes;

/**
 *
 * @author Marcel
 */


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.keyboard.*;

public class HelloUniverse extends Applet {

  
    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        // Create the transform group node and initialize it to the
        // identity.  Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime.  Add it to the
        // root of the subgraph.
//        TransformGroup objTrans = new TransformGroup();
//        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        objRoot.addChild(objTrans);

        // Create a simple shape leaf node, add it to the scene graph.
//        objTrans.addChild(new ColorCube(1.0));

        // Create a new Behavior object that will perform the desired
        // operation on the specified transform object and add it into
        // the scene graph.
//        Transform3D yAxis = new Transform3D();
//        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
//                                        0, 0,
//                                        4000, 0, 0,
//                                        0, 0, 0);
//
//        RotationInterpolator rotator =
//            new RotationInterpolator(rotationAlpha, objTrans, yAxis,
//                                     0.0f, (float) Math.PI*2.0f);
//        BoundingSphere bounds =
//            new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//        rotator.setSchedulingBounds(bounds);
//        objTrans.addChild(rotator);

        // Have Java 3D perform optimizations on this scene graph.
//        objRoot.compile();
//
        return objRoot;
    }

    public HelloUniverse() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D c = new Canvas3D(config);
        add("Center", c);

        // Create a simple scene and attach it to the virtual universe
        BranchGroup scene = createSceneGraph();
        SimpleUniverse u = new SimpleUniverse(c);
        u.getViewingPlatform().getViewPlatform().setActivationRadius(0.3f);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

        BranchGroup b = new BranchGroup();
        KeyNavigatorBehavior key = new
KeyNavigatorBehavior(u.getViewingPlatform().getViewPlatformTransform());
        key.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100000.0));
        b.addChild(key);

//        BoundingSphere bnd = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 0.5);
         
//        b.addChild(coll);
//        b.addChild(box);

        u.addBranchGraph(scene);
        u.addBranchGraph(b);
        u.getViewingPlatform().setNominalViewingTransform();
    }

    //
    // The following allows HelloUniverse to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
        new MainFrame(new HelloUniverse(), 256, 256);
    }
}

