package br.com.datasol.vendasm.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.vendasm.DB.DB;
import br.com.datasol.vo.ProdVendVO;
import br.com.datasol.vo.RecVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProdVendDAO {
	
	private static String table_name = "prod_vend";
	private static Context ctx;
	private static String[] columns = {"cod", "prod", "q1", "vl_u", "vl_t", "codvend", "data", "unid", "vendedor"};
	
	public ProdVendDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(ProdVendVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("prod", vo.getProd());
		ctv.put("q1", vo.getQ1());
		ctv.put("vl_u", vo.getVl_u());
		ctv.put("vl_t", vo.getVl_t());
		ctv.put("codvend", vo.getCodvend());
		ctv.put("data", vo.getData());
		ctv.put("unid", vo.getUnid());
		ctv.put("vendedor", vo.getVendedor());
		
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(ProdVendVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "cod=?", new String[]{vo.getCod().toString()}) > 0);
	}
	
	public void deleteAll() {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT * FROM prod_vend", null);

		List<ProdVendVO> lista = new ArrayList<ProdVendVO>();

		while (rs.moveToNext()) {
			
			ProdVendVO vo = new ProdVendVO(rs.getInt(0), rs.getString(1),
					rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
			//listaVendaC.add(vo);
			
			delete(vo);
		}
		
//		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
//		db.rawQuery("DELETE from PROD_VEND", null);
		
//		Log.d("deleteall dao", "prodvend");
	}
	
	public void deleteCodVend(String cod) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT * FROM prod_vend where codvend=" + cod, null);

		List<ProdVendVO> lista = new ArrayList<ProdVendVO>();

		while (rs.moveToNext()) {
			
			ProdVendVO vo = new ProdVendVO(rs.getInt(0), rs.getString(1),
					rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
			delete(vo);
		}		
	}	
	
	
	public boolean update(ProdVendVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("prod", vo.getProd());
		ctv.put("q1", vo.getQ1());
		ctv.put("vl_u", vo.getVl_u());
		ctv.put("vl_t", vo.getVl_t());
		ctv.put("codvend", vo.getCodvend());
		ctv.put("data", vo.getData());
		ctv.put("unid", vo.getUnid());
		ctv.put("vendedor", vo.getVendedor());

		return (db.update(table_name, ctv, "cod=?", new String[]{vo.getCod().toString()}) > 0);
	}	
	
	public ProdVendVO getById(Integer COD){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "cod=?", new String[]{COD.toString()}, null, null, null);
		
		ProdVendVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new ProdVendVO();
			vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
			vo.setProd(rs.getString(rs.getColumnIndex("prod")));
			vo.setQ1(rs.getString(rs.getColumnIndex("q1")));
			vo.setVl_u(rs.getString(rs.getColumnIndex("vl_u")));
			vo.setVl_t(rs.getString(rs.getColumnIndex("vl_t")));
			vo.setCodvend(rs.getString(rs.getColumnIndex("codvend")));
			vo.setData(rs.getString(rs.getColumnIndex("data")));
			vo.setUnid(rs.getString(rs.getColumnIndex("unid")));
			vo.setVendedor(rs.getString(rs.getColumnIndex("vendedor")));
		}
		
		return vo;
	}
	
	public List<ProdVendVO> getAll(){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM prod_vend", null);
		
		List<ProdVendVO> lista = new ArrayList<ProdVendVO>();
		
		while(rs.moveToNext()){
			ProdVendVO vo = new ProdVendVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					 rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
			lista.add(vo);
		}

		return lista;
	}
	
	public List<String> getAllLista(){
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM prod_vend", null);
		
		ArrayList<String> lista2= null;
		while(rs.moveToNext()){
			lista2.add(rs.getString(1));
		}
		
		return lista2;
	}
	
	public List<ProdVendVO> getProdutosVenda(String codvend){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM prod_vend where codvend=" + codvend + " order by prod", null);
		
		List<ProdVendVO> lista = new ArrayList<ProdVendVO>();
		
		while(rs.moveToNext()){
			ProdVendVO vo = new ProdVendVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					 rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
			lista.add(vo);
		}

		return lista;
	}
	
	public String getSomaProdutos(String codvend) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT sum(vl_t) as total FROM PROD_VEND where codvend=" + codvend, null);

		String total="0";
		while (rs.moveToNext()) {
			Log.d("PVDao", "codvend " + codvend + " total " + rs.getString(rs.getColumnIndex("total")));
			total = rs.getString(rs.getColumnIndex("total"));
		}
		return total;
	}
	

}