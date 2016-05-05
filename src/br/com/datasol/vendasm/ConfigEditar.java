package br.com.datasol.vendasm;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vo.ConfigVO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigEditar extends Activity{
	
	private int COD = 0;
	private EditText txtCOD;
	private EditText txtUrl;
	private EditText txtUsuario;
	private EditText txtSenha;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configeditar);
		
		Intent it = getIntent();
		COD = it.getIntExtra("codigo", 1);
		
		Button btnOK = (Button)findViewById(R.id.btAtualizar);
		
		final ConfigDAO dao = new ConfigDAO(getBaseContext());
		final ConfigVO vo = dao.getById(COD);
		
		txtCOD     = (EditText)findViewById(R.id.txtCod);
		txtUrl     = (EditText) findViewById(R.id.txtUrl);
		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtSenha   = (EditText) findViewById(R.id.txtSenha);
		
		txtCOD.setText(vo.getId().toString());
		txtUrl.setText(vo.getUrl());
		txtUsuario.setText(vo.getUsuario());
		txtSenha.setText(vo.getSenha());
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ConfigVO vo = new ConfigVO();
				vo.setId((COD));
				vo.setUrl(txtUrl.getText().toString());
				vo.setUsuario(txtUsuario.getText().toString());
				vo.setSenha(txtSenha.getText().toString());
				
				ConfigDAO dao = new ConfigDAO(getBaseContext());
				if(dao.update(vo)){
					Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}

}
