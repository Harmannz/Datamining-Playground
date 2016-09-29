package Util;

import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.ADF;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Add3;
import org.jgap.gp.function.Cosine;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Exp;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Sine;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.function.Xor;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import DAO.Instance;

public class GPClassifier extends GPProblem {
    // terminal set
    private static Variable xValue;


    public static List<Instance> instances;

    public GPClassifier(List<Instance> instances, GPConfiguration config) throws InvalidConfigurationException {
        super(config);
        GPClassifier.instances = new ArrayList<>(instances);
    }

    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration conf = getGPConfiguration();
        // At first, we define the return type of the GP program.
        // ------------------------------------------------------
        Class[] types = {
        		CommandGene.FloatClass,
        		CommandGene.FloatClass
        };

        // Then, we define the arguments of the GP parts. Normally, only for ADF's
        // there is a specification here, otherwise it is empty as in first case.
        // -----------------------------------------------------------------------
        Class[][] argTypes = {
                // Result producing chromosome
                {},
                // ADF1
                {
                        CommandGene.FloatClass,
                        CommandGene.FloatClass,
                        CommandGene.FloatClass
                }
        };

        // Next, we define the set of available GP commands and terminals to use.
        // Please see package org.jgap.gp.function and org.jgap.gp.terminal
        // You can easily add commands and terminals of your own.
        // ----------------------------------------------------------------------
		CommandGene[][] nodeSets = {
				{
				// We create 2 variables that correspond to the data attributes
				// and will be set in the fitness function.
		        // ----------------------------------------------------------
				xValue = Variable.create(conf, "x", CommandGene.FloatClass),
				
				new Multiply(conf, CommandGene.FloatClass), 
				new Add(conf, CommandGene.FloatClass),
				new Divide(conf, CommandGene.FloatClass), 
				new Subtract(conf, CommandGene.FloatClass),
				new Sine(conf, CommandGene.FloatClass),
				new Cosine(conf, CommandGene.FloatClass),
				new Exp(conf, CommandGene.FloatClass),

		        new Terminal(conf, CommandGene.FloatClass, 2.0d, 10.0d, true)
			},
				{ 
				new Add3(conf, CommandGene.FloatClass), }
			};
		// ------------------------------------------------------------------------------
        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodeSets, 20, true);
    }

    public static class FitnessFunction extends GPFitnessFunction {

        @Override
        protected double evaluate(final IGPProgram a_subject) {
            return computeRawFitness(a_subject);
        }

        public double computeRawFitness(final IGPProgram program) {
            double error = 0.0f;
            for (Instance instance : instances) {
                xValue.set(instance.getxValue());

                try {
                    double result = program.execute_int(0, new Object[0]);
                    // Sum up the error between actual and expected result to get a defect
                    // rate.
                    // -------------------------------------------------------------------
                    error += Math.pow(Math.abs(result - instance.getyValue()), 2);
                    // If the error is too high, stop evlauation and return worst error
                    // possible.
                    // ----------------------------------------------------------------
                    if (Double.isInfinite(error)) {
                      return Double.MAX_VALUE;
                    }
                    
                } catch (ArithmeticException exception) {
                    System.err.println("x = " + instance);
                    System.err.println(program);
                    throw exception;
                }
            }
            //now normalise the error by dividing it by total 
            error = error / instances.size();
            // In case the error is small enough, consider it perfect.
            // -------------------------------------------------------
            if (error < 0.001) {
            	error = 0.0d;
            }
            return error;
        }
    }
}