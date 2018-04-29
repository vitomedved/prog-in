package weka.api;

public class Dummy {
	
	public String str = "";
	
	Dummy(String dummyString)
	{
		this.str = dummyString;
	}
	
	public void method1()
	{
		System.out.println("method1");
	}
	
	public void method2()
	{
		System.out.println("method2");
	}
	
	public int add(int a, int b)
	{
		return a + b;
	}
}
