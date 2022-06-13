package persistence;

import java.util.ArrayList;

import exception.ColecaoException;

public interface ColecaoPokemonInterface<Pokemon> {
	
	public ArrayList<Pokemon> todosPokemons() throws ColecaoException;
	public void inserirPokemon(Pokemon pokemon) throws ColecaoException;
	public Pokemon porNumPokemon(String num) throws ColecaoException;
	public void alterarPokemon(Pokemon pokemon, String num) throws ColecaoException;
	public void removerPokemon(Pokemon pokemon) throws ColecaoException;
}
