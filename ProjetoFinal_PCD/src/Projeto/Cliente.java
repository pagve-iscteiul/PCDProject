package Projeto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private GUIExplorer gui = new GUIExplorer(this);
	private RemoteDirectory rd;

	public synchronized void runCliente(int PORT, InetAddress endereço)
			throws IOException, InterruptedException {
		socket = new Socket(endereço, PORT);
		try {
			System.out.println("Cliente conectou-se");
			connectToServer(socket);
			sendRequestRoot();
		} catch (IOException e) {
			System.out.println("Cliente caiu");
			e.printStackTrace();
		}
	}

	private void connectToServer(Socket socket) throws IOException {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

//Tratar dos request to cliente

	public void sendRequestRoot() throws IOException {
		Request root = new Request(1);
		out.writeObject(root);
		try {
			rd = (RemoteDirectory) in.readObject();
			gui.criarRootGUI(rd.getFicheiros());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void sendRequestTamanho(String name) throws IOException {
		Request tamanho = new Request(2, name);
		out.writeObject(tamanho);
		try {
			RemoteFile rf = (RemoteFile) in.readObject();
			new GUITamanho(this, rf);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void sendRequestExibir(String name) throws IOException {
		Request edit = new Request(3, name);
		out.writeObject(edit);
		try {
			RemoteFile rf = (RemoteFile) in.readObject();
			new GUIExibir(this, rf);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void sendRequestEdit(String name) throws IOException {
		Request edit = new Request(4, name);
		out.writeObject(edit);
		try {
			RemoteFile rf = (RemoteFile) in.readObject();
			new GUIEditar(this, rf);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void sendRequestNewFile(String name) throws IOException {
		Request newFile = new Request(5, name);
		out.writeObject(newFile);

	}

	public void sendRequestDeleteFile(String name) throws IOException {
		Request newFile = new Request(6, name);
		out.writeObject(newFile);
	}

	public void sendRequestEditSave(String name, String text) throws IOException {
		Request save = new Request(7, name, text);
		out.writeObject(save);
	}
	
	public void sendRequestExibirFechar(String name, String text) throws IOException {
		Request fechar = new Request(8, name, text);
		out.writeObject(fechar);
	}
	
	
	

	
	
	

	public GUIExplorer getGui() {
		return gui;
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new Cliente().runCliente(Servidor.PORT, InetAddress.getByName(null));
	}
}
