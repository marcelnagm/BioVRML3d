package Testes;

import vrml3d.core.*;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcel
 */
/*
 * @(#)CollisionDetector.java 1.4 98/02/20 14:30:17
 *
 */
class Detector_Colisão extends Behavior {

    /** The separate criteria used to wake up this beahvior. */
    protected WakeupCriterion[] theCriteria;
    /** The OR of the separate criteria. */
    protected WakeupOr oredCriteria;
    /** The shape that is watched for collision. */
    protected Objeto collidingShape;
    protected String Shape;
    protected My_VRML core;

    /**
     * @param theShape
     *            Shape3D that is to be watched for collisions.
     * @param theBounds
     *            Bounds that define the active region for this behaviour
     */
    public Detector_Colisão(Objeto theShape, Bounds theBounds, String nome) {

        theShape.setBoundsAutoCompute(true);
        Shape = nome;
        collidingShape = theShape;
        setSchedulingBounds(theBounds);
    }

    /**
     * This creates an entry, exit and movement collision criteria. These are
     * then OR'ed together, and the wake up condition set to the result.
     */
    /**
     * This creates an entry, exit and movement collision criteria. These are
     * then OR'ed together, and the wake up condition set to the result.
     */
    public void initialize() {
        theCriteria = new WakeupCriterion[3];
        theCriteria[0] = new WakeupOnCollisionEntry(collidingShape);
        theCriteria[1] = new WakeupOnCollisionExit(collidingShape);
        theCriteria[2] = new WakeupOnCollisionMovement(collidingShape);
        oredCriteria = new WakeupOr(theCriteria);
        wakeupOn(oredCriteria);
    }

    /**
     * Where the work is done in this class. A message is printed out using the
     * userData of the object collided with. The wake up condition is then set
     * to the OR'ed criterion again.
     */
    public void processStimulus(Enumeration criteria) {
        WakeupCriterion theCriterion = (WakeupCriterion) criteria.nextElement();
        if (theCriterion instanceof WakeupOnCollisionEntry) {
            Node theLeaf = ((WakeupOnCollisionEntry) theCriterion).getTriggeringPath().getObject();
            System.out.println("Collided with "+Shape);
                    collidingShape.setXYZ(collidingShape.getX(), collidingShape.getY()+0.01,collidingShape.getZ());

        } else if (theCriterion instanceof WakeupOnCollisionExit) {
            Node theLeaf = ((WakeupOnCollisionExit) theCriterion).getTriggeringPath().getObject();
            System.out.println("Stopped colliding with  " +Shape);
            collidingShape.setXYZ(collidingShape.getX(), collidingShape.getY()+0.01,collidingShape.getZ());
        } else {
            Node theLeaf = ((WakeupOnCollisionMovement) theCriterion).getTriggeringPath().getObject();
            System.out.println("Moved whilst colliding with " +Shape);

                collidingShape.setXYZ(collidingShape.getX(), collidingShape.getY()+0.01,collidingShape.getZ());

        }

        wakeupOn(oredCriteria);
    }
}