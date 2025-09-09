package Projeto;

import java.io.Serializable;

public class RemoteFile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int size;
	private String text;
	
	public RemoteFile(String name) {
		this.name = name; 
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}


	
	
}
