package br.com.datasol.vendasmcel;

import br.com.datasol.vendasmcel.R;
import br.com.datasol.vendasmcel.dao.ConfigDAO;
import br.com.datasol.vendasmcel.vo.ConfigVO;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Config extends Activity{
	
	private EditText txtUrl;
	private EditText txtUsuario;
	private EditText txtSenha;
	//private ListView lvTipoFiltroCliente;
	//private String[] lstTipoFiltroCliente;
	private Spinner spTipoFiltroCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
//		ConfigDAO daoAlter = new ConfigDAO(getBaseContext());
//		daoAlter.alterTable();

		Button btnSalvar = (Button) findViewById(R.id.btSalvar);

		txtUrl     = (EditText) findViewById(R.id.txtUrl);
		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtSenha   = (EditText) findViewById(R.id.txtSenha);
		//lvTipoFiltroCliente = (ListView) findViewById(R.id.lvTipoFiltroCliente);
		spTipoFiltroCliente = (Spinner) findViewById(R.id.spTipoFiltroCliente);
		
		//lstTipoFiltroCliente = new String[] {"Fantasia", "Razão Social"};
		
		//lvTipoFiltroCliente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstTipoFiltroCliente));
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.arrayTipoFiltroCliente, android.R.layout.simple_spinner_item);
		
		spTipoFiltroCliente.setAdapter(adapter);
		
		btnSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ConfigVO vo = new ConfigVO();
				
				vo.setUrl(txtUrl.getText().toString());
				vo.setUsuario(txtUsuario.getText().toString());
				vo.setSenha(txtSenha.getText().toString());
				vo.setFiltrocliente(spTipoFiltroCliente.getSelectedItem().toString());
				//Log.d("item spinner", spTipoFiltroCliente.getSelectedItem().toString());
				
				ConfigDAO dao = new ConfigDAO(getBaseContext());
				if (dao.insert(vo)) {
					Toast.makeText(getBaseContext(), "Sucesso!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}