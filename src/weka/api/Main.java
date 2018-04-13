package weka.api;

import java.util.Random;

//Weka handles .arff (attribute relation file format) and .csv formats
import weka.classifiers.Evaluation;
//import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

	public static void main(String[] args) {
		try {
			
			/*
			 * STARTING CODE
			 * 
			DataSource source = new DataSource("C:\\Users\\vitom\\Desktop\\iris.arff");
			
			Instances dataset = source.getDataSet(); 
			  
			//class index treba postaviti na zadnji atribut
			dataset.setClassIndex(dataset.numAttributes() - 1);
			
			//create and build classifier
			//TODO: za kasnije bi bilo dobro diskretizirati vrijednosti prije buildanja classifiera da vidimo ako podrzava odredeni tip
			NaiveBayes nb = new NaiveBayes();
			
			//Ako zelim postaviti neke opcije to radim ovdje prije buildanja
			//1. Napravi string array optionsa koje zelimo staviti i spremimo ih, npr. za 2 opcije:
			//String[] options = new String[2];
			//options[0] = "-U";
			//options[1] = "opcija2";
			//nb.setOptions(options);
			
			nb.buildClassifier(dataset);
			
			//isprintaj capabilities od tog classifiera (atribute koje moze primiti)
			
			System.out.println(nb.getCapabilities().toString());
			*
			*
			*/
			
			DataSource source = new DataSource("C:\\Users\\vitom\\Desktop\\iris.arff");
			
			Instances trainDataset = source.getDataSet();
			
			trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
			
			J48 tree = new J48();
			
			tree.buildClassifier(trainDataset);
			
			Evaluation eval = new Evaluation(trainDataset);
			
			DataSource source1 = new DataSource("C:\\Users\\vitom\\Desktop\\iris-test.arff");
			Instances testDataset = source1.getDataSet();
			testDataset.setClassIndex(testDataset.numAttributes() - 1);
			
			eval.evaluateModel(tree, trainDataset);
			
			//za cross validation koristimo sljedeci kod umjesto linije 63
			//Random random = new Random(1);
			//int numFolds = 10;
			//eval.crossValidateModel(tree, testDataset, numFolds, random);
			
			System.out.println(eval.toSummaryString("Eval results:\n", false));
			System.out.println(eval.toMatrixString("=== Overall confusion matrix ===\n"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("IO exception has been thrown, maybe this file does not exist.");
		}
	}

}
