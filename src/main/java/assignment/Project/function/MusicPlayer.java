package assignment.Project.function;

import javax.sound.sampled.*;
import java.io.File;
public class MusicPlayer {
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceDataLine;
	public MusicPlayer(String path) {
		playMusic(path);
	}
	private void playMusic(String path){
		try{
			int count;
			byte buf[] = new byte[2048];
		    audioStream = AudioSystem.getAudioInputStream(new File(path));
			audioFormat = audioStream.getFormat();
			System.out.println("音频文件: "+path);
			System.out.println("音频Encoding: "+audioFormat.getEncoding());
			if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						audioFormat.getSampleRate(), 16, audioFormat
						.getChannels(), audioFormat.getChannels() * 2,
						audioFormat.getSampleRate(), false);

				audioStream = AudioSystem.getAudioInputStream(audioFormat,
						audioStream);
			} //转换mp3文件编码结束
			//封装音频信息
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
					audioFormat,AudioSystem.NOT_SPECIFIED);
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();	
			//播放音频
			while((count = audioStream.read(buf,0,buf.length)) != -1){
				sourceDataLine.write(buf,0,count);			
			}
			//播放结束，释放资源
			sourceDataLine.drain();
			sourceDataLine.close();
			audioStream.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String path = "bin\\music.beep\\msgBeep.wav";
		new MusicPlayer(path);

	}
}