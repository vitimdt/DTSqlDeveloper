package dt.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Classe que representa as mensagens da aplicação
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
	 * Recupera a instância da classe, se ele não foi instanciada será criada
	 * @return Instância da classe
	 */
	public static Mensagens getInstancia(){
		if(instancia == null)
			instancia = new Mensagens();
		
		return instancia;
	}
	
	/**
	 * Método que carrega as mensagens na coleção estática
	 * @throws IOException - Exceção que pode ser gerada caso ocorra algum problema na leitura do arquivo de configuração
	 */
	private void inicializar(){
		colMensagens = new Hashtable<String, String>();
		
		Properties prop = new Properties();
		File file = new File("config/application.properties");
		FileInputStream fis = null;
		
		try{
			fis = new FileInputStream(file);
			
			prop.load(fis);
			
			//lógica para carregar a lista de Mensagens
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
	 * Método que retorna a mensagem passando o código da mensagem
	 * @param codigo - Código da mensagem
	 * @return Retorna a mensagem pelo código que foi passado
	 */
	public String getMensagem(String codigo){
		String msg = "";
		
		if(colMensagens.containsKey(codigo)){
			msg = colMensagens.get(codigo);
		}
		
		return msg;
	}
	
	/**
	 * Método que retorna a mensagem passando o código da mensagem montando com o array de parametros enviados
	 * @param codigo - Código da mensagem
	 * @param params - Array de String que são os parâmetros da mensagem
	 * @return A mensagem do código montada com o parâmetros enviados
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
	 * Recupera a mensagem de erro se ocorreu algum problema na leitura do arquivo de configuração das mensagens
	 * @return Erro ocorrido na leitura das mensagens
	 */
	public String getErroCarregarMensagens(){
		return this.erroCarregarMensagens;
	}
	
	/**
	 * Verifica se ocorreu algum problema na leitura do arquivo de configuração das mensagens
	 * @return Retorna True se ocorreu erro e False se não
	 */
	public boolean ocorreuErroCarregarMensagens(){
		if(this.erroCarregarMensagens != null && !this.erroCarregarMensagens.trim().equals(""))
			return true;
		else
			return false;
	}
}
