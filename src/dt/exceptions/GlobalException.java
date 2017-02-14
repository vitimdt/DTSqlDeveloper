package dt.exceptions;

/**
 * Classe de exceção global da aplicação
 * @author Victor Araujo
 *
 */
public class GlobalException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	
	/**
	 * Construtor da classe GlobalException para registrar problema ocorrido
	 * @param message - Mensagem que ocasionou o problema
	 */
	public GlobalException(String mensagem){
		super(mensagem);
		this.codigo = "E0000";
	}
	
	/**
	 * Construtor da classe GlobalException para registrar problema ocorrido
	 * @param message - Mensagem que ocasionou o problema
	 * @param codigo - Código da mensagem tratada que ocasionou o problema
	 */
	public GlobalException(String mensagem, String codigo){
		super(mensagem);
		this.codigo = codigo;
	}

	/**
	 * Retorna o código da mensagem da exceção
	 * @return String que representa o código da mensagem
	 */
	public String getCodigo() {
		return codigo;
	}
}
