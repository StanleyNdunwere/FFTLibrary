package com.company;

import javax.sound.sampled.AudioFormat;
import java.util.HashMap;

public class AudioFormatter {

    protected AudioFormat.Encoding encoding;

    protected float sampleRate;

    protected int sampleSizeInBits;

    protected int channels;

    protected int frameSize;

    protected float frameRate;

    protected boolean bigEndian;


    /**
     * The set of properties
     */
    private HashMap<String, Object> properties;

    public static final int NOT_SPECIFIED = -1;


    public AudioFormatter(AudioFormat.Encoding encoding, float sampleRate, int sampleSizeInBits,
                          int channels, int frameSize, float frameRate, boolean bigEndian) {

        this.encoding = encoding;
        this.sampleRate = sampleRate;
        this.sampleSizeInBits = sampleSizeInBits;
        this.channels = channels;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.bigEndian = bigEndian;
        this.properties = null;
    }


}
