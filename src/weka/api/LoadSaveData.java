package weka.api;


import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

import weka.core.converters.ConverterUtils.DataSource;

public class LoadSaveData {
	
	public void LoadData(String filePath)
	{
		
		try {
			DataSource source = new DataSource("path-to-file");
			
			Instances dataset = source.getDataSet();
			
			System.out.println(dataset.toSummaryString());
			
			ArffSaver saver = new ArffSaver();
			saver.setInstances(dataset);
			saver.setFile(new File("C:\\Users\\vitom\\Desktop\\new.arff"));
			saver.writeBatch();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void CSVtoARFF(String csvPath, String destination) throws IOException
	{
		//Load CSV file
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvPath));
		Instances data = loader.getDataSet();
		
		//Save to ARFF file
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		//Save as ARFF
		saver.setFile(new File(destination));
		saver.writeBatch();
	}
}
