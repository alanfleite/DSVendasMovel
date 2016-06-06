package br.com.datasol.vendasmcel.adapters;

import java.util.List;

import br.com.datasol.vendasmcel.vo.RecVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<RecVO> lista;
	
	public RecAdapter(Context ctx, List<RecVO> lista){
		this.ctx = ctx;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecVO vo = (RecVO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
		
		TextView txtUsuario = (TextView)v.findViewById(android.R.id.text1);
		//txtUsuario.setText("Nome " + vo.getFantasia() + " - Data " + vo.getDataems() + " - Valor " + vo.getTot());
		txtUsuario.setText(vo.getFantasia() + " - " + vo.getDataems() + " - " + vo.getTot());
		
		return v;
	}

}
