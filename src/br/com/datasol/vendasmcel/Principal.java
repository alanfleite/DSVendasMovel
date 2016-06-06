package br.com.datasol.vendasmcel;

import br.com.datasol.vendasmcel.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		
		ImageButton ibtAuxiliar = (ImageButton)findViewById(R.id.ibtAuxiliar);
		ImageButton ibtClientesDow = (ImageButton)findViewById(R.id.ibtClienteDow);
		ImageButton ibtProdutosDow = (ImageButton)findViewById(R.id.ibtProdutosDow);
		ImageButton ibtVendaC = (ImageButton)findViewById(R.id.ibtVendaC);
		ImageButton ibtVendaD = (ImageButton)findViewById(R.id.ibtVendaD);
		ImageButton ibtVendasUp = (ImageButton)findViewById(R.id.ibtVendasUP);
		ImageButton ibtSair = (ImageButton)findViewById(R.id.ibtSair);
		
		ibtAuxiliar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), MenuPrincipal.class));
			}
		});
		
		ibtVendaC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),VendaC.class)); 
				
			}
		});

		ibtVendaD.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),VendaD.class));
			}
		});		
		
		ibtClientesDow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext() ,ReplicarClientesIn.class)); 
				
			}
		});
		
		ibtProdutosDow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),ReplicarEstoqueIn.class));
				
			}
		});
		
		ibtVendasUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),ReplicarVendasOff1.class)); 
				
			}
		});
				
		ibtSair.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
}