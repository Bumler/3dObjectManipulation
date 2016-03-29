package Rotations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class frame extends JFrame {

	protected graphicsPanelV gPane = new graphicsPanelV(this);
	private JPanel contentPane;
	private JTextField angleInput;
	
	int modeXYZ = 0; //modexyz can be set to 1-3: 1 = x, 2 = y, 3 = z 
	int modeRotate = 0; //modeRotate can be set to 1-2: 1 = origin, 2 = center
	int angle = -1;
	private JTextField arbX;
	private JTextField arbY;
	private JTextField arbZ;
	Cube c = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.EAST);
		btnPanel.setLayout(new GridLayout(0, 2, 5, 5));
		
		JButton rotateBtn = new JButton("Rotato!");
		btnPanel.add(rotateBtn);
		rotateBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				if (angleInput.getText().length() != 0)	
					{angle = Integer.parseInt(angleInput.getText());}
				gPane.rotate(modeXYZ, modeRotate, angle);
			}
		});
		
		angleInput = new JTextField();
		btnPanel.add(angleInput);
		angleInput.setColumns(1);
		
		ButtonGroup xyz = new ButtonGroup();
		ButtonGroup rPoint = new ButtonGroup();
		
		
		JRadioButton xBtn = new JRadioButton("X");
		btnPanel.add(xBtn);
		xyz.add(xBtn);
		xBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				modeXYZ = 1;
			}
		});
		
		JRadioButton centerBtn = new JRadioButton("Center");
		btnPanel.add(centerBtn);
		rPoint.add(centerBtn);
		centerBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				modeRotate = 2;
			}
		});
		
		JRadioButton yBtn = new JRadioButton("Y");
		btnPanel.add(yBtn);
		xyz.add(yBtn);
		yBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				modeXYZ = 2;
			}
		});
		
		JRadioButton origin = new JRadioButton("Origin");
		btnPanel.add(origin);
		rPoint.add(origin);
		origin.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				modeRotate = 1;
			}
		});
		
		JRadioButton zBtn = new JRadioButton("Z");
		btnPanel.add(zBtn);
		xyz.add(zBtn);
		zBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				modeXYZ = 3;
			}
		});
		
		JPanel blankA = new JPanel();
		btnPanel.add(blankA);
		JPanel blankB = new JPanel();
		btnPanel.add(blankB);
		JPanel blankC = new JPanel();
		btnPanel.add(blankC);
		
		JButton btnScale = new JButton("Scale");
		btnPanel.add(btnScale);
		
		JButton btnTranslate = new JButton("Translate");
		btnPanel.add(btnTranslate);
		
		JButton btnArbitrary = new JButton("Arbitrary");
		btnPanel.add(btnArbitrary);
		
		JPanel blankD = new JPanel();
		btnPanel.add(blankD);
		
		arbX = new JTextField();
		btnPanel.add(arbX);
		arbX.setColumns(0);
		
		arbY = new JTextField();
		btnPanel.add(arbY);
		arbY.setColumns(0);
		
		arbZ = new JTextField();
		btnPanel.add(arbZ);
		arbZ.setColumns(0);
		
		btnArbitrary.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				gPane.arbitraryRotation(new coordinate(Float.parseFloat(arbX.getText()), Float.parseFloat(arbY.getText()), Float.parseFloat(arbZ.getText())), Integer.parseInt(angleInput.getText()));
			}
		});
		///the action listeners for scale and translate aren't with the buttons themselves because they need the input of the arb axes
		btnScale.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				gPane.scale(Float.parseFloat(arbX.getText()), Float.parseFloat(arbY.getText()), Float.parseFloat(arbZ.getText()));
			}
		});
		btnTranslate.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				gPane.translate(Float.parseFloat(arbX.getText()), Float.parseFloat(arbY.getText()), Float.parseFloat(arbZ.getText()));
			}
		});
		
		JPanel blankE = new JPanel();
		btnPanel.add(blankE);
		
		JButton animateBTN = new JButton("Animate!");
		btnPanel.add(animateBTN);
		animateBTN.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				gPane.animate(modeXYZ, modeRotate, angle);
			}
		});
		
		JPanel blankG = new JPanel();
		btnPanel.add(blankG);
		
		JButton eraseBtn = new JButton("Erase");
		btnPanel.add(eraseBtn);
		eraseBtn.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				gPane.erase();
			}
		});
		
		
		JButton btnReadFile = new JButton("Read File");
		btnPanel.add(btnReadFile);
		btnReadFile.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent arg0){
				c = new Cube("cube.txt");
				gPane.fileRead(c);
			}
		});
		
		
		int emptySpace = 2;
		for (int i = 0; i < emptySpace; i++){
			JPanel blank = new JPanel();
			btnPanel.add(blank);
		}
		
		gPane.setBorder(new LineBorder(new Color(240, 240, 240), 10));
		gPane.setBackground(Color.PINK);
		gPane.setForeground(Color.BLACK);
		contentPane.add(gPane, BorderLayout.CENTER);
		
	}
}



