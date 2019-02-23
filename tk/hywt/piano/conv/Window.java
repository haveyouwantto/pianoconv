package tk.hywt.piano.conv;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Piano Converter");
		frame.setBounds(100, 100, 640, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton button = new JButton("Select File");
		button.setBounds(244, 13, 132, 23);
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 46, 604, 265);
		textArea.setLineWrap(true);
		String[] midi= {"mid","midi"};
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter Filter = new FileNameExtensionFilter("MIDI Files", midi);
					//Attaching Filter to JFileChooser object
				fileChooser.setFileFilter(Filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) 
				{
					textArea.setText("Loading ...");
					File selectedFile = fileChooser.getSelectedFile();
					try {
						textArea.setText(Main.load(selectedFile));   
					} catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(button);
		frame.getContentPane().add(textArea);
	}
}
