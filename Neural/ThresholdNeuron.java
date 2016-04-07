package neural;

/** A linear threshold neuron. */
public class ThresholdNeuron extends Neuron {

	public ThresholdNeuron(Neuron[] inputs, double[] weights, double bias) {
		super(inputs, weights, bias);
	}

	@Override
	protected double squash(double sum) {
		return sum > 0 ? 1 : 0;
	}

}
