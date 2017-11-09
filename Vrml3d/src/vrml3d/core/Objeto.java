/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.vrml97.VrmlLoader;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Marcel
 */
public class Objeto extends TransformGroup {

    private BranchGroup Bg;
    Transform3D transform = new Transform3D();
    private Point3d Ponto = null;
    private double x;
    private double y;
    private double z;
    private int rotX = 0;
    private int rotY = 0;
    private int rotZ =  0;
    private double Scale = 1;

    public Objeto(String tipo) {
        this.setBoundsAutoCompute(true);
        this.setName(tipo);
    }

    public void Carrega_Arquivo(String file) {
        Bg = loadFile(file);

    }

    public BranchGroup loadFile(String file) //carrega um arquivo de um objeto VRML externo
    {

        System.out.println(file);
        VrmlLoader loader = new VrmlLoader();


        BranchGroup sceneGroup = null;
        Scene scene = null;
        try {
            scene = loader.load(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(My_VRML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(My_VRML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(My_VRML.class.getName()).log(Level.SEVERE, null, ex);
        }

        sceneGroup = scene.getSceneGroup();

        return sceneGroup;
    }

    public Point3d getRotXYZ(){
        return new Point3d(getRotX(), getRotX(), getRotZ());
    }

    public void setXYZ(Point3d p) {
        setXYZ(p.getX(), p.getY(), p.getZ());
    }

    public void setXYZ(double x, double y, double z) {
        try {
            this.setX(x);
        } catch (NullPointerException ex) {
        }
        try {
            this.setY(y);
        } catch (NullPointerException ex) {
        }
        try {
            this.setZ(z);
        } catch (NullPointerException ex) {
        }
        this.Aplicar();
    }

    /**
     * @return the localizacao
     */
    public void SetRotacao(int roty, int rotx) {
        this.setRotY(roty);
        this.setRotX(rotx);
        this.Aplicar();
    }

    private void Aplicar() {




        Matrix3d yMatrix = null;
        Matrix3d xMatrix = null;
        Matrix3d zMatrix = null;

        
        yMatrix = new Matrix3d(1, 0, 0.0,
                0.0, 1, 0.0,
                0.0, 0.0, 1);
        yMatrix.rotY(rotY * 2 * Math.PI / 360);
//            yMatrix.rotX(getVertical() * Math.PI / 120);
        // 0 0 x
        //0 y 0
        //0 0 z

        xMatrix = new Matrix3d(1, 0, 0.0,
                0.0, 1, 0.0,
                0.0, 0.0, 1);
        xMatrix.rotX(rotX * 2 * Math.PI / 360);
        


        zMatrix = new Matrix3d(1, 0, 0.0,
                0.0, 1, 0.0,
                0.0, 0.0, 1);
        zMatrix.rotZ(rotZ * 2 * Math.PI / 360);

        //
        //
        //



        yMatrix.mul(zMatrix);
        xMatrix.mul(yMatrix);

//        transform.setRotation(yMatrix);
        
//        transform.setRotation(xMatrix);

        Vector3f vector;
        if (Ponto == null) {
            vector = new Vector3f((float) x, (float) y, (float) z);
        }else
        {
            vector = new Vector3f((float) Ponto.x, (float) Ponto.y, (float) Ponto.z);


        }

        transform.setTranslation(vector);
        transform.setScale(Scale);
        transform.setRotation(xMatrix);

        this.setTransform(transform);
//        if(getCollisionBounds() != null){
//            BoundingBox ox = new BoundingBox(new Point3d(getX(), getY(), getZ()),new Point3d(getX(), getY(), getZ()) );
//            ox.transform(transform);
//            setCollisionBounds(ox);
//        }


    }

    /**
     * @return the Scale
     */
    public double getScale() {
        return Scale;
    }

    /**
     * @param Scale the Scale to set
     */
    public void setScale(double Scale) {
        this.Scale = Scale;
        this.Aplicar();
    }

    /**
     * @return the Bg
     */
    public BranchGroup getBranchGroup() {
        return Bg;
    }

    public Point3d getXYZ() {
        return new Point3d(x, y, z);
    }

    /**
     * @param Bg the Bg to set
     */
    public void setBranchGroup(BranchGroup Bg) {
        this.Bg = Bg;
    }

    /**
     * @param Ponto the Ponto to set
     */
    public void setPonto(Point3d Ponto) {
        this.Ponto = Ponto;
        Aplicar();
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param x the x to set
     */
    public void setX(double xExt) {
        this.x = xExt;
        Aplicar();
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
        Aplicar();
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
        Aplicar();
    }

    /**
     * @return the horizontal
     */
    public int getRotY() {
        return rotY;
    }

    /**
     * @param horizontal the horizontal to set
     */
    public void setRotY(int horizontal) {
        this.rotY = horizontal;
        Aplicar();
    }

    /**
     * @return the vertical
     */
    public int getRotX() {
        return rotX;
    }

    /**
     * @param vertical the vertical to set
     */
    public void setRotX(int vertical) {
        this.rotX = vertical;
        Aplicar();
    }

    /**
     * @return the rotZ
     */
    public int getRotZ() {
        return rotZ;
    }

    /**
     * @param rotZ the rotZ to set
     */
    public void setRotZ(int rotZ) {
        this.rotZ = rotZ;
        Aplicar();
    }
}
