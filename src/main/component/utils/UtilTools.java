package main.component.utils;

import main.component.exception.MissMatchDimensionException;

/**
 * Created by Barry on 18/10/12.
 */
public class UtilTools {
    public static String toString(double[] array){
        String res="";
        for (int i=0;i<array.length;i++){
            res+=array[i]+" ";
        }
        return res;
    }

    public static double dotProduct(double[] arrayA, double[] arrayB) {
        double res = 0.0;
        try {
            if (arrayA.length != arrayB.length) {
                throw new MissMatchDimensionException("ERROR: the two calcuated array doesn't macth in length");
            }
            for (int i = 0; i < arrayA.length; i++) {
                res += arrayA[i] * arrayB[i];
            }
        } catch (MissMatchDimensionException e) {
            e.printStackTrace();
        }
        return res;
    }
}
