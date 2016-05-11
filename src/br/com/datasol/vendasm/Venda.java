package br.com.datasol.vendasm;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.adapters.ProdVendAdapter;
import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.dao.Cad_cliDAO;
import br.com.datasol.vendasm.dao.EstoqueDAO;
import br.com.datasol.vendasm.dao.ProdVendDAO;
import br.com.datasol.vendasm.dao.RecDAO;
import br.com.datasol.vendasm.vo.Cad_cliVO;
import br.com.datasol.vendasm.vo.EstoqueVO;
import br.com.datasol.vendasm.vo.ProdVendVO;
import br.com.datasol.vendasm.vo.RecVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Venda extends Activity {
	
	FormatarCampos fc = new FormatarCampos();
	
	ListView ltwProdutosVenda;
	List<ProdVendVO> listaProdutosVenda = null;
	private double vlTotal=0;
	private String[] lstEstoque;
	private ArrayList<String> lstEstoque_Encontrados = new ArrayList<String>();
	private SQLiteDatabase db;
	private Cursor rec = null;
	private Cursor prodvend = null;
	private int posicao=0;
	
	private String codigo;
	private String fantasia;
	private String razao;
	private String cnpj;
	private String TotalGeral;
	private int contProdutos=0;
	
	EditText txtID;
	EditText txtUsuario;
	EditText txtRazao;
	EditText txtCnpj;

	EditText txtProduto;
	EditText txtQt;
	EditText txtVu;
	EditText txtVt;

	EditText txtTotalGeral;
	EditText txtData;

	Button btNovo;
	Button btSalvarVendaC;
	Button btAtualizarVendaC;
	Button btApagar;
	Button btBuscarCliente;
	Button btBuscarProduto;
	Button btInserirProduto;
	Button btVoltarCad;
	ImageButton imbBuscarProduto;
	ImageButton imbAdicionarProduto;

	private int codCliente = 0;
	private int codProduto = 0;
	String idVendaC = "";
	private static int RETORNO_NOME = 1;
	private static int RETORNO_COR = 1;
	private static int RETORNO_PRODUTO = 1;
	private static int buscarCliente = 0;
	private static int buscarProduto = 0;
	private String codProdutoStr;
	private String codUnid;
//	private double VU;
//	private double VT;
//	private double QTDouble;

	//@SuppressLint("NewApi")
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("venda 1", " - ");
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);
		
		//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.venda);
		
		ltwProdutosVenda = (ListView)findViewById(R.id.listProdutos);
		ltwProdutosVenda.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		Log.d("venda 2", " - ");
		if (buscarCliente > 0) {
			Log.d("venda it", " - ");
			Intent it = getIntent();
			codCliente = it.getIntExtra("codigo", 1);
			Log.d("venda it", String.valueOf(codCliente));
			final Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
			final Cad_cliVO vo = dao.getById(codCliente);

			txtID = (EditText) findViewById(R.id.txtCod);
			txtUsuario = (EditText) findViewById(R.id.txtFantasia);
			txtRazao = (EditText) findViewById(R.id.txtRazao);
			txtCnpj = (EditText) findViewById(R.id.txtCnpj);

			txtID.setText(vo.getCod().toString());
			txtUsuario.setText(vo.getUsuario());
			txtRazao.setText(vo.getRazao());
			txtCnpj.setText(vo.getCnpj());
			
			Log.d("venda it", "nome " + vo.getUsuario());
		}
		Log.d("venda 3", " - ");
		
		if (buscarProduto > 0) {
			Log.d("venda itProd", "-");
			Intent itProd = getIntent();
			codProduto = itProd.getIntExtra("codProd", 1);

			Log.d("produto dao", String.valueOf(codProduto));
			final EstoqueDAO dao = new EstoqueDAO(getBaseContext());
			final EstoqueVO vo = dao.getById(codProduto);	
			Log.d("produto dao", String.valueOf(codProduto) + " produto vo " + vo.getProd());

			txtProduto = (EditText) findViewById(R.id.txtProduto);
			txtQt = (EditText) findViewById(R.id.txtQt);
			txtVu = (EditText) findViewById(R.id.txtVU);
			
			txtProduto.setText(vo.getProd());
			txtVu.setText(vo.getVt());

			//buscarUltimaVenda1();
			Log.d("venda itProd", "-");
			
/*			
			// txtID = (EditText) findViewById(R.id.txtCod);
//			txtProduto = (EditText) findViewById(R.id.txtProduto);
//			txtQt = (EditText) findViewById(R.id.txtQt);
//			txtVu = (EditText) findViewById(R.id.txtVU);
			//txtVt = (EditText) findViewById(R.id.txtVT);

			// txtID.setText(vo.getCod().toString());
//			txtProduto.setText(vo.getProd());
//			codProdutoStr = vo.getCodprod();
//			codUnid = vo.getUnid();
//			txtVu.setText(vo.getVt());
			//VU = Double.parseDouble(vo.getVt());
			buscarUltimaVenda1();
			//txtID.setText(codigo);
			//txtUsuario.setText(fantasia);
			//txtRazao.setText(razao);
			//txtCnpj.setText(cnpj);
			//txtTotalGeral.setText(TotalGeral);
*/			
		}
		Log.d("venda 4", " - ");
		txtTotalGeral = (EditText) findViewById(R.id.txtTotalVendaC);
		txtTotalGeral.setText("0.00");
		txtData = (EditText) findViewById(R.id.txtData);
		txtData.setText(fc.dataAtualTela());

		btNovo = (Button) findViewById(R.id.btNovoVendaC);
		btSalvarVendaC = (Button) findViewById(R.id.btSalvar);
		//btAtualizarVendaC = (Button) findViewById(R.id.btNovoVendaC);
		btApagar = (Button) findViewById(R.id.btApagar);
		btBuscarCliente = (Button) findViewById(R.id.btBuscarcliente);
		btVoltarCad = (Button) findViewById(R.id.btVoltarCad);
		//btBuscarProduto = (Button) findViewById(R.id.btBuscarProduto);
		//btInserirProduto = (Button) findViewById(R.id.btInserir);
		imbBuscarProduto = (ImageButton) findViewById(R.id.imbBuscarProduto1);
		imbAdicionarProduto = (ImageButton) findViewById(R.id.imbAdicionarProduto1);
		Log.d("venda 5", " - ");
		
		btVoltarCad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		btNovo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				limparTela();
			}
		});
		
		btBuscarCliente.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivityForResult(new Intent(getBaseContext(),
				// ListarCores.class), RETORNO_COR);
				// startActivity(new Intent(getBaseContext(),
				// Cad_cliListar.class));
				
				//finish();
				
				startActivityForResult(new Intent(getBaseContext(),
						ListarClientes.class), RETORNO_COR);
				
				buscarCliente = buscarCliente + 1; 
			}
		});
		
		btSalvarVendaC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarVendaC();
			}
		});
/*		
		btAtualizarVendaC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//atualizarVendaC();
			}
		});
*/		

		btApagar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buscarUltimaVenda();
			}
		});

/*
		btBuscarProduto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getBaseContext(),
						ListarEstoque.class), RETORNO_PRODUTO);

				buscarProduto = buscarProduto + 1;
			}
		});
		
		btInserirProduto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarVendaD();
			}
		});		
*/		
		imbBuscarProduto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//finish();
				
				startActivityForResult(new Intent(getBaseContext(),
						ListarEstoque.class), RETORNO_PRODUTO);

				buscarProduto = buscarProduto + 1;
			}
		});
		
		imbAdicionarProduto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarVendaD();
			}
		});		
		

	}
/*
	protected void buscarCliente(){
			Intent it = getIntent();
			codCliente = it.getIntExtra("codigo", 1);

			final Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
			final Cad_cliVO vo = dao.getById(codCliente);

			txtID.setText(vo.getCod().toString());
			txtUsuario.setText(vo.getUsuario());
			txtRazao.setText(vo.getRazao());
			txtCnpj.setText(vo.getCnpj());
	}
*/	
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		if (RETORNO_COR == requestCode) {
			if (resultCode == RESULT_OK)
				txtUsuario.setText(data.getStringExtra("nome"));
		}

		if (RETORNO_PRODUTO == requestCode) {
			if (resultCode == RESULT_OK)
				txtProduto.setText(data.getStringExtra("nome"));
		}
	}


	protected void salvarVendaC() {
		RecVO vo = new RecVO();
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		if (txtTotalGeral.getText().toString().equals("")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}

		// vo.setCondpg(txtEnde_num.getText().toString());
		//vo.setDataems(txtData.getText().toString());
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());
		// vo.setVendedor(txtvCpf.getText().toString());

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.insert(vo)) {
			buscarUltimaVenda1();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void atualizarVendaC() {
		RecVO vo = new RecVO();
		vo.setCod(Integer.parseInt(txtID.getText().toString()));
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));
		
		Log.d("at VC", pvdao.getSomaProdutos(txtID.getText().toString()));
		
		if (txtTotalGeral.getText().toString().equals("") || txtTotalGeral.getText().toString().equals("0.00")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}

		// vo.setCondpg(txtEnde_num.getText().toString());
		//vo.setDataems(txtData.getText().toString());
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());
		// vo.setVendedor(txtvCpf.getText().toString());

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.update(vo)) {
			buscarUltimaVenda();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}	
	
	protected void salvarVendaD() {

		double Qt = Double.parseDouble(txtQt.getText().toString());
		double Vu = Double.parseDouble(txtVu.getText().toString());
		double VtAnt;
    	double Vt;
/*    	
		if (txtTotalGeral.getText().toString().equals("")){
			VtAnt = 0;
		}else {
			VtAnt = Double.parseDouble(txtTotalGeral.getText().toString());	
		}
*/	    		
    	
		Vt = Vu * Qt;
		Log.d("salvarD","1 " + Vt);
//		vlTotal = VtAnt + Vt;
		vlTotal = vlTotal + Vt;
		Log.d("salvarD","2 " + vlTotal);
		txtTotalGeral.setText(String.valueOf(vlTotal));
		
		ProdVendVO vo = new ProdVendVO();
		
		vo.setCodvend(txtID.getText().toString());
		//vo.setCodvend(idVendaC);
		vo.setProd(txtProduto.getText().toString());
		vo.setVl_u(txtVu.getText().toString());
		vo.setQ1(txtQt.getText().toString());
//		if (txtVt.getText().toString() == "") {
//			vo.setVl_t("0.00");
//		} else {
			//vo.setVl_t(txtVt.getText().toString());
			vo.setVl_t(String.valueOf(vlTotal));
//		}

		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		if (dao.insert(vo)) {
			atualizarVendaC();
			listarProdutosVenda();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void listarProdutosVenda(){
		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		listaProdutosVenda = dao.getProdutosVenda(txtID.getText().toString());
		ltwProdutosVenda.setAdapter(new ProdVendAdapter(getBaseContext(), listaProdutosVenda));
	}
	
	protected void buscarUltimaVenda() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		idVendaC = vo.getCod().toString();
		txtID.setText(vo.getCod().toString());
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		//txtTotalGeral.setText(vo.getTot());
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		//pvdao.getSomaProdutos(txtID.getText().toString());
		
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));
		
		//codigo = vo.getCod().toString();
		//fantasia = vo.getFantasia();
		//razao = vo.getRazao();
		//cnpj = vo.getCnpj();			
		//TotalGeral = vo.getTot();
		
	}
	
	protected void buscarUltimaVenda1() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		txtID.setText(vo.getCod().toString());
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		txtTotalGeral.setText(vo.getTot());
		
		//codigo = vo.getCod().toString();
		//fantasia = vo.getFantasia();
		//razao = vo.getRazao();
		//cnpj = vo.getCnpj();			
		//TotalGeral = vo.getTot();
	}
	
	protected void limparTela(){

		txtID.setText("");
		txtUsuario.setText("");
		txtRazao.setText("");
		txtCnpj.setText("");
		txtProduto.setText("");
		txtQt.setText("");
		txtVu.setText("");
		txtProduto.setText("");
		txtVu.setText("");
		txtTotalGeral.setText("");
		
		listaProdutosVenda.clear();
	}

}