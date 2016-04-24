package br.com.datasol.vo;

public class ConfigVO {
	
	private Integer id;
	private String url;
	private String usuario;
	private String senha;
	
	public ConfigVO(){
		
	}
	
	public ConfigVO(int id, String url, String usuario, String senha){
		this.id = id;
		this.url = url;
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	
}