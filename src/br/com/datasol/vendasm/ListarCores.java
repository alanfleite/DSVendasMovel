package br.com.datasol.vendasm;

import java.util.ArrayList;

import br.com.datasol.vendasm.DB.DB;
import br.com.datasol.vendasm.dao.Cad_cliDAO;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListarCores extends ListActivity{
	
	//private static Context ctx=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] cores = {"Azul", "Verde", "Vermelho", "Preto", "Cinza", "Amarelo", "Vermelho"};
		setListAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, cores));
/*        
		Log.d("1", null);		
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
        Log.d("2", null);
		Cursor rs = db.rawQuery("SELECT * FROM cad_cli", null);
		Log.d("3", null);
//		List listaVendaC;
		
		ArrayList<String> lista2= null;
		Log.d("4", null);
		while(rs.moveToNext()){
			//listaVendaC.add(rs.getString(1));
			//String[] lista1 = {rs.getString(1)};
			//lista1 = {rs.getString(1)};
			lista2.add(rs.getString(1));
			Log.d("5", rs.getString(1));
		}
*/
//		Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
//		setListAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, dao.getAllLista()));
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		setResult(RESULT_OK, new Intent().putExtra("cor", l.getAdapter().getItem(position).toString()));
		finish();
	}
}