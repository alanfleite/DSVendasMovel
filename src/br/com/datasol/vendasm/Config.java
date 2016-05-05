package br.com.datasol.vendasm;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vo.ConfigVO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Config extends Activity{
	
	private EditText txtUrl;
	private EditText txtUsuario;
	private EditText txtSenha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);

		Button btnSalvar = (Button) findViewById(R.id.btSalvar);

		txtUrl     = (EditText) findViewById(R.id.txtUrl);
		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtSenha   = (EditText) findViewById(R.id.txtSenha);

		btnSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ConfigVO vo = new ConfigVO();
				
				vo.setUrl(txtUrl.getText().toString());
				vo.setUsuario(txtUsuario.getText().toString());
				vo.setSenha(txtSenha.getText().toString());
				
				ConfigDAO dao = new ConfigDAO(getBaseContext());
				if (dao.insert(vo)) {
					Toast.makeText(getBaseContext(), "Sucesso!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}