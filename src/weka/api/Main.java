package weka.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

//Weka handles .arff (attribute relation file format) and .csv formats
import weka.classifiers.Evaluation;
//import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//TODO: use classloader from java libs to get classes by name???

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
		
		try {
			
			//Load class by its "path"
			Class c = Class.forName("weka.api.Dummy");
			System.out.println("Loaded class: " + c.getName());
			
			//Set arguments for constructor
			//First set Class array with argument types for constructor
			Class[] arguments = new Class[1];
			arguments[0] = String.class;
			//Second create  those arguments
			String pathToFile = "path";
			try {
				//Now I can create object of that class
				Object o = c.getDeclaredConstructor(arguments).newInstance(pathToFile);
				System.out.println("Cereated object: " + o.toString());
				//Let's call a method. As we will know arguments and names of each function, we can call it like this.
				//Initialize parameters
				Class parameterTypes[] = new Class[2];
				parameterTypes[0] = Integer.TYPE;
				parameterTypes[1] = Integer.TYPE;
				//Get wanted method by name and param. types
				Method add = c.getMethod("add", parameterTypes);
				
				//Now we can use this method on object o initalized on line 95
				//Create argument values for methods
				Object[] argumentList = new Object[2];
				argumentList[0] = new Integer(2);
				argumentList[1] = new Integer(2);
				//This means: invoke method "add" on object "o" with list of arguments "argumentList"
				Object retObj = add.invoke(o, argumentList);
				System.out.println("Adding: " + argumentList[0] + " + " + argumentList[1]);
				Integer retVal = (Integer)retObj;
				System.out.println("Returned value is: " + retVal);
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
