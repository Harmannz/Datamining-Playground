package DAO;

import java.util.Map;

public class Instance {

	private float xValue;
	private float yValue;

	public Instance(float double1, float double2) {
		this.xValue = double1;
		this.yValue = double2;
	}

	public Instance(Map<String, Float> instanceData) {
		this(instanceData.get("x"), instanceData.get("y"));
	}

	public float getxValue() {
		return xValue;
	}

	public void setxValue(float xValue) {
		this.xValue = xValue;
	}

	public float getyValue() {
		return yValue;
	}

	public void setyValue(float yValue) {
		this.yValue = yValue;
	}

	@Override
	public String toString() {
		return "Instance [xValue=" + xValue + ", yValue=" + yValue + "]";
	}

}
