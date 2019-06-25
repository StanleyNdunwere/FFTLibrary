package com.company;

import java.util.ArrayList;
import java.util.List;

public class DFT {


    public static void main(String[] args) throws Exception {
        float sampleRate = 44100;
        double f1 = 261.626;
        double f2 = 329.628;
        double a = .5;
        double twoPiF1 = 2 * Math.PI * f1;
        double twoPiF2 = 2 * Math.PI * f2;

        double[] bufferR = new double[2048];
        for (int sample = 0; sample < bufferR.length; sample++) {
            double time = sample / sampleRate;
            bufferR[sample] = a * (Math.sin(twoPiF1 * time) + Math.sin(twoPiF2 * time)) / 2;
        }

        double[] outR = new double[bufferR.length];
        double[] outI = new double[bufferR.length];

        dft(bufferR, outR, outI);

        double results[] = new double[outR.length];
        for (int i = 0; i < outR.length; i++) {
            results[i] = Math.sqrt(outR[i] * outR[i] + outI[i] * outI[i]);
        }

        java.io.PrintStream ps = new java.io.PrintStream("Sample.txt");
        for (double d : results) {
            ps.println(d);
        }
        ps.close();

        List<Float> found = process(results, sampleRate, bufferR.length, 4);
        for (float freq : found) {
            System.out.println("Found: " + freq);
        }
    }

    static List<Float> process(double results[], float sampleRate, int numSamples, int sigma) {
        double average = 0;
        for (int i = 0; i < results.length; i++) {
            average += results[i];
        }
        average = average / results.length;

        double sums = 0;
        for (int i = 0; i < results.length; i++) {
            sums += (results[i] - average) * (results[i] - average);
        }

        double stdev = Math.sqrt(sums / (results.length - 1));

        ArrayList<Float> found = new ArrayList<Float>();
        double max = Integer.MIN_VALUE;
        int maxF = -1;
        for (int f = 0; f < results.length / 2; f++) {
            if (results[f] > average + sigma * stdev) {
                if (results[f] > max) {
                    max = results[f];
                    maxF = f;
                }
            } else {
                if (maxF != -1) {
                    found.add(maxF * sampleRate / numSamples);
                    max = Integer.MIN_VALUE;
                    maxF = -1;
                }
            }
        }

        return (found);
    }


    static void dft(double[] inR, double[] outR, double[] outI) {
        for (int k = 0; k < inR.length; k++) {
            for (int t = 0; t < inR.length; t++) {
                outR[k] += inR[t] * Math.cos(2 * Math.PI * t * k / inR.length);
                outI[k] -= inR[t] * Math.sin(2 * Math.PI * t * k / inR.length);
            }
        }
    }
}

