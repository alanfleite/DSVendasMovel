package br.com.datasol.vendasm.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper{

	private static String dbName = "datasol.db";
	
	private static String sqlCliente = "CREATE TABLE IF NOT EXISTS CAD_CLI (cod integer primary key autoincrement,"     
    + " usuario varchar(90), razao varchar(90), ende varchar(80), ende_num varchar(15), fone varchar(50), cel varchar(13),"
    + " bairro varchar(25), cidade varchar(90), uf varchar(2), cnpj varchar(18), rg varchar(25), inscest varchar(20),"
    + " resp varchar(70), email varchar(70), contato varchar(80), cpf varchar(25), atraso varchar(1), atrasoinfo varchar(160),"
    + " atrasovl varchar(15), campo1 varchar(90), campo2 varchar(90));";

	private static String sqlEstoque = "CREATE TABLE IF NOT EXISTS ESTOQ (cod integer primary key autoincrement,"     
			+ " prod varchar(90), codprod varchar(20), q1 decimal(12,4), vt decimal(12,2), unid varchar(15));";
	
	private static String sqlRec = "CREATE TABLE IF NOT EXISTS REC (cod integer primary key autoincrement,"
			+ " fantasia varchar(120), razao varchar(90), codcli varchar(15), ende varchar(80)," 
			+ " cida varchar(45), uf varchar(2), tot decimal(15,2), totg decimal(15,2), cpf varchar(14)," 
            + "cnpj varchar(18), condpg varchar(35), dataems date, vendedor varchar(90), codvend varchar(15),"
			+ "sincrinozado varchar(1), campo1 varchar(90), campo2 varchar(90));";
    		
    private static String sqlProdvend = "CREATE TABLE IF NOT EXISTS PROD_VEND (cod integer primary key autoincrement,"
    	      + " prod varchar(80), q1 decimal(12,4), vl_u decimal(12,4), vl_t decimal(15,2), codvend varchar(15),"
    	      + " data date, unid varchar(15), codprod varchar(20), codcli varchar(20), vendedor varchar(90),"
    	      + " sincrinozado varchar(1), campo1 varchar(90), campo2 varchar(90));";

    private static String sqlVendedor = "CREATE TABLE IF NOT EXISTS VENDEDOR (cod integer primary key autoincrement,"
  	      + " nome varchar(90), campo1 varchar(90), campo2 varchar(90));";
		  
    private static String sqlConfig = "CREATE TABLE IF NOT EXISTS CONFIG (id integer primary key autoincrement,"
    	      + " url varchar(90), usuario varchar(50), senha varchar(90), filtrocliente varchar(15), versaonova varchar(10),"
    		  + " versaoant varchar(10), campo1 varchar(90), campo2 varchar(90);";
       
	String[] statements = new String[]{sqlCliente, sqlEstoque, sqlRec, sqlProdvend, sqlVendedor, sqlConfig};
	
	private static int version = 1;
	
	public DB(Context ctx) {
		super(ctx, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(sqlCLIENTE + sqlREC + sqlPRODVEND);
		//db.execSQL(sqlCliente);
//		db.execSQL(sqlEstoque);
		for(String sql : statements){
		    db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		//String sqlUpdate = "ALTER TABLE rec ADD COLUMN sinc varchar(1)";
		String sqlUpdate = "ALTER TABLE config ADD COLUMN sinc varchar(1)";
		
		//db.execSQL(sqlUpdate);
/*		
		for(String sql1 : statementsUpdate){
		    db.execSQL(sql1);
		}
*/		
		
		/*if(oldVersion == 1){
			if(newVersion == 2){
				db.execSQL("DROP TABLE logs");
				db.execSQL("CREATE TABLE clientes..");
			}
		}*/
	}
}
