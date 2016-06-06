package br.com.datasol.vendasmcel.vo;

public class ProdVendVO {
	private Integer cod;
	private String codprod;
	private String prod;
	private String q1;
	private String vl_u;
	private String vl_t;
	private String codvend;
	private String data;	
	private String unid;
	private String vendedor;
	private String codcli;
	private String sincronizado;
	
	public ProdVendVO() {
		
	}

	public ProdVendVO(Integer cod, String codprod, String prod, String q1, String vl_u,
			String vl_t, String codvend, String data, String unid,
			String vendedor, String codcli, String sincronizado) {
		this.cod = cod;
		this.codprod = codprod;
		this.prod = prod;
		this.q1 = q1;
		this.vl_u = vl_u;
		this.vl_t = vl_t;
		this.codvend = codvend;
		this.data = data;
		this.unid = unid;
		this.vendedor = vendedor;
		this.codcli = codcli;
		this.sincronizado = sincronizado;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getCodprod() {
		return codprod;
	}

	public void setCodprod(String codprod) {
		this.codprod = codprod;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getVl_u() {
		return vl_u;
	}

	public void setVl_u(String vl_u) {
		this.vl_u = vl_u;
	}

	public String getVl_t() {
		return vl_t;
	}

	public void setVl_t(String vl_t) {
		this.vl_t = vl_t;
	}

	public String getCodvend() {
		return codvend;
	}

	public void setCodvend(String codvend) {
		this.codvend = codvend;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getCodcli() {
		return codcli;
	}

	public void setCodcli(String codcli) {
		this.codcli = codcli;
	}

	public String getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}	
}
