package mainFrame;

import javax.swing.JMenuBar;

public class PMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private PFileMenu pFileMenu;

	public PMenuBar() {		
		this.pFileMenu = new PFileMenu();
		this.add(this.pFileMenu);

	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
