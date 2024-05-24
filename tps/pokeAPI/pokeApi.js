
class Move {
    constructor(id, name, learned_by_pokemon) {
        this.id = id;
        this.name = name;
        this.learned_by_pokemon = learned_by_pokemon
    }
}

class Pokemon {
    constructor(id, name, image, moves) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.moves = moves;
    }

    getImage() {
        return this.image;
    }

    getMoves() {
        return this.moves;
    }
}

class PokeAPI {
    BASE_URL = "https://pokeapi.co/api/v2/";

    async obtenerPokemonPorNombre( nombreDelPokemon ) {
        const response = await fetch(`${this.BASE_URL}pokemon/${nombreDelPokemon}`);
        const data = await response.json();
        const id = data.id;
        const pokemonName = data.name;
        const image = data.sprites.front_default;
        const moves = data.moves.map(move => new Move(move.id, move.name, move.power));

        return new Pokemon(id, pokemonName, image, moves);
    }

    async obtenerPokemonPorMovimiento( nombreDelMovimiento ) {
        const response = await fetch(`${this.BASE_URL}move/${ nombreDelMovimiento }`);
        const data = await response.json();
        
        const pokemonPromises = data.learned_by_pokemon.map(pokemon => this.obtenerPokemonPorNombre(pokemon.name));

        //!HASTA ACA ARRIBA TENGO LA LISTA DE POKEMONS QUE PUEDEN APRENDER EL MOVIMIENTO, TODAVIA NO PUDE FILTRAR LOS QUE YA LO SABEN

        const pokemonsResponse = await Promise.all(pokemonPromises);

        const pokemonsQueSabenElMovimiento = pokemonsResponse.filter(pokemon => pokemon.moves.some(move => move.name == nombreDelMovimiento));
        // ESTO DE ARRIBA NO FUNCIONA

        console.log(pokemonsQueSabenElMovimiento);
        return pokemonsQueSabenElMovimiento;   
        } catch (error) {
            console.error('Error al obtener Pokémon por movimiento:', error);
            throw error;
        }
    
    }



const api = new PokeAPI();
api.obtenerPokemonPorNombre("bulbasaur");
api.obtenerPokemonPorMovimiento("pound");