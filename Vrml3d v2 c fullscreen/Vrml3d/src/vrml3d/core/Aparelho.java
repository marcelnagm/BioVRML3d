/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vrml3d.core;

import java.util.ArrayList;

/**
 *
 * @author Marcel
 */
public class Aparelho {

    private Objeto Aparelho = new Objeto("Aparelho");
    private Objeto Paciente = new Objeto("Paciente");
    private Objeto Livro = new Objeto("Livro");
    private ArrayList plugs = new ArrayList();
    private ArrayList posicoes = new ArrayList();
    private int numplugs;

    public Aparelho() {
        Aparelho = new Objeto("Aparelho");
        Paciente = new Objeto("Paciente");
//getAparelho().setCollidable(true);
        getPaciente().setCollidable(true);
//getLivro().setCollidable(true);

    }

    /**
     * @return the Aparelho
     */
    public Objeto getAparelho() {
        return Aparelho;
    }

    /**
     * @return the Paciente
     */
    public Objeto getPaciente() {
        return Paciente;
    }

    /**
     * @param Aparelho the Aparelho to set
     */
    public void setAparelho(Objeto Aparelho) {
        this.Aparelho = Aparelho;
    }

    /**
     * @param Paciente the Paciente to set
     */
    public void setPaciente(Objeto Paciente) {
        this.Paciente = Paciente;
    }

    /**
     * @return the Livro
     */
    public Objeto getLivro() {
        return Livro;
    }

    /**
     * @param Livro the Livro to set
     */
    public void setLivro(Objeto Livro) {
        this.Livro = Livro;
    }

    public void Add_Plug(Plug in) {
        plugs.add(in);
    }

    public ArrayList getListPlugs() {
        return plugs;
    }

  
    /**
     * @return the posicoes
     */
    public ArrayList getPosicoes() {
        return posicoes;
    }

    /**
     * @param posicoes the posicoes to set
     */
    public void setPosicoes(ArrayList posicoes) {
        this.posicoes = posicoes;
    }

    /**
     * @param plugs the plugs to set
     */
    public void setPlugs(ArrayList plugs) {
        this.plugs = plugs;
    }

    /**
     * @return the numplugs
     */
    public int getNumplugs() {
        return numplugs;
    }

    /**
     * @param numplugs the numplugs to set
     */
    public void setNumplugs(int numplugs) {
        this.numplugs = numplugs;
    }
}
