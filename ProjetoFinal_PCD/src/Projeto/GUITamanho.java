package Projeto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class GUITamanho {

	public GUITamanho(Cliente c ,RemoteFile file) {
		JFrame frame = new JFrame("Tamanho do: "+file.getName());
		JTextArea tamanho = new JTextArea(String.valueOf(file.getSize()) + " bytes");
		tamanho.setEditable(false);
		JButton fechar = new JButton("Fechar");
		fechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				c.getGui().getFrame().setVisible(true);
			}
		});
		frame.add(fechar, BorderLayout.SOUTH);
		frame.add(tamanho);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

}
