package monopoly;

import monopoly.casilla.Casilla;
import monopoly.casilla.Especial;
import monopoly.casilla.Impuesto;
import monopoly.casilla.accion.AccionCajaComunidad;
import monopoly.casilla.accion.AccionSuerte;
import monopoly.casilla.propiedad.Servicio;
import monopoly.casilla.propiedad.Solar;
import monopoly.casilla.propiedad.Transporte;
import partida.*;
import partida.avatar.Avatar;

import java.util.ArrayList;
import java.util.HashMap;

import static monopoly.Valor.*;


public class Tablero {

    /**********Atributos**********/
    private final ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private final HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private final Jugador banca; //Un jugador que será la banca.

    /**********Constructor**********/
    /**
     * @param banca El jugador que representa a la banca en el juego.
     *              Este jugador se encargará de gestionar las propiedades y pagos.
     */
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>(4);
        this.grupos = new HashMap<String, Grupo>(8);
        this.generarCasillas();
    }

    /**********Getter**********/
    /*Getter hecho para auxiliar a descCasilla, para verificar la existencia de la casilla*/
    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    /**********Métodos**********/

    /**
     * Método para generar todas las casillas del tablero.
     *
     * Este método es responsable de crear y organizar las casillas que formarán el tablero.
     * Está compuesto por cuatro métodos auxiliares, cada uno encargado de insertar las casillas
     * en un lado del tablero: sur, oeste, norte y este.
     *
     * Los métodos auxiliares utilizados son:
     * - {@link #insertarLadoSur()} para insertar las casillas en el lado sur.
     * - {@link #insertarLadoOeste()} para insertar las casillas en el lado oeste.
     * - {@link #insertarLadoNorte()} para insertar las casillas en el lado norte.
     * - {@link #insertarLadoEste()} para insertar las casillas en el lado este.
     */
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    /**
     * Método para insertar las casillas correspondientes al lado norte del tablero.
     * Este método crea un ArrayList llamado `ladoNorte` que contiene las casillas del lado norte del tablero.
     */
    private void insertarLadoNorte() {
        ArrayList<Casilla> ladoNorte = new ArrayList<>();

        ladoNorte.add(new Especial("Parking", 21, banca));

        Solar solar12 = new Solar("Solar12", 22, 1142440, banca);
        ladoNorte.add(solar12);

        ladoNorte.add(new AccionSuerte("Suerte", 23, banca));

        Solar solar13 = new Solar("Solar13", 24, 1142440, banca);
        ladoNorte.add(solar13);

        Solar solar14 = new Solar("Solar14", 25, 1142440, banca);
        ladoNorte.add(solar14);

        ladoNorte.add(new Transporte("Trans3", 26, SUMA_VUELTA, banca));

        Solar solar15 = new Solar("Solar15", 27, 1485172, banca);
        ladoNorte.add(solar15);

        Solar solar16 = new Solar("Solar16", 28, 1485172, banca);
        ladoNorte.add(solar16);

        ladoNorte.add(new Servicio("Serv2", 29, 0.75f * SUMA_VUELTA, banca));

        Solar solar17 = new Solar("Solar17", 30, 1485172, banca);
        ladoNorte.add(solar17);

        Grupo grupo5 = new Grupo(solar12, solar13, solar14, RED, "Red");
        Grupo grupo6 = new Grupo(solar15, solar16, solar17, BLACK, "Black");

        grupos.put(grupo5.getColorGrupo(), grupo5);
        grupos.put(grupo6.getColorGrupo(), grupo6);
        posiciones.add(ladoNorte);
    }

    /**
     * Método para insertar las casillas correspondientes al lado sur del tablero.
     * Este método crea un ArrayList llamado `ladoSur` que contiene las casillas del lado sur del tablero.
     */
    private void insertarLadoSur() {
        ArrayList<Casilla> ladoSur = new ArrayList<>();

        ladoSur.add(new Especial("Salida", 1, banca));

        Solar solar1 = new Solar("Solar1", 2, 600000, banca);
        ladoSur.add(solar1);

        ladoSur.add(new AccionCajaComunidad("Caja", 3, banca));

        Solar solar2 = new Solar("Solar2", 4, 600000, banca);
        ladoSur.add(solar2);

        ladoSur.add(new Impuesto("Imp1", 5, 0.5f * SUMA_VUELTA, banca));

        ladoSur.add(new Transporte("Trans1", 6, SUMA_VUELTA, banca));

        Solar solar3 = new Solar("Solar3", 7, 520000, banca);
        ladoSur.add(solar3);

        ladoSur.add(new AccionSuerte("Suerte", 8, banca));

        Solar solar4 = new Solar("Solar4", 9, 520000, banca);
        ladoSur.add(solar4);

        Solar solar5 = new Solar("Solar5", 10, 520000, banca);
        ladoSur.add(solar5);

        Grupo grupo1 = new Grupo(solar1, solar2, WHITE, "White");
        Grupo grupo2 = new Grupo(solar3, solar4, solar5, BLUE, "Blue");

        grupos.put(grupo1.getColorGrupo(), grupo1);
        grupos.put(grupo2.getColorGrupo(), grupo2);
        posiciones.add(ladoSur);
    }

    /**
     * Método para insertar las casillas correspondientes al lado oeste del tablero.
     * Este método crea un ArrayList llamado `ladoOeste` que contiene las casillas del lado oeste del tablero.
     */
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();

        ladoOeste.add(new Especial("Cárcel", 11, banca));

        Solar solar6 = new Solar("Solar6", 12, 676000, banca);
        ladoOeste.add(solar6);

        ladoOeste.add(new Servicio("Serv1", 13, 0.75f * SUMA_VUELTA, banca));

        Solar solar7 = new Solar("Solar7", 14, 676000, banca);
        ladoOeste.add(solar7);

        Solar solar8 = new Solar("Solar8", 15, 676000, banca);
        ladoOeste.add(solar8);

        ladoOeste.add(new Transporte("Trans2", 16, SUMA_VUELTA, banca));

        Solar solar9 = new Solar("Solar9", 17, 878800, banca);
        ladoOeste.add(solar9);

        ladoOeste.add(new AccionCajaComunidad("Caja", 18, banca));

        Solar solar10 = new Solar("Solar10", 19, 878800, banca);
        ladoOeste.add(solar10);

        Solar solar11 = new Solar("Solar11", 20, 878800, banca);
        ladoOeste.add(solar11);

        Grupo grupo3 = new Grupo(solar6, solar7, solar8, PURPLE, "Purple");
        Grupo grupo4 = new Grupo(solar9, solar10, solar11, YELLOW, "Yellow");

        grupos.put(grupo3.getColorGrupo(), grupo3);
        grupos.put(grupo4.getColorGrupo(), grupo4);
        posiciones.add(ladoOeste);
    }

    /**
     * Método para insertar las casillas correspondientes al lado este del tablero.
     * Este método crea un ArrayList llamado `ladoEste` que contiene las casillas del lado este del tablero.
     */
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();

        ladoEste.add(new Especial("IrCarcel", 31, banca));

        Solar solar18 = new Solar("Solar18", 32, 1930723.6f, banca);
        ladoEste.add(solar18);

        Solar solar19 = new Solar("Solar19", 33, 1930723.6f, banca);
        ladoEste.add(solar19);

        ladoEste.add(new AccionCajaComunidad("Caja", 34, banca));

        Solar solar20 = new Solar("Solar20", 35, 1930723.6f, banca);
        ladoEste.add(solar20);

        ladoEste.add(new Transporte("Trans4", 36, SUMA_VUELTA, banca));

        ladoEste.add(new AccionSuerte("Suerte", 37, banca));

        Solar solar21 = new Solar("Solar21", 38, 3764911.02f, banca);
        ladoEste.add(solar21);

        ladoEste.add(new Impuesto("Imp2", 39, SUMA_VUELTA, banca));

        Solar solar22 = new Solar("Solar22", 40, 3764911.02f, banca);
        ladoEste.add(solar22);

        Grupo grupo7 = new Grupo(solar18, solar19, solar20, GREEN, "Green");
        Grupo grupo8 = new Grupo(solar21, solar22, BLUE, "Blue");

        grupos.put(grupo7.getColorGrupo(), grupo7);
        grupos.put(grupo8.getColorGrupo(), grupo8);
        posiciones.add(ladoEste);
    }

    /**
     * Método que genera una representación en forma de cadena del tablero de juego.
     * Este método sobrescribe el método toString() para crear una visualización del tablero
     * en la consola. Utiliza caracteres especiales para formar la estructura del tablero,
     * incluyendo casillas, líneas divisorias y avatares de los jugadores.
     *
     * @return Una cadena que representa el estado actual del tablero.
     */
    @Override
    public String toString() {
        String tablero = """
                    %s
                    │%12s│%21s│%12s│%21s│%21s│%12s│%21s│%21s│%12s│%21s│%12s│
                    │%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│
                    %s
                    │%21s│                                                                                                                    │%21s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%21s│                                                                                                                    │%21s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%12s│                                                                                                                    │%12s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%21s│              .___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____                 │%21s│
                    │%12s│              |   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   /                 │%12s│
                    %s              |  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /                  %s
                    │%12s│              |  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/                   │%12s│
                    │%12s│              |  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |                     │%12s│
                    %s              |__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|                     %s
                    │%21s│                                                                                                                    │%12s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%21s│                                                                                                                    │%21s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%12s│                                                                                                                    │%12s│
                    │%12s│                                                                                                                    │%12s│
                    %s                                                                                                                    %s
                    │%21s│                                                                                                                    │%21s│
                    │%12s│                                                                                                                    │%12s│
                    %s
                    │%12s│%21s│%21s│%12s│%21s│%12s│%12s│%21s│%12s│%21s│%12s│
                    │%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│%12s│
                    %s
                    """.formatted(
                "─".repeat(144),
                //Norte (con la última casilla el inicio del este)
                pintarCasilla(posiciones.get(2).get(0)),
                //posiciones.get(2).get(1).getGrupo().getColorGrupo() + posiciones.get(2).get(1).getNombre() + RESET,
                pintarCasilla(posiciones.get(2).get(1)),
                pintarCasilla(posiciones.get(2).get(2)),
                pintarCasilla(posiciones.get(2).get(3)),
                pintarCasilla(posiciones.get(2).get(4)),
                pintarCasilla(posiciones.get(2).get(5)),
                pintarCasilla(posiciones.get(2).get(6)),
                pintarCasilla(posiciones.get(2).get(7)),
                pintarCasilla(posiciones.get(2).get(8)),
                pintarCasilla(posiciones.get(2).get(9)),

                pintarCasilla(posiciones.get(3).get(0)),

                imprimirAvatares(posiciones.get(2).get(0)),
                imprimirAvatares(posiciones.get(2).get(1)),
                imprimirAvatares(posiciones.get(2).get(2)),
                imprimirAvatares(posiciones.get(2).get(3)),
                imprimirAvatares(posiciones.get(2).get(4)),
                imprimirAvatares(posiciones.get(2).get(5)),
                imprimirAvatares(posiciones.get(2).get(6)),
                imprimirAvatares(posiciones.get(2).get(7)),
                imprimirAvatares(posiciones.get(2).get(8)),
                imprimirAvatares(posiciones.get(2).get(9)),

                imprimirAvatares(posiciones.get(3).get(0)),

                "─".repeat(144),

                //Este y oeste simultáneo

                pintarCasilla(posiciones.get(1).get(9)),
                pintarCasilla(posiciones.get(3).get(1)),

                imprimirAvatares(posiciones.get(1).get(9)),
                imprimirAvatares(posiciones.get(3).get(1)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(8)),
                pintarCasilla(posiciones.get(3).get(2)),

                imprimirAvatares(posiciones.get(1).get(8)),
                imprimirAvatares(posiciones.get(3).get(2)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(7)),
                pintarCasilla(posiciones.get(3).get(3)),

                imprimirAvatares(posiciones.get(1).get(7)),
                imprimirAvatares(posiciones.get(3).get(3)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(6)),
                pintarCasilla(posiciones.get(3).get(4)),

                imprimirAvatares(posiciones.get(1).get(6)),
                imprimirAvatares(posiciones.get(3).get(4)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(5)),
                pintarCasilla(posiciones.get(3).get(5)),

                imprimirAvatares(posiciones.get(1).get(5)),
                imprimirAvatares(posiciones.get(3).get(5)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(4)),
                pintarCasilla(posiciones.get(3).get(6)),

                imprimirAvatares(posiciones.get(1).get(4)),
                imprimirAvatares(posiciones.get(3).get(6)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(3)),
                pintarCasilla(posiciones.get(3).get(7)),

                imprimirAvatares(posiciones.get(1).get(3)),
                imprimirAvatares(posiciones.get(3).get(7)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(2)),
                pintarCasilla(posiciones.get(3).get(8)),

                imprimirAvatares(posiciones.get(1).get(2)),
                imprimirAvatares(posiciones.get(3).get(8)),

                "─".repeat(14),
                "─".repeat(14),

                pintarCasilla(posiciones.get(1).get(1)),
                pintarCasilla(posiciones.get(3).get(9)),

                imprimirAvatares(posiciones.get(1).get(1)),
                imprimirAvatares(posiciones.get(3).get(9)),

                //Sur (con la primera casilla inicio oeste)

                "─".repeat(144),

                pintarCasilla(posiciones.get(1).get(0)),

                pintarCasilla(posiciones.get(0).get(9)),
                pintarCasilla(posiciones.get(0).get(8)),
                pintarCasilla(posiciones.get(0).get(7)),
                pintarCasilla(posiciones.get(0).get(6)),
                pintarCasilla(posiciones.get(0).get(5)),
                pintarCasilla(posiciones.get(0).get(4)),
                pintarCasilla(posiciones.get(0).get(3)),
                pintarCasilla(posiciones.get(0).get(2)),
                pintarCasilla(posiciones.get(0).get(1)),
                pintarCasilla(posiciones.get(0).get(0)),

                imprimirAvatares(posiciones.get(1).get(0)),

                imprimirAvatares(posiciones.get(0).get(9)),
                imprimirAvatares(posiciones.get(0).get(8)),
                imprimirAvatares(posiciones.get(0).get(7)),
                imprimirAvatares(posiciones.get(0).get(6)),
                imprimirAvatares(posiciones.get(0).get(5)),
                imprimirAvatares(posiciones.get(0).get(4)),
                imprimirAvatares(posiciones.get(0).get(3)),
                imprimirAvatares(posiciones.get(0).get(2)),
                imprimirAvatares(posiciones.get(0).get(1)),
                imprimirAvatares(posiciones.get(0).get(0)),

                "─".repeat(144)

        );

        // Formatear el tablero con las casillas
        return tablero;
    }

    /**
     * Busca una casilla en el tablero por su nombre.
     *
     * @param nombre El nombre de la casilla que se desea encontrar.
     * @return La casilla que coincide con el nombre proporcionado, o null si no se encuentra.
     */
    public Casilla encontrar_casilla(String nombre){
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                if (casilla.getNombre().equals(nombre)) {
                    return casilla;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve una representación en forma de cadena de la casilla,
     * pintada con el color correspondiente si pertenece a un grupo.
     *
     * @param casilla La casilla que se desea pintar.
     * @return Una cadena que representa la casilla,
     *         con el color del grupo si está asociado;
     *         de lo contrario, el nombre de la casilla sin formato.
     */
    private String pintarCasilla(Casilla casilla) {
        if (casilla instanceof Solar solar) {
            return (solar.getGrupo().getColorGrupo() + solar.getNombre() + RESET);
        } else {
            return casilla.getNombre();
        }
    }

    /**
     * Genera una cadena que representa los avatares presentes en una casilla.
     *
     * @param casilla La casilla de la que se desea obtener los avatares.
     * @return Una cadena que contiene los ID de los avatares presentes en la casilla,
     *         separados por el carácter '&'.
     */
    private String imprimirAvatares(Casilla casilla) {
        ArrayList<Avatar> avatares = casilla.getAvatares();
        StringBuilder str = new StringBuilder();
        for (Avatar avatar : avatares) {
            str.append("&");
            str.append(avatar.getId());
        }
        return str.toString();
    }

}
