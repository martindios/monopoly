package monopoly;

import java.util.Scanner;

//Esta clase no necesita getters ni setters ya que no hay atributos. Además no hace falta un constructor ya que nunca se va a instanciar esta clase
public class MonopolyETSE {

    public static void main(String[] args) {

        Tablero tablero = new Tablero();
        System.out.println(tablero.toString());
        /*System.out.print("$>");
        Scanner scanner = new Scanner(System.in);
        String comando = scanner.nextLine();
        Menu menu = new Menu();
        menu.analizarComando(comando);*/

    }
    
}
