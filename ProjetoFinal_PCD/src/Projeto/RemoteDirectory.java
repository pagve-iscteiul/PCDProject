package Projeto;

import java.io.Serializable;

public class RemoteDirectory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] ficheiros;

	public RemoteDirectory(String[] ficheiros) {
		this.ficheiros = ficheiros;
	}

	public String[] getFicheiros() {
		return ficheiros;
	}
	
	
	

}
