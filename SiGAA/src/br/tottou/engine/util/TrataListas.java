package br.tottou.engine.util;


import java.util.List;

public class TrataListas {

	
	public static  <E> List<E> removerElementos(List<E> maior, List<E> menor){
		
		    maior.removeAll(menor);
			
			return maior;
		
	}
	
	
}
