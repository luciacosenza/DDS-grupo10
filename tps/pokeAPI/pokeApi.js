class PokemonAPI {
    constructor() {
        this.baseUrl = 'https://pokeapi.co/api/v2/';
    }

    async obtenerPokemonPorNombre(nombreDelPokemon) {
        try {
            const response = await fetch(`${this.baseUrl}pokemon/${nombreDelPokemon}`);
            const data = await response.json();
            return {
                id: data.id,
                nombre: data.name,
                imagenUrl: data.sprites.front_default
            };
        } catch (error) {
            console.error('Ha ocurrido un error obteniendo el pokemon por nombre:', error);
            throw error;
        }
    }

    async obtenerPokemonsPorMovimiento(nombreDelMovimiento) {
        try {
            const response = await fetch(`${this.baseUrl}move/${nombreDelMovimiento}`);
            const data = await response.json();
            return {
                id: data.id,
                nombre: data.name,
                descripcion: data.effect_entries[0].effect,
                tipo: data.type.name
                };
            } catch (error) {
                console.error('Ha ocurrido un error obteniendo los pokemons por movimiento:', error);
                throw error;
            }
                
    }


}

// Ejemplo de uso:
const api = new PokemonAPI();


api.obtenerPokemonPorNombre('pikachu')
    .then(pokemon => console.log(pokemon))
    .catch(error => console.error(error));

api.obtenerPokemonsPorMovimiento('pound')
    .then(movement => console.log(movement))
    .catch(error => console.error(error));