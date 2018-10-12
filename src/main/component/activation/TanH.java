package main.component.activation;

/**
 * Created by Barry on 18/10/11.
 */
public class TanH extends AbstractActivationFunction {
    @Override
    public double function(double input) {
        double res = 0.0;
        if (input > 0) {
            res = input;
        }
        return res;
    }
}
