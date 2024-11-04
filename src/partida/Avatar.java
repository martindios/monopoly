package partida;

import monopoly.*;

import static monopoly.Valor.*;
import java.util.ArrayList;
import java.util.Random;



public class Avatar {

    /**********Atributos**********/
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.
    private int avanzado = 0; //Si está en 0, no está en modo avanzado, mientras que si está en 1 sí lo está.

    /**********Constructor**********/

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    //El ArrayList se encarga de almacenar todos los avatares creados. Con esto, creamos la variable id para generar un ID ÚNICO con la función generarID,
    //pasando el ArrayList para que la función evite la repetición
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        //Generamos o id do avatar co metodo e asígnase dentro deste, pasándolle o array de avatares xa creados, para asi dsp coller o seu id e non repetilos
        generarId(avCreados);
        lugar.anhadirAvatar(this);
    }

    /**********Getters**********/

    //getter para devolver el id de un jugador
    public String getId() {
        return id;
    }

    //getter para devolver el tipo de avatar de un jugador
    public String getTipo() {
        return tipo;
    }

    //getter para devolver el jugador asociado al avatar
    public Jugador getJugador() {
        return jugador;
    }

    //getters para devolver el lugar en el que se encuentra el avatar
    public Casilla getLugar() {
        return lugar;
    }

    public int getAvanzado() {
        return avanzado;
    }

    public String infoAvatar() {
        return """
                {
                    Id: %s,
                    Tipo: %s,
                    Casilla: %s,
                    Jugador: %s
                }""".formatted(id, tipo, lugar.getNombre(), jugador.getNombre());
    }

    //SETTERS
    public void setLugar(Casilla lugar) {
        if(lugar.getPosicion() >= 1 && lugar.getPosicion() <= 40) {
            this.lugar = lugar;
        }
    }


    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        Casilla casillaOld = this.lugar;
        casillaOld.eliminarAvatar(this);
        int max = 40;
        int posicionNueva = (casillaOld.getPosicion() + valorTirada);
        boolean pasaPorSalida = posicionNueva > max;
        if (pasaPorSalida) {
            posicionNueva = posicionNueva % max;
            if (posicionNueva == 0) posicionNueva = max;  // Si el resultado es 0, en realidad estamos en la casilla 40
        }
        for (ArrayList<Casilla> fila : casillas) {
            for (Casilla casilla : fila) {
                if (casilla.getPosicion() == posicionNueva) {
                    if (casilla.getNombre().equals("IrCarcel")) {
                        jugador.encarcelar(casillas);
                        return;
                    }
                    if(casilla.getNombre().equals("Solar") && casilla.getDuenho().equals(jugador)){
                        casilla.sumarContadorDuenho();
                    }
                    if (casilla.getNombre().equals("Parking")) {
                        if(casilla.getValor() > 0) {
                            jugador.sumarFortuna(casilla.getValor());
                            System.out.println("El jugador " + jugador.getNombre() + " ha recibido " + casilla.getValor() + " de la banca, como bote del Parking.");
                            Jugador banca = casilla.getDuenho();
                            banca.sumarFortuna(-casilla.getValor());
                            casilla.setValor(0);
                        }
                        else {
                            System.out.println("El bote está vacío. No se entrega nada.");
                        }
                    }
                    if (pasaPorSalida) {
                        jugador.setVueltas(jugador.getVueltas() + 1);
                        jugador.sumarFortuna(SUMA_VUELTA);
                        System.out.println("El jugador ha completado una vuelta y recibe " + SUMA_VUELTA);
                    }
                    casilla.anhadirAvatar(this);
                    this.lugar = casilla;  // Actualiza la casilla actual del avatar
                    System.out.println("El avatar se mueve a la casilla " + casilla.getNombre() + ". Posición: " + casilla.getPosicion());
                    return;
                }
            }
        }
    }

    public void moverJugadorCoche(ArrayList<ArrayList<Casilla>> casillas, int dado1, int dado2, int lanzamientos){
        int valorTirada = dado1 + dado2;
        Dado tiradaAdicional1 = new Dado();
        Dado tiradaAdicional2 = new Dado();


        //Caso en el que el valor de la tirada es menor o igual que 4
        if (valorTirada <= 4){
            moverAvatar(casillas, -valorTirada); //Si no va a la cárcel retrocede el numero de casillas correspondiente
        }

        //En el caso de que el valor de la tirada sea mayor que 4 se tiene que contar un máximo de 4 tiradas
        while (lanzamientos <= 4){
            if (valorTirada > 4) {
                moverAvatar(casillas, valorTirada); //Si no va a la cárcel avanza el numero de casillas correspondiente
                if (lanzamientos == 4 && (dado1 == dado2)){
                    int valor1 = tiradaAdicional1.hacerTirada();
                    int valor2 = tiradaAdicional2.hacerTirada();
                    System.out.println("Dado 1: " + valor1);
                    System.out.println("Dado 2: " + valor2);

                    this.moverAvatar(casillas, (valor1 + valor2));

                }

                lanzamientos++;

            } else if (valorTirada <= 4 && lanzamientos == 4) {
                int valor1 = tiradaAdicional1.hacerTirada();
                int valor2 = tiradaAdicional2.hacerTirada();
                System.out.println("Dado 1: " + valor1);
                System.out.println("Dado 2: " + valor2);

                this.moverAvatar(casillas, (valor1 + valor2));
            }
            else {
                break;
            }
        }

    }

    public void moverJugadorPelota(ArrayList<ArrayList<Casilla>> casillas, int dado1, int dado2) {
        int valorTirada = dado1 + dado2;
        int i = 4;
        int j = 0;
        int k = 0;
        Dado tiradaDobles1 = new Dado();
        Dado tiradaDobles2 = new Dado();
        int valor1 = tiradaDobles1.hacerTirada();
        int valor2 = tiradaDobles2.hacerTirada();

        if (valorTirada > 4){
            while(i != valorTirada){
                i ++;
                if (i % 2 != 0) {
                    if(i == 5){
                        moverAvatar(casillas, 5);
                    }else{
                        moverAvatar(casillas, 2);
                    }
                }if((i == valorTirada) && (i % 2 == 0)){
                    moverAvatar(casillas, 1);
                }
            }


        }
        if (valorTirada <= 4){
            while(j != valorTirada){
                j ++;
                if (j % 2 != 0) {
                    if(j == 1){
                        moverAvatar(casillas, -1);
                    }else{
                        moverAvatar(casillas, -2);
                    }
                }if((j == valorTirada) && (j % 2 == 0)){
                    moverAvatar(casillas, -1);
                }
            }

        }

        if(dado1 == dado2){
            while(valor1 == valor2 && k != 3){
                k++;
                System.out.println("Dado 1: " + valor1);
                System.out.println("Dado 2: " + valor2);
                moverJugadorPelota(casillas, valor1, valor2);
                valor1 = tiradaDobles1.hacerTirada();
                valor2 = tiradaDobles2.hacerTirada();
            }

            if(k == 3){
                jugador.encarcelar(casillas);
                System.out.println("El jugador ha sido encarcelado.");

            }


        }

    }
    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {

        Random random = new Random();
        int numeroAleatorio = random.nextInt(26) + 65;  // Genera un número entre 65 y 90
        String idCreado = String.valueOf((char) numeroAleatorio);;  // Convierte el número a su valor de String correspondiente

        //Recorre la lista de avatares para comprobar si el id ya existe. Si existe, se llama recursivamente a la función para que cree uno nuevo
        for(Avatar avatar : avCreados){
            if(idCreado.equalsIgnoreCase(avatar.getId())){
                generarId(avCreados);
                return ;
            }
        }
        setId(idCreado);
    }

    /*
    GETTERS
     */



    /*
    SETTERS
     */

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAvanzado(int avanzado) {
        this.avanzado = avanzado;
    }
}
