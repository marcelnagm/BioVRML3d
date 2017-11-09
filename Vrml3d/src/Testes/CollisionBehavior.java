/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PickBounds;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * This behavior detects collisions between the branch of a scene, and a
 * collision object. The Java 3D 1.2 picking utilities are used to implement
 * collision detection. The objects in the scene that are collidable should have
 * their user data set. The collision object's user data is used to ignore
 * collisions between the object and itself.
 *
 * When a collision is detected the trajectory of the collision object is
 * reversed (plus a small random factor) and an Appearance object is modified.
 *
 * When a collision is not detected the collision object is moved along its
 * current trajectory and the Appearance color is reset.
 *
 * Colision checking is run after every frame.
 *
 */
/**
 *
 * @author Marcel
 */
public class CollisionBehavior extends Behavior {
    // the wake up condition for the behavior

    protected WakeupCondition m_WakeupCondition = null;

    // how often we check for a collision
    private static final int ELAPSED_FRAME_COUNT = 1;

    // the branch that we check for collisions
    private BranchGroup pickRoot = null;

    // the collision object that we are controlling
    private TransformGroup collisionObject = null;

    // the appearance object that we are controlling
    private Appearance objectAppearance = null;

    // cached PickBounds object used for collision detection
    private PickBounds pickBounds = null;

    // cached Material objects that define the collided and missed colors
    private Material collideMaterial = null;
    private Material missMaterial = null;

    // the current trajectory of the object
    private Vector3d incrementVector = null;

    // the current position of the object
    private Vector3d positionVector = null;

    public CollisionBehavior(BranchGroup pickRoot,
            TransformGroup collisionObject, Appearance app, Vector3d posVector,
            Vector3d incVector) {
        // save references to the objects
        this.pickRoot = pickRoot;
        this.collisionObject = collisionObject;
        this.objectAppearance = app;

        incrementVector = incVector;
        positionVector = posVector;

        // create the WakeupCriterion for the behavior
        WakeupCriterion criterionArray[] = new WakeupCriterion[1];
        criterionArray[0] = new WakeupOnElapsedFrames(ELAPSED_FRAME_COUNT);

        objectAppearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

        collisionObject.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        collisionObject.setCapability(Node.ALLOW_BOUNDS_READ);

        // save the WakeupCriterion for the behavior
        m_WakeupCondition = new WakeupOr(criterionArray);
    }

    public void initialize() {
        // apply the initial WakeupCriterion
        wakeupOn(m_WakeupCondition);

        Color3f objColor = new Color3f(1.0f, 0.1f, 0.2f);
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        collideMaterial = new Material(objColor, black, objColor, black, 80.0f);

        objColor = new Color3f(0.0f, 0.1f, 0.8f);
        missMaterial = new Material(objColor, black, objColor, black, 80.0f);

        objectAppearance.setMaterial(missMaterial);
    }

    protected void onCollide() {
        objectAppearance.setMaterial(collideMaterial);

        incrementVector.negate();

        // add a little randomness
        incrementVector.x += (Math.random() - 0.5) / 20.0;
        incrementVector.y += (Math.random() - 0.5) / 20.0;
        incrementVector.z += (Math.random() - 0.5) / 20.0;
    }

    protected void onMiss() {
        objectAppearance.setMaterial(missMaterial);
    }

    protected void moveCollisionObject() {
        Transform3D t3d = new Transform3D();

        positionVector.add(incrementVector);
        t3d.setTranslation(positionVector);

        collisionObject.setTransform(t3d);
    }

    public boolean isCollision(PickResult[] resultArray) {
        if (resultArray == null || resultArray.length == 0) {
            return false;
        }

        // we use the user data on the nodes to ignore the
        // case of the collisionObject having collided with itself!
        // the user data also gives us a good mechanism for reporting the
        // collisions.
        for (int n = 0; n < resultArray.length; n++) {
            Object userData = resultArray[n].getObject().getUserData();

            if (userData != null && userData instanceof String) {
                // check that we are not colliding with ourselves...
                if (((String) userData).equals((String) collisionObject.getUserData()) == false) {
                    System.out.println("Collision between: " + collisionObject.getUserData() + " and: " + userData);
                    return true;
                }
            }
        }

        return false;
    }

    public void processStimulus(java.util.Enumeration criteria) {
        while (criteria.hasMoreElements()) {
            WakeupCriterion wakeUp = (WakeupCriterion) criteria.nextElement();

            // every N frames, check for a collision
            if (wakeUp instanceof WakeupOnElapsedFrames) {
                // create a PickBounds
                PickTool pickTool = new PickTool(pickRoot);
                pickTool.setMode(PickTool.BOUNDS);

                BoundingSphere bounds = (BoundingSphere) collisionObject.getBounds();
                pickBounds = new PickBounds(new BoundingSphere(new Point3d(
                        positionVector.x, positionVector.y, positionVector.z),
                        bounds.getRadius()));
                pickTool.setShape(pickBounds, new Point3d(0, 0, 0));
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
        wakeupOn(m_WakeupCondition);
    }
}

