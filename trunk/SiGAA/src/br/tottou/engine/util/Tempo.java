package br.tottou.engine.util;

import java.util.Calendar;
import java.util.Date;

public class Tempo {
	
	public static int calcIdade(Date data) {
		
		Calendar agora = Calendar.getInstance();
		Calendar nasc = Calendar.getInstance();
		nasc.setTime(data);
		if (nasc.after(agora)) {
		  throw new IllegalArgumentException("Can't be born in the future");
		}
		int year1 = agora.get(Calendar.YEAR);
		int year2 = nasc.get(Calendar.YEAR);
		int age = year1 - year2;
		int month1 = agora.get(Calendar.MONTH);
		int month2 = nasc.get(Calendar.MONTH);
		if (month2 > month1) {
		  age--;
		} else if (month1 == month2) {
		  int day1 = agora.get(Calendar.DAY_OF_MONTH);
		  int day2 = nasc.get(Calendar.DAY_OF_MONTH);
		  if (day2 > day1) {
		    age--;
		  }
		}
		return age;
	}

}
