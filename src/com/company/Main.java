package com.company;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class Main {

    public static float[] toFloatArray(byte[] in_buff, int in_offset,
                                float[] out_buff, int out_offset, int out_len) {
        int ix = in_offset;
        int len = out_offset + out_len;
        for (int ox = out_offset; ox < len; ox++) {
            out_buff[ox] = ((short) ((in_buff[ix++] & 0xFF) |
                    (in_buff[ix++] << 8))) * (1.0f / 32767.0f);
        }

        return out_buff;
    }

    public static float[] byteToFloat(byte[] input) {
        float[] ret = new float[input.length/4];
        for (int x = 0; x < input.length; x+=4) {
            ret[x/4] = ByteBuffer.wrap(input, x, 4).getFloat();
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {

//        AudioFormatter format = new AudioFormatter(AudioFormat.Encoding.PCM_SIGNED, 44100, 32,1,);


        AudioFormat format = new AudioFormat(44100, 16,1,true,false);
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {

        }
// Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format,16324);
            ByteArrayOutputStream out  = new ByteArrayOutputStream();
            int numBytesRead;
            float[] holder = new float[8192];
            byte[] data = new byte[line.getBufferSize()];
//            float[] display = toFloatArray(data,0, holder,0, 2048);

// Begin audio capture.
            line.start();

// Here, stopped is a global boolean set by another thread.
            while (true) {
                // Read the next chunk of data from the TargetDataLine.
                numBytesRead =  line.read(data, 0, data.length);
                // Save this chunk of data.
                out.write(data, 0, numBytesRead);

                java.io.PrintStream ps = new java.io.PrintStream("Samplefloat.txt");
                float[] val = byteToFloat(data);
                for(float f: val){
                    ps.println(f);
                }


                java.io.PrintStream ffps = new java.io.PrintStream("Samplefloat22222.txt");
                float[] vala = new float[data.length/2];
                toFloatArray(data,0,vala,0, data.length/2);
                for(float f: vala){
                    ffps.println(f);
                }



                java.io.PrintStream fps = new java.io.PrintStream("Samplebyte.txt");
                    for (byte d : data) {
                        fps.append(String.valueOf(Byte.toUnsignedInt(d)) + "\n");
                }

            }
        } catch (LineUnavailableException ex) {
            // Handle the error ...
        }


















































//        float sampleRate = 44100;
//        double f1 = 261.626;
//        double f2 = 329.628;
//        double a = .5;
//        double twoPiF1 = 2 * Math.PI * f1;
//        double twoPiF2 = 2 * Math.PI * f2;
//
//        double[] buffer = new double[44100 * 2];
//        for (int sample = 0; sample < buffer.length; sample++) {
//            double time = sample / sampleRate;
//            buffer[sample] = a * (Math.sin(twoPiF1 * time) + Math.sin(twoPiF2 * time)) / 2;
//        }
//
//        byte[] byteBuffer = new byte[buffer.length * 2];
//        int idx = 0;
//        for (int i = 0; i < byteBuffer.length; ) {
//            int x = (int) (buffer[idx++] * 32767);
//            byteBuffer[i++] = (byte) x;
//            byteBuffer[i++] = (byte) (x >>> 8);
//        }
//
//        boolean bigEndian = false;
//        boolean signed = true;
//        int bits = 16;
//        int channels = 1;
//        AudioFormat format = new AudioFormat(sampleRate, bits, channels, signed, bigEndian);
//
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
//        line.open(format);
//        line.start();
//        long now = System.currentTimeMillis();
//        System.out.println("buffer size:" + line.available());
//        int written = line.write(byteBuffer, 0, byteBuffer.length);
//        System.out.println(written + " bytes written.");
//        line.drain();
//        line.close();
//        long total = System.currentTimeMillis() - now;
//        System.out.println(total + " ms.");
    }

}


