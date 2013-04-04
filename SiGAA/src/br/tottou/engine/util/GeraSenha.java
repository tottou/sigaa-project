package br.tottou.engine.util;

import java.util.Random;

public class GeraSenha {

	  protected Random  m_generator = new Random ();

	  public static final String  DIGITS               = "0123456789";
	  public static final String  LOCASE_CHARACTERS    = "abcdefghijklmnopqrstuvwxyz";
	  public static final String  UPCASE_CHARACTERS    = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	  public static final String  PRINTABLE_CHARACTERS = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS;
	  
	  public String  gerarSenha (int passLength) throws Exception  {
		  String chars = PRINTABLE_CHARACTERS;
	    if (passLength > chars.length()) {
	      throw new Exception ("Password generation is imposible");
	    }
	    char[] availableChars = chars.toCharArray();
	    int availableCharsLeft = availableChars.length;
	    StringBuffer  temp = new StringBuffer (passLength);
	    for (int i = 0; i < passLength; i++) {
	      int pos = (int) (availableCharsLeft * m_generator.nextDouble());
	      temp.append(availableChars[pos]);
	      availableChars[pos] = availableChars[availableCharsLeft - 1];
	      --availableCharsLeft;
	    }
	    return String .valueOf(temp);
	  }
}
