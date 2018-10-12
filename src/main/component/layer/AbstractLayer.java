package main.component.layer;

import main.component.basic.Neuron;
import main.component.activation.AbstractActivationFunction;
import main.component.activation.Sigmod;

/**
 * Created by Barry on 18/10/11.
 */
public abstract class AbstractLayer {
    private int neuronNums;
    private Neuron[] layer;
    private AbstractActivationFunction activationFunction;

    public AbstractLayer(int neuronNums) {
        this.neuronNums = neuronNums;
        layer = new Neuron[neuronNums];
        for(int i=0;i<neuronNums;i++){
            layer[i] = new Neuron();
        }
        this.activationFunction = new Sigmod();
    }

    public AbstractLayer(int neuronNums, AbstractActivationFunction activationFunction) {
        this(neuronNums);
        this.activationFunction = activationFunction;
    }

    public int getNeuronNums() {
        return this.neuronNums;
    }

    public Neuron getNeuron(int index){
        return layer[index];
    }

    public AbstractActivationFunction getActivationFunction() {
        return this.activationFunction;
    }


    public void setActivationFunction(AbstractActivationFunction activationFunction) {
        if (activationFunction != null && activationFunction instanceof AbstractActivationFunction) {
            this.activationFunction = activationFunction;
        }
    }

    public abstract String getLayerType();
}
