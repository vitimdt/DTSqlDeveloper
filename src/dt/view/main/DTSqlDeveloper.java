package dt.view.main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import dt.db.DBManager;
import dt.db.SQL;
import dt.exceptions.DBException;
import dt.exceptions.GlobalException;
import dt.helper.*;
import dt.view.componentes.*;

/**
 * Tela principal do aplicativo
 * @author Victor Araujo
 *
 */
public class DTSqlDeveloper extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager.LookAndFeelInfo looks[];
	private JSplitPane splitPaneVertical; 
	private PanelControle panelControle;
	private PanelInputArea panelInputArea;
	private PanelResultArea panelResultArea;
	
	
	/**
	 * Construtor da classe principal do aplicativo, configura o layout da tela
	 */
	public DTSqlDeveloper(){
		super(Constantes.NOME_APLICACAO + " " + Constantes.VERSAO_APLICACAO);		
	}

	/**
	 * Configura os componentes dependendo do status da conexão passada como parâmetro
	 * @param conectado - Define se conexão esta ativa ou não
	 */
	private void definirStatusConexao(boolean conectado){
		if(conectado){
			DTSqlDeveloper.this.panelControle.getCbxSGBD().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getCbxConexao().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnConectar().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnDesconectar().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getBtnEditarConexao().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnRemoveConexao().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnExecutar().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getChkAutoCommit().setEnabled(true);
			DTSqlDeveloper.this.panelInputArea.setFocusable(true);
		}else{
			DTSqlDeveloper.this.panelControle.getBtnConectar().setFocusable(true);
			DTSqlDeveloper.this.panelControle.getCbxSGBD().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getCbxConexao().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getBtnConectar().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getBtnDesconectar().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnEditarConexao().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getBtnRemoveConexao().setEnabled(true);
			DTSqlDeveloper.this.panelControle.getBtnExecutar().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getChkAutoCommit().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getChkAutoCommit().setSelected(true);
			DTSqlDeveloper.this.panelControle.getBtnCommit().setEnabled(false);
			DTSqlDeveloper.this.panelControle.getBtnRollback().setEnabled(false);
		}
	}
	
	/**
	 * Classe interna para tratamento de evento das teclas pressionadas
	 * @author Victor Araujo
	 *
	 */
	private class KeyListenerHandler extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.isAltDown()){
				switch(e.getKeyCode()){
					case KeyEvent.VK_A:
						if(DTSqlDeveloper.this.panelControle.getBtnEditarConexao().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnEditarConexao().doClick();
						break;
					case KeyEvent.VK_X:
						if(DTSqlDeveloper.this.panelControle.getBtnExecutar().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnExecutar().doClick();
						break;
					case KeyEvent.VK_N:
						if(DTSqlDeveloper.this.panelControle.getBtnAddConexao().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnAddConexao().doClick();
						break;
					case KeyEvent.VK_E:
						if(DTSqlDeveloper.this.panelControle.getBtnRemoveConexao().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnRemoveConexao().doClick();
						break;
					case KeyEvent.VK_C:
						if(DTSqlDeveloper.this.panelControle.getBtnConectar().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnConectar().doClick();
						break;
					case KeyEvent.VK_D:
						if(DTSqlDeveloper.this.panelControle.getBtnDesconectar().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnDesconectar().doClick();
						break;
					case KeyEvent.VK_M:
						if(DTSqlDeveloper.this.panelControle.getBtnCommit().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnCommit().doClick();
						break;
					case KeyEvent.VK_R:
						if(DTSqlDeveloper.this.panelControle.getBtnRollback().isEnabled())
							DTSqlDeveloper.this.panelControle.getBtnRollback().doClick();
						break;
				}				
			}
		}		
	}
	
	/**
	 * Classe interna para tratamento de evento dos botões de controle
	 * @author Victor Araujo
	 *
	 */
	private class ActionListenerHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((JButton)e.getSource()).getName().equals("btnConectar")){
				SGBD sgbd = (SGBD)DTSqlDeveloper.this.panelControle.getCbxSGBD().getSelectedItem();
				Conexao conexao = (Conexao)DTSqlDeveloper.this.panelControle.getCbxConexao().getSelectedItem();
				try{
					DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					DBManager.getInstancia().conectar(sgbd.getNomeDriver(), conexao.getURL(), conexao.getUsuario(), conexao.getSenha());
					DTSqlDeveloper.this.definirStatusConexao(true);
					DTSqlDeveloper.this.panelResultArea.addMensagem(Mensagens.getInstancia().getMensagem("I0001") + "\n\n");
					DTSqlDeveloper.this.panelInputArea.receberFoco();
				}catch(DBException dbException){
					DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
				}finally{
					DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
				}
			}else
				if(((JButton)e.getSource()).getName().equals("btnDesconectar")){
					try{
						DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						
						if(!DBManager.getInstancia().autoCommitHabilitado())
						{
							JOptionPane.showMessageDialog(DTSqlDeveloper.this, Mensagens.getInstancia().getMensagem("I0002"), Constantes.TITULO_TELA_AVISO, JOptionPane.INFORMATION_MESSAGE);
							DBManager.getInstancia().rollback();
						}
						DBManager.getInstancia().fecharConexao();
						
						DTSqlDeveloper.this.definirStatusConexao(false);						
						DTSqlDeveloper.this.panelResultArea.addMensagem(Mensagens.getInstancia().getMensagem("I0003") + "\n\n");
						DTSqlDeveloper.this.panelControle.getBtnConectar().requestFocus();
					}catch(DBException dbException){
						DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
						if(!dbException.conexaoEstabelecida())
							DTSqlDeveloper.this.definirStatusConexao(false);
					}finally{
						DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
					}
				}else
					if(((JButton)e.getSource()).getName().equals("btnExecutar")){
						try{
							SQL sql = new SQL(DTSqlDeveloper.this.panelInputArea.getComando());
							
							if(!sql.getComando().trim().equals("")){
								DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								
								if(sql.getComandos().length == 1 
										&& (sql.getComando().toUpperCase().contains(Constantes.COMANDO_SELECT)
												|| sql.getComando().toUpperCase().contains(Constantes.COMANDO_SHOW_MYSQL)))
								{
									DBManager.getInstancia().executarQuery(sql);									
									DTSqlDeveloper.this.panelResultArea.atualizarDados(DBManager.getInstancia().getResultSet());
									DTSqlDeveloper.this.repaint();
								}
								else
								{
									String result = DBManager.getInstancia().executarOperacao(sql);
									DTSqlDeveloper.this.panelResultArea.addMensagem(result + "\n\n");
								}
							}
						}catch(SQLException sqlException){
							DTSqlDeveloper.this.panelResultArea.addMensagem(sqlException.getMessage() + "\n\n");
						}catch(DBException dbException){
							DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
							if(!dbException.conexaoEstabelecida())
								DTSqlDeveloper.this.definirStatusConexao(false);
						}finally{
							DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
						}
					}else
						if(((JButton)e.getSource()).getName().equals("btnCommit")){
							try{
								DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								
								DBManager.getInstancia().commit();
								
								DTSqlDeveloper.this.panelResultArea.addMensagem(Mensagens.getInstancia().getMensagem("I0004") + "\n\n");								
							}catch(DBException dbException){
								DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
								if(!dbException.conexaoEstabelecida())
									DTSqlDeveloper.this.definirStatusConexao(false);
							}finally{
								DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
							}
						}else
							if(((JButton)e.getSource()).getName().equals("btnRollback")){
								try{
									DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
									
									DBManager.getInstancia().rollback();
									
									DTSqlDeveloper.this.panelResultArea.addMensagem(Mensagens.getInstancia().getMensagem("I0005") + "\n\n");								
								}catch(DBException dbException){
									DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
									if(!dbException.conexaoEstabelecida())
										DTSqlDeveloper.this.definirStatusConexao(false);
								}finally{
									DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
								}
							}else								
								if(((JButton)e.getSource()).getName().equals("btnAddConexao")){
									DialogNovaConexao dialogNovaConexao = new DialogNovaConexao(DTSqlDeveloper.this, true);
									
									if(dialogNovaConexao.getResult() == DialogNovaConexao.RESULT_OK){
										try{
											Conexao.adicionarConexao(dialogNovaConexao.getConexao());
											DTSqlDeveloper.this.panelControle.carregarComboConexoes();
										}catch(GlobalException globalException){
											JOptionPane.showMessageDialog(DTSqlDeveloper.this, globalException.getMessage(), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
										}
									}
								}else
									if(((JButton)e.getSource()).getName().equals("btnEditarConexao")){
										DialogNovaConexao dialogNovaConexao = new DialogNovaConexao(DTSqlDeveloper.this, true, (Conexao)DTSqlDeveloper.this.panelControle.getCbxConexao().getSelectedItem());
										
										if(dialogNovaConexao.getResult() == DialogNovaConexao.RESULT_OK)
											Conexao.alterarConexao(dialogNovaConexao.getConexao());
									}else
										if(((JButton)e.getSource()).getName().equals("btnRemoveConexao")){
											if(JOptionPane.showConfirmDialog(DTSqlDeveloper.this,
													Mensagens.getInstancia().getMensagem("I0013", new String[]{ DTSqlDeveloper.this.panelControle.getCbxConexao().getSelectedItem().toString() }), Constantes.TITULO_TELA_COFIRMACAO, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
												Conexao.removerConexao((Conexao)DTSqlDeveloper.this.panelControle.getCbxConexao().getSelectedItem());
												DTSqlDeveloper.this.panelControle.carregarComboConexoes();
											}
										}
		}
		
	}
	
	/**
	 * Classe interna para tratamento de evento da tela
	 * @author Victor Araujo
	 *
	 */
	private class WindowListenerHandler extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent e) {
			try{
				if(!DBManager.getInstancia().conexaoFechada())
				{
					if(!DBManager.getInstancia().autoCommitHabilitado())
					{
						JOptionPane.showMessageDialog(DTSqlDeveloper.this, Mensagens.getInstancia().getMensagem("I0006"), Constantes.TITULO_TELA_AVISO, JOptionPane.INFORMATION_MESSAGE);
						DBManager.getInstancia().rollback();
					}
					DBManager.getInstancia().fecharConexao();					
				}				
			}catch(DBException dbException){
				JOptionPane.showMessageDialog(DTSqlDeveloper.this, dbException.getMessage(), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
			}
			
			//Salvando alterações nas conexões
			if(Conexao.getListaConexoesAlterada()){
				try{
					Conexao.salvarConexoes();
				}catch(IOException ioException){
					JOptionPane.showMessageDialog(DTSqlDeveloper.this, Mensagens.getInstancia().getMensagem("E0022"), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
				}
			}
		}		
	}
	
	/**
	 * Classe interna para tratamento de evento do checkbox de AutoCommit da conexão
	 * @author Victor Araujo
	 *
	 */
	private class AutoCommitActionListenerHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){			
			if(DTSqlDeveloper.this.panelControle.getChkAutoCommit().isSelected()){
				try{
					DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					DBManager.getInstancia().setAutoCommit(true);
					
					DTSqlDeveloper.this.panelControle.getBtnCommit().setEnabled(false);
					DTSqlDeveloper.this.panelControle.getBtnRollback().setEnabled(false);
				}catch(DBException dbException){
					DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
					DTSqlDeveloper.this.panelControle.getChkAutoCommit().setSelected(false);
					if(!dbException.conexaoEstabelecida())
						DTSqlDeveloper.this.definirStatusConexao(false);
				}finally{
					DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
				}
			}else{
				try{
					DTSqlDeveloper.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					DBManager.getInstancia().setAutoCommit(false);
					
					DTSqlDeveloper.this.panelControle.getBtnCommit().setEnabled(true);
					DTSqlDeveloper.this.panelControle.getBtnRollback().setEnabled(true);
				}catch(DBException dbException){
					DTSqlDeveloper.this.panelResultArea.addMensagem(dbException.getMessage() + "\n\n");
					DTSqlDeveloper.this.panelControle.getChkAutoCommit().setSelected(true);
					if(!dbException.conexaoEstabelecida())
						DTSqlDeveloper.this.definirStatusConexao(false);
				}finally{
					DTSqlDeveloper.this.setCursor(Cursor.getDefaultCursor());
				}
			}
		}
		
	}

	@Override
	/**
	 * Implementação do método da interface Runnable que carrega os componentes da tela.
	 */
	public void run() {
		this.looks = UIManager.getInstalledLookAndFeels();		
		//Tenta atualizar layout das janelas para padrões definidos pela plataforma
		try{
			UIManager.setLookAndFeel(this.looks[1].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		//Configurando splitPaneVertical
		this.splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		//Configurando panelControle
		this.panelControle = new PanelControle();
		this.panelControle.getBtnConectar().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnDesconectar().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnAddConexao().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnEditarConexao().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnRemoveConexao().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnExecutar().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnCommit().addActionListener(new ActionListenerHandler());
		this.panelControle.getBtnRollback().addActionListener(new ActionListenerHandler());
		this.panelControle.getChkAutoCommit().addActionListener(new AutoCommitActionListenerHandler());
		this.panelControle.getCbxSGBD().addKeyListener(new KeyListenerHandler());
		this.panelControle.getCbxConexao().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnConectar().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnDesconectar().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnAddConexao().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnEditarConexao().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnRemoveConexao().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnExecutar().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnCommit().addKeyListener(new KeyListenerHandler());
		this.panelControle.getBtnRollback().addKeyListener(new KeyListenerHandler());
		this.panelControle.getChkAutoCommit().addKeyListener(new KeyListenerHandler());
		container.add(this.panelControle, BorderLayout.NORTH);
		
		//Configurando panelInputArea
		this.panelInputArea = new PanelInputArea();
		this.panelInputArea.setPreferredSize(new Dimension(800, 300));
		this.panelInputArea.setKeyListener(new KeyListenerHandler());
		this.splitPaneVertical.add(this.panelInputArea);
		
		//Configurando panelResultArea
		this.panelResultArea = new PanelResultArea();
		this.splitPaneVertical.add(this.panelResultArea);
		
		container.add(this.splitPaneVertical, BorderLayout.CENTER);
		
		//Configurando propriedades da tela principal
		this.setIconImage(new ImageIcon("img/database_process.png").getImage());
		this.setSize(1100, 700);
		this.setMinimumSize(new Dimension(930, 600));
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowListenerHandler());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
