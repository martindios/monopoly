package monopoly.casilla;

import partida.avatar.Avatar;
import partida.Jugador;

import java.util.Objects;

import static monopoly.Valor.SUMA_VUELTA;

/*Salida, parking, cárcel e irCárcel*/
public class Especial extends Casilla{

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);

        switch(nombre) {
            case "Cárcel" -> this.setImpuesto(SUMA_VUELTA * 0.25f);
            case "Parking" -> this.setValor(0); //Bote que recibe el jugador cuando cae en la casilla.
        }
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        if (this.getNombre().equals("Cárcel")) {
            return (jugador.getFortuna() > this.getImpuesto()) || (jugador.getTiradasCarcel() < 3);
        } else {
            return true;
        }
    }

    @Override
    public String infoCasilla() throws Exception {
        System.out.println(super.infoCasilla());
        if (Objects.equals(this.getNombre(), "Cárcel")) {
            StringBuilder carcel = new StringBuilder();
            carcel.append("Tipo: Especial,\n");
            carcel.append("salir: ").append(this.getImpuesto()).append(",\n");
            carcel.append("jugadores: ");
            for(Avatar avatar : this.getAvatares()) {
                if(avatar.getJugador().getEnCarcel()) {
                    carcel.append("[").append(avatar.getJugador().getNombre())
                            .append(", ").append(avatar.getJugador().getTiradasCarcel()).append("] ");
                }
                else {
                    carcel.append("[").append(avatar.getJugador().getNombre()).append("] ");
                }
            }
            return carcel.toString();
        } else if (Objects.equals(this.getNombre(), "Parking")) {
            StringBuilder parking = new StringBuilder();
            parking.append("Tipo: Especial,\n");
            parking.append("bote: ").append(this.getValor()).append(",\n");
            parking.append("jugadores: [");
            for(Avatar avatar : this.getAvatares()) {
                parking.append(avatar.getJugador().getNombre()).append(", ");
            }

            if(!this.getAvatares().isEmpty()) {
                parking.setLength(parking.length() - 2);
            }
            parking.append("]\n");
            return parking.toString();
        } else {
            throw new Exception("Esta casilla no necesita descripción");
        }
    }

    public void evaluarParking(Jugador jugador) throws Exception {
        if (this.getValor() > 0) {
            //Súmaselle ao xogador o que hai no parking
            jugador.sumarFortuna(this.getValor());
            jugador.sumarPremiosInversionesOBote(this.getValor());
            System.out.println("El jugador " + jugador.getNombre() + " ha recibido " + this.getValor() + " de la banca, como bote del Parking.");
            Jugador banca = this.getDuenho();
            //Réstaselle á banca o que hai que pagar
            banca.sumarFortuna(-this.getValor());
            //o valor do parking ponse a 0. este valor é SIMBÓLICO
            this.setValor(0);
        } else {
            throw new Exception("El bote está vacío. No se entrega nada.");
        }
    }
}
