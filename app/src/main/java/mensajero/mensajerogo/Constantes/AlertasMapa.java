package mensajero.mensajerogo.Constantes;

public class AlertasMapa {

    private double lat;
    private double lng;
    private String tipo_alerta;
    private long hora_de_inicio;
    private long tiempo_de_vida;
    private String descripcion;
    private String id_alerta;
    private String nombre_creador;
    private String comentarios;
    private String numero_usuario;
    private String nombre_mensajero_que_toma_servicio;
    private String codigo_mensajero_servicio;
    private String TIME_ZONE;

    public AlertasMapa() {
    }

    public AlertasMapa(double lat, double lng, String tipo_alerta, long hora_de_inicio, String nombre_creador) {
        this.lat = lat;
        this.lng = lng;
        this.tipo_alerta = tipo_alerta;
        this.hora_de_inicio = hora_de_inicio;
        this.nombre_creador = nombre_creador;

        switch (tipo_alerta){
            case Constantes.ALERTA_720:
                this.tiempo_de_vida = 3600000;
                break;
            case Constantes.ALERTA_SERVICIOS:
                this.tiempo_de_vida = 900000;
                break;
            case Constantes.ALERTA_VARADA:
                this.tiempo_de_vida = 1800000;
                break;
            case Constantes.ALERTA_ACCIDENTE:
                this.tiempo_de_vida = 1800000;
                break;
            case Constantes.ALERTA_CALLE_CERRADA:
                this.tiempo_de_vida = 7200000;
                break;
        }

    }

    public String getTIME_ZONE() {
        return TIME_ZONE;
    }

    public void setTIME_ZONE(String TIME_ZONE) {
        this.TIME_ZONE = TIME_ZONE;
    }

    public String getNumero_usuario() {
        return numero_usuario;
    }

    public void setNumero_usuario(String numero_usuario) {
        this.numero_usuario = numero_usuario;
    }

    public String getNombre_mensajero_que_toma_servicio() {
        return nombre_mensajero_que_toma_servicio;
    }

    public void setNombre_mensajero_que_toma_servicio(String nombre_mensajero_que_toma_servicio) {
        this.nombre_mensajero_que_toma_servicio = nombre_mensajero_que_toma_servicio;
    }

    public String getCodigo_mensajero_servicio() {
        return codigo_mensajero_servicio;
    }

    public void setCodigo_mensajero_servicio(String codigo_mensajero_servicio) {
        this.codigo_mensajero_servicio = codigo_mensajero_servicio;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombre_creador() {
        return nombre_creador;
    }

    public void setNombre_creador(String nombre_creador) {
        this.nombre_creador = nombre_creador;
    }

    public String getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(String id_alerta) {
        this.id_alerta = id_alerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getHora_de_inicio() {
        return hora_de_inicio;
    }

    public void setHora_de_inicio(long hora_de_inicio) {
        this.hora_de_inicio = hora_de_inicio;
    }

    public long getTiempo_de_vida() {
       return tiempo_de_vida;
    }

    public void setTiempo_de_vida(long tiempo_de_vida) {
        this.tiempo_de_vida = tiempo_de_vida;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTipo_alerta() {
        return tipo_alerta;
    }

    public void setTipo_alerta(String tipo_alerta) {
        this.tipo_alerta = tipo_alerta;
        switch (tipo_alerta){
            case Constantes.ALERTA_720:
                this.tiempo_de_vida = 3600000;
                break;
            case Constantes.ALERTA_SERVICIOS:
                this.tiempo_de_vida = 10000;
                break;
            case Constantes.ALERTA_VARADA:
                this.tiempo_de_vida = 1800000;
                break;
            case Constantes.ALERTA_ACCIDENTE:
                this.tiempo_de_vida = 1800000;
                break;
            case Constantes.ALERTA_CALLE_CERRADA:
                this.tiempo_de_vida = 3600000;
                break;
        }

    }
}
