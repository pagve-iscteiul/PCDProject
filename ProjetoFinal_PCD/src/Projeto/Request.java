package Projeto;

import java.io.Serializable;

public class Request implements Serializable{
	
	private int order;
	private String name;
	private String text;
	private static final long serialVersionUID = 6024938563432345231L;

//	Orders: 1 -> Pedir Directório
//	Orders: 2 -> Pedir Tamanho do ficheiro
//	Orders: 3 -> Exibir conteudos do ficheiro
//	Orders: 4 -> Editar ficheiro
//	Orders: 5 -> Criar ficheiro
//	Orders: 6 -> Eliminar ficheiro
//	Orders: 7 -> Salvar ficheiro no servidor
//	Orders: 8 -> Fechar o exibir
	
	
	public Request(int order) {
		this.order = order;
	}
	
	public Request(int order, String name) {
		this.order = order;
		this.name=name;
	}
	
	
	public Request(int order, String name, String text) {
		this.order = order;
		this.name=name;
		this.text = text;
	}

	public int getOrder() {
		return order;
	}
	
	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

}
