package monopoly;

import monopoly.casilla.Casilla;
import partida.*;
import java.util.ArrayList;


public class Grupo {

    /**********Atributos**********/
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private String nombreGrupo; //Nombre del grupo
    private int numCasillas; //Número de casillas del grupo.
    private ArrayList<Edificio> edificiosGrupo;


    /**********Constructores**********/

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo, String nombreGrupo) {
        this.numCasillas = 2;
        this.colorGrupo = colorGrupo;
        this.nombreGrupo = nombreGrupo;
        this.miembros = new ArrayList<Casilla>();
        this.edificiosGrupo = new ArrayList<>();
        anhadirCasilla(cas1);
        anhadirCasilla(cas2);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo, String nombreGrupo) {
        this.numCasillas = 3;
        this.colorGrupo = colorGrupo;
        this.nombreGrupo = nombreGrupo;
        this.miembros = new ArrayList<Casilla>();
        this.edificiosGrupo = new ArrayList<>();
        anhadirCasilla(cas1);
        anhadirCasilla(cas2);
        anhadirCasilla(cas3);
    }

    /**********Getters**********/

    //getter para devolver los miembros que pertenecen a un grupo
    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }

    //getter para devolver el color correspondiente a un grupo
    public String getColorGrupo() {
        return colorGrupo;
    }

    //getter para devolver el número de casillas que pertenecen a un grupo
    public int getNumCasillas() {
        return numCasillas;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public ArrayList<Edificio> getEdificiosGrupo() {
        return edificiosGrupo;
    }

    /**********Métodos**********/

    /**
     * Método que añade una casilla al array de casillas miembro de un grupo.
     *
     * @param miembro La casilla que se quiere añadir al grupo.
     */
    public void anhadirCasilla(Casilla miembro) {
        if(!this.miembros.contains(miembro)) {
            this.miembros.add(miembro);
            miembro.setGrupo(this);
        }
    }

    /**
     * Método que comprueba si el jugador pasado tiene todas las casillas del grupo.
     *
     * @param jugador El jugador que se quiere evaluar.
     * @return true si el jugador es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        int contador = 0;
        for(Casilla casilla : this.miembros) {
            if(casilla.getDuenho() == jugador) {
                contador++;
            }
        }
        return contador == this.numCasillas;
    }

    public int getNumEdificios(ArrayList<Edificio> edificios, String tipoEdificio) {
        int numEdificios = 0;
        for (Edificio edificio : edificios) {
            if (edificio.getTipo().contains(tipoEdificio)) {
                numEdificios++;
            }
        }
        return numEdificios;
    }

}
