package monopoly;

public class MonopolyETSE {

    public static void main(String[] args) {
        new Menu();
        //martin-branch

        Tablero tablero = new Tablero();
        System.out.println(tablero.toString());

        System.out.println("\u2500".repeat(20));
        System.out.println("│");
        System.out.println("\u2502");
        System.out.println("\u2502");
        System.out.println("\u2502");
    }
    
}
