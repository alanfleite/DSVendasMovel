package br.com.datasol.vendasm;

import java.util.List;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.adapters.ConfigAdapter;
import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vendasm.vo.ConfigVO;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class ConfigListar extends Activity{
	ListView ltw;
	List<ConfigVO> lista = null;
	int idItem = 0;
	private static int MENU_EDITAR = 1;
	private static int MENU_APAGAR = 2;
//	private static int MENU_CALL = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configlistar);
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
		ConfigDAO dao = new ConfigDAO(getBaseContext());
		lista = dao.getAll();
		ltw.setAdapter(new ConfigAdapter(getBaseContext(), lista));
	}
	
	public void Apagar_click(View v){
		String nomes = "";
		SparseBooleanArray checkeds = ltw.getCheckedItemPositions();
		
		for(int i = 0; i < checkeds.size(); i++){
			nomes += lista.get(checkeds.keyAt(i)).getUrl() + ", ";
		}
		
		
		Toast.makeText(getBaseContext(), nomes, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		
		menu.setHeaderTitle(lista.get(info.position).getUrl());
		menu.add(Menu.NONE, MENU_EDITAR, 0, "Editar");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		idItem = lista.get(info.position).getId();
		
		
		if(item.getItemId() == MENU_EDITAR){
			Intent it = new Intent(getBaseContext(), ConfigEditar.class);
			it.putExtra("codigo", idItem);
			startActivity(it);
		}

		else if(item.getItemId() == MENU_APAGAR){
			Builder msg = new  Builder(ConfigListar.this);
			msg.setMessage("Deseja excluir esta Configuração?");
			msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					ConfigDAO dao = new ConfigDAO(getBaseContext());
					ConfigVO Config = dao.getById(idItem);
					if(dao.delete(Config) == true){
						Toast.makeText(getBaseContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
						ltw.setAdapter(new ConfigAdapter(getBaseContext(), dao.getAll()));
					}
				}
			});
			msg.setNegativeButton("Não", null);
			
			msg.show();
		}
		return super.onContextItemSelected(item);
	}
}