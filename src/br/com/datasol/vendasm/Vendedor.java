package br.com.datasol.vendasm;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.dao.VendedorDAO;
import br.com.datasol.vo.VendedorVO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Vendedor extends Activity{
	
	private EditText txtNome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vendedor);

		Button btnSalvar = (Button) findViewById(R.id.btSalvar);

		txtNome = (EditText) findViewById(R.id.txtNome);

		btnSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				VendedorVO vo = new VendedorVO();
				
				vo.setNome(txtNome.getText().toString());
				
				VendedorDAO dao = new VendedorDAO(getBaseContext());
				if (dao.insert(vo)) {
					Toast.makeText(getBaseContext(), "Sucesso!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
