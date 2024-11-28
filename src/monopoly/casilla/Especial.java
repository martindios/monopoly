package monopoly.casilla;

import partida.Avatar;
import partida.Jugador;

import java.util.Objects;

import static monopoly.Valor.SUMA_VUELTA;

/*Salida, parking, cárcel e irCárcel*/
public class Especial extends Casilla{

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);

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
    public String infoCasilla() {
        System.out.println(super.infoCasilla());
        if (Objects.equals(this.getNombre(), "Cárcel")) {
            StringBuilder carcel = new StringBuilder();
            carcel.append("Tipo: ").append(this.getTipo().toLowerCase()).append(",\n");
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
            parking.append("Tipo: ").append(this.getTipo().toLowerCase()).append(",\n");
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
            System.out.println("Esta casilla no necesita descripción");
            return "";
        }
    }
}
