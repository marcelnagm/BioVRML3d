/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point2i;
import javax.vecmath.Point3d;

/**
 *
 * @author Marcel
 */
public class Camera extends vrml3d.core.Objeto {

    private Point3d inicial,  colocacao;
    private boolean explorador = false,  colocando_plug = false;
    private My_VRML core;
    private Point2i Limites_rotVertical,  Limites_rotHorizontal;
    private BoundMulti multi;

    public Camera(My_VRML nucleo) {
        super("Camera");
        this.core = nucleo;
        Limites_rotHorizontal = new Point2i(90, -90);
        Limites_rotVertical = new Point2i(45, -60);

    }

    /**
     * Procedimento que seta um padrão de camera
     * Para i = 0 : posição inicial
     * Para i = 1 : posição 1ª pessoa
     * Para i = 2 : colocação de plugs
     * @param i Possição
     *
     *
     */
    public void setPadrao(int i) {
        Objeto move;
        switch (i) {
            case 0: {
                setXYZ(getInicial());
                setColocando_plug(false);
                setPessoa(false);
                core.getView().labelCamera.setText("modo Inicial de Câmera");
                break;
            }
            case 1: {
                setXYZ(getInicial());
                SetRotacao(0, 0);
                setColocando_plug(false);
                setPessoa(true);
                core.getView().labelCamera.setText("modo de Câmera 1ª Pessoa");
                break;
            }

            case 2: {
                    setColocando_plug(true);
                     setPessoa(false);
       //                    this.setXYZ(getColocacao());
//                    System.out.println("x:"+getX()+" y:"+getY()+" Z:"+getZ());
//                    this.SetRotacao(0, -60);
                    Transform3D temp = new Transform3D();
                    move = new Objeto("Visão");
//                    move.setXYZ(core.getApp().getPaciente().getX(), 4, 0);
                    move.setXYZ(getColocacao());
                    move.SetRotacao(0, -90);

                    move.getTransform(temp);
                    core.Get_Universe().getViewingPlatform().getViewPlatformTransform().setTransform(temp);                  
                     core.getView().labelCamera.setText("modo de Câmera Câmera de colocação de plugs");
                break;
            }

        }
    }

    @Override
    public void setXYZ(double x, double y, double z) {
//  System.out.println("Dentro");

//        try {
//            if (multi.intersect(new Point3d(x, y, z))) {
//                System.out.println("Dentro da área");
//
//            } else {
//                System.out.println("Fora da área");
//                super.setXYZ(x, y, z);
//            }
////
//        } catch (NullPointerException ex) {
//            System.out.println("Deu erro");
            super.setXYZ(x, y, z);
//        }

            System.out.println("x 22:" + getX() + " y:" + getY() + "z :" + getZ());
        Transform3D temp = new Transform3D();
        getTransform(temp);
        setTransformView(temp);

    }

    @Override
    public void SetRotacao(int roty, int rotx) {
        if (isPessoa() && (roty >= Limites_rotHorizontal.y && roty <= Limites_rotHorizontal.x) && (rotx >= Limites_rotVertical.y && rotx <= Limites_rotVertical.x)) {
            super.SetRotacao(roty, rotx);
            super.setRotZ(roty);

            System.out.println("x 22:" + getX() + " y:" + getY() + "z :" + getZ());
            Transform3D temp = new Transform3D();
            getTransform(temp);
            core.Get_Universe().getViewingPlatform().getViewPlatformTransform().setTransform(temp);
        }

    }

    public boolean Colliding(double x, double y, double z) {
        return getMulti().intersect(new Point3d(x, y, z));
    }


    public void setTransformView(Transform3D arg0) {
        super.setTransform(arg0);
        core.Get_Universe().getViewingPlatform().getViewPlatformTransform().setTransform(arg0);
    }

    

    @Override
    public void setRotY(int horizontal) {
          System.out.println("x 22:" + getX() + " y:" + getY() + "z :" + getZ());
        super.setRotY(horizontal);
    }

    @Override
    public void setRotX(int vertical) {
          System.out.println("x 22:" + getX() + " y:" + getY() + "z :" + getZ());
        super.setRotX(vertical);
    }
    /**
     * @return the _pessoa
     */
    public boolean isPessoa() {
        return isExplorador();
    }

    /**
     * @param pessoa the _pessoa to set
     */
    public void setPessoa(boolean pessoa) {
        this.setExplorador(pessoa);
    }

    /**
     * @return the colocando_plug
     */
    public boolean isColocando_plug() {
        return colocando_plug;
    }

    /**
     * @param colocando_plug the colocando_plug to set
     */
    public void setColocando_plug(boolean colocando_plug) {
        this.colocando_plug = colocando_plug;
    }

    /**
     * @return the inicial
     */
    public Point3d getInicial() {
        return inicial;
    }

    /**
     * @param inicial the inicial to set
     */
    public void setInicial(Point3d inicial) {
        this.inicial = inicial;
    }

    /**
     * @param x the x to set
     */
    @Override
    public void setX(double x) {
//        if (!Colliding(x, getRotY(), getRotZ())) {
            super.setX(x);
//        } else {
//            System.out.println("Colidion X");
//        }

    }

    /**
     * @param y the y to set
     */
    @Override
    public void setY(double y) {
//        if (!Colliding(getX(), y, getRotZ())) {
            super.setY(y);
//        } else {
//            System.out.println("Colidion Y");
//        }
    }

    /**
     * @param z the z to set
     */
    @Override
    public void setZ(double z) {
//        if (!Colliding(getX(), getY(), z)) {
            super.setZ(z);
//        } else {
//            System.out.println("Colidion Z");
//        }
    }

    /**
     * @return the explorador
     */
    public boolean isExplorador() {
        return explorador;
    }

    /**
     * @param explorador the explorador to set
     */
    public void setExplorador(boolean explorador) {
        this.explorador = explorador;
    }

    /**
     * @return the colocacao
     */
    public Point3d getColocacao() {
        return colocacao;
    }

    /**
     * @param colocacao the colocacao to set
     */
    public void setColocacao(Point3d colocacao) {
        this.colocacao = colocacao;
    }

    /**
     * @return the multi
     */
    public BoundMulti getMulti() {
        return multi;
    }

    /**
     * @param multi the multi to set
     */
    public void setMulti(BoundMulti multi) {
        this.multi = multi;
    }
}
