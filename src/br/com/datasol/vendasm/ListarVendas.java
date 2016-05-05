package br.com.datasol.vendasm;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.dao.EstoqueDAO;
import br.com.datasol.vendasm.dao.ProdVendDAO;
import br.com.datasol.vendasm.dao.RecDAO;
import br.com.datasol.vo.EstoqueVO;
import br.com.datasol.vo.ProdVendVO;
import br.com.datasol.vo.RecVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListarVendas extends Activity {

	FormatarCampos fc = new FormatarCampos();

	List<RecVO> listaVendaC = null;
	private ListView lvVendaC;
	private String[] lstVendaC;

	ListView lvVendaD;
	List<ProdVendVO> listaVendaD = null;
	private String[] lstVendaD;

	int idItem = 0;
	private static int MENU_EDITAR = 1;
	private static int MENU_BUSCAR_PRODVEND = 2;
	private static int MENU_APAGAR_PRODUTO = 3;
	private String nome;

	private ArrayList<String> lstVendaC_encontradas = new ArrayList<String>();
	private ArrayList<String> lstVendaD_encontradas = new ArrayList<String>();
	private SQLiteDatabase db;
	private Cursor cVendaC = null;
	private Cursor cVendaD = null;
	private int posicao = 0;
	private int posicaoD = 0;
	String idVendaC;

	EditText etProcurar;

	Button btApagarProduto;
	Button btFechar;

	// @SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendaslistar);

		lvVendaC = (ListView) findViewById(R.id.listVendas);
		lvVendaC.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		lvVendaD = (ListView) findViewById(R.id.listProdutos);
		lvVendaD.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		etProcurar = (EditText) findViewById(R.id.etProcurar);

		//btApagarProduto = (Button) findViewById(R.id.btApagar);
		btFechar = (Button) findViewById(R.id.btFechar);

		// registerForContextMenu(ltwVendaC);

		BuscarVendasC();

		etProcurar.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				CarregarEncontrados();

				lvVendaC.setAdapter(new ArrayAdapter<String>(ListarVendas.this,
						android.R.layout.simple_list_item_1,
						lstVendaC_encontradas));
			}
		});

		lvVendaC.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View view, int position,
					long index) {
				Mensagem("Venda selecionada : "
						+ lstVendaC_encontradas.get(position).toString());

				String cod = lstVendaC_encontradas.get(position).toString();
				int p = cod.indexOf("-");
				final String cod1 = cod.substring(0, p - 1);

				idVendaC = cod1;
				
				final RecDAO dao = new RecDAO(getBaseContext());
				final RecVO vo = dao.getById(Integer.parseInt(cod1));

				final String cliente = vo.getRazao();

				
				Builder msg = new Builder(ListarVendas.this);
				msg.setMessage("Deseja APAGAR esta venda: " + cliente + "?");
				msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						apagarVendaC(cod1, cliente);
					}
				});
				msg.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						listarProdutosVenda(cod1);					}
				});

				msg.show();
			}
		});

		lvVendaD.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View view, int position,
					long index) {
				Log.d("produtos", "1");
				//String cod = lstVendaD_encontradas.get(position).toString();
				//Log.d("Produto selecionado : " + cod);
				Mensagem("Produto selecionado : " + lstVendaD_encontradas.get(position).toString());
				//Log.d("produtos", "2");
				String cod = lstVendaD_encontradas.get(position).toString();
				//Log.d("produtos", "3");
				int p = cod.indexOf("-");
				//Log.d("produtos", "4");
				String cod1 = cod.substring(0, p - 1);
				//Log.d("produtos", "5 - cod " + cod1); 
				// final RecDAO dao = new RecDAO(getBaseContext());
				// final RecVO vo = dao.getById(Integer.parseInt(cod1));

				// idVendaC = String.valueOf(vo.getCod());

				apagarProduto(cod1);
			}
		});

		btFechar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void BuscarVendasC(){
		posicao = 0;
		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		cVendaC = db.rawQuery("SELECT COD, FANTASIA, RAZAO, DATAEMS, CIDA, VENDEDOR, TOT from REC order by COD", null);

		int contVendaC = cVendaC.getCount();
		lstVendaC = new String[contVendaC];
		
		String data;
		while (cVendaC.moveToNext()) {
			//String data = fc.formatarDataTela(cVendaC.getString(cVendaC.getColumnIndex("dataems")));
			lstVendaC[posicao] = ""	+ cVendaC.getString(cVendaC.getColumnIndex("cod")) 
					+ " - "	+ cVendaC.getString(cVendaC.getColumnIndex("fantasia"))	
					+ " - "	+ cVendaC.getString(cVendaC.getColumnIndex("dataems"))
					//+ " - "	+ data
					//+ " - "	+ fc.formatarDataTela(cVendaC.getString(cVendaC.getColumnIndex("dataems")))
					+ " - " + cVendaC.getString(cVendaC.getColumnIndex("tot"));
			posicao++;
		}

		lvVendaC.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstVendaC));
		CarregarEncontrados();		
	}
	
	public void CarregarEncontrados() {
		int textlength = etProcurar.getText().length();

		// Limpa o array com os estados encontrados
		// para poder efetuar nova busca
		lstVendaC_encontradas.clear();

		for (int i = 0; i < lstVendaC.length; i++) {
			if (textlength <= lstVendaC[i].length()) {
				// Verifica se existe algum item no array original
				// caso encontre é adicionado no array de encontrados
				if (etProcurar
						.getText()
						.toString()
						.equalsIgnoreCase(
								(String) lstVendaC[i]
										.subSequence(0, textlength))) {
					lstVendaC_encontradas.add(lstVendaC[i]);
				}
			}
		}
	}

	protected void listarProdutosVenda(String cod) {
		// lstVendaD = null;
		// cVendaD = null;
		posicaoD = 0;
		/*
		 * ProdVendDAO dao = new ProdVendDAO(getBaseContext()); listaVendaD =
		 * dao.getProdutosVenda(cod); lvVendaD.setAdapter(new
		 * ProdVendAdapter(getBaseContext(), listaVendaD));
		 */

		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		cVendaD = db.rawQuery(
				"SELECT COD, PROD, Q1, VL_U, VL_T from PROD_VEND where CODVEND="
						+ cod, null);
		// Log.d("sql",
		// "SELECT COD, PROD, Q1, VL_U, VL_T from PROD_VEND where CODVEND=" +
		// cod);

		int contVendaD = cVendaD.getCount();
		lstVendaD = new String[contVendaD];
		while (cVendaD.moveToNext()) {
			lstVendaD[posicaoD] = ""
					+ cVendaD.getString(cVendaD.getColumnIndex("cod")) + " - "
					+ cVendaD.getString(cVendaD.getColumnIndex("prod")) + " - "
					+ cVendaD.getString(cVendaD.getColumnIndex("vl_u")) + " - "
					+ cVendaD.getString(cVendaD.getColumnIndex("q1")) + " - "
					+ cVendaD.getString(cVendaD.getColumnIndex("vl_t"));
			posicaoD++;
		}

		lvVendaD.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lstVendaD));
		CarregarEncontradosD();
	}

	public void CarregarEncontradosD() {
		int textlength = etProcurar.getText().length();

		// Limpa o array com os estados encontrados
		// para poder efetuar nova busca
		lstVendaD_encontradas.clear();

		for (int i = 0; i < lstVendaD.length; i++) {
			if (textlength <= lstVendaD[i].length()) {
				// Verifica se existe algum item no array original
				// caso encontre é adicionado no array de encontrados
				if (etProcurar
						.getText()
						.toString()
						.equalsIgnoreCase(
								(String) lstVendaD[i]
										.subSequence(0, textlength))) {
					lstVendaD_encontradas.add(lstVendaD[i]);
				}
			}
		}
	}

	private void Mensagem(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void apagarVendaC(String cod, String cliente) {
		final String cod1 = cod;		
		
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getById(Integer.parseInt(cod1));
		
		dao.delete(vo);

		apagarVendaD(cod1);
		
		BuscarVendasC();
		
		Mensagem("Venda Apagada : " + cliente);

/*		
		final String cliente = vo.getRazao();
		
		Builder msg = new Builder(ListarVendas.this);
		msg.setMessage("Deseja APAGAR esta venda: " + cliente + "?");
		msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dao.delete(vo);

				apagarVendaD(cod1);
				
				BuscarVendasC();
				
				Mensagem("Venda Apagada : " + cliente);
			}
		});
		
		msg.setNegativeButton("NÃO", null);
		msg.show();
*/		
	}

	private void apagarVendaD(String cod) {
		final String cod1 = cod;		
		final ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		//final ProdVendVO vo = dao.getByCodVend(Integer.parseInt(cod1));

		//final String produto = vo.getProd();
		dao.deleteCodVend(cod);
		
/*		
        Builder msg = new Builder(ListarVendas.this);
		msg.setMessage("Deseja APAGAR este produto: " + produto + "?");
		msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dao.delete(vo);

				listarProdutosVenda(idVendaC);
				
				buscarTotalProdutos();
				
				Mensagem("Produto Apagado : " + produto);
			}
		});
		msg.setNegativeButton("NÃO", null);
		msg.show();
*/		
	}	
	
	private void apagarProduto(String cod) {
		final String cod1 = cod;		
		final ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		final ProdVendVO vo = dao.getById(Integer.parseInt(cod1));

		final String produto = vo.getProd();
		
		Builder msg = new Builder(ListarVendas.this);
		msg.setMessage("Deseja APAGAR este produto: " + produto + "?");
		msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dao.delete(vo);

				listarProdutosVenda(idVendaC);
				
				buscarTotalProdutos();
				
				Mensagem("Produto Apagado : " + produto);
			}
		});
		msg.setNegativeButton("NÃO", null);
		msg.show();
	}
	
	protected void buscarTotalProdutos(){
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		//pvdao.getSomaProdutos(txtID.getText().toString());
		
		String total = pvdao.getSomaProdutos(idVendaC);
		
		atualizarVendaC(total);
	}
	
	protected void atualizarVendaC(String total) {
		RecDAO dao = new RecDAO(getBaseContext());
		RecVO vo = dao.getById(Integer.parseInt(idVendaC));
		vo.setTot(total);
		
		if (dao.update(vo)) {
			BuscarVendasC();
		}
	}	

}
