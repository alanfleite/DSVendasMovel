package br.com.datasol.vo;

public class ClienteVO {
	
	private Integer id;
	private String nome;
	private String email;
	private String endereco;
	private String numero;
	
	public ClienteVO(){
		
	}
	
	public ClienteVO(int id, String nome, String email, String endereco, String numero){
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.numero = numero;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
