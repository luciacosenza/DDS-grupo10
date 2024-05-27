const readline = require('node:readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

class Move {
    constructor(id, name, learned_by_pokemon) {
        this.id = id;
        this.name = name;
        this.learned_by_pokemon = learned_by_pokemon;
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
    constructor(){
        this.BASE_URL = "https://pokeapi.co/api/v2/";
    }

    async obtenerPokemonPorNombre(nombreDelPokemon) {
        try {
            const endpoint = `${this.BASE_URL}pokemon/${nombreDelPokemon}`
            const response = await fetch(endpoint);
            const data = await response.json();
            
            const moves = await Promise.all(data.moves.map(async moveData => {
                const moveResponse = await fetch(moveData.move.url);
                const moveDetails = await moveResponse.json();
                return new Move(moveDetails.id, moveDetails.name, moveDetails.learned_by_pokemon);
            }));
            const pokemon = new Pokemon(data.id, data.name, data.sprites.front_default, moves);

            return pokemon;

        } catch (error) {
            console.error('Error al obtener el Pokémon:', error);
        }
    }

    async obtenerPokemonPorMovimiento(nombreDelMovimiento) {
        try {
            const endpoint = `${this.BASE_URL}move/${nombreDelMovimiento}`
            const response = await fetch( endpoint );
            const data = await response.json();

            const learnedByPokemon = data.learned_by_pokemon;
        
            return learnedByPokemon;

        } catch (error) {
            console.error('Error al obtener Pokémon por movimiento:', error);
            throw error;
        }
    }

    async buscarPokemonsPorMovimiento( movimiento ){
        const pokemons = await this.obtenerPokemonPorMovimiento(movimiento);
        const texto = `Los pokemons que saben ${movimiento} son: ${pokemons.map(pokemon => `\n${pokemon.name}`)}`;
            console.log(texto);
    }

    async buscarPokemonsPorNombre( nombre ){
        const pokemon = await this.obtenerPokemonPorNombre(nombre);
        const nombreMovimientos = pokemon.moves.map(mov => `\n${mov.name}`);
        const texto = `pokemon "${pokemon.name},\nid: ${pokemon.id},\nimagen: ${pokemon.image},\nmovimientos: ${nombreMovimientos}`;
        console.log(texto);
    }
    
}


const api = new PokeAPI();

function mainMenu() {
    console.log("\nSeleccione una opción:");
    console.log("1. Buscar Pokémon por nombre");
    console.log("2. Buscar Pokémon por movimiento");
    console.log("3. Salir");

    rl.question("\nIngrese su elección: ", (choice) => {
        switch (choice) {
            case '1':
                rl.question("Ingrese el nombre del Pokémon: ", (nombreDelPokemon) => {
                    api.buscarPokemonsPorNombre(nombreDelPokemon).then(() => {
                        mainMenu();
                    });
                });
                break;
            case '2':
                rl.question("Ingrese el nombre del movimiento: ", (nombreDelMovimiento) => {
                    api.buscarPokemonsPorMovimiento(nombreDelMovimiento).then(() => {
                        mainMenu();
                    });
                });
                break;
            case '3':
                console.log("¡Adiós!");
                rl.close();
                break;
            default:
                console.log("Opción no válida. Intente nuevamente.");
                mainMenu();
                break;
        }
    });
}

mainMenu();


