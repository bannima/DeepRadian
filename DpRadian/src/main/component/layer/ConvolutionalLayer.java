package main.component.layer;

/**
 * Created by Barry on 18/10/11.
 */
public class ConvolutionalLayer extends AbstractLayer{
    public ConvolutionalLayer(int neuronNums){
        super(neuronNums);
    }

    @Override
    public String getLayerType() {
        return "Convolutional Layer";
    }
}
