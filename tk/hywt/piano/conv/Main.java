package tk.hywt.piano.conv;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Main {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	public static final int SET_TEMPO =0x51;
	public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

	public static String load(File file) throws InvalidMidiDataException, IOException, MidiUnavailableException {
		Sequence seq = MidiSystem.getSequence(file);
		int km = 0;
		String out = "";
		String out2 = "";
		String mem="";
		String temp="";
		String snip="";
		int bpm=0;
		boolean red = false;
		Map<Integer, String> a = new HashMap<Integer, String>();
		for (Track track : seq.getTracks()) {
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				
				if(message instanceof MetaMessage) {
					MetaMessage mm = (MetaMessage) message;
					if(mm.getType()==SET_TEMPO&&red==false) {
						red=true;
					byte[] data = mm.getData();
					int tempo = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff);
					bpm = 60000000 / tempo;
				}}
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON) {
						int key = sm.getData1();
						if (event.getTick() > km || event.getTick() < km) {
							// out=out+")";
							/*
							for (int j = 1; j < (int) (event.getTick() / 250) - km; j++) {
								out = out + " ";
							}*/
							// out=out+"(";
							km = (int) (event.getTick());
						}
						if(a.get((int)event.getTick())!=null) {
						temp=a.get((int)event.getTick())+Notes.getNote(key+1);
						}else{
							temp=Notes.getNote(key+1);
						}
						// out=out+Notes.getNote(key+2);
						a.put((int) event.getTick(),temp);
					}
				}
			}
		}
		for (int j = 0; j < km; j++) {
			if (a.get(j) != null) {
				out = out + a.get(j);
				//System.out.println(j + " / " + a.get(j));
			} else {
				//System.out.println(j + " / " + a.get(j));
				out = out + " ";
			}
		}
		double space = (seq.getResolution()*(bpm/60d/4d));
		System.out.println("bpm : "+bpm+" | res : "+seq.getResolution()+" | ticks : "+km+" | out length :"+out.length()+" | space : "+space+" ; "+(seq.getResolution()/4));
		for(int k = 0; k < out.length(); k++) {
			mem=mem+out.charAt(k);
			if(k%space==0||k==out.length()-1) {
				snip=mem.replaceAll("\\s","");
				if(snip.length()>1) {
				out2=out2+"("+snip+")";
				}else if(snip.length()==0) {
					out2=out2+" ";
				}else {
					out2=out2+snip;
				}
				mem="";
			}
		}
		// System.out.println(a.keySet());
		return out2+"        ";
	}
}
