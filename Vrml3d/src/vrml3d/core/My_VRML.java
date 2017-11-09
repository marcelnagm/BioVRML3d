/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.TransformGroup;


import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import vrml3d.GUI.Vrml3dPainel;

/**
 *
 * @author Marcel
 */
public class My_VRML {

    private SimpleUniverse universe;
    private Objeto principal = new Objeto("Root");
    private String EMG,  ECG,  EEG,  Homem,  Mulher,  plg,  Casa;
    private Canvas3D Tela;
    private Aparelho App = new Aparelho();
    private int i = 0;
    private Camera cam;
    private String Path;
    private BoundMulti forbiden = new BoundMulti("Negad");
    private vrml3d.GUI.Vml3dView view;

    public My_VRML(JPanel jPanel1,vrml3d.GUI.Vml3dView view) {


        //cria tela para exibição do conteudo
        GraphicsConfigTemplate3D g3 = new GraphicsConfigTemplate3D();
        g3.setSceneAntialiasing(g3.PREFERRED);
        g3.setDoubleBuffer(g3.PREFERRED);
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Tela = new Canvas3D(gd[0].getBestConfiguration(g3));
        Tela.setSize(jPanel1.getSize());
        jPanel1.add(Tela);
        Set_Universe(new SimpleUniverse(Tela));
        setCanvas3D(Tela);
        this.setView(view);
        setClassPath("C:\\Modelagens TCC");
        //gera basico para adicionar os elementos
        //cria luz + branch group + transforma group

        Gera_Cena();

        //adiciona os listeners ao Canvas
        new Interface(this);
        //seta a camera
        cam = new Camera(this);
//        Get_Universe().getViewingPlatform().getViewPlatform().setActivationRadius(1);
        
    }

    @SuppressWarnings("static-access")
    public void GeraS(String in, vrml3d.GUI.Vrml3dInterno inter) {


        final Plug plug = new Plug(i, this);

        inter.setItem((Plug) plug);
        i++;
        plug.Carrega_Arquivo(in);
        BranchGroup bg = plug.getBranchGroup();

        plug.set_Bounds_Proibido(forbiden);

        plug.addChild(bg);
        plug.setScale(0.40);
        bg = new BranchGroup();

        Colisao coll = new Colisao(plug, this, forbiden);
        plug.getBranchGroup().addChild(coll);
        bg.addChild(plug);

        plug.setCollidable(false);
        plug.setXYZ(getApp().getPaciente().getX(), getApp().getPaciente().getY() + 0.3, getApp().getPaciente().getZ() - 0.5);
        plug.setCollidable(true);

        getPrincipal().addChild(bg);
        getApp().Add_Plug(plug);



    }

    private DirectionalLight Lights(Vector3f destiny, Point3d from, double intensidade) {

        Color3f light1Color = new Color3f(1, 1, 1);


        BoundingSphere bounds = new BoundingSphere(from, intensidade);
        Vector3f light1Direction = destiny; //new Vector3f(0, -2, 0);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

        light1.setInfluencingBounds(bounds);

        return light1;
    }

    public void Monta_EEG(String Aparelho, String Paciente) {


        Gera(Aparelho, getApp().getAparelho());
        Gera(Paciente, getApp().getPaciente());
        Gera(getPath() + "\\EEG\\LIVRO.wrl", getApp().getLivro());


        App.getLivro().setXYZ(1.03, -0.52, 0.38);
        App.getLivro().SetRotacao(-90, 0);
        App.getLivro().setScale(1.14);

        App.getPaciente().setXYZ(-0.63, 0, 0.41);
        App.getPaciente().setScale(0.55);
        App.getPaciente().SetRotacao(0, -87);


        Objeto temp = new Objeto("Casa");
        Gera(Casa, temp);
        temp.setXYZ(-0.68, -0.99, -0.25);
        temp.setScale(0.65);



        cam.setInicial(new Point3d(0, 0.35, 2.3));
        cam.setXYZ(cam.getInicial());

        //Maca Permitido
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.146), new Point3d(-0.48, -0.07, -0.23)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.23), new Point3d(-0.48, 0.03, -0.25)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.25), new Point3d(-0.48, 0.04, -0.28)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.28), new Point3d(-0.48, 0.05, -0.34)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.34), new Point3d(-0.48, 0.06, -0.40)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.79, -0.44, -0.40), new Point3d(-0.48, 0.07, App.getPaciente().getZ())));

    //


    }

    public void Monta_ECG(String Aparelho, String Paciente) {

        Gera(Aparelho, getApp().getAparelho());
        Gera(Paciente, getApp().getPaciente());
        Gera(getPath() + "\\ECG\\LIVRO.wrl", getApp().getLivro());



        App.getAparelho().setScale(0.81);


        App.getPaciente().setXYZ(0.38, 0.01, 0.54);
        App.getPaciente().setScale(0.54);
        App.getPaciente().SetRotacao(0, -87);


        App.getLivro().setXYZ(-1.07, -0.46, 0.47);
//        App.getLivro().SetRotacao(90, 0);
        App.getLivro().setScale(1.38);

        Objeto temp = new Objeto("Casa");
        Gera(Casa, temp);
        temp.setXYZ(-0.68, -0.91, -0.25);
        temp.setScale(0.65);

        cam.setInicial(new Point3d(-0.06, 0.29, 2.54));
        cam.setColocacao(new Point3d(App.getAparelho().getX(),3, 0));
        cam.setXYZ(cam.getInicial());


        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.45, -0.146), new Point3d(0.92, 0.09, -0.23))); //1
        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.44, -0.23), new Point3d(0.92, 0.08, -0.25)));  //2
        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.44, -0.25), new Point3d(0.92, 0.08, -0.28)));  //3
        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.44, -0.28), new Point3d(0.92, 0.07, -0.34))); //4
        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.44, -0.34), new Point3d(0.92, 0.06, -0.40))); //5
        forbiden.AddBounds(new BoundingBox(new Point3d(0.23, -0.44, -0.24), new Point3d(0.92, 0.015, App.getPaciente().getZ())));  //6


        forbiden.AddBounds(new BoundingBox(new Point3d(0.3068, -0.45, 0.4976), new Point3d(0.3662, 0.11, 0.5386))); //PÉ ESQUERDO 6
        forbiden.AddBounds(new BoundingBox(new Point3d(0.3745, -0.45,  0.5), new Point3d(0.4362, 0.11, 0.5386))); //PÉ DIREITO 7
        forbiden.AddBounds(new BoundingBox(new Point3d(0.2694, -0.45, 0.2949), new Point3d(0.3517, 0.04, 0.4976))); //TIBIA ESQUERDA 8
        forbiden.AddBounds(new BoundingBox(new Point3d(0.3677, -0.45, 0.2877), new Point3d(0.4371, 0.04, 0.5))); //TIBIA DIREITA 9

        forbiden.AddBounds(new BoundingBox(new Point3d(0.2785, -0.45, 0.2251), new Point3d(0.4454, 0.07, 0.2877))); //JOELHO 10
        forbiden.AddBounds(new BoundingBox(new Point3d(0.2885, -0.45, 0.077), new Point3d(0.3562, 0.09, 0.2236))); //COXA ESQUERDA 11
        forbiden.AddBounds(new BoundingBox(new Point3d(0.38, -0.45, 0.077), new Point3d(0.4577, 0.09, 0.2164))); //COXA DIREITA 12

        forbiden.AddBounds(new BoundingBox(new Point3d(0.2862, -0.45, -0.0337), new Point3d(0.4577, 0.109, 0.077))); //VIRILHA 13

        forbiden.AddBounds(new BoundingBox(new Point3d(0.2359, -0.45, -0.0746), new Point3d(0.2588, 0.07, 0.0891))); //BRAÇO ESQUERDO PART1 14
        forbiden.AddBounds(new BoundingBox(new Point3d(0.2542, -0.45, -0.2216), new Point3d(0.2885, 0.09, -0.0746))); //BRAÇO ESQUERDO PART2 15
        forbiden.AddBounds(new BoundingBox(new Point3d(0.4668, -0.45, -0.0818), new Point3d(0.5308, 0.06, 0.0722))); //BRAÇO DIREITO PART1 16
        forbiden.AddBounds(new BoundingBox(new Point3d(0.46, -0.45, -0.2249), new Point3d(0.4988, 0.09, -0.0948))); //BRAÇO DIREITO PART2 17
        forbiden.AddBounds(new BoundingBox(new Point3d(0.46, -0.45, -0.2249), new Point3d(0.5377, 0.09, -0.0948))); //BRAÇO DIREITO PART2 17

        forbiden.AddBounds(new BoundingBox(new Point3d(0.3068, -0.45, -0.1493), new Point3d(0.4394, 0.12, -0.0447))); //VICERAS 18
        forbiden.AddBounds(new BoundingBox(new Point3d(0.2908, -0.45, -0.2143), new Point3d(0.4394, 0.12, -0.0447))); //PEITO 19

        forbiden.AddBounds(new BoundingBox(new Point3d(0.3479, -0.45, -0.2581), new Point3d(0.4079, 0.09, -0.2381))); //PESCOÇO 20
        forbiden.AddBounds(new BoundingBox(new Point3d(0.3462, -0.45, -0.5388), new Point3d(0.4034, 0.11, -0.40410))); //Cabeça 21


        
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.24055597020469663, -0.44, 0.020729764344636702), new Point3d(0.2519858087125083, 0.075, 0.03759122054307989))); //braço esquerdo
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.4920124173765551, -0.44, 0.008685867060034408), new Point3d(0.5171580620937407, 0.09, 0.023138543801557145))); //braço direito
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.32056483975937883, -0.44, 0.44635396387492095), new Point3d(0.35714032298437665, 0.08, 0.4783978611595233))); //pé esquerdo
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.4069536391040964,-0.44,0.4758066406164427), new Point3d(0.4169536391040964, 0.05 ,0.4858066406164427))); //pé direito
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.3536975739306021,-0.44,-0.16492869492439782), new Point3d(0.3636975739306021,0.14,-0.15492869492439781))); //v1
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.3892016173795982 ,-0.44, -0.16733747438131816), new Point3d(0.3992016173795982,0.14,-0.15733747438131815))); //v2
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.3927520217244981 ,-0.44,-0.13843212089827275 ), new Point3d(0.4027520217244981, 0.14,-0.12843212089827274))); //v3
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.3998528304142964,-0.44,-0.11434432632906809), new Point3d(0.40985283041429643,0.14,-0.10434432632906808))); //v4
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.4247056608285947,-0.44,-0.10711798795830685), new Point3d(0.4347056608285947, 0.13, -0.09711798795830684))); //v5
        getApp().getPosicoes().add(new BoundingBox(new Point3d(0.43180646951839297, 0.04 ,-0.10970920850138636), new Point3d(0.4524576825530914,0.07,-0.09370920850138635))); //v6

        String[] derivações = { "Punho Esquerdo","Punho Direito","Tornozelo Esquerdo","Tornozelo Direito",
            "V1","V2","V3","V4","V5","V6",
        };
        new Vrml3dPainel(10, this,derivações);
        App.setNumplugs(10);

        getView().labelCamera.setText("Ambiente Carregado");
    }

    public void Monta_EMG(String Aparelho, String Paciente) {


//        getApp().setAparelho(Gera(Aparelho, "EMG"));
//        getApp().setPaciente(Gera(Paciente,"Paciente"));

        Gera(Aparelho, getApp().getAparelho());
        Gera(Paciente, getApp().getPaciente());
        Gera(getPath() + "\\EMG\\LIVRO.wrl", getApp().getLivro());

        App.getAparelho().setXYZ(0, 0, 0);
        App.getAparelho().setScale(0.95);
        App.getAparelho().SetRotacao(0, 0);
        getApp().getPaciente().setCollisionBounds(forbiden);

        App.getLivro().setXYZ(1.12, -0.52, 0.26);
        App.getLivro().SetRotacao(-90, 0);
        App.getLivro().setScale(1.31);

        Objeto temp = new Objeto("Casa");
        Gera(Casa, temp);
        temp.setXYZ(-0.68, -0.99, -0.25);
        temp.setScale(0.65);

        cam.setInicial(new Point3d(0, 0.41, 1.34));
        cam.setXYZ(cam.getInicial());


        App.getPaciente().setXYZ(-0.55, -0.02, 0.54);
        App.getPaciente().setScale(0.58);
        App.getPaciente().SetRotacao(0, -90);

        //Maca
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.146), new Point3d(-0.40, -0.07, -0.23)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.23), new Point3d(-0.40, 0, -0.25)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.25), new Point3d(-0.40, -0.02, -0.28)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.28), new Point3d(-0.40, -0.04, -0.34)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.34), new Point3d(-0.40, -0.05, -0.40)));
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.71, -0.44, -0.40), new Point3d(-0.40, -0.06, App.getPaciente().getZ())));

//        Paciente
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6243, -0.40, 0.485), new Point3d(-0.552, 0.06, 0.54))); //apé esquerda
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6243, -0.40, 0.2579), new Point3d(-0.552, 0., 0.485))); //tibia esquerda
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6536, -0.40, 0.0399), new Point3d(-0.5612, 0.02, 0.2004))); //Coxa Esquerda
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.4643, -0.40, -0.2880), new Point3d(-0.4193, 0.02, -0.142))); //Braço1 esquedo
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.4531, -0.40, -0.1348), new Point3d(-0.39001, 0.02, 0.02802))); //Braço1 esquedo



        forbiden.AddBounds(new BoundingBox(new Point3d(-0.7189, -0.40, -0.2761), new Point3d(-0.6446, 0, 0.0471))); //Braço Direito
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.5522, -0.40, 0.29146), new Point3d(-0.466, 0, 0.4734))); //tibia direita
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.552, -0.40, 0.4734), new Point3d(-0.466, 0.08, 0.5213))); //pé direita
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.5364, -0.40, 0.0204), new Point3d(-0.4598, 0.02, 0.2004))); //coxa direita direita


        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6536, -0.40, 0.2004), new Point3d(-0.466, 0.02, 0.2579))); //Joelhos
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6333, -0.40, -0.0701), new Point3d(-0.4598, 0.05, 0.2004))); //VIrilha
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6153, -0.40, -0.1779), new Point3d(-0.4598, 0.065, 0.2004))); //Peito 1
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.7189, -0.40, -0.2497), new Point3d(-0.4193, 0.065, -0.1779))); //Peito 2
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.7189, -0.40, -0.30), new Point3d(-0.4193, 0.045, -0.2497))); //Peito 2
        forbiden.AddBounds(new BoundingBox(new Point3d(-0.6089, -0.40, -0.4126), new Point3d(-0.5093, 0.06, -0.30))); //Cabeça

//        new Vrml3dPainel(4, this);


    }

    @SuppressWarnings("static-access")
    public void Gera(String in, Objeto obj) {

        obj.Carrega_Arquivo(in);
        BranchGroup bg = obj.getBranchGroup();

        obj.addChild(bg);
        obj.setCapability(obj.ALLOW_CHILDREN_READ);
        obj.setCapability(obj.ALLOW_CHILDREN_WRITE);
        obj.setCapability(obj.ALLOW_PARENT_READ);
        obj.setCapability(obj.ALLOW_TRANSFORM_READ);
        obj.setCapability(obj.ALLOW_TRANSFORM_WRITE);
        obj.setCapability(Objeto.ALLOW_COLLIDABLE_WRITE);
        obj.setCapability(Objeto.ALLOW_COLLIDABLE_READ);
        obj.setCapability(Objeto.ALLOW_COLLISION_BOUNDS_READ);
        obj.setCapability(Objeto.ALLOW_COLLISION_BOUNDS_WRITE);
        obj.setCapability(Objeto.ALLOW_AUTO_COMPUTE_BOUNDS_READ);
        obj.setCapability(Objeto.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);

        BranchGroup temp = new BranchGroup();
        temp.addChild(obj);
        obj.setBranchGroup(bg);
        obj.setBoundsAutoCompute(true);

        getPrincipal().addChild(temp);

//        return tg;
    }

    public void setClassPath(String in) {

        setEMG(in + "\\EMG\\EMG Completo.WRL");
        setECG(in + "\\ECG\\ECG Completo.WRL");
        setEEG(in + "\\EEG\\EEG Completo.WRL");
        setHomem(in + "\\Avatars\\homem.wrl");
        setMulher(in + "\\Avatars\\mulher.wrl");
        setPlug(in + "\\plug.wrl");
        setCasa(in + "\\Consultorio\\sala.wrl");
        setPath(in);
    }

    private void Gera_Cena() {

        principal.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        principal.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        principal.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        principal.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        principal.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        principal.setCapability(TransformGroup.ALLOW_COLLIDABLE_READ);
        principal.setCapability(TransformGroup.ALLOW_COLLIDABLE_WRITE);
        principal.setCapability(TransformGroup.ALLOW_COLLISION_BOUNDS_READ);
        principal.setCapability(TransformGroup.ALLOW_COLLISION_BOUNDS_WRITE);
        principal.setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        principal.setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_READ);



        principal.addChild(Lights(new Vector3f(0, 0, -2), new Point3d(0, 0, 0), 3));
        principal.addChild(Lights(new Vector3f(0, -4, 0), new Point3d(0, 4, 0), 10));
        principal.addChild(Lights(new Vector3f(0, 2, 0), new Point3d(0, -4, 0), 10));

        final BranchGroup bg = new BranchGroup();
        principal.setBranchGroup(bg);
        bg.addChild(principal);
        principal.setCollidable(true);

        Get_Universe().addBranchGraph(bg);


    }


    //Getters and Seters
    public SimpleUniverse Get_Universe() {
        return universe;
    }

    public void Set_Universe(SimpleUniverse univ) {
        universe = univ;
    }

    /**
     * @return the Tela
     */
    public Canvas3D getCanvas3D() {
        return Tela;
    }

    /**
     * @param Tela the Tela to set
     */
    public void setCanvas3D(Canvas3D Tela) {
        this.Tela = Tela;
    }

    /**
     * @return the EMG
     */
    public String getEMG() {
        return EMG;
    }

    /**
     * @param EMG the EMG to set
     */
    public void setEMG(String EMG) {
        this.EMG = EMG;
    }

    /**
     * @return the ECG
     */
    public String getECG() {
        return ECG;
    }

    /**
     * @param ECG the ECG to set
     */
    public void setECG(String ECG) {
        this.ECG = ECG;
    }

    /**
     * @return the EEG
     */
    public String getEEG() {
        return EEG;
    }

    /**
     * @param EEG the EEG to set
     */
    public void setEEG(String EEG) {
        this.EEG = EEG;
    }

    /**
     * @return the App
     */
    public Aparelho getApp() {
        return App;
    }

    /**
     * @return the Homem
     */
    public String getHomem() {
        return Homem;
    }

    /**
     * @param Homem the Homem to set
     */
    public void setHomem(String Homem) {
        this.Homem = Homem;
    }

    /**
     * @return the Mulher
     */
    public String getMulher() {
        return Mulher;
    }

    /**
     * @param Mulher the Mulher to set
     */
    public void setMulher(String Mulher) {
        this.Mulher = Mulher;
    }

    /**
     * @return the principal
     */
    public Objeto getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Objeto principal) {
        this.principal = principal;
    }

    /**
     * @return the plug
     */
    public String getPlug() {
        return plg;
    }

    /**
     * @param plug the plug to set
     */
    public void setPlug(String plug) {
        this.plg = plug;
    }

    /**
     * @return the Casa
     */
    public String getCasa() {
        return Casa;
    }

    /**
     * @param Casa the Casa to set
     */
    public void setCasa(String Casa) {
        this.Casa = Casa;
    }

    /**
     * @return the cam
     */
    public Camera getCam() {
        return cam;
    }

    /**
     * @param cam the cam to set
     */
    public void setCam(Camera cam) {
        this.cam = cam;
    }

    /**
     * @return the Path
     */
    public String getPath() {
        return Path;
    }

    /**
     * @param Path the Path to set
     */
    public void setPath(String Path) {
        this.Path = Path;
    }

    /**
     * @return the view
     */
    public vrml3d.GUI.Vml3dView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(vrml3d.GUI.Vml3dView view) {
        this.view = view;
    }
}
