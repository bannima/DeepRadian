package main.component.optimizer;

/**
 * Created by Barry on 18/10/11.
 */
public class GradientDescent extends AbstractOptimizer {
    public GradientDescent(){
        super(0.05);
    }
    public GradientDescent(double learningRate){
        super(learningRate);
    }
}
