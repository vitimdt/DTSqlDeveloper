package dt.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Classe que representa as mensagens da aplica��o
 * @author Victor Araujo
 *
 */
public class Mensagens{
	private static Mensagens instancia;
	private Hashtable<String, String> colMensagens;
	private String erroCarregarMensagens;
	
	/**
	 * Construtor privado da classe
	 */
	private Mensagens(){
		this.inicializar();
	}
	
	/**
	 * Recupera a inst�ncia da classe, se ele n�o foi instanciada ser� criada
	 * @return Inst�ncia da classe
	 */
	public static Mensagens getInstancia(){
		if(instancia == null)
			instancia = new Mensagens();
		
		return instancia;
	}
	
	/**
	 * M�todo que carrega as mensagens na cole��o est�tica
	 * @throws IOException - Exce��o que pode ser gerada caso ocorra algum problema na leitura do arquivo de configura��o
	 */
	private void inicializar(){
		colMensagens = new Hashtable<String, String>();
		
		Properties prop = new Properties();
		File file = new File("config/application.properties");
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(file);
			
			prop.load(fis);
			
			//l�gica para carregar a lista de Mensagens
			if(!prop.isEmpty()){
				Enumeration<Object> keys = prop.keys();
				do{
					String key = keys.nextElement().toString();
					colMensagens.put(key, prop.getProperty(key));
				}while(keys.hasMoreElements());
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
			this.erroCarregarMensagens = ioException.getMessage();
		}		
	}
	
	/**
	 * M�todo que retorna a mensagem passando o c�digo da mensagem
	 * @param codigo - C�digo da mensagem
	 * @return Retorna a mensagem pelo c�digo que foi passado
	 */
	public String getMensagem(String codigo){
		String msg = "";
		
		if(colMensagens.containsKey(codigo)){
			msg = colMensagens.get(codigo);
		}
		
		return msg;
	}
	
	/**
	 * M�todo que retorna a mensagem passando o c�digo da mensagem montando com o array de parametros enviados
	 * @param codigo - C�digo da mensagem
	 * @param params - Array de String que s�o os par�metros da mensagem
	 * @return A mensagem do c�digo montada com o par�metros enviados
	 */
	public String getMensagem(String codigo, String[] params){
		String msg = "";
		
		if(colMensagens.containsKey(codigo)){
			msg = colMensagens.get(codigo);
		}
		if(!msg.equals("") && params != null && params.length > 0){
			for(int pos = 0; pos < params.length; pos++){
				msg = msg.replace("{" + pos + "}" , params[pos].toString());
			}
		}
		
		return msg;
	}
	
	/**
	 * Recupera a mensagem de erro se ocorreu algum problema na leitura do arquivo de configura��o das mensagens
	 * @return Erro ocorrido na leitura das mensagens
	 */
	public String getErroCarregarMensagens(){
		return this.erroCarregarMensagens;
	}
	
	/**
	 * Verifica se ocorreu algum problema na leitura do arquivo de configura��o das mensagens
	 * @return Retorna True se ocorreu erro e False se n�o
	 */
	public boolean ocorreuErroCarregarMensagens(){
		if(this.erroCarregarMensagens != null && !this.erroCarregarMensagens.trim().equals(""))
			return true;
		else
			return false;
	}
}
