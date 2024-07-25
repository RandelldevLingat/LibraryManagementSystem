package GUI;
import java.awt.LayoutManager;
import javax.swing.JFrame;

public class Structure extends JFrame{

	public Structure(String title, LayoutManager layout) {
		super(title);
		setLayout(layout);
		initializeFrame();
	}
	
	private void initializeFrame() {
		setSize(700,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
	}
}
