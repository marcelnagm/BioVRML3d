/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vrml3d.readerConf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcel
 */
public class RWConf {

    File configuration;

    public RWConf() {
    configuration = new File("./configuration.txt");
    }



    public String read(){
        try {
            Scanner in = new Scanner(configuration);
            return in.nextLine().trim();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RWConf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void write(String in){
        try {
            RandomAccessFile file = new RandomAccessFile(configuration, "rw");
            file.writeBytes(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RWConf.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException ex){
            Logger.getLogger(RWConf.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]){
        RWConf teste = new RWConf();
        teste.write("c:\\modelagens tcc\\");
        System.out.println(teste.read());

        
    }

}
