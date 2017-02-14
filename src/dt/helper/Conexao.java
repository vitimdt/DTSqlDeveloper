package dt.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import dt.exceptions.GlobalException;

/**
 * Classe que representa a conex�o com o BD
 * @author Victor Araujo
 *
 */
public class Conexao extends Object{
	private static Hashtable<String, Conexao> listaConexoes;
	private static boolean listaAlterada;
	private String nome;
	private String sgbd;
	private String url;
	private String usuario;
	private String senha;
	
	/**
	 * Construtor que inicializa os atributos da classe
	 * @param nome - Nome da conex�o
	 * @param nomeBD - Nome do banco de dados
	 * @param usuario - Nome do usu�rio do banco de dados
	 * @param senha  - Senha do banco de dados
	 * @param codificarSenha - Define se a senha ser� criptografada ou n�o
	 */
	public Conexao(String nome, String sgbd, String url, String usuario, String senha, boolean codificarSenha){
		this.nome = nome;
		this.sgbd = sgbd;
		this.url = url;
		this.usuario = usuario;
		if(codificarSenha)
			this.senha = Criptografia.codifica(senha);
		else
			this.senha = senha;
	}
	
	/**
	 * M�todo est�tico que carrega as Conex�es de um arquivo Properties
	 * @throws IOException
	 */
	public static void carregarConex�es()
							throws IOException{
		listaConexoes = new Hashtable<String, Conexao>();
		listaAlterada = false;
		File file = new File("config/conexao.properties");
		Properties prop = new Properties();
		FileInputStream fis = null;		
		try{
			fis = new FileInputStream(file);
			
			prop.load(fis);
			
			//l�gica para carregar a lista de SGBDs			
			int quantidadeConexoes = Integer.parseInt(prop.getProperty("conexao.contador"));
			for(int pos = 0; pos < quantidadeConexoes; pos++){
				listaConexoes.put(prop.getProperty("conexao.sgbd_" + pos).toUpperCase() + "_" + prop.getProperty("conexao.nome_" + pos).toUpperCase() ,
												new Conexao(prop.getProperty("conexao.nome_" + pos), 
											  	prop.getProperty("conexao.sgbd_" + pos),
											  	prop.getProperty("conexao.url_" + pos),
											  	prop.getProperty("conexao.username_" + pos),
											  	prop.getProperty("conexao.password_" + pos),
											  	false));
			}
			
			fis.close();			
		}catch(IOException ioException){
			ioException.printStackTrace();
			throw ioException;
		}
	}
	
	/**
	 * M�todo est�tico que salva as conex�es no arquivo de configura��o de conex�es
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void salvarConexoes()
							throws IOException{
		File file = new File("config/conexao.properties");
		Properties prop = new Properties();
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(file);
			prop.put("conexao.contador", String.valueOf(listaConexoes.size()));
			prop.save(fos, "Contador");
			
			prop.clear();
			int cont = 0;
			for(Conexao conexao: listaConexoes.values()){
				prop.put("conexao.nome_" + cont, conexao.nome);
				prop.put("conexao.sgbd_" + cont, conexao.sgbd);
				prop.put("conexao.url_" + cont, conexao.url);
				prop.put("conexao.username_" + cont, conexao.usuario);
				prop.put("conexao.password_" + cont, conexao.senha);
				cont++;
			}
			prop.save(fos, "Lista de Conexoes");			
		}catch(IOException ioException){
			ioException.printStackTrace();
			throw ioException;
		}		
	}
	
	/**
	 * Retorna o nome da conex�o
	 * @return Nome da conex�o
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Altera o nome da conex�o
	 * @param nome - Novo nome da conex�o
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Retorna o nome do sgbd do banco de dados
	 * @return Nome do sgbd do banco de dados
	 */
	public String getSgbd() {
		return sgbd;
	}

	/**
	 * Altera o nome do sgbd do banco de dados
	 * @param sgbd Nova nome do sgbd do banco de dados
	 */
	public void setSgbd(String sgbd) {
		this.sgbd = sgbd;
	}

	/**
	 * Retorna a URL do banco de dados
	 * @return Nome da URL do banco de dados
	 */
	public String getURL() {
		return this.url;
	}	

	/**
	 * Altera a URL do banco de dados
	 * @param nomeBD - Novo da URL do banco de dados
	 */
	public void setURL(String url) {
		this.url = url;
	}
	
	/**
	 * Retorna o nome do usu�rio do banco de dados
	 * @return Nome do usu�rio do banco de dados
	 */
	public String getUsuario() {
		return this.usuario;
	}
	
	/**
	 * Altera o nome de usu�rio do banco de dados
	 * @param usuario - Novo nome de usu�rio do banco de dados
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Retorna a senha do banco de dados decriptografando
	 * @return Senha do banco de dados decriptografada
	 */
	public String getSenha() {
		return Criptografia.decodifica(this.senha);
	}

	/**
	 * Altera a senha do banco de dados criptografando
	 * @param senha - Nova senha do banco de dados que ser� criptograda
	 */
	public void setSenha(String senha) {
		this.senha = Criptografia.codifica(senha);
	}
	
	/**
	 * Retorna a informa��o se a lista de conex�es foi alterada ou n�o
	 * @return Retorna True se a lista foi alterada e False se n�o
	 */
	public static boolean getListaConexoesAlterada(){
		return listaAlterada;
	}
	
	/**
	 * Verifica se j� existe uma conex�o igual na lista de conex�es do sistema
	 * @param nomeConexao - Nome da conex�o
	 * @param nomeSGBD - Nome do SGBD
	 * @return Retorna True se existir a conex�o na lista de conex�es do sistema e False se n�o
	 */
	public static boolean existeConexao(String nomeConexao, String nomeSGBD){
		if(listaConexoes.containsKey(nomeSGBD.toUpperCase() + "_" +  nomeConexao.trim().toUpperCase())){
			return true;
		}
		return false;
	}
	
	/**
	 * Adiciona uma nova conex�o � lista de conex�es
	 * @param conexao - Nova conex�o
	 * @throws GlobalException - Exce��o gerada se j� existir uma conex�o igual na lista
	 */
	public static void adicionarConexao(Conexao conexao) throws GlobalException{
		if(existeConexao(conexao.getNome(), conexao.getSgbd()))
			throw new GlobalException(Mensagens.getInstancia().getMensagem("E0021"), "E0021");
		listaConexoes.put(conexao.getSgbd().toUpperCase() + "_" + conexao.getNome().toUpperCase(), conexao);
		listaAlterada = true;
	}
	
	/**
	 * Altera uma nova conex�o � lista de conex�es
	 * @param conexao - Conex�o alterada
	 */
	public static void alterarConexao(Conexao conexao){
		listaConexoes.get(conexao.getSgbd().toUpperCase() + "_" + conexao.getNome().toUpperCase()).setURL(conexao.getURL());
		listaConexoes.get(conexao.getSgbd().toUpperCase() + "_" + conexao.getNome().toUpperCase()).setUsuario(conexao.getUsuario());
		listaConexoes.get(conexao.getSgbd().toUpperCase() + "_" + conexao.getNome().toUpperCase()).setSenha(conexao.getSenha());
		listaAlterada = true;
	}
	
	/**
	 * Remove a conex�o passada como par�metro da lista de conex�es
	 * @param conexao - Conex�o que ser� removida
	 */
	public static void removerConexao(Conexao conexao){
		listaConexoes.remove(conexao.getSgbd().toUpperCase() + "_" + conexao.getNome().toUpperCase());
		listaAlterada = true;
	}

	/**
	 * M�todo est�tico que retorna a lista de Conex�es que deve ser previamente carregada
	 * @param sgbd - Nome do SGBD do banco de dados para retornar apenas as conex�es do mesmo sgbd 
	 * @return Lista de Conex�es
	 */
	public static ArrayList<Conexao> getListaConexoes(String sgbd) {
		ArrayList<Conexao> listaRet = new ArrayList<Conexao>();
		
		for(Conexao con: listaConexoes.values())
			if(con.getSgbd().toUpperCase().equals(sgbd.toUpperCase()))
				listaRet.add(con);
		
		return listaRet;
	}
	
	/**
	 * Sobreescrevendo m�todo da superclasse que retorna o nome da inst�ncia
	 */
	@Override	
	public String toString() {
		return this.nome;
	}
}
