package dt.view.componentes;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Tabela que ser� criada com os dados de resultado das consultas que forem executadas no BD
 * @author Victor Araujo
 *
 */
public class TableDadosResult extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor da classe para inicializar as v�riaveis
	 */
	public TableDadosResult(){
		super();
		
		this.setRowSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	/**
	 * Construtor da classe para inicializar as v�riaveis, passando o n�mero de linhas e colunas
	 */
	public TableDadosResult(int numLinhas, int numColunas){
		super(numLinhas, numColunas);
		
		this.setRowSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//definindo um tamanho inicial para as colunas
		for(int pos = 0; pos < numColunas; pos++){
			TableColumn col = this.getColumnModel().getColumn(pos);
			col.setPreferredWidth(150);
		}
	}
	
	/**
	 * Construtor da classe para inicializar as v�riaveis, passando o linhas e colunas j� criadas
	 */
	@SuppressWarnings("unchecked")
	public TableDadosResult(Vector linhas, Vector colunas){
		super(linhas, colunas);
		
		this.setRowSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//definindo um tamanho inicial para as colunas
		for(int pos = 0; pos < colunas.size(); pos++){
			TableColumn col = this.getColumnModel().getColumn(pos);
			col.setPreferredWidth(150);
		}
	}
	
	/**
	 * M�todo sobrescrito da super classe para validar de C�lulas s�o edit�veis ou n�o
	 * @param row - Linha da tabela
	 * @param column - Coluna da tabela
	 * @return Retorna true se for edit�vel e false se n�o for
	 */
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
}
