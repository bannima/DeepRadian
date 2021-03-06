package test.java;

import main.component.layer.DenseLayer;
import main.component.loss.SquareLoss;
import main.component.optimizer.GradientDescent;
import main.component.utils.UtilTools;
import main.model.Model;

/**
 * Created by Barry on 18/10/11.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("--------------- model start ---------------");
        String path = "./model.txt";
        Model model = new Model();
        model.add(new DenseLayer(2));
        model.add(new DenseLayer(5));
        model.add(new DenseLayer(1));
        model.setOptimizer(new GradientDescent(0.5));
        model.setLoss(new SquareLoss());
        int times = 1000000;
        if (model.compile()) {
            //model.summary();
            model.run(loadInputs(),loadLabels(),times);
        }
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
        double[] input = {1,0};
        double[] output = model.calculate(input);

    }

    public static double[][] loadInputs() {
        double[][] inputs = {{0,1},{1,0},{1,1},{0,0}};

        return inputs;
    }

    public static double[][] loadLabels(){
        double[][] labels = {{1},{1},{0},{0}};
        return labels;
    }
}
