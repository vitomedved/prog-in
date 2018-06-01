package weka.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassLoaderManager extends ClassLoader 
{
	
	private final String PATH = "D://xampp//htdocs//prog-ing//classifiers//";
	
	//TODO: check why getClass is overriding
	public Class getClassByPath(String className)
	{
		/*
		try {
			Class c = Class.forName(className);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//TODO: does printstack return null automatically?
			return null;
		}*/
		File file = new File(PATH + "J48" + ".class");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];

            fileInputStream.read(bytes);

            return defineClass("J48", bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
	}
	
	public static Object getObjectByClassName(String className)
	{
		Object o = null;
		Class c = null;
		String classPath = /*"weka.api." + */className;
		
		
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
