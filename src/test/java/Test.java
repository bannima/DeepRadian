package test.java;

import main.component.layer.DenseLayer;
import main.component.loss.SquareLoss;
import main.component.optimizer.GradientDescent;
import main.component.utils.UtilTools;
import main.component.weights.WeightMatrix;
import main.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry on 18/10/11.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("--------------- model start ---------------");
        String path = "./model.txt";
        Model model = new Model();
        model.add(new DenseLayer(2));
        model.add(new DenseLayer(2));
        model.add(new DenseLayer(1));

        List<WeightMatrix> allWeights = new ArrayList<>();
        double[][] weight1 = {{0.1,0.4},{0.8,0.6}};
        WeightMatrix matrix1 = new WeightMatrix(weight1);
        double[][] weight2 = {{0.3},{0.9}};
        WeightMatrix matrix2 = new WeightMatrix(weight2);
        allWeights.add(matrix1);
        allWeights.add(matrix2);

        model.setOptimizer(new GradientDescent(1));
        model.setLoss(new SquareLoss());
        int times = 100;
        if (model.compile(allWeights)) {
            //show init state
            model.showParams();
            //model.summary();
            model.run(loadInputs(),loadLabels(),times);
        }

        //show params
        model.showParams();

        model.saveModel(path);
        System.out.println("--------------- model end ---------------");

        //show model
        for (int i=0;i<loadInputs().length;i++){
            double[] input = loadInputs()[i];
            double[] labels = loadLabels()[i];
            double[] output = model.calculate(input);
            System.out.println("input: "+UtilTools.toString(input)
                    +" output: "+ UtilTools.toString(output) + " expected: "+ UtilTools.toString(labels));
        }
    }

    public static double[][] loadInputs() {
        double[][] inputs = {{0.35,0.9}};
        return inputs;
    }

    public static double[][] loadLabels(){
        double[][] labels = {{0.5}};
        return labels;
    }
}
