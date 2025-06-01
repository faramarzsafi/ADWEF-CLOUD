package common.tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;

import common.share;

public class Audio {
	
	public void continuousPlay(){
		try {
		    // From file
		    AudioInputStream stream = AudioSystem.getAudioInputStream(new File(share.AUDIO_FILE_PATH));

		    // From URL
//		    stream = AudioSystem.getAudioInputStream(new URL("http://hostname/audiofile"));

		    // At present, ALAW and ULAW encodings must be converted
		    // to PCM_SIGNED before it can be played
		    AudioFormat format = stream.getFormat();
		    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
		        format = new AudioFormat(
		                AudioFormat.Encoding.PCM_SIGNED,
		                format.getSampleRate(),
		                format.getSampleSizeInBits()*2,
		                format.getChannels(),
		                format.getFrameSize()*2,
		                format.getFrameRate(),
		                true);        // big endian
		        stream = AudioSystem.getAudioInputStream(format, stream);
		    }

		    // Create line
		    SourceDataLine.Info info = new DataLine.Info(
		        SourceDataLine.class, stream.getFormat(),
		        ((int)stream.getFrameLength()*format.getFrameSize()));
		    SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		    line.open(stream.getFormat());
		    line.start();

		    // Continuously read and play chunks of audio
		    int numRead = 0;
		    byte[] buf = new byte[line.getBufferSize()];
		    while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
		        int offset = 0;
		        while (offset < numRead) {
		            offset += line.write(buf, offset, numRead-offset);
		        }
		    }
		    line.drain();
		    line.stop();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (LineUnavailableException e) {
		} catch (UnsupportedAudioFileException e) {
		}
	}

	public void play(int noOfPlay){
		try {
		    // From file
		    AudioInputStream stream = 
		    	AudioSystem.getAudioInputStream(new File("/opt/Waves/chimes.wav"));

		    // From URL
//		    stream = AudioSystem.getAudioInputStream(new URL("http://hostname/audiofile"));

		    // At present, ALAW and ULAW encodings must be converted
		    // to PCM_SIGNED before it can be played
		    AudioFormat format = stream.getFormat();
		    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
		        format = new AudioFormat(
		                AudioFormat.Encoding.PCM_SIGNED,
		                format.getSampleRate(),
		                format.getSampleSizeInBits()*2,
		                format.getChannels(),
		                format.getFrameSize()*2,
		                format.getFrameRate(),
		                true);        // big endian
		        stream = AudioSystem.getAudioInputStream(format, stream);
		    }

		    // Create the clip
		    DataLine.Info info = new DataLine.Info(
		        Clip.class, stream.getFormat(), ((int)stream.getFrameLength()*format.getFrameSize()));
		    Clip clip = (Clip) AudioSystem.getLine(info);

		    // This method does not return until the audio file is completely loaded
		    clip.open(stream);

		    // Start playing
		    clip.start();
//		    clip.loop(noOfPlay);
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (LineUnavailableException e) {
		} catch (UnsupportedAudioFileException e) {
		}
	}
	
	public void repeatPlay(int nop){
		int i=0;	
		do{
			play(i);
			i++;
		}while(i<nop);
	}
		
	
	public static void main(String[] args) {
		Audio aud = new Audio();
		aud.repeatPlay(3000);
	}
}
