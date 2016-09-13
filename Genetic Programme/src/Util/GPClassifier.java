package Util;

import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Add3;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import DAO.Instance;

public class GPClassifier extends GPProblem {
	public static final int X = 1;
    public static final int Y = 2;
    // terminal set
    private static Variable xValue;
    private static Variable yValue;


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
                CommandGene.IntegerClass,
                CommandGene.IntegerClass
        };

        // Then, we define the arguments of the GP parts. Normally, only for ADF's
        // there is a specification here, otherwise it is empty as in first case.
        // -----------------------------------------------------------------------
        Class[][] argTypes = {
                // Result producing chromosome
                {},
                // ADF1
                {
                        CommandGene.IntegerClass,
                        CommandGene.IntegerClass,
                        CommandGene.IntegerClass
                }
        };

        // Next, we define the set of available GP commands and terminals to use.
        // Please see package org.jgap.gp.function and org.jgap.gp.terminal
        // You can easily add commands and terminals of your own.
        // ----------------------------------------------------------------------
		CommandGene[][] nodeSets = {
				{
				// We create 8 variables that correspond to the data attributes
				// and will be set in the fitness function.
		        // ----------------------------------------------------------
				xValue = Variable.create(conf, "xvalue", CommandGene.IntegerClass),
				xValue = Variable.create(conf, "yvalue", CommandGene.IntegerClass),
				
				new Multiply(conf, CommandGene.IntegerClass), new Add(conf, CommandGene.IntegerClass),
				new Divide(conf, CommandGene.IntegerClass), new Subtract(conf, CommandGene.IntegerClass),
				new Terminal(conf, CommandGene.IntegerClass, -1, 10, true),

			},
				{ 
				new Add3(conf, CommandGene.IntegerClass), }
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
            double correct = 0.0f;
            for (Instance instance : instances) {
                xValue.set(instance.getxValue());
                yValue.set(instance.getyValue());
                
                try {
                    int result = program.execute_int(0, new Object[0]);
                    int predictClass;

                    if (result > 0) {
                    	predictClass = X;
                    } else {
                    	predictClass = Y;
                    }
                    if(predictClass == instance.getClassLabel()){
                    	correct++;
                    }
                    
                } catch (ArithmeticException exception) {
                    System.err.println("x = " + instance);
                    System.err.println(program);
                    throw exception;
                }
            }
            return (correct / instances.size()) * 100;
        }
    }
}