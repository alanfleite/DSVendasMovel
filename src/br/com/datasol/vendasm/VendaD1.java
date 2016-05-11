package br.com.datasol.vendasm;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.adapters.EstoqueAdapter;
import br.com.datasol.vendasm.adapters.ProdVendAdapter;
import br.com.datasol.vendasm.auxilio.FormatarCampos;
import br.com.datasol.vendasm.dao.Cad_cliDAO;
import br.com.datasol.vendasm.dao.EstoqueDAO;
import br.com.datasol.vendasm.dao.ProdVendDAO;
import br.com.datasol.vendasm.dao.RecDAO;
import br.com.datasol.vendasm.dao.VendedorDAO;
import br.com.datasol.vendasm.vo.Cad_cliVO;
import br.com.datasol.vendasm.vo.EstoqueVO;
import br.com.datasol.vendasm.vo.ProdVendVO;
import br.com.datasol.vendasm.vo.RecVO;
import br.com.datasol.vendasm.vo.VendedorVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VendaD1 extends Activity {
	
	FormatarCampos fc = new FormatarCampos();
	
	ListView ltwProdutosVenda;
	List<ProdVendVO> listaProdutosVenda = null;
	private double vlTotal=0;
	
	ListView ltwEstoq;
	List<EstoqueVO> listaEstoq = null;
	private String[] lstEstoque;	
	private ArrayList<String> lstEstoque_Encontrado = new ArrayList<String>();
	int idItem = 0;
	private static int MENU_EDITAR = 1;
	private String nome;
	
	private String codigo;
	private String TotalGeral;
	private int contProdutos=0;
	String idVendaC;

	EditText etProcurar;
	EditText txtProduto;
	EditText txtQt;
	EditText txtVu;
	EditText txtVt;
	
	TextView txvVlTotalG;

	Button btBuscarProduto;
	Button btInserirProduto;
	Button btFechar;
	ImageButton imbBuscarProduto;
	ImageButton imbAdicionarProduto;

	private int codProduto = 0;
	private static int RETORNO_PRODUTO = 1;
	private static int buscarProduto = 0;
	private String codProdutoStr;
	private String codUnid;

	protected void onCreate(Bundle savedInstanceState) {
		Log.d("SalvarD", "1");
		
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.vendad);
		Log.d("SalvarD", "2");
		
		ltwProdutosVenda = (ListView)findViewById(R.id.listProdutos);
		ltwProdutosVenda.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		ltwEstoq = (ListView)findViewById(R.id.ltvDados);
		ltwEstoq.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		Log.d("SalvarD", "3");
		
		etProcurar = (EditText) findViewById(R.id.etProcurar);
		Log.d("SalvarD", "4");
		EstoqueDAO dao = new EstoqueDAO(getBaseContext());
		listaEstoq = dao.getAll();
		ltwEstoq.setAdapter(new EstoqueAdapter(getBaseContext(), listaEstoq));
		Log.d("SalvarD", "5");
//		ltwEstoq.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstEstoque));
		//ltwEstoq.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEstoq));
		Log.d("SalvarD", "6");
		CarregarEncontrados();
		Log.d("SalvarD", "7");
		txvVlTotalG = (TextView)findViewById(R.id.txvVlTotal);

		//registerForContextMenu(ltwEstoq);
		Log.d("SalvarD", "8");
        etProcurar.addTextChangedListener(new TextWatcher()  
        {  
            public void afterTextChanged(Editable s)  
            {  
                // Abstract Method of TextWatcher Interface.  
            }  
      
            public void beforeTextChanged(CharSequence s, int start, int count, int after)  
            {  
                // Abstract Method of TextWatcher Interface.  
            }  
      
            //Evento acionado quando o usuário teclar algo  
            //na caixa de texto "Procurar"  
            public void onTextChanged(CharSequence s, int start, int before, int count)  
            {  
                CarregarEncontrados();  
       
                //Carrega o listview com os itens encontrados  
                ltwEstoq.setAdapter(new ArrayAdapter<String>(VendaD1.this, android.R.layout.simple_list_item_1, lstEstoque_Encontrado));  
            }  
        });  
        Log.d("SalvarD", "9");
        ltwEstoq.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView arg0, View view, int position, long index) {  
                Mensagem("Você clicou no estado : " + lstEstoque_Encontrado.get(position).toString());  
            }  
        });	
		
		buscarUltimaVenda();

		btFechar = (Button) findViewById(R.id.btVoltarCad);
		imbBuscarProduto = (ImageButton) findViewById(R.id.imbBuscarProduto1);
		imbAdicionarProduto = (ImageButton) findViewById(R.id.imbAdicionarProduto1);

		btFechar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		imbBuscarProduto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				startActivityForResult(new Intent(getBaseContext(), ListarEstoque.class), RETORNO_PRODUTO);

//				buscarProduto = buscarProduto + 1;
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
	@Override
	public void onResume(){
		super.onResume();
		EstoqueDAO dao = new EstoqueDAO(getBaseContext());
		listaEstoq = dao.getAll();
		ltwEstoq.setAdapter(new EstoqueAdapter(getBaseContext(), listaEstoq));
	}	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		
		menu.setHeaderTitle(listaEstoq.get(info.position).getCodprod() + " - " + listaEstoq.get(info.position).getProd() + " - " + listaEstoq.get(info.position).getVt());
//		Log.d("listaVendaC",listaEstoq.get(info.position).getCodprod() + " - " + listaEstoq.get(info.position).getProd() + " - " + listaEstoq.get(info.position).getVt());
		//menu.setHeaderTitle(listaEstoq.get(info.position).getProd());
		//menu.setHeaderTitle(listaEstoq.get(info.position).getVt());
		menu.add(Menu.NONE, MENU_EDITAR, 0, "Carregar");
//		menu.add(Menu.NONE, MENU_APAGAR, 0, "Apagar");
//		menu.add(Menu.NONE, MENU_CALL, 0, "Telefonar");
	}
*/	
/*	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		idItem = listaEstoq.get(info.position).getCod();
		nome = listaEstoq.get(info.position).getCodprod() + " - " +  listaEstoq.get(info.position).getProd() + " - " + listaEstoq.get(info.position).getVt();
		//nome = listaEstoq.get(info.position).getProd();
		
		if(item.getItemId() == MENU_EDITAR){
			//finish();
			Log.d("listar estoque", String.valueOf(idItem));
			
			final EstoqueDAO dao = new EstoqueDAO(getBaseContext());
			final EstoqueVO vo = dao.getById(idItem);

			txtProduto = (EditText) findViewById(R.id.txtProduto);
			txtQt = (EditText) findViewById(R.id.txtQt);
			txtVu = (EditText) findViewById(R.id.txtVU);

			txtProduto.setText(vo.getProd());
			txtVu.setText(vo.getVt());
			txtQt.setText("");
			//txtQt.setFocusableInTouchMode(true);
			
			//Intent it = new Intent(getBaseContext(), Venda.class);
			//it.putExtra("codProd", idItem);
			//startActivity(it);
//			setResult(RESULT_OK, new Intent().putExtra("nome", nome));
			Log.d("listar estoque 1", String.valueOf(idItem));
		} 
		return super.onContextItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
	
		if (RETORNO_PRODUTO == requestCode) {
			if (resultCode == RESULT_OK)
				txtProduto.setText(data.getStringExtra("nome"));
		}
	}
*/	
	protected void salvarVendaD() {
		double Qt = Double.parseDouble(txtQt.getText().toString());
		double Vu = Double.parseDouble(txtVu.getText().toString());
		double VtAnt;
    	double Vt;
    	
		Vt = Vu * Qt;
		Log.d("salvarD","1 " + Vt);
		vlTotal = vlTotal + Vt;
		Log.d("salvarD","2 " + vlTotal);
		//txtTotalGeral.setText(String.valueOf(vlTotal));
		
		ProdVendVO vo = new ProdVendVO();
		
		vo.setCodvend(idVendaC);
		vo.setProd(txtProduto.getText().toString());
		vo.setVl_u(txtVu.getText().toString());
		vo.setQ1(txtQt.getText().toString());
		vo.setVl_t(String.valueOf(vlTotal));
		
		//pegando o vendedor		
		VendedorDAO vDAO = new VendedorDAO(getBaseContext());
		VendedorVO vVO= vDAO.getById(1);
				
		vo.setVendedor(vVO.getNome());

		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		if (dao.insert(vo)) {
			listarProdutosVenda();

            buscarTotalProdutos();
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void listarProdutosVenda(){
		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		listaProdutosVenda = dao.getProdutosVenda(idVendaC);
		ltwProdutosVenda.setAdapter(new ProdVendAdapter(getBaseContext(), listaProdutosVenda));
	}
	
	protected void buscarUltimaVenda() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		idVendaC = vo.getCod().toString();
		txvVlTotalG.setText(vo.getTot());
	}
	
	protected void buscarTotalProdutos(){
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		//pvdao.getSomaProdutos(txtID.getText().toString());
		
		String total = pvdao.getSomaProdutos(idVendaC);
		
		txvVlTotalG.setText("Vl. Total: " + total);
		
		atualizarVendaC(total);
	}
	
	protected void atualizarTotalVendaC(String total){
		RecVO vo = new RecVO();
		
		vo.setCod(Integer.parseInt(idVendaC));
		vo.setTot(total);

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.update(vo)) {
			//Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}		
	}
	
	protected void atualizarVendaC(String total) {
		RecDAO dao;
		RecVO vo;
		dao = new RecDAO(getBaseContext());
		vo = dao.getById(Integer.parseInt(idVendaC));
		String fantazia = vo.getFantasia();
		String razao = vo.getRazao();
		String cnpj = vo.getCnpj();
		String data = vo.getDataems();
		String vendedor = vo.getVendedor();
				
		vo = new RecVO();
		vo.setCod(Integer.parseInt(idVendaC));
		vo.setFantasia(fantazia);
		vo.setRazao(razao);
		vo.setCnpj(cnpj);
		vo.setDataems(data);
		vo.setVendedor(vendedor);
		vo.setTot(total);
		
		dao = new RecDAO(getBaseContext());
		if (dao.update(vo)) {
			//Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}	
	
	 public void CarregarEncontrados()  
	    {  
	        int textlength = etProcurar.getText().length();  
	  Log.d("carregar", "1");
	        //Limpa o array com os estados encontrados  
	        //para poder efetuar nova busca  
	        lstEstoque_Encontrado.clear();  
	        Log.d("carregar", "2" + lstEstoque.length);
	        for (int i = 0; i < lstEstoque.length; i++)  
	        {  
	            if (textlength <= lstEstoque[i].length())  
	            {  
	            	Log.d("carregar", "4");
	                //Verifica se existe algum item no array original  
	                //caso encontre é adicionado no array de encontrados  
	                if(etProcurar.getText().toString().equalsIgnoreCase((String)lstEstoque[i].subSequence(0, textlength)))  
	                {  
	                    lstEstoque_Encontrado.add(lstEstoque[i]);
	                    Log.d("carregar", "5");
	                }  
	            }  
	        }  
	    }  	
	
	    private void Mensagem(String msg)   
	    {  
	        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();  
	    }	 
}