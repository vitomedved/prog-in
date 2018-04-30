package weka.api;

import weka.core.Instances;

public abstract class ClassifierAbstract 
{	
	abstract void buildClassifier(Instances dataset);
}
