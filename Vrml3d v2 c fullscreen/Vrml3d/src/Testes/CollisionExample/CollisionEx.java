package Testes.CollisionExample;

import java.applet.Applet;
import java.awt.BorderLayout;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.picking.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class CollisionEx extends Applet {
   public BranchGroup createSceneGraph(Canvas3D canvas) {
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
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);

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

      BranchGroup objTop = new BranchGroup();
      objTop.setCapability(Group.ALLOW_CHILDREN_EXTEND);
      objTop.setCapability(Group.ALLOW_CHILDREN_WRITE);
      objTop.setCapability(Group.ALLOW_CHILDREN_READ);
      objTop.setCapability(BranchGroup.ALLOW_DETACH);
      objView.addChild(objTop);

    Group box1 = createBox(1.0, new Vector3d(-0.4, 0.0, 0.0));
    Group box2 = createBox(1.0, new Vector3d( 0.4, 0.0, 0.0));

    objTop.addChild(box1);
    objTop.addChild(box2);

      // Behaviors
  PickRotateBehavior behavior = new PickRotateBehavior(objRoot, canvas, bounds, PickObject.USE_GEOMETRY);
      objTop.addChild(behavior);
      PickZoomBehavior behavior2 = new PickZoomBehavior(objRoot, canvas,bounds, PickObject.USE_GEOMETRY);
      objTop.addChild(behavior2);
      PickTranslateBehavior behavior3 = new PickTranslateBehavior(objRoot, canvas, bounds, PickObject.USE_GEOMETRY);

      objTop.addChild(behavior3);

      // Have Java 3D perform optimizations on this scene graph.
      objRoot.compile();

    return objRoot;
   }


   private Group createBox(double scale, Vector3d pos) {
    // Create a transform group node to scale and position the object.
    Transform3D t = new Transform3D();
    t.set(scale, pos);
    TransformGroup objTrans = new TransformGroup(t);
      objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
      objTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

    // Create a simple shape leaf node and add it to the scene graph
    Shape3D shape = new Box(0.6, 0.6, 0.6);
      shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
      shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
      shape.setCapability(Shape3D.ALLOW_COLLISION_BOUNDS_READ);
      shape.setCapability(Shape3D.ALLOW_COLLISION_BOUNDS_WRITE);
    objTrans.addChild(shape);

    // Create a new ColoringAttributes object for the shape's
    // appearance and make it writable at runtime.
    Appearance app = shape.getAppearance();
    ColoringAttributes ca = new ColoringAttributes();
    ca.setColor(0.0f, 0.0f, 0.0f);
    app.setCapability(app.ALLOW_COLORING_ATTRIBUTES_WRITE);
    app.setColoringAttributes(ca);
      PolygonAttributes attr = new PolygonAttributes();
      attr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
      attr.setCullFace(PolygonAttributes.CULL_NONE);
      app.setPolygonAttributes(attr);

    // Create a new Behavior object that will perform the collision
    // detection on the specified object, and add it into
    // the scene graph.
    CollisionDetector cd = new CollisionDetector(shape);
//    BoundingBox bounds = new BoundingBox(new Point3d(-0.1,-0.1,-0.1), new Point3d( 0.1, 0.1, 0.1));
    BoundMulti multi = new BoundMulti();
    multi.AddBounds(new BoundingBox(new Point3d(-0.1,-0.1,-0.1), new Point3d( 0.01, 0.01, 0.01)));

    cd.setSchedulingBounds(multi);
//    cd.setSchedulingBounds(bounds);

    // Add the behavior to the scene graph
    objTrans.addChild(cd);


      return objTrans;
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


   //
   // The following allows TickTockCollision to be run as an application

   // as well as an applet
   //
   public static void main(String[] args) {
    new MainFrame(new CollisionEx(), 700, 600);
   }
}