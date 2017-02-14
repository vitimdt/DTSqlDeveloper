package dt.exceptions;

/**
 * Classe de exceção para representar problemas ocorridos na camada de persistência
 * @author Victor Araujo
 *
 */
public class DBException extends GlobalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean conexaoEstabelecida;
	private Exception excecaoInicial;
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido
	 * @param message - Mensagem que ocasionou o problema
	 * @param codigo - Código da mensagem tratada que ocasionou o problema
	 */
	public DBException(String mensagem, String codigo){
		super(mensagem, codigo);
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando um status se a conexão
	 * está estabelecida
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - Código da mensagem tratada que ocasionou o problema
	 * @param conexaoEstabelecida - Define se a conexão está estabelecida ou não
	 */
	public DBException(String mensagem, String codigo, boolean conexaoEstabelecida){
		super(mensagem, codigo);
		this.conexaoEstabelecida = conexaoEstabelecida;
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando um status se a conexão
	 * está estabelecida e passando outra exceção que representa a exceção inicial que ocorreu
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - Código da mensagem tratada que ocasionou o problema
	 * @param excecaoInicial - Exceção que originou o problema
	 */
	public DBException(String mensagem, String codigo, Exception excecaoInicial){
		super(mensagem, codigo);
		this.excecaoInicial = excecaoInicial;
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando outra exceção que 
	 * representa a exceção inicial que ocorreu
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - Código da mensagem tratada que ocasionou o problema
	 * @param conexaoEstabelecida - Define se a conexão está estabelecida ou não
	 * @param excecaoInicial - Exceção que originou o problema
	 */
	public DBException(String mensagem, String codigo, boolean conexaoEstabelecida, Exception excecaoInicial){
		super(mensagem, codigo);
		this.conexaoEstabelecida = conexaoEstabelecida;
		this.excecaoInicial = excecaoInicial;
	}
	
	/**
	 * Verifica se apesar do erro a conexão com a base de dados continua ativa
	 * @return
	 */
	public boolean conexaoEstabelecida(){
		return this.conexaoEstabelecida;
	}
	
	/**
	 * Método acessor que retorna a exceção que originou o problema
	 * @return
	 */
	public Exception getExcecaoInicial(){
		return this.excecaoInicial;
	}
}
