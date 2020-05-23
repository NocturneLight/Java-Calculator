import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;


class CalculatorFrame extends JFrame implements ActionListener, FocusListener
{
	// Our serial ID.
	private static final long serialVersionUID = 819247707764632833L;

	private static boolean shouldFill = true;
	private static boolean shouldWeightX = true;
	private static boolean RIGHT_TO_LEFT = false;
	private GridBagConstraints constraint = new GridBagConstraints();
	
	private JPanel displayAndBit = new JPanel();
	private JPanel bitPanel = new JPanel();
	private JPanel firstRow = new JPanel();
	private JPanel secondRow = new JPanel();
	private JPanel thirdRow = new JPanel();
	private JPanel fourthRow = new JPanel();
	private JLabel bitLabels[] = new JLabel[6];
	private InputOutputField inOut = new InputOutputField();
	private BitToggler bit[] = new BitToggler[64];
	
	private JPanel radioButtonPanel = new JPanel();
	
	private JPanel bitConversionPanel = new JPanel();
	private ButtonGroup bitConversionGroup = new ButtonGroup();
	private JRadioButton Hex = new JRadioButton("Hex");
	private JRadioButton Dec = new JRadioButton("Dec");
	private JRadioButton Oct = new JRadioButton("Oct");
	private JRadioButton Bin = new JRadioButton("Bin");
	
	private JPanel wordPanel = new JPanel();
	private JRadioButton qword = new JRadioButton("Qword");
	private JRadioButton dword = new JRadioButton("Dword");
	private JRadioButton word = new JRadioButton("Word");
	private JRadioButton byteButton = new JRadioButton("Byte");
	
	private JPanel mainAlgebraButtonPanel = new JPanel();
	private JPanel quoModPanel = new JPanel();
	private JPanel algebraPanel = new JPanel();
	private JPanel rows[] = new JPanel[6];
	private JButton mod = new JButton("Mod");
	private JButton algebraButtons[] = new JButton[34];
	private String[] algebraButtonIdentity = {"A", "", "", "", "", "", "B", "←", "CE", "c", "±", "√", "C", "7", "8", "9", "/", "%", "D", "4", "5", "6", "*", "1/x", "E", "1", "2", "3", "-", "=", "F", "0", ".", "+"};
	
	private Insets insets = new Insets(2, 2, 2, 2);
	
	boolean add = false;
	boolean subtract = false;
	boolean multiply = false;
	boolean divide = false;
	boolean modulus = false;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu help = new JMenu("Help");
	private JMenu view = new JMenu("View");
	private JMenu edit = new JMenu("Edit");
	private JMenuItem hideItem = new JMenuItem("Hide Screen");
	private JMenuItem helpItem = new JMenuItem("Tutorials");
	private JMenuItem helpItem2 = new JMenuItem("Text Input");
	private JMenuItem copyItem = new JMenuItem("Copy");
	
	public CalculatorFrame()
	{
		
		//Instantiate the toggle buttons and assign 0 as their name.
		for (int i = 0; i <= bit.length - 1; i++)
		{
			bit[i] = new BitToggler();
			bit[i].setText("0");
		}
		
		//Instantiate our row array.
		for (int i = 0; i <= rows.length - 1; i++)
		{
			rows[i] = new JPanel();
		}
		
		//Instantiate our algebra buttons.
		for (int i = 0; i <= algebraButtons.length - 1; i++)
		{
			algebraButtons[i] = new JButton();
		}
		
		//Instantiate and label our bitLabels.
		for (int i = 0; i <= bitLabels.length - 1; i++)
		{
			bitLabels[i] = new JLabel();
			
			if (i == 0)
			{
				bitLabels[i].setText("63");
			}
			else if (i == 1)
			{
				bitLabels[i].setText("47");
			}
			else if (i == 2)
			{
				bitLabels[i].setText("32");
			}
			else if (i == 3)
			{
				bitLabels[i].setText("31");
			}
			else if (i == 4)
			{
				bitLabels[i].setText("15");
			}
			else if (i == 5)
			{
				bitLabels[i].setText("0");
			}
		}
		
		
		//Determine if we want things going from the left to the right.
		if (RIGHT_TO_LEFT)
		{
			setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		
		//Set the layout for our panels. 
		displayAndBit.setLayout(new GridBagLayout());
		bitPanel.setLayout(new BoxLayout(bitPanel, BoxLayout.PAGE_AXIS));
		firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.LINE_AXIS));
		secondRow.setLayout(new FlowLayout());
		thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.LINE_AXIS));
		fourthRow.setLayout(new FlowLayout());
		
		//Determine if we want the natural size of something or not.
		if (shouldFill)
		{
			//The natural height, and the maximum width.
			constraint.fill = GridBagConstraints.HORIZONTAL;
		}
		
		//Create the text field, set the constraints and add it to the panel.
		if (shouldWeightX)
		{
			constraint.weightx = 0.5;
		}
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.gridx = 0;
		constraint.gridy = 0;
		displayAndBit.add(inOut, constraint);
		
		//Create the bit buttons and add them to the bitPanel grid.
		//Don't worry about having the bits be pressable. Just have them change to match the number in bits.
		for (int i = 0; i <= bit.length - 1; i++)
		{
			if (i <= (bit.length - 1) / 2)
			{
				if (i == 0)
				{
					firstRow.add(Box.createRigidArea(new Dimension(28, 0)));
				}
				
				firstRow.add(bit[i]);
			
				//Create a space after every 4 bits.
				if (i % 4 == 3)
				{
					firstRow.add(Box.createRigidArea(new Dimension(28, 0)));
				}
			}
			else if (i > (bit.length - 1) / 2)
			{
				if (i == ((bit.length - 1) / 2) + 1)
				{
					thirdRow.add(Box.createRigidArea(new Dimension(28, 0)));
				}
				
				thirdRow.add(bit[i]);
				
				//Create a space after every 4 bits.
				if (i % 4 == 3)
				{
					thirdRow.add(Box.createRigidArea(new Dimension(28, 0)));
				}
			}
		}
		
		//Add the bit count to the second and fourth rows.
		bitLabels[1].setBorder(new EmptyBorder(0, 205, 0, 165));
		bitLabels[4].setBorder(new EmptyBorder(0, 205, 0, 170));
		
		for (int i = 0; i <= bitLabels.length - 1; i++)
		{
			if (i <= (bitLabels.length - 1) / 2)
			{
				secondRow.add(bitLabels[i]);
			}
			else if (i > (bitLabels.length - 1) / 2)
			{
				fourthRow.add(bitLabels[i]);
			}
		}
		
		
		
		//Set the constraints, add the bitPanel to displayAndBit, and add displayAndBit to the North Quadrant.
		if (shouldWeightX)
		{
			constraint.weightx = 0.5;
		}
		constraint.fill = GridBagConstraints.NONE;
		constraint.gridwidth = 1;
		constraint.gridx = 0;
		constraint.gridy = 1;
		bitPanel.add(firstRow);
		bitPanel.add(secondRow);
		bitPanel.add(thirdRow);
		bitPanel.add(fourthRow);
		displayAndBit.add(bitPanel, constraint);
		add(displayAndBit, BorderLayout.NORTH);
		
		
		//Create a space between the displayAndBit panel and the radioButtonPanel.
		radioButtonPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		
		
		//Set the layout for the radioButtonPanel, bitConversionPanel, and the wordPanel.
		radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.PAGE_AXIS));
		bitConversionPanel.setLayout(new BoxLayout(bitConversionPanel, BoxLayout.PAGE_AXIS));
		wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.PAGE_AXIS));
		
		//Add the JRadioButtons to a group so that only one can be selected at a time.
		bitConversionGroup.add(Hex);
		bitConversionGroup.add(Bin);
		bitConversionGroup.add(Oct);
		bitConversionGroup.add(Dec);
		
		//Make the Dec button auto clicked right from the get go.
		Dec.setSelected(true);
		
		//Add the bit conversion JRadioButtons to the bitConversion panel.
		bitConversionPanel.add(Bin);
		bitConversionPanel.add(Dec);
		bitConversionPanel.add(Hex);
		bitConversionPanel.add(Oct);
		
		
		//Place the bitConversionPanel in the radioButtonPanel.
		radioButtonPanel.add(bitConversionPanel);
		
		
		//Create a space between the two sets of buttons.
		radioButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		
		//Add the JRadioButtons to the wordPanel.
		wordPanel.add(qword);
		wordPanel.add(dword);
		wordPanel.add(word);
		wordPanel.add(byteButton);
		
		
		//Disable the word buttons and the byte buttons as they are not required to be 
		//functioning for this project.
		qword.setEnabled(false);
		dword.setEnabled(false);
		word.setEnabled(false);
		byteButton.setEnabled(false);
		
		//Place the wordPanel into radioButtonPanel.
		radioButtonPanel.add(wordPanel);
		
		//Add the radioButtonPanel into the West Quadrant.
		add(radioButtonPanel, BorderLayout.WEST);
		
		//Add a rigid area vertically to prevent a graphical glitch from occurring
		//when using setMaximumSize to get the bitConversionPanel to match the panel
		//under it.
		bitConversionPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		bitConversionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, bitConversionPanel.getMinimumSize().height));
		
		
		//Set the layout for the quoModPanel and the algebraPanel.
		quoModPanel.setLayout(new BoxLayout(quoModPanel, BoxLayout.LINE_AXIS));
		algebraPanel.setLayout(new GridBagLayout());
		
		//Give the mod button an actionListener.
		mod.addActionListener(this);
		
		//Add the modulus buttons to the quoModPanel.
		quoModPanel.add(mod);
		
		
		//For loop to add all the algebra buttons to a grid.
		for (int i = 0; i <= algebraButtons.length - 1; i++)
		{
			constraint.insets = insets;
			
			//Make it so that each button is the same size as each other.
			constraint.fill = GridBagConstraints.BOTH;
			
			//If it's the equal button, have the button fill the leftover vertical space.
			if (i == 29)
			{
				constraint.gridheight = GridBagConstraints.REMAINDER;
			}
			
			//Add the algebraButtons to algebraPanel, then increment gridx.
			algebraPanel.add(algebraButtons[i], constraint);
			constraint.gridx++;
			
			//If i reached the 6th button, then after the button was added, move on to the next row.
			if (i % 6 == 5)
			{
				constraint.gridx = 0;
				constraint.gridy++;
			}
		}
		
		
		
		//Assign the symbols in the algebraIdentifiers array to the algebra buttons.
		//Also add ActionListeners to all the buttons.
		for (int i = 0; i <= algebraButtons.length - 1; i++)
		{
			algebraButtons[i].setText(algebraButtonIdentity[i]);
			algebraButtons[i].addActionListener(this);
			
			//Disable the buttons not needed for this project.
			if ((i >= 1 && i <= 5) || i == 11 || i == 17 || i == 23 || i == 32)
			{
				algebraButtons[i].setEnabled(false);
			}
		}
		
		//Disable the letter buttons
		for (int i = 0; i <= algebraButtons.length; i++)
		{
			if (i == 0 || i == 6 || i == 12 || i == 18 || i == 24 || i == 30)
			{
				algebraButtons[i].setEnabled(false);
			}
		}
		
		//Add ActionListeners to items not in an array here.
		inOut.addActionListener(this);
		Hex.addActionListener(this);
		Dec.addActionListener(this);
		Oct.addActionListener(this);
		Bin.addActionListener(this);
		
		//Add FocusListeners to items not in an array here.
		Hex.addFocusListener(this);
		Dec.addFocusListener(this);
		Oct.addFocusListener(this);
		Bin.addFocusListener(this);
		
		
		//Add the quoModPanel to the mainAlgebraPanel in the West Quadrant, 
		//then add that to the Center Quadrant.
		//Then add all the rows to the mainAlgebraPanel.
		add(mainAlgebraButtonPanel, BorderLayout.CENTER);
		mainAlgebraButtonPanel.add(quoModPanel, BorderLayout.WEST);
		mainAlgebraButtonPanel.add(algebraPanel,BorderLayout.EAST);
		
		
		
		//Create a black border around each set of buttons.
		bitPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		bitConversionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		wordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Set the font color for the text in the bitLabels labels.
		for (int i = 0; i <= bitLabels.length - 1; i++)
		{
			bitLabels[i].setForeground(Color.GRAY);
		}
		
		//Create the menu bar and it's contents here.
		view.add(hideItem);
		menuBar.add(view);
		
		help.add(helpItem);
		help.add(helpItem2);
		menuBar.add(help);
		
		edit.add(copyItem);
		menuBar.add(edit);
		
		setJMenuBar(menuBar);
		
		
		//Add actionListeners to appropriate menu items.
		helpItem.addActionListener(this);
		helpItem2.addActionListener(this);
		hideItem.addActionListener(this);
		copyItem.addActionListener(this);
		
		//Define the dimensions of the menu bar and such here.
		setSize(350, 200);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if (e.getSource() == algebraButtons[0])
		{
			//Add an A symbol to the text field.
			inOut.setText(inOut.getText() + "A");
		}
		else if (e.getSource() == algebraButtons[6])
		{
			//Add a B symbol to the text field.
			inOut.setText(inOut.getText() + "B");
		}
		else if (e.getSource() == algebraButtons[7])
		{
			//Remove the last character in the text field.
			if (inOut.getText().length() != 0)
			{
				inOut.setText(inOut.getText().substring(0, (inOut.getText().length() - 1)));
			}
		}
		else if (e.getSource() == algebraButtons[8])
		{
			//Remove the end portion of the string until you get to the last value.
			if (inOut.getText() != null && inOut.getText().length() > 0)
			{
				if (inOut.getText().lastIndexOf(" ") != -1)
				{
					inOut.setText(inOut.getText().substring(0, inOut.getText().lastIndexOf(" ")));
				}
			}
		}
		else if (e.getSource() == algebraButtons[9])
		{
			//Clear the textfield.
			inOut.setText("");
		}
		else if (e.getSource() == algebraButtons[10])
		{
			//Split the user's input into multiple pieces and put the pieces in an array.
			String test[] = inOut.getText().split(" ");
			
			//Take the last piece and put a negative behind it.
			test[test.length - 1] = "-" + test[test.length - 1];
			
			//Create a StringBuilder so we can reconstruct the string.
			StringBuilder reconstruct = new StringBuilder();
			
			//Reconstruct the string using a for each loop.
			for (String revise : test)
			{
				reconstruct.append(revise + " ");
			}
			
			//Store the new string in a new variable.
			String rebuiltString = reconstruct.toString();
			
			//Put the rebuild string into the inOut JTextField.
			inOut.setText(rebuiltString);
			
		}
		else if (e.getSource() == algebraButtons[12])
		{
			//Add a C symbol to the text field.
			inOut.setText(inOut.getText() + "C");
		}
		else if (e.getSource() == algebraButtons[13])
		{
			//Insert 7 into the text field.
			inOut.setText(inOut.getText() + "7");
		}
		else if (e.getSource() == algebraButtons[14])
		{
			//Insert 8 into the text field.
			inOut.setText(inOut.getText() + "8");
		}
		else if (e.getSource() == algebraButtons[15])
		{
			//Insert 9 into the text field.
			inOut.setText(inOut.getText() + "9");
		}
		else if (e.getSource() == algebraButtons[16])
		{	
			//Add a division symbol to the text field.
			//If the text field is empty, insert a 0 before the division symbol.
			if (inOut.getText().length() == 0)
			{
				inOut.setText("0 / ");
			}
			
			inOut.setText(inOut.getText() + " / ");
			
		}
		else if (e.getSource() == algebraButtons[18])
		{
			//Add a D symbol to the text field.
			inOut.setText(inOut.getText() + "D");
		}
		else if (e.getSource() == algebraButtons[19])
		{
			//Insert a 4 into the text field.
			inOut.setText(inOut.getText() + "4");
		}
		else if (e.getSource() == algebraButtons[20])
		{
			//Insert a 5 into the textfield.
			inOut.setText(inOut.getText() + "5");
		}
		else if (e.getSource() == algebraButtons[21])
		{
			//Insert a 6 into the text field.
			inOut.setText(inOut.getText() + " 6");
		}
		else if (e.getSource() == algebraButtons[22])
		{
			if (inOut.getText().length() == 0)
			{
				//Insert a multiplication symbol into the text field.
				//If the text field is empty, put a 0 before multiplication symbol. 
				inOut.setText("0 *");
			}
			
			inOut.setText(inOut.getText() + "*");
			
		}
		else if (e.getSource() == algebraButtons[24])
		{
			//Add an E symbol to the text field.
			inOut.setText(inOut.getText() + "E");
		}
		else if (e.getSource() == algebraButtons[25])
		{
			//Insert a 1 into the text field.
			inOut.setText(inOut.getText() + "1");
		}
		else if (e.getSource() == algebraButtons[26])
		{
			//Insert a 2 into the text field.
			inOut.setText(inOut.getText() + "2");
		}
		else if (e.getSource() == algebraButtons[27])
		{
			//Insert a 3 into the text field.
			inOut.setText(inOut.getText() + "3");
		}
		else if (e.getSource() == algebraButtons[28])
		{	
			//Insert a minus symbol into the text field.
			//If the text field is empty, insert a 0 before the minus symbol.
			if (inOut.getText().length() == 0)
			{
				inOut.setText("0 - ");
			}
			
			inOut.setText(inOut.getText() + " - ");
			
		}
		else if (e.getSource() == algebraButtons[29])
		{
			//Initialize variables exclusive to the equal button here.
			String input[] = inOut.getText().split(" ");
			
			//Replace any instance in the JTextField of a - sign being used			
			//to signify a number as a negative with a U.
			for (int i = 0; i <= input.length - 1; i++)
			{
				if (input[i].charAt(0) == '-')
				{
					if (input[i].length() > 1 && input[i].matches("[-0-9]*"))
					{
						input[i] = input[i].replace("-", "U ");
					}
				}
			}
			
			//Check if we're in Hex mode.
			if (Hex.isSelected())
			{
				System.out.println("We're good.");
				//Replace the letters with numbers.
				for (int i = 0; i <= input.length - 1; i++)
				{
					if (input[i].contains("A"))
					{
						input[i] = input[i].replace("A", "10");
					}
					else if (input[i].contains("B"))
					{
						input[i] = input[i].replace("B", "11");
					}
					else if (input[i].contains("C"))
					{
						input[i] = input[i].replace("C", "12");
					}
					else if (input[i].contains("D"))
					{
						input[i] = input[i].replace("D", "13");
					}
					else if (input[i].contains("E"))
					{
						input[i] = input[i].replace("E", "14");
					}
					else if (input[i].contains("F"))
					{
						input[i] = input[i].replace("F", "16");
					}
				}
			}
			
			//Create a StringBuilder.
			StringBuilder reconstruct = new StringBuilder();
			
			//Take the array created from replacing - with U and reconstruct it back into a String.
			for (String revisedInput : input)
			{
				reconstruct.append(revisedInput + " ");
			}
			
			//Our reconstructed string.
			String newText = reconstruct.toString();
			
			//Convert the string to postfix, then solve it.
			//Assign the result of the equation to a variable so that we can cast it.
			double result = Double.valueOf(String.valueOf(solveEquation(convertToPostfix(newText))));
			
			//Cast result to integer, convert to a base,
			//then display it in the bit panel.
			if (Bin.isSelected())
			{
				inOut.setText(Double.toString(result));
				convertToBase((int) result, 2);
			}
			else if (Dec.isSelected())
			{
				inOut.setText(Double.toString(result));
				convertToBase((int) result, 2);
			}
			else if (Oct.isSelected())
			{
				convertToBase((int) result, 8);
				convertToBase((int) result, 2);
			}
			else if (Hex.isSelected())
			{
				convertToBase((int) result, 16);
				convertToBase((int) result, 2);
			}
			
		}
		else if (e.getSource() == algebraButtons[30])
		{
			//Insert an F symbol into the text field.
			inOut.setText(inOut.getText() + "F");
		}
		else if (e.getSource() == algebraButtons[31])
		{
			//Insert a 0 into the text field.
			inOut.setText(inOut.getText() + "0");
		}
		else if (e.getSource() == algebraButtons[32])
		{
			
		}
		else if (e.getSource() == algebraButtons[33])
		{
			
			//Insert an addition symbol into the text field.
			//If the text field is empty, insert a 0 before the addition symbol.
			if (inOut.getText().length() == 0)
			{
				inOut.setText("0 + ");
			}
			
			inOut.setText(inOut.getText() + " + ");
			
		}
		else if (e.getSource() == mod)
		{
			//Insert a minus symbol into the text field.
			//If the text field is empty, insert a 0 before the minus symbol.
			if (inOut.getText().length() == 0)
			{
				inOut.setText("0 % ");
			}
			else if (inOut.getText().charAt(inOut.getText().length() - 1) != ' ')
			{
				inOut.setText(inOut.getText() + " % ");
			}
		}
		else if (e.getSource() == helpItem)
		{
			//When the user clicks on the help item, show a Message Dialog informing the user
			//of a website they can look to for help.
			JOptionPane.showMessageDialog(null, "For help, visit http://www.java2s.com/");
		}
		else if (e.getSource() == helpItem2)
		{
			JOptionPane.showMessageDialog(null, "Trouble inputting text?\n Make sure there's no space between the left end of the textfield and the first character.\nBe sure every number and operator after that has only one space between each other.");
		}
		else if (e.getSource() == hideItem)
		{
			//It's impossible to reopen a window when you hide it without incorporating a useless
			//window whose only purpose is reopening the previous window.
			//To "hide" the window, I'm just closing it.
			this.dispose();
		}
		else if (e.getSource() == copyItem)
		{
			//Copy the contents of the inOut JTextField  to the clipboard.
			StringSelection copiedText = new StringSelection(inOut.getText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copiedText, copiedText);
		}
	}
	
	public void convertToBase(int value, int base)
	{	
		//Define variables and arrays here.
		ArrayList<Integer> divisionList = new ArrayList<Integer>();
		ArrayList<Integer> modList = new ArrayList<Integer>();
		double userInput = value;
		int bitIndex = bit.length - 1;
		
		if (base == 2)
		{
			if (value > 0)
			{
				//Reset the bit panel to all 0's before displaying the new bit combination.
				for (int i = 0; i <= bit.length - 1; i++)
				{
					bit[i].setText("0");
				}
			
				//Convert input value to BigInteger and then convert it to binary.
				BigInteger bigInt = BigInteger.valueOf(value);
				String bigIntBinaryForm = bigInt.toString(2);
			
				for (int i = bigIntBinaryForm.length() - 1; i >= 0; i--)
				{
					if (bitIndex >= 0)
					{
						bit[bitIndex].setText(String.valueOf(String.valueOf(bigIntBinaryForm).charAt(i)));
						bitIndex--;
					}
				}
			}
			else if (value < 0)	
			{
				//Turn the number positive again.
				int positiveForm = -value;
				
				//Reset the bit panel to all 0's before displaying the new bit combination.
				for (int i = 0; i <= bit.length - 1; i++)
				{
					bit[i].setText("0");
				}
				
				//Convert input value to BigInteger and then convert it to binary.
				BigInteger bigInt = BigInteger.valueOf(positiveForm);
				String bigIntBinaryForm = bigInt.toString(2);

				for (int i = bigIntBinaryForm.length() - 1; i >= 0; i--)
				{
					if (bigIntBinaryForm.charAt(i) == '0')
					{
						bit[i].setText("1");
					}
					else if (bigIntBinaryForm.charAt(i) == '1')
					{
						bit[i].setText("0");
					}
					bitIndex--;
				}
				
				
				while (bitIndex > 0)
				{
					bit[bitIndex].setText("1");
					bitIndex--;
				}
				
				if (bigIntBinaryForm.charAt(bigIntBinaryForm.length() - 1) == '1')
				{
					bigIntBinaryForm = bigIntBinaryForm.substring(0, bigIntBinaryForm.length() - 1) + 0;
				}
				else if (bigIntBinaryForm.charAt(bigIntBinaryForm.length() - 1) == '0')
				{
					bigIntBinaryForm = bigIntBinaryForm.substring(0, bigIntBinaryForm.length() - 1) + 1;
				}	
			}
		}
		else if (base == 8 || base == 16)
		{
			//Use % to find the remainder and store it in an ArrayList
			for (int i = (int) userInput; i > 0;)
			{
				modList.add(i % base);
				divisionList.add(i /= base);
			}
			
			//Define the size of the array now that we have definite length.
			int modArray[] = new int[modList.size()];
			String modArrayString[] = new String[modArray.length];
			
			//Convert the ArrayList to array.
			for(int i = 0; i <= modList.size() - 1; i++) 
			{
			    if (modList.get(i) != null) 
			    {
			        modArray[i] = modList.get(i);
			    }
			}
			
			//Convert to letter if base 15
			if (base == 16)
			{
				//Convert numbers higher than 9 to letters.
				for (int i = 0; i <= modArray.length - 1; i++)
				{
					if (modArray[i] == 10)
					{
						modArrayString[i] = "A";
					}
					else if (modArray[i] == 11)
					{
						modArrayString[i] = "B";
					}
					else if (modArray[i] == 12)
					{
						modArrayString[i] = "C";
					}
					else if (modArray[i] == 13)
					{
						modArrayString[i] = "D";
					}
					else if (modArray[i] == 14)
					{
						modArrayString[i] = "E";
					}
					else if (modArray[i] == 15)
					{
						modArrayString[i] = "F";
					}
					else
					{
						modArrayString[i] = String.valueOf(modArray[i]);
					}
				}
			}
			else
			{
				for (int i = 0; i <= modArray.length - 1; i++)
				{
					modArrayString[i] = String.valueOf(modArray[i]);
				}
			}
			
			//inOut.setText(new StringBuilder(modList.toString()).reverse().toString());
			StringBuilder input = new StringBuilder();
			
			for (String construct : modArrayString)
			{
				input.append(construct);
				input.reverse();
			}
			
			inOut.setText(input.toString());
		}
		else
		{
			inOut.setText("Something's wrong with your input. Press the 'C' button and try again.");
		}
	}  
	
	@Override
	public void focusGained(FocusEvent e) 
	{
		// TODO Auto-generated method stub
		//Depending on which bit button gained focus, disable buttons not compatible with
		//the bit mode.
		if (e.getSource() == Bin)
		{
			for (int i = 0; i <= algebraButtons.length - 1; i++)
			{
				if (!algebraButtons[i].getText().equals("CE") && !algebraButtons[i].getText().matches("[01+-/*±c←]"))
				{
					algebraButtons[i].setEnabled(false);
				}
				
				if (algebraButtons[i].getText().matches("[=]"))
				{
					algebraButtons[i].setEnabled(true);
				}
			}
		}
		else if (e.getSource() == Dec)
		{
			for (int i = 0; i <= algebraButtons.length - 1; i++)
			{
				if (!algebraButtons[i].getText().matches("[01A-F%√.]") && !algebraButtons[i].getText().equals("") && !algebraButtons[i].getText().equals("1/x"))
				{
					algebraButtons[i].setEnabled(true);
				}
				
				if (algebraButtons[i].getText().matches("[A-F]"))
				{
					algebraButtons[i].setEnabled(false);
				}
			}
		}
		else if (e.getSource() == Hex)
		{
			for (int i = 0; i <= algebraButtons.length - 1; i++)
			{
				if (algebraButtons[i].getText().matches("[*-Fc←±]") || algebraButtons[i].getText().equals("CE"))
				{
					algebraButtons[i].setEnabled(true);
				}
				
				if (algebraButtons[i].getText().equals("."))
				{
					algebraButtons[i].setEnabled(false);
				}
			}
		}
		else if (e.getSource() == Oct)
		{
			for (int i = 0; i <= algebraButtons.length - 1; i++)
			{
				if (!algebraButtons[i].getText().matches("[01A-F%√.]") && !algebraButtons[i].getText().equals("") && !algebraButtons[i].getText().equals("1/x"))
				{
					algebraButtons[i].setEnabled(true);
				}
				
				if (algebraButtons[i].getText().matches("[A-F89]"))
				{
					algebraButtons[i].setEnabled(false);
				}
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
		// TODO Auto-generated method stub
	}
	
	public static boolean nextOperator(String nextUp) 
	{
		//Check which algebraic operator is next in the stack.
		return (nextUp.equals("+") || nextUp.equals("-") || nextUp.equals("*") || nextUp.equals("/") || nextUp.equals("%") || nextUp.equals("U"));
	}

	public double solveEquation(String convertedInput) 
	{
	    //Remove any and all whitespace from the user's input.
	    convertedInput = convertedInput.trim();
	    
	    //Initialize variables exclusive to this method here.
	    String nextUp;
	    Stack<Double> stack = new Stack<Double>();
	    Scanner userInput = new Scanner(convertedInput);
	    
	    //Loop as long as userInput still numbers and algebraic symbols.
	    while (userInput.hasNext()) 
	    {
	    	//Assign the next thing in the user input to the nextUp variable
	    	nextUp = userInput.next();
	    	
	    	//Check for an algebraic symbol.
	    	if (nextOperator(nextUp)) 
	    	{
	    		//If there are enough numbers to do algebra with, then apply the proper calculation.
	    		if (stack.size() >= 1) 
	    		{
	    			if (nextUp.equals("+")) 
	    			{
	    				stack.push((Double) stack.pop() + (Double) stack.pop());
	    			} 
	    			else if (nextUp.equals("-")) 
	    			{
	    				stack.push(-(Double) stack.pop() + (Double) stack.pop());
	    			} 
	    			else if (nextUp.equals("*")) 
	    			{
	    				stack.push((Double) stack.pop() * (Double) stack.pop());
	    			} 
	    			else if (nextUp.equals("/")) 
	    			{
	    				double first = stack.pop();
	    				double second = stack.pop();

	    				if (first == 0) 
	    				{
	    					inOut.setText("You can't divide by 0. Press the 'C' button and try again.");
	    				} 
	    				else 
	    				{
	    					stack.push(second / first);
	    				}
	    			}
	    			else if (nextUp.equals("%"))
	    			{
	    				double numberOne = stack.pop();
	    				double numberTwo = stack.pop();
	    				double remainder = numberTwo % numberOne;
	    				
	    				//Give the user a positive remainder when they mod using a negative number.
	    				//Otherwise if the numbers are positive use just % to get the answer.
	    				if (remainder < 0)
	    				{
	    					remainder += numberOne;
	    					stack.push(remainder);
	    				}
	    				else
	    				{
	    					stack.push(remainder);
	    				}
	    				
	    			}
	    			else if (nextUp.equals("U"))
	    			{
	    				//Change the number to a negative so that negative numbers used in calculations
	    				//give the correct answer.
	    				stack.push(-stack.pop());
	    			}
	    		} 
	    		else 
	    		{
	    			System.out.println("Please make sure you have a space between all you numbers and operators and no space at the start.");
	    		}
	    	} 
	    	else 
	    	{
	    		try 
	    		{
	    			stack.push(Double.parseDouble(nextUp));
	    		} 
	    		catch (NumberFormatException c) 
	    		{
	    			System.out.println("Please make sure you have a space between all you numbers and operators and no space at the start.");
	    		}
	    	}
	    }

	    //Try to catch the user's mistake of leaving out an algebraic symbol.
	    if (stack.size() > 1) 
	    {
	    	System.out.println("There are more numbers than algebraic symbols. Please press the 'C' button and try again.");
	    }
	    
	    //Close our scanner to prevent leakage.
	    userInput.close();
	    
	    return (Double) stack.pop();
	}
	
	static String convertToPostfix(String infixInput) 
	{
		//Initialize variables exclusive this method here.
		String validOperations = "-+/*%U";
		StringBuilder builder = new StringBuilder();
		Stack<Integer> stack = new Stack<>();
	 
		//For each spanning each element created from splitting up the original string.
		for (String token : infixInput.split("\\s")) 
		{
			//Before doing anything else, check if we've run out numbers and algebraic symbols.
			//If so, end the loop, if not, continue the loop.
			if (token.isEmpty())
			{
				continue;
			}
			
			//Instantiate variables needed inside the for each loop here.
			char character = token.charAt(0);
			int index = validOperations.indexOf(character);
	 
			//Check our stack for algebraic symbols and reconstruct a new string in postfix form accordingly.
			if (index != -1) 
			{
				if (stack.isEmpty())
				{
					stack.push(index);
				}
				else 
				{
					while (!stack.isEmpty()) 
					{
						int precheckTwo = stack.peek() / 2;
						int precheckOne = index / 2;
						
						if (precheckTwo > precheckOne || (precheckTwo == precheckOne && character != '^'))
						{
							builder.append(validOperations.charAt(stack.pop())).append(' ');
						}
						else 
						{
							break;
						}
					}
					
					stack.push(index);
				}
			} 
			else if (character == '(') 
			{
				//-2 stands for "(", if the character is a parenthesis, push it.
				stack.push(-2);
			} 
			else if (character == ')') 
			{
				//Until we reach the left parenthesis, reconstruct the string by adding a space and algebraic symbol to it.
				while (stack.peek() != -2)
				{
					builder.append(validOperations.charAt(stack.pop())).append(' ');
				}
				
				stack.pop();
			}
			else 
			{
				//If none of the above, just add a space.
				builder.append(token).append(' ');
			}
		}
		
		while (!stack.isEmpty())
		{
			//Do the same as in before. Append an algebraic symbol and space.
			builder.append(validOperations.charAt(stack.pop())).append(' ');
		}

		return builder.toString();
	}
}
