package Projeto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUINovoFicheiro {
	
	public GUINovoFicheiro(Cliente c) {
		JFrame frame = new JFrame("Criando novo ficheiro");
		JTextField editor = new JTextField("Nome do novo ficheiro");
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = (editor.getText());
				try {
					c.sendRequestNewFile(name);
					frame.dispose();
					c.getGui().getFrame().setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.add(guardar, BorderLayout.SOUTH);
		frame.add(editor);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

}
