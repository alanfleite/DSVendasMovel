package br.com.datasol.vendasm;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vo.ConfigVO;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ReplicarClientesOff extends ListActivity {
//	EditText etUsuario;
	String[] listaClienteC;
	
	private Cursor cCliente = null;
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
        replicacaoCliente();
	}

	public void replicacaoCliente(){
		String sql = "SELECT USUARIO, RAZAO, ENDE, ENDE_NUM, FONE, CEL, BAIRRO, CIDADE, UF, CNPJ, RG, INSCEST,"
				+ " RESP, EMAIL, CONTATO, CPF FROM CAD_CLI where RESP = \"Sim\"";
		cCliente = db.rawQuery(sql, null);
		
		//Log.i("Replicação das Clientes", sql);
		
		String respostaRetornada = null;

        ConfigDAO configDAO = new ConfigDAO(getBaseContext());
        ConfigVO configVO = configDAO.getById(1);
	    String url = configVO.getUrl();
		
		while(cCliente.moveToNext()){
			//Log.i("Replicação das Clientes", "1");
			
	        url= url + "/ReplicarClienteIn.jsp";

			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			paramentosPost.add(new BasicNameValuePair("usuario", cCliente.getString(cCliente.getColumnIndex("usuario"))));
			paramentosPost.add(new BasicNameValuePair("razao", cCliente.getString(cCliente.getColumnIndex("razao"))));
			paramentosPost.add(new BasicNameValuePair("ende", cCliente.getString(cCliente.getColumnIndex("ende"))));
			paramentosPost.add(new BasicNameValuePair("ende_num", cCliente.getString(cCliente.getColumnIndex("ende_num"))));
			paramentosPost.add(new BasicNameValuePair("fone", cCliente.getString(cCliente.getColumnIndex("fone"))));
			paramentosPost.add(new BasicNameValuePair("cel", cCliente.getString(cCliente.getColumnIndex("cel"))));
			paramentosPost.add(new BasicNameValuePair("bairro", cCliente.getString(cCliente.getColumnIndex("bairro"))));
			paramentosPost.add(new BasicNameValuePair("cidade", cCliente.getString(cCliente.getColumnIndex("cidade"))));
			paramentosPost.add(new BasicNameValuePair("uf", cCliente.getString(cCliente.getColumnIndex("uf"))));
			paramentosPost.add(new BasicNameValuePair("cnpj", cCliente.getString(cCliente.getColumnIndex("cnpj"))));
			paramentosPost.add(new BasicNameValuePair("rg", cCliente.getString(cCliente.getColumnIndex("rg"))));
			paramentosPost.add(new BasicNameValuePair("inscest", cCliente.getString(cCliente.getColumnIndex("inscest"))));
			//paramentosPost.add(new BasicNameValuePair("resp", cCliente.getString(cCliente.getColumnIndex("resp"))));
			paramentosPost.add(new BasicNameValuePair("email", cCliente.getString(cCliente.getColumnIndex("email"))));
			paramentosPost.add(new BasicNameValuePair("contato", cCliente.getString(cCliente.getColumnIndex("contato"))));
			paramentosPost.add(new BasicNameValuePair("cpf", cCliente.getString(cCliente.getColumnIndex("cpf"))));
			
			//Log.i("url", url);
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
			mensagemExibir("Informação do Sistema", "Replicação de Clientes realizada com sucesso!!");
		} else
			mensagemExibir("Informação do Sistema", "Replicação de Clientes não foi realizada!!");
	}
	
	public void mensagemExibir(String titulo, String mensagemm) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarClientesOff.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(mensagemm);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
}