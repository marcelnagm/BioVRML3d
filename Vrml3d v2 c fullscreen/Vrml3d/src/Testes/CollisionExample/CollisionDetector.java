package Testes.CollisionExample;

/*
 * @(#)CollisionDetector.java 1.4 98/02/20 14:30:17
 *
 */

import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;

public class CollisionDetector extends Behavior {
   private static final Color3f highlightColor = new Color3f(1.0f, 0.0f, 0.0f);
   private static final ColoringAttributes highlight = new ColoringAttributes(highlightColor, ColoringAttributes.SHADE_GOURAUD);
   private boolean inCollision = false;
   private Shape3D shape;
   private ColoringAttributes shapeColoring;
   private Appearance shapeAppearance;

   private WakeupOnCollisionEntry wEnter;
   private WakeupOnCollisionExit wExit;


   public CollisionDetector(Shape3D s) {
    shape = s;
    shapeAppearance = shape.getAppearance();
    shapeColoring = shapeAppearance.getColoringAttributes();
    inCollision = false;
   }


   public void initialize() {
    wEnter = new WakeupOnCollisionEntry(shape,WakeupOnCollisionEntry.USE_BOUNDS);
      wExit = new WakeupOnCollisionExit(shape,WakeupOnCollisionEntry.USE_BOUNDS);
    wakeupOn(wEnter);
   }

   public void processStimulus(Enumeration criteria) {
    inCollision = !inCollision;

    if (inCollision) {
       shapeAppearance.setColoringAttributes(highlight);
       wakeupOn(wExit);
    } else {
       shapeAppearance.setColoringAttributes(shapeColoring);
       wakeupOn(wEnter);
    }
   }

}