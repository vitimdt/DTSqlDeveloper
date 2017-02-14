package dt.view.componentes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import dt.helper.*;

/**
 * Tela para criação de uma nova conexão
 * @author Victor Araujo
 *
 */
public class DialogNovaConexao extends JDialog{
	
	/**
	 * Constante que representa que a tela de diálogo foi finalizada com confirmação
	 */
	public static final int RESULT_OK = 0;
	/**
	 * Constante que representa que a tela de diálogo foi finalizada com cancelamento
	 */
	public static final int RESULT_CANCELADO = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox cbxSGBD;
	private JTextField txtNome, txtURL, txtNomeUsuario;
	private JPasswordField txtSenha;
	private JLabel lblSGBD, lblNome, lblURL, lblNomeUsuario, lblSenha;
	private JButton btnConfirmar, btnCancelar;
	private Conexao conexao;
	private int result = RESULT_CANCELADO;

	/**
	 * Construtor para inicializar os componentes da tela
	 * @param owner - Tela principal que chama a tela de diálogo
	 */
	public DialogNovaConexao(Frame owner){
		super(owner, "Nova Conexão");
		
		inicializarComponentes();
		
		this.setSize(500, 300);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Construtor para inicializar os componentes da tela e carregar os dados da conexão
	 * @param owner - Tela principal que chama a tela de diálogo
	 * @param conexao - Conexão para ser alterada
	 */
	public DialogNovaConexao(Frame owner, Conexao conexao){
		super(owner, "Editar Conexão");
		
		this.conexao = conexao;
		inicializarComponentes();
		
		this.setSize(500, 300);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Construtor para inicializar os componentes da tela
	 * @param owner - Tela principal que chama a tela de diálogo
	 * @param modal - Define se a tela de diálogo será iniciada de forma modal ou não
	 */
	public DialogNovaConexao(Frame owner, boolean modal){
		super(owner, "Nova Conexão", modal);
		
		inicializarComponentes();
		
		if(UIManager.getLookAndFeel().getName().contains(Constantes.ESTILO_WINDOWS))
			this.setSize(500, 150);
		else
			this.setSize(550, 180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Construtor para inicializar os componentes da tela
	 * @param owner - Tela principal que chama a tela de diálogo
	 * @param modal - Define se a tela de diálogo será iniciada de forma modal ou não
	 * @param conexao - Conexão para ser alterada
	 */
	public DialogNovaConexao(Frame owner, boolean modal, Conexao conexao){
		super(owner, "Editar Conexão", modal);
		
		this.conexao = conexao;
		inicializarComponentes();
		
		if(UIManager.getLookAndFeel().getName().contains(Constantes.ESTILO_WINDOWS))
			this.setSize(500, 150);
		else
			this.setSize(550, 180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Método que inicializa e configura os componentes na tela
	 */
	private void inicializarComponentes(){
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JPanel panelFields = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//Configurando lblSGBD
		this.lblSGBD = new JLabel("SGBD: ");
		panelFields.add(this.lblSGBD);
		
		//Configurando cbxSGBD
		this.cbxSGBD = new JComboBox();
		this.cbxSGBD.setName("cbxSGBD");
		if(UIManager.getLookAndFeel().getName().contains(Constantes.ESTILO_WINDOWS))
			this.cbxSGBD.setPreferredSize(new Dimension(180, 20));
		else	
			this.cbxSGBD.setPreferredSize(new Dimension(180, 26));
		this.cbxSGBD.addItem(Constantes.OPCAO_SELECIONE);
		for(SGBD sgbd: SGBD.getListaSGBD())
			this.cbxSGBD.addItem(sgbd);
		this.cbxSGBD.addItemListener(new ItemListenerHandler());
		panelFields.add(this.cbxSGBD);
		
		//Configurando lblNome
		this.lblNome = new JLabel("Nome da Conexão: ");
		panelFields.add(this.lblNome);
		
		//Configurando txtNome
		this.txtNome = new JTextField(15);
		panelFields.add(this.txtNome);
		
		//Configurando lblURL
		this.lblURL = new JLabel("String de Conexão: ");
		panelFields.add(this.lblURL);
		
		//Configurando txtURL
		this.txtURL = new JTextField(36);
		panelFields.add(this.txtURL);
		
		//Configurando lblNomeUsuario
		this.lblNomeUsuario = new JLabel("Nome do Usuário:  ");
		panelFields.add(this.lblNomeUsuario);
		
		//Configurando txtNomeUsuario
		this.txtNomeUsuario = new JTextField(18);
		panelFields.add(this.txtNomeUsuario);
		
		//Configurando lblSenha
		this.lblSenha = new JLabel("    Senha: ");
		panelFields.add(this.lblSenha);
		
		//Configurando txtSenha
		this.txtSenha = new JPasswordField(11);
		panelFields.add(this.txtSenha);
		
		container.add(panelFields, BorderLayout.CENTER);
		
		JPanel panelControle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		//Configurando btnConfirmar
		this.btnConfirmar = new JButton("Confirmar", new ImageIcon("img/confirm.png"));
		this.btnConfirmar.setName("btnConfirmar");
		this.btnConfirmar.setEnabled(true);
		this.btnConfirmar.setToolTipText("Confirmar conexão");
		this.btnConfirmar.addActionListener(new ActionListenerHandler());
		panelControle.add(this.btnConfirmar);
		
		//Configurando btnCancelar
		this.btnCancelar = new JButton("Cancelar", new ImageIcon("img/cancel.png"));
		this.btnCancelar.setName("btnCancelar");
		this.btnCancelar.setEnabled(true);
		this.btnCancelar.setToolTipText("Cancelar conexão");
		this.btnCancelar.addActionListener(new ActionListenerHandler());
		panelControle.add(this.btnCancelar);
		
		container.add(panelControle, BorderLayout.SOUTH);
		
		//se tela para edição, carrega os dados da conexão
		if(this.conexao != null){
			for(int index = 1; index < cbxSGBD.getItemCount(); index++){
				if(((SGBD)cbxSGBD.getItemAt(index)).getNome().equals(this.conexao.getSgbd().toString())){
					cbxSGBD.setSelectedIndex(index);
					break;
				}
			}
			cbxSGBD.setEnabled(false);
			txtNome.setText(this.conexao.getNome());
			txtNome.setEnabled(false);
			txtURL.setText(this.conexao.getURL());
			txtNomeUsuario.setText(this.conexao.getUsuario());
			txtSenha.setText(this.conexao.getSenha());
		}
	}
	
	/**
	 * Retorna a conexão que foi criada
	 * @return Conexão que foi criada
	 */
	public Conexao getConexao() {
		return this.conexao;
	}
	
	/**
	 * Retorna a resultado da tela de diálogo se foi confirmada ou cancelada
	 * @return Retorna um inteiro que representa as constantes da classe se a tela foi confirmada ou cancelada
	 */
	public int getResult() {
		return this.result;
	}
	
	/**
	 * Valida os campos obrigatórios que não foram preenchidos
	 * @return Retorna True se todos os campos foram preenchidos e False se algum campo não foi preenchido
	 */
	@SuppressWarnings("deprecation")
	private boolean validarCampos(){
		if(this.cbxSGBD.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0011", new String[]{ "SGBD" }), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
			this.cbxSGBD.setFocusable(true);
			return false;
		}
		if(this.txtNome.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0011", new String[]{ "Nome da Conexão" }), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
			this.txtNome.setFocusable(true);
			return false;
		}
		if(this.txtURL.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0011", new String[]{ "String de Conexão" }), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
			this.txtURL.setFocusable(true);
			return false;
		}
		if(this.txtNomeUsuario.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0011", new String[]{ "Nome do Usuário" }), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
			this.txtNomeUsuario.setFocusable(true);
			return false;
		}
		if(this.txtSenha.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0011", new String[]{ "Senha" }), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
			this.txtSenha.setFocusable(true);
			return false;
		}
		
		//Se tela for acionada para criar uma nova conexão
		if(this.conexao == null)
			if(Conexao.existeConexao(this.txtNome.getText(), this.cbxSGBD.getSelectedItem().toString())){
				JOptionPane.showMessageDialog(this, Mensagens.getInstancia().getMensagem("I0012"), Constantes.TITULO_TELA_ALERTA, JOptionPane.WARNING_MESSAGE);
				return false;
			}
		return true;
	}
	
	/**
	 * Classe interna para tratamento de evento dos botões de controle
	 * @author Victor Araujo
	 *
	 */
	private class ActionListenerHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((JButton)e.getSource()).getName().equals("btnConfirmar")){
				if(validarCampos())
				{
					DialogNovaConexao.this.conexao = new Conexao(DialogNovaConexao.this.txtNome.getText(),
	   													 		DialogNovaConexao.this.cbxSGBD.getSelectedItem().toString(),
	   													 		DialogNovaConexao.this.txtURL.getText(), 
	   													 		DialogNovaConexao.this.txtNomeUsuario.getText(),
	   													 		DialogNovaConexao.this.txtSenha.getText().toString(), true);
				
					DialogNovaConexao.this.result = RESULT_OK;
					DialogNovaConexao.this.dispose();
				}
			}else
				if(((JButton)e.getSource()).getName().equals("btnCancelar")){
					DialogNovaConexao.this.conexao = null;
					DialogNovaConexao.this.result = RESULT_CANCELADO;
					DialogNovaConexao.this.dispose();
				}
		}
	}
	
	/**
	 * Classe interna para tratamento de evento das combobox SGBD
	 * @author Victor Araujo
	 *
	 */
	private class ItemListenerHandler implements ItemListener{
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(((JComboBox)e.getSource()).getName().equals("cbxSGBD")){				
				if(DialogNovaConexao.this.cbxSGBD.getSelectedIndex() != 0)
					DialogNovaConexao.this.txtURL.setToolTipText(((SGBD)DialogNovaConexao.this.cbxSGBD.getSelectedItem()).getFormatoURL());
				else
					DialogNovaConexao.this.txtURL.setToolTipText("");
				
			}
		}
		
	}
}
