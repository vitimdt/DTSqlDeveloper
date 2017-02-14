package dt.db;

/**
 * Classe que vai conter os comandos SQLs que serão executados
 * @author Victor Araujo
 *
 */
public class SQL {
	private String comando;
	
	/**
	 * Construtor que recebe o comando SQL como parâmetro
	 * @param comando - Comando SQL para ser executado posteriormente
	 */
	public SQL(String comando){
		this.comando = comando;
	}
	
	/**
	 * Retorna o array de comandos separados pelo caracter ";"
	 * @return Array de <code>java.lang.String</code> que representa cada comando
	 */
	public String[] getComandos(){
		return this.comando.split(";");
	}
	
	/**
	 * Retorna o comando SQL para ser executado
	 * @return É uma <code>java.lang.String</code> que representa o comando SQL
	 */
	public String getComando(){
		return this.comando;
	}
	
	/**
	 * Altera o comando SQL que será executado
	 * @param comando - Novo comando SQL
	 */
	public void setComando(String comando){
		this.comando = comando;
	}
}
