package br.com.datasol.vendasm.vo;

public class Cad_cliVO {
	private Integer cod;
	private String codcli;
	private String usuario;
	private String razao;
	private String ende;
	private String ende_num;
	private String fone;
	private String cel;
	private String bairro;
	private String cidade;
	private String uf;
	private String cnpj;
	private String rg;
	private String inscest;
	private String resp;
	private String email;
	private String contato;
	private String cpf;
	private String atrasoinfo;
//	private String parcelaatrazo;
//	private String infoparcelaatrazo;
	
	public Cad_cliVO() {
		
	}
	
	public Cad_cliVO(Integer cod, String codcli, String usuario, String razao, String ende,
			String ende_num, String fone, String cel, String bairro,
			String cidade, String uf, String cnpj, String rg,
			String inscest, String resp, String email, String contato,
			String cpf) {
		//, String parcelaatrazo, String infoparcelaatrazo		
		this.cod = cod;
		this.codcli = codcli;
		this.usuario = usuario;
		this.razao = razao;
		this.ende = ende;
		this.ende_num = ende_num;
		this.fone = fone;
		this.cel = cel;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cnpj = cnpj;
		this.rg = rg;
		this.inscest = inscest;
		this.resp = resp;
		this.email = email;
		this.contato = contato;
		this.cpf = cpf;
//		this.parcelaatrazo = parcelaatrazo;
//		this.infoparcelaatrazo = infoparcelaatrazo;
	}




	public Integer getCod() {
		return cod;
	}
	public void setCod(Integer cod) {
		this.cod = cod;
	}
	public String getCodcli() {
		return codcli;
	}
	public void setCodcli(String codcli) {
		this.codcli = codcli;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getRazao() {
		return razao;
	}
	public void setRazao(String razao) {
		this.razao = razao;
	}
	public String getEnde() {
		return ende;
	}
	public void setEnde(String ende) {
		this.ende = ende;
	}
	public String getEnde_num() {
		return ende_num;
	}
	public void setEnde_num(String ende_num) {
		this.ende_num = ende_num;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getInscest() {
		return inscest;
	}
	public void setInscest(String inscest) {
		this.inscest = inscest;
	}
	public String getResp() {
		return resp;
	}
	public void setResp(String resp) {
		this.resp = resp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getAtrasoinfo() {
		return atrasoinfo;
	}

	public void setAtrasoinfo(String atrasoinfo) {
		this.atrasoinfo = atrasoinfo;
	}


/*	
	public String getParcelaatrazo() {
		return parcelaatrazo;
	}

	public void setParcelaatrazo(String parcelaatrazo) {
		this.parcelaatrazo = parcelaatrazo;
	}
	
	public String getInfoparcelaatrazo() {
		return infoparcelaatrazo;
	}
	
	public void setInfoparcelaatrazo(String infoparcelaatrazo) {
		this.infoparcelaatrazo = infoparcelaatrazo;
	}
*/		
}