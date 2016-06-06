package br.com.datasol.vendasmcel;

import br.com.datasol.vendasmcel.R;
import br.com.datasol.vendasmcel.dao.VendedorDAO;
import br.com.datasol.vendasmcel.vo.VendedorVO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VendedorEditar extends Activity{
	
	private int COD = 0;
	private EditText txtCOD;
	private EditText txtNome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendedoreditar);
		
		Intent it = getIntent();
		COD = it.getIntExtra("codigo", 1);
		
		Button btnOK = (Button)findViewById(R.id.btAtualizar);
		
		final VendedorDAO dao = new VendedorDAO(getBaseContext());
		final VendedorVO vo = dao.getById(COD);
		
		txtCOD = (EditText)findViewById(R.id.txtCod);
		txtNome = (EditText) findViewById(R.id.txtNome);
		
		txtCOD.setText(vo.getId().toString());
		txtNome.setText(vo.getNome());
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				VendedorVO vo = new VendedorVO();
				vo.setId((COD));
				vo.setNome(txtNome.getText().toString());
				
				VendedorDAO dao = new VendedorDAO(getBaseContext());
				if(dao.update(vo)){
					Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}

}
