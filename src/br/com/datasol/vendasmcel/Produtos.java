package br.com.datasol.vendasmcel;

import br.com.datasol.vendasmcel.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.vendasmcel.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class Produtos extends Activity{
	int posicao=0;
	String [] listaProdutos;
//	String [] listaQt = new String[] {"1","2","3","4", "5", "6", "7", "8", "9", "10"};
	EditText etQt;
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        setContentView(R.layout.listarreplicacaoestoque);
	        
			String url="http://192.168.1.3:8080/AndroidWeb/listarProdutos.jsp";			
			String respostaRetornada = null;
			try {
				respostaRetornada = ConexaoHTTPClient.executaHttpGet(url);
				String resposta = respostaRetornada.toString();
				
				char separador='#';
				int contUsuarios = 0;
				for(int i=0;i < resposta.length();i++){
					if (separador == resposta.charAt(i))
						contUsuarios++;
				}
				
				listaProdutos = new String[contUsuarios];
				
				char caracter_lido=resposta.charAt(0);
				String nome="";
				for (int i=0; caracter_lido != '^'; i++){
					caracter_lido = resposta.charAt(i);
					
					if (caracter_lido != '#'){
						nome += caracter_lido;
					}else {
						listaProdutos[posicao] = "" + nome;
						posicao++;
						nome = "";
					}
				}
			} catch (Exception e) {
				//Toast.makeText(MainActivity.this, "Erro: " + e, Toast.LENGTH_LONG);
				mensagemExibir("Produtos", "Erro " + e);
			}

	        ArrayAdapter<String> aaProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaProdutos);
            AutoCompleteTextView autoCompletarProdutos = (AutoCompleteTextView) findViewById(R.id.autoCompleteProdutos);
            autoCompletarProdutos.setAdapter(aaProdutos);
            
            Spinner spinnerUsuarios = (Spinner) findViewById(R.id.spinnerProdutos);
            ArrayAdapter<String> todosProdutos;
            todosProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaProdutos);
            todosProdutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUsuarios.setAdapter(todosProdutos);

/*            
            listaQt = new String[] {"1","2","3","4", "5", "6", "7", "8", "9", "10"};;
            
	        ArrayAdapter<String> aaQtProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaQt);
            AutoCompleteTextView autoCompletarQtProdutos = (AutoCompleteTextView) findViewById(R.id.autoCompleteQtProdutos);
            autoCompletarQtProdutos.setAdapter(aaQtProdutos);	
	        
	        System.out.println("qt " + listaQt);
	        Log.i("Lista ", listaQt.toString());
*/	       

	 }
	 
	   public void mensagemExibir(String titulo, String texto)
	   {
			AlertDialog.Builder mensagem = new AlertDialog.Builder(Produtos.this);
			mensagem.setTitle(titulo);
			mensagem.setMessage(texto);
			mensagem.setNeutralButton("OK",null);
			mensagem.show();
	   }
}