package br.com.datasol.vendasm.adapters;

import java.util.List;

import br.com.datasol.vo.ConfigVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConfigAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<ConfigVO> lista;
	
	public ConfigAdapter(Context ctx, List<ConfigVO> lista){
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
		ConfigVO vo = (ConfigVO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
		
		/*TextView txtID = (TextView)v.findViewById(br.com.devmedia.clientes.R.id.txtID);
		txtID.setText(vo.getId().toString());*/
		
		TextView txtUrl = (TextView)v.findViewById(android.R.id.text1);
		txtUrl.setText(vo.getUrl());
		
		return v;
	}
}