package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.ConexaoException;

public class ConexaoDB {
	
	private static Connection conexao;
	
	public static Connection novaConexao() throws ConexaoException{
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon?useTimezone=true&serverTimezone=UTC","root","admin123");
			
		}catch(SQLException e) {
			throw new ConexaoException("Erro ao contectar-se com o banco de dados!", e);
		} catch (ClassNotFoundException e) {
			throw new ConexaoException("Erro no driver do banco de dados!", e);
		}
		
		return conn;
	}
	
	public static Connection getConexao() throws ConexaoException{
		if(conexao == null) {
			conexao = novaConexao();
		}
		return conexao;
	}
	
	public static void finalizarConexao() throws ConexaoException{
		try {
			conexao.close();
			System.out.println("conexão fechada");
		} catch(SQLException e) {
			throw new ConexaoException("Erro ao fechar conexão do banco de dados!", e);
		}
		
		conexao = null;
	}
}
