package mensajero.mensajerogo.Constantes;

public class Vehiculo {

    private String placa;
    private String marca;
    private String modelo_vehiculo;
    private String fecha_seguro;
    private String color;

    public Vehiculo() {

    }

    public Vehiculo(String placa, String marca, String modelo_vehiculo, String color) {
        this.placa = placa;
        this.marca = marca;
        this.modelo_vehiculo = modelo_vehiculo;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFecha_seguro() {
        return fecha_seguro;
    }

    public void setFecha_seguro(String fecha_seguro) {
        this.fecha_seguro = fecha_seguro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo_vehiculo() {
        return modelo_vehiculo;
    }

    public void setModelo_vehiculo(String modelo_vehiculo) {
        this.modelo_vehiculo = modelo_vehiculo;
    }
}
