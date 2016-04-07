package neural;

import static java.lang.Math.exp;
import static edu.princeton.cs.introcs.StdRandom.uniform;



/** A neuron with a logistic sigmoid activation function. */
public class SigmoidNeuron extends Neuron {

	/** Scaling constant for learning. */
	public static final double LEARNING_RATE = 0.1;
	
	/** Error of this unit. */
	private double delta;
	
	public SigmoidNeuron(Neuron[] inputs) {
		// TODO You have to do this one
		// All weights (including the bias) should be distributed
		// uniformly in the range [-0.1, 0.1).
		super(inputs, null, 0.0);
		double[] weights = new double[inputs.length]; 
		double bias=0;
		for(int i=0; i<inputs.length; i++)
			weights[i] = uniform(-0.1, 0.1);
		bias = uniform(-0.1, 0.1);
		super.setWeights(weights, bias);
	}

	/** Returns the delta (error) value of this neuron. */
	protected double getDelta() {
		return delta;
	}

	/** Sets the delta (error) value of this neuron. */
	protected void setDelta(double delta) {
		this.delta = delta;
	}
	
	@Override
	protected double squash(double sum) {
		// TODO You have to do this one
		return 1.0/(1.0+Math.exp(-sum));
	}

	/** Updates the weights for this neuron to minimize error. Assumes delta has been set. */
	public void updateWeights() {
		// TODO You have to do this one
		//System.out.print("Before: " + toString());
		double[] weights = getWeights();
		for(int i=0; i<weights.length; i++)
		{
			weights[i] += LEARNING_RATE*delta*(getInput(i).getOutput());
		}
		increaseBias(LEARNING_RATE*delta);
		//System.out.println(" After: " + toString());
	}

}
