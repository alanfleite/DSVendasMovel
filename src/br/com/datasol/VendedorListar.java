package br.com.datasol;

import java.util.List;

import br.com.datasol.adapters.VendedorAdapter;
import br.com.datasol.dao.VendedorDAO;
import br.com.datasol.R;
import br.com.datasol.vo.VendedorVO;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VendedorListar extends Activity{
	ListView ltw;
	List<VendedorVO> lista = null;
	int idItem = 0;
	private static int MENU_EDITAR = 1;
	private static int MENU_APAGAR = 2;
//	private static int MENU_CALL = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendedorlistar);
		ltw = (ListView)findViewById(R.id.ltvDados);
		ltw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		final Button btnApagar = (Button)findViewById(R.id.btnApagar);
		
		
		registerForContextMenu(ltw);
		
		ltw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				btnApagar.setVisibility(0);
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		VendedorDAO dao = new VendedorDAO(getBaseContext());
		lista = dao.getAll();
		ltw.setAdapter(new VendedorAdapter(getBaseContext(), lista));
	}
	
	public void Apagar_click(View v){
		String nomes = "";
		SparseBooleanArray checkeds = ltw.getCheckedItemPositions();
		
		for(int i = 0; i < checkeds.size(); i++){
			nomes += lista.get(checkeds.keyAt(i)).getNome() + ", ";
		}
		
		Toast.makeText(getBaseContext(), nomes, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		
		menu.setHeaderTitle(lista.get(info.position).getNome());
		menu.add(Menu.NONE, MENU_EDITAR, 0, "Editar");
		menu.add(Menu.NONE, MENU_APAGAR, 1, "Apagar");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		idItem = lista.get(info.position).getId();
		
		
		if(item.getItemId() == MENU_EDITAR){
			Intent it = new Intent(getBaseContext(), VendedorEditar.class);
			it.putExtra("codigo", idItem);
			startActivity(it);
		}

		else if(item.getItemId() == MENU_APAGAR){
			Log.d("deletar vendedor", String.valueOf(idItem));
			Builder msg = new  Builder(VendedorListar.this);
			msg.setMessage("Deseja excluir este Vendedor?");
			msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					VendedorDAO dao = new VendedorDAO(getBaseContext());
					VendedorVO vendedor = dao.getById(idItem);
					Log.d("deletar vendedor", String.valueOf(idItem));
					if(dao.delete(vendedor) == true){
						Toast.makeText(getBaseContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
						ltw.setAdapter(new VendedorAdapter(getBaseContext(), dao.getAll()));
					}
				}
			});
			msg.setNegativeButton("Não", null);
			
			msg.show();
		}
		return super.onContextItemSelected(item);
	}

}
