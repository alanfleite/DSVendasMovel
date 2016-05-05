package br.com.datasol.vendasm;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.datasol.vendasm.conexaoweb.ConexaoHTTPClient;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MesasLivres extends ListActivity {
//	EditText etUsuario;
	String[] listaMesas;
	String garcom;
	String garcomLogado;
	
//	Button btVoltar;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		//listarMesas();
        abrirMesa();

		//ArrayAdapter<String> aaCursos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaMesas);
		//setListAdapter(aaCursos);
	}
	
	public void abrirMesa() {
		Log.i("abrir mesa", "1");
		//String url = "http://192.168.1.3:8080/AndroidWeb/abrirMesa.jsp";
		//String url = "http://192.168.1.3:8080/AndroidWeb/ReplicarVendaCIn.jsp";
		String url = "http://192.168.1.3:8080/AndroidWeb/abrirMesa1.jsp";
		ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
		paramentosPost.add(new BasicNameValuePair("f", "teste"));
		paramentosPost.add(new BasicNameValuePair("r", "teste"));
		String respostaRetornada = null;
		Log.i("abrir", "2");
		try {
			Log.i("abrir", "3 " + paramentosPost);
			respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
			Log.i("abrir", "3.5");
			String resposta = respostaRetornada.toString();
			resposta = resposta.replaceAll("\\s+", "");
			Log.i("abrir", "4" + resposta);
			if (resposta.equals("1")) {
				mensagemExibir("Mesas", "Mesa Aberta com sucesso!!");
				Log.i("abrir", "5");
				
				//iniciarVenda(mesa, garcom);
			} else
				mensagemExibir("Mesas", "Mesa não foi Aberta!!");

		} catch (Exception e) {
			mensagemExibir("Mesas erro", "Erro " + e);
		}
	}

	public void mensagemExibir(String titulo, String mensagemm) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(MesasLivres.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(mensagemm);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
	
			
}