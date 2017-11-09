/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Testes.CollisionExample;

import vrml3d.core.*;
import java.util.ArrayList;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Bounds;
import javax.vecmath.Point3d;

/**
 *
 * @author Marcel
 */
public class BoundMulti extends BoundingBox {

    private ArrayList bounds = new ArrayList();

    public BoundMulti(){
        bounds = new ArrayList();
    }


    @Override
    public void combine(Bounds arg0) {
//        super.combine(arg0);
        AddBounds((BoundingBox) arg0);
    }
    public void AddBounds(BoundingBox box){
        bounds.add(box);
    }

    @Override
    public boolean intersect(Point3d point){

        for(int i =0;i<bounds.size();i++){
            BoundingBox box = (BoundingBox) bounds.get(i);
            if(box.intersect(point) == true){
                System.out.println("Area "+i+"!!");
                return true;
            }
        }
//                    System.out.println("PEgo2!!");
        return false;
    }


    @Override
    public boolean intersect(Bounds arg0) {
       for(int i =0;i<bounds.size();i++){
            BoundingBox box = (BoundingBox) bounds.get(i);
            if(box.intersect(arg0) == true){
                System.out.println("PEgo!!");
                return true;
            }
       }
                    System.out.println("PEgo2!!");
        return false;
    }

    
}