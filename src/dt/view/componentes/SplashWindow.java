package dt.view.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 * Classe que controla uma tela de splash
 * @author Victor Araujo
 *
 */
public class SplashWindow extends JWindow implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel imagem = null;
	private ImageIcon figura = null;
	private int tempo;
	
	/** 
	* Construtor que rebece uma String com o endereco da imagem que sera exibida na tela de splash. 
	* @param splash - String com o endereco da imagem exibida na tela de splash.
	* @param tempo - Define o tempo de espera da tela Splash
	*/  
	public SplashWindow(String splash, int tempo){
		this.figura = new ImageIcon(splash);
		this.imagem = new JLabel(figura);
		this.imagem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.tempo = tempo;
	}
	
	/**
	 * Exibe a tela de splash.  
     */
	public void open(){
		this.getContentPane().add(this.imagem);
		
		this.pack();
		//Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowListenerAdapter());
		this.setVisible(true);		
	}
	
	/**
	 * Aguarda um tempo em milisegundos.
	 */
	public void sleep(){
		try{			
			Thread.sleep(this.tempo);
		}catch(InterruptedException ieException){
			ieException.printStackTrace();			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.close();
		}
	}
	
	/**
	 * Fecha a janela de splash
	 */
	public void close(){
		this.dispose();
	}
	
	/**
	 * Retorna figura da tela splash
	 * @return - ImageIcon que representa a figura.
	 */
	public ImageIcon getFigura(){
		return this.figura;
	}
	
	/**
	 * Classe interna que define eventos da tela
	 * @author Victor Araujo
	 *
	 */
	private class WindowListenerAdapter extends WindowAdapter{
		
		@Override
		public void windowOpened(WindowEvent e){
			Graphics2D graphics2D = (Graphics2D)getGlassPane().getGraphics();
			graphics2D.setColor(new Color(51, 102, 153));
			graphics2D.setFont(new Font("SansSerif", 0, 16));
		}
	}

	@Override
	/**
	 * Implementação do método da interface Runnable que executa o método de espera.
	 */
	public void run() {
		this.sleep();
	}
}
