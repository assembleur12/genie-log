import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hello extends JFrame {

	// Constructeur
	public BDs() {
		super("Magasin BDs UMA");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                
		Container contentPane = this.getContentPane();
		// Choix du gestionnaire de disposition
		BorderLayout layout = new BorderLayout();
		contentPane.setLayout(layout);

		JPanel panel = new JPanel();

		JLabel label = new JLabel(
			"Bonjour, ceci est une JFrame qui contient"+
                        " un JPanel qui contient un JLabel");
		panel.add(label);
		// Ici ne sert pas car le panel est seul
		contentPane.add(panel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	
	public static void main(String[] args) {
		new BDs();
	}
}