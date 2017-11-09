/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Marcel
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.event.*;
import java.util.Enumeration;

//   SimpleBehaviorApp renders a single ColorCube
//   that rotates when any key is pressed.

public class SimpleBehaviorApp extends Applet {

    public class SimpleBehavior extends Behavior{

        private TransformGroup targetTG;
        private Transform3D rotation = new Transform3D();
        private double angle = 0.0;

        // create SimpleBehavior
        SimpleBehavior(TransformGroup targetTG){
            this.targetTG = targetTG;
        }

        // initialize the Behavior
        //     set initial wakeup condition
        //     called when behavior beacomes live
        public void initialize(){
            // set initial wakeup condition
            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
        }

        // behave
        // called by Java 3D when appropriate stimulus occures
        public void processStimulus(Enumeration criteria){
            // decode event

            // do what is necessary
            angle += 0.1;
            rotation.rotY(angle);
            targetTG.setTransform(rotation);
            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
        }

    } // end of class SimpleBehavior

    public BranchGroup createSceneGraph() {
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

        TransformGroup objRotate = new TransformGroup();
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

	objRoot.addChild(objRotate);
	objRotate.addChild(new ColorCube(0.4));

        SimpleBehavior myRotationBehavior = new SimpleBehavior(objRotate);
        myRotationBehavior.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myRotationBehavior);

	// Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

	return objRoot;
    } // end of CreateSceneGraph method of SimpleBehaviorApp

    // Create a simple scene and attach it to the virtual universe

    public SimpleBehaviorApp() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    } // end of SimpleBehaviorApp (constructor)
    //  The following allows this to be run as an application
    //  as well as an applet

    public static void main(String[] args) {
        System.out.print("SimpleBehaviorApp.java \n- a demonstration of creating a simple");
        System.out.println("behavior class to provide interaction in a Java 3D scene.");
        System.out.println("When the app loads, press any key to make the cube rotate.");
        System.out.println("This is a simple example progam from The Java 3D API Tutorial.");
        System.out.println("The Java 3D Tutorial is available on the web at:");
        System.out.println("http://java.sun.com/products/java-media/3D/collateral");
        Frame frame = new MainFrame(new SimpleBehaviorApp(), 256, 256);
    } // end of main (method of SimpleBehaviorApp)

} // end of class SimpleBehaviorApp

