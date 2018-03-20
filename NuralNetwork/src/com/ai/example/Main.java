/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai.example;

import com.ai.nuralnetwork.NuralNetwork;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author j3rry
 */
public class Main extends JFrame implements Runnable {

    int x, y;
    NuralNetwork nn;
    float[][] inputs = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    float[] targets = {0, 1, 1, 0};
    Color c;

    Main() {
        super("Demo");
        this.setBounds(100, 100, 600, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        x = 50;
        y = 50;

        nn = new NuralNetwork(2, 2, 1);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
            process();
            repaint();
        }
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, 600, 600);
        int x, y, scale;
        x = 0;
        y = 50;
        scale = 50;
        int c;

        float[][] out = new float[1][1];
        float s=10.0f;
        for (float i = 0f; i < 10; i++) {
            for (float j = 0f; j < 10; j++) {
                out = nn.feedforward(new float[]{i / s,j / s});
                c = (int) (out[0][0] * 255);
                g.setColor(new Color(c, c, c));
                g.fillRect(x, y, scale, scale);
                y += scale;
            }
            x += scale;
            y = 50;
        }
    }

    private void process() {
        float[] n = new float[2];
        float[] t = new float[1];
        for (int i = 0; i < 5000; i++) {
            int r = (int) Math.floor(Math.random() * 4 - 1) + 1;
            n[0] = inputs[r][0];
            n[1] = inputs[r][1];
            t[0] = targets[r];
            nn.train(n, t);
        }
    }

    public static void main(String args[]) {
        new Main();
    }
}

