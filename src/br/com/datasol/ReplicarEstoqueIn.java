package br.com.datasol;


import br.com.datasol.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.dao.ConfigDAO;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.dao.VendedorDAO;
import br.com.datasol.R;
import br.com.datasol.vo.ConfigVO;
import br.com.datasol.vo.EstoqueVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ReplicarEstoqueIn extends Activity{
	int posicao=0;
	String [] listaProdutos;
//	String [] listaQt = new String[] {"1","2","3","4", "5", "6", "7", "8", "9", "10"};
	EditText etQt;
	
	private Cursor c = null;
	private String produto;
	private String codproduto;
	private String unid;
	private String qt;
	private String valor;
	private SQLiteDatabase db;
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
			        
	        etQt = (EditText) findViewById(R.id.txtQt);
	        
	        EstoqueDAO dao = new EstoqueDAO(getBaseContext());
	        dao.deleteAll();
	        
	        VendedorDAO vdao = new VendedorDAO(getBaseContext());
	        String vendedor = vdao.getVendedor();
	        
	        ConfigDAO configDAO = new ConfigDAO(getBaseContext());
	        ConfigVO configVO = configDAO.getById(1);
	        String url = configVO.getUrl();	        
	        
	        setContentView(R.layout.listarreplicacaoestoque);
	        
//	        String url;
	        if (vendedor.equals("WASHINGTON")){
	        	url= url + "/ListarEstoque.jsp";
	        	//url="http://192.168.1.12:8080/AndroidWeb/ListarEstoque.jsp";
	        } else{
	        	url= url + "/ListarEstoque.jsp";
	        	//url="http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ListarEstoque.jsp";
				//String url="http://192.168.1.3:8080/AndroidWeb/ListarEstoque.jsp";	        	
	        }
	        	
	        //Log.d("Estoq ", "1");
	        //db.execSQL("DELETE FROM estoq");
	        //db.delete("estoq", null,null);
	        //Log.d("Estoq ", "2");
	        //String url="http://datasol1.no-ip.biz:8080/AndroidWeb/ListarEstoque.jsp";
			//String url="http://192.168.1.3:8080/AndroidWeb/ListarEstoque.jsp";
			
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
				
				db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
				
				
				listaProdutos = new String[contUsuarios];			
				
				char caracter_lido=resposta.charAt(0);
				String nome="";
				//String sql = "insert into estoq (prod) values (\"" + nome + "\");";
				int contador=0;
				
				for (int i=0; caracter_lido != '^'; i++){
					caracter_lido = resposta.charAt(i);

					if (caracter_lido != '#'){
						nome += caracter_lido;
//						valor += caracter_lido;
					}else {
						listaProdutos[posicao] = "" + nome;
						posicao++;

						int p = nome.indexOf("@"); 
	                    produto = nome.substring(0, p);
	                    int cp = nome.indexOf("$");
	                    p = p + 1;
	                    codproduto = nome.substring(p, cp);
	                    int u = nome.indexOf("|");
	                    cp = cp + 1;
	                    unid = nome.substring(cp, u);
	                    int q = nome.indexOf("{");
	                    u = u + 1;
	                    qt = nome.substring(u, q);
	                    int v = nome.indexOf("}");
	                    q = q + 1;
	                    valor = nome.substring(q, v);
	                    
	                    


	                    //Log.d("Estoq 3", String.valueOf(p) + " - " + String.valueOf(cp) + " - " + String.valueOf(u));
	                    //Log.d("Estoq", String.valueOf(p) + " - " + nome);
	                    
	                    //produto = nome.getChars(1, p, null, null);
	                    
/*
	                    produto = nome.substring(1, p);
						codproduto = nome.substring(p, cp);
						unid = nome.substring(cp, u);
						qt = nome.substring(u, q);
						valor = nome.substring(q, v);
*/						
						
						//Log.d("Estoq ", nome);
						
						EstoqueVO vo = new EstoqueVO();
						
						vo.setProd(produto.toString());
						vo.setCodprod(codproduto.toString());
						vo.setQ1(qt.toString());
						vo.setVt(valor.toString());
						vo.setUnid(unid.toString());
						
						//EstoqueDAO dao = new EstoqueDAO(getBaseContext());
						dao = new EstoqueDAO(getBaseContext());
						dao.insert(vo);
						
						//Log.d("Estoq 4", "Prod " + produto + " - Cod Prod " + codproduto + " - Unid " + unid + " - Qt " + qt + " - Vl " + valor + " Contador " + String.valueOf(contador));
						//Log.d("Estoq ", "Prod " + produto + " Qt " + qt + " Vl " + valor + " Contador " + String.valueOf(contador));
						//c = db.rawQuery("SELECT * FROM estoq", null);
						//String sql = "insert into estoq (prod) values ('" + nome + "');";
						//String sql = "insert into estoq (prod) values (" + nome + ");";
						//Log.d("sql Estoque ", sql);
						//c = db.rawQuery(sql, null);
						//Log.d("sql Estoque 1 ", sql);
						contador = contador + 1;
						//Log.d("RepEst contador ", String.valueOf(contador));
/*						
						
						EstoqueVO vo = new EstoqueVO();
						
						vo.setProd(nome);
						
						EstoqueDAO dao = new EstoqueDAO(getBaseContext());
						dao.insert(vo);
*/						
						
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
            
            mensagemExibir("Dados", "Estoque importado com sucesso do servidor!!");

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
			AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarEstoqueIn.this);
			mensagem.setTitle(titulo);
			mensagem.setMessage(texto);
			mensagem.setNeutralButton("OK",null);
			mensagem.show();
	   }
}