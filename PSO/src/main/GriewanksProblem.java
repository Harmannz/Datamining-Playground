package main;

import java.util.List;

import pso.Problem;

public class GriewanksProblem extends Problem {

	public GriewanksProblem(){
		setMinimization(true);
        setMinDomain(-30);
        setMaxDomain(30);
        setMaxVelocity(20);
	}
	
	@Override
	public double fitness(List<Double> position) {
		double fitness = 0;
		double sum1 = 0, sum2 = position.isEmpty() ? 0: 1;
        for (int i = 0; i < position.size(); i++) {
        	double x = position.get(i);
        	sum1 += x*x/4000.0;
        	sum2 *= Math.cos(x/(Math.sqrt(i+1)));
        }
        fitness = sum1 - sum2 + 1;
        return fitness;
	}

}
