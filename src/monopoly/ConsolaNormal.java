package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaNormal implements Consola{
    static Scanner scanner = new Scanner(System.in);
    public void imprimir(String mensaje){
        System.out.println(mensaje);
    }
    public void imprimirSinSalto(char mensaje){System.out.print(mensaje);}
    public void imprimirSinSalto(String mensaje){System.out.print(mensaje);}

    public void imprimirStrBuilder(StringBuilder mensaje){
        System.out.println(mensaje);
    }

    public String leer(){
        return scanner.nextLine();
    }
    public String leerPalabra(){
        return scanner.next();
    }
    public int leerInt(){
        return scanner.nextInt();
    }

}
