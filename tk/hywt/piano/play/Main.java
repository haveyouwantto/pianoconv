package tk.hywt.piano.play;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Main {
	public static void play(String str) throws MidiUnavailableException, InterruptedException {
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
		MidiChannel[] mChannels = midiSynth.getChannels();
		mChannels[0].programChange(46);
		boolean multi = false;
		int i = 0;
		MainWindow.getSlider().setValue(0);
		MainWindow.getSlider().setMaximum(str.length() - 1);
		MainWindow.getLblPlayback().setText("0");
		MainWindow.getLblLength().setText(String.valueOf(str.length() - 1));
		MainWindow.getLblString().setText(str.substring(0, str.length()));
		while (i < str.length()) {
			String n = null;
			try {
				n = String.valueOf(str.charAt(i));
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				i = 0;
				e.printStackTrace();
			}
			String nl = n.toLowerCase();
			int note = Notes.getNote(nl);
			System.out.print(n);
			while (MainWindow.pause) {
				try {
					int v = MainWindow.getSlider().getValue();
					MainWindow.getLblString().setText(str.substring(v + 1, v + 21));
				} catch (Exception e) {

				}
				Thread.sleep(10);
			}
			if (MainWindow.getBtnNewButton().getText() == "播放") {
				mChannels[0].allNotesOff();
				return;
			}
			if (MainWindow.b) {
				multi = false;
				i = MainWindow.p;
				MainWindow.b = false;
			} else {
				MainWindow.getSlider().setValue(i);
				MainWindow.getLblPlayback().setText(String.valueOf(i));
				try {
					MainWindow.getLblString().setText(str.substring(i + 1, i + 21));
				} catch (Exception e) {

				}
				++i;
			}
			if (note != 0) {
				{
					mChannels[0].noteOn(note, 100);
					if (multi == false) {
						Thread.sleep((long) MainWindow.getSpinner().getValue());
					}
				}
			} else {
				int ctrl = Notes.getControl(n);
				if (ctrl == 1) {
					Thread.sleep((long) MainWindow.getSpinner().getValue());
				} else if (ctrl == 2) {
					Thread.sleep((long) MainWindow.getSpinner().getValue());
				} else if (ctrl == 3) {
					multi = true;
				} else if (ctrl == 4) {
					multi = false;
					Thread.sleep((long) MainWindow.getSpinner().getValue());
				}
			}
		}
		MainWindow.getBtnNewButton().setText("播放");
		mChannels[0].allNotesOff();
		Thread.sleep(2000);
	}
}
