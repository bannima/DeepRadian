package main.component.loss;

import main.component.exception.MissMatchDimensionException;

/**
 * Created by Barry on 18/10/11.
 */
public class SquareLoss extends AbstractLoss {
    @Override
    public double lossFunction(double[] output, double[] expected) {
        double res =0.0;
        try{
            if(output.length!=expected.length){
                throw new MissMatchDimensionException("EORROR: the dimension of output and expected not match");
            }
            for(int i=0;i<output.length;i++){
                res += Math.pow(output[i]-expected[i],2);
            }
        }
        catch(MissMatchDimensionException e){
            e.printStackTrace();
        }
        return 0.5*res;
    }
}
