import javax.swing.JFrame;

public class CreateCalculator 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//Create a new instance of a frame.
		CalculatorFrame framework1 = new CalculatorFrame();
		
		//Set our calculator's dimensions and determine it's behavior for some things.
		framework1.setSize(300, 400);
		framework1.setVisible(true);
		framework1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framework1.setTitle("Calculator");
	}

}
