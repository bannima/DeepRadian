package main.component.basic;

/**
 * Created by Barry on 18/10/11.
 */
public class Neuron {
    private double value;
    private double bias;
    private double derivation;

    public Neuron() {
        value = 0.0;
        derivation = 0.0;
        bias = 0;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getBias(){
        return this.bias;
    }
    public void setBias(double bias){
        this.bias = bias;
    }

    public void setDerivation(double derivation){
        this.derivation = derivation;
    }
    public double getDerivation(){
        return this.derivation;
    }
}
