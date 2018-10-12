package main.component.weights;

import java.util.Random;

/**
 * Created by Barry on 18/10/11.
 * Note: this class is the weight maxtrix between two layers
 */
public class WeightMatrix {
    private int frontLayerNums;
    private int backLayerNums;
    private double[][] weights;
    public WeightMatrix(){
        this.frontLayerNums=0;
        this.backLayerNums=0;
    }
    public WeightMatrix(int frontLayerNums, int backLayerNums){
        this.frontLayerNums = frontLayerNums;
        this.backLayerNums = backLayerNums;
        weights = new double[frontLayerNums][backLayerNums];
        //random initialize
        Random rand = new Random();
        for (int i =0;i<frontLayerNums;i++){
            for(int j=0;j<backLayerNums;j++){
                weights[i][j]= rand.nextDouble();
            }
        }
    }

    public void updateWeight(int row,int column,double newWeight){
        weights[row][column]=newWeight;
    }

    public int[] getShape(){
        int[] res = {frontLayerNums,backLayerNums};
        return res;
    }

    public int getParamNums(){
        return frontLayerNums*backLayerNums;
    }

    public double[] getRow(int index){
        double[] res = new double[weights[index].length];
        for (int i=0;i<weights[index].length;i++){
            res[i] = weights[index][i];
        }
        return res;
    }

    public double[] getColumn(int index){
        double[] res = new double[weights.length];
        for(int i=0;i<weights.length;i++){
            res[i] = weights[i][index];
        }
        return res;
    }
}
