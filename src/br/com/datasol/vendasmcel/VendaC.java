package br.com.datasol.vendasmcel;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasmcel.R;
import br.com.datasol.vendasmcel.adapters.ProdVendAdapter;
import br.com.datasol.vendasmcel.auxilio.FormatarCampos;
import br.com.datasol.vendasmcel.dao.Cad_cliDAO;
import br.com.datasol.vendasmcel.dao.ConfigDAO;
import br.com.datasol.vendasmcel.dao.EstoqueDAO;
import br.com.datasol.vendasmcel.dao.ProdVendDAO;
import br.com.datasol.vendasmcel.dao.RecDAO;
import br.com.datasol.vendasmcel.dao.VendedorDAO;
import br.com.datasol.vendasmcel.vo.Cad_cliVO;
import br.com.datasol.vendasmcel.vo.ConfigVO;
import br.com.datasol.vendasmcel.vo.EstoqueVO;
import br.com.datasol.vendasmcel.vo.ProdVendVO;
import br.com.datasol.vendasmcel.vo.RecVO;
import br.com.datasol.vendasmcel.vo.VendedorVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VendaC extends Activity {
	
	FormatarCampos fc = new FormatarCampos();
	
	private ListView lvCliente;
    private String[] lstCliente;
    private ArrayList<String> lstCliente_Encontrados = new ArrayList<String>();
	private SQLiteDatabase db;
	private Cursor rec = null;
	private int posicao=0;
	
	private double vlTotal=0;
	
	private String codigo;
	private String fantasia;
	private String razao;
	private String cnpj;
	private String TotalGeral;
	
	EditText txtID;
	EditText txtUsuario;
	EditText txtRazao;
	EditText txtCnpj;
	EditText txtCidade;
	EditText txtCondPg;

	EditText txtTotalGeral;
	EditText txtData;
	EditText etProcurar;

	Button btNovo;
	Button btSalvarVendaC;
	Button btAtualizarVendaC;
	Button btApagar;
	Button btBuscarCliente;
	Button btVoltarCad;

	private String codCliente;
	String idVendaC = "";
	private static int RETORNO_NOME = 1;
	private static int RETORNO_COR = 1;
	private static int buscarCliente = 0;
	private String codUnid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.vendac);
		
		lvCliente = (ListView)findViewById(R.id.ltvDados);
		lvCliente.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		txtID = (EditText) findViewById(R.id.txtCod);
		txtData = (EditText) findViewById(R.id.txtData);
		txtData.setText(fc.dataAtualTela());
		txtTotalGeral = (EditText) findViewById(R.id.txtTotalVendaC);
		txtTotalGeral.setText("0.00");		
		txtUsuario = (EditText) findViewById(R.id.txtFantasia);
		txtRazao = (EditText) findViewById(R.id.txtRazao);
		txtCnpj = (EditText) findViewById(R.id.txtCnpj);
		txtCidade = (EditText) findViewById(R.id.txtCidade);
		txtCondPg = (EditText) findViewById(R.id.txtCondPgto);
		etProcurar = (EditText) findViewById(R.id.etProcurar);
					
		btNovo = (Button) findViewById(R.id.btNovoVendaC);
		btSalvarVendaC = (Button) findViewById(R.id.btSalvar);
		btAtualizarVendaC = (Button) findViewById(R.id.btAlterar);
		btApagar = (Button) findViewById(R.id.btApagar);
		//btBuscarCliente = (Button) findViewById(R.id.btBuscarcliente);
		btVoltarCad = (Button) findViewById(R.id.btVoltarCad);
        db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		rec = db.rawQuery("SELECT USUARIO, RAZAO, CIDADE, CNPJ, CPF FROM cad_cli order by usuario", null);

		int contProdEstoq= rec.getCount();
		lstCliente = new String[contProdEstoq];
		
		ConfigDAO configDAO = new ConfigDAO(getBaseContext());
		ConfigVO configVO = configDAO.getById(1);
		
		String filtroCliente = configVO.getFiltrocliente().toString();
		
		if (filtroCliente.equals("Fantasia")){
			while(rec.moveToNext()){
				
				lstCliente[posicao] = "" + rec.getString(rec.getColumnIndex("usuario")) // campo fantasia 
		                                 + " - " + rec.getString(rec.getColumnIndex("razao"))					
				                         + " = " + rec.getString(rec.getColumnIndex("cidade"));
				posicao++;
			}			
		} else if (filtroCliente.equals("Raz�o Social")){
				while(rec.moveToNext()){
					
					lstCliente[posicao] = "" + rec.getString(rec.getColumnIndex("razao")) // campo razao 
			                                 + " = " + rec.getString(rec.getColumnIndex("usuario"))					
					                         + " - " + rec.getString(rec.getColumnIndex("cidade"));
					posicao++;
				}			
			}			

		lvCliente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstCliente));

        CarregarEncontrados();  

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
      
            public void onTextChanged(CharSequence s, int start, int before, int count)  
            {  
                CarregarEncontrados();  
       
                lvCliente.setAdapter(new ArrayAdapter<String>(VendaC.this, android.R.layout.simple_list_item_1, lstCliente_Encontrados));  
            }  
        });          
		
        lvCliente.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView arg0, View view, int position, long index) {  
                Mensagem("Cliente selecionado : " + lstCliente_Encontrados.get(position).toString());
                String cliente = lstCliente_Encontrados.get(position).toString();
                
        		ConfigDAO configDAO = new ConfigDAO(getBaseContext());
        		ConfigVO configVO = configDAO.getById(1);
        		
        		String filtroCliente = configVO.getFiltrocliente().toString();
                String fantasia = "";                
                String razao = "";        		

        		if (filtroCliente.equals("Fantasia")){
        			int u = cliente.indexOf("-");
                    //String fantasia = cliente.substring(0, u);                
        			fantasia = cliente.substring(0, u);
                    u = u + 2;
                    int c = cliente.indexOf("=");
                    //String razao = cliente.substring(u, c-1);        			
                    razao = cliente.substring(u, c-1);
        		} else if (filtroCliente.equals("Raz�o Social")){
        			int u = cliente.indexOf("=");
                    //String fantasia = cliente.substring(0, u-1);                
                    //String razao = fantasia;
                    fantasia = cliente.substring(0, u-1);                
                    razao = fantasia;        			
        		}			
                
                final Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
    			//final Cad_cliVO vo = dao.getByCnpj(cnpj);
                final Cad_cliVO vo = dao.getByRazao(razao);
    			
    			txtID.setText(String.valueOf(vo.getCod()));
    			codCliente = vo.getCodcli();
    			txtUsuario.setText(vo.getUsuario());
    			txtRazao.setText(vo.getRazao());
    			txtCnpj.setText(vo.getCnpj());
    			txtCidade.setText(vo.getCidade());
            }  
        });  
		
		
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

		btSalvarVendaC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarVendaC();
			}
		});
		
		btAtualizarVendaC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizarVendaC();
			}
		});		

		btApagar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				buscarUltimaVenda();
			}
		});

		buscarUltimaVenda1();
	}
/*
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		if (RETORNO_COR == requestCode) {
			if (resultCode == RESULT_OK)
				txtUsuario.setText(data.getStringExtra("nome"));
		}
	}
*/
	protected void salvarVendaC() {
		RecVO vo = new RecVO();
		
		//pegando o vendedor		
		VendedorDAO vDAO = new VendedorDAO(getBaseContext());
		VendedorVO vVO= vDAO.getById(1);

		vo.setCodcli(codCliente);
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		if (txtTotalGeral.getText().toString().equals("")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());
		vo.setCondpg(txtCondPg.getText().toString());
		vo.setVendedor(vVO.getNome());
		vo.setCidade(txtCidade.getText().toString());
		vo.setSincronizado("N");

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.insert(vo)) {
			buscarUltimaVenda1();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void atualizarVendaC() {
		RecVO vo = new RecVO();
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		
		//pegando o vendedor		
    	VendedorDAO vDAO = new VendedorDAO(getBaseContext());
		VendedorVO vVO= vDAO.getById(1);
		
		vo.setCod(Integer.parseInt(txtID.getText().toString()));
		vo.setCodcli(codCliente);		
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));	
		if (txtTotalGeral.getText().toString().equals("") || txtTotalGeral.getText().toString().equals("0.00")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());
		vo.setCidade(txtCidade.getText().toString());
		vo.setCondpg(txtCondPg.getText().toString());
		vo.setVendedor(vVO.getNome());
		vo.setSincronizado("N");

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.update(vo)) {
			buscarUltimaVenda();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void buscarUltimaVenda() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		
		idVendaC = vo.getCod().toString();
		txtID.setText(vo.getCod().toString());
		codCliente = vo.getCodcli();
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		txtCondPg.setText(vo.getCondpg());
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));
		txtCidade.setText(vo.getCidade());
	}
	
	protected void buscarUltimaVenda1() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();

		txtID.setText(vo.getCod().toString());
		codCliente = vo.getCodcli().toString();
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		txtCondPg.setText(vo.getCondpg());
		txtTotalGeral.setText(vo.getTot());
		txtCidade.setText(vo.getCidade());
		
		btSalvarVendaC.setEnabled(false);
		btAtualizarVendaC.setEnabled(true);
	}
	
	protected void limparTela(){
		txtID.setText("");
		codCliente = "";
		txtUsuario.setText("");
		txtRazao.setText("");
		txtCnpj.setText("");
		txtTotalGeral.setText("");
		txtCondPg.setText("");
		txtCidade.setText("");
		
		btSalvarVendaC.setEnabled(true);
		btAtualizarVendaC.setEnabled(false);
	}
	
    public void CarregarEncontrados()  
    {  
        int textlength = etProcurar.getText().length();  
  
        //Limpa o array com os estados encontrados  
        //para poder efetuar nova busca  
        lstCliente_Encontrados.clear();  
     
        for (int i = 0; i < lstCliente.length; i++)  
        {  
            if (textlength <= lstCliente[i].length())  
            {  
                //Verifica se existe algum item no array original  
                //caso encontre � adicionado no array de encontrados  
                if(etProcurar.getText().toString().equalsIgnoreCase((String)lstCliente[i].subSequence(0, textlength)))  
                {  
                    lstCliente_Encontrados.add(lstCliente[i]);  
                }  
            }  
        }  
    }  
    
    private void Mensagem(String msg)   
    {  
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();  
    }	
    
    public void BuscarVendaC(String id){
    	//Log.d("VendaC", "1 " + id);
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getById(Integer.parseInt(id));
		
		//Log.d("VendaC", "2 " + id);
		txtID.setText(vo.getCod().toString());
		codCliente = vo.getCodcli();
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		txtCondPg.setText(vo.getCondpg());
		txtTotalGeral.setText(vo.getTot());
		txtCidade.setText(vo.getCidade());
		//Log.d("VendaC", "3 " + vo.getFantasia());
		btSalvarVendaC.setEnabled(false);
		btAtualizarVendaC.setEnabled(true);
		//Log.d("VendaC", "4 " + id + " - " + vo.getFantasia());
    }
}