package main.component.optimizer;

/**
 * Created by Barry on 18/10/11.
 */
public abstract class AbstractOptimizer {
    private double learningRate;
    public AbstractOptimizer(double learningRate){
        this.learningRate = learningRate;
    }
    public double getLearningRate(){
        return this.learningRate;
    }
}
