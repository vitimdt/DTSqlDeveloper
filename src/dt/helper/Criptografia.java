package dt.helper;

import sun.misc.*;


/**
 * Codifica e decodifica uma string para mascarar a verdadeiro texto
 * @author Victor Araujo
 *
 */
public class Criptografia {
	
	/**
	 * Codifica a string para mascarar o conteúdo
	 * @param texto - String que será codificada
	 * @return String codificada
	 */
	public static String codifica(String texto){
		String retVal = "";
		try {
			String textoCripto = new BASE64Encoder().encode(texto.getBytes());		    
			retVal = textoCripto;
		}catch(Exception ex){
		    ex.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Decodifica a string para mascarar o conteúdo
	 * @param texto - String que será decodificada
	 * @return String decodificada
	 */
	public static String decodifica(String texto){
		String retVal = "";
		try {
			String textoDecripto = new String(new BASE64Decoder().decodeBuffer(texto));		    
			retVal = textoDecripto;
		}catch(Exception ex){
		    ex.printStackTrace();
		}
		return retVal;
	}
}
