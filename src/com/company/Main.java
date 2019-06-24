package com.company;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Main {

    public static void main(String[] args) throws Exception {

        float sampleRate = 44100;
        double f1 = 261.626;
        double f2 = 329.628;
        double a = .5;
        double twoPiF1 = 2 * Math.PI * f1;
        double twoPiF2 = 2 * Math.PI * f2;

        double[] buffer = new double[44100 * 2];
        for (int sample = 0; sample < buffer.length; sample++) {
            double time = sample / sampleRate;
            buffer[sample] = a * (Math.sin(twoPiF1 * time) + Math.sin(twoPiF2 * time)) / 2;
        }

        byte[] byteBuffer = new byte[buffer.length * 2];
        int idx = 0;
        for (int i = 0; i < byteBuffer.length; ) {
            int x = (int) (buffer[idx++] * 32767);
            byteBuffer[i++] = (byte) x;
            byteBuffer[i++] = (byte) (x >>> 8);
        }

        boolean bigEndian = false;
        boolean signed = true;
        int bits = 16;
        int channels = 1;
        AudioFormat format = new AudioFormat(sampleRate, bits, channels, signed, bigEndian);

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


