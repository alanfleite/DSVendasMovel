package br.com.datasol.vendasm;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.datasol.vendasm.DB.DB;
import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vendasm.dao.ProdVendDAO;
import br.com.datasol.vendasm.dao.RecDAO;
import br.com.datasol.vendasm.dao.VendedorDAO;
import br.com.datasol.vendasm.vo.ConfigVO;
import br.com.datasol.vendasm.vo.ProdVendVO;
import br.com.datasol.vendasm.vo.RecVO;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ReplicarVendasOff1 extends ListActivity {
//	EditText etUsuario;
	String[] listaVendaC;
	String[] listaVendaD;
	String garcom;
	String garcomLogado;
	
	private Cursor rec = null;
	private Cursor prodvend = null;
	private int totalDBRec = 0;
	private int total = 0;
	private ProgressDialog pg;
	private SQLiteDatabase db;
	FormatarCampos fc = new FormatarCampos();
	//public String url;
	//public String vendedor;
	
	
//	Button btVoltar;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		//listarMesas();
        iniciarReplicacaoVendaC();
	}

	public void iniciarReplicacaoVendaC(){
		
		//db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		rec = db.rawQuery("SELECT FANTASIA, RAZAO, CNPJ, TOT, DATAEMS, VENDEDOR, COD, CONDPG, CODCLI FROM rec where TOT not null and DATAEMS not null order by COD", null);		
		totalDBRec = rec.getCount();
		String respostaRetornada = null;
		
		VendedorDAO vdao = new VendedorDAO(getBaseContext());
        String vendedor = vdao.getVendedor();
        
        ConfigDAO configDAO = new ConfigDAO(getBaseContext());
        ConfigVO configVO = configDAO.getById(1);
        String url = configVO.getUrl();

        url= url + "/ReplicarVendaCIn.jsp";
/*        
        if (vendedor.equals("WASHINGTON")){
        	url= url + "/ReplicarVendaCIn.jsp";
        	//url = "http://192.168.1.12:8080/AndroidWeb/ReplicarVendaCIn.jsp";
        } else {
        	url= url + "/ReplicarVendaCIn.jsp";
        	//url = "http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaCIn.jsp";
			//url = "http://10.1.1.5:8080/AndroidWeb/ReplicarVendaCIn.jsp";
        }
*/        
        //Log.d("url vendaC", url);
		
        //String url;
		while(rec.moveToNext()){
			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			paramentosPost.add(new BasicNameValuePair("fantasia", rec.getString(rec.getColumnIndex("fantasia"))));
			//paramentosPost.add(new BasicNameValuePair("razao", rec.getString(rec.getColumnIndex("fantasia"))));
			paramentosPost.add(new BasicNameValuePair("razao", rec.getString(rec.getColumnIndex("razao"))));
			paramentosPost.add(new BasicNameValuePair("cnpj", rec.getString(rec.getColumnIndex("cnpj"))));
			paramentosPost.add(new BasicNameValuePair("tot", rec.getString(rec.getColumnIndex("tot"))));
			paramentosPost.add(new BasicNameValuePair("totg", rec.getString(rec.getColumnIndex("tot"))));
			paramentosPost.add(new BasicNameValuePair("dataems", rec.getString(rec.getColumnIndex("dataems"))));
			paramentosPost.add(new BasicNameValuePair("vendedor", rec.getString(rec.getColumnIndex("vendedor"))));
			paramentosPost.add(new BasicNameValuePair("codvend", rec.getString(rec.getColumnIndex("cod"))));
			if (rec.getString(rec.getColumnIndex("condpg")) == ""){
				paramentosPost.add(new BasicNameValuePair("condpg", ""));
			}else {
				paramentosPost.add(new BasicNameValuePair("condpg", rec.getString(rec.getColumnIndex("condpg"))));
			}
			paramentosPost.add(new BasicNameValuePair("codcli", rec.getString(rec.getColumnIndex("codcli"))));
			
/*			
	        RecDAO recDAO = new RecDAO(getBaseContext());
			RecVO recVO = recDAO.getById(rec.getColumnIndex("cod"));
			recVO.setSincronizado("S");
			
			if (recDAO.update(recVO)) {
				//Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
			}
*/
			try {
				respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
			} catch (Exception e) {
				mensagemExibir("Replicação do cliente", "Erro " + e);
			}
		}
		
//		Log.i("Replicação das Vendas", "3");
		String resposta = respostaRetornada.toString();
		resposta = resposta.replaceAll("\\s+", "");
		if (resposta != "0") {
			iniciarReplicacaoVendaD();
			
		} else
			mensagemExibir("Informação do Sistema", "Replicação das Vendas não foi realizada!!");
	}

	public void iniciarReplicacaoVendaD(){
		prodvend = db.rawQuery("SELECT prod, q1, vl_u, vl_t, codvend, vendedor, data, codcli, codprod, unid FROM prod_vend", null);		
		String respostaRetornada = null;
		VendedorDAO vdao = new VendedorDAO(getBaseContext());
        String vendedor = vdao.getVendedor();
        
        ConfigDAO configDAO = new ConfigDAO(getBaseContext());
        ConfigVO configVO = configDAO.getById(1);
	    String url = configVO.getUrl();
        //String url;
	    
	    url= url + "/ReplicarVendaDIn.jsp";
/*        
		if (vendedor.equals("WASHINGTON")){
			url= url + "/ReplicarVendaDIn.jsp";
			//url = "http://192.168.1.12:8080/AndroidWeb/ReplicarVendaDIn.jsp";
		} else {
			url= url + "/ReplicarVendaDIn.jsp";
			//url = "http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaDIn.jsp";
			//url = "http://10.1.1.5:8080/AndroidWeb/ReplicarVendaDIn.jsp";				
		}
*/		
		//Log.d("url vendaD", url);
		
		while(prodvend.moveToNext()){
			
			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			paramentosPost.add(new BasicNameValuePair("prod", prodvend.getString(prodvend.getColumnIndex("prod"))));
			paramentosPost.add(new BasicNameValuePair("q1", prodvend.getString(prodvend.getColumnIndex("q1"))));
			paramentosPost.add(new BasicNameValuePair("vl_u", prodvend.getString(prodvend.getColumnIndex("vl_u"))));
			paramentosPost.add(new BasicNameValuePair("vl_t", prodvend.getString(prodvend.getColumnIndex("vl_t"))));
			paramentosPost.add(new BasicNameValuePair("codvend", prodvend.getString(prodvend.getColumnIndex("codvend"))));
			paramentosPost.add(new BasicNameValuePair("vendedor", prodvend.getString(prodvend.getColumnIndex("vendedor"))));			
			paramentosPost.add(new BasicNameValuePair("data", prodvend.getString(prodvend.getColumnIndex("data"))));
			paramentosPost.add(new BasicNameValuePair("codcli", prodvend.getString(prodvend.getColumnIndex("codcli"))));
			paramentosPost.add(new BasicNameValuePair("codprod", prodvend.getString(prodvend.getColumnIndex("codprod"))));
			paramentosPost.add(new BasicNameValuePair("unid", prodvend.getString(prodvend.getColumnIndex("unid"))));
			
/*			
			ProdVendDAO prodVendDAO = new ProdVendDAO(getBaseContext());
			ProdVendVO prodVendVO = prodVendDAO.getById(prodvend.getColumnIndex("cod"));
			prodVendVO.setSincronizado("S");
			
			if (prodVendDAO.update(prodVendVO)) {
				//Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
			}
*/			
			
			try {
				respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mensagemExibir("Replicação do cliente", "Erro " + e);
			}
		}
		String resposta = respostaRetornada.toString();
		resposta = resposta.replaceAll("\\s+", "");
		if (resposta != "0") {
			mensagemExibir("Replicação das Vendas", "Realizada com sucesso no servidor!!");

			//deleteVendaC();
			//deleteVendaD();
		} else
			mensagemExibir("Replicação das Vendas", "Não foi realizada!!");
	}
	
	public void deleteVendaC() {
		RecDAO dao = new RecDAO(getBaseContext());
		dao.deleteAll();
	}
	
	public void deleteVendaD() {
		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		dao.deleteAll();
	}	
	
	public void mensagemExibir(String titulo, String mensagemm) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarVendasOff1.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(mensagemm);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
}