package tk.hywt.piano.conv;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;

public class Window {

	public static File opened;
	public static int mode = 2;
	public static char divider = '-';
	public static boolean isUpperCase = false;
	public static boolean isforcedBPM = true;
	public static int forcedBPM = 120;

	private JFrame frame;
	private static JTextArea output;
	private static JProgressBar progressBar;

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
		String[] midi = { "mid", "midi" };
		

		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 604, 215);
		frame.getContentPane().add(scrollPane);
		output = new JTextArea();
		scrollPane.setViewportView(output);
		output.setLineWrap(true);

		JPanel settings = new JPanel();
		settings.setBounds(10, 9, 604, 55);
		frame.getContentPane().add(settings);
		settings.setLayout(null);

		// Divider
		JComboBox<String> div = new JComboBox<String>();
		div.setEditable(true);
		div.setBounds(207, 29, 81, 21);
		div.addItem("-");
		div.addItem(" ");

		JLabel resLabel = new JLabel("\u5206\u8FA8\u7387:");
		resLabel.setBounds(0, 32, 42, 15);
		settings.add(resLabel);

		// Resolution
		JComboBox<String> resolution = new JComboBox<String>();
		resolution.setBounds(47, 29, 102, 21);
		resolution.addItem("全音符");
		resolution.addItem("二分音符");
		resolution.addItem("四分音符");
		resolution.addItem("八分音符");
		resolution.addItem("十六分音符");
		resolution.setSelectedIndex(2);
		settings.add(resolution);

		JLabel divLabel = new JLabel("\u5206\u9694\u7B26: ");
		divLabel.setBounds(154, 32, 48, 15);
		settings.add(divLabel);
		settings.add(div);

		// Upper Case Output
		JCheckBox isUp = new JCheckBox("\u5927\u5199\u8F93\u51FA");
		isUp.setBounds(293, 28, 100, 23);
		settings.add(isUp);

		JSpinner forceBPM = new JSpinner();
		forceBPM.setBounds(483, 29, 41, 22);
		forceBPM.setValue(120);
		settings.add(forceBPM);

		// Force BPM
		JCheckBox chckbxbpm = new JCheckBox("\u5F3A\u5236BPM");
		chckbxbpm.setSelected(true);
		chckbxbpm.setBounds(395, 28, 86, 23);
		settings.add(chckbxbpm);

		chckbxbpm.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent onChange) {
				if (onChange.getStateChange() == ItemEvent.SELECTED) {
					forceBPM.setEnabled(true);
					isforcedBPM = true;
				} else {
					forceBPM.setEnabled(false);
					isforcedBPM = false;
				}
			}
		});

		JButton loadBtn = new JButton("\u9009\u62E9\u6587\u4EF6");
		loadBtn.setBounds(165, 0, 102, 23);

		Runnable r = new Runnable(){
			public void run() {
				try {
	                output.setText("");
					mode = resolution.getSelectedIndex();
					divider = div.getSelectedItem().toString().charAt(0);
					isUpperCase = isUp.isSelected();
					forcedBPM = (int) forceBPM.getValue();

					Main.load(opened);
				} catch (InvalidMidiDataException | IOException | MidiUnavailableException e) {
					// TODO Auto-generated catch block
					output.setText("错误: " + e.toString());
					e.printStackTrace();
				}

			}
			
		};
		
		// Load file
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				FileNameExtensionFilter Filter = new FileNameExtensionFilter("MIDI 文件", midi);
				// Attaching Filter to JFileChooser object
				fileChooser.setFileFilter(Filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					opened = selectedFile;
                    new Thread(r).start();
				}
			}
		});
		settings.add(loadBtn);

		JButton copyBtn = new JButton("\u590D\u5236");
		copyBtn.setBounds(354, 0, 73, 23);
		settings.add(copyBtn);
		copyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StringSelection stringSelection = new StringSelection(output.getText());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});

		JButton reloadBtn = new JButton("\u5237\u65B0");
		reloadBtn.setBounds(277, 0, 67, 23);
		settings.add(reloadBtn);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 297, 604, 14);
		progressBar.setMaximum(1000);
		frame.getContentPane().add(progressBar);
		
		// Reload file
		reloadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new Thread(r).start();
			}
		});

	}
	public static JTextArea getOutput() {
		return output;
	}
	public static JProgressBar getProgressBar() {
		return progressBar;
	}
}
