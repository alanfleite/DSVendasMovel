package br.com.datasol.vendasm;

import br.com.datasol.vendasm.R;
import br.com.datasol.vendasm.dao.Cad_cliDAO;
import br.com.datasol.vendasm.vo.Cad_cliVO;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cad_cli extends Activity{
	
	private EditText txtUsuario;
	private EditText txtRazao;
	private EditText txtEnde;
	private EditText txtEnde_num;
	private EditText txtFone;
	private EditText txtCel;
	private EditText txtBairro;
	private EditText txtCidade;
	private EditText txtUf;
	private EditText txtCnpj;
	private EditText txtRg;
	private EditText txtInscest;
	private EditText txtResp;
	private EditText txtEmail;
	private EditText txtContato;
	private EditText txtCpf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_cli);

		Button btnSalvar = (Button) findViewById(R.id.btSalvar);

		txtUsuario = (EditText) findViewById(R.id.txtFantasia);
		txtRazao = (EditText) findViewById(R.id.txtRazao);
		txtEnde = (EditText) findViewById(R.id.txtEnde);
		txtEnde_num = (EditText) findViewById(R.id.txtEnde_num);
		txtFone = (EditText) findViewById(R.id.txtFone);
		txtCel = (EditText) findViewById(R.id.txtCel);
		txtBairro = (EditText) findViewById(R.id.txtBairro);
		txtCidade = (EditText) findViewById(R.id.txtCidade);
		txtUf = (EditText) findViewById(R.id.txtUf);
		txtCnpj = (EditText) findViewById(R.id.txtCnpj);
		txtRg = (EditText) findViewById(R.id.txtRg);
		txtInscest = (EditText) findViewById(R.id.txtInscEst);
		//txtResp = (EditText) findViewById(R.id.txtResp); //03.05.2016 - colocado para armazenar informacao de cliente novo
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtContato = (EditText) findViewById(R.id.txtContato);
		txtCpf = (EditText) findViewById(R.id.txtCPF);
		
		btnSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Cad_cliVO vo = new Cad_cliVO();
				
				vo.setUsuario(txtUsuario.getText().toString());
				vo.setRazao(txtRazao.getText().toString());
				vo.setEnde(txtEnde.getText().toString());
				vo.setEnde_num(txtEnde_num.getText().toString());
				vo.setFone(txtFone.getText().toString());
				vo.setCel(txtCel.getText().toString());
				vo.setBairro(txtBairro.getText().toString());
				vo.setCidade(txtCidade.getText().toString());
				//Log.d("salvar cliente0", vo.getUsuario());				
				vo.setUf(txtUf.getText().toString());
				//Log.d("salvar cliente1", vo.getUsuario());								
				vo.setCnpj(txtCnpj.getText().toString());
				vo.setRg(txtRg.getText().toString());
				vo.setInscest(txtInscest.getText().toString());
				//vo.setResp(txtResp.getText().toString());
				vo.setResp("Sim");
				vo.setEmail(txtEmail.getText().toString());
				vo.setContato(txtContato.getText().toString());
				vo.setCpf(txtCpf.getText().toString());
				
				Log.d("salvar cliente", vo.getUsuario());
				
				Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
				if (dao.insert(vo)) {
					Toast.makeText(getBaseContext(), "Sucesso!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
