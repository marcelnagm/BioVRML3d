/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Testes;

/**
 *
 * @author Marcel
 */
/**********************************************************
 Copyright (C) 2001   Daniel Selman

 First distributed with the book "Java 3D Programming"
 by Daniel Selman and published by Manning Publications.
 http://manning.com/selman

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation, version 2.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 The license can be found on the WWW at:
 http://www.fsf.org/copyleft/gpl.html

 Or by writing to:
 Free Software Foundation, Inc.,
 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

 Authors can be contacted at:
 Daniel Selman: daniel@selman.org

 If you make changes you think others would like, please
 contact one of the authors or someone at the
 www.j3d.org web site.
 **************************************************************/

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Group;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointArray;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;

/**
 * Test harness to investigate the Bounds objects for a Java 3D scenegraph\.
 */
public class BoundsTest extends Java3dApplet {
  private static int m_kWidth = 256;

  private static int m_kHeight = 256;

  public BoundsTest() {
    initJava3d();
  }

  protected double getScale() {
    return 0.5;
  }

  Group createColorCubes() {
    Group group = new Group();

    // defaults
    ColorCube cube1 = new ColorCube(1.0);
    group.addChild(cube1);

    // explicitly set the bounds (box)
    ColorCube cube2 = new ColorCube(2.0);
    cube2.setBoundsAutoCompute(false);
    Bounds bounds = new BoundingBox(new Point3d(-2, -2, -2), new Point3d(2,
        2, 2));
    cube2.setBounds(bounds);
    cube2.setCollisionBounds(bounds);
    group.addChild(cube2);

    // (sphere)
    ColorCube cube3 = new ColorCube(4.0);
    cube3.setBoundsAutoCompute(false);
    bounds = new BoundingSphere(new Point3d(0, 0, 0), 4);
    cube3.setBounds(bounds);
    cube3.setCollisionBounds(bounds);
    group.addChild(cube3);

    // auto compute, manual collision
    ColorCube cube4 = new ColorCube(6.0);
    cube4.setBoundsAutoCompute(true);
    bounds = new BoundingBox(new Point3d(-10, -10, -10), new Point3d(10,
        10, 10));
    cube4.setCollisionBounds(bounds);
    group.addChild(cube4);

    // auto compute both
    ColorCube cube5 = new ColorCube(6.0);
    cube5.setBoundsAutoCompute(true);
    group.addChild(cube5);

    return group;
  }

  protected Group createPoints() {
    Group group = new Group();

    final int kNumPoints = 200;
    final double kRadius = 10.0;
    Point3d points[] = new Point3d[kNumPoints];

    for (int n = 0; n < kNumPoints; n++) {
      double randX = (java.lang.Math.random() * kRadius) - kRadius / 2;
      double randY = (java.lang.Math.random() * kRadius) - kRadius / 2;
      double randZ = (java.lang.Math.random() * kRadius) - kRadius / 2;

      points[n] = new Point3d(randX, randY, randZ);
    }

    PointArray pointArray = new PointArray(points.length,
        GeometryArray.COLOR_4 | GeometryArray.COORDINATES);
    pointArray.setCoordinates(0, points);
    Shape3D shapePoints = new Shape3D(pointArray, new Appearance());

    group.addChild(shapePoints);
    return group;
  }

  protected BranchGroup createSceneBranchGroup() {
    BranchGroup objRoot = super.createSceneBranchGroup();

    // do NOT auto compute bounds for this node
    objRoot.setBoundsAutoCompute(false);

    TransformGroup objTrans = new TransformGroup();
    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

    Transform3D yAxis = new Transform3D();
    Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
        4000, 0, 0, 0, 0, 0);

    RotationInterpolator rotator = new RotationInterpolator(rotationAlpha,
        objTrans, yAxis, 0.0f, (float) Math.PI * 2.0f);

    rotator.setSchedulingBounds(createApplicationBounds());
    objTrans.addChild(rotator);

    objTrans.addChild(createColorCubes());
    objTrans.addChild(createPoints());

    objRoot.addChild(objTrans);

    return objRoot;
  }

  public static void main(String[] args) {
    BoundsTest boundsTest = new BoundsTest();
    boundsTest.saveCommandLineArguments(args);

    new MainFrame(boundsTest, m_kWidth, m_kHeight);
  }
}

/*******************************************************************************
 * Copyright (C) 2001 Daniel Selman
 *
 * First distributed with the book "Java 3D Programming" by Daniel Selman and
 * published by Manning Publications. http://manning.com/selman
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, version 2.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * The license can be found on the WWW at: http://www.fsf.org/copyleft/gpl.html
 *
 * Or by writing to: Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA.
 *
 * Authors can be contacted at: Daniel Selman: daniel@selman.org
 *
 * If you make changes you think others would like, please contact one of the
 * authors or someone at the www.j3d.org web site.
 ******************************************************************************/

//*****************************************************************************
/**
 * Java3dApplet
 *
 * Base class for defining a Java 3D applet. Contains some useful methods for
 * defining views and scenegraphs etc.
 *
 * @author Daniel Selman
 * @version 1.0
 */
//*****************************************************************************

abstract class Java3dApplet extends Applet {
  public static int m_kWidth = 300;

  public static int m_kHeight = 300;

  protected String[] m_szCommandLineArray = null;

  protected VirtualUniverse m_Universe = null;

  protected BranchGroup m_SceneBranchGroup = null;

  protected Bounds m_ApplicationBounds = null;

  //  protected com.tornadolabs.j3dtree.Java3dTree m_Java3dTree = null;

  public Java3dApplet() {
  }

  public boolean isApplet() {
    try {
      System.getProperty("user.dir");
      System.out.println("Running as Application.");
      return false;
    } catch (Exception e) {
    }

    System.out.println("Running as Applet.");
    return true;
  }

  public URL getWorkingDirectory() throws java.net.MalformedURLException {
    URL url = null;

    try {
      File file = new File(System.getProperty("user.dir"));
      System.out.println("Running as Application:");
      System.out.println("   " + file.toURL());
      return file.toURL();
    } catch (Exception e) {
    }

    System.out.println("Running as Applet:");
    System.out.println("   " + getCodeBase());

    return getCodeBase();
  }

  public VirtualUniverse getVirtualUniverse() {
    return m_Universe;
  }

  //public com.tornadolabs.j3dtree.Java3dTree getJ3dTree() {
  //return m_Java3dTree;
  //  }

  public Locale getFirstLocale() {
    java.util.Enumeration e = m_Universe.getAllLocales();

    if (e.hasMoreElements() != false)
      return (Locale) e.nextElement();

    return null;
  }

  protected Bounds getApplicationBounds() {
    if (m_ApplicationBounds == null)
      m_ApplicationBounds = createApplicationBounds();

    return m_ApplicationBounds;
  }

  protected Bounds createApplicationBounds() {
    m_ApplicationBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
        100.0);
    return m_ApplicationBounds;
  }

  protected Background createBackground() {
    Background back = new Background(new Color3f(0.9f, 0.9f, 0.9f));
    back.setApplicationBounds(createApplicationBounds());
    return back;
  }

  public void initJava3d() {
    //  m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
    m_Universe = createVirtualUniverse();

    Locale locale = createLocale(m_Universe);

    BranchGroup sceneBranchGroup = createSceneBranchGroup();

    ViewPlatform vp = createViewPlatform();
    BranchGroup viewBranchGroup = createViewBranchGroup(
        getViewTransformGroupArray(), vp);

    createView(vp);

    Background background = createBackground();

    if (background != null)
      sceneBranchGroup.addChild(background);

    //    m_Java3dTree.recursiveApplyCapability(sceneBranchGroup);
    //  m_Java3dTree.recursiveApplyCapability(viewBranchGroup);

    locale.addBranchGraph(sceneBranchGroup);
    addViewBranchGroup(locale, viewBranchGroup);

    onDoneInit();
  }

  protected void onDoneInit() {
    //  m_Java3dTree.updateNodes(m_Universe);
  }

  protected double getScale() {
    return 1.0;
  }

  public TransformGroup[] getViewTransformGroupArray() {
    TransformGroup[] tgArray = new TransformGroup[1];
    tgArray[0] = new TransformGroup();

    // move the camera BACK a little...
    // note that we have to invert the matrix as
    // we are moving the viewer
    Transform3D t3d = new Transform3D();
    t3d.setScale(getScale());
    t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
    t3d.invert();
    tgArray[0].setTransform(t3d);

    return tgArray;
  }

  protected void addViewBranchGroup(Locale locale, BranchGroup bg) {
    locale.addBranchGraph(bg);
  }

  protected Locale createLocale(VirtualUniverse u) {
    return new Locale(u);
  }

  protected BranchGroup createSceneBranchGroup() {
    m_SceneBranchGroup = new BranchGroup();
    return m_SceneBranchGroup;
  }

  protected View createView(ViewPlatform vp) {
    View view = new View();

    PhysicalBody pb = createPhysicalBody();
    PhysicalEnvironment pe = createPhysicalEnvironment();

    AudioDevice audioDevice = createAudioDevice(pe);

    if (audioDevice != null) {
      pe.setAudioDevice(audioDevice);
      audioDevice.initialize();
    }

    view.setPhysicalEnvironment(pe);
    view.setPhysicalBody(pb);

    if (vp != null)
      view.attachViewPlatform(vp);

    view.setBackClipDistance(getBackClipDistance());
    view.setFrontClipDistance(getFrontClipDistance());

    Canvas3D c3d = createCanvas3D();
    view.addCanvas3D(c3d);
    addCanvas3D(c3d);

    return view;
  }

  protected PhysicalBody createPhysicalBody() {
    return new PhysicalBody();
  }

  protected AudioDevice createAudioDevice(PhysicalEnvironment pe) {
    JavaSoundMixer javaSoundMixer = new JavaSoundMixer(pe);

    if (javaSoundMixer == null)
      System.out.println("create of audiodevice failed");

    return javaSoundMixer;
  }

  protected PhysicalEnvironment createPhysicalEnvironment() {
    return new PhysicalEnvironment();
  }

  protected float getViewPlatformActivationRadius() {
    return 100;
  }

  protected ViewPlatform createViewPlatform() {
    ViewPlatform vp = new ViewPlatform();
    vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
    vp.setActivationRadius(getViewPlatformActivationRadius());

    return vp;
  }

  protected Canvas3D createCanvas3D() {
    GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
    gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
    GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getScreenDevices();

    Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
    c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));

    return c3d;
  }

  protected int getCanvas3dWidth(Canvas3D c3d) {
    return m_kWidth;
  }

  protected int getCanvas3dHeight(Canvas3D c3d) {
    return m_kHeight;
  }

  protected double getBackClipDistance() {
    return 100.0;
  }

  protected double getFrontClipDistance() {
    return 1.0;
  }

  protected BranchGroup createViewBranchGroup(TransformGroup[] tgArray,
      ViewPlatform vp) {
    BranchGroup vpBranchGroup = new BranchGroup();

    if (tgArray != null && tgArray.length > 0) {
      Group parentGroup = vpBranchGroup;
      TransformGroup curTg = null;

      for (int n = 0; n < tgArray.length; n++) {
        curTg = tgArray[n];
        parentGroup.addChild(curTg);
        parentGroup = curTg;
      }

      tgArray[tgArray.length - 1].addChild(vp);
    } else
      vpBranchGroup.addChild(vp);

    return vpBranchGroup;
  }

  protected void addCanvas3D(Canvas3D c3d) {
    setLayout(new BorderLayout());
    add(c3d, BorderLayout.CENTER);
    doLayout();
  }

  protected VirtualUniverse createVirtualUniverse() {
    return new VirtualUniverse();
  }

  protected void saveCommandLineArguments(String[] szArgs) {
    m_szCommandLineArray = szArgs;
  }

  protected String[] getCommandLineArguments() {
    return m_szCommandLineArray;
  }
}