
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

    async obtenerPokemonPorNombre(nombreDelPokemon) {
        try {
            const response = await fetch(`${this.BASE_URL}pokemon/${nombreDelPokemon}`);
            const data = await response.json();
            const id = data.id;
            const pokemonName = data.name;
            const image = data.sprites.front_default;
            const moves = await Promise.all(data.moves.map(async moveData => {
                const moveResponse = await fetch(moveData.move.url);
                const moveDetails = await moveResponse.json();
                return new Move(moveDetails.id, moveDetails.name, moveDetails.learned_by_pokemon);
            }));
            const pokemon = new Pokemon(id, pokemonName, image, moves);
            //console.log(pokemon);
            return pokemon;
        } catch (error) {
            console.error('Error al obtener el Pokémon:', error);
        }
    }

    async obtenerPokemonPorMovimiento(nombreDelMovimiento) {
        try {
            const response = await fetch(`${this.BASE_URL}move/${nombreDelMovimiento}`);
            const data = await response.json();

            // Obtener la lista de promesas de Pokémon que pueden aprender el movimiento
            const pokemonPromises = data.learned_by_pokemon.map(pokemon => this.obtenerPokemonPorNombre(pokemon.name));

            // Esperar a que todas las promesas se resuelvan
            const pokemonsResponse = await Promise.all(pokemonPromises);

            // Filtrar los Pokémon que ya saben el movimiento
            const pokemonsQueSabenElMovimiento = pokemonsResponse.filter(pokemon =>
                pokemon.moves.some(move => move.name === nombreDelMovimiento)
            );

            const texto = pokemonsQueSabenElMovimiento.map(pokemon => pokemon.name)

            console.log(texto);
            return pokemonsQueSabenElMovimiento;
        } catch (error) {
            console.error('Error al obtener Pokémon por movimiento:', error);
            throw error;
        }
    }
    
    }



const api = new PokeAPI();
api.obtenerPokemonPorNombre("ditto");
api.obtenerPokemonPorMovimiento("pound");