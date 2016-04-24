package br.com.datasol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;  
import java.util.List;

import br.com.datasol.adapters.EstoqueAdapter;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.R;
import br.com.datasol.vo.EstoqueVO;
import android.app.Activity;  
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.text.Editable;  
import android.text.TextWatcher;  
import android.util.Log;
import android.view.View;  
import android.widget.AdapterView;  
import android.widget.ArrayAdapter;  
import android.widget.EditText;  
import android.widget.ListView;  
import android.widget.Toast;  
import android.widget.AdapterView.OnItemClickListener;  
  
//public class Teste extends Activity implements Runnable{  
public class Teste extends Activity{
	private ListView lv;
    private EditText et;
    private String[] lstEstoque;
//    String [] listaProdutos;
    private ArrayList<String> lstEstoque_Encontrados = new ArrayList<String>();
	private SQLiteDatabase db;
	private Cursor rec = null;
	private Cursor prodvend = null;
	private int posicao=0;
  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.teste);  
        //Log.d("teste", "1");
        lv = (ListView) findViewById(R.id.lvEstados);  
        et = (EditText) findViewById(R.id.etProcurar);
        //Log.d("teste", "2");
        db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
        //Log.d("teste", "3");
		rec = db.rawQuery("SELECT COD, CODPROD, PROD, VT, Q1 FROM estoq order by prod", null);
		
		int contProdEstoq= rec.getCount();
        //Log.d("teste", "3.5" + " - " + String.valueOf(contProdEstoq));
		lstEstoque = new String[contProdEstoq];
		//Log.d("teste", "4");
		
		while(rec.moveToNext()){
			//lstEstoque[posicao] = "" + rec.getString(rec.getColumnIndex("prod"));
			
			lstEstoque[posicao] = "" + rec.getString(rec.getColumnIndex("cod")) 
					                 + " - " + rec.getString(rec.getColumnIndex("prod"))
					                 + " - " + rec.getString(rec.getColumnIndex("codprod"))
			                         + " - " + rec.getString(rec.getColumnIndex("vt"));
			                         
			//Log.d("REC", rec.getString(rec.getColumnIndex("prod")) + " - posicao " + String.valueOf(posicao));
			posicao++;
		}
        
        //lstEstados = new String[] {"São Paulo", "Rio de Janeiro", "Minas Gerais", "Rio Grande do Sul",
        //        "Santa Catarina", "Paraná", "Mato Grosso", "Amazonas"};
     
		
        //Carrega o listview com todos os itens  
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstEstoque));
        CarregarEncontrados();  
  
        //Adiciona um TextWatcher ao TextView cujos métodos são chamados sempre   
        //que este TextView sofra alterações.  
        et.addTextChangedListener(new TextWatcher()  
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
                lv.setAdapter(new ArrayAdapter<String>(Teste.this, android.R.layout.simple_list_item_1, lstEstoque_Encontrados));  
            }  
        });  
     
        lv.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView arg0, View view, int position, long index) {  
                Mensagem("Você clicou no estado : " + lstEstoque_Encontrados.get(position).toString());  
            }  
        });  
     
    }
    
    public void CarregarEncontrados()  
    {  
        int textlength = et.getText().length();  
  
        //Limpa o array com os estados encontrados  
        //para poder efetuar nova busca  
        lstEstoque_Encontrados.clear();  
     
        for (int i = 0; i < lstEstoque.length; i++)  
        {  
            if (textlength <= lstEstoque[i].length())  
            {  
                //Verifica se existe algum item no array original  
                //caso encontre é adicionado no array de encontrados  
                if(et.getText().toString().equalsIgnoreCase((String)lstEstoque[i].subSequence(0, textlength)))  
                {  
                    lstEstoque_Encontrados.add(lstEstoque[i]);  
                }  
            }  
        }  
    }  
    
    private void Mensagem(String msg)   
    {  
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();  
    }   
}  