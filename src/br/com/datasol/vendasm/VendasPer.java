package br.com.datasol.vendasm;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.adapters.Cad_cliAdapter;
import br.com.datasol.vendasm.adapters.EstoqueAdapter;
import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.vendasm.dao.VendedorDAO;
import br.com.datasol.vo.VendedorVO;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class VendasPer extends Activity {
	
	FormatarCampos fc = new FormatarCampos();
	
	int posicao = 0;
	String[] listaProdutos;
	ListView ltw;

	private EditText txtVendedor;
	private EditText txtDataI;
	private EditText txtDataF;

	private String fantazia;
	private String dataems;
	private String totg;
	private String vendedor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendasper);

		txtVendedor = (EditText) findViewById(R.id.txtVendedor);
		txtDataI = (EditText) findViewById(R.id.txtDataI);
		txtDataF = (EditText) findViewById(R.id.txtDataF);
		ltw = (ListView) findViewById(R.id.ltvDados);
		ltw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		txtDataI.setText(fc.dataAtualTela());
		txtDataF.setText(fc.dataAtualTela());

		// pegando o vendedor
		VendedorDAO vDAO = new VendedorDAO(getBaseContext());
		VendedorVO vVO = vDAO.getById(1);

		txtVendedor.setText(vVO.getNome());

		Button btnVerificar = (Button) findViewById(R.id.btVerificar);
		Button btnFechar = (Button) findViewById(R.id.btFechar);

		btnVerificar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				verificar(txtVendedor.getText().toString(), txtDataI.getText()
						.toString(), txtDataF.getText().toString());
			}
		});

		btnFechar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void verificar(String vendedor, String dataI, String dataF) {
		Log.d("Verdas per 0", "");
		String url = "http://192.168.1.3:8080/AndroidWeb/VendasPer.jsp?vendedor="
				+ vendedor + "&datai=" + dataI + "&dataf=" + dataF + ";";
		
		Log.d("Verdas per 1", url);
		
		String respostaRetornada = null;
		try {
			Log.d("Verdas per 1.5", "");
			respostaRetornada = ConexaoHTTPClient.executaHttpGet(url);
			String resposta = respostaRetornada.toString();
			Log.d("Verdas per 1.6", "");
			char separador = '#';
			int contUsuarios = 0;
			for (int i = 0; i < resposta.length(); i++) {
				if (separador == resposta.charAt(i))
					contUsuarios++;
			}

			Log.d("Verdas per 1.7", "");
			listaProdutos = new String[contUsuarios];

			char caracter_lido = resposta.charAt(0);
			String nome = "";
			for (int i = 0; caracter_lido != '^'; i++) {
				caracter_lido = resposta.charAt(i);

				if (caracter_lido != '#') {
					nome += caracter_lido;
				} else {
					Log.d("Verdas per 1.8", "");
					listaProdutos[posicao] = "" + nome;
					posicao++;

					int p = nome.indexOf("@");
					fantazia = nome.substring(0, p);
					int cp = nome.indexOf("$");
					p = p + 1;
					dataems = nome.substring(p, cp);
					int u = nome.indexOf("|");
					cp = cp + 1;
					totg = nome.substring(cp, u);
					int q = nome.indexOf("}");
					u = u + 1;
					vendedor = nome.substring(u, q);
					
					Log.d("Verdas per 2", nome);
					
					nome = "";
				}
			}
		} catch (Exception e) {
			// Toast.makeText(MainActivity.this, "Erro: " + e,
			// Toast.LENGTH_LONG);
			// mensagemExibir("Produtos", "Erro " + e);
		}

		ArrayAdapter<String> aaVendasPer = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaProdutos);
		AutoCompleteTextView autoCompletarProdutos = (AutoCompleteTextView) findViewById(R.id.autoCompleteProdutos);
		autoCompletarProdutos.setAdapter(aaVendasPer);

		//ltwVendaC.setAdapter(aaVendasPer);
		//ltwVendaC.setAdapter(new EstoqueAdapter(getBaseContext(), listaProdutos));

	}
}
