package br.com.datasol.vendasmcel;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
  
public class PrincipalNovo extends ListActivity{
  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        //setContentView(R.layout.teste);
        
        String [] menu = new String[] {"Auxiliar", "At. Cliente", "At. Produtos", "Venda - Cliente", "Venda - Produtos", "Vendas UP(Enviar Servidor)", "Versão 10.0.40 - Ano 2016", "Fechar"};
        ArrayAdapter<String> aaMenu = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        setListAdapter(aaMenu);        
    }    
    
    protected void onListItemClick(ListView l, View v, int position, long id)
	 {
		 super.onListItemClick(l, v, position, id);	 
		 Object objetoSelecionado = this.getListAdapter().getItem(position);
		 String menuSelecionado = objetoSelecionado.toString();
		 
		 switch(position) {
		 case 0:
			 startActivity(new Intent(this,MenuPrincipal.class)); 
			 break;
		 case 1:
			 startActivity(new Intent(this,ReplicarClientesIn.class)); 
			 break;		 
		 case 2:
			 startActivity(new Intent(this,ReplicarEstoqueIn.class)); break;			 
		 case 3:
			 startActivity(new Intent(this,VendaC.class)); break;			 
		 case 4:
			 startActivity(new Intent(this,VendaD.class)); break;			 
		 case 5:
			 startActivity(new Intent(this,ReplicarVendasOff1.class)); break;			 
		 case 6:
			 //startActivity(new Intent(this,VendasPer.class)); break;
		 case 7:
			 finish(); break;
			 
		default: finish();
		 }
	 }    
}  