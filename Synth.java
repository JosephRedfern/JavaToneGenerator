import java.util.Scanner;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class Synth{
    private int frequency;
    private int amplitude = 127;
    private Thread runner;
    private boolean running;

    public Synth(int frequency){
        this.frequency = frequency;
        this.running = false;
    }

    public int getFrequency(){
        return this.frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public void setAmplitude(double value){
        if(value > 1){
            value = 1;
        }else if(value < 0){
            value = 0;
        }

        this.amplitude = (int)Math.round(value * 127);
    }

    public int getAmplitude(){
        return this.amplitude;
    }

    public void stop(){
        this.running = false;
    }

    public void start(){
        this.running = true;
        this.runner = new Thread(new ToneGenerator(this));
        this.runner.start();
    }


    public boolean shouldRun(){
        return this.running;
    }

    class ToneGenerator implements Runnable{
        private static final int SAMPLE_RATE = 44100;
        private static final int BUFFER_SIZE = 2200;
        private AudioFormat af;
        private SourceDataLine line;
        private Synth synth;

        public ToneGenerator(Synth synth){
            af = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);
            this.synth = synth;
            try{
                line = AudioSystem.getSourceDataLine(af);
                line.open(af, BUFFER_SIZE);
                line.start();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void run(){
            byte[] buffer = new byte[BUFFER_SIZE];

            int i = 0;

            while(synth.shouldRun()){
                double samplingInterval = (double) (2 * SAMPLE_RATE / synth.getFrequency());
                double angle = (2.0*Math.PI*i)/samplingInterval;
                buffer[i%BUFFER_SIZE] = (byte) (Math.sin(angle)*synth.getAmplitude());

                if(i%BUFFER_SIZE == 0){
                    line.write(buffer, 0, buffer.length);
                }

                i++;
            }
        }

    }
}
