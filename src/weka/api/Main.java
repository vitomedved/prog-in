package weka.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.RefineryUtilities;

//Weka handles .arff (attribute relation file format) and .csv formats
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
//import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


//TODO: use classloader from java libs to get classes by name???

public class Main {

	public static void main(String[] args) {
		try {			
			//Get data
			DataSource source = new DataSource("C:\\Users\\vitom\\Desktop\\iris.arff");
			Instances dataset = source.getDataSet();
			dataset.setClassIndex(dataset.numAttributes() - 1);
			
			//create classificator
			CClassifier bayes = new CClassifier();
			bayes.setClassifier("weka.classifiers.bayes.NaiveBayes");
			//bayes.buildClassifier(dataset);
			
			System.out.println("========================================================================");
			//EvaluateModel(bayes, dataset);
			System.out.println("========================================================================");
			//CrossClassify(bayes, dataset, 1, 10);
			System.out.println("========================================================================");			
			//PredictInstance(bayes, dataset, dataset.instance(5));
			
			/*
			Evaluation eval = new Evaluation(dataset);
			Random random = new Random(1);
			int numFolds = 10;
			eval.crossValidateModel(bayes, dataset, numFolds, random);
			
			System.out.println(eval.toSummaryString());
			*/
			//CGUI window = new CGUI();
			//window.open();
			
			CrossClassify(bayes, dataset, 1, 10);
			
			CClassifier j48 = new CClassifier();
			j48.setClassifier("weka.classifiers.trees.J48");
			
			CrossClassify(j48, dataset, 5, 10);
			
			
			BoxAndWhiskerCategoryDataset m_dataset = CrossClassify(j48, dataset, 5, 10);;
			
			
			
			BoxPlot plot = new BoxPlot("Test", m_dataset);
			plot.pack();
			RefineryUtilities.centerFrameOnScreen(plot);
			plot.setVisible(true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static CClassifier runClassificator(String path, Instances dataset) throws Exception
	{
		CClassifier classificator = new CClassifier();
		classificator.setClassifier(path);
		classificator.buildClassifier(dataset);
		
		return classificator;
	}
	
	@SuppressWarnings("unchecked")
	private static BoxAndWhiskerCategoryDataset CrossClassify(CClassifier bayes, Instances randData, int seed, int folds) throws Exception
	{
		Random rand = new Random(seed);
		
		//randomizes the dataset
		randData.randomize(rand);
		
		//check if attribute of dataset is nominal
		//if yes, stratify data on folds (ubiti podijeli set podataka na folds komada)
		if(randData.classAttribute().isNominal())
		{
			randData.stratify(folds);
		}
		
		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		@SuppressWarnings("rawtypes")
		List[] list = new ArrayList[3];
		list[0] = new ArrayList();
		list[1] = new ArrayList();
		list[2] = new ArrayList();
		
		//uvijek ce uzeti random data i iz njega napraviti test i train podatke koje onda evaluira medusobno
		for(int i = 0; i < folds; i++)
		{
			System.out.println("=== FOLD: " + (i+1) + "/" + folds + " ===");
			
			Evaluation eval = new Evaluation(randData);
			//get the folds
			Instances train = randData.trainCV(folds, i);
			Instances test = randData.testCV(folds, i);
			
			System.out.println(train.numInstances() + " train instanci");
			System.out.println(test.numInstances() + " test instanci");
			
			//System.out.println(train.toSummaryString());
			//System.out.println(test.toSummaryString());
			
			bayes.buildClassifier(train);
			eval.evaluateModel(bayes, test);
			
			System.out.println("num TP: " + eval.numTruePositives(0) + ", " + eval.numTruePositives(1) + ", " + eval.numTruePositives(2) + ", sum: " + (eval.numTruePositives(0) + eval.numTruePositives(1) + eval.numTruePositives(2)));
			System.out.println("num TN: " + eval.numTrueNegatives(0) + ", " + eval.numTrueNegatives(1) + ", " + eval.numTrueNegatives(2) + ", sum: " + (eval.numTrueNegatives(0) + eval.numTrueNegatives(1) + eval.numTrueNegatives(2)));
			
			
			for(int j = 0; j < 3; j++)
			{
				double gm = Math.sqrt(eval.truePositiveRate(j) * eval.trueNegativeRate(j));
				System.out.println("GM za klasu " + j + ": " + gm);

				list[j].add(gm);
			}
			
			System.out.println();
		}
		
		for(int x = 0; x < 3; x++)
		{
			dataset.add(list[x], "Class " + x, "Class " + x);
		}
		
		return dataset;
	}
	
	private static void EvaluateModel(CClassifier classificator, Instances dataset) throws Exception
	{
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(classificator, dataset);
		System.out.println(classificator.toString());
		System.out.println(eval.toSummaryString("Eval results:\n", false));
		System.out.println(eval.toMatrixString("=== Overall confusion matrix ===\n"));
	}

	public static void PredictInstance(CClassifier classificator, Instances dataset, Instance newInstance) throws Exception
	{
		for(int i = 0; i < dataset.numClasses(); i++)
		{
			System.out.println("Class value "+i+" is " + dataset.classAttribute().value(i));
		}
		
		int correct = 0;
		int incorrect = 0;
		
		//System.out.println("=== Actual class === Predicted ===");
		System.out.println("Prediction for instance: " + newInstance.toString());
		for(int i = 0; i < dataset.numInstances(); i++)
		{
			double actualClass = dataset.instance(i).classValue();
			
			String actual = dataset.classAttribute().value((int)actualClass);
			
			//Instance newInstance = dataset.instance(i);
			
			double predictedNB = classificator.classifyInstance(newInstance);
			//System.out.println(actualClass + " " +predictedNB);
			String predicted = dataset.classAttribute().value((int) predictedNB);
			//System.out.println(actual + " === " + predicted);
			if(actualClass != predictedNB)
			{
				incorrect++;
			}
			else
			{
				correct++;
			}
		}
		
		System.out.println("Correctly predicted: " + correct + ", incorrectly predicted: " + incorrect);
	}
}
