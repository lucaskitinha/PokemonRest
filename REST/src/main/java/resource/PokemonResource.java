package resource;

import java.sql.Connection;
import java.util.ArrayList;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import exception.ColecaoException;
import exception.ConexaoException;
import model.Pokemon;
import persistence.ColecaoPokemon;
import persistence.ConexaoDB;

@Path("/pokemon")
@Produces(MediaType.APPLICATION_JSON)

public class PokemonResource {
	
	
	@GET
	@Path("/")
	public ArrayList<Pokemon> todos() throws ConexaoException, ColecaoException{
		Connection conn = ConexaoDB.getConexao();
		ColecaoPokemon pokemons = new ColecaoPokemon(conn);
		return pokemons.todosPokemons();
	}
	
	@GET
	@Path("{num}")
	public Pokemon retornaPorNum(@PathParam("num") String num) throws ColecaoException, ConexaoException {
		Connection conn = ConexaoDB.getConexao();
		ColecaoPokemon pokemon = new ColecaoPokemon(conn);
		System.out.println("ok");
		System.out.println(num);
		return pokemon.porNumPokemon(num);
		
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void inserePorNum(Pokemon p) throws ColecaoException, ConexaoException {
		Connection conn = ConexaoDB.getConexao();
		ColecaoPokemon pokemon = new ColecaoPokemon(conn); 
		pokemon.inserirPokemon(p);
		System.out.println("ok");
		
	}
	
	@PUT
	@Path("{num}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void alterarPokemon(@PathParam("num") String num, Pokemon p) throws ColecaoException, ConexaoException{
		Connection conn = ConexaoDB.getConexao();
		ColecaoPokemon pokemon = new ColecaoPokemon(conn);
		
		pokemon.alterarPokemon(p, num);
	}
	
	@DELETE
	@Path("{num}")
	public void removerPokemon(@PathParam("num") String num) throws ColecaoException, ConexaoException{
		Connection conn = ConexaoDB.getConexao();
		ColecaoPokemon pokemon = new ColecaoPokemon(conn);
		Pokemon p = new Pokemon(num);
		
		pokemon.removerPokemon(p);
	}
}
