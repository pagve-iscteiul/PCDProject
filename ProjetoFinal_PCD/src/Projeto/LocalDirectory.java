package Projeto;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

public class LocalDirectory implements PCDDirectory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FilePath;
	private File root = new File("");

	public LocalDirectory(String FilePath) {
		this.FilePath = FilePath;
		root = new File(FilePath);
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	@Override
	public boolean fileExists(String name) throws IOException {
		if (new File(FilePath + "//" + name).exists()) {
			System.out.println("File " + name + " exists");
			return true;
		}
		System.out.println("File " + name + " doesn't exists");
		return false;
	}

	@Override
	public PCDFile newFile(String name) throws FileSystemException, IOException {
		if (fileExists(name)) {
			File file = new File(FilePath + "//" + name);
			file.delete();
		}
		File file = new File(FilePath + "//" + name + ".txt");
		file.createNewFile();
		LocalFile newFile = new LocalFile(file);
		return newFile;
	}

	@Override
	public void delete(String name) throws FileSystemException, IOException {
		if (fileExists(name)) {
			new File(FilePath + "//" + name).delete();
		}
	}

	@Override
	public String[] getDirectoryListing() {
		String[] ficheiros = root.list();
		return ficheiros;
	}

	public ArrayList<LocalFile> getLocalFiles(ArrayList<LocalFile> files) {
		for (File f : root.listFiles()) {
			LocalFile localFile = new LocalFile(f);
			files.add(localFile);
		}
		return files;
	}


	@Override
	public PCDFile getFile(String name) throws FileSystemException, IOException {
		if (fileExists(name)) {
			return new LocalFile(new File(FilePath + "//" + name));
		}
		return null;
	}

}
