package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>(4);
        this.generarCasillas();
    }

    //TEMPORAL, BORRARLO
    public Tablero() {
        this.posiciones = new ArrayList<>(4);
        this.generarCasillas();
    }


    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {

        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> ladoSur = new ArrayList<>();
        ladoSur.add(new Casilla("Salida", "Especiales", 1, banca));
        ladoSur.add(new Casilla("Solar1", "Solar", 2, 1, banca));
        ladoSur.add(new Casilla("Caja", "Solar", 3, 1, banca));
        ladoSur.add(new Casilla("Solar2", "Solar", 4, 1, banca));
        ladoSur.add(new Casilla("Imp1", "Impuestos", 5, 1, banca));
        ladoSur.add(new Casilla("Trans1", "Transporte", 6, 1, banca));
        ladoSur.add(new Casilla("Solar3", "Solar", 7, 1, banca));
        ladoSur.add(new Casilla("Suerte", "Solar", 8, 1, banca));
        ladoSur.add(new Casilla("Solar4", "Solar", 9, 1, banca));
        ladoSur.add(new Casilla("Solar5", "Solar", 10, 1, banca));

        posiciones.add(ladoSur);
    }



    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(new Casilla("Cárcel", "Especiales", 11, 1, banca));
        ladoOeste.add(new Casilla("Solar6", "Solar", 12, banca));
        ladoOeste.add(new Casilla("Serv1", "Servicios", 13, banca));
        ladoOeste.add(new Casilla("Solar7", "Solar", 14, banca));
        ladoOeste.add(new Casilla("Solar8", "Solar", 15, banca));
        ladoOeste.add(new Casilla("Trans2", "Transporte", 16, banca));
        ladoOeste.add(new Casilla("Solar9", "Solar", 17, banca));
        ladoOeste.add(new Casilla("Caja", "Solar", 2, 18, banca));
        ladoOeste.add(new Casilla("Solar10", "Solar", 19, banca));
        ladoOeste.add(new Casilla("Solar11", "Solar", 20, banca));

        posiciones.add(ladoOeste);
    }
    
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(new Casilla("Parking", "Especiales", 20, banca));
        ladoNorte.add(new Casilla("Solar12", "Solar", 21, 100, banca));
        ladoNorte.add(new Casilla("Suerte", "Suerte", 22, banca));
        ladoNorte.add(new Casilla("Solar13", "Solar", 23, 100, banca));
        ladoNorte.add(new Casilla("Solar14", "Solar", 24, 100, banca));
        ladoNorte.add(new Casilla("Trans3", "Transporte", 25, 100, banca));
        ladoNorte.add(new Casilla("Solar15", "Solar", 26, 100, banca));
        ladoNorte.add(new Casilla("Solar16", "Solar", 27, 100, banca));
        ladoNorte.add(new Casilla("Serv2", "Servicios", 28, 100, banca));
        ladoNorte.add(new Casilla("Solar17", "Solar", 29, 100, banca));

        posiciones.add(ladoNorte);
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(new Casilla("IrCarcel", "Especiales", 30, banca));
        ladoEste.add(new Casilla("Solar18", "Solar", 31, 100, banca));
        ladoEste.add(new Casilla("Solar19", "Solar", 32, 100, banca));
        ladoEste.add(new Casilla("Caja", "Solar", 33, 100, banca));
        ladoEste.add(new Casilla("Solar20", "Solar", 34, 100, banca));
        ladoEste.add(new Casilla("Trans4", "Transporte", 35, 100, banca));
        ladoEste.add(new Casilla("Suerte", "Solar", 36, 100, banca));
        ladoEste.add(new Casilla("Solar21", "Solar", 37, 100, banca));
        ladoEste.add(new Casilla("Imp2", "Impuestos", 38, 100, banca));
        ladoEste.add(new Casilla("Solar22", "Solar", 39, 100, banca));

        posiciones.add(ladoEste);
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {

        String tablero = """
                     %s
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                     %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s                                                                                                  %s
                    │%10s│                                                                                                  │%10s│
                    │%10s│                                                                                                  │%10s│
                    %s
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    │%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│%10s│
                    %s
                    """.formatted(
                "\u2500".repeat(120),
                //Norte (con la última casilla el inicio del este)
                posiciones.get(2).get(0).getNombre(),
                posiciones.get(2).get(1).getNombre(),
                posiciones.get(2).get(2).getNombre(),
                posiciones.get(2).get(3).getNombre(),
                posiciones.get(2).get(4).getNombre(),
                posiciones.get(2).get(5).getNombre(),
                posiciones.get(2).get(6).getNombre(),
                posiciones.get(2).get(7).getNombre(),
                posiciones.get(2).get(8).getNombre(),
                posiciones.get(2).get(9).getNombre(),

                posiciones.get(3).get(0).getNombre(),

                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",

                "&J",

                "\u2500".repeat(120),

                //Este y oeste simultáneo

                posiciones.get(1).get(9).getNombre(),
                posiciones.get(3).get(1).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(8).getNombre(),
                posiciones.get(3).get(2).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(7).getNombre(),
                posiciones.get(3).get(3).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(6).getNombre(),
                posiciones.get(3).get(4).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(5).getNombre(),
                posiciones.get(3).get(5).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(4).getNombre(),
                posiciones.get(3).get(6).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(3).getNombre(),
                posiciones.get(3).get(7).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(2).getNombre(),
                posiciones.get(3).get(8).getNombre(),

                "&J",
                "&J",

                "\u2500".repeat(12),
                "\u2500".repeat(12),

                posiciones.get(1).get(1).getNombre(),
                posiciones.get(3).get(9).getNombre(),

                "&J",
                "&J",

                //Sur (con la primera casilla inicio oeste)

                "\u2500".repeat(120),

                posiciones.get(1).get(0).getNombre(),

                posiciones.get(0).get(9).getNombre(),
                posiciones.get(0).get(8).getNombre(),
                posiciones.get(0).get(7).getNombre(),
                posiciones.get(0).get(6).getNombre(),
                posiciones.get(0).get(5).getNombre(),
                posiciones.get(0).get(4).getNombre(),
                posiciones.get(0).get(3).getNombre(),
                posiciones.get(0).get(2).getNombre(),
                posiciones.get(0).get(1).getNombre(),
                posiciones.get(0).get(0).getNombre(),

                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",
                "&J",

                "\u2500".repeat(120)

        );

        // Formatear el tablero con las casillas
        return tablero;
    }

        /*
        String tablero = """
                 ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______
                |%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|                                                                       |%s|
                |%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|
                """.formatted(YELLOW + "1 " + RESET, "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10 ",
                "11 ", "12 ", "13 ", "13 ", "15 ", "16 ", "17 ", "18 ", "19 ", "20 ",
                "21 ", "22 ", "23 ", "24 ", "25 ", "26 ", "27 ", "28 ", "29 ", "30 ",
                "31 ", "32 ", "33 ", "34 ", "35 ", "36 ", "37 ", "38 ", "39 ", "40 ");
        System.out.println(tablero);
        return tablero;

         */

    public String representarTablero() {
        return null; //
    }
    
    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        return null; //
    }
}

/*
*
* public String representarTablero() {
        ArrayList<String> ampersand = new ArrayList<String>();
        for (Casilla casilla: Casillas) {
            if (casilla.getJugadores().length() == 0) {
                ampersand.add(" ");
            }
            else {
                ampersand.add("&");
            }
        }
        String tablero = """
                 ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______
                |%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|                                                                                                                                                        |%s|
                |%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|
                """.formatted(SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(20).getNombre(), ampersand.get(20), Casillas.get(20).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(21).getNombre(), ampersand.get(21), Casillas.get(21).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(22).getNombre(), ampersand.get(22), Casillas.get(22).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(23).getNombre(), ampersand.get(23), Casillas.get(23).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(24).getNombre(), ampersand.get(24), Casillas.get(24).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(25).getNombre(), ampersand.get(25), Casillas.get(25).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(26).getNombre(), ampersand.get(26), Casillas.get(26).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(27).getNombre(), ampersand.get(27), Casillas.get(27).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(28).getNombre(), ampersand.get(28), Casillas.get(28).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(29).getNombre(), ampersand.get(29), Casillas.get(29).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(30).getNombre(), ampersand.get(30), Casillas.get(30).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(19).getNombre(), ampersand.get(19), Casillas.get(19).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(31).getNombre(), ampersand.get(31), Casillas.get(31).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(18).getNombre(), ampersand.get(18), Casillas.get(18).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(32).getNombre(), ampersand.get(32), Casillas.get(32).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(17).getNombre(), ampersand.get(17), Casillas.get(17).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(33).getNombre(), ampersand.get(33), Casillas.get(33).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(16).getNombre(), ampersand.get(16), Casillas.get(16).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(34).getNombre(), ampersand.get(34), Casillas.get(34).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(15).getNombre(), ampersand.get(15), Casillas.get(15).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(35).getNombre(), ampersand.get(35), Casillas.get(35).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(14).getNombre(), ampersand.get(14), Casillas.get(14).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(36).getNombre(), ampersand.get(36), Casillas.get(36).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(13).getNombre(), ampersand.get(13), Casillas.get(13).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(37).getNombre(), ampersand.get(37), Casillas.get(37).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(12).getNombre(), ampersand.get(12), Casillas.get(12).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(38).getNombre(), ampersand.get(38), Casillas.get(38).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(11).getNombre(), ampersand.get(11), Casillas.get(11).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(39).getNombre(), ampersand.get(39), Casillas.get(39).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(10).getNombre(), ampersand.get(10), Casillas.get(10).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(9).getNombre(), ampersand.get(9), Casillas.get(9).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(8).getNombre(), ampersand.get(8), Casillas.get(8).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(7).getNombre(), ampersand.get(7), Casillas.get(7).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(6).getNombre(), ampersand.get(6), Casillas.get(6).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(5).getNombre(), ampersand.get(5), Casillas.get(5).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(4).getNombre(), ampersand.get(4), Casillas.get(4).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(3).getNombre(), ampersand.get(3), Casillas.get(3).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(2).getNombre(), ampersand.get(2), Casillas.get(2).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(1).getNombre(), ampersand.get(1), Casillas.get(1).getJugadores()) + RESET,
                SUBRAYAR + "%-8s %s %5s".formatted(Casillas.get(0).getNombre(), ampersand.get(0), Casillas.get(0).getJugadores()) + RESET);
        return tablero;
    }
* */