package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    //public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
    //}
    //temporal
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.numCasillas = 2;
        this.colorGrupo = colorGrupo;
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    //public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
    //}
    //temporal
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.numCasillas = 3;
        this.colorGrupo = colorGrupo;
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
    }

    //Getters temporales para el tablero
    public String getColorGrupo() {
        return colorGrupo;
    }
    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        return false;
    }




    //Getter temporal
    public String getColorGrupo() {
        return colorGrupo;
    }

    /*
    GETTERS
     */

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
}
