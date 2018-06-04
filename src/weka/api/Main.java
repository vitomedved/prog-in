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
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;


//TODO: use classloader from java libs to get classes by name???

public class Main {

	public static void main(String[] args) {
		try {			
			//Get data
			DataSource source = new DataSource("C:\\Users\\vitom\\Desktop\\test123.arff");
			Instances dataset = source.getDataSet();
			dataset.setClassIndex(dataset.numAttributes() - 1);
			
			//create classificator
			//CClassifier bayes = new CClassifier();
			//bayes.setClassifier("weka.classifiers.bayes.NaiveBayes");
			//bayes.buildClassifier(dataset);

			//CGUI window = new CGUI();
			//window.open();
			
			//CrossClassify(bayes, dataset, 1, 10);
			
			/*CClassifier j48 = new CClassifier();
			j48.setClassifier("weka.classifiers.trees.J48");
			
			CClassifier nb = new CClassifier();
			nb.setClassifier("classifiers.NaiveBayes");
			
			CClassifier oneR = new CClassifier();
			oneR.setClassifier("weka.classifiers.bayes.BayesNet");
			
			
			List[] arr = new ArrayList[3];
			arr[0] = new ArrayList();
			arr[1] = new ArrayList();
			arr[2] = new ArrayList();
			
			arr[0] = j48.CrossClassify(dataset, 1, 10);
			arr[1] = nb.CrossClassify(dataset, 1, 10);
			arr[2] = oneR.CrossClassify(dataset, 1, 10);
			
			DefaultBoxAndWhiskerCategoryDataset m_dataset = new DefaultBoxAndWhiskerCategoryDataset();
			
			m_dataset.add(arr[0], j48.toString(), j48.toString());
			m_dataset.add(arr[1], nb.toString(), nb.toString());
			m_dataset.add(arr[2], oneR.toString(), oneR.toString());
			//BoxAndWhiskerCategoryDataset m_dataset = new DefaultBoxAndWhiskerCategoryDataset();
			
			
			BoxPlot plot = new BoxPlot("Test", m_dataset);
			plot.pack();
			RefineryUtilities.centerFrameOnScreen(plot);
			plot.setVisible(true);*/
			
			CGUI frame = new CGUI();
			frame.setVisible(true);
			
			//LoadSaveData.CSVtoARFF("C:\\Users\\vitom\\Desktop\\JDT_R2_0.csv", "C:\\Users\\vitom\\Desktop\\test.arff");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*private static CClassifier runClassificator(String path, Instances dataset) throws Exception
	{
		CClassifier classificator = new CClassifier();
		classificator.setClassifier(path);
		classificator.buildClassifier(dataset);
		
		return classificator;
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
	}*/
}
