import javax.swing.JFrame;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
//import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class DefineCalculator 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		//Instantiate a frame for the calculator.
		//We will place panels inside this.
		CalculatorFrame frame = new CalculatorFrame();
		
		
		//Determine the size, visibility, and title of the frame as well as set behavior.
		//frame.setSize(510, 500);
		frame.pack();
		frame.setVisible(true);
		frame.setTitle("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		//Set the look and feel to Windows.
		try 
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} 
		catch (UnsupportedLookAndFeelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
