package Projeto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	public static final int PORT = 8080;
	private static String FilePath = (System.getProperty("user.dir") + "\\" + "ficheiros\\");
	private DealWithClient dc;
	private static final Servidor servidor = new Servidor();
	private ArrayList<LocalFile> files = new ArrayList<>();

	public static Servidor getServidor() {
		return servidor;
	}

	public void StartServidor(int PORT, String folder) throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		createArrayFiles();
		System.out.println("Server ligado");
		while (true) {
			Socket socket = serverSocket.accept();
			try {
				System.out.println("Um cliente connectou-se");
				dc = new DealWithClient();
				dc.getServerSocket(socket);
				dc.start();

			} catch (Exception e) {
				System.out.println("Erro na ligaçao do cliente");
			}
		}
	}

	public RemoteDirectory getRoot() {
		LocalDirectory ld = new LocalDirectory(FilePath);
		RemoteDirectory rd = new RemoteDirectory(ld.getDirectoryListing());
		return rd;
	}

	public RemoteFile getFileSize(String name) {
		try {
			LocalFile lf = getFile(name);
			RemoteFile tamanho = new RemoteFile(lf.getName());
			tamanho.setSize(lf.length());
			return tamanho;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public RemoteFile getFileDetails(String name) {
		LocalFile lf = getFile(name);
		while (true) {
			System.out.println("Trying to get lock");
			try {
				if (lf.readLock()) {
					System.out.println("Got read lock");
					RemoteFile exibir = new RemoteFile(lf.getName());
					exibir.setText(lf.read());
					return exibir;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createFile(String name) {
		LocalDirectory ld = new LocalDirectory(FilePath);
		try {
			LocalFile lf = (LocalFile) ld.newFile(name);
			files.add(lf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RemoteFile sendFileToEdit(String name) {
		LocalFile lf = getFile(name);
		while (true) {
			System.out.println("Trying to get lock");
			try {
				if (lf.writeLock()) {
					System.out.println("Got write lock");
					RemoteFile edit = new RemoteFile(lf.getName());
					edit.setText(lf.read());
					return edit;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void exitDetails(String name) {
		try {
			LocalFile lf = getFile(name);
			lf.readUnlock();
			System.out.println(lf.getName() + "is now unlocked");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveAndEdit(String name, String text) {
		LocalFile lf = getFile(name);
		try {
			lf.write(text);
			lf.writeUnlock();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deleteFile(String name) {
		LocalDirectory ld = new LocalDirectory(FilePath);
		LocalFile lf = getFile(name);
		try {
			if (lf.writeLock())
				ld.delete(name);
				files.remove(getFile(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createArrayFiles() {
		LocalDirectory ld = new LocalDirectory(FilePath);
		ld.getLocalFiles(files);
	}

	public LocalFile getFile(String name) {
		for (LocalFile localFile : files) {
			if (localFile.getName().equals(name)) {
				return localFile;
			}
		}
		return null;

	}

	public static void main(String[] args) throws InterruptedException {
		try {
			getServidor().StartServidor(PORT, FilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
