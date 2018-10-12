package main.model;

import main.component.basic.Neuron;
import main.component.exception.MissMatchDimensionException;
import main.component.exception.WrongModelStructureException;
import main.component.exception.WrongOptimizerException;
import main.component.exception.WrongSigmodException;
import main.component.layer.AbstractLayer;
import main.component.loss.AbstractLoss;
import main.component.optimizer.AbstractOptimizer;
import main.component.activation.AbstractActivationFunction;
import main.component.utils.UtilTools;
import main.component.weights.WeightMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry on 18/10/11.
 */
public class Model {
    private List<AbstractLayer> layers = new ArrayList<>();
    private AbstractOptimizer optimizer;
    private AbstractLoss loss;
    private List<WeightMatrix> allWeights;

    public Model() {

    }

    public Model(List<AbstractLayer> layers, AbstractOptimizer optimizer, AbstractLoss loss) {
        this.layers = layers;
        this.optimizer = optimizer;
        this.loss = loss;
    }

    public void add(AbstractLayer layer) {
        if (layer != null && layer instanceof AbstractLayer)
            layers.add(layer);
    }

    public void setOptimizer(AbstractOptimizer optimizer) {
        if (optimizer != null && optimizer instanceof AbstractOptimizer) {
            this.optimizer = optimizer;
        }
    }

    public AbstractLoss getLoss() {
        return this.loss;
    }

    public void setLoss(AbstractLoss loss) {
        if (loss != null && loss instanceof AbstractLoss) {
            this.loss = loss;
        }
    }

    /*
    * check errors in model structure
    * */
    private boolean compileCheck() {

        try {
            if (this.optimizer == null) {
                throw new WrongOptimizerException("Error: NUll optimizer for current model");
            }
            if (layers.size() <= 1) {
                throw new WrongModelStructureException("ERROR: model layers is " + layers.size() + " which is no more than 1");
            }
            for (int i = 0; i < layers.size(); i++) {
                AbstractLayer currentLayer = layers.get(i);
                if (currentLayer instanceof AbstractLayer) {
                    if (currentLayer.getActivationFunction() != null
                            && currentLayer.getActivationFunction() instanceof AbstractActivationFunction) {
                        continue;
                    } else {
                        throw new WrongSigmodException("ERROR: Null sigmod for current model in layer index: " + i);
                    }

                } else {
                    throw new RuntimeException("ERROR: Wrong layer for index: " + i);
                }
            }

        } catch (WrongOptimizerException e) {
            e.printStackTrace();
            return false;
        } catch (WrongSigmodException e) {
            e.printStackTrace();
            return false;
        } catch (WrongModelStructureException e) {
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    * init all weights with random value
    * */
    public boolean compile() {
        if (!compileCheck()) {
            return false;
        }
        int frontLayerNums = -1;
        int backLayerNums = -1;
        allWeights = new ArrayList<>();
        for (int i = 0; i < layers.size(); i++) {
            AbstractLayer currentLayer = layers.get(i);
            //initialize all weights between layers
            if (i == 0 && frontLayerNums == -1) {
                frontLayerNums = currentLayer.getNeuronNums();
            } else {
                backLayerNums = currentLayer.getNeuronNums();
                WeightMatrix weightMatrix = new WeightMatrix(frontLayerNums, backLayerNums);
                allWeights.add(weightMatrix);
                frontLayerNums = backLayerNums;
            }
        }
        return true;
    }

    /*
    * init all weights with given input
    * */
    public boolean compile(List<WeightMatrix> givenWeights) {
        if (!compileCheck()) {
            return false;
        }
        allWeights = givenWeights;
        return true;
    }

    /*
    * front propagation for a single time
    * */
    private double[] frontPropagation(double[] input) {
        //check input and first layer dimension
        try {
            if (input.length != layers.get(0).getNeuronNums()) {
                throw new MissMatchDimensionException("ERROR: the dimension of input not match the first layer");
            }
        } catch (MissMatchDimensionException e) {
            e.printStackTrace();
        }

        //calculate the front propagation
        //input layer
        Neuron currentNeuron;
        for(int i=0;i<input.length;i++){
            currentNeuron = layers.get(0).getNeuron(i);
            currentNeuron.setValue(input[i]);
        }
        double[] hidden = new double[input.length];

        for (int i = 0; i < layers.size() - 1; i++) {
            WeightMatrix currentWeight = allWeights.get(i);
            hidden = new double[currentWeight.getShape()[1]];
            for (int j = 0; j < hidden.length; j++) {
                currentNeuron = layers.get(i+1).getNeuron(j);
                double[] columnWeights = currentWeight.getColumn(j);
                double tmp = UtilTools.dotProduct(input, columnWeights);
                hidden[j] = layers.get(i+1).getActivationFunction().function(
                        tmp + currentNeuron.getBias());
                currentNeuron.setValue(hidden[j]);
            }
            input = hidden;
        }
        return hidden;
    }

    /*
    * back propagation for a single time
    * */
    private void backPropagation(double[] output,double[] expected) {
        try{
            if(output.length!=expected.length){
                throw new MissMatchDimensionException("ERROR: the dimension of output and expected miss match");
            }
        }catch(MissMatchDimensionException e) {
            e.printStackTrace();
            return;
        }

        //cal the derivation of last layer
        Neuron currentNeuron;
        AbstractLayer lastLayer = layers.get(layers.size()-1);
        for (int i=0;i<output.length;i++){
            double derivation = output[i]*(1-output[i])*(expected[i]-output[i]);
            currentNeuron = lastLayer.getNeuron(i);
            currentNeuron.setDerivation(derivation);
        }

        //cal other layers of derivation and update all params
        for (int i = layers.size() - 2; i >= 0; i--) {
            WeightMatrix currentMatrix = allWeights.get(i);
            AbstractLayer currentLayer =layers.get(i);
            lastLayer = layers.get(i+1);
            //update weights and  bias
            for(int j=0;j<currentMatrix.getShape()[0];j++){
                currentNeuron = currentLayer.getNeuron(j);
                for(int k=0;k<currentMatrix.getShape()[1];k++){
                    Neuron lastNeuron = lastLayer.getNeuron(k);
                    double weight = currentMatrix.getRow(j)[k];
                    weight = weight+optimizer.getLearningRate()*lastNeuron.getDerivation()*currentNeuron.getValue();
                    //update weight
                    currentMatrix.updateWeight(j,k,weight);

                    //cal new derivation and update
                    double derivation=0.0;
                    for(int z=0;z<currentMatrix.getShape()[1];z++){
                        derivation += currentMatrix.getRow(j)[k]*lastLayer.getNeuron(z).getDerivation();
                    }
                    derivation = derivation*currentNeuron.getValue()*(1-currentNeuron.getValue());
                    currentNeuron.setDerivation(derivation);

                }
                //update bias
                double bias = currentNeuron.getBias();
                bias = bias+ (-1)*optimizer.getLearningRate()*currentNeuron.getDerivation();
                currentNeuron.setBias(bias);
            }
        }
    }


    /*
    * run current model with a single input
    * */
    private double runSingle(double[] input, double[] expected) {
        double[] output = frontPropagation(input);
        double loss = getLoss().lossFunction(output,expected);
        backPropagation(output,expected);
        return loss;
    }

    /*
    * save model into local file
    * */
    public void saveModel(String path) {

    }

    /*
    * load model from local file
    * */
    public void loadModel(String path) {

    }

    /*
    * cal input
    * */
    public double[] calculate(double[] input){
        return frontPropagation(input);
    }

    /*
    * run model with input
    * */
    public double run(double[][] inputs,double[][] expects,int times) {
        double sumLoss = 0.0;
        try{
            if (inputs.length!=expects.length){
                throw new MissMatchDimensionException("ERROR: the length of inputs and expects not match");
            }

            for(int t=0;t<times;t++){
                for(int i=0;i<inputs.length;i++){
                    double loss = runSingle(inputs[i],expects[i]);
                    sumLoss += loss;
                    //System.out.print(loss);
                }
                System.out.println(" index: "+ t + " loss: "+ (double)sumLoss/inputs.length);
                sumLoss = 0.0;
            }

        }
        catch (MissMatchDimensionException e){
            e.printStackTrace();
            return 0;
        }
        return (double) sumLoss/(inputs.length);
    }

    /*
    * show model structure
    * */
    public void summary() {
        int allParams = 0;
        System.out.println("########## model summary #############");
        for (int i = 0; i < layers.size(); i++) {
            System.out.println("layer index: " + i + "  layer type: " + layers.get(i).getLayerType()
                    + " neuron nums: " + layers.get(i).getNeuronNums());
        }
        for (int i = 0; i < allWeights.size(); i++) {
            allParams += allWeights.get(i).getParamNums();
        }
        System.out.println("########## all params nums: " + allParams + " ##########");
    }

    /*
    * show model params
    * */
    public void showParams(){
        for(int i=0;i<allWeights.size();i++){
            WeightMatrix currentMatrix = allWeights.get(i);
            currentMatrix.report();
        }
    }

}
