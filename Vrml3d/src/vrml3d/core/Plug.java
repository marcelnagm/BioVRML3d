/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import javax.media.j3d.BoundingBox;
import javax.vecmath.Point2d;
import javax.vecmath.Point2i;
import javax.vecmath.Point3d;

/**
 *
 * @author Marcel
 */
public class Plug extends Objeto {

    int err;
    final Point2i rotyMax = new Point2i(-90, 90),  rotzMax = new Point2i(90, -90);
    final Point2d xMax,  zMax;
    int num;
    private My_VRML core;
    private BoundMulti proibido;
    private int local;
    double xAnt, yAnt, zAnt;
    int rotxAnt, rotyAnt, rotzAnt;

    public Plug(int i, My_VRML vr) {
        super("Plug");
        core = vr;
        num = i;
        setCapability(ALLOW_CHILDREN_READ);
        setCapability(ALLOW_CHILDREN_WRITE);
        setCapability(ALLOW_PARENT_READ);
        setCapability(ALLOW_TRANSFORM_READ);
        setCapability(ALLOW_TRANSFORM_WRITE);
        setCapability(ALLOW_BOUNDS_READ);
        setCapability(ALLOW_BOUNDS_WRITE);

        setCapability(ALLOW_AUTO_COMPUTE_BOUNDS_READ);
        setCapability(ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        setCapability(ALLOW_COLLISION_BOUNDS_READ);
        setCapability(ALLOW_COLLISION_BOUNDS_WRITE);

        setCapability(ALLOW_COLLIDABLE_READ);
        setCapability(ALLOW_COLLIDABLE_WRITE);
        setCapability(ALLOW_TRANSFORM_WRITE);
        setCapability(ENABLE_COLLISION_REPORTING);
        setCapability(ENABLE_PICK_REPORTING);
        xMax = new Point2d(core.getApp().getPaciente().getX() + 0.30, core.getApp().getPaciente().getX() - 0.30);
        zMax = new Point2d(core.getApp().getPaciente().getZ()- 0.90, core.getApp().getPaciente().getZ() );

    }

    @Override
    public void setX(double x) {

            if (xMax.x >= x && x >= xMax.y) {

                    xAnt = getX();
                    super.setX(x);

                }

    }


    @Override
    public void setXYZ(double x, double y, double z) {

        super.setXYZ(x, y, z);
        if(getCollidable()){
            BoundingBox temp = new BoundingBox(new Point3d(getX() - 0.005, getY() , getZ() - 0.005), new Point3d(getX() + 0.005, getY() + 0.01, getZ() + 0.005));
        super.setCollisionBounds(temp);
        }

    }



    @Override
    public void SetRotacao(int roty, int rotx) {
        super.SetRotacao(roty, rotx);

    }


    @Override
    public void setY(double y) {

                yAnt = getY();
                super.setY(y);
    }

    @Override
    public void setZ(double z) {

            if (zMax.x <= z && z <= zMax.y /*&& !get_Bounds_Proibido().intersect(getCollisionBounds())*/) {

                    zAnt = getZ();
                    super.setZ(z);

        }
    }

    @Override
    public void setRotZ(int rotZ) {

        if (rotZ <= rotzMax.x && rotZ >= rotzMax.y) {
            rotzAnt = getRotZ();
            super.setRotZ(rotZ);

        }

    }

    @Override
    public void setRotY(int roty) {


        if (roty <= rotyMax.x && roty >= rotyMax.y ) {

            rotyAnt = getRotY();
            super.setRotY(roty);

        } 
    }


    /**
     * @return the proibido
     */
    public BoundMulti get_Bounds_Proibido() {
        return proibido;
    }

    /**
     * @param proibido the proibido to set
     */
    public void set_Bounds_Proibido(BoundMulti proibido) {
        this.proibido = proibido;
    }

    /**
     * @return the local
     */
    public int getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(int local) {
        this.local = local;
    }
}