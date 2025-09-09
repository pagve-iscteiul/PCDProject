package Projeto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class GUIEditar {

	
	public GUIEditar(Cliente c ,RemoteFile file) {
		JFrame frame = new JFrame("Editando: "+file.getName());
		String text = file.getText();
		JTextArea editor = new JTextArea(text);
		editor.setEditable(true);
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newText = (editor.getText());
				try {
					c.sendRequestEditSave(file.getName(), newText);
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
