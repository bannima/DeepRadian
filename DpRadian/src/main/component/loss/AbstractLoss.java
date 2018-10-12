package main.component.loss;

/**
 * Created by Barry on 18/10/11.
 */
public abstract class AbstractLoss {
    public abstract double lossFunction(double[] output,double[] expected);
}
