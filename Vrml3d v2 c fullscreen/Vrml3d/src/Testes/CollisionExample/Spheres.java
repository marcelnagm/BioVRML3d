/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Testes.CollisionExample;

/**
 *
 * @author Marcel
 */
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import javax.swing.*;
import java.awt.Container;
import javax.media.j3d.Shape3D;
import javax.media.j3d.GeometryArray;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Spheres extends JFrame
{

  private java.util.List<Sphere> movingSpheres = new java.util.ArrayList<Sphere>();
  private java.util.List<Float> startX = new java.util.ArrayList<Float>();
  private java.util.List<Float> startY = new java.util.ArrayList<Float>();
  private java.util.List<Float> startZ = new java.util.ArrayList<Float>();
  private java.util.List<Float> moveY = new java.util.ArrayList<Float>();
  private java.util.List<Float> moveX = new java.util.ArrayList<Float>();
  private java.util.List<Float> moveZ = new java.util.ArrayList<Float>();

  private java.util.List<TransformGroup> transGroups = new java.util.ArrayList<TransformGroup>();
  private java.util.List<Transform3D> translates = new java.util.ArrayList<Transform3D>();

  private BranchGroup scene = new BranchGroup();
  private Appearance ap = new Appearance();
  private GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
  private Canvas3D c = new Canvas3D(config);
  private SimpleUniverse u = new SimpleUniverse(c);



  public Spheres()
  {
     super("Colliding Spheres");
     setLayout(new BorderLayout());
     add("Center", c);
     setSize(400,400);
     setResizable(false);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     create();
     time.start();
  }


  public void create()
  {
     scene = createSceneGraph();
     scene.compile();
     u.getViewingPlatform().setNominalViewingTransform();
     u.addBranchGraph(scene);
     BranchGroup box = new BranchGroup();
     u.addBranchGraph(box);
  }



  public void setUp(BranchGroup bg,int x)
  {
     startX.add(-0.975f+randomGen(1.95));  //Creates start Positions
     startY.add(-0.74f+randomGen(1.48));
     startZ.add(0f);
     moveX.add(-0.01f+ randomGen(0.02f));  // Creates direction to move to
     moveY.add(-0.01f+ randomGen(0.02f));
     moveZ.add(0f);

     movingSpheres.add(new Sphere(0.05f, ap)); //Adds a Sphere to list

     transGroups.add(new TransformGroup());
     transGroups.get(x).setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

     translates.add(new Transform3D());
     translates.get(x).setTranslation(new Vector3f(startX.get(x),startY.get(x),startZ.get(x)));

     transGroups.get(x).setTransform(translates.get(x));
     transGroups.get(x).addChild(movingSpheres.get(x));

     bg.addChild(transGroups.get(x));
  }


   public BranchGroup createSceneGraph()
  {
     BranchGroup branch1 = new BranchGroup();

     for(int i=0;i<4;i++)//creates mulitple spheres
     {
         setUp(branch1, i);
     }

     return branch1;
  }




  public Timer time = new Timer(9, new ActionListener()
  {
     public void actionPerformed(ActionEvent arg0)
     {
          //Loops and moves the spheres
          for(int i=0;i<movingSpheres.size();i++)
          {
            //adds to the vectors
            startX.set(i, startX.get(i)+moveX.get(i));
            startY.set(i, startY.get(i)+moveY.get(i));
            startZ.set(i, startZ.get(i)+moveZ.get(i));

          if(startX.get(i) < -0.975 || startX.get(i) > 0.975)
          {
               moveX.set(i, -moveX.get(i));
          }
          if(startY.get(i) < -0.74 || startY.get(i) > 0.74)
          {
               moveY.set(i, -moveY.get(i));
          }
          if(startZ.get(i) < -1 || startZ.get(i) > 0)
          {
               moveZ.set(i, -moveZ.get(i));
          }

            translates.get(i).setTranslation(new Vector3f(startX.get(i),startY.get(i),startZ.get(i)));
            transGroups.get(i).setTransform(translates.get(i));
          }
     }
  });


  public float randomGen(double x)//Returns a positive random Number between 1 and x
  {
      return (float) (Math.random() * x);//Casts a random double into an integer
  }

  public static void main(String[] args)
  {
      Spheres window = new Spheres();
      window.setVisible(true);
  }



}
