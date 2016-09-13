package DAO;

import java.util.Map;

public class Instance {

	private int xValue;
	private int yValue;
	private int classLabel;

	public Instance(int xValue, int yValue, int classLabel) {
		this.xValue = xValue;
		this.yValue = yValue;
		this.classLabel = classLabel;
	}

	public Instance(Map<String, Integer> instanceData) {
		this.xValue = instanceData.get("x-value");
		this.yValue = instanceData.get("y-value");
		this.classLabel = instanceData.get("class-label");
	}

	public int getxValue() {
		return xValue;
	}

	public void setxValue(int xValue) {
		this.xValue = xValue;
	}

	public int getyValue() {
		return yValue;
	}

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}

	public int getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(int classLabel) {
		this.classLabel = classLabel;
	}

	@Override
	public String toString() {
		return "Instance [xValue=" + xValue + ", yValue=" + yValue + ", classLabel=" + classLabel + "]";
	}
}
