package weka.api;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Test;

public class CClassifierTest {

	@Test
	public void setClassifierTest() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		CClassifier klasifikator = new CClassifier();
		
		String path = "classifiers.J48";
		
		klasifikator.setClassifier(path);
		
		String classifier = klasifikator.toString();
		
		System.out.print(classifier);
		
		assertEquals("class " + path, classifier);
	}

}
