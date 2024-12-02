package monopoly;

import java.util.ArrayList;

public interface Consola {
    void imprimir(String mensaje);
    void imprimirSinSalto(char mensaje);
    void imprimirStrBuilder(StringBuilder mensaje);
    String leer();
    String leerPalabra();
    int leerInt();

}
