/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import vrml3d.core.*;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnActivation;
import javax.media.j3d.WakeupOnBehaviorPost;
import javax.media.j3d.WakeupOnDeactivation;
import javax.media.j3d.WakeupOr;

/**
 *
 * @author Marcel
 */
public class Colisão_Camera extends Behavior {

    private Objeto Shape;
    private Bounds bound;
    private WakeupCondition criterion;
    private boolean collided;


    public Colisão_Camera(Objeto ob, Bounds b) {
        bound = b;
        Shape = ob;
        super.setSchedulingBounds(b);
    }

        public void initialize()
        {
            WakeupCriterion[] allEvents = new WakeupCriterion[2];
            allEvents[0] = new WakeupOnActivation();
            allEvents[1] = new WakeupOnDeactivation();
            criterion = new WakeupOr( allEvents );
            wakeupOn( criterion );
        }

        public void processStimulus( Enumeration criteria )
        {
            WakeupCriterion wakeup;

            while( criteria.hasMoreElements() )
                {
                    wakeup = (WakeupCriterion) criteria.nextElement();

                    if( wakeup instanceof WakeupOnActivation)
                        {
                            System.err.println( "Entered View Platform entrou" );
                            collided = true;
                        }
                    else
                        if( wakeup instanceof WakeupOnDeactivation )
                            {
                                System.err.println( "Exited View Platform SAiu" );
                                collided = false;
                            }
                }
            wakeupOn( criterion );
        }

        public boolean IsCollided()
        {
            return collided;
        }
    /**
     * @return the Shape
     */
    public Objeto getShape() {
        return Shape;
    }

    /**
     * @param Shape the Shape to set
     */
    public void setShape(Objeto Shape) {
        this.Shape = Shape;
    }

    /**
     * @return the bound
     */
    public Bounds getBound() {
        return bound;
    }

    /**
     * @param bound the bound to set
     */
    public void setBound(Bounds bound) {
        this.bound = bound;
    }
}
