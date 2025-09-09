package Projeto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class GUIExibir {

	public GUIExibir(Cliente c ,RemoteFile file) {
		JFrame frame = new JFrame("Exibindo: "+file.getName());
		String text = file.getText();
		JTextArea exibir = new JTextArea(text);
		exibir.setEditable(false);
		JButton fechar = new JButton("Fechar");
		fechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newText = (exibir.getText());
				try {
					c.sendRequestExibirFechar(file.getName(), newText);
					frame.dispose();
					c.getGui().getFrame().setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(fechar, BorderLayout.SOUTH);
		frame.add(exibir);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

}
