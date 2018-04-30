package weka.api;

public class ClassLoaderManager 
{
	//TODO: check why getClass is overriding
	public static Class getClassByPath(String className)
	{
		try {
			Class c = Class.forName("weka.api." + className);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//TODO: does printstack return null automatically?
			return null;
		}
	}
	
	public static Object getObjectByClassName(String className)
	{
		Object o = null;
		Class c = null;
		String classPath = "weka.api." + className;
		
		
		try {
			c = Class.forName(classPath);
			
			try {
				o = c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
}
