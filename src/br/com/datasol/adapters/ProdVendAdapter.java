package br.com.datasol.adapters;

import java.util.List;

import br.com.datasol.vo.ProdVendVO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProdVendAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<ProdVendVO> lista;
	
	public ProdVendAdapter(Context ctx, List<ProdVendVO> lista){
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
		ProdVendVO vo = (ProdVendVO)getItem(position);
				
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(android.R.layout.simple_list_item_checked, null);
		
		TextView txtProdutoLista = (TextView)v.findViewById(android.R.id.text1);
		txtProdutoLista.setText(vo.getProd() + " - Qt. " + vo.getQ1() + " - V. Unit. " + vo.getVl_u() + " - V. Total " + vo.getVl_t());
		
		
/*		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(R.layout.venda, null);
		

		TextView txtProdutoLista = (TextView)v.findViewById(R.id.txtProdutoLista);
		txtProdutoLista.setText(vo.getProd().toString());
		
		TextView txtQtLista = (TextView)v.findViewById(R.id.txtQtLista);
		txtQtLista.setText(vo.getQ1().toString());
		
		TextView txtVULista = (TextView)v.findViewById(R.id.txtVULista);
		txtVULista.setText(vo.getVl_u().toString());
		
		TextView txtVTLista = (TextView)v.findViewById(R.id.txtVTLista);
		txtVTLista.setText(vo.getVl_t().toString());
*/		
		return v;
	}
}