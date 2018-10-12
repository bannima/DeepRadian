package main.component.layer;

import main.component.loss.AbstractLoss;

/**
 * Created by Barry on 18/10/11.
 */
public class MaxPoolingLayer extends AbstractLayer {
    public MaxPoolingLayer(int neuronNums){
        super(neuronNums);
    }

    @Override
    public String getLayerType() {
        return "Max Pooling Layer";
    }
}
