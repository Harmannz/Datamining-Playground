package dao;

import java.util.Map;

public class FeatureVector {

	private Map<Feature, Double> features;
	
	public FeatureVector(Map<Feature, Double> features) {
		this.features = features;
	}
	
	public Map<Feature, Double> getFeatures() {
		return features;
	}
	
	public void setFeatures(Map<Feature, Double> features) {
		this.features = features;
	}
	
	@Override
	public String toString() {
		return "FeatureVector [features=" + features + "]";
	}

	public enum Feature {
	    RightEyeMean("RightEyeMean"),
	    LeftEyeMean("LeftEyeMean"),
	    NoseMean("NoseMean"),
	    LipsMean("LipsMean"),
	    RightEyeSD("RightEyeSD"),
	    LeftEyeSD("LeftEyeSD"),
	    NoseSD("NoseSD"),
	    LipsSD("LipsSD");

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
