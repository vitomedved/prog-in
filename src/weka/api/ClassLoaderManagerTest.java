package weka.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassLoaderManagerTest {

	@Test
	public void getClassByPathTest() {
		String classPath = "classifiers.J48";
		
		ClassLoaderManager clm = new ClassLoaderManager();
		
		Class ret = clm.getClassByPath(classPath);
		
		String expected = "class classifiers.J48";
	
		assertEquals(expected, ret.toString());
	}
}
