package Projeto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DealWithClient extends Thread {

	/**
	 * 
	 */

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private Servidor servidor = Servidor.getServidor();
	private Request rq;
	
	public DealWithClient() {
	}

	public void run() {
		try {
			System.out.println("thread iniciada");
			doConnections(socket);
			serve();
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			System.out.println("O cliente desconectou-se");
			e.printStackTrace();
		}
	}

	private void doConnections(Socket socket) throws IOException {

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	private void serve() throws IOException, InterruptedException, ClassNotFoundException {
		while (true) {
			System.out.println("entra no lock");
			rq = (Request) in.readObject();
			treatRequest(rq);
		}
	}

	

	private void treatRequest(Request rq) throws IOException {
		if (rq.getOrder() == 1) {
			RemoteDirectory root = servidor.getRoot();
			out.writeObject(root);

		} else if (rq.getOrder() == 2) {
			RemoteFile tamanho = servidor.getFileSize(rq.getName());
			out.writeObject(tamanho);

		} else if (rq.getOrder() == 3) {
			RemoteFile exibir = servidor.getFileDetails(rq.getName());
			out.writeObject(exibir);

		} else if (rq.getOrder() == 4) {
			RemoteFile edit = servidor.sendFileToEdit(rq.getName());
			out.writeObject(edit);

		} else if (rq.getOrder() == 5) {
			servidor.createFile(rq.getName());

		} else if (rq.getOrder() == 6) {
			servidor.deleteFile(rq.getName());

		} else if (rq.getOrder() == 7) {
			servidor.receiveAndEdit(rq.getName(), rq.getText());

		} else if (rq.getOrder() == 8) {
			servidor.exitDetails(rq.getName());

		}

	}

	public void getServerSocket(Socket socket) {
		this.socket = socket;

	}

}