package br.com.datasol.vendasmcel.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasmcel.DB.DB;
import br.com.datasol.vendasmcel.vo.EstoqueVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EstoqueDAO {
	
	private static String table_name = "estoq";
	private static Context ctx;
	private static String[] columns = {"cod", "prod", "codprod", "q1", "vt", "unid"};
	
	public EstoqueDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(EstoqueVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("prod", vo.getProd());
		ctv.put("codprod", vo.getCodprod());
		ctv.put("q1", vo.getQ1());
		ctv.put("vt", vo.getVt());
		ctv.put("unid", vo.getUnid());
		
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(EstoqueVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "cod=?", new String[]{vo.getCod().toString()}) > 0);
	}
	
	public void deleteAll(){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.delete(table_name, null, null);
	}	
	
	public boolean update(EstoqueVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("prod", vo.getProd());
		ctv.put("codprod", vo.getCodprod());
		ctv.put("q1", vo.getQ1());
		ctv.put("vt", vo.getVt());
		ctv.put("unid", vo.getUnid());

		return (db.update(table_name, ctv, "cod=?", new String[]{vo.getCod().toString()}) > 0);
	}	
	
	public EstoqueVO getById(Integer COD){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "cod=?", new String[]{COD.toString()}, null, null, null);
		
		EstoqueVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new EstoqueVO();
			vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
			vo.setProd(rs.getString(rs.getColumnIndex("prod")));
			vo.setCodprod(rs.getString(rs.getColumnIndex("codprod")));
			vo.setQ1(rs.getString(rs.getColumnIndex("q1")));
			vo.setVt(rs.getString(rs.getColumnIndex("vt")));
			vo.setUnid(rs.getString(rs.getColumnIndex("unid")));
		}
		
		return vo;
	}
	
	public EstoqueVO getByName(String produto){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "prod=?", new String[]{produto.toString()}, null, null, null);
		
		EstoqueVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new EstoqueVO();
			vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
			vo.setProd(rs.getString(rs.getColumnIndex("prod")));
			vo.setCodprod(rs.getString(rs.getColumnIndex("codprod")));
			vo.setQ1(rs.getString(rs.getColumnIndex("q1")));
			vo.setVt(rs.getString(rs.getColumnIndex("vt")));
			vo.setUnid(rs.getString(rs.getColumnIndex("unid")));
		}
		
		return vo;
	}
	
	
	public List<EstoqueVO> getAll(){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM estoq", null);
		
		List<EstoqueVO> lista = new ArrayList<EstoqueVO>();
		
		while(rs.moveToNext()){
			EstoqueVO vo = new EstoqueVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					 rs.getString(5));
			lista.add(vo);
		}

		return lista;
	}
	
	public List<String> getAllLista(){
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM estoq", null);
		
		ArrayList<String> lista2= null;
		while(rs.moveToNext()){
			lista2.add(rs.getString(1));
		}
		
		return lista2;
	}	

}