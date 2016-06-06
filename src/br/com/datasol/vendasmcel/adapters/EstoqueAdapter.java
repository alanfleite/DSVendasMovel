package br.com.datasol.vendasmcel.adapters;

import java.util.List;

import br.com.datasol.vendasmcel.vo.EstoqueVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EstoqueAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<EstoqueVO> lista;
	
	public EstoqueAdapter(Context ctx, List<EstoqueVO> lista){
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
		EstoqueVO vo = (EstoqueVO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
		
		TextView txtUsuario = (TextView)v.findViewById(android.R.id.text1);
		txtUsuario.setText(vo.getProd());
		
		return v;
	}

}
