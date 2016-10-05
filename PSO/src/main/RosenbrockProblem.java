package main;

import java.util.List;

import pso.Problem;

public class RosenbrockProblem extends Problem{

	public RosenbrockProblem() {
        setMinimization(true);
        setMinDomain(-30);
        setMaxDomain(30);
        setMaxVelocity(20);
    }
	
	@Override
	public double fitness(List<Double> position) {
		double fitness = 0;
        for (int i = 0; i < position.size() - 1; i++) {
        	double x1 = position.get(i), x2 = position.get(i+1);
        	fitness += 100 * Math.pow((Math.pow(x1, 2) - x2), 2);
        	fitness += Math.pow(x1 - 1, 2);
        }
        return fitness;
	}

}
