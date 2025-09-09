package Projeto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

public class GUIExplorer extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("File explorer");
	private JPanel panel = new JPanel();
	private JList<String> list;

	public GUIExplorer(Cliente c) {

		frame.setLayout(new BorderLayout(10, 10));
		panel.setLayout(new FlowLayout());

		JButton tamanho = new JButton("Tamanho");
		tamanho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.sendRequestTamanho(list.getSelectedValue());
					frame.setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(tamanho);

		JButton exibir = new JButton("Exibir");
		exibir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.sendRequestExibir(list.getSelectedValue());
					frame.setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(exibir);

		JButton editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.sendRequestEdit(list.getSelectedValue());
					frame.setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(editar);

		JButton novo = new JButton("Novo");
		novo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUINovoFicheiro(c);
				frame.dispose();
			}
		});
		panel.add(novo);

		JButton apagar = new JButton("Apagar");
		apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.sendRequestDeleteFile(list.getSelectedValue());
					frame.setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(apagar);

		frame.add(panel, BorderLayout.SOUTH);

	}

	public void criarRootGUI(String[] ficheiros) {
		list = new JList<String>(ficheiros);
		list.setVisibleRowCount(8);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frame.add(new JScrollPane(list), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
