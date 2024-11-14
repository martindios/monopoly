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
    private boolean avanzado;
    private boolean conseguirDinero;

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
        this.avanzado = false;
        this.conseguirDinero = false;
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

    public boolean isAvanzado() {
        return avanzado;
    }

    public boolean isConseguirDinero() {
        return conseguirDinero;
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

    /**********Setters**********/

    public void setLugar(Casilla lugar) {
        if(lugar.getPosicion() >= 1 && lugar.getPosicion() <= 40) {
            this.lugar = lugar;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAvanzado(boolean avanzado) {
        this.avanzado = avanzado;
    }

    public void setConseguirDinero(boolean conseguirDinero) {
        this.conseguirDinero = conseguirDinero;
    }

    /**********Métodos**********/

    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> tablero, int valorTirada) {
        boolean pasaPorSalidaReves = false, pasaPorSalidaDerecho = false;
        Casilla casillaOld = this.lugar;
        casillaOld.eliminarAvatar(this);
        int max = 40;
        int posicionNueva = (casillaOld.getPosicion() + valorTirada);
        if(posicionNueva == 0) {
            posicionNueva = 40;
        }
        if(posicionNueva < 0) { /*Pasa por salida al revés*/
            pasaPorSalidaReves = true;
            posicionNueva = max + posicionNueva;
        } else if (posicionNueva > max) { /*Pasa por salida del derecho*/
                pasaPorSalidaDerecho = true;
                posicionNueva = posicionNueva % max;
                if (posicionNueva == 0) posicionNueva = max;  // Si el resultado es 0, en realidad estamos en la casilla 40
        }

        for (ArrayList<Casilla> fila : tablero) {
            for (Casilla casilla : fila) {

                if (casilla.getPosicion() == posicionNueva) {

                    casilla.anhadirAvatar(this);
                    this.lugar = casilla;  // Actualiza la casilla actual del avatar
                    casilla.sumarVecesFrecuentada();
                    System.out.println("El avatar se mueve a la casilla " + casilla.getNombre() + ". Posición: " + casilla.getPosicion());

                    if (casilla.getNombre().equals("IrCarcel")) {
                        jugador.encarcelar(tablero);
                        return;
                    }
                    if(casilla.getNombre().equals("Solar") && casilla.getDuenho().equals(jugador)){
                        casilla.sumarContadorDuenho();
                    }
                    if (casilla.getNombre().equals("Parking")) {
                        if(casilla.getValor() > 0) {
                            //Súmaselle ao xogador o que hai no parking
                            jugador.sumarFortuna(casilla.getValor());
                            jugador.sumarPremiosInversionesOBote(casilla.getValor());
                            System.out.println("El jugador " + jugador.getNombre() + " ha recibido " + casilla.getValor() + " de la banca, como bote del Parking.");
                            Jugador banca = casilla.getDuenho();
                            //Réstaselle á banca o que hai que pagar
                            banca.sumarFortuna(-casilla.getValor());
                            //o valor do parking ponse a 0. este valor é SIMBÓLICO
                            casilla.setValor(0);
                        }
                        else {
                            System.out.println("El bote está vacío. No se entrega nada.");
                        }
                    }

                    if (pasaPorSalidaDerecho) {
                        jugador.setVueltas(jugador.getVueltas() + 1);
                        jugador.sumarFortuna(SUMA_VUELTA);
                        jugador.sumarPasarPorCasillaDeSalida(SUMA_VUELTA);
                        System.out.println("El jugador ha completado una vuelta y recibe " + SUMA_VUELTA);
                    }
                    if (pasaPorSalidaReves) {
                        jugador.setVueltas(jugador.getVueltas() - 1);
                        if (jugador.getFortuna() < SUMA_VUELTA) {
                            System.out.println("El jugador no tiene suficiente dinero para pagar la vuelta. " +
                                    "Debe vender edificio, hipotecar propiedades o declarse en bancarrota.");
                            conseguirDinero = true;
                            return;
                        }

                    }

                    return ;
                }
            }
        }
    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> tablero, Casilla casilla, boolean cobraSalida) {
        if (cobraSalida) {
            if((casilla.getPosicion() - this.getLugar().getPosicion()) < 0) { //Pasa por la salida
                jugador.setVueltas(jugador.getVueltas() + 1);
                jugador.sumarFortuna(SUMA_VUELTA);
                jugador.sumarPasarPorCasillaDeSalida(SUMA_VUELTA);
                System.out.println("El jugador ha completado una vuelta y recibe " + SUMA_VUELTA);
            }
        }

        if(casilla.getNombre().equals("Solar") && casilla.getDuenho().equals(jugador)){
            casilla.sumarContadorDuenho();
        }

        Casilla casillaOld = this.getLugar();
        casillaOld.eliminarAvatar(this);
        for (ArrayList<Casilla> casillas : tablero) {
            for (Casilla cas : casillas) {
                if (cas.getNombre().equals(casilla.getNombre())) {
                    casilla.anhadirAvatar(this);
                    this.lugar = casilla;
                    casilla.sumarVecesFrecuentada();
                    System.out.println("El avatar se mueve a la casilla " + casilla.getNombre() + ". Posición: " + casilla.getPosicion());
                    return ;
                }
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

}
