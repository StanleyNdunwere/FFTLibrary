package com.company;

import javax.sound.sampled.*;

public class Generate {
    public static void main(String[] args) throws Exception {
        float sampleRate = 44100;
        double f = 261.626;
        double a = .5;
        double twoPiF = 2*Math.PI*f;

        double[] buffer = new double[44100*4];
        for (int sample = 0; sample < buffer.length; sample++) {
            double time = (sample/2) / sampleRate;
            double a1 = a*Math.sin(Math.PI*time);
            double a2 = a*Math.cos(Math.PI*time);
            buffer[sample++] = a1 * Math.sin(twoPiF*time);    // channel 1
            buffer[sample] = a2 * Math.sin(twoPiF*time);      // channel 2
        }

        byte[] byteBuffer = new byte[buffer.length];
        int idx = 0;
        for (int i = 0; i < byteBuffer.length; ) {
            int x = (int) (buffer[idx++]*127);
            byteBuffer[i++] = (byte) x;
        }

        boolean bigEndian = false;
        boolean signed = true;
        int bits = 8;
        int channels = 2;
        AudioFormat format = new AudioFormat(sampleRate,bits,channels,signed,bigEndian);

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        long now = System.currentTimeMillis();
        System.out.println("buffer size:" + line.available());
        int written = line.write(byteBuffer, 0, byteBuffer.length);
        System.out.println(written + " bytes written.");
        line.drain();
        line.close();
        long total = System.currentTimeMillis() - now;
        System.out.println(total + " ms.");
    }
}
