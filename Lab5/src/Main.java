import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		myFrame.setSize(((30*9)+1)+68, ((30*9)+1)+85);
		myFrame.setLocationRelativeTo(null);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);



        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}