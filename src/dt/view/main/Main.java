package dt.view.main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import dt.helper.Constantes;
import dt.helper.Mensagens;
import dt.view.componentes.SplashWindow;

/**
 * Classe que inicializa o aplicativo
 * @author Victor Araujo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SplashWindow splash = new SplashWindow("img/DTSqlDeveloper_Splash.png", 4500);
		DTSqlDeveloper dtSqlDeveloper = new DTSqlDeveloper();
				
		try{
			SwingUtilities.invokeLater(dtSqlDeveloper);
			splash.open();
			SwingUtilities.invokeAndWait(splash);			
		}catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(dtSqlDeveloper, ex.getMessage(), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		//Inicialiazando classe de mensagens
		if(Mensagens.getInstancia().ocorreuErroCarregarMensagens()){
			JOptionPane.showMessageDialog(dtSqlDeveloper, Mensagens.getInstancia().getErroCarregarMensagens(), Constantes.TITULO_TELA_ERRO, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		dtSqlDeveloper.setVisible(true);
	}
}
