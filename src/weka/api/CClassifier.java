package weka.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.TechnicalInformation;
import weka.estimators.Estimator;


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
	
	public void buildClassifier(Instances trainingSet)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instances.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = trainingSet;
		
		this.castMethod("buildClassifier", paramTypes, argumentList);
	}
	
	public double[] distributionForInstance(Instance instance)
    {
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instance.class;

		Object[] argumentList = new Object[1];
		argumentList[0] = instance;
		
		return (double[])this.castMethod("distributionForInstance", paramTypes, argumentList);
    }
	
	public String toString()
	{
		return this.m_c.toString();
	}
	/*
	public void updateClassifier(Instance instance)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = Instance.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = instance;
		
		this.castMethod("updateClassifier", paramTypes, argumentList);
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
	
	@SuppressWarnings("unchecked")
	public Enumeration<Option> listOptions()
	{		
		return (Enumeration<Option>)this.castMethod("listOptions");
	}
	
	public void setOptions(String[] options)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = String[].class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = options;
		
		this.castMethod("setOptions", paramTypes, argumentList);
	}

	public String[] getOptions()
	{
		return (String[])this.castMethod("getOptions");
	}
	
	public String toString()
	{
		return (String)this.castMethod("toString");
	}
	
	protected String toStringOriginal()
	{
		return (String)this.castMethod("toStringOriginal");
	}
	
	public String useKernelEstimatorTipText()
	{
		return (String)this.castMethod("useKernelEstimatorTipText");
	}
	
	public boolean getUseKernelEstimator()
	{
		return (boolean)this.castMethod("getUseKernelEstimator");
	}
	
	public void setUseKernelEstimator(boolean v)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = boolean.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = v;
		
		this.castMethod("setUseKernelEstimator", paramTypes, argumentList);
	}

	public String useSupervisedDiscretizationTipText()
	{
		return (String)this.castMethod("useSupervisedDiscretizationTipText");
	}
	
	public void setUseSupervisedDiscretization(boolean newblah)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = boolean.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = newblah;
		
		this.castMethod("setUseSupervisedDiscretization", paramTypes, argumentList);
	}
	
	public String displayModelInOldFormatTipText()
	{
		return (String)this.castMethod("displayModelInOldFormatTipText");
	}
	
	public void setDisplayModelInOldFormat(boolean d)
	{
		Class<?> paramTypes[] = new Class[1];
		paramTypes[0] = boolean.class;
		
		Object[] argumentList = new Object[1];
		argumentList[0] = d;
		
		this.castMethod("setDisplayModelInOldFormat", paramTypes, argumentList);
	}

	public boolean getDisplayModelInOldFormat()
	{
		return (boolean)this.castMethod("getDisplayModelInOldFormat");
	}
	
	public Instances getHeader()
	{
		return (Instances)this.castMethod("getHeader");
	}
	
	public Estimator[][] getConditionalEstimators()
	{
		return (Estimator[][])this.castMethod("getConditionalEstimators");
	}
	
	public Estimator getClassEstimator()
	{
		return (Estimator)this.castMethod("getClassEstimator");
	}
	
	public String getRevision()
	{
		return (String)this.castMethod("getRevision");
	}
	
	public void finalizeAggregation()
	{
		this.castMethod("finalizeAggregation");
	}*/
}
