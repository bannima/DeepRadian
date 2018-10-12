package main.component.activation;

/**
 * Created by Barry on 18/10/11.
 */
public class Sigmod extends AbstractActivationFunction {
    public double function(double input) {
        return Math.exp(input)/(Math.exp(input)+1);
    }
}
