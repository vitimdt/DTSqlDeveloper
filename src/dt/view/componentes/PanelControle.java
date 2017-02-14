package dt.view.componentes;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import dt.helper.Conexao;
import dt.helper.Constantes;
import dt.helper.Mensagens;
import dt.helper.SGBD;

/**
 * Classe que define o painel de controle da aplicação, onde será controlado as conexões e os botões de controle
 * @author Victor Araujo
 *
 */
public class PanelControle extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblSGBD,  lblConexao;
	private JButton btnConectar, btnDesconectar, btnCommit, btnEditarConexao, 
					btnRollback, btnAddConexao, btnRemoveConexao, btnExecutar;
	private JCheckBox chkAutoCommit;
	private JComboBox cbxSGBD, cbxConexao;
	
	/**
	 * Construtor para criar o Layout do painel de controle
	 */
	public PanelControle(){
		super(new BorderLayout());
			
		/* Configurando o painel superior de conexões */
		JPanel panelConexao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		//Configurando lblSGBD
		this.lblSGBD = new JLabel("SGBD: ");
		panelConexao.add(this.lblSGBD);
		
		//Configurando cbxSGBD
		this.cbxSGBD = new JComboBox();
		this.cbxSGBD.setName("cbxSGBD");
		if(UIManager.getLookAndFeel().getName().contains(Constantes.ESTILO_WINDOWS))
			this.cbxSGBD.setPreferredSize(new Dimension(200,20));
		else
			this.cbxSGBD.setPreferredSize(new Dimension(200,26));
		this.cbxSGBD.addItem(Constantes.OPCAO_SELECIONE);
		
		//Carregando lista de SGBDs
		try{
			SGBD.carregarSGBDs();
			
			for(SGBD sgbd: SGBD.getListaSGBD())
				this.cbxSGBD.addItem(sgbd);
		}catch(IOException ioException){
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("E0019"), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
		}
		
		this.cbxSGBD.addItemListener(new ItemListenerHandler());
		panelConexao.add(this.cbxSGBD);
		panelConexao.add(new JLabel("       "));
		
		//Configurando lblConexao
		this.lblConexao = new JLabel("Conexão: ");
		panelConexao.add(this.lblConexao);
		
		//Configurando cbxConexao
		this.cbxConexao = new JComboBox();
		this.cbxConexao.setName("cbxConexao");
		if(UIManager.getLookAndFeel().getName().contains(Constantes.ESTILO_WINDOWS))
			this.cbxConexao.setPreferredSize(new Dimension(200, 20));
		else
			this.cbxConexao.setPreferredSize(new Dimension(200, 26));
		this.cbxConexao.addItem(Constantes.OPCAO_SELECIONE);
		
		//Carregando lista de Conexoes
		try{
			Conexao.carregarConexões();
		}catch(IOException ioException){
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("E0020"), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
		}
		
		this.cbxConexao.setEnabled(false);
		this.cbxConexao.addItemListener(new ItemListenerHandler());
		panelConexao.add(this.cbxConexao);
		
		//Configurando btnAddConexao
		this.btnAddConexao = new JButton("Nova", new ImageIcon("img/application_add.png"));
		this.btnAddConexao.setName("btnAddConexao");
		this.btnAddConexao.setEnabled(true);
		this.btnAddConexao.setToolTipText("Nova Conexão (ALT + N)");
		panelConexao.add(this.btnAddConexao);
		
		//Configurando btnEditarConexao
		this.btnEditarConexao = new JButton("Alterar", new ImageIcon("img/application_edit.png"));
		this.btnEditarConexao.setName("btnEditarConexao");
		this.btnEditarConexao.setEnabled(false);
		this.btnEditarConexao.setToolTipText("Editar Conexão (ALT + A)");
		panelConexao.add(this.btnEditarConexao);
		
		//Configurando btnAddConexao
		this.btnRemoveConexao = new JButton("Excluir", new ImageIcon("img/application_remove.png"));
		this.btnRemoveConexao.setName("btnRemoveConexao");
		this.btnRemoveConexao.setEnabled(false);
		this.btnRemoveConexao.setToolTipText("Excluir Conexão (ALT + E)");
		panelConexao.add(this.btnRemoveConexao);	
		
		this.add(panelConexao, BorderLayout.NORTH);
		
		/* Configurando o painel superior de conexões */
		JPanel panelConfigConexao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		//Configurando btnConectar
		this.btnConectar = new JButton("Conectar", new ImageIcon("img/database_accept.png"));
		this.btnConectar.setName("btnConectar");		
		this.btnConectar.setEnabled(false);
		this.btnConectar.setToolTipText("Criar conexão com o Banco de Dados (ALT + C)");
		panelConfigConexao.add(this.btnConectar);
		
		//Configurando btnDesconectar
		this.btnDesconectar = new JButton("Desconectar", new ImageIcon("img/database_remove.png"));
		this.btnDesconectar.setName("btnDesconectar");
		this.btnDesconectar.setEnabled(false);
		this.btnDesconectar.setToolTipText("Desconectar do Banco de Dados (ALT + D)");
		panelConfigConexao.add(this.btnDesconectar);
		panelConfigConexao.add(new JLabel("          "));
		
		//Configurando btnExecutar
		this.btnExecutar = new JButton("Executar", new ImageIcon("img/database_process.png"));
		this.btnExecutar.setName("btnExecutar");
		this.btnExecutar.setEnabled(false);
		this.btnExecutar.setToolTipText("Executar comando (ALT + X)");
		panelConfigConexao.add(this.btnExecutar);
		panelConfigConexao.add(new JLabel("          "));
		
		//Configurando chkAutoCommit
		this.chkAutoCommit = new JCheckBox("Auto Commit", true);
		this.chkAutoCommit.setEnabled(false);
		panelConfigConexao.add(this.chkAutoCommit);
		panelConfigConexao.add(new JLabel("    "));
		
		//Configurando btnCommit
		this.btnCommit = new JButton("Commit", new ImageIcon("img/accept.png"));
		this.btnCommit.setName("btnCommit");
		this.btnCommit.setEnabled(false);
		this.btnCommit.setToolTipText("Confirmar operações realizadas no Banco de Dados (ALT + M)");
		panelConfigConexao.add(this.btnCommit);
		
		//Configurando btnRollback
		this.btnRollback = new JButton("Rollback", new ImageIcon("img/block.png"));
		this.btnRollback.setName("btnRollback");
		this.btnRollback.setEnabled(false);
		this.btnRollback.setToolTipText("Cancelar operações realizadas no Banco de Dados (ALT + R)");
		panelConfigConexao.add(this.btnRollback);
		
		this.add(panelConfigConexao, BorderLayout.CENTER);
		this.setBorder(new TitledBorder("Propriedades da Conexão"));
	}
	
	/**
	 * Retorna a instância do botão Conectar
	 * @return O botão Conectar
	 */
	public JButton getBtnConectar() {
		return btnConectar;
	}
	
	/**
	 * Retorna a instância do botão Desconectar
	 * @return O botão Desconectar
	 */
	public JButton getBtnDesconectar() {
		return btnDesconectar;
	}

	/**
	 * Retorna a instância do botão Commit
	 * @return O botão Commit
	 */
	public JButton getBtnCommit() {
		return btnCommit;
	}

	/**
	 * Retorna a instância do botão Rollback
	 * @return O botão Rollback
	 */
	public JButton getBtnRollback() {
		return btnRollback;
	}

	/**
	 * Retorna a instância do botão Nova Conexão
	 * @return O botão Nova Conexão
	 */
	public JButton getBtnAddConexao() {
		return btnAddConexao;
	}
	
	/**
	 * Retorna a instância do botão Editar Conexão
	 * @return O botão Nova Conexão
	 */
	public JButton getBtnEditarConexao() {
		return btnEditarConexao;
	}

	/**
	 * Retorna a instância do botão Excluir Conexão
	 * @return O botão Excluir Conexão
	 */
	public JButton getBtnRemoveConexao() {
		return btnRemoveConexao;
	}

	/**
	 * Retorna a instância do botão Executar
	 * @return O botão Executar
	 */
	public JButton getBtnExecutar() {
		return btnExecutar;
	}

	/**
	 * Retorna a instância do checkbox Auto Commit
	 * @return O checkbox Auto Commit
	 */
	public JCheckBox getChkAutoCommit() {
		return chkAutoCommit;
	}
	
	/**
	 * Retorna a instância da combobox SGBD
	 * @return A combobox SGBD
	 */
	public JComboBox getCbxSGBD() {
		return cbxSGBD;
	}
	
	/**
	 * Retorna a instância da combobox Conexão
	 * @return A combobox Conexão
	 */
	public JComboBox getCbxConexao() {
		return cbxConexao;
	}
	
	/**
	 * Recarregar as opções da combo de Conexões baseado na lista do sistema
	 */
	public void carregarComboConexoes(){
		if(PanelControle.this.cbxSGBD.getSelectedIndex() != 0)
		{				
			PanelControle.this.cbxConexao.removeAllItems();
			PanelControle.this.cbxConexao.addItem(Constantes.OPCAO_SELECIONE);
			for(Conexao conexao: Conexao.getListaConexoes(((SGBD)PanelControle.this.cbxSGBD.getSelectedItem()).getNome()))
				PanelControle.this.cbxConexao.addItem(conexao);
			PanelControle.this.cbxConexao.setEnabled(true);
		}else{
			PanelControle.this.cbxConexao.removeAllItems();
			PanelControle.this.cbxConexao.addItem(Constantes.OPCAO_SELECIONE);
			PanelControle.this.cbxConexao.setEnabled(false);
			PanelControle.this.btnConectar.setEnabled(false);
		}
	}

	/**
	 * Classe interna para tratamento de evento das combobox de Conexão e SGBD
	 * @author Victor Araujo
	 *
	 */
	private class ItemListenerHandler implements ItemListener{
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(((JComboBox)e.getSource()).getName().equals("cbxSGBD")){				
				carregarComboConexoes();
			}else
				if(((JComboBox)e.getSource()).getName().equals("cbxConexao")){
					if(PanelControle.this.cbxConexao.getSelectedIndex() != 0){
						PanelControle.this.btnConectar.setEnabled(true);
						PanelControle.this.btnAddConexao.setEnabled(false);
						PanelControle.this.btnEditarConexao.setEnabled(true);
						PanelControle.this.btnRemoveConexao.setEnabled(true);
					}else{
						PanelControle.this.btnConectar.setEnabled(false);
						PanelControle.this.btnAddConexao.setEnabled(true);
						PanelControle.this.btnEditarConexao.setEnabled(false);
						PanelControle.this.btnRemoveConexao.setEnabled(false);
					}
				}
		}
		
	}	
}
