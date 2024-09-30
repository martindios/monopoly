package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;

import static monopoly.Valor.*;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>(4);
        this.grupos = new HashMap<String, Grupo>(8);
        this.generarCasillas();
    }

    //TEMPORAL, borrarlo pero lo de dentro está bien
    public Tablero() {
        this.posiciones = new ArrayList<>(4);
        this.grupos = new HashMap<String, Grupo>(8);
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
        Casilla solar1 = new Casilla("Solar1", "Solar", 2, 2, banca);
        ladoSur.add(solar1);
        ladoSur.add(new Casilla("Caja", "Solar", 3, 3, banca));
        Casilla solar2 = new Casilla("Solar2", "Solar", 4, 4, banca);
        ladoSur.add(solar2);
        ladoSur.add(new Casilla("Imp1", "Impuestos", 5, 5, banca));
        ladoSur.add(new Casilla("Trans1", "Transporte", 6, 6, banca));
        Casilla solar3 = new Casilla("Solar3", "Solar", 7, 7, banca);
        ladoSur.add(solar3);
        ladoSur.add(new Casilla("Suerte", "Solar", 8, 8, banca));
        Casilla solar4 = new Casilla("Solar4", "Solar", 9, 9, banca);
        ladoSur.add(solar4);
        Casilla solar5 = new Casilla("Solar5", "Solar", 10, 10, banca);
        ladoSur.add(solar5);

        Grupo grupo1 = new Grupo(solar1, solar2, BLUE);
        Grupo grupo2 = new Grupo(solar3, solar4, solar5, GREEN);

        grupos.put(grupo1.getColorGrupo(), grupo1);
        grupos.put(grupo2.getColorGrupo(), grupo2);

        posiciones.add(ladoSur);
    }



    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(new Casilla("Cárcel", "Especiales", 1, 11, banca));
        Casilla solar6 = new Casilla("Solar6", "Solar", 6, 12, banca);
        ladoOeste.add(solar6);
        ladoOeste.add(new Casilla("Serv1", "Servicios", 13, banca));
        Casilla solar7 =  new Casilla("Solar7", "Solar", 7, 14, banca);
        ladoOeste.add(solar7);
        Casilla solar8 = new Casilla("Solar8", "Solar", 8, 15, banca);
        ladoOeste.add(solar8);
        ladoOeste.add(new Casilla("Trans2", "Transporte", 16, banca));
        Casilla solar9 = new Casilla("Solar9", "Solar", 9, 17, banca);
        ladoOeste.add(solar9);
        ladoOeste.add(new Casilla("Caja", "Solar", 2, 18, banca));
        Casilla solar10 = new Casilla("Solar10", "Solar", 10, 19, banca);
        ladoOeste.add(solar10);
        Casilla solar11 = new Casilla("Solar11", "Solar", 11, 20, banca);
        ladoOeste.add(solar11);

        Grupo grupo3 = new Grupo(solar6, solar7, solar8, PURPLE);
        Grupo grupo4 = new Grupo(solar9, solar10, solar11, CYAN);

        grupos.put(grupo3.getColorGrupo(), grupo3);
        grupos.put(grupo4.getColorGrupo(), grupo4);

        posiciones.add(ladoOeste);
    }
    
    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(new Casilla("Parking", "Especiales", 21, banca));
        Casilla solar12 = new Casilla("Solar12", "Solar", 12, 22, banca);
        ladoNorte.add(solar12);
        ladoNorte.add(new Casilla("Suerte", "Suerte", 23, banca));
        Casilla solar13 = new Casilla("Solar13", "Solar", 13, 24, banca);
        ladoNorte.add(solar13);
        Casilla solar14 = new Casilla("Solar14", "Solar", 14, 25, banca);
        ladoNorte.add(solar14);
        ladoNorte.add(new Casilla("Trans3", "Transporte", 25, 26, banca));
        Casilla solar15 = new Casilla("Solar15", "Solar", 15, 27, banca);
        ladoNorte.add(solar15);
        Casilla solar16 = new Casilla("Solar16", "Solar", 16, 28, banca);
        ladoNorte.add(solar16);
        ladoNorte.add(new Casilla("Serv2", "Servicios", 28, 29, banca));
        Casilla solar17 = new Casilla("Solar17", "Solar", 17, 30, banca);
        ladoNorte.add(solar17);

        Grupo grupo5 = new Grupo(solar12, solar13, solar14, YELLOW);
        Grupo grupo6 = new Grupo(solar15, solar16, solar17, RED);

        grupos.put(grupo5.getColorGrupo(), grupo5);
        grupos.put(grupo6.getColorGrupo(), grupo6);

        posiciones.add(ladoNorte);
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(new Casilla("IrCarcel", "Especiales", 31, banca));
        Casilla solar18 = new Casilla("Solar18", "Solar", 18, 32, banca);
        ladoEste.add(solar18);
        Casilla solar19 = new Casilla("Solar19", "Solar", 19, 33, banca);
        ladoEste.add(solar19);
        ladoEste.add(new Casilla("Caja", "Solar", 33, 34, banca));
        Casilla solar20 = new Casilla("Solar20", "Solar", 20, 35, banca);
        ladoEste.add(solar20);
        ladoEste.add(new Casilla("Trans4", "Transporte", 35, 36, banca));
        ladoEste.add(new Casilla("Suerte", "Solar", 36, 37, banca));
        Casilla solar21 = new Casilla("Solar21", "Solar", 21, 38, banca);
        ladoEste.add(solar21);
        ladoEste.add(new Casilla("Imp2", "Impuestos", 38, 39, banca));
        Casilla solar22 = new Casilla("Solar22", "Solar", 22, 40, banca);
        ladoEste.add(solar22);

        Grupo grupo7 = new Grupo(solar18, solar19, solar20, BLACK);
        Grupo grupo8 = new Grupo(solar21, solar22, IRed);

        grupos.put(grupo7.getColorGrupo(), grupo7);
        grupos.put(grupo8.getColorGrupo(), grupo8);

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
                "─".repeat(122),
                //Norte (con la última casilla el inicio del este)
                posiciones.get(2).get(0).getGrupo().getColorGrupo() + posiciones.get(2).get(0).getNombre() + RESET,
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

                "─".repeat(122),

                //Este y oeste simultáneo

                posiciones.get(1).get(9).getNombre(),
                posiciones.get(3).get(1).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(8).getNombre(),
                posiciones.get(3).get(2).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(7).getNombre(),
                posiciones.get(3).get(3).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(6).getNombre(),
                posiciones.get(3).get(4).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(5).getNombre(),
                posiciones.get(3).get(5).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(4).getNombre(),
                posiciones.get(3).get(6).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(3).getNombre(),
                posiciones.get(3).get(7).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(2).getNombre(),
                posiciones.get(3).get(8).getNombre(),

                "&J",
                "&J",

                "─".repeat(12),
                "─".repeat(12),

                posiciones.get(1).get(1).getNombre(),
                posiciones.get(3).get(9).getNombre(),

                "&J",
                "&J",

                //Sur (con la primera casilla inicio oeste)

                "─".repeat(122),

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

                "─".repeat(122)

        );

        // Formatear el tablero con las casillas
        return tablero;
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