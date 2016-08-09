package dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FeatureVector {

	private Map<Feature, Double> features;
	private boolean classLabel;
	
	public FeatureVector(Map<Feature, Double> features, boolean classLabel) {
		this.features = features;
		this.classLabel = classLabel;
	}
	
	public Map<Feature, Double> getFeatures() {
		return features;
	}
	
	public void setFeatures(Map<Feature, Double> features) {
		this.features = features;
	}
	
	public boolean isClassLabel() {
		return classLabel;
	}
	
	public void setClassLabel(boolean classLabel) {
		this.classLabel = classLabel;
	}	
	
	@Override
	public String toString() {
		return "FeatureVector [features=" + features + ", classLabel=" + classLabel + "]";
	}

	public enum Feature {
	    RightEyeMean("RightEyeMean"),
	    LeftEyeMean("LeftEyeMean");

	    private final String text;


	    private Feature(final String text) {
	        this.text = text;
	    }

	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	
}
