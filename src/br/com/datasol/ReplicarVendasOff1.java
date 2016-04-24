package br.com.datasol;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.datasol.DB.DB;
import br.com.datasol.auxilio.FormatarCampos;
import br.com.datasol.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.dao.ProdVendDAO;
import br.com.datasol.dao.RecDAO;
import br.com.datasol.dao.VendedorDAO;
import br.com.datasol.vo.RecVO;
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
		
		rec = db.rawQuery("SELECT FANTASIA, RAZAO, CNPJ, TOT, DATAEMS, VENDEDOR, COD, CONDPG FROM rec where TOT not null and DATAEMS not null", null);		
		totalDBRec = rec.getCount();
		String respostaRetornada = null;
		
		VendedorDAO vdao = new VendedorDAO(getBaseContext());
        String vendedor = vdao.getVendedor();
        String url;
		while(rec.moveToNext()){
			//Log.i("Replicação das Vendas", "1");
			
	        if (vendedor.equals("WASHINGTON")){
	        	url = "http://192.168.1.12:8080/AndroidWeb/ReplicarVendaCIn.jsp";
	        } else {
	        	url = "http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaCIn.jsp";
				//url = "http://10.1.1.5:8080/AndroidWeb/ReplicarVendaCIn.jsp";
	        }
			
/*	        
	        Log.i("Replicação das Vendas", "2 "  
	                + rec.getString(rec.getColumnIndex("fantasia")) + " - " + rec.getString(rec.getColumnIndex("fantasia")) + " - "  
	        		+ rec.getString(rec.getColumnIndex("fantasia")) + " - " +  rec.getString(rec.getColumnIndex("cnpj")) +  " - " 
	        		+ rec.getString(rec.getColumnIndex("tot")) +  " - " + rec.getString(rec.getColumnIndex("dataems")) + " - "  
	        		+ rec.getString(rec.getColumnIndex("vendedor")) +  " - " + rec.getString(rec.getColumnIndex("cod")));
*/	
	        
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
			mensagemExibir("Replicação das Vendas", "Não foi realizada!!");
	}

	public void iniciarReplicacaoVendaD(){
		prodvend = db.rawQuery("SELECT prod, q1, vl_u, vl_t, codvend, vendedor FROM prod_vend", null);		
		String respostaRetornada = null;
		
		VendedorDAO vdao = new VendedorDAO(getBaseContext());
        String vendedor = vdao.getVendedor();
        
        String url;
        
		while(prodvend.moveToNext()){
			if (vendedor.equals("WASHINGTON")){
				url = "http://192.168.1.12:8080/AndroidWeb/ReplicarVendaDIn.jsp";
			} else {
				url = "http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaDIn.jsp";
				//url = "http://10.1.1.5:8080/AndroidWeb/ReplicarVendaDIn.jsp";				
			}
			
			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			paramentosPost.add(new BasicNameValuePair("prod", prodvend.getString(prodvend.getColumnIndex("prod"))));
			paramentosPost.add(new BasicNameValuePair("q1", prodvend.getString(prodvend.getColumnIndex("q1"))));
			paramentosPost.add(new BasicNameValuePair("vl_u", prodvend.getString(prodvend.getColumnIndex("vl_u"))));
			paramentosPost.add(new BasicNameValuePair("vl_t", prodvend.getString(prodvend.getColumnIndex("vl_t"))));
			paramentosPost.add(new BasicNameValuePair("codvend", prodvend.getString(prodvend.getColumnIndex("codvend"))));
			paramentosPost.add(new BasicNameValuePair("vendedor", prodvend.getString(prodvend.getColumnIndex("vendedor"))));
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

			deleteVendaC();
			deleteVendaD();
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