package br.com.datasol.vendasm;

import br.com.datasol.vendasm.dao.ConfigDAO;
import br.com.datasol.vendasm.R;
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
					startActivity(new Intent(Login.this,Principal.class));
				}else{				
					ConfigDAO configDAO = new ConfigDAO(getBaseContext());							
					String login = configDAO.verificaLogin(etUsuario.getText().toString(), etSenha.getText().toString());
				//Log.d("login", login);
					if (login=="1"){
					startActivity(new Intent(Login.this,Principal.class));					
				} else {
					if (etUsuario.getText().toString().equals(usuario) && etSenha.getText().toString().equals(senha)){
						startActivity(new Intent(Login.this,Principal.class));
					}else{
						mensagemExibir("Login", "Dados não conferem, favor digitar novamente!");
					}
				}
				}
/*								
				if (etUsuario.getText().toString().equals(usuario) && etSenha.getText().toString().equals(senha)){
					startActivity(new Intent(Login.this,Principal.class));
				}else{
					mensagemExibir("Login", "Dados não conferem, favor digitar novamente!");
				}
*/					
/*				
				Log.i("logar", "1");
				String urlPost="http://192.168.1.3:8080/AndroidWeb/logar.jsp";
				ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
				paramentosPost.add(new BasicNameValuePair("usuario", etUsuario.getText().toString()));
				paramentosPost.add(new BasicNameValuePair("senha", etSenha.getText().toString()));
				
				String respostaRetornada = null;
//				Log.i("logar", "2");
				try {
//					Log.i("logar", "3");
					respostaRetornada = ConexaoHTTPClient.executaHttpPost(urlPost, paramentosPost);
//					Log.i("logar", "3.5");
					String resposta = respostaRetornada.toString();
					resposta = resposta.replaceAll("\\s+", "");
//					Log.i("logar", "4" + resposta);
					if (resposta.equals("1")){
						startActivity(new Intent(Login.this,MenuPrincipal.class));
//						MenuPrincipal menuPrincipal = new MenuPrincipal();
//						menuPrincipal.recebeGarcom(etUsuario.getText().toString());
//					Log.i("logar", etUsuario.getText().toString());
					}else
						mensagemExibir("Login", "Usuário inválido");
					
				} catch (Exception e) {
					mensagemExibir("Login", "Erro " + e);
				}
*/				
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
