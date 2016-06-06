package br.com.datasol.vendasmcel.adapters;

import java.util.List;

import br.com.datasol.vendasmcel.vo.VendedorVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VendedorAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<VendedorVO> lista;
	
	public VendedorAdapter(Context ctx, List<VendedorVO> lista){
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
		VendedorVO vo = (VendedorVO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
		
		/*TextView txtID = (TextView)v.findViewById(br.com.devmedia.clientes.R.id.txtID);
		txtID.setText(vo.getId().toString());*/
		
		TextView txtNome = (TextView)v.findViewById(android.R.id.text1);
		txtNome.setText(vo.getNome());
		
		return v;
	}
}