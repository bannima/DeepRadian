package main.component.layer;

import main.component.activation.AbstractActivationFunction;
import main.component.activation.Sigmod;

/**
 * Created by Barry on 18/10/11.
 */
public class DenseLayer extends AbstractLayer {

    public DenseLayer(int neuronNums) {
        super(neuronNums);
        this.setActivationFunction(new Sigmod());
    }

    public DenseLayer(int neuronNums, AbstractActivationFunction activationFunction){
        super(neuronNums);
        this.setActivationFunction(activationFunction);
    }

    @Override
    public String getLayerType() {
        return "Dense Layer";
    }
}
