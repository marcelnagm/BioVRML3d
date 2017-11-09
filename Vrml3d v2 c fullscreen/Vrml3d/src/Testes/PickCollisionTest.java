///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package Testes;
//
///**
// *
// * @author Marcel
// */
//import java.applet.Applet;
//import java.awt.BorderLayout;
//import java.awt.GraphicsConfigTemplate;
//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.net.URL;
//
//import javax.media.j3d.AmbientLight;
//import javax.media.j3d.Appearance;
//import javax.media.j3d.AudioDevice;
//import javax.media.j3d.Background;
//import javax.media.j3d.Behavior;
//import javax.media.j3d.BoundingSphere;
//import javax.media.j3d.Bounds;
//import javax.media.j3d.BranchGroup;
//import javax.media.j3d.Canvas3D;
//import javax.media.j3d.DirectionalLight;
//import javax.media.j3d.GraphicsConfigTemplate3D;
//import javax.media.j3d.Group;
//import javax.media.j3d.Locale;
//import javax.media.j3d.Material;
//import javax.media.j3d.Node;
//import javax.media.j3d.PhysicalBody;
//import javax.media.j3d.PhysicalEnvironment;
//import javax.media.j3d.PickBounds;
//import javax.media.j3d.PolygonAttributes;
//import javax.media.j3d.SceneGraphObject;
//import javax.media.j3d.Transform3D;
//import javax.media.j3d.TransformGroup;
//import javax.media.j3d.View;
//import javax.media.j3d.ViewPlatform;
//import javax.media.j3d.VirtualUniverse;
//import javax.media.j3d.WakeupCondition;
//import javax.media.j3d.WakeupCriterion;
//import javax.media.j3d.WakeupOnElapsedFrames;
//import javax.media.j3d.WakeupOr;
//import javax.vecmath.Color3f;
//import javax.vecmath.Point3d;
//import javax.vecmath.Vector3d;
//
//import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
//import com.sun.j3d.utils.applet.MainFrame;
//import com.sun.j3d.utils.geometry.ColorCube;
//import com.sun.j3d.utils.geometry.Sphere;
//import com.sun.j3d.utils.picking.PickResult;
//import com.sun.j3d.utils.picking.PickTool;
//
///**
// * This example creates a large hollow box (out of ColorCubes, one for each side
// * of the box). Within the box, four Spheres are created. Each Sphere has a
// * behavior attached which detects collisions with the sides of the box, and the
// * other Spheres. When a collision is detected the trajectory of the Sphere is
// * reversed and the color of the Sphere changed. When a collision is not
// * detected the Sphere is advanced along its current trajectory.
// */
//public class PickCollisionTest extends Java3dApplet implements ActionListener {
//  private static int m_kWidth = 400;
//
//  private static int m_kHeight = 400;
//
//  private static final int boxSize = 10;
//
//  public PickCollisionTest() {
//    initJava3d();
//  }
//
//  public void actionPerformed(ActionEvent event) {
//  }
//
//  protected double getScale() {
//    return 0.5;
//  }
//
//  // method to recursively set the user data for objects in the scenegraph
//  // tree
//  // we also set the capabilites on Shape3D and Morph objects required by the
//  // PickTool
//  void recursiveSetUserData(SceneGraphObject root, Object value) {
//    root.setUserData(value);
//
//    // recursively process group
//    if (root instanceof Group) {
//      Group g = (Group) root;
//
//      // recurse on child nodes
//      java.util.Enumeration enumKids = g.getAllChildren();
//
//      while (enumKids.hasMoreElements() != false)
//        recursiveSetUserData((SceneGraphObject) enumKids.nextElement(),
//            value);
//    }
//  }
//
//  protected void addCube(BranchGroup bg, double x, double y, double z,
//      double sx, double sy, double sz, String name, boolean wireframe) {
//    // create four ColorCube objects
//    TransformGroup cubeTg = new TransformGroup();
//    Transform3D t3d = new Transform3D();
//    t3d.setTranslation(new Vector3d(x, y, z));
//    t3d.setScale(new Vector3d(sx, sy, sz));
//    cubeTg.setTransform(t3d);
//    ColorCube cube = new ColorCube(1.0);
//
//    // we have to make the front face wireframe
//    // or we can't see inside the box!
//    if (wireframe) {
//      Appearance app = new Appearance();
//      app.setPolygonAttributes(new PolygonAttributes(
//          PolygonAttributes.POLYGON_LINE,
//          PolygonAttributes.CULL_NONE, 0));
//      cube.setAppearance(app);
//    }
//
//    cubeTg.addChild(cube);
//    recursiveSetUserData(cubeTg, name);
//
//    bg.addChild(cubeTg);
//  }
//
//  protected void addSphere(BranchGroup bg, double x, double y, double z,
//      Vector3d incVector, String name) {
//    Appearance app = new Appearance();
//
//    TransformGroup sphereTg = new TransformGroup();
//    Transform3D t3d = new Transform3D();
//    t3d.setTranslation(new Vector3d(x, y, z));
//    sphereTg.setTransform(t3d);
//
//    sphereTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//    sphereTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//
//    sphereTg.addChild(new Sphere(1, app));
//    bg.addChild(sphereTg);
//    recursiveSetUserData(sphereTg, name);
//
//    // create the collision behaviour
//    CollisionBehavior collisionBehavior = new CollisionBehavior(bg,
//        sphereTg, app, new Vector3d(x, y, z), incVector);
//    collisionBehavior.setSchedulingBounds(getApplicationBounds());
//    bg.addChild(collisionBehavior);
//  }
//
//  protected BranchGroup createSceneBranchGroup() {
//    BranchGroup objRoot = super.createSceneBranchGroup();
//
//    Bounds lightBounds = getApplicationBounds();
//
//    AmbientLight ambLight = new AmbientLight(true, new Color3f(1.0f, 1.0f,
//        1.0f));
//    ambLight.setInfluencingBounds(lightBounds);
//    objRoot.addChild(ambLight);
//
//    DirectionalLight headLight = new DirectionalLight();
//    headLight.setInfluencingBounds(lightBounds);
//    objRoot.addChild(headLight);
//
//    // create ColorCube objects, one for each side of a cube
//    addCube(objRoot, 0, boxSize, 0, boxSize, 0.1, boxSize, "Top", false);
//    addCube(objRoot, 0, -boxSize, 0, boxSize, 0.1, boxSize, "Bottom", false);
//    addCube(objRoot, boxSize, 0, 0, 0.1, boxSize, boxSize, "Right", false);
//    addCube(objRoot, -boxSize, 0, 0, 0.1, boxSize, boxSize, "Left", false);
//    addCube(objRoot, 0, 0, -boxSize, boxSize, boxSize, 0.1, "Back", false);
//    addCube(objRoot, 0, 0, boxSize, boxSize, boxSize, 0.1, "Front", true);
//
//    // create the spheres
//    addSphere(objRoot, 0, 3, 4, new Vector3d(0.1, 0.3, 0.1), "Sphere 1");
////    addSphere(objRoot, 3, 0, -2, new Vector3d(0.4, 0.1, 0.2), "Sphere 2");
////    addSphere(objRoot, 0, -3, 0, new Vector3d(0.2, 0.2, 0.6), "Sphere 3");
////    addSphere(objRoot, -3, 0, -4, new Vector3d(0.1, 0.6, 0.3), "Sphere 4");
//
//    return objRoot;
//  }
//
//  public static void main(String[] args) {
//    PickCollisionTest pickCollisionTest = new PickCollisionTest();
//    pickCollisionTest.saveCommandLineArguments(args);
//
//    new MainFrame(pickCollisionTest, m_kWidth, m_kHeight);
//  }
//}
//
///**
// * This behavior detects collisions between the branch of a scene, and a
// * collision object. The Java 3D 1.2 picking utilities are used to implement
// * collision detection. The objects in the scene that are collidable should have
// * their user data set. The collision object's user data is used to ignore
// * collisions between the object and itself.
// *
// * When a collision is detected the trajectory of the collision object is
// * reversed (plus a small random factor) and an Appearance object is modified.
// *
// * When a collision is not detected the collision object is moved along its
// * current trajectory and the Appearance color is reset.
// *
// * Colision checking is run after every frame.
// *
// */
//
//class CollisionBehavior extends Behavior {
//  // the wake up condition for the behavior
//  protected WakeupCondition m_WakeupCondition = null;
//
//  // how often we check for a collision
//  private static final int ELAPSED_FRAME_COUNT = 1;
//
//  // the branch that we check for collisions
//  private BranchGroup pickRoot = null;
//
//  // the collision object that we are controlling
//  private TransformGroup collisionObject = null;
//
//  // the appearance object that we are controlling
//  private Appearance objectAppearance = null;
//
//  // cached PickBounds object used for collision detection
//  private PickBounds pickBounds = null;
//
//  // cached Material objects that define the collided and missed colors
//  private Material collideMaterial = null;
//
//  private Material missMaterial = null;
//
//  // the current trajectory of the object
//  private Vector3d incrementVector = null;
//
//  // the current position of the object
//  private Vector3d positionVector = null;
//
//  public CollisionBehavior(BranchGroup pickRoot,
//      TransformGroup collisionObject, Appearance app, Vector3d posVector,
//      Vector3d incVector) {
//    // save references to the objects
//    this.pickRoot = pickRoot;
//    this.collisionObject = collisionObject;
//    this.objectAppearance = app;
//
//    incrementVector = incVector;
//    positionVector = posVector;
//
//    // create the WakeupCriterion for the behavior
//    WakeupCriterion criterionArray[] = new WakeupCriterion[1];
//    criterionArray[0] = new WakeupOnElapsedFrames(ELAPSED_FRAME_COUNT);
//
//    objectAppearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
//
//    collisionObject.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//    collisionObject.setCapability(Node.ALLOW_BOUNDS_READ);
//
//    // save the WakeupCriterion for the behavior
//    m_WakeupCondition = new WakeupOr(criterionArray);
//  }
//
//  public void initialize() {
//    // apply the initial WakeupCriterion
//    wakeupOn(m_WakeupCondition);
//
//    Color3f objColor = new Color3f(1.0f, 0.1f, 0.2f);
//    Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
//    collideMaterial = new Material(objColor, black, objColor, black, 80.0f);
//
//    objColor = new Color3f(0.0f, 0.1f, 0.8f);
//    missMaterial = new Material(objColor, black, objColor, black, 80.0f);
//
//    objectAppearance.setMaterial(missMaterial);
//  }
//
//  protected void onCollide() {
//    objectAppearance.setMaterial(collideMaterial);
//
//    incrementVector.negate();
//
//    // add a little randomness
//    incrementVector.x += (Math.random() - 0.5) / 20.0;
//    incrementVector.y += (Math.random() - 0.5) / 20.0;
//    incrementVector.z += (Math.random() - 0.5) / 20.0;
//  }
//
//  protected void onMiss() {
//    objectAppearance.setMaterial(missMaterial);
//  }
//
//  protected void moveCollisionObject() {
//    Transform3D t3d = new Transform3D();
//
//    positionVector.add(incrementVector);
//    t3d.setTranslation(positionVector);
//
//    collisionObject.setTransform(t3d);
//  }
//
//  public boolean isCollision(PickResult[] resultArray) {
//    if (resultArray == null || resultArray.length == 0)
//      return false;
//
//    // we use the user data on the nodes to ignore the
//    // case of the collisionObject having collided with itself!
//    // the user data also gives us a good mechanism for reporting the
//    // collisions.
//    for (int n = 0; n < resultArray.length; n++) {
//      Object userData = resultArray[n].getObject().getUserData();
//
//      if (userData != null && userData instanceof String) {
//        // check that we are not colliding with ourselves...
//        if (((String) userData).equals((String) collisionObject
//            .getUserData()) == false) {
//          System.out.println("Collision between: "
//              + collisionObject.getUserData() + " and: "
//              + userData);
//          return true;
//        }
//      }
//    }
//
//    return false;
//  }
//
//  public void processStimulus(java.util.Enumeration criteria) {
//    while (criteria.hasMoreElements()) {
//      WakeupCriterion wakeUp = (WakeupCriterion) criteria.nextElement();
//
//      // every N frames, check for a collision
//      if (wakeUp instanceof WakeupOnElapsedFrames) {
//        // create a PickBounds
//        PickTool pickTool = new PickTool(pickRoot);
//        pickTool.setMode(PickTool.BOUNDS);
//
//        BoundingSphere bounds = (BoundingSphere) collisionObject
//            .getBounds();
//        pickBounds = new PickBounds(new BoundingSphere(new Point3d(
//            positionVector.x, positionVector.y, positionVector.z),
//            bounds.getRadius()));
//        pickTool.setShape(pickBounds, new Point3d(0, 0, 0));
//        PickResult[] resultArray = pickTool.pickAll();
//
//        if (isCollision(resultArray))
//          onCollide();
//        else
//          onMiss();
//
//        moveCollisionObject();
//      }
//    }
//
//    // assign the next WakeUpCondition, so we are notified again
//    wakeupOn(m_WakeupCondition);
//  }
//}
//
///*******************************************************************************
// * Copyright (C) 2001 Daniel Selman
// *
// * First distributed with the book "Java 3D Programming" by Daniel Selman and
// * published by Manning Publications. http://manning.com/selman
// *
// * This program is free software; you can redistribute it and/or modify it under
// * the terms of the GNU General Public License as published by the Free Software
// * Foundation, version 2.
// *
// * This program is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
// * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
// * details.
// *
// * The license can be found on the WWW at: http://www.fsf.org/copyleft/gpl.html
// *
// * Or by writing to: Free Software Foundation, Inc., 59 Temple Place - Suite
// * 330, Boston, MA 02111-1307, USA.
// *
// * Authors can be contacted at: Daniel Selman:  daniel@selman.orgThis e-mail address is being protected from spam bots, you need JavaScript enabled to view it
// *
// * If you make changes you think others would like, please contact one of the
// * authors or someone at the www.j3d.org web site.
// ******************************************************************************/
//
////*****************************************************************************
///**
// * Java3dApplet
// *
// * Base class for defining a Java 3D applet. Contains some useful methods for
// * defining views and scenegraphs etc.
// *
// * @author Daniel Selman
// * @version 1.0
// */
////*****************************************************************************
//
//abstract class Java3dApplet extends Applet {
//  public static int m_kWidth = 300;
//
//  public static int m_kHeight = 300;
//
//  protected String[] m_szCommandLineArray = null;
//
//  protected VirtualUniverse m_Universe = null;
//
//  protected BranchGroup m_SceneBranchGroup = null;
//
//  protected Bounds m_ApplicationBounds = null;
//
//  //  protected com.tornadolabs.j3dtree.Java3dTree m_Java3dTree = null;
//
//  public Java3dApplet() {
//  }
//
//  public boolean isApplet() {
//    try {
//      System.getProperty("user.dir");
//      System.out.println("Running as Application.");
//      return false;
//    } catch (Exception e) {
//    }
//
//    System.out.println("Running as Applet.");
//    return true;
//  }
//
//  public URL getWorkingDirectory() throws java.net.MalformedURLException {
//    URL url = null;
//
//    try {
//      File file = new File(System.getProperty("user.dir"));
//      System.out.println("Running as Application:");
//      System.out.println("   " + file.toURL());
//      return file.toURL();
//    } catch (Exception e) {
//    }
//
//    System.out.println("Running as Applet:");
//    System.out.println("   " + getCodeBase());
//
//    return getCodeBase();
//  }
//
//  public VirtualUniverse getVirtualUniverse() {
//    return m_Universe;
//  }
//
//  //public com.tornadolabs.j3dtree.Java3dTree getJ3dTree() {
//  //return m_Java3dTree;
//  //  }
//
//  public Locale getFirstLocale() {
//    java.util.Enumeration e = m_Universe.getAllLocales();
//
//    if (e.hasMoreElements() != false)
//      return (Locale) e.nextElement();
//
//    return null;
//  }
//
//  protected Bounds getApplicationBounds() {
//    if (m_ApplicationBounds == null)
//      m_ApplicationBounds = createApplicationBounds();
//
//    return m_ApplicationBounds;
//  }
//
//  protected Bounds createApplicationBounds() {
//    m_ApplicationBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
//        100.0);
//    return m_ApplicationBounds;
//  }
//
//  protected Background createBackground() {
//    Background back = new Background(new Color3f(0.9f, 0.9f, 0.9f));
//    back.setApplicationBounds(createApplicationBounds());
//    return back;
//  }
//
//  public void initJava3d() {
//    //  m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
//    m_Universe = createVirtualUniverse();
//
//    Locale locale = createLocale(m_Universe);
//
//    BranchGroup sceneBranchGroup = createSceneBranchGroup();
//
//    ViewPlatform vp = createViewPlatform();
//    BranchGroup viewBranchGroup = createViewBranchGroup(
//        getViewTransformGroupArray(), vp);
//
//    createView(vp);
//
//    Background background = createBackground();
//
//    if (background != null)
//      sceneBranchGroup.addChild(background);
//
//    //    m_Java3dTree.recursiveApplyCapability(sceneBranchGroup);
//    //  m_Java3dTree.recursiveApplyCapability(viewBranchGroup);
//
//    locale.addBranchGraph(sceneBranchGroup);
//    addViewBranchGroup(locale, viewBranchGroup);
//
//    onDoneInit();
//  }
//
//  protected void onDoneInit() {
//    //  m_Java3dTree.updateNodes(m_Universe);
//  }
//
//  protected double getScale() {
//    return 1.0;
//  }
//
//  public TransformGroup[] getViewTransformGroupArray() {
//    TransformGroup[] tgArray = new TransformGroup[1];
//    tgArray[0] = new TransformGroup();
//
//    // move the camera BACK a little...
//    // note that we have to invert the matrix as
//    // we are moving the viewer
//    Transform3D t3d = new Transform3D();
//    t3d.setScale(getScale());
//    t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
//    t3d.invert();
//    tgArray[0].setTransform(t3d);
//
//    return tgArray;
//  }  protected void addViewBranchGroup(Locale locale, BranchGroup bg) {
//    locale.addBranchGraph(bg);
//  }
//
//  protected Locale createLocale(VirtualUniverse u) {
//    return new Locale(u);
//  }
//
//  protected BranchGroup createSceneBranchGroup() {
//    m_SceneBranchGroup = new BranchGroup();
//    return m_SceneBranchGroup;
//  }
//
//  protected View createView(ViewPlatform vp) {
//    View view = new View();
//
//    PhysicalBody pb = createPhysicalBody();
//    PhysicalEnvironment pe = createPhysicalEnvironment();
//
//    AudioDevice audioDevice = createAudioDevice(pe);
//
//    if (audioDevice != null) {
//      pe.setAudioDevice(audioDevice);
//      audioDevice.initialize();
//    }
//
//    view.setPhysicalEnvironment(pe);
//    view.setPhysicalBody(pb);
//
//    if (vp != null)
//      view.attachViewPlatform(vp);
//
//    view.setBackClipDistance(getBackClipDistance());
//    view.setFrontClipDistance(getFrontClipDistance());
//
//    Canvas3D c3d = createCanvas3D();
//    view.addCanvas3D(c3d);
//    addCanvas3D(c3d);
//
//    return view;
//  }
//
//  protected PhysicalBody createPhysicalBody() {
//    return new PhysicalBody();
//  }
//
//  protected AudioDevice createAudioDevice(PhysicalEnvironment pe) {
//    JavaSoundMixer javaSoundMixer = new JavaSoundMixer(pe);
//
//    if (javaSoundMixer == null)
//      System.out.println("create of audiodevice failed");
//
//    return javaSoundMixer;
//  }
//
//  protected PhysicalEnvironment createPhysicalEnvironment() {
//    return new PhysicalEnvironment();
//  }
//
//  protected float getViewPlatformActivationRadius() {
//    return 100;
//  }
//
//  protected ViewPlatform createViewPlatform() {
//    ViewPlatform vp = new ViewPlatform();
//    vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
//    vp.setActivationRadius(getViewPlatformActivationRadius());
//
//    return vp;
//  }
//
//  protected Canvas3D createCanvas3D() {
//    GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
//    gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
//    GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
//        .getScreenDevices();
//
//    Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
//    c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));
//
//    return c3d;
//  }
//
//  protected int getCanvas3dWidth(Canvas3D c3d) {
//    return m_kWidth;
//  }
//
//  protected int getCanvas3dHeight(Canvas3D c3d) {
//    return m_kHeight;
//  }
//
//  protected double getBackClipDistance() {
//    return 100.0;
//  }
//
//  protected double getFrontClipDistance() {
//    return 1.0;
//  }
//
//  protected BranchGroup createViewBranchGroup(TransformGroup[] tgArray,
//      ViewPlatform vp) {
//    BranchGroup vpBranchGroup = new BranchGroup();
//
//    if (tgArray != null && tgArray.length > 0) {
//      Group parentGroup = vpBranchGroup;
//      TransformGroup curTg = null;
//
//      for (int n = 0; n < tgArray.length; n++) {
//        curTg = tgArray[n];
//        parentGroup.addChild(curTg);
//        parentGroup = curTg;
//      }
//
//      tgArray[tgArray.length - 1].addChild(vp);
//    } else
//      vpBranchGroup.addChild(vp);
//
//    return vpBranchGroup;
//  }
//
//  protected void addCanvas3D(Canvas3D c3d) {
//    setLayout(new BorderLayout());
//    add(c3d, BorderLayout.CENTER);
//    doLayout();
//  }
//
//  protected VirtualUniverse createVirtualUniverse() {
//    return new VirtualUniverse();
//  }
//
//  protected void saveCommandLineArguments(String[] szArgs) {
//    m_szCommandLineArray = szArgs;
//  }
//
//  protected String[] getCommandLineArguments() {
//    return m_szCommandLineArray;
//  }
//}
