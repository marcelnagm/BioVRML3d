/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import vrml3d.core.*;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.PickBounds;
import javax.media.j3d.SceneGraphPath;
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

    private BoundMulti bounds;
//        private boolean collided;
    protected WakeupCriterion[] theCriteria;
    /** The OR of the separate criteria. */
    protected WakeupOr oredCriteria;
    /** The shape that is watched for collision. */
    protected Plug collidingShape;
//    protected String Shape;
    int i = 0;
    private PickBounds pickBounds = null;
    private BranchGroup pickRoot = null;
    private My_VRML core;

    public Colisao(Plug col,My_VRML c,BoundMulti bd) {
        collidingShape = col;
    
        core = c;
        setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1000));
    }

    public void initialize() {
//        WakeupOnCollisionEntry cl = new WakeupOnCollisionEntry(collidingShape);
//        cl.getTriggeringPath();
        theCriteria = new WakeupCriterion[1];
        theCriteria[0] = new WakeupOnElapsedFrames(2);
        oredCriteria = new WakeupOr(theCriteria);
        wakeupOn(oredCriteria);

        
    }

    protected void onCollide() {

        System.out.println("COlidiu");
//        collidingShape.setY(collidingShape.getY() + 0.01);
 
        
    }

    protected void onMiss() {
        System.out.println("Escapou");
       
    }

    protected void moveCollisionObject() {
//        System.out.println("MOvendo o safado");
    }

    public boolean isCollision(PickResult[] resultArray) {
int i =0;
        if (resultArray == null || resultArray.length ==4) {
            return false;
        }else{
              Node temp;              
            for (int j = 0; j < resultArray.length; j++) {
                   temp =   resultArray[i].getObject();
//
                    for (temp = temp.getParent(); !(temp instanceof Objeto); temp = temp.getParent()) {
//                        System.out.println(temp.getClass().getName());
                    }
                   
//                       Objeto t = (Objeto) temp;
//                       if(!t.equals(collidingShape))
//                        System.out.println(t.getName());
                   
            }
//              System.out.println("Lenght:"+resultArray.length);
//              System.out.println("Lenght:"+i);
//            System.out.println("----------------------------------------------------");

            return true;

        }


    }

    public void processStimulus(java.util.Enumeration criteria) {
        while (criteria.hasMoreElements()) {
            WakeupCriterion wakeUp = (WakeupCriterion) criteria.nextElement();

            // every N frames, check for a collision
            if (wakeUp instanceof WakeupOnElapsedFrames) {
                // create a PickBounds
                PickTool pickTool = new PickTool(core.getPrincipal().getBranchGroup());
                pickTool.setMode(PickTool.BOUNDS);
                pickBounds = new PickBounds(collidingShape.getCollisionBounds());
                pickTool.setShape(pickBounds, new Point3d(collidingShape.getX(), collidingShape.getY(), collidingShape.getZ()));
                PickResult[] resultArray = pickTool.pickAll();

                if (isCollision(resultArray)) {
                    onCollide();
                } else {
                    onMiss();
                }

                moveCollisionObject();
            }
        }

        // assign the next WakeUpCondition, so we are notified again
        wakeupOn(oredCriteria);
    }
}