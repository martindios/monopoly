package monopoly;

/*Almacenar valores iniciales de los grupos de los solares. Como se debe reinicir acorde al precio inicial y el número de vueltas. Debido a esto,
* es útil almacenar los valores inciales de los grupos. En la situación de que se reinicie, para poner el precio acorde al número de vueltas, podemos
* dividir el valor actual (sin contar los edificios) entre el inicial de un grupo, desta forma encontramos el "factor" por el que multiplicar */
public class Valor {
    /*Se incluyen una serie de constantes útiles para no repetir valores*/
    public static final float FORTUNA_BANCA = 500000; // Cantidad que tiene inicialmente la Banca
    public static final float FORTUNA_INICIAL = 9543076.28f; // Cantidad que recibe cada jugador al comenzar la partida
    //public static final float FORTUNA_INICIAL = 1; //Debug
    public static final float SUMA_VUELTA = 1301328.584f; // Cantidad que recibe un jugador al pasar por la Salida

    /*Colores del texto*/
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    /*Valores iniciales de los grupos*/
    public static final float VALINICIALG1 = 600000;
    public static final float VALINICIALG2 = 520000;
    public static final float VALINICIALG3 = 676000;
    public static final float VALINICIALG4 = 878800;
    public static final float VALINICIALG5 = 1142440;
    public static final float VALINICIALG6 = 1485172;
    public static final float VALINICIALG7 = 1930723.6f;
    public static final float VALINICIALG8 = 3764911.02f;

}
