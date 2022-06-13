package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import exception.ColecaoException;
import model.NextEvolutions;
import model.Pokemon;
import model.PrevEvolutions;

public class ColecaoPokemon implements ColecaoPokemonInterface<Pokemon>{
	
	private Connection conn;
	
	public ColecaoPokemon(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ArrayList<Pokemon> todosPokemons() throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pokemon> pokemons = null;
		
		try {
			pokemons = new ArrayList<Pokemon>();
			String sql = "SELECT POKEMONS.ID,POKEMONS.NOME,POKEMONS.NUM,TIPO.TIPO1,TIPO.TIPO2,"
					+ "NEXT_EVOLUTION.NEXT1_NAME,NEXT_EVOLUTION.NEXT1_NUM,NEXT_EVOLUTION.NEXT2_NAME,NEXT_EVOLUTION.NEXT2_NUM,"
					+ "PREV_EVOLUTION.PREV1_NAME,PREV_EVOLUTION.PREV1_NUM,PREV_EVOLUTION.PREV2_NAME,PREV_EVOLUTION.PREV2_NUM "
					+ "FROM POKEMONS INNER JOIN TIPO ON POKEMONS.ID = TIPO.ID INNER JOIN NEXT_EVOLUTION ON POKEMONS.ID = NEXT_EVOLUTION.ID "
					+ "INNER JOIN PREV_EVOLUTION ON POKEMONS.ID = PREV_EVOLUTION.ID ";
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ArrayList<String> type = new ArrayList<String>();
				ArrayList<NextEvolutions> nexts = new ArrayList<NextEvolutions>();
				ArrayList<PrevEvolutions> prevs = new ArrayList<PrevEvolutions>();
				
				if(rs.getString("NEXT_EVOLUTION.NEXT1_NAME")!=null) {
					NextEvolutions n = new NextEvolutions();
					n.setName(rs.getString("NEXT_EVOLUTION.NEXT1_NAME"));
					n.setNum(rs.getString("NEXT_EVOLUTION.NEXT1_NUM"));
					nexts.add(n);
					if(rs.getString("NEXT_EVOLUTION.NEXT2_NAME")!=null) {
						NextEvolutions n2 = new NextEvolutions();
						n2 .setName(rs.getString("NEXT_EVOLUTION.NEXT2_NAME"));
						n2.setNum(rs.getString("NEXT_EVOLUTION.NEXT2_NUM"));
						nexts.add(n2);
					}
				}
				
				if(rs.getString("PREV_EVOLUTION.PREV1_NAME")!=null) {
					PrevEvolutions p = new PrevEvolutions();
					p.setName(rs.getString("PREV_EVOLUTION.PREV1_NAME"));
					p.setNum(rs.getString("PREV_EVOLUTION.PREV1_NUM"));
					prevs.add(p);
					if(rs.getString("PREV_EVOLUTION.PREV2_NAME")!=null) {
						PrevEvolutions p2 = new PrevEvolutions();
						p2.setName(rs.getString("PREV_EVOLUTION.PREV2_NAME"));
						p2.setNum(rs.getString("PREV_EVOLUTION.PREV2_NUM"));
						prevs.add(p2);
					}
				}
				
				type.add(rs.getString("TIPO.TIPO1"));
				if(rs.getString("TIPO.TIPO2")!=null) {
					type.add(rs.getString("TIPO.TIPO2"));
				}
			
				Pokemon pokemon = new Pokemon(rs.getInt("POKEMONS.ID"), rs.getString("POKEMONS.NUM"), rs.getString("POKEMONS.NOME"), type, nexts, prevs);
				
				pokemons.add(pokemon);
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao obter os pokemons do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		return pokemons;
	}

	@Override
	public void inserirPokemon(Pokemon pokemon) throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("entrando");
		int id = 0;
		try {
			String sql = "INSERT INTO POKEMONS (NUM,NOME) VALUES (?,?)";
			ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pokemon.getNum());
			ps.setString(2, pokemon.getName());
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					pokemon.setId(rs.getInt(1));
					System.out.println(pokemon.getId());
					id = pokemon.getId();
				}
			}
			
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao inserir o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		
		try {
			String sql = "INSERT INTO TIPO (ID,NUM,TIPO1,TIPO2) VALUES (?,?,?,?)";
			ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(id);
			ps.setInt(1, id);
			ps.setString(2, pokemon.getNum());
			ps.setString(3, pokemon.getType().get(0));
			if (pokemon.getType().size()==2) {
				ps.setString(4, pokemon.getType().get(1));
			}else {
				ps.setString(4, null);
			}
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					pokemon.setId(rs.getInt(1));
					System.out.println(pokemon.getId());
				}
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao inserir o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		
		try {
			String sql = "INSERT INTO NEXT_EVOLUTION (ID,NUM,NEXT1_NAME,NEXT1_NUM,NEXT2_NAME,NEXT2_NUM) VALUES (?,?,?,?,?,?)";
			ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(id);
			ps.setInt(1, id);
			ps.setString(2, pokemon.getNum());
			if(pokemon.getNext_evolution().size()==2) {
				ps.setString(3, pokemon.getNext_evolution().get(0).getName());
				ps.setString(4, pokemon.getNext_evolution().get(0).getNum());
				ps.setString(5, pokemon.getNext_evolution().get(1).getName());
				ps.setString(6, pokemon.getNext_evolution().get(1).getNum());
			}else if(pokemon.getNext_evolution().size()==1) {
				ps.setString(3, pokemon.getNext_evolution().get(0).getName());
				ps.setString(4, pokemon.getNext_evolution().get(0).getNum());
				ps.setString(5, null);
				ps.setString(6, null);
			}else {
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setString(6, null);
			}
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					pokemon.setId(rs.getInt(1));
					System.out.println(pokemon.getId());
				}
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao inserir o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		
		try {
			String sql = "INSERT INTO PREV_EVOLUTION (ID,NUM,PREV1_NAME,PREV1_NUM,PREV2_NAME,PREV2_NUM) VALUES (?,?,?,?,?,?)";
			ps = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(id);
			ps.setInt(1, id);
			ps.setString(2, pokemon.getNum());
			if(pokemon.getPrev_evolution().size()==2) {
				ps.setString(3, pokemon.getPrev_evolution().get(0).getName());
				ps.setString(4, pokemon.getPrev_evolution().get(0).getNum());
				ps.setString(5, pokemon.getPrev_evolution().get(1).getName());
				ps.setString(6, pokemon.getPrev_evolution().get(1).getNum());
			}else if(pokemon.getNext_evolution().size()==1) {
				ps.setString(3, pokemon.getPrev_evolution().get(0).getName());
				ps.setString(4, pokemon.getPrev_evolution().get(0).getNum());
				ps.setString(5, null);
				ps.setString(6, null);
			}else {
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setString(6, null);
			}
			
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					pokemon.setId(rs.getInt(1));
					System.out.println(pokemon.getId());
				}
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao inserir o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
	}

	@Override
	public Pokemon porNumPokemon(String num) throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Pokemon pokemon = null;
		System.out.println(num);
		
		try {
			String sql = "SELECT POKEMONS.ID,POKEMONS.NOME,POKEMONS.NUM,TIPO.TIPO1,TIPO.TIPO2,"
					+ "NEXT_EVOLUTION.NEXT1_NAME,NEXT_EVOLUTION.NEXT1_NUM,NEXT_EVOLUTION.NEXT2_NAME,NEXT_EVOLUTION.NEXT2_NUM,"
					+ "PREV_EVOLUTION.PREV1_NAME,PREV_EVOLUTION.PREV1_NUM,PREV_EVOLUTION.PREV2_NAME,PREV_EVOLUTION.PREV2_NUM "
					+ "FROM POKEMONS INNER JOIN TIPO ON POKEMONS.ID = TIPO.ID INNER JOIN NEXT_EVOLUTION ON POKEMONS.ID = NEXT_EVOLUTION.ID "
					+ "INNER JOIN PREV_EVOLUTION ON POKEMONS.ID = PREV_EVOLUTION.ID "
					+ "WHERE POKEMONS.NUM = ?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, num);
			System.out.println("entrando");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				ArrayList<String> type = new ArrayList<String>();
				ArrayList<NextEvolutions> nexts = new ArrayList<NextEvolutions>();
				ArrayList<PrevEvolutions> prevs = new ArrayList<PrevEvolutions>();
				
				if(rs.getString("NEXT_EVOLUTION.NEXT1_NAME")!=null) {
					NextEvolutions n = new NextEvolutions();
					n.setName(rs.getString("NEXT_EVOLUTION.NEXT1_NAME"));
					n.setNum(rs.getString("NEXT_EVOLUTION.NEXT1_NUM"));
					nexts.add(n);
					if(rs.getString("NEXT_EVOLUTION.NEXT2_NAME")!=null) {
						NextEvolutions n2 = new NextEvolutions();
						n2 .setName(rs.getString("NEXT_EVOLUTION.NEXT2_NAME"));
						n2.setNum(rs.getString("NEXT_EVOLUTION.NEXT2_NUM"));
						nexts.add(n2);
					}
				}
				
				if(rs.getString("PREV_EVOLUTION.PREV1_NAME")!=null) {
					PrevEvolutions p = new PrevEvolutions();
					p.setName(rs.getString("PREV_EVOLUTION.PREV1_NAME"));
					p.setNum(rs.getString("PREV_EVOLUTION.PREV1_NUM"));
					prevs.add(p);
					if(rs.getString("PREV_EVOLUTION.PREV2_NAME")!=null) {
						PrevEvolutions p2 = new PrevEvolutions();
						p2.setName(rs.getString("PREV_EVOLUTION.PREV2_NAME"));
						p2.setNum(rs.getString("PREV_EVOLUTION.PREV2_NUM"));
						prevs.add(p2);
					}
				}
				
				type.add(rs.getString("TIPO.TIPO1"));
				if(rs.getString("TIPO.TIPO2")!=null) {
					type.add(rs.getString("TIPO.TIPO2"));
				}
			
				pokemon = new Pokemon(rs.getInt("POKEMONS.ID"), num, rs.getString("POKEMONS.NOME"), type, nexts, prevs);
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao obter o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		return pokemon;
	}

	@Override
	public void alterarPokemon(Pokemon pokemon, String num) throws ColecaoException {
		PreparedStatement ps = null;
		
		try {
			String sql = "UPDATE POKEMONS SET NUM=?, NOME=? WHERE NUM=?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, pokemon.getNum());
			ps.setString(2, pokemon.getName());
			ps.setString(3, num);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao alterar o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "UPDATE TIPO SET NUM=?, TIPO1=?, TIPO2=? WHERE NUM=?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, pokemon.getNum());
			ps.setString(2, pokemon.getType().get(0));
			if(pokemon.getType().size()==2) {
				ps.setString(3, pokemon.getType().get(1));
			}else {
				ps.setString(3, null);
			}
			ps.setString(4, num);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao alterar o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "UPDATE NEXT_EVOLUTION SET NUM=?, NEXT1_NAME=?, NEXT1_NUM=?, "
					+ "NEXT2_NAME=?, NEXT2_NUM=? WHERE NUM=?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, pokemon.getNum());
			if(pokemon.getNext_evolution().size()==2) {
				ps.setString(2, pokemon.getNext_evolution().get(0).getName());
				ps.setString(3, pokemon.getNext_evolution().get(0).getNum());
				ps.setString(4, pokemon.getNext_evolution().get(1).getName());
				ps.setString(5, pokemon.getNext_evolution().get(1).getNum());
			}else if(pokemon.getNext_evolution().size()==1) {
				ps.setString(2, pokemon.getNext_evolution().get(0).getName());
				ps.setString(3, pokemon.getNext_evolution().get(0).getNum());
				ps.setString(4, null);
				ps.setString(5, null);
			}else {
				ps.setString(2, null);
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setString(5, null);
			}
			
			ps.setString(6, num);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao alterar o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "UPDATE PREV_EVOLUTION SET NUM=?, PREV1_NAME=?, PREV1_NUM=?, "
					+ "PREV2_NAME=?, PREV2_NUM=? WHERE NUM=?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, pokemon.getNum());
			if(pokemon.getPrev_evolution().size()==2) {
				ps.setString(2, pokemon.getPrev_evolution().get(0).getName());
				ps.setString(3, pokemon.getPrev_evolution().get(0).getNum());
				ps.setString(4, pokemon.getPrev_evolution().get(1).getName());
				ps.setString(5, pokemon.getPrev_evolution().get(1).getNum());
			}else if(pokemon.getNext_evolution().size()==1) {
				ps.setString(2, pokemon.getPrev_evolution().get(0).getName());
				ps.setString(3, pokemon.getPrev_evolution().get(0).getNum());
				ps.setString(4, null);
				ps.setString(5, null);
			}else {
				ps.setString(2, null);
				ps.setString(3, null);
				ps.setString(4, null);
				ps.setString(5, null);
			}
			
			ps.setString(6, num);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao alterar o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
	}

	@Override
	public void removerPokemon(Pokemon pokemon) throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id=0;
		try {
			String sql = "SELECT ID FROM POKEMONS WHERE NUM=?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, pokemon.getNum());
			System.out.println("entrando");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("ID");
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao obter o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		
		try {
			String sql = "DELETE FROM TIPO WHERE ID_TIPO=?";
			ps = this.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao remover o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "DELETE FROM NEXT_EVOLUTION WHERE ID_NEXT_EVOL=?";
			ps = this.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao remover o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "DELETE FROM PREV_EVOLUTION WHERE ID_PREV_EVOL=?";
			ps = this.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao remover o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
		try {
			String sql = "DELETE FROM POKEMONS WHERE ID=?";
			ps = this.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao remover o pokemon do banco de dados", e);
		}finally {
			try {
				ps.close();
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipulador do banco de dados",e);
			}
		}
		
	}

	@Override
	public ArrayList<Pokemon> getPokemonsPorTipo(String type) throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pokemon> pokemons = null;
		try {
			pokemons = new ArrayList<Pokemon>();
			String sql = "SELECT POKEMONS.ID,POKEMONS.NOME,POKEMONS.NUM,TIPO.TIPO1,TIPO.TIPO2,"
					+ "NEXT_EVOLUTION.NEXT1_NAME,NEXT_EVOLUTION.NEXT1_NUM,NEXT_EVOLUTION.NEXT2_NAME,NEXT_EVOLUTION.NEXT2_NUM,"
					+ "PREV_EVOLUTION.PREV1_NAME,PREV_EVOLUTION.PREV1_NUM,PREV_EVOLUTION.PREV2_NAME,PREV_EVOLUTION.PREV2_NUM "
					+ "FROM POKEMONS INNER JOIN TIPO ON POKEMONS.ID = TIPO.ID INNER JOIN NEXT_EVOLUTION ON POKEMONS.ID = NEXT_EVOLUTION.ID "
					+ "INNER JOIN PREV_EVOLUTION ON POKEMONS.ID = PREV_EVOLUTION.ID "
					+ "WHERE TIPO.TIPO1 = ? OR TIPO.TIPO2 = ?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, type);
			ps.setString(2, type);
			System.out.println("entrando");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ArrayList<String> tipo = new ArrayList<String>();
				ArrayList<NextEvolutions> nexts = new ArrayList<NextEvolutions>();
				ArrayList<PrevEvolutions> prevs = new ArrayList<PrevEvolutions>();
				
				if(rs.getString("NEXT_EVOLUTION.NEXT1_NAME")!=null) {
					NextEvolutions n = new NextEvolutions();
					n.setName(rs.getString("NEXT_EVOLUTION.NEXT1_NAME"));
					n.setNum(rs.getString("NEXT_EVOLUTION.NEXT1_NUM"));
					nexts.add(n);
					if(rs.getString("NEXT_EVOLUTION.NEXT2_NAME")!=null) {
						NextEvolutions n2 = new NextEvolutions();
						n2 .setName(rs.getString("NEXT_EVOLUTION.NEXT2_NAME"));
						n2.setNum(rs.getString("NEXT_EVOLUTION.NEXT2_NUM"));
						nexts.add(n2);
					}
				}
				
				if(rs.getString("PREV_EVOLUTION.PREV1_NAME")!=null) {
					PrevEvolutions p = new PrevEvolutions();
					p.setName(rs.getString("PREV_EVOLUTION.PREV1_NAME"));
					p.setNum(rs.getString("PREV_EVOLUTION.PREV1_NUM"));
					prevs.add(p);
					if(rs.getString("PREV_EVOLUTION.PREV2_NAME")!=null) {
						PrevEvolutions p2 = new PrevEvolutions();
						p2.setName(rs.getString("PREV_EVOLUTION.PREV2_NAME"));
						p2.setNum(rs.getString("PREV_EVOLUTION.PREV2_NUM"));
						prevs.add(p2);
					}
				}
				
				tipo.add(rs.getString("TIPO.TIPO1"));
				if(rs.getString("TIPO.TIPO2")!=null) {
					tipo.add(rs.getString("TIPO.TIPO2"));
				}
			
				Pokemon pokemon = new Pokemon(rs.getInt("POKEMONS.ID"), rs.getString("POKEMONS.NUM"), rs.getString("POKEMONS.NOME"), tipo, nexts, prevs);
				
				pokemons.add(pokemon);
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao obter os pokemons do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		return pokemons;
	}

	@Override
	public ArrayList<Pokemon> getPaginaPokemon(int numPagina, int qtdPorPagina) throws ColecaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Pokemon> pokemons = null;
		
		try {
			pokemons = new ArrayList<Pokemon>();
			String sql = "SELECT POKEMONS.ID,POKEMONS.NOME,POKEMONS.NUM,TIPO.TIPO1,TIPO.TIPO2,"
					+ "NEXT_EVOLUTION.NEXT1_NAME,NEXT_EVOLUTION.NEXT1_NUM,NEXT_EVOLUTION.NEXT2_NAME,NEXT_EVOLUTION.NEXT2_NUM,"
					+ "PREV_EVOLUTION.PREV1_NAME,PREV_EVOLUTION.PREV1_NUM,PREV_EVOLUTION.PREV2_NAME,PREV_EVOLUTION.PREV2_NUM "
					+ "FROM POKEMONS INNER JOIN TIPO ON POKEMONS.ID = TIPO.ID INNER JOIN NEXT_EVOLUTION ON POKEMONS.ID = NEXT_EVOLUTION.ID "
					+ "INNER JOIN PREV_EVOLUTION ON POKEMONS.ID = PREV_EVOLUTION.ID ";
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int qtd = (numPagina-1)*qtdPorPagina;
			int i=0;
			while(rs.next()) {
				ArrayList<String> type = new ArrayList<String>();
				ArrayList<NextEvolutions> nexts = new ArrayList<NextEvolutions>();
				ArrayList<PrevEvolutions> prevs = new ArrayList<PrevEvolutions>();
				
				if(i>=qtd && i < (qtd+qtdPorPagina)) {
					if(rs.getString("NEXT_EVOLUTION.NEXT1_NAME")!=null) {
						NextEvolutions n = new NextEvolutions();
						n.setName(rs.getString("NEXT_EVOLUTION.NEXT1_NAME"));
						n.setNum(rs.getString("NEXT_EVOLUTION.NEXT1_NUM"));
						nexts.add(n);
						if(rs.getString("NEXT_EVOLUTION.NEXT2_NAME")!=null) {
							NextEvolutions n2 = new NextEvolutions();
							n2 .setName(rs.getString("NEXT_EVOLUTION.NEXT2_NAME"));
							n2.setNum(rs.getString("NEXT_EVOLUTION.NEXT2_NUM"));
							nexts.add(n2);
						}
					}
					
					if(rs.getString("PREV_EVOLUTION.PREV1_NAME")!=null) {
						PrevEvolutions p = new PrevEvolutions();
						p.setName(rs.getString("PREV_EVOLUTION.PREV1_NAME"));
						p.setNum(rs.getString("PREV_EVOLUTION.PREV1_NUM"));
						prevs.add(p);
						if(rs.getString("PREV_EVOLUTION.PREV2_NAME")!=null) {
							PrevEvolutions p2 = new PrevEvolutions();
							p2.setName(rs.getString("PREV_EVOLUTION.PREV2_NAME"));
							p2.setNum(rs.getString("PREV_EVOLUTION.PREV2_NUM"));
							prevs.add(p2);
						}
					}
					
					type.add(rs.getString("TIPO.TIPO1"));
					if(rs.getString("TIPO.TIPO2")!=null) {
						type.add(rs.getString("TIPO.TIPO2"));
					}
				
					Pokemon pokemon = new Pokemon(rs.getInt("POKEMONS.ID"), rs.getString("POKEMONS.NUM"), rs.getString("POKEMONS.NOME"), type, nexts, prevs);
					
					pokemons.add(pokemon);
					
					i=i+1;
					
					if(i==(qtd+qtdPorPagina)) {
						continue;
					}
				}else {
					i=i+1;
				}
					
			}
		}catch(SQLException e) {
			throw new ColecaoException("Erro ao obter os pokemons do banco de dados", e);
		}finally {
			try {
				ps.close();
				if(rs!=null) {
					rs.close();
				}
			}catch (SQLException e) {
				throw new ColecaoException("Erro ao fechar manipuladores do banco de dados",e);
			}
		}
		return pokemons;

	}

}
