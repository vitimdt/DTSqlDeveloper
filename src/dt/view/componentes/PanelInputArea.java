package dt.view.componentes;

import java.awt.*;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Classe que define o painel de entrada de comandos
 * @author Victor Araujo
 *
 */
public class PanelInputArea extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea inputArea;
	
	/**
	 * Construtor que configura os componentes no painel
	 */
	public PanelInputArea(){
		super(new BorderLayout());
				
		//Configurando inputArea
		this.inputArea = new JTextArea();		
		Font font = new Font("SansSerif", Font.BOLD, 13);
		this.inputArea.setFont(font);
		this.add(new JScrollPane(this.inputArea), BorderLayout.CENTER);
		this.setBorder(new TitledBorder("Prompt de Comando"));
	}
	
	/**
	 * Retorna o comando selecionado se estiver alguma parte do comando selecionado, se não retorna todo comando
	 * @return Comando que será executado
	 */
	public String getComando(){
		if(this.inputArea.getText().trim().equals(""))
			return "";
		else
			if(this.inputArea.getSelectedText() != null
					&& !this.inputArea.getSelectedText().trim().equals(""))
				return this.inputArea.getSelectedText().trim();
			else
				return this.inputArea.getText().trim();
	}
	
	/**
	 * Define um evento de KeyListener para o componente de entrada de dados
	 * @param keyListener - Evento que será associado ao componente
	 */
	public void setKeyListener(KeyListener keyListener){
		this.inputArea.addKeyListener(keyListener);
	}
	
	/**
	 * Colocar o foco na área de entrada de dados
	 */
	public void receberFoco(){
		this.inputArea.requestFocus();
	}
}
