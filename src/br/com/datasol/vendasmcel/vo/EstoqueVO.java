package br.com.datasol.vendasmcel.vo;

public class EstoqueVO {
	
	private Integer cod;
	private String prod;
	private String codprod;
	private String q1;
	private String vt;
	private String unid;
	
	public EstoqueVO() {
		
	}

	public EstoqueVO(Integer cod, String prod, String codprod, String q1,
			String vt, String unid) {
		this.cod = cod;
		this.prod = prod;
		this.codprod = codprod;
		this.q1 = q1;
		this.vt = vt;
		this.unid = unid;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getCodprod() {
		return codprod;
	}

	public void setCodprod(String codprod) {
		this.codprod = codprod;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getVt() {
		return vt;
	}

	public void setVt(String vt) {
		this.vt = vt;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}
	
}