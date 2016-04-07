package neural;

/** A feed-forward network with input, hidden, and output layers. */
public class Network {

	/** The input neurons. */
	private InputNeuron[] inputLayer;

	/** The hidden neurons. */
	private SigmoidNeuron[] hiddenLayer;

	/** The output neurons. */
	private SigmoidNeuron[] outputLayer;

	/**
	 * @param in
	 *            Number of input neurons.
	 * @param hid
	 *            Number of hidden neurons.
	 * @param out
	 *            Number of output neurons.
	 */
	public Network(int in, int hid, int out) {
		// TODO You have to do this one
		inputLayer = new InputNeuron[in];
		for(int i=0; i<in; i++) {
			inputLayer[i] = new InputNeuron();
		}
		hiddenLayer = new SigmoidNeuron[hid];
		for(int i=0; i<hid; i++) {
			hiddenLayer[i] = new SigmoidNeuron(inputLayer);
		}
		outputLayer = new SigmoidNeuron[out];
		for(int i=0; i<out; i++) {
			outputLayer[i] = new SigmoidNeuron(hiddenLayer);
		}
	}

	/**
	 * Returns the specified neuron. The input layer is layer 0, hidden 1,
	 * output 2.
	 */
	protected Neuron getNeuron(int layer, int index) {
		// TODO You have to do this one
		if(layer==0)
			return inputLayer[index];
		else if(layer==1)
			return hiddenLayer[index];
		else
			return outputLayer[index];
	}

	/**
	 * Returns the sum, over a set of training examples and across all outputs,
	 * of the square of the difference between actual and correct outputs. If
	 * learning is working properly, this should decrease over the course of
	 * training.
	 */
	public double meanSquaredError(double[][] inputs, double[][] correct) {
		double sum = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			run(inputs[i]);
			for (int j = 0; j < outputLayer.length; j++) {
				sum += Math.pow(correct[i][j] - outputLayer[j].getOutput(), 2);
			}
		}
		return sum / (inputs.length * outputLayer.length);
	}

	/** Feeds inputs through the network, updating the output of each neuron. */
	public double[] run(double[] inputs) {
		// TODO You have to do this one
		//System.out.println("Inputs: " + inputs[0] + " " + inputs[1]);
		double[] outputs = new double[outputLayer.length];
		for(int j=0; j<inputLayer.length; j++) {
			inputLayer[j].setOutput(inputs[j]);	
			//System.out.println(inputLayer[j].getOutput());
		}
		for(int j=0; j<hiddenLayer.length; j++) {
			hiddenLayer[j].update();
			//System.out.println(hiddenLayer[j].getOutput());
		}
		for(int j=0; j<outputLayer.length; j++) {
			outputLayer[j].update();
			//System.out.println(outputLayer[j].getOutput());
			outputs[j] = outputLayer[j].getOutput();
			/*if(outputs[j]<0.5)
				outputs[j] = 0.0;
			else
				outputs[j] = 1.0;*/
		}
		return outputs;
	}

	@Override
	public String toString() {
		String result = "";
		result += "OUTPUT UNITS:\n";
		for (int i = 0; i < outputLayer.length; i++) {
			result += i + ": " + outputLayer[i] + "\n";
		}
		result += "HIDDEN UNITS:\n";
		for (int i = 0; i < hiddenLayer.length; i++) {
			result += i + ": " + hiddenLayer[i] + "\n";
		}
		return result + "(" + inputLayer.length + " INPUT UNITS)\n";
	}

	/**
	 * Slightly modifies this network's weights to cause it to response to inputs
	 * with something closer to the correct outputs.
	 */
	public void train(double[] inputs, double[] correct) {
		// TODO You have to do this one
		// This is a long method, with the following steps:
		// Feed the input forward through the network
		// Update deltas for output layer
		// Update deltas for hidden layer
		// Update weights for output layer
		// Update weights for hidden layer
		double sumWeights = 0;
		double g = 1.0;
		double[] outputs = run(inputs);
		for(int i=0; i<outputLayer.length; i++)
		{
			outputLayer[i].setDelta((outputs[i]) * (1-outputs[i]) * (correct[i]-outputs[i]));
			//System.out.print("Correct: " + correct[i]);
			//System.out.println(" Error: " + (correct[i]-outputs[i]));
			//System.out.println(" Delta: " + (outputs[i]) * (1-outputs[i]) * (correct[i]-outputs[i]));
		}
		for(int i=0; i<outputLayer.length; i++) {
			for(int j=0; j<outputLayer[i].getWeights().length; j++)
				sumWeights += outputLayer[i].getWeights()[j];
			g*=sumWeights*outputLayer[i].getDelta();
			sumWeights = 0;
		}	
		for(int i=0; i<hiddenLayer.length; i++)
		{
			hiddenLayer[i].setDelta(hiddenLayer[i].getOutput() * (1-hiddenLayer[i].getOutput()) * g);
		}
		for(int i=0; i<outputLayer.length; i++)
		{
			outputLayer[i].updateWeights();
			//System.out.println("o: " + outputLayer[i].toString());
		}
		for(int i=0; i<hiddenLayer.length; i++) 
		{
			//System.out.print(i + " ");
			hiddenLayer[i].updateWeights();
			//System.out.println("h: " + hiddenLayer[i].toString());
		}
		//double[] finished = run(inputs);
		/*for(int i=0; i<finished.length; i++)
			System.out.print(finished[i] + " ");
		System.out.println();*/
	}
}
