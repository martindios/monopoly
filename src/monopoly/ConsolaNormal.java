package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaNormal implements Consola{
    static Scanner scanner = new Scanner(System.in);
    public void imprimir(String mensaje){
        System.out.println(mensaje);
    }
    public void imprimirArray(ArrayList<String> mensaje){
        System.out.println(mensaje);
    }
    public String leer(String descripcion){
        imprimir(descripcion);
        return scanner.nextLine();
    }
}
