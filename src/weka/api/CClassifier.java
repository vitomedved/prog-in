package weka.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.TechnicalInformation;


/*
 * Constructor only creates instance of a class.
 * Use setClassifier to load class and create instance of certain classifier.
 * Then use methods as if this was classifier itself.
 * */
public class CClassifier extends weka.classifiers.AbstractClassifier{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Class<?> m_c = null;
	private Object m_o = null;
	
	CClassifier()
	{
		super();
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
	
	private Object castMethod(String methodName, Class<?> paramTypes[], Object[] argumentList)
	{
		Method method = null;
		try {
			method = m_c.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object retObj = null;
		try {
			retObj = method.invoke(m_o, argumentList);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retObj;
	}
	
	private Object castMethod(String methodName)
	{
		Method method = null;
		try {
			method = m_c.getMethod(methodName);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object retObj = null;
		try {
			retObj = method.invoke(m_o);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retObj;
	}
	
	public Capabilities getCapabilities()
	{
		return (Capabilities)this.castMethod("getCapabilities");
	}
	
	public String globalInfo()
	{
		return (String)this.castMethod("globalInfo");
	}
	
	public TechnicalInformation getTechnicalInformation()
	{
		return (TechnicalInformation)this.castMethod("getTechnicalInformation");
	}
	
	public void buildClassifier(Instances trainingSet)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instances.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = trainingSet;
		
		/*Method build = m_c.getMethod("buildClassifier", paramTypes);
		
		
		
		Object retObj = build.invoke(m_o, argumentList);*/
		
		this.castMethod("buildClassifier", paramTypes, argumentList);
	}
	
	public void updateClassifier(Instance instance)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instance.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = instance;
		
		this.castMethod("updateClassifier", paramTypes, argumentList);
	}
	
	public double[] distributionForInstance(Instance instance)
    {
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instance.class;
		
		//Method build = m_c.getMethod("distributionForInstance", paramTypes);
		
		Object[] argumentList = new Object[1];
		argumentList[0] = instance;
		
		//Object retObj = build.invoke(m_o, argumentList);
		
		//System.out.println(retObj);
		
		//return (double[]) retObj;
		
		return (double[])this.castMethod("distributionForInstance", paramTypes, argumentList);
    }
	
	@SuppressWarnings("unchecked")
	public Enumeration<Option> listOptions()
	{		
		return (Enumeration<Option>)this.castMethod("listOptions");
	}
}
