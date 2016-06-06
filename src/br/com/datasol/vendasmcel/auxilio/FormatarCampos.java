package br.com.datasol.vendasmcel.auxilio;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FormatarCampos {
	
	public String dataAtualTela(){
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime( new java.util.Date() );  
	    //calendar.add( Calendar.DAY_OF_MONTH , Integer.parseInt(dias) );  
	    
	    String formato = "dd/MM/yyyy";  
		SimpleDateFormat sd = new SimpleDateFormat(formato);   
//	    System.out.println("data " + sd.format(calendar.getTime()) );
	    
	    String data = sd.format(calendar.getTime());
//	    System.out.println("data 1 " + data);
	    return data;
	}
	
	public String dataSalvarSQLite(){
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime( new java.util.Date() );  
	    //calendar.add( Calendar.DAY_OF_MONTH , Integer.parseInt(dias) );  
	    
	    String formato = "yyyy-MM-dd";  
		SimpleDateFormat sd = new SimpleDateFormat(formato);   
//	    System.out.println("data " + sd.format(calendar.getTime()) );
	    
	    String data = sd.format(calendar.getTime());
//	    System.out.println("data 1 " + data);
	    return data;
	}	
	
	public String dataSalvarFirebird1(String dataString){
		String dataAtual1 = null;

		Date data  = new Date();

		//DateTimeFormat formatacao = DateTimeFormat.getFormat("yyyy-MM-dd");
		String formato = "dd.MM.yyyy";  
		SimpleDateFormat sd = new SimpleDateFormat(formato);   
		//data = sd.parse(dataString);

		//dataAtual1 = DateTimeFormat.getMediumDateFormat().format(data);
	    dataAtual1 =  sd.format(dataString);

		return dataAtual1;
	}	
	
	public String dataSalvarFirebird(String data1){
	    Calendar calendar = Calendar.getInstance();  
	    //calendar.setTime( new java.util.Date() );  
	    calendar.setTime( new java.util.Date(data1));
	    
	    String formato = "dd.MM.yyyy";  
		SimpleDateFormat sd = new SimpleDateFormat(formato);   
//	    System.out.println("data " + sd.format(calendar.getTime()) );
	    
	    String data = sd.format(calendar.getTime());
//	    System.out.println("data 1 " + data);
	    return data;
	}	
	
	public String subtrairDeDoisValores(String valor1, String valor2){
		String valor1F = campoNumericoGravar(valor1);
		String valor2F = campoNumericoGravar(valor2);
		
		double v1 = Double.parseDouble(valor1F);
		double v2 = Double.parseDouble(valor2F);
		double resultado = 0;
		
		resultado = v1 - v2;
		
		return String.valueOf(resultado);
	}
	
	public String calcularValorTotalProd(String qt, String valorUnit){
		String qtF = campoNumericoGravar(qt);
		String valorUnitF = campoNumericoGravar(valorUnit);
		
		double v1 = Double.parseDouble(qtF);
		double v2 = Double.parseDouble(valorUnitF);
		double resultado = 0;
		
		resultado = v1 * v2;
		
		return String.valueOf(resultado);
	}

	public String campoMoeda(String valor){
		double valorD = 0;
		valorD = Double.parseDouble(valor);

//		NumberFormat nf = NumberFormat.getCurrencyFormat();
//		String valorFormatado = nf.format(valorD);

//		return valorFormatado;
		return null;
	}

	public String campoMoeda1(String valor){
		double valorD = 0;
		valorD = Double.parseDouble(valor);

//		NumberFormat nf = NumberFormat.getFormat("####0.00");
//		String valorFormatado = nf.format(valorD);

//		return valorFormatado;
		return null;
		
	}

	public String campoNumericoGravar(String valor){
		String valorFormatado;

		if(valor == null || valor.toString().trim().equals("")){  
			valorFormatado = "0.0";	   
		} else { 
			valorFormatado = valor.replace(",", ".");
		}
		return valorFormatado;		
	}
	
	public String dataVencimento(String dias){
	    Calendar calendar = Calendar.getInstance();  
	    calendar.setTime( new java.util.Date() );  
	    calendar.add( Calendar.DAY_OF_MONTH , Integer.parseInt(dias) );  
	    
	    String formato = "dd/MM/yyyy";  
		SimpleDateFormat sd = new SimpleDateFormat(formato);   
//	    System.out.println("data " + sd.format(calendar.getTime()) );
	    
	    String data = sd.format(calendar.getTime());
//	    System.out.println("data 1 " + data);
	    return data;
	}
	
	public String somarDiaaData(String dataI, String dias){
		String formato = "dd/MM/yyyy";
		Date dataD = dataDate(dataI);
//		System.out.println("data 3 ant" + dataD);
		dataD.setDate(dataD.getDate() + Integer.parseInt(dias));          
//		System.out.println("data 4 depois" + dataD);		  
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);   
//		System.out.println("Daqui há ...diass: " + dataFormatada.format(dataD));
		String dataForm = dataFormatada.format(dataD);
		return dataForm;
	}

	public Date dataDate(String data){
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
	    Date dataD = null;
		try {
			dataD = new Date(format.parse(data).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return dataD; 
	}
	
    public static Date formataData(String data) throws Exception {   
        if (data == null || data.equals(""))  
            return null;  
          
        Date date = null;  
        try {  
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            date = (java.util.Date)formatter.parse(data);  
        } catch (ParseException e) {              
            throw e;  
        }  
        return date;  
    }	
    
    public String formatarDataTela(String data){
        SimpleDateFormat in= new SimpleDateFormat("yyyy-MM-dd");  
        SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");  
          
        String result = out.format(data);
        
        return result;
    }
	
	public String periodoHoras(){
		String periodo="";
	    Calendar cal = Calendar.getInstance();  
	    // Entre 00:00 e 09:59 imprimimos "Ciclo 1"  
	    // Entre 10:00 e 15:59 imprimimos "Ciclo 2"  
	    // Entre 16:00 e 23:59 imprimimos "Ciclo 3"  
	    int hora = cal.get (Calendar.HOUR_OF_DAY); 
	  //if (0 <= hora && hora <= 9) --> "Ciclo 1"
	    //else if (10 <= hora && hora <= 15) --> "Ciclo 2"  
	    //else --> "Ciclo 3"	    
	    if (hora >= 6 && hora <= 9){
	    	periodo = "cafe";
	    } else if (hora >= 11 && hora <= 14){
	    	periodo = "almoco";  
	    } else if (hora >= 17 && hora <= 20){
	    	periodo = "janta";  
	    }  
	    return periodo;
	}
	
	public String periodoDias(String dataIString){
		//TODO
//		java.sql.Date begin =rs_espera.getDate("data_sistema"));     
//	     java.sql.Date ebd = date;
/*		
		Date dataI = new Date(dataIString);
		Date dataF = new Date();
	        
	     GregorianCalendar gCalendar = new GregorianCalendar();    
	     //define tempo da data    
	     //gCalendar.setTime(begin.getTime());    
	     gCalendar.setTime(dataI.getTime());
	     //dia do mes    
	     int dyBegin = gCalendar .get(Calendar.DAY_OF_YEAR);    
	     int yearBegin = gCalendar .get(Calendar.YEAR);   
	  
	    GregorianCalendar gcEnd= new GregorianCalendar();    
	     //define tempo da data    
	     gcEnd.setTime(end.getTime());    
	     //dia do mes    
	     int dyEnd = gcEnd.get(Calendar.DAY_OF_YEAR);    
	     int yearEnd = gcEnd.get(Calendar.YEAR);   
	    int diferenca;  
	    if(yearEnd ==yearBegin ){  
	        diferenca = dyEnd -dyBegin ;  
		
*/		
		return null;
	}

}
