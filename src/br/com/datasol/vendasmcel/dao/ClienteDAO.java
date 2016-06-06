package br.com.datasol.vendasmcel.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasmcel.DB.DB;
import br.com.datasol.vendasmcel.vo.ClienteVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClienteDAO {

	private static String table_name = "clientes";
	private static Context ctx;
	private static String[] columns = {"id", "nome", "email", "endereco", "numero"};
	
	public ClienteDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(ClienteVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		ctv.put("email", vo.getEmail());
		ctv.put("endereco", vo.getEndereco());
		ctv.put("numero", vo.getNumero());
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(ClienteVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public boolean update(ClienteVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		ctv.put("email", vo.getEmail());
		ctv.put("endereco", vo.getEndereco());
		ctv.put("numero", vo.getNumero());
		
		return (db.update(table_name, ctv, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public ClienteVO getById(Integer ID){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ClienteVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new ClienteVO();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setNome(rs.getString(rs.getColumnIndex("nome")));
			vo.setEmail(rs.getString(rs.getColumnIndex("email")));
			vo.setEndereco(rs.getString(rs.getColumnIndex("endereco")));
			vo.setNumero(rs.getString(rs.getColumnIndex("numero")));
		}
		
		return vo;
	}
	
	public List<ClienteVO> getAll(){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM clientes", null);
		
		List<ClienteVO> lista = new ArrayList<ClienteVO>();
		
		while(rs.moveToNext()){
			ClienteVO vo = new ClienteVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			lista.add(vo);
		}
		
		return lista;
	}
}
