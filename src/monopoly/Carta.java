package monopoly;

public class Carta {

    private String descripcion;
    private String tipoCarta;
    private int idCarta;

    public Carta(String descripcion, String tipoCarta, int idCarta) {
        this.descripcion = descripcion;
        this.tipoCarta = tipoCarta;
        this.idCarta = idCarta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public String getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(String tipoCarta) {
        this.tipoCarta = tipoCarta;
    }
}
