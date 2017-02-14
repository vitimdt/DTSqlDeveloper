package dt.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Classe auxiliar para armazenar os nomes e os drivers dos SGBDs
 * @author Victor Araujo
 *
 */
public class SGBD extends Object{
	private static ArrayList<SGBD> listaSGBD;	
	private String nome;
	private String nomeDriver;
	private String formatoURL;
	
	/**
	 * Construtor que inicializa os atributos da classe
	 * @param nome - Nome do SGDB
	 * @param nomeDriver - Nome do Driver do SGBD
	 */
	public SGBD(String nome, String nomeDriver, String formatoURL){
		this.nome = nome;
		this.nomeDriver = nomeDriver;
		this.formatoURL = formatoURL;
	}
	
	/**
	 * Método estático que carrega os SGBDs de um arquivo Properties
	 */
	public static void carregarSGBDs() 
							throws IOException{
		File file = new File("config/sgbd.properties");
		Properties prop = new Properties();
		FileInputStream fis = null;		
		try{
			fis = new FileInputStream(file);
			
			prop.load(fis);
			
			//lógica para carregar a lista de SGBDs
			listaSGBD = new ArrayList<SGBD>();
			int quantidadeSGBDs = Integer.parseInt(prop.getProperty("sgbd.contador"));
			for(int pos = 0; pos < quantidadeSGBDs; pos++){
				listaSGBD.add(new SGBD(prop.getProperty("sgbd.nome_" + pos), 
									   prop.getProperty("sgbd.driver_" + pos),
									   prop.getProperty("sgbd.formatoUrl_" + pos)));
			}
			
			fis.close();			
		}catch(IOException ioException){
			ioException.printStackTrace();
			throw ioException;
		}
	}
	
	/**
	 * Retorna o nome do SGBD
	 * @return Nome do SGBD
	 */
	public String getNome(){
		return this.nome;
	}
	
	/**
	 * Retorna o nome do Driver do SGBD
	 * @return Nome do Driver do SGBD
	 */
	public String getNomeDriver(){
		return this.nomeDriver;
	}
	
	/**
	 * Altera o nome do SGBD
	 * @param nome - Novo nome do SGBD
	 */
	public void setNome(String nome){
		this.nome = nome;
	}
	
	/**
	 * Altera o nome do Driver do SGBD
	 * @param nomeDriver - Novo nome do Driver do SGBD
	 */
	public void setNomeDriver(String nomeDriver){
		this.nomeDriver = nomeDriver;
	}
	
	/**
	 * Retorna o formato da URL
	 * @return Formato da URL do SGBD
	 */
	public String getFormatoURL(){
		return this.formatoURL;
	}
	
	/**
	 * Altera o formato da URL
	 * @param formatoURL - Novo formato da URL
	 */
	public void setFormatoURL(String formatoURL){
		this.formatoURL = formatoURL;
	}
	
	/**
	 * Método estático que retorna a lista de SGBD que deve ser previamente carregada
	 * @return Lista dos SGBDs
	 */
	public static ArrayList<SGBD> getListaSGBD(){
		return listaSGBD;
	}
	
	/**
	 * Sobreescrevendo método da superclasse que retorna o nome da instância
	 */
	@Override
	public String toString(){
		return this.nome;
	}
}
