package partida;
import java.util.Random;

public class Dado {

    /**********Atributos**********/
    private int valor;
    private final Random random = new Random();

    /**********Getter**********/
    public int getValor() {
        return valor;
    }

    /**********Método**********/
    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        this.valor = this.random.nextInt(6) + 1;
        //this.valor = 2; prueba encarcelar
        return valor;
    }

}


