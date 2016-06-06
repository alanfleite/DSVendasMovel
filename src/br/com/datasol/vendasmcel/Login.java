package br.com.datasol.vendasmcel;

import br.com.datasol.vendasmcel.dao.ConfigDAO;
import br.com.datasol.vendasmcel.R;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	private String usuario="datasol";
	private String senha="ds2009";
	EditText etUsuario;
	EditText etSenha;
	Button btAcessar;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.login);
		
		etUsuario=(EditText) findViewById(R.id.etUsuario);
        etSenha=(EditText) findViewById(R.id.etSenha);
        btAcessar=(Button) findViewById(R.id.bAcessar);
        
        btAcessar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (etUsuario.getText().toString().equals(usuario) && etSenha.getText().toString().equals(senha)){
					startActivity(new Intent(Login.this,PrincipalNovo.class));
				}else{				
					ConfigDAO configDAO = new ConfigDAO(getBaseContext());							
					String login = configDAO.verificaLogin(etUsuario.getText().toString(), etSenha.getText().toString());
				//Log.d("login", login);
					if (login=="1"){
					startActivity(new Intent(Login.this,PrincipalNovo.class));					
				} else {
					if (etUsuario.getText().toString().equals(usuario) && etSenha.getText().toString().equals(senha)){
						startActivity(new Intent(Login.this,PrincipalNovo.class));
					}else{
						mensagemExibir("Login", "Dados não conferem, favor digitar novamente!");
					}
				}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void mensagemExibir(String titulo, String mensagemm) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(
				Login.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(mensagemm);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}		

}
