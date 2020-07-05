/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arabictextprocessing;

/**
 *
 * @author Muazzam Siddiqui
 */
public class Instance {

    double[] data;
    String filename;
    String target;

    Instance(){};
    
    Instance(double[] data, String filename, String target){
        for(int i=0; i<data.length; i++)
            this.data[i] = data[i];
        this.filename = filename;
        this.target = target;
    }

    Instance(int size, String filename, String target){
        for(int i=0; i<size; i++)
            this.data[i] = 0;
        this.filename = filename;
        this.target = target;
    }

    public void setData(double[] data){
        for(int i=0; i<data.length; i++)
            this.data[i] = data[i];
    }

    public void setData(int size){
        this.data = new double[size];
        for(int i=0; i<size; i++)
            this.data[i] = 0;
    }

    public void setData(int index, double data){
            this.data[index] = data;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public void setTarget(String target){
        this.target = target;
    }

    public double[] getData(){
        return (this.data);
    }

    public double getData(int i){
        return (this.data[i]);
    }

    public String getFilename(){
        return (this.filename);
    }

    public String getTarget(){
        return (this.target);
    }

}

