package dt.view.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * Tabela que marca as numeração das linhas baseada na tabela principal
 * @author Victor Araujo
 *
 */
public class TableRowNumber extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablePrincipal;
	
	/**
	 * Construtor que cria a table com a numeração das linhas baseado na table principal passada como parâmetro
	 * @param tablePrincipal - Table principal
	 */
	public TableRowNumber(JTable tablePrincipal){
		super();
		this.tablePrincipal = tablePrincipal;
		this.setAutoCreateColumnsFromModel( false );
		this.setModel( this.tablePrincipal.getModel() );
		this.setSelectionModel( this.tablePrincipal.getSelectionModel() );
		this.setAutoscrolls( false );
		 
		this.addColumn( new TableColumn() );
		this.getColumnModel().getColumn(0).setCellRenderer(this.tablePrincipal.getTableHeader().getDefaultRenderer());
		this.getColumnModel().getColumn(0).setPreferredWidth(70);
		this.setPreferredScrollableViewportSize(getPreferredSize());
		this.addKeyListener(new KeyListenerHandler());
	}
	
	/**
	 * Método sobrescrito da super classe para validar de Células são editáveis ou não
	 * @param row - Linha da tabela
	 * @param column - Coluna da tabela
	 * @return Retorna true se for editável e false se não for
	 */
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	/**
	 * Método sobrescrito da super classe que retorna o índice da linha corrente
	 * @param row - Linha da tabela
	 * @param column - Coluna da tabela
	 * @return Retorna o índice da linha corrente
	 */
	@Override
	public Object getValueAt(int row, int column)
	{
		return new Integer(row + 1);
	}
	
	/**
	 * Método sobrescrito da super classe que retorna a altura da linha corrente
	 * @param row - Linha da tabela
	 * @return Retorna a altura da linha corrente
	 */
	@Override
	public int getRowHeight(int row)
	{
		return this.tablePrincipal.getRowHeight();
	}
	
	/**
	 * Classe interna para tratamento de evento das teclas pressionadas
	 * @author Victor Araujo
	 *
	 */
	private class KeyListenerHandler extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				TableRowNumber.this.tablePrincipal.requestFocus();
			}
		}
		
	}
}
