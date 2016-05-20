package br.com.datasol.vendasm.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasm.DB.DB;
import br.com.datasol.vendasm.vo.ConfigVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConfigDAO {

	private static String table_name = "config";
	private static Context ctx;
	private static String[] columns = {"id", "url", "usuario", "senha", "filtrocliente"};
	
	public ConfigDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(ConfigVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("url", vo.getUrl());
		ctv.put("usuario", vo.getUsuario());
		ctv.put("senha", vo.getSenha());
		ctv.put("filtrocliente", vo.getFiltrocliente());
		
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(ConfigVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public boolean update(ConfigVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("url", vo.getUrl());
		ctv.put("usuario", vo.getUsuario());
		ctv.put("senha", vo.getSenha());
		ctv.put("filtrocliente", vo.getFiltrocliente());
		
		return (db.update(table_name, ctv, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public ConfigVO getById(Integer ID){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ConfigVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new ConfigVO();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setUrl(rs.getString(rs.getColumnIndex("url")));
			vo.setUsuario(rs.getString(rs.getColumnIndex("usuario")));
			vo.setSenha(rs.getString(rs.getColumnIndex("senha")));
			vo.setFiltrocliente(rs.getString(rs.getColumnIndex("filtrocliente")));
		}		
		return vo;
	}
	
	public List<ConfigVO> getAll(){		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM Config", null);
		
		List<ConfigVO> lista = new ArrayList<ConfigVO>();
		
		while(rs.moveToNext()){
			ConfigVO vo = new ConfigVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			lista.add(vo);
		}		
		return lista;
	}
	
	public String verificaLogin(String usuario, String senha){
		String retorno = "0";
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM Config where usuario=\"" + usuario + "\" and senha=\"" + senha + "\";", null);
		
		while(rs.moveToNext()){
			retorno = "1";
		}		
		return retorno;
	}	

	public void alterTable(){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
	    String sqlConfigTemp = "CREATE TABLE IF NOT EXISTS CONFIGTEMP (id integer primary key autoincrement,"
	    	      + " url varchar(90), usuario varchar(50), senha varchar(90), tipocliente varchar(90);";
	      
	    String sqlConfigPump = "INSERT INTO configtemp (id, url, usuario, senha) SELECT id, url, usuario, senha FROM config;";
	    	    
	    //String sqlUpdate = "ALTER TABLE rec ADD COLUMN sinc varchar(1)"; 
		
		//db.execSQL(sqlUpdate);
	    
	    String[] statements = new String[]{sqlConfigTemp, sqlConfigPump};
		//String[] statements = new String[]{sqlUpdate};
		
		Log.d("alter table 1", "1");
	    for(String sql : statements){
		    db.execSQL(sql);
		}
	    
	    
		
	    Log.d("alter table 2", "2");
	}
	
	
}