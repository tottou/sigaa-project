package br.tottou.engine.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ajuste {
	
	public static String getDataHora(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss");
		return sdf.format(data)+" - "+sdf2.format(data);
	}
	
	public static String getData(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		return sdf.format(data);
	}
	
	public static String getHora(Date data) {		
		SimpleDateFormat sdf2 = new SimpleDateFormat("kk:mm:ss");
		return sdf2.format(data);
	}

}
