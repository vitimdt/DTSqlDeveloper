package dt.exceptions;

/**
 * Classe de exce��o para representar problemas ocorridos na camada de persist�ncia
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
	 * @param codigo - C�digo da mensagem tratada que ocasionou o problema
	 */
	public DBException(String mensagem, String codigo){
		super(mensagem, codigo);
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando um status se a conex�o
	 * est� estabelecida
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - C�digo da mensagem tratada que ocasionou o problema
	 * @param conexaoEstabelecida - Define se a conex�o est� estabelecida ou n�o
	 */
	public DBException(String mensagem, String codigo, boolean conexaoEstabelecida){
		super(mensagem, codigo);
		this.conexaoEstabelecida = conexaoEstabelecida;
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando um status se a conex�o
	 * est� estabelecida e passando outra exce��o que representa a exce��o inicial que ocorreu
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - C�digo da mensagem tratada que ocasionou o problema
	 * @param excecaoInicial - Exce��o que originou o problema
	 */
	public DBException(String mensagem, String codigo, Exception excecaoInicial){
		super(mensagem, codigo);
		this.excecaoInicial = excecaoInicial;
	}
	
	/**
	 * Construtor da classe DBException para registrar problema ocorrido e passando outra exce��o que 
	 * representa a exce��o inicial que ocorreu
	 * @param mensagem - Mensagem que ocasionou o problema
	 * @param codigo - C�digo da mensagem tratada que ocasionou o problema
	 * @param conexaoEstabelecida - Define se a conex�o est� estabelecida ou n�o
	 * @param excecaoInicial - Exce��o que originou o problema
	 */
	public DBException(String mensagem, String codigo, boolean conexaoEstabelecida, Exception excecaoInicial){
		super(mensagem, codigo);
		this.conexaoEstabelecida = conexaoEstabelecida;
		this.excecaoInicial = excecaoInicial;
	}
	
	/**
	 * Verifica se apesar do erro a conex�o com a base de dados continua ativa
	 * @return
	 */
	public boolean conexaoEstabelecida(){
		return this.conexaoEstabelecida;
	}
	
	/**
	 * M�todo acessor que retorna a exce��o que originou o problema
	 * @return
	 */
	public Exception getExcecaoInicial(){
		return this.excecaoInicial;
	}
}
