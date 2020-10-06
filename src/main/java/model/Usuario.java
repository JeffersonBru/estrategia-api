package model;

public class Usuario {
	
	public String id, name, email, senha, token;
	
	public Usuario(String name, String email, String senha) {
		this.name = name;
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

}
