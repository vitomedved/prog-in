package weka.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import weka.core.Instances;


/*
 * Constructor only creates instance of a class.
 * Use setClassifier to load class and create instance of certain classifier.
 * Then use methods as if this was classifier itself.
 * */
public class Classifier{
	
	private Class<?> m_c = null;
	private Object m_o = null;
	
	Classifier()
	{
		System.out.println("Instance is created, now use setClassifier(name) to load classifier");
	}
	
	public boolean setClassifier(String classifierName)
	{
		boolean ret = false;
		
		if(m_c != null || m_o != null)
		{
			m_c = null;
			m_o = null;
		}
		
		m_c = ClassLoaderManager.getClassByPath(classifierName);
		m_o = ClassLoaderManager.getObjectByClassName(classifierName);
		
		if(m_c != null && m_o != null)
		{
			ret = true;
		}
		
		return ret;
	}
	
	public void buildClassifier(Instances trainingSet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instances.class;
		
		Method build = m_c.getMethod("buildClassifier", paramTypes);
		
		Object[] argumentList = new Object[1];
		argumentList[0] = trainingSet;
		
		Object retObj = build.invoke(m_o, argumentList);
	}
}
