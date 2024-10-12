package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private String nombreGrupo; //Nombre del grupo
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
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo, String nombreGrupo) {
        this.numCasillas = 2;
        this.colorGrupo = colorGrupo;
        this.nombreGrupo = nombreGrupo;
        this.miembros = new ArrayList<Casilla>();
        anhadirCasilla(cas1);
        anhadirCasilla(cas2);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    //public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
    //}
    //temporal
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo, String nombreGrupo) {
        this.numCasillas = 3;
        this.colorGrupo = colorGrupo;
        this.nombreGrupo = nombreGrupo;
        this.miembros = new ArrayList<Casilla>();
        anhadirCasilla(cas1);
        anhadirCasilla(cas2);
        anhadirCasilla(cas3);
    }



    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        if(!this.miembros.contains(miembro)) {
            this.miembros.add(miembro);
            miembro.setGrupo(this);
        }
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        int contador = 0;
        for(Casilla casilla : this.miembros) {
            if(casilla.getDuenho() == jugador) {
                contador++;
            }
        }
        if(contador == this.numCasillas) {
            return true;
        }
        return false;
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

    public String getNombreGrupo() {
        return nombreGrupo;
    }
}
