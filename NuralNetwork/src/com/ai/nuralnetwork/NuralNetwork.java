/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.nuralnetwork;

/**
 *
 * @author j3rry
 */
public class NuralNetwork {

    int input_nodes, hidden_nodes, output_nodes;
    float learning_rate;
    Matrix weights_ih, weights_ho, bias_h, bias_o;

    public NuralNetwork(int input_nodes, int hidden_nodes, int output_nodes) {
        this.input_nodes = input_nodes;
        this.hidden_nodes = hidden_nodes;
        this.output_nodes = output_nodes;

        this.weights_ih = new Matrix(this.hidden_nodes, this.input_nodes);
        this.weights_ho = new Matrix(this.output_nodes, this.hidden_nodes);
        this.weights_ih.randomize();
        this.weights_ho.randomize();

        this.bias_h = new Matrix(this.hidden_nodes, 1);
        this.bias_o = new Matrix(this.output_nodes, 1);
        this.bias_h.randomize();
        this.bias_o.randomize();
        this.learning_rate = 0.1f;
    }

    public float[][] feedforward(float[] input_array) {

        // Generating the Hidden Outputs
        Matrix inputs = Matrix.fromArray(input_array);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        
        hidden.add(this.bias_h);
        // activation function!
        hidden.map(Matrix.SIG);
        
        // Generating the output's output!
        Matrix output = Matrix.multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.map(Matrix.SIG);
        
        // Sending back to the caller!
        return output.toArray();
    }

    public void train(float[] input_array, float[] target_array) {
        // Generating the Hidden Outputs
        Matrix inputs = Matrix.fromArray(input_array);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        // activation function!
        hidden.map(Matrix.SIG);

        // Generating the output's output!
        Matrix outputs = Matrix.multiply(this.weights_ho, hidden);
        outputs.add(this.bias_o);
        outputs.map(Matrix.SIG);

        // Convert array to matrix object
        Matrix targets = Matrix.fromArray(target_array);

        // Calculate the error
        // ERROR = TARGETS - OUTPUTS
        Matrix output_errors = Matrix.subtract(targets, outputs);

        // let gradient = outputs * (1 - outputs);
        // Calculate gradient
        Matrix gradients = outputs;
        gradients.map(Matrix.DSIG);

        gradients.multiply(output_errors);
        gradients.multiply(this.learning_rate);

        // Calculate deltas
        Matrix hidden_T = Matrix.transpose(hidden);
        Matrix weight_ho_deltas = Matrix.multiply(gradients, hidden_T);

        // Adjust the weights by deltas
        this.weights_ho.add(weight_ho_deltas);
        // Adjust the bias by its deltas (which is just the gradients)
        this.bias_o.add(gradients);

        // Calculate the hidden layer errors
        Matrix who_t = Matrix.transpose(this.weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_t, output_errors);

        // Calculate hidden gradient
        Matrix hidden_gradient = hidden;
        hidden_gradient.map(Matrix.DSIG);
        hidden_gradient.multiply(hidden_errors);
        hidden_gradient.multiply(this.learning_rate);

        // Calcuate input->hidden deltas
        Matrix inputs_T = Matrix.transpose(inputs);
        Matrix weight_ih_deltas = Matrix.multiply(hidden_gradient, inputs_T);

        this.weights_ih.add(weight_ih_deltas);
        // Adjust the bias by its deltas (which is just the gradients)
        this.bias_h.add(hidden_gradient);

        // outputs.print();
        // targets.print();
        // error.print();
    }

}
