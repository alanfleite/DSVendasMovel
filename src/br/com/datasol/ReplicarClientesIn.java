package br.com.datasol;

import br.com.datasol.DB.DB;
import br.com.datasol.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.dao.Cad_cliDAO;
import br.com.datasol.dao.ConfigDAO;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.dao.VendedorDAO;
import br.com.datasol.R;
import br.com.datasol.vo.Cad_cliVO;
import br.com.datasol.vo.ConfigVO;
import br.com.datasol.vo.EstoqueVO;
import br.com.datasol.vo.VendedorVO;
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

public class ReplicarClientesIn extends Activity{
	int posicao=0;
	String [] listaClientes;
	EditText etQt;
	
	private Cursor c = null;
	private String cliente;
	private String usuario;
	private String razao;
	private String ende;
	private String ende_num;
	private String fone;
	private String cel;
	private String bairro;
	private String cida;
	private String uf;
	private String cnpj;
	private String cpf;
	private String parcelaatrazo;

	private SQLiteDatabase db;
	private static Context ctx;	
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        //etQt = (EditText) findViewById(R.id.txtQt);
	        
	        //Log.d("delete", "1");
	        Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
	        dao.deleteAll();
	        //Log.d("delete", "2");
	        
	        VendedorDAO vdao = new VendedorDAO(getBaseContext());
	        String vendedor = vdao.getVendedor();
	        
	        ConfigDAO configDAO = new ConfigDAO(getBaseContext());
	        ConfigVO configVO = configDAO.getById(1);
	        String url = configVO.getUrl(); 
	        //Log.d("url", url);
	        
	        setContentView(R.layout.listarreplicacaocad_cli);
	        //Log.d("RepCli", "1");
	        //db.delete("cad_cli", null,null);
	        //Log.d("RepCli", "2");
	        //String url="http://datasol1.no-ip.biz:8080/AndroidWeb/ListarClientes.jsp";
//	        String url;
	        if (vendedor.equals("WASHINGTON")){
	        	url= url + "/ListarClientes.jsp?vendedor=" + vendedor;
	        	//url="http://192.168.1.12:8080/AndroidWeb/ListarClientes.jsp?vendedor=" + vendedor;	        	
	        } else{
	        	url= url + "/ListarClientes.jsp?vendedor=" + vendedor;
	        	//url="http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ListarClientes.jsp?vendedor=" + vendedor;
	        	//url="http://10.1.1.7:8080/AndroidWeb/ListarClientes.jsp?vendedor=" + vendedor;
	        }
			
			//Log.d("URL", url);
			String respostaRetornada = null;
			//Log.d("RepCli", "4");
			try {
				//Log.d("RepCli", "5");
				respostaRetornada = ConexaoHTTPClient.executaHttpGet(url);
				String resposta = respostaRetornada.toString();
				
				char separador='#';
				int contUsuarios = 0;
				for(int i=0;i < resposta.length();i++){
					if (separador == resposta.charAt(i))
						contUsuarios++;
				}
				
				db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
				
				
				listaClientes = new String[contUsuarios];
				
				char caracter_lido=resposta.charAt(0);
				String nome="";
				//String sql = "insert into CAD_CLI (usuario) values (" + nome + ");";
				int contador=0;
				
				for (int i=0; caracter_lido != '^'; i++){
					caracter_lido = resposta.charAt(i);
					
					if (caracter_lido != '#'){
						nome += caracter_lido;
					}else {
						listaClientes[posicao] = "" + nome;
						posicao++;
						
//						CAMPO E LAVOURA|A. R. DOS ANGELOS ME@17.775.485/0001-98$N*PARECIS%#
						int us = nome.indexOf("|"); 
	                    usuario = nome.substring(0, us);	                    
	                    us = us + 1;
	                    int r = nome.indexOf("@");
	                    razao = nome.substring(us, r);
	                    r = r + 1;
	                    int cn = nome.indexOf("$");
	                    cnpj = nome.substring(r, cn);
	                    cn = cn + 1;
	                    int patr = nome.indexOf("*");
	                    parcelaatrazo = nome.substring(cn, patr);
	                    patr = patr + 1;
	                    int ci = nome.indexOf("%");
	                    cida = nome.substring(patr, ci);
	                    //Log.d("cli 4", "usuario " + usuario + " - razao " + razao + " - ende " + ende + " - ende n " + ende_num + " - fone " + fone + " - cel " + cel + " - bairro " + bairro + " - cida " + cida + " - uf " + uf + " - cnpj " + cnpj + " - cpf " + cpf + " - parcela atrazo " + parcelaatrazo + " Contador " + String.valueOf(contador));
					
						Cad_cliVO vo = new Cad_cliVO();						
						vo.setUsuario(usuario.toString());
						vo.setRazao(razao.toString());
						vo.setCidade(cida.toString());
						vo.setCnpj(cnpj.toString());
						vo.setContato(parcelaatrazo.toString());
						
						dao = new Cad_cliDAO(getBaseContext());
						dao.insert(vo);
						
						contador = contador + 1;
						//Log.d("RepCli contador ", String.valueOf(contador));
						nome = "";
					}
				}
			} catch (Exception e) {
				mensagemExibir("Clientes", "Erro " + e);
			}

	        ArrayAdapter<String> aaProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaClientes);
            AutoCompleteTextView autoCompletarProdutos = (AutoCompleteTextView) findViewById(R.id.autoCompleteClientes);
            autoCompletarProdutos.setAdapter(aaProdutos);
            
            Spinner spinnerUsuarios = (Spinner) findViewById(R.id.spinnerClientes);
            ArrayAdapter<String> todosProdutos;
            todosProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaClientes);
            todosProdutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUsuarios.setAdapter(todosProdutos);
            
            mensagemExibir("Dados", "Importado com sucesso do servidor!!");
	 }
	 
	   public void mensagemExibir(String titulo, String texto)
	   {
			AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarClientesIn.this);
			mensagem.setTitle(titulo);
			mensagem.setMessage(texto);
			mensagem.setNeutralButton("OK",null);
			mensagem.show();
	   }
}