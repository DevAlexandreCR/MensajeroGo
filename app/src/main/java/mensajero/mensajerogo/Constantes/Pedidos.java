package mensajero.mensajerogo.Constantes;

/**
 * Created by equipo on 10/09/2017.
 */

public class Pedidos {

    private String id_pedido;
    private String id_usuario;
    private String fecha_pedido;
    private String tipo_pedido;
    private String dir_inicial;
    private String dir_final;
    private Double valor_pedido, lat_dir_inicial, lat_dir_final, long_dir_inicial, long_dir_final;
    private String estado_pedido;
    private String comentario;
    private String codigo_mensajero;
    private String tipo_servicio;
    private String nombre, telefono;
    private String token;
    private Float calificacion;
    private String token_conductor;
    private boolean liberado;
    private long date;



    public Pedidos() {

    }

    public Pedidos(String id_pedido, String id_usuario, String fecha_pedido, String tipo_pedido, String dir_inicial
            , String dir_final, Double valor_pedido, Double lat_dir_inicial, Double lat_dir_final, Double long_dir_inicial, Double
                           long_dir_final, String estado_pedido, String comentario, String codigo_mensajero,
                   String tipo_servicio) {
        this.fecha_pedido = fecha_pedido;
        this.tipo_pedido = tipo_pedido;
        this.dir_inicial = dir_inicial;
        this.dir_final = dir_final;
        this.valor_pedido = valor_pedido;
        this.estado_pedido = estado_pedido;
        this.comentario = comentario;
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.codigo_mensajero = codigo_mensajero;
        this.tipo_servicio = tipo_servicio;
        this.lat_dir_inicial = lat_dir_inicial;
        this.lat_dir_final = lat_dir_final;
        this.long_dir_inicial = long_dir_inicial;
        this.long_dir_final = long_dir_final;
    }

    public String getToken_conductor() {
        return token_conductor;
    }

    public void setToken_conductor(String token_conductor) {
        this.token_conductor = token_conductor;
    }

    public boolean getLiberado() {
        return liberado;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public String getToken() {
        return token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLat_dir_inicial() {
        return lat_dir_inicial;
    }

    public void setLat_dir_inicial(Double lat_dir_inicial) {
        this.lat_dir_inicial = lat_dir_inicial;
    }

    public Double getLat_dir_final() {
        return lat_dir_final;
    }

    public void setLat_dir_final(Double lat_dir_final) {
        this.lat_dir_final = lat_dir_final;
    }

    public Double getLong_dir_inicial() {
        return long_dir_inicial;
    }

    public void setLong_dir_inicial(Double long_dir_inicial) {
        this.long_dir_inicial = long_dir_inicial;
    }

    public Double getLong_dir_final() {
        return long_dir_final;
    }

    public void setLong_dir_final(Double long_dir_final) {
        this.long_dir_final = long_dir_final;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getTipo_pedido() {
        return tipo_pedido;
    }

    public void setTipo_pedido(String tipo_pedido) {
        this.tipo_pedido = tipo_pedido;
    }

    public String getDir_inicial() {
        return dir_inicial;
    }

    public void setDir_inicial(String dir_inicial) {
        this.dir_inicial = dir_inicial;
    }

    public String getDir_final() {
        return dir_final;
    }

    public void setDir_final(String dir_final) {
        this.dir_final = dir_final;
    }

    public Double getValor_pedido() {
        return valor_pedido;
    }

    public String getEstado_pedido() {
        return estado_pedido;

    }

    public void setValor_pedido(Double valor_pedido) {
        this.valor_pedido = valor_pedido;
    }

    public void setEstado_pedido(String estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getCodigo_mensajero() {
        return codigo_mensajero;
    }

    public void setCodigo_mensajero(String codigo_mensajero) {
        this.codigo_mensajero = codigo_mensajero;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

