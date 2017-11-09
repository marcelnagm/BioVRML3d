/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;

/**
 *
 * @author Marcel
 */
public class Colisao extends Behavior {
//        private WakeupCondition criterion;

    Point3d upp = new Point3d(), low = new Point3d();
    protected WakeupCriterion[] theCriteria;
    /** The OR of the separate criteria. */
    protected WakeupOr oredCriteria;
    /** The shape that is watched for collision. */
    protected Plug collidingShape;
    protected My_VRML core;

    public Colisao(Plug col, My_VRML c, BoundMulti bd) {
        core = c;
        collidingShape = col;
        setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1000));
    }

    public void initialize() {

        theCriteria = new WakeupCriterion[1];
        theCriteria[0] = new WakeupOnElapsedFrames(20);
        oredCriteria = new WakeupOr(theCriteria);
        wakeupOn(oredCriteria);


    }

    protected void onCollide() {
//        System.out.println("COlidiu");

        collidingShape.setXYZ(collidingShape.getX(), collidingShape.getY() + 0.01, collidingShape.getZ());
//        System.out.println("Bounds:" + (BoundingBox) collidingShape.getCollisionBounds());
//        System.out.println("Bounds:" + collidingShape.getXYZ());
//        System.out.println("-----------------------------------------------");
    }

    protected void onMiss() {
//        System.out.println("Escapou");
    }

    public void processStimulus(java.util.Enumeration criteria) {

        while (criteria.hasMoreElements()) {
            WakeupCriterion wakeUp = (WakeupCriterion) criteria.nextElement();
            if (core.getCam().isColocando_plug()) // every N frames, check for a collision
            {
                if (wakeUp instanceof WakeupOnElapsedFrames) {
                    if (collidingShape.get_Bounds_Proibido().intersect(collidingShape.getCollisionBounds())) {
                        onCollide();
                    } else {
                        onMiss();
                    }

                }
            }
        }

        wakeupOn(oredCriteria);
    }
}