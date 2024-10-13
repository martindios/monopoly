package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;

import static monopoly.Valor.*;


public class Tablero {

    /**********Atributos**********/
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    /**********Constructor**********/
    /*Únicamente le pasamos el jugador banca (que se creará desde el menú)*/
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

    /*Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado)*/
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(new Casilla("Parking", "Especiales", 21, banca));
        Casilla solar12 = new Casilla("Solar12", "Solar", 22, 1142440, banca);
        ladoNorte.add(solar12);
        ladoNorte.add(new Casilla("Suerte", "Suerte", 23, banca));
        Casilla solar13 = new Casilla("Solar13", "Solar", 24, 1142440, banca);
        ladoNorte.add(solar13);
        Casilla solar14 = new Casilla("Solar14", "Solar", 25, 1142440, banca);
        ladoNorte.add(solar14);
        ladoNorte.add(new Casilla("Trans3", "Transporte", 26, SUMA_VUELTA, banca));
        Casilla solar15 = new Casilla("Solar15", "Solar", 27, 1485172, banca);
        ladoNorte.add(solar15);
        Casilla solar16 = new Casilla("Solar16", "Solar", 28, 1485172, banca);
        ladoNorte.add(solar16);
        ladoNorte.add(new Casilla("Serv2", "Servicios", 29, 0.75f*SUMA_VUELTA, banca));
        Casilla solar17 = new Casilla("Solar17", "Solar", 30, 1485172, banca);
        ladoNorte.add(solar17);
        Grupo grupo5 = new Grupo(solar12, solar13, solar14, RED, "Red");
        Grupo grupo6 = new Grupo(solar15, solar16, solar17, BLACK, "Black");



        grupos.put(grupo5.getColorGrupo(), grupo5);
        grupos.put(grupo6.getColorGrupo(), grupo6);
        posiciones.add(ladoNorte);
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> ladoSur = new ArrayList<>();
        ladoSur.add(new Casilla("Salida", "Especiales", 1, banca));
        Casilla solar1 = new Casilla("Solar1", "Solar", 2, 600000, banca);
        ladoSur.add(solar1);
        ladoSur.add(new Casilla("Caja", "Comunidad", 3, banca));
        Casilla solar2 = new Casilla("Solar2", "Solar", 4, 600000, banca);
        ladoSur.add(solar2);
        ladoSur.add(new Casilla("Imp1", 5, 0.5f*SUMA_VUELTA, banca));
        ladoSur.add(new Casilla("Trans1", "Transporte", 6, SUMA_VUELTA, banca));
        Casilla solar3 = new Casilla("Solar3", "Solar", 7, 520000, banca);
        ladoSur.add(solar3);
        ladoSur.add(new Casilla("Suerte", "Suerte", 8, banca));
        Casilla solar4 = new Casilla("Solar4", "Solar", 9, 520000, banca);
        ladoSur.add(solar4);
        Casilla solar5 = new Casilla("Solar5", "Solar", 10, 520000, banca);
        ladoSur.add(solar5);
        Grupo grupo1 = new Grupo(solar1, solar2, WHITE, "White");
        Grupo grupo2 = new Grupo(solar3, solar4, solar5, BLUE, "Blue");

        grupos.put(grupo1.getColorGrupo(), grupo1);
        grupos.put(grupo2.getColorGrupo(), grupo2);
        posiciones.add(ladoSur);
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(new Casilla("Cárcel", "Especiales", 11, banca));
        Casilla solar6 = new Casilla("Solar6", "Solar", 12, 676000, banca);
        ladoOeste.add(solar6);
        ladoOeste.add(new Casilla("Serv1", "Servicios", 13, 0.75f*SUMA_VUELTA, banca));
        Casilla solar7 =  new Casilla("Solar7", "Solar", 14, 676000, banca);
        ladoOeste.add(solar7);
        Casilla solar8 = new Casilla("Solar8", "Solar", 15, 676000, banca);
        ladoOeste.add(solar8);
        ladoOeste.add(new Casilla("Trans2", "Transporte", 16, SUMA_VUELTA, banca));
        Casilla solar9 = new Casilla("Solar9", "Solar", 17, 878800, banca);
        ladoOeste.add(solar9);
        ladoOeste.add(new Casilla("Caja", "Comunidad", 18, banca));
        Casilla solar10 = new Casilla("Solar10", "Solar", 19, 878800, banca);
        ladoOeste.add(solar10);
        Casilla solar11 = new Casilla("Solar11", "Solar", 20, 878800, banca);
        ladoOeste.add(solar11);
        Grupo grupo3 = new Grupo(solar6, solar7, solar8, PURPLE, "Purple");
        Grupo grupo4 = new Grupo(solar9, solar10, solar11, YELLOW, "Yellow");

        grupos.put(grupo3.getColorGrupo(), grupo3);
        grupos.put(grupo4.getColorGrupo(), grupo4);
        posiciones.add(ladoOeste);
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(new Casilla("IrCarcel", "Especiales", 31, banca));
        Casilla solar18 = new Casilla("Solar18", "Solar", 32, 1930723.6f, banca);
        ladoEste.add(solar18);
        Casilla solar19 = new Casilla("Solar19", "Solar", 33, 1930723.6f, banca);
        ladoEste.add(solar19);
        ladoEste.add(new Casilla("Caja", "Comunidad", 34, banca));
        Casilla solar20 = new Casilla("Solar20", "Solar", 35, 1930723.6f, banca);
        ladoEste.add(solar20);
        ladoEste.add(new Casilla("Trans4", "Transporte", 36, SUMA_VUELTA, banca));
        ladoEste.add(new Casilla("Suerte", "Suerte", 37, banca));
        Casilla solar21 = new Casilla("Solar21", "Solar", 38, 3764911.02f, banca);
        ladoEste.add(solar21);
        ladoEste.add(new Casilla("Imp2", 39, SUMA_VUELTA, banca));
        Casilla solar22 = new Casilla("Solar22", "Solar", 40, 3764911.02f, banca);
        ladoEste.add(solar22);
        Grupo grupo7 = new Grupo(solar18, solar19, solar20, GREEN, "Green");
        Grupo grupo8 = new Grupo(solar21, solar22, BLUE, "Blue");

        grupos.put(grupo7.getColorGrupo(), grupo7);
        grupos.put(grupo8.getColorGrupo(), grupo8);
        posiciones.add(ladoEste);
    }

    //Para imprimir el tablero, modificamos el método toString()
    @Override
    public String toString() {
        String tablero = """
                    %s
                    │%10s│%19s│%10s│%19s│%19s│%10s│%19s│%19s│%10s│%19s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│      .___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____       │%19s│
                    │%10s│      |   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   /       │%10s│
                    %s      |  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /        %s
                    │%10s│      |  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/         │%10s│
                    │%10s│      |  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |           │%10s│
                    %s      |__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|           %s
                    │%19s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%19s│                                                                                                  │%19s│
                    │%10s│                                                                                                  │%10s│
                    %s
                    │%10s│%19s│%19s│%10s│%19s│%10s│%10s│%19s│%10s│%19s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    %s
                    """.formatted(
                "─".repeat(122),
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

                "─".repeat(122),

                //Este y oeste simultáneo

                pintarCasilla(posiciones.get(1).get(9)),
                pintarCasilla(posiciones.get(3).get(1)),

                imprimirAvatares(posiciones.get(1).get(9)),
                imprimirAvatares(posiciones.get(3).get(1)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(8)),
                pintarCasilla(posiciones.get(3).get(2)),

                imprimirAvatares(posiciones.get(1).get(8)),
                imprimirAvatares(posiciones.get(3).get(2)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(7)),
                pintarCasilla(posiciones.get(3).get(3)),

                imprimirAvatares(posiciones.get(1).get(7)),
                imprimirAvatares(posiciones.get(3).get(3)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(6)),
                pintarCasilla(posiciones.get(3).get(4)),

                imprimirAvatares(posiciones.get(1).get(6)),
                imprimirAvatares(posiciones.get(3).get(4)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(5)),
                pintarCasilla(posiciones.get(3).get(5)),

                imprimirAvatares(posiciones.get(1).get(5)),
                imprimirAvatares(posiciones.get(3).get(5)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(4)),
                pintarCasilla(posiciones.get(3).get(6)),

                imprimirAvatares(posiciones.get(1).get(4)),
                imprimirAvatares(posiciones.get(3).get(6)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(3)),
                pintarCasilla(posiciones.get(3).get(7)),

                imprimirAvatares(posiciones.get(1).get(3)),
                imprimirAvatares(posiciones.get(3).get(7)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(2)),
                pintarCasilla(posiciones.get(3).get(8)),

                imprimirAvatares(posiciones.get(1).get(2)),
                imprimirAvatares(posiciones.get(3).get(8)),

                "─".repeat(12),
                "─".repeat(12),

                pintarCasilla(posiciones.get(1).get(1)),
                pintarCasilla(posiciones.get(3).get(9)),

                imprimirAvatares(posiciones.get(1).get(1)),
                imprimirAvatares(posiciones.get(3).get(9)),

                //Sur (con la primera casilla inicio oeste)

                "─".repeat(122),

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

                "─".repeat(122)

        );

        // Formatear el tablero con las casillas
        return tablero;
    }

    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        //return null;
        // Recorremos cada lado del tablero
        for (ArrayList<Casilla> lado : posiciones) {
            // Recorremos cada Casilla dentro de cada lado
            for (Casilla casilla : lado) {
                // Si el nombre de la casilla coincide con el nombre buscado, retornamos la casilla
                if (casilla.getNombre().equals(nombre)) {
                    return casilla;
                }
            }
        }
        // Si no encontramos la casilla, devolvemos null
        return null;
    }

    private String pintarCasilla(Casilla casilla) {
        if (casilla.getGrupo() != null) {
            return (casilla.getGrupo().getColorGrupo() + casilla.getNombre() + RESET);
        } else {
            return casilla.getNombre();
        }
    }

    private String imprimirAvatares(Casilla casilla) {
        ArrayList<Avatar> avatares = casilla.getAvatares();
        StringBuilder str = new StringBuilder();
        for (Avatar avatar : avatares) {
            str.append("&");
            str.append(avatar.getId());
            str.append(" ");
        }
        return str.toString();
    }

}
