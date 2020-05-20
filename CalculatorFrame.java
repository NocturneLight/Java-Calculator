import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

class CalculatorFrame extends JFrame implements ActionListener
{	
	//Instantiate private objects to go into the frame here.
	private InputField textField;
	private OutputField result;
	private JButton addition;
	private JButton subtraction;
	private JButton multiplication;
	private JButton division;
	private JButton equals;
	private JButton zero;
	private JButton one;
	private JButton two;
	private JButton three;
	private JButton four;
	private JButton five;
	private JButton six;
	private JButton seven;
	private JButton eight;
	private JButton nine;
	
	public CalculatorFrame()
	{
		//Instantiate buttons to be in the frame.
		addition = new JButton("+");
		subtraction = new JButton("-");
		multiplication = new JButton("*");
		division = new JButton("÷");
		equals = new JButton("=");
		zero = new JButton("0");
		one = new JButton("1");
		two = new JButton("2");
		three = new JButton("3");
		four = new JButton("4");
		five = new JButton("5");
		six = new JButton("6");
		seven = new JButton("7");
		eight = new JButton("8");
		nine = new JButton("9");
		
		//Give all the buttons an ActionListener
		addition.addActionListener(this);
		subtraction.addActionListener(this);
		multiplication.addActionListener(this);
		division.addActionListener(this);
		equals.addActionListener(this);
		zero.addActionListener(this);
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		six.addActionListener(this);
		seven.addActionListener(this);
		eight.addActionListener(this);
		nine.addActionListener(this);
		
		//Create a new panel called ButtonPanel and put it in the center.
		JPanel ButtonPanel = new JPanel();
		add(ButtonPanel, BorderLayout.CENTER);
		
		//Add all the buttons to ButtonPanel.
		ButtonPanel.add(addition);
		ButtonPanel.add(subtraction);
		ButtonPanel.add(multiplication);
		ButtonPanel.add(division);
		ButtonPanel.add(equals);
		ButtonPanel.add(zero);
		ButtonPanel.add(one);
		ButtonPanel.add(two);
		ButtonPanel.add(three);
		ButtonPanel.add(four);
		ButtonPanel.add(five);
		ButtonPanel.add(six);
		ButtonPanel.add(seven);
		ButtonPanel.add(eight);
		ButtonPanel.add(nine);
		
		
		//Create a new InputField object and place it in BorderLayout.NORTH of the frame.
		textField = new InputField();
		add(textField, BorderLayout.NORTH);	
		
		//Create the result text field object and place it in the BorderLayout.SOUTH of the frame.
		result = new OutputField();
		add(result, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if (e.getSource() == addition)
		{
			result.setText("+ is pressed");
		}
	}
	
	public class resultOutput
	{
		public void setText(String s)
		{
			result.setText(s);
		}
	}
}