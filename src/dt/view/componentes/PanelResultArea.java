package dt.view.componentes;

import java.awt.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import dt.db.DBManager;
import dt.helper.Mensagens;

/**
 * Classe que define o painel de resultado das consultas e operações
 * @author Victor Araujo
 *
 */
public class PanelResultArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPaneResult;
	private JTextArea outputArea;
	private TableDadosResult tableDadosResult;
	private JPanel panelTableResult;
	private JPanel panelInfResult;
	
	/**
	 * Construtor que configura os componentes no painel
	 */
	public PanelResultArea(){
		super(new BorderLayout());
		
		//Configurando outputArea
		this.outputArea = new JTextArea();
		this.outputArea.setEditable(false);
		
		//Configurando panelInfResult
		this.panelInfResult = new JPanel(new BorderLayout());
		this.panelInfResult.add(new JScrollPane(this.outputArea), BorderLayout.CENTER);
		
		//Configurando panelTableResult
		this.panelTableResult = new JPanel(new BorderLayout());
		
		//Configurando tableDadosResult
		//this.tableDadosResult = new TableDadosResult();
		//this.panelTableResult.add(new JScrollPane(this.tableDadosResult), BorderLayout.CENTER);
		
		//Configurando tabbedPaneResult
		this.tabbedPaneResult = new JTabbedPane();
		this.tabbedPaneResult.addTab("Dados", this.panelTableResult);
		this.tabbedPaneResult.addTab("Mensagem", this.panelInfResult);
		
		this.add(this.tabbedPaneResult, BorderLayout.CENTER);
		this.setBorder(new TitledBorder("Resultado"));
		this.setPreferredSize(new Dimension(800, 200));
	}
	
	/**
	 * Limpa a área de mensagens de resultado do sistema
	 */
	public void limparAreaMensagem(){
		this.outputArea.setText("");
	}
	
	/**
	 * Adicionar mensagem na área de mensagens de resultado
	 * @param mensagem - Nova mensagem do sistema
	 */
	public void addMensagem(String mensagem){
		this.outputArea.setText(this.outputArea.getText() + mensagem);
		this.tabbedPaneResult.setSelectedIndex(1);
	}
	
	/**
	 * Limpa os dados da tabela de resultado
	 */
	public void limparDados(){
		this.tableDadosResult = new TableDadosResult();
		this.panelTableResult.removeAll();
	}
	
	/**
	 * Atualiza os dados retornado pela consulta executada
	 * @param resultSet - Resultado da query que foi executada
	 * @throws SQLException - Exceção que pode ocorrer durante a atualização dos dados
	 */
	@SuppressWarnings("unchecked")
	public void atualizarDados(ResultSet resultSet)
					throws SQLException{
		if(resultSet != null && resultSet.next()){
			Vector columnHeads = new Vector();
			Vector rows = new Vector();			
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++){
				columnHeads.addElement(rsmd.getColumnName(i));
			}
			
			do{
				rows.addElement(DBManager.getNextRow(resultSet, rsmd));
			}while(resultSet.next());
			
			this.tableDadosResult = new TableDadosResult(rows, columnHeads);
			this.panelTableResult.removeAll();
			JScrollPane scrollPane = new JScrollPane(this.tableDadosResult);
			scrollPane.setRowHeaderView(new TableRowNumber(this.tableDadosResult));
			this.panelTableResult.add(scrollPane, BorderLayout.CENTER);
			this.panelTableResult.repaint();
			this.tabbedPaneResult.setSelectedIndex(0);
		}else{
			this.limparDados();
			this.addMensagem(Mensagens.getInstancia().getMensagem("I0014") + "\n\n");
		}		
	}	
}
