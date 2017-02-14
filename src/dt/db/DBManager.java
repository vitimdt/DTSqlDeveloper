package dt.db;

import java.sql.*;
import java.util.Vector;

import dt.exceptions.DBException;
import dt.helper.Constantes;
import dt.helper.Mensagens;

/**
 * Classe gerenciador de banco de dados, responsavel por realizar a conexão com a base de dados definida
 * e realizar as operações
 * @author Victor Araujo
 *
 */
public class DBManager {
	
	private static DBManager instance;
	private Connection connection;
	private Statement stmt;
	private ResultSet resultSet;
		
	/**
	 * Construtor privado que inicializa as váriaveis da classe
	 */
	private DBManager(){
		this.connection = null;
	}
	
	/**
	 * Método estático que retorna a instância da classe, se ela não estiver instânciada será criada uma nova
	 * instância
	 * @return Uma instância da classe <code>dt.db.DBManager</code>
	 */
	public static DBManager getInstancia(){
		if(instance == null)
			instance = new DBManager();
		
		return instance;
	}
	
	/**
	 * Estabelecer uma conexão com uma base de dados
	 * @param nomeClasse - Nome da classe JDBC do SGBD que esta sendo utilizado no momentos
	 * @param strConexao - String de conexão da base de dados
	 * @param nomeUsuario - Nome do usuário da base de dados
	 * @param senha - Senha da base de dados
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema
	 */
	public void conectar(String nomeClasse, String strConexao, String nomeUsuario, String senha)
					throws DBException{
		try{			
			Class.forName(nomeClasse);
			
			if(this.connection != null)
				this.connection.close();
			
			this.connection = DriverManager.getConnection(strConexao, nomeUsuario, senha);
		}catch(ClassNotFoundException classNotFoundException){
			classNotFoundException.printStackTrace();
			throw new DBException(Mensagens.getInstancia().getMensagem("E0001"), "E0001", false, classNotFoundException);
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
			if(!this.conexaoFechada())
				throw new DBException(Mensagens.getInstancia().getMensagem("E0002") 
									  + "\nErro original: " + sqlException.getMessage()
						  			  , "E0002", true, sqlException);
			else
				throw new DBException(Mensagens.getInstancia().getMensagem("E0002") 
									  + "\nErro original: " + sqlException.getMessage()
									  , "E0002", false, sqlException);
		}
	}
	
	/**
	 * Fecha a conexão se ela estiver aberta
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema no fechamento da conexão
	 */
	public void fecharConexao()
					throws DBException{
		if(this.connection != null){
			try{
				this.connection.close();
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				throw new DBException(Mensagens.getInstancia().getMensagem("E0003") + "\nErro original: " + sqlException.getMessage()
									  , "E0003", true, sqlException);
			}
		}
	}
	
	/**
	 * Método que verifica se a conexão está fechada
	 * @return Retorna o status se a conexão está fechada ou não
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema na verificação da conexão
	 */
	public boolean conexaoFechada()
						throws DBException{
		if(this.connection != null){
			try{
				return this.connection.isClosed();
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				throw new DBException(Mensagens.getInstancia().getMensagem("E0004") 
										+ "\nErro original: " + sqlException.getMessage()
										, "E0004", false, sqlException);
			}
		}
		return true;
	}
	
	/**
	 * Método que verifica se existir uma conexão aberta se está com auto commit habilitado
	 * @return Retorna o status se o auto commit esta habilitado ou não
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema na verificação da conexão
	 */
	public boolean autoCommitHabilitado()
						throws DBException{
		if(this.connection != null){
			try{
				return this.connection.getAutoCommit();
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				if(!this.conexaoFechada())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0005") 
											+ "\nErro original: " + sqlException.getMessage()
											, "E0005", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0005") 
											+ "\nErro original: " + sqlException.getMessage()
											, "E0005", false, sqlException);
			}
		}
		return true;
	}
	
	/**
	 * Definir o nível de isolamento da transação de banco de dados
	 * @param nivelIsolamento - Inteiro que define o nivel de isolamento de transação através das constantes definidas na classe <code>java.sql.Connection</code>
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema na definição do nivel de isolamento da transação
	 */
	public void setNivelDeIsolamento(int nivelIsolamento)
					throws DBException{
		if(this.connection != null)
			try{
				if(this.connection.isClosed())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0006"), "E0006", false);
					
				this.connection.setTransactionIsolation(nivelIsolamento);
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				if(!this.conexaoFechada())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0007") 
											+ "\nErro original: " + sqlException.getMessage()
											, "E0007", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0007") 
											+ "\nErro original: " + sqlException.getMessage()
											, "E0007", false, sqlException);
			}				
		else
			throw new DBException(Mensagens.getInstancia().getMensagem("E0006"), "E0006", false);
	}
	
	/**
	 * Método para definir se o controle de transação será realizado de forma automática ou não
	 * @param autoCommit - Define se o controle de transação vai ser realizado automaticamente
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema na definição da forma de fechamento da transação
	 */
	public void setAutoCommit(boolean autoCommit)
					throws DBException{
		if(this.connection != null)
			try{
				if(this.connection.isClosed())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0008"), "E0008", false);
					
				this.connection.setAutoCommit(autoCommit);
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				if(!this.conexaoFechada())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0009") 
											+ "\nErro original: " + sqlException.getMessage(), "E0009", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0009") 
											+ "\nErro original: " + sqlException.getMessage(), "E0009", false, sqlException);
			}				
		else
			throw new DBException(Mensagens.getInstancia().getMensagem("E0008"), "E0008", false);
	}
	
	/**
	 * Realizar a confirmação das alterações realizadas na transação
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema na confirmação das alterações da transação
	 */
	public void commit()
					throws DBException{
		if(this.connection != null)
			try{
				if(this.connection.isClosed())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0010"), "E0010", false);
					
				this.connection.commit();
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				if(!this.conexaoFechada())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0011")
											+ "\nErro original: " + sqlException.getMessage(), "E0011", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0011")
											+ "\nErro original: " + sqlException.getMessage(), "E0011", false, sqlException);
			}				
		else
			throw new DBException(Mensagens.getInstancia().getMensagem("E0010"), "E0010", false);
	}
	
	/**
	 * Cancela todas alterações realizadas na transação
	 * @throws DBException - Exceção que será gerada caso ocorrer algum problema no cancelamento das alterações da transação
	 */
	public void rollback()
					throws DBException{
		if(this.connection != null)
			try{
				if(this.connection.isClosed())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0012"), "E0012", false);
					
				this.connection.rollback();
			}catch(SQLException sqlException){
				sqlException.printStackTrace();
				if(!this.conexaoFechada())
					throw new DBException(Mensagens.getInstancia().getMensagem("E0013") 
											+ "\nErro original: " + sqlException.getMessage(), "E0013", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0013") 
											+ "\nErro original: " + sqlException.getMessage(), "E0013", false, sqlException);
			}				
		else
			throw new DBException(Mensagens.getInstancia().getMensagem("E0012"), "E0012", false);
	}
	
	/**
	 * Método que executa uma query no BD dados e retorna o resultado
	 * @param sql - Objeto que contém a query que será executada
	 * @throws DBException - Exceçao que será gerada caso ocorra algum outro problema na tentativa de executar a query
	 */
	public void executarQuery(SQL sql)
						throws DBException{
		if(this.connection == null)
			throw new DBException(Mensagens.getInstancia().getMensagem("E0014"), "E0014", false);
			
		try{
			if(this.connection.isClosed())
				throw new DBException(Mensagens.getInstancia().getMensagem("E0014"), "E0014", false);
				
			stmt = this.connection.createStatement();
			this.resultSet = stmt.executeQuery(sql.getComandos()[0]);
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
			if(!this.conexaoFechada())
				throw new DBException( Mensagens.getInstancia().getMensagem("E0015")
										+ "\nErro original: " + sqlException.getMessage(), "E0015", true, sqlException);
			else
				throw new DBException( Mensagens.getInstancia().getMensagem("E0015")
										+ "\nErro original: " + sqlException.getMessage(), "E0015", false, sqlException);
		}
	}
	
	/**
	 * Método que executa uma ou mais operações no BD
	 * @param sql - Objeto que contém as operações que serão executadas
	 * @return Objeto <code>java.lang.String</code> que contém o resultado da consulta
	 * @throws DBException - Exceçao que será gerada caso ocorra algum outro problema na tentativa de executar alguma das operações
	 */
	public String executarOperacao(SQL sql)
					throws DBException{
		if(this.connection == null)
			throw new DBException(Mensagens.getInstancia().getMensagem("E0016"), "E0016", false);
		
		Integer numeroComandoExecutado = null;
		StringBuffer bufferResult = new StringBuffer();
		try{
			if(this.connection.isClosed())
				throw new DBException(Mensagens.getInstancia().getMensagem("E0016"), "E0016", false);
				
			stmt = this.connection.createStatement();
			
			//Tratamento para executar as operações de BD
			String[] comandos = sql.getComandos();
			if(comandos != null){				
				for(int pos = 0; pos < comandos.length; pos++){
					numeroComandoExecutado = pos + 1;
					if(comandos[pos].toUpperCase().contains(Constantes.COMANDO_INSERT) 
							|| comandos[pos].toUpperCase().contains(Constantes.COMANDO_UPDATE)
							|| comandos[pos].toUpperCase().contains(Constantes.COMANDO_DELETE))
					{
						int numRows = stmt.executeUpdate(comandos[pos]);
						if(comandos[pos].toUpperCase().contains(Constantes.COMANDO_INSERT))
							bufferResult.append(Mensagens.getInstancia().getMensagem("I0007") + "\n\n");
						else
							if(comandos[pos].toUpperCase().contains(Constantes.COMANDO_UPDATE))
								bufferResult.append(Mensagens.getInstancia().getMensagem("I0008", new String[]{ "" + numRows }) + "\n\n");
							else
								if(comandos[pos].toUpperCase().contains(Constantes.COMANDO_DELETE))
									bufferResult.append(Mensagens.getInstancia().getMensagem("I0009", new String[] { "" + numRows }) + "\n\n");
					}
					else
					{
						stmt.executeUpdate(comandos[pos]);						
						bufferResult.append(Mensagens.getInstancia().getMensagem("I0010", new String[] { "" + numeroComandoExecutado }) + "\n\n");
					}
				}
			}
			
		}catch(SQLException sqlException){
			sqlException.printStackTrace();
			if(!this.conexaoFechada()){
				if(numeroComandoExecutado != null)
					throw new DBException(Mensagens.getInstancia().getMensagem("E0017", new String[]{ numeroComandoExecutado.toString() }) 
																+ "\nErro original: " + sqlException.getMessage(), "E0017", true, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0018") + 
										  "\nErro original: " + sqlException.getMessage(), "E0018", true, sqlException);
			}
			else{
				if(numeroComandoExecutado != null)
					throw new DBException(Mensagens.getInstancia().getMensagem("E0017", new String[]{ numeroComandoExecutado.toString() }) 
																+ "\nErro original: " + sqlException.getMessage(), "E0017", false, sqlException);
				else
					throw new DBException(Mensagens.getInstancia().getMensagem("E0018") + 
										  "\nErro original: " + sqlException.getMessage(), "E0018", false, sqlException);
			}
		}
		
		return bufferResult.toString();
	}
	
	/**
	 * Retorna o objeto ResultSet depois de executado o executeQuery
	 * @return Objeto ResultSet
	 */
	public ResultSet getResultSet(){
		return this.resultSet;
	}
	
	/**
	 * Recupera os dados da próxima linha e retornando um vetor 
	 * @param rs - Resultado da consulta que foi executada
	 * @param rsmd - Metadados do consulta que foi executada
	 * @return Vetor contendo os dados da linha que foi lida
	 * @throws SQLException - Exceção que é gerada caso ocorra algum erro na leitura da linha
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getNextRow(ResultSet rs, ResultSetMetaData rsmd) 
						throws SQLException{
		Vector currentRow = new Vector();
		for(int i = 1; i <= rsmd.getColumnCount(); i++)
			switch(rsmd.getColumnType(i)){
				case Types.VARCHAR:
				case Types.LONGNVARCHAR:
					if(rs.getString(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(rs.getString(i));
					break;
				case Types.INTEGER:
					currentRow.addElement(new Integer(rs.getInt(i)));
					break;
				case Types.SMALLINT:
					currentRow.addElement(new Short(rs.getShort(i)));
					break;
				case Types.BIGINT:
					currentRow.addElement(new Long(rs.getLong(i)));
					break;	
				case Types.BOOLEAN:
					currentRow.addElement(new Boolean(rs.getBoolean(i)));
					break;	
				case Types.CHAR:
					if(rs.getString(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(rs.getString(i));
					break;	
				case Types.DATE:
					if(rs.getDate(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(new Date(rs.getDate(i).getTime()));
					break;	
				case Types.BLOB:
					if(rs.getBlob(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(rs.getBlob(i));
					break;	
				case Types.DECIMAL:
					currentRow.addElement(new Double(rs.getDouble(i)));
					break;	
				case Types.DOUBLE:
					currentRow.addElement(new Double(rs.getDouble(i)));
					break;	
				case Types.FLOAT:
					currentRow.addElement(new Float(rs.getFloat(i)));
					break;	
				case Types.BINARY:
					if(rs.getBinaryStream(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(rs.getBinaryStream(i));
					break;
				case Types.NULL:
					currentRow.addElement("<NULL>");
					break;
				case Types.TIME:
					if(rs.getTime(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(new Time(rs.getTime(i).getTime()));
					break;
				case Types.TIMESTAMP:
					if(rs.getTime(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(new Timestamp(rs.getTimestamp(i).getTime()));
					break;
				case Types.REAL:
					if(rs.getString(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(new Float(rs.getFloat(i)));
					break;				
				default:
					if(rs.getString(i) == null)
						currentRow.addElement("<NULL>");
					else
						currentRow.addElement(rs.getString(i));
					break;
			}
		
		return currentRow;
	}
}
