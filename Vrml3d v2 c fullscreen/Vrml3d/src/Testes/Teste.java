/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Testes;

import javax.media.j3d.BoundingBox;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;

/**
 *
 * @author Marcel
 */
public class Teste {

  public static void main(String[] args) {
    BoundingBox box = new BoundingBox(new Point3d(0, 0, 0), new Point3d(1, 1, 1));
    BoundingBox box2 = new BoundingBox(new Point3d(0, 0, 0), new Point3d(-1, 1, 1));
    box.combine(box2);
    
    System.out.println(box.intersect(new Point3d(-1, 1, 1)));
    }


}
