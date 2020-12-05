package mensajero.mensajerogo.Constantes;

/**
 * Created by equipo on 30/01/2018.
 */

public class Constantes {

    public static final String BD_NOMBRE_USUARIO = "nombre";
    public static final String BD_TELEFONO_USUARIO = "telefono";
    public static final String BD_ID_USUARIO = "id_usuario";
    public static final String BD_ID_MENSAJERO = "id_mensajero";
    public static final String BD_ID_PEDIDO = "id_pedido";
    public static final String BD_USUARIO="usuario";
    public static final String BD_GERENTE="gerente";
    public static final String BD_ADMIN="admin";
    public static final String BD_PEDIDO="pedido";
    public static final String BD_DIR_INICIAL="dir_inicial";
    public static final String BD_DIR_FINAL="dir_final";
    public static final String BD_TIPO_PEDIDO="tipo_pedido";
    public static final String BD_ESTADO_PEDIDO="estado_pedido";
    public static final String BD_FECHA_PEDIDO="fecha_pedido";
    public static final String BD_VALOR_PEDIDO="valor_pedido";
    public static final String BD_COMENTARIO ="comentario";
    public static final String BD_CODIGO_MENSAJERO ="codigo_mensajero";
    public static final String BD_ASIGNAR_MOVIL ="asignar movil";
    public static final String URL_DESCARGA_APP = "https://play.google.com/store/apps/details?id=com.mensajero.equipo.mensajero";
    public static final String INICIAR_DE_NOTIFICACION = "notificacion";
    public static final String BD_MENSAJERO_ESPECIAL_CONECTADO = "mensajero_especial_conectado";
    public static final String BD_PEDIDO_ESPECIAL="pedido_especial";
    public static final String BD_MENSAJERO_ESPECIAL = "mensajero_especial";
    public static final String BD_ESTADO_MENSAJERO = "estado";
    public static final String ESTADO_ACTIVO= "activo";
    public static final String ESTADO_BLOQUEADO= "bloqueado";
    public static final String ESTADO_VERIFICAR = "verificar";
    public static final String ESTADO_SUBIR_IMAGENES = "subir_imagenes";
    public static final String BD_PLACA = "placa";
    public static final String BD_RUTA_FOTOS_MENSAJERO = "mensajeros/mensajero_carro/movil_";
    public static final String BD_FOTO_DE_PERFIL = "foto_perfil.jpg";
    public static final String BD_CODIGO ="codigo";
    public static final String BD_TOKEN ="token";
    public static final String BD_MENSAJEROS_CERCA_ENV_MENSAJE ="mensajeros_especial_env_mensaje";
    public static final String BD_POSICION_EN_LA_LISTA ="posicion_en_la_lista";
    public static final String ID_LISTA ="id_lista";
    public static final String URL_FOTO_PERFIL_CONDUCTOR = "mensajeros/mensajero_carro/movil_";
    public static final String LAT_DIR_INICIAL = "lat_dir_ini";
    public static final String LGN_DIR_INICIAL = "lgn_dir_ini";
    public static final String LAST_LOCATION_HORA = "ultimo_cambio_location";
    public static final String CHAT = "chat";


    //Constantes para los Job
    public static final int PRIORIDAD_ALTA = 1;

    //CONSTANTES PARA LOS SERVICIOS
    public static final String STARTFOREGROUND_ACTION = "mensajero.mensajerogo.foregroundservice.action.startforeground";
    public static final String STOPFOREGROUND_ACTION = "mensajero.mensajerogo.foregroundservice.action.stopforeground";
    public static final String ACTION_DESCONECTAR = "mensajero.mensajerogo.foregroundservice.action.desconectar";
    public static final String ACTION_CONECTAR = "mensajero.mensajerogo.foregroundservice.action.conectar";
    public static final String MAIN_ACTION = "mensajero.mensajerogo.foregroundservice.action.main";
    public static final String ACTION_INICIO_SESION = "mensajero.mensajerogo.inicio_sesion";
    public static final String ACTION_PEDIDO_ACEPTADO = "mensajero.mensajerogo.pedido_aceptado";
    public static final String ACTION_RECOGER = "mensajero.mensajerogo.action_recoger_pasajero";
    public static final String ACTION_INICIAR_CARRERA = "mensajero.mensajerogo.action_iniciar_carrera";
    public static final String ACTION_TERMINAR_CARRERA = "mensajero.mensajerogo.action_terminar_carrera";
    public static final String ACTION_CONECTAR_DESDE_NOTIFICACION = "mensajero.mensajerogo.foregroundservice.action.conectar_desde_notificacion";
    public static final String ACTION_SERVICIO_PENDIENTE = "mensajero.mensajerogo.foregroundservice.action.servicio-pendiente";

    public static final String ESTADO_CONECTADO = "Conectado";
    public static final String ESTADO_DESCONECTADO = "Desconectado";
    public static final String SERVICIO_ACTIVO = "servicio_activo";
    public static final String SERVICIO_NUEVO = "servicio_nuevo";
    public static final String LAT_INI = "lat_dir_ini";
    public static final String LNG_INI = "lgn_dir_ini";
    public static final String TIEMPO = "tiempo";
    public static final String DISTANCIA = "distancia";
    public static final String BD_SIN_PLACA = "sinplaca";
    public static final String ESTADO_EN_CURSO = "en_curso" ;
    public static final String ESTADO_CANCELADO = "cancelado";
    public static final String ESTADO_TERMINADO = "terminado";
    public static final String ESTADO_SIN_MOVIL_ASIGNADO = "sin_movil_asignado";
    public static final String SERVICIO_GENERICO = "generico";
    public static final String ESTADO_VIAJE_INICIADO="viaje_iniciado";

    //canales de notificacion id´s:
    public static final String ID_CANAL_CHAT = "com.mensajero.canal_05";
    public static final String NOMBRE_CANAL_CHAT= "com.mensajero.chat";
    public static final String ID_CANAL_DEFAULT = "com.mensajero.canal_06";
    public static final String NOMBRE_CANAL_DEFAULT= "com.mensajero.default";
    public static final String ID_CANAL_CONECTADO = "com.mensajerogo.canal_01";
    public static final String NOMBRE_CANAL_CONECTADO = "com.mensajerogo.canal_conectado";
    public static final String ID_CANAL_SERVICIO_CARRERA = "com.mensajerogo.canal_02";
    public static final String NOMBRE_CANAL_SERVICIO_CARRERA = "com.mensajeroo.canal_servicio_carrera";
    public static final String NOMBRE_CANAL_DESCONECTAR = "com.mensajerogo.canal_desconectar";
    public static final String ID_CANAL_DESCONECTAR = "com.mensajerogo.canal_03";
    public static final String CONFIRMAR_LLEGADA = "com.mensajerogo.confirmar_llegada";
    public static final String MENSAJE_CHAT = "mensaje_chat";
    public static final String CONFIRMAR_VALOR_VIAJE = "confirmar_valor_viaje";


    //acciones del receptor de mensajes
    public static final String ACTION_MENSAJE_CHAT = "action.mensaje_chat";
    public static final String ACTION_CONFIRMAR_VALOR_VIAJE = "action.confirma.valor.viaje";
    public static final String BD_ID_PEDIDO_ACEPTADO = "id_pedido_aceptado";
    public static final String ACTION_ACTUALIZAR = "actualizacion_disponible";
    public static final String TOKEN_CONDUCTOR = "token_conductor";

    //para agregar las alertas en el mapa
    public static final String ALERTAS_MAPA = "alertas_mapa" ;
    public static final String ALERTA_720 = "Policia";
    public static final String ALERTA_VARADA = "Necesito ayuda";
    public static final String ALERTA_SERVICIOS = "Solicitan Mensajeros";
    public static final String ALERTA_CALLE_CERRADA = "Calle cerrada";
    public static final String ALERTA_ACCIDENTE = "Accidente";
    public static final String SERVICIO_MENSAJERO_GO = "pedido_especial";

    // para eventos del servicodr socket
    public static final String EVENT_TERMINAR_VIAJE_GO = "terminar-viajeGo";


    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
        int FOREGROUND_SERVICE_ESTADO = 102;
        int FOREGROUND_SERVICE_DESCEONECTAR = 103;
    }
}
