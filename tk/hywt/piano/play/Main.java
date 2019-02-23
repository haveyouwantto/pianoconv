package tk.hywt.piano;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import tk.hywt.piano.Notes;

public class Main {

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
	}
	public static void play(String str) throws MidiUnavailableException, InterruptedException {
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
		MidiChannel[] mChannels = midiSynth.getChannels();
		boolean multi = false;
		for (int i = 0; i < str.length(); i++) {
			String n = str.substring(i, i + 1);
			String nl = n.toLowerCase();
			int note = Notes.getNote(nl);
			System.out.print(n);
			if (note != 0) {
				{
					mChannels[0].noteOn(note, 100);
					if (multi == false) {
						Thread.sleep(250);
					}
				}
			} else {
				int ctrl = Notes.getControl(n);
				if (ctrl == 1) {
					mChannels[0].allNotesOff();
					Thread.sleep(250);
				} else if (ctrl == 2) {
					Thread.sleep(250);
				} else if (ctrl == 3) {
					multi = true;
				} else if (ctrl == 4) {
					multi = false;
					Thread.sleep(250);
				}
			}
		}
		Thread.sleep(2000);
	}
}
