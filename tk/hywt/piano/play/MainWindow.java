package tk.hywt.piano.play;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MainWindow {

	private JFrame frame;
	private JTextArea textArea;
	private static JButton btnNewButton;
	private static JSlider slider;
	public static int p;
	public static boolean b=false;
	public static boolean pause=false;
	private static JLabel lblPlayback;
	private static JLabel lblLength;
	private static JLabel lblString;
	private JButton btnClear;
	private static JSpinner spinner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Piano Player");
		frame.setBounds(100, 100, 640, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		btnNewButton = new JButton("播放");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnNewButton.getText()=="播放") {
				Thread thread = new Thread(new Runnable() {
			        @Override
			        public void run() {
			        	try {
							Main.play(textArea.getText());
						} catch (MidiUnavailableException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
					thread.start();
					btnNewButton.setText("停止");
				}else {
					System.out.println();
					btnNewButton.setText("播放");
				}
			}
		});
		btnNewButton.setBounds(154, 288, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 600, 212);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		
		lblPlayback = new JLabel("0");
		lblPlayback.setBounds(14, 254, 72, 18);
		frame.getContentPane().add(lblPlayback);
		
		lblLength = new JLabel("0");
		lblLength.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLength.setBounds(536, 254, 72, 18);
		frame.getContentPane().add(lblLength);
		
		slider = new JSlider();
		slider.setMaximum(0);
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblPlayback.setText(String.valueOf(slider.getValue()));
			}
		});
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				b=true;
				p=slider.getValue();
				pause=false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				pause=true;
			}
		});
		slider.setValue(0);
		slider.setBounds(14, 228, 600, 26);
		frame.getContentPane().add(slider);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("暂停");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause=tglbtnNewToggleButton.isSelected();
			}
		});
		tglbtnNewToggleButton.setBounds(261, 288, 93, 23);
		frame.getContentPane().add(tglbtnNewToggleButton);
		
		lblString = new JLabel("");
		lblString.setFont(new Font("SimSun-ExtB", Font.PLAIN, 20));
		lblString.setBounds(200, 254, 200, 26);
		frame.getContentPane().add(lblString);
		
		btnClear = new JButton("\u6E05\u9664");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnClear.setBounds(368, 288, 93, 23);
		frame.getContentPane().add(btnClear);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Long(240), new Long(30), new Long(960), new Long(30)));
		spinner.setBounds(475, 288, 72, 23);
		frame.getContentPane().add(spinner);
		
	}
	public static JButton getBtnNewButton() {
		return btnNewButton;
	}
	public static JSlider getSlider() {
		return slider;
	}
	public static JLabel getLblPlayback() {
		return lblPlayback;
	}
	public static JLabel getLblLength() {
		return lblLength;
	}
	public static JLabel getLblString() {
		return lblString;
	}
	public static JSpinner getSpinner() {
		return spinner;
	}
}
