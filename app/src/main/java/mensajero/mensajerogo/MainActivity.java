package mensajero.mensajerogo;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import mensajero.mensajerogo.Constantes.AdapterServiciosPendientes;
import mensajero.mensajerogo.Constantes.AlertasMapa;
import mensajero.mensajerogo.Constantes.Constantes;
import mensajero.mensajerogo.Constantes.GestureListener;
import mensajero.mensajerogo.Constantes.GlideApp;
import mensajero.mensajerogo.Constantes.Mensajeros;
import mensajero.mensajerogo.Constantes.Pedidos;
import mensajero.mensajerogo.Fragmentos.EnviarDocumentosFragm;
import mensajero.mensajerogo.Fragmentos.FragmentBloqueado;
import mensajero.mensajerogo.Fragmentos.FragmentChat;
import mensajero.mensajerogo.Fragmentos.Fragment_balance;
import mensajero.mensajerogo.Fragmentos.Fragment_perfil;
import mensajero.mensajerogo.Fragmentos.Historial;
import mensajero.mensajerogo.Servicios.ServicioCarrera;
import mensajero.mensajerogo.Servicios.ServicioCarrera.binderServicioCarrera;
import mensajero.mensajerogo.Servicios.ServicioDesconectar;
import mensajero.mensajerogo.Servicios.ServicioEstadoConectado;
import mensajero.mensajerogo.Servicios.VerificarActualizaciones;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener, View.OnTouchListener {

    private static final int PERMISO_OVERLAYS = 156;
    private GoogleMap map;
    private Marker marcador, navegacion;
    private MapFragment mapFragment;
    private String token;
    private LottieAnimationView lottie_alerta_servicio;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    //este intent es para cada servicio nuevo que se recibe
    private Intent ServicioNuevo;
    private Boolean ActionServicioNuevo = false,
            ActionCalcularViaje = false;
    private String posicion_en_la_lista, ActionServicioApp = Constantes.SERVICIO_GENERICO;
    private String id_lista;
    double valor = 0;
    int Distancia = 0;
    int Tiempo = 0;
    //variable para que el listener no se dispare en el oncreate()
    public int contador = 0;
    double lat = 0.0;
    double lng = 0.0;
    double lat_actualzada = 0.0;
    double lng_actualizada = 0.0;
    double lat_final = 0.0;
    double lng_final = 0.0;
    String Mensaje = "";
    String Direccion = "";
    public Switch aSwitchEstado;
    public String keyconectado = "";
    private boolean Conectado = false;
    public DatabaseReference database = FirebaseDatabase.getInstance().getReference()
            .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN);
    public DatabaseReference currentUserDB, refPedidosPendientes;
    public String codigo, placa, estado_mensajero;
    public String id_mensajero, sessionId;
    public Mensajeros mensajero;
    public JobManager jobManager;
    private FirebaseAuth mAuth;
    public SharedPreferences sharedPref;
    public FragmentManager fragmentManager;
    public FragmentTransaction transaction;
    public FragmentBloqueado fragmentBloqueado;
    public EnviarDocumentosFragm documentosFragm;
    public FragmentChat fragmentChat;
    public Historial historial;
    public Button BotonIniciarCarrera;
    public EditText ETAgregarDestino;
    private Boolean SERVICIO_ACTIVO = false;
    //declaramos el frame para la carrera activa y sus componentes
    public FrameLayout frame_carrera_activa;
    public TextView textoDistancia, textoValor, textoVersion, texto_chat;
    public PolylineOptions lineOptions;
    public ArrayList<LatLng> points;
    public LatLng pocisionInicial, pocisionFinal;
    public LatLngBounds Popayan;
    LocationManager locationManager;
    public ServicioCarrera servicioCarrera;
    public Boolean servicioEnlazado = false;
    public Chronometer cronometro;
    //dialog para mostrar carrera terminada
    private Dialog dialog_terminar_viaje, dialog_Actualizar;
    private ReceptorMensajesServidor receptorMensajesServidor;
    private boolean mapa_movido_po_el_usuario = false;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            servicioEnlazado = false;
            cronometro.stop();
            cronometro.setBase(SystemClock.elapsedRealtime());
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binderServicioCarrera myBinder = (binderServicioCarrera) service;
            servicioCarrera = myBinder.getService();
            servicioEnlazado = true;
            cronometro.setBase(servicioCarrera.getBaseCronometro());
            cronometro.setOnChronometerTickListener(new OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    double modificadortarifa = modificadorDeTarifaSegunLaHora();
                    Double dist = servicioCarrera.getDistancia();
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    long tiempo = servicioCarrera.getTiempoTranscurrido();
                    textoDistancia.setText(formatter.format(dist / 1000) + " km");
                    valor = (((dist / 1000) * (530 * modificadortarifa)) + (tiempo * (120 * modificadortarifa)) + 1800);
                    if (valor <= 3500) valor = 3500;
                    textoValor.setText("$ " + valor);
                    points = servicioCarrera.getPoints();

                    if (points != null) {
                        PolylineOptions options = new PolylineOptions()
                                .width(17)
                                .color(getResources().getColor(R.color.colorPrimary))
                                .geodesic(true);
                        for (int i = 0; i < points.size(); i++) {
                            LatLng point = points.get(i);
                            options.add(point);
                        }
                        if (map != null) {
                            map.addPolyline(options);
                            if (!mapa_movido_po_el_usuario) {
                                miUbicacion();
                            }
                        }

                    }
                }
            });

            cronometro.start();
        }
    };
    //las variables para el servicio que se acepta
    private String id_pedido_aceptado;
    private Pedidos pedido_aceptado;
    private Boolean dir_viaje = false;
    private Boolean dir_recoger = false;

    //estas son las variables del frame para recoger al cliente
    private FrameLayout frame_recoger_pasajero, frame_detalles_alertas;
    private TextView recoger_nombre, recoger_numero, recoger_direccion, texto_nom_cliente, texto_num_cliente,
            texto_dir_cliente, text_cancelar, text_avisar;
    private ImageButton wase_boton, maps_boton, boton_info;
    private FloatingActionButton FAB_maps, FAB_wase, FAB_cancelar_servicio, FAB_avisar_llegada, FAB_Chat;

    //animaciones
    private Animation hacia_arriba, hacia_abajo;
    private LinearLayout layout_info;

    //Firebase Remote Config
    private FirebaseRemoteConfig configuracionRemotaFirebase;
    private FirebaseRemoteConfigSettings settingsConfRemote;

    //para activar la funcion de chat
    private FirebaseFunctions funtions;

    //para crear los canales de notificaciones
    private NotificationManager notificationManager;

    Dialog dialog_calculando;

    //listener para el pedido aceptado
    private boolean alertaRemovida = false;
    Query pedidoQuery, alertasQuery;
    ArrayList<Marker> markers;
    ChildEventListener listenerAlertasMapa = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getValue(AlertasMapa.class) != null) {
                AlertasMapa alertamapa = dataSnapshot.getValue(AlertasMapa.class);
                Calendar calendar = Calendar.getInstance();
                Calendar calendarAhora = Calendar.getInstance();
                long ahora = calendarAhora.getTimeInMillis();
                calendar.setTimeInMillis(alertamapa.getHora_de_inicio());
                long tiempo_duracion = ahora - calendar.getTimeInMillis();
                long duracion = alertamapa.getTiempo_de_vida();
                long dif = duracion - tiempo_duracion;
                Log.i(" ListernetAlerta", "dif " + dif);
                if (dif < 0) {
                    if (!alertamapa.getTipo_alerta().equals(Constantes.ALERTA_SERVICIOS)) {
                        database.child(Constantes.ALERTAS_MAPA).child(alertamapa.getId_alerta()).removeValue();
                        Log.i(" ListernetAlerta", "Remover alerta");
                    }
                } else {
                    agregarAlertaMapa(alertamapa);
                    Log.i(" ListernetAlerta", "Agregar alerta");
                }
            } else {
                toast("sin alertas en el mapa");
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            try {
                if (dataSnapshot.getValue(AlertasMapa.class) != null) {
                    AlertasMapa alertamapa = dataSnapshot.getValue(AlertasMapa.class);
                    for (int i = 0; i <= markers.size() - 1; i++) {
                        AlertasMapa alerta = (AlertasMapa) markers.get(i).getTag();
                        if (alerta.getId_alerta().equals(alertamapa.getId_alerta())) {
                            markers.get(i).remove();
                            markers.remove(i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener listener_pedido_aceptado = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            pedido_aceptado = dataSnapshot.getValue(Pedidos.class);
            //guardamos el pedido en las preferencias
            if (pedido_aceptado != null) {
                layout_pendientes.setVisibility(View.INVISIBLE);
                Log.i("PEDIDO ACEPTADO", pedido_aceptado.getId_pedido());
                Gson gson = new Gson();
                String json = gson.toJson(pedido_aceptado);//convertimos el objeto a json
                final SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                editor.putString(Constantes.BD_PEDIDO_ESPECIAL, json);
                editor.apply();

                //verificamos el estado del pedido para saber si el cliente cancela el servicio o el conductor
                if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_EN_CURSO)) {
                    //aparecemos el frame y le agregamos sus respectivos valores
                    FAB_Chat.show();
                    frame_detalles_alertas.setVisibility(View.INVISIBLE);
                    if (dir_viaje) {
                        frame_carrera_activa.setVisibility(View.VISIBLE);
                        frame_recoger_pasajero.setVisibility(View.INVISIBLE);
                        try {
                            frame_carrera_activa.startAnimation(hacia_arriba);
                            layout_info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            boton_info.setBackground(getDrawable(android.R.drawable.arrow_down_float));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        texto_num_cliente.setText("Teléfono: " + pedido_aceptado.getTelefono());
                        if (pedido_aceptado.getDir_final() != null) {
                            texto_dir_cliente.setText("Dirección: " + pedido_aceptado.getDir_final());
                        } else {
                            texto_dir_cliente.setText("Dirección: " + "Preguntar al usuario");
                        }
                        texto_nom_cliente.setText("Nombre: " + pedido_aceptado.getNombre());

                        if (map != null) {
                            if (pedido_aceptado.getLat_dir_final() != null) {
                                agregarMarcador(pedido_aceptado.getLat_dir_final(), pedido_aceptado.getLong_dir_final());

                                miUbicacion();
                                pocisionFinal = new LatLng(pedido_aceptado.getLat_dir_final(), pedido_aceptado.getLong_dir_final());
                                LatLngBounds bounds_ruta = LatLngBounds.builder().include(pocisionInicial).include(pocisionFinal).build();
                                //map.setPadding(0, 0, frame_recoger_pasajero.getHeight(), 0);
                                try {
                                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds_ruta, 100));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Ruta(pocisionInicial, pocisionFinal);
                            }

                        }

                    } else {
                        FAB_avisar_llegada.show();
                        texto_chat.setVisibility(View.VISIBLE);
                        text_avisar.setVisibility(View.VISIBLE);
                        recoger_direccion.setText(pedido_aceptado.getDir_inicial());
                        frame_carrera_activa.setVisibility(View.INVISIBLE);
                        frame_recoger_pasajero.setVisibility(View.VISIBLE);
                        recoger_nombre.setText(pedido_aceptado.getNombre());
                        recoger_numero.setText(pedido_aceptado.getTelefono() + " click para llamar");
                        recoger_numero.setLinksClickable(true);
                        //aqui vamos a agregar el punto al mapa para que detecte cuando esté cerca y vuelva a
                        // Esta pantalla y continúe con el servicio
                        if (map != null) {
                            agregarMarcador(pedido_aceptado.getLat_dir_inicial(), pedido_aceptado.getLong_dir_inicial());

                            miUbicacion();
                            pocisionFinal = new LatLng(pedido_aceptado.getLat_dir_inicial(), pedido_aceptado.getLong_dir_inicial());
                            LatLngBounds bounds_ruta = LatLngBounds.builder().include(pocisionInicial).include(pocisionFinal).build();
                            //map.setPadding(0, 0, frame_recoger_pasajero.getHeight(), 0);
                            try {
                                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds_ruta, 100));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Ruta(pocisionInicial, pocisionFinal);

                        }

                        View.OnClickListener clickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.boton_waze:
                                        Uri waseuri = Uri.parse("waze://?ll=" + pedido_aceptado.getLat_dir_inicial() + "," + pedido_aceptado.getLong_dir_inicial() + "&navigate=yes");
                                        Intent waseintent = new Intent(Intent.ACTION_VIEW, waseuri);
                                        if (waseintent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(waseintent);
                                        } else {
                                            toast("No tienes instalada la app");
                                        }
                                        break;
                                    case R.id.boton_maps:
                                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + pedido_aceptado.getLat_dir_inicial() + "," + pedido_aceptado.getLong_dir_inicial());
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(mapIntent);
                                        } else {
                                            toast("No tienes instalada la app");
                                        }
                                        break;
                                }
                            }
                        };

                        //le cargamos el metodo onclick
                        wase_boton.setOnClickListener(clickListener);
                        maps_boton.setOnClickListener(clickListener);
                    }

                    locationManager.removeUpdates(locationListener);

                    FAB_cancelar_servicio.show();
                    text_cancelar.setVisibility(View.VISIBLE);

                    id_pedido_aceptado = pedido_aceptado.getId_pedido();
                    Intent detenerDesconectar = new Intent();
                    detenerDesconectar.setAction(Constantes.ACTION_PEDIDO_ACEPTADO);
                    sendBroadcast(detenerDesconectar);

                    //**************************************************************************//


                    // Aqui iniciamos el servicio para que detecte cuando esté cerca del pasajero y
                    // aparezca la pantalla para iniciar la carrera con el pasajero
                    if (!dir_viaje && !isMyServiceRunning(ServicioCarrera.class)) {
                        Intent recogerPasajero = new Intent(MainActivity.this, ServicioCarrera.class);
                        recogerPasajero.setAction(Constantes.ACTION_RECOGER);
                        recogerPasajero.putExtra(Constantes.LAT_INI, pedido_aceptado.getLat_dir_inicial());
                        recogerPasajero.putExtra(Constantes.LNG_INI, pedido_aceptado.getLong_dir_inicial());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(recogerPasajero);
                        } else {
                            startService(recogerPasajero);
                        }
                    }

                } else if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_VIAJE_INICIADO)) {
                    //aparecemos el frame y le agregamos sus respectivos valores
                    layout_pendientes.setVisibility(View.INVISIBLE);
                    frame_carrera_activa.setVisibility(View.VISIBLE);
                    frame_recoger_pasajero.setVisibility(View.INVISIBLE);
                    recoger_nombre.setText(pedido_aceptado.getNombre());
                    recoger_numero.setText(pedido_aceptado.getTelefono());
                    recoger_numero.setLinksClickable(true);
                    recoger_direccion.setText(pedido_aceptado.getDir_final());
                    FAB_cancelar_servicio.hide();
                    text_cancelar.setVisibility(View.INVISIBLE);
                    FAB_avisar_llegada.hide();
                    text_avisar.setVisibility(View.INVISIBLE);
                    texto_chat.setVisibility(View.INVISIBLE);
                    FAB_Chat.hide();
                    FAB_wase.show();
                    FAB_maps.show();
                    texto_num_cliente.setText("Teléfono: " + pedido_aceptado.getTelefono());
                    if (pedido_aceptado.getDir_final() != null) {
                        texto_dir_cliente.setText("Dirección: " + pedido_aceptado.getDir_final());
                    } else {
                        texto_dir_cliente.setText("Dirección: " + "Preguntar al usuario");
                    }
                    texto_nom_cliente.setText("Nombre: " + pedido_aceptado.getNombre());
                    Intent detenerDesconectar = new Intent();
                    detenerDesconectar.setAction(Constantes.ACTION_PEDIDO_ACEPTADO);
                    sendBroadcast(detenerDesconectar);

                    //**************************************************************************//

                } else if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_CANCELADO)) {
                    Intent iniciarDesconectar = new Intent();
                    iniciarDesconectar.setAction(Constantes.ACTION_TERMINAR_CARRERA);
                    layout_pendientes.setVisibility(View.VISIBLE);
                    sendBroadcast(iniciarDesconectar);
                    stopService(new Intent(MainActivity.this, ServicioCarrera.class));
                    pedido_aceptado = null;
                    FAB_Chat.hide();
                    texto_chat.setVisibility(View.INVISIBLE);
                    dir_viaje = false;
                    dir_recoger = false;
                    id_pedido_aceptado = null;
                    if (fragmentChat.isAdded()) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.remove(fragmentChat);
                        ft.commit();
                    }
                    FAB_avisar_llegada.hide();
                    ActionServicioApp = Constantes.SERVICIO_GENERICO;
                    text_avisar.setVisibility(View.INVISIBLE);
                    frame_carrera_activa.setVisibility(View.INVISIBLE);
                    Log.i("AcTION PEDACEPT value", "dir_recoger= " + dir_recoger);
                    ActivityManager activityManager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
                    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                        if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            Log.i("Foreground App", appProcess.processName);
                            if (appProcess.processName.equals(getApplication().getPackageName())) {
                                Log.i("CANCELADO", "APP EN PRIMER PLANO");
                                try {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("El servicio ha sido cancelado")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Log.i("dialog click", "int " + i);
                                                    pedido_aceptado = null;
                                                    dir_viaje = false;
                                                    dir_recoger = false;
                                                    editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                                                    editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                                                    editor.putString(Constantes.BD_ID_PEDIDO_ACEPTADO, id_pedido_aceptado);
                                                    editor.apply();
                                                    Log.i("AcTION PEDACEPT value", "dir_recoger= " + dir_recoger);

                                                    reiniciarApp(MainActivity.this, null);
                                                }
                                            })
                                            .setCancelable(false).show();


                                } catch (Exception e) {

                                    e.printStackTrace();
                                    editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                                    editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                                    editor.putString(Constantes.BD_ID_PEDIDO_ACEPTADO, id_pedido_aceptado);
                                    editor.apply();
                                    reiniciarApp(MainActivity.this, true);
                                }
                                break;
                            }
                        } else {
                            Log.i("CANCELADO", " APP EN SEGUNDO PLANO");
                            try {
                                reiniciarApp(MainActivity.this, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    pedidoQuery.removeEventListener(this);

                } else if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_TERMINADO)) {
                    servicioEnlazado = false;
                    SERVICIO_ACTIVO = false;
                    layout_pendientes.setVisibility(View.VISIBLE);
                    cronometro.stop();
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    textoDistancia.setText("");
                    BotonIniciarCarrera.setText("INICIAR CARRERA");
                    texto_num_cliente.setText("Teléfono: ");
                    texto_dir_cliente.setText("Dirección: ");
                    texto_nom_cliente.setText("Nombre: ");
                    textoDistancia.setText("Distancia Recorrida: ");
                    textoValor.setText("Valor: ");
                    map.clear();
                    points = null;
                    FAB_maps.hide();
                    FAB_Chat.hide();
                    texto_chat.setVisibility(View.INVISIBLE);
                    FAB_wase.hide();
                    FAB_cancelar_servicio.hide();
                    text_cancelar.setVisibility(View.INVISIBLE);
                    dir_viaje = false;
                    dir_recoger = false;
                    pedido_aceptado = null;
                    id_pedido_aceptado = null;
                    frame_carrera_activa.setVisibility(View.INVISIBLE);
                    editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                    editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                    editor.putString(Constantes.BD_ID_PEDIDO_ACEPTADO, id_pedido_aceptado);
                    editor.apply();
                    Intent iniciarDesconectar = new Intent();
                    iniciarDesconectar.setAction(Constantes.ACTION_TERMINAR_CARRERA);
                    sendBroadcast(iniciarDesconectar);
                    pedidoQuery.removeEventListener(this);
                    ActionServicioApp = Constantes.SERVICIO_GENERICO;
                    pedido_aceptado = null;
                }
            } else {
                FAB_Chat.hide();
                texto_chat.setVisibility(View.INVISIBLE);
                Log.i("PEDIDO ACEPTADO", "PEDIDO NULO");
                dir_recoger = false;
                dir_viaje = false;
                id_pedido_aceptado = null;
                pedidoQuery.removeEventListener(this);
                stopService(new Intent(MainActivity.this, ServicioCarrera.class));
            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public List<Pedidos> pedidos_pendientes;
    public AdapterServiciosPendientes adapterServiciosPendientes;
    public RecyclerView reciclerPedidospendientes;
    public TextView texto_vacio_pendientes;
    public ConstraintLayout layout_pendientes;
    public TextView textoDisponibles;

    public GestureDetector gestureDetector;

    private ChildEventListener listenerPedidosPendientes = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Pedidos pedido = dataSnapshot.getValue(Pedidos.class);
            if (pedido != null && pedido.getEstado_pedido() != null) {
                if(pedido.getEstado_pedido().equals(Constantes.ESTADO_SIN_MOVIL_ASIGNADO) && pedido.getLiberado()){
                    Log.i("chil dd main", pedido.getId_pedido());
                    pedidos_pendientes.remove(pedido);
                    pedidos_pendientes.add(pedido);
                } else if(pedido.getCodigo_mensajero() != null && pedido.getCodigo_mensajero().equals(codigo) && !pedido.getEstado_pedido().equals(Constantes.ESTADO_TERMINADO)
                        && !pedido.getEstado_pedido().equals(Constantes.ESTADO_CANCELADO) && id_pedido_aceptado == null){
                    Log.i("pedido propio", pedido.getFecha_pedido());
                    id_pedido_aceptado = pedido.getId_pedido();
                            pedidoQuery = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
                            pedidoQuery.addValueEventListener(listener_pedido_aceptado);
                }
            }

            if(pedidos_pendientes.size() < 1){
                texto_vacio_pendientes.setVisibility(View.VISIBLE);
            }else{
                texto_vacio_pendientes.setVisibility(View.INVISIBLE);
                if (pedido_aceptado == null) {
                    layout_pendientes.setVisibility(View.VISIBLE);
                } else{
                    layout_pendientes.setVisibility(View.INVISIBLE);
                }
            }

            adapterServiciosPendientes.notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Pedidos pedido = dataSnapshot.getValue(Pedidos.class);
            Log.i("child change main", dataSnapshot.toString());
            if (pedido != null && pedido.getEstado_pedido() != null) {
                if(pedido.getEstado_pedido().equals(Constantes.ESTADO_SIN_MOVIL_ASIGNADO) && pedido.getLiberado()){
                    Log.i("pedido liberado", pedido.getId_pedido());
                    Pedidos borrar = new Pedidos();
                    for (Pedidos p :
                            pedidos_pendientes) {
                        if(p.getId_pedido().equals(pedido.getId_pedido())) {
                            borrar = p;
                        }
                    }
                    pedidos_pendientes.remove(borrar);
                    pedidos_pendientes.add(pedido);
                } else if(pedido.getCodigo_mensajero() != null && pedido.getCodigo_mensajero().equals(codigo) && !pedido.getEstado_pedido().equals(Constantes.ESTADO_TERMINADO)
                        && !pedido.getEstado_pedido().equals(Constantes.ESTADO_CANCELADO) && id_pedido_aceptado == null){
                    Log.i("pedido propio", pedido.getFecha_pedido());
                    if (pedido_aceptado == null){
                        id_pedido_aceptado = pedido.getId_pedido();
                        pedidoQuery = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
                        pedidoQuery.addValueEventListener(listener_pedido_aceptado);
                    }

                } else {
                    Pedidos borrar = new Pedidos();
                    for (Pedidos p :
                            pedidos_pendientes) {
                        if(p.getId_pedido().equals(pedido.getId_pedido())) {
                            borrar = p;
                        }
                    }
                    pedidos_pendientes.remove(borrar);
                    }
            }

            if(pedidos_pendientes.size() < 1){
                texto_vacio_pendientes.setVisibility(View.VISIBLE);
            }else{
                texto_vacio_pendientes.setVisibility(View.INVISIBLE);
                if (pedido_aceptado == null) {
                    layout_pendientes.setVisibility(View.VISIBLE);
                } else{
                    layout_pendientes.setVisibility(View.INVISIBLE);
                }
            }
            adapterServiciosPendientes.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            gestureDetector.onTouchEvent(motionEvent);
            return true;
        }
    };

    //fragmentos del menu
    Fragment_perfil fragment_perfil;
    Fragment_balance fragment_balance;
    private DrawerLayout drawer;

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    //para eventos del servidor


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //iniciar el dialogo para cuandop se está esperando el valor del servicio
        dialog_calculando = new Dialog(this, R.style.Theme_AppCompat_DialogWhenLarge);

        //esto es para crear los canales de notificaciones si aún no están creados
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CrearCanalesDeNOtificaciones(notificationManager);
        }
        //creando el dialogo confirmacion
        dialog_terminar_viaje = new Dialog(this, R.style.Theme_AppCompat_DialogWhenLarge);
        dialog_terminar_viaje.setCancelable(false);

        //inicializar los fragmentos
        fragment_balance = new Fragment_balance();
        fragment_perfil = new Fragment_perfil();
        fragmentBloqueado = new FragmentBloqueado();
        documentosFragm = new EnviarDocumentosFragm();
        historial = new Historial();
        fragmentChat = new FragmentChat();

        lottie_alerta_servicio = findViewById(R.id.lottie_alerta_servicio);
        markers = new ArrayList<>();

        //cargar el cuadrante de popayan
        Popayan = new LatLngBounds.Builder().
                include(new LatLng(2.419813, -76.659250))
                .include(new LatLng(2.494589, -76.556597)).build();

        //cargar animaciones
        hacia_abajo = AnimationUtils.loadAnimation(this, R.anim.aparecer_abajo_arriba);
        hacia_arriba = AnimationUtils.loadAnimation(this, R.anim.aparecer);

        //pantalla siempre encendida
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //iniciar locationllistener
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        points = new ArrayList<LatLng>();

        //inicializamos el frame para la carrera activa y sus componentes
        layout_info = findViewById(R.id.L_info);
        frame_carrera_activa = findViewById(R.id.frame_carrera_activa);
        frame_carrera_activa.setVisibility(View.INVISIBLE);
        textoDistancia = findViewById(R.id.textViewDistancia);
        textoValor = findViewById(R.id.textViewValor);
        cronometro = findViewById(R.id.cronometro);
        texto_dir_cliente = findViewById(R.id.texto_dir_final);
        texto_nom_cliente = findViewById(R.id.texto_nom_cliente);
        texto_num_cliente = findViewById(R.id.texto_tel_cliente);
        texto_num_cliente.setOnClickListener(this);
        texto_chat = findViewById(R.id.texto_chat);
        textoVersion = findViewById(R.id.textViewversionApp);
        //********************************
        //aqui vamos a inicializar el frame que indica los datos para recoger al cliente
        frame_recoger_pasajero = findViewById(R.id.frame_servicio_aceptado);
        frame_detalles_alertas = findViewById(R.id.frame_detalles);
        recoger_direccion = findViewById(R.id.dir_recoger_pasajero);
        recoger_nombre = findViewById(R.id.nom_recoger_pasajero);
        recoger_numero = findViewById(R.id.num_recoger_pasajero);
        wase_boton = findViewById(R.id.boton_waze);
        maps_boton = findViewById(R.id.boton_maps);
        text_cancelar = findViewById(R.id.text_cancelar);
        text_avisar = findViewById(R.id.text_avisar_llegada);

        //para pedidos pendientes
        refPedidosPendientes = database.child(Constantes.BD_PEDIDO_ESPECIAL);
        reciclerPedidospendientes = findViewById(R.id.recycler_servicios);
        reciclerPedidospendientes.setLayoutManager(new LinearLayoutManager(this));
        pedidos_pendientes = new ArrayList<>();
        adapterServiciosPendientes = new AdapterServiciosPendientes(pedidos_pendientes,this);
        reciclerPedidospendientes.setAdapter(adapterServiciosPendientes);
        reciclerPedidospendientes.setItemAnimator(new DefaultItemAnimator());
        layout_pendientes = findViewById(R.id.layout_pendientes);
        texto_vacio_pendientes = findViewById(R.id.text_servicios_vacio);
        layout_pendientes.setVisibility(View.INVISIBLE);
        textoDisponibles = findViewById(R.id.textView29);
        layout_pendientes.setOnTouchListener(touchListener);
        gestureDetector = new GestureDetector(this, new GestureListener() {
            @Override
            public void onSwipeTop(MotionEvent e) {
                super.onSwipeTop(e);
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) layout_pendientes.getLayoutParams();
                lp.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
                layout_pendientes.setLayoutParams(lp);
                ConstraintLayout.LayoutParams lpR = (ConstraintLayout.LayoutParams) reciclerPedidospendientes.getLayoutParams();
                lpR.topMargin = 200;
                reciclerPedidospendientes.setLayoutParams(lpR);
            }

            @Override
            public void onSwipeBottom(MotionEvent e) {
                super.onSwipeBottom(e);
                View v = getWindow().getDecorView();
                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) layout_pendientes.getLayoutParams();
                lp.height = v.getHeight() / 3;
                layout_pendientes.setLayoutParams(lp);
                ConstraintLayout.LayoutParams lpR = (ConstraintLayout.LayoutParams) reciclerPedidospendientes.getLayoutParams();
                lpR.topMargin = 200;
                reciclerPedidospendientes.setLayoutParams(lpR);
            }
        });


        //widgets de la pantalla botones y demas
        BotonIniciarCarrera = findViewById(R.id.btiniciarcarrera);
        ETAgregarDestino = findViewById(R.id.ETAgregarDestino);
        ETAgregarDestino.setOnClickListener(this);
        BotonIniciarCarrera.setOnClickListener(this);
        boton_info = findViewById(R.id.boton_info);
        boton_info.setOnClickListener(this);
        FAB_maps = findViewById(R.id.floating_maps);
        FAB_wase = findViewById(R.id.floating_waze);
        FAB_cancelar_servicio = findViewById(R.id.floating_cancelar_servicio);
        FAB_avisar_llegada = findViewById(R.id.floating_avisar_llegue);
        FAB_avisar_llegada.setOnClickListener(this);
        FAB_cancelar_servicio.setOnClickListener(this);
        FAB_wase.setOnClickListener(this);
        FAB_maps.setOnClickListener(this);
        FAB_wase.hide();
        FAB_maps.hide();
        text_cancelar.setVisibility(View.INVISIBLE);
        text_avisar.setVisibility(View.INVISIBLE);
        recoger_numero.setOnClickListener(this);
        FAB_avisar_llegada.hide();
        FAB_cancelar_servicio.hide();
        FAB_Chat = findViewById(R.id.fabChat);
        FAB_Chat.setOnClickListener(this);
        FAB_Chat.hide();
        texto_chat.setVisibility(View.INVISIBLE);
        //pra guardar datos
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        funtions = FirebaseFunctions.getInstance();
        configuracionRemotaFirebase = FirebaseRemoteConfig.getInstance();
        settingsConfRemote = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        configuracionRemotaFirebase.setConfigSettings(settingsConfRemote);
        configuracionRemotaFirebase.setDefaults(R.xml.remote_config_defaults);
        long cacheExpiration = 3600;
        if (configuracionRemotaFirebase.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        configuracionRemotaFirebase.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("FETCH CONFG", "cargados correctamente");
                    configuracionRemotaFirebase.activateFetched();


                } else {
                    Log.i("FETCH CONFG", "error al cargar");
                    Log.e("FETCH CONFG", task.getException() + "");
                }

                String mensje = configuracionRemotaFirebase.getString("mensaje_bienvenida");
                //toast(mensje);
            }
        });


        //boton para conectar y desconectar
        aSwitchEstado = findViewById(R.id.switch1);
        aSwitchEstado.setOnCheckedChangeListener(checkedChangeListener);
        //cambiarEstado(aSwitchEstado);//escucha para el cambio de estado

        sessionId = UUID.randomUUID().toString();


        //Configuracion para el trabajo de mantener el listener de la base de datos en background
        // configureJobManager();


        //obtener soporte para el mapa
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //validamos si la app se inicia desde el servicio
            String actionNotificacion = "";
            if (getIntent().getAction() != null) {
                actionNotificacion = getIntent().getAction();
            }
            Log.i("accion", actionNotificacion + " ");
            switch (actionNotificacion) {
                case Constantes.ACTION_INICIO_SESION:
                    Log.i("ACTION", "INICIO DE SESION");
                    //este es el caso en el que el usuario inicia sesion por primero vez o
                    // abre la app con la sesion ya activa

                    id_mensajero = getIntent().getStringExtra(Constantes.BD_ID_USUARIO);
                  SharedPreferences.Editor editor = sharedPref.edit();
                    if (id_mensajero != null) {
                        DatosMensajero(id_mensajero);
                        Log.i("INICIO DE SESION", id_mensajero);
                    } else {

                        if (mAuth.getCurrentUser() != null) {
                            DatosMensajero(mAuth.getCurrentUser().getUid());
                        }

                    }
                    /*
                     Aqui lo que hacemos es verificar el estado del mensjaero
                     y de acuerdo a eso aparecerá la pantalla correspondiente
                     - ESTADO_VERIFICAR == es cuando ya sube las imagenes pero aun hay que verificarlas.
                     - ESTADO_SUBIR_IMAGENES == es el estado con el que queda el mensajero cuando recien se registra
                                                para que aparezca la pantalla de subir las imagenes
                     - ESTADO_ACTIVO == para el mensajero que puede iniciar sin problemas
                     - ESTADO_BLOQUEADO == para el mensajero que se encuantra bloqueado por alguna razon
                    */
                    break;
                case Constantes.ACTION_DESCONECTAR:
                    Log.i("ACTION", "DESCONECTAR");
                    setConectado(false);
                    Intent deconectar = new Intent(MainActivity.this, ServicioDesconectar.class);
                    stopService(deconectar);
                    try {
                        finishAffinity();
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }
                    break;
                case Constantes.ACTION_CONECTAR:
                    Log.i("ACTION", "CONECTAR");
                    Intent conectar = new Intent(MainActivity.this, ServicioDesconectar.class);
                    stopService(conectar);
                    if (getIntent().getBooleanExtra(Constantes.ESTADO_CANCELADO, false)) {
                        toast("El cliente canceló el servicio");
                        dir_recoger = false;
                        dir_viaje = false;
                        editor = sharedPref.edit();
                        editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                        editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                        editor.apply();

                    }
                    setConectado(true);
                    break;
                case Constantes.ACTION_CONECTAR_DESDE_NOTIFICACION:
                    Log.i("ACTION", "CONECTAR_desde_motificacion");
                    try {
                        if (isMyServiceRunning(ServicioDesconectar.class)) {
                            Intent desconectar = new Intent(MainActivity.this, ServicioDesconectar.class);
                            stopService(desconectar);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    aSwitchEstado.setChecked(true);

                    break;
                case Constantes.SERVICIO_NUEVO:
                    Log.i("ACTION", "PEDIDO NUEVO");
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if(location != null)pocisionInicial = new LatLng(location.getLatitude(), location.getLongitude());
                    String id_pedido = getIntent().getStringExtra(Constantes.BD_ID_PEDIDO);
                    posicion_en_la_lista = getIntent().getStringExtra(Constantes.BD_POSICION_EN_LA_LISTA);
                    id_lista = getIntent().getStringExtra(Constantes.ID_LISTA);
                    Log.i("Id_pedido", id_pedido);
                    ActionServicioNuevo = true;
                    layout_pendientes.setVisibility(View.INVISIBLE);
                    ServicioRecibido(id_pedido);
                    dir_viaje = false;
                    dir_recoger = false;
                    editor = sharedPref.edit();
                    editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, true);
                    editor.apply();
                    break;

                case Constantes.ACTION_PEDIDO_ACEPTADO:
                    Intent detenerDesconectar = new Intent();
                    detenerDesconectar.setAction(Constantes.ACTION_PEDIDO_ACEPTADO);
                    sendBroadcast(detenerDesconectar);
                    editor = sharedPref.edit();
                    ETAgregarDestino.setVisibility(View.VISIBLE);
                    editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, true);
                    layout_pendientes.setVisibility(View.INVISIBLE);
                    editor.apply();
                    Log.i("ACTION", "PEDIDO ECPTADO");
                    FAB_maps.hide();
                    FAB_wase.hide();
                    FAB_cancelar_servicio.show();
                    FAB_avisar_llegada.show();
                    text_avisar.setVisibility(View.VISIBLE);
                    text_cancelar.setVisibility(View.VISIBLE);
                    dir_recoger = true;
                    Log.i("AcTION PEDIDOACEPT", "dir_recoger= " + dir_recoger);
                    id_pedido_aceptado = getIntent().getStringExtra(Constantes.BD_ID_PEDIDO);
                    editor = sharedPref.edit();
                    editor.putString(Constantes.BD_ID_PEDIDO_ACEPTADO, id_pedido_aceptado);
                    editor.apply();
                    pedidoQuery = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
                    break;
                case Constantes.ACTION_INICIAR_CARRERA:
                    dir_viaje = true;
                    dir_recoger = false;
                    ActionServicioApp = Constantes.BD_PEDIDO_ESPECIAL;
                    frame_carrera_activa.setVisibility(View.VISIBLE);
                    editor = sharedPref.edit();
                    editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, true);
                    editor.putString(Constantes.BD_TIPO_PEDIDO, ActionServicioApp);
                    editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                    editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                    editor.apply();
                    FAB_maps.show();
                    FAB_wase.show();
                    FAB_Chat.show();
                    FAB_cancelar_servicio.show();
                    FAB_avisar_llegada.hide();
                    text_cancelar.setVisibility(View.VISIBLE);
                    text_avisar.setVisibility(View.INVISIBLE);
                    ETAgregarDestino.setVisibility(View.VISIBLE);
                    Gson ggson = new Gson(); //Instancia Gson.
                    String jjson = sharedPref.getString(Constantes.BD_PEDIDO_ESPECIAL, null);
                    pedido_aceptado = ggson.fromJson(jjson, Pedidos.class);
                    Log.i("INICIAR CARRERA", "pedido=" + pedido_aceptado.getNombre());
                    //aqui vamos a agregar el punto al mapa a donde el cliente va a ir
                    //en caso del que el cliente no haya puesto un punto se deja el mapa en blanco

                    if (pedido_aceptado != null) {
                        id_pedido_aceptado = pedido_aceptado.getId_pedido();
                        frame_carrera_activa.startAnimation(hacia_arriba);
                        layout_info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        boton_info.setBackground(getDrawable(android.R.drawable.arrow_down_float));
                        texto_num_cliente.setText("Teléfono: " + pedido_aceptado.getTelefono());
                        if (pedido_aceptado.getDir_final() != null) {
                            texto_dir_cliente.setText("Dirección: " + pedido_aceptado.getDir_final());
                        } else {
                            texto_dir_cliente.setText("Dirección: " + "Preguntar al usuario");
                        }
                        texto_nom_cliente.setText("Nombre: " + pedido_aceptado.getNombre());
                    } else {
                        Log.i("INICIAR CARRERA", "pedido nulo");
                    }
                    break;

                case Constantes.INICIAR_DE_NOTIFICACION:
                    if (getIntent().getBooleanExtra(Constantes.MENSAJE_CHAT, false)) {
                        Log.i("INICIAR_DE_NOTIFICACION", "Mensaje chat true");
                        Gson gson1 = new Gson(); //Instancia Gson.
                        String json1 = sharedPref.getString(Constantes.BD_MENSAJERO_ESPECIAL, null);
                        mensajero = gson1.fromJson(json1, Mensajeros.class);
                        fragmentChat = new FragmentChat();
                        Bundle bundle = new Bundle();
                        id_pedido_aceptado = sharedPref.getString(Constantes.BD_ID_PEDIDO_ACEPTADO, null);
                        bundle.putString(Constantes.BD_NOMBRE_USUARIO, mensajero.getNombre());
                        bundle.putString(Constantes.BD_ID_PEDIDO, id_pedido_aceptado);
                        fragmentChat.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.main_cont, fragmentChat, FragmentChat.TAG);
                        ft.commit();
                    }
                    break;
                case Constantes.ALERTAS_MAPA:
                    unlockScreen();
                    mapa_movido_po_el_usuario = true;
                    break;
                default:
                    //reiniciarApp(this);
                    Log.i("get intent oncreate", "inicio sin action");
                    break;
            }


        //ponemos en pantalla el texto de la version de la app
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = "v " + info.versionName;
            textoVersion.setText(version);
            VerificarActualizaciones verificarActualizaciones = new VerificarActualizaciones(MainActivity.this, info.versionName);
            verificarActualizaciones.execute();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (fragment_perfil.isAdded()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(fragment_perfil);
            ft.commit();
            if (map == null) {
                Log.i("onBackpresed", "mapa nulo..");
            } else {
                Log.i("onBackpresed", "mapa OK..");
            }
        } else if (fragment_balance.isAdded()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(fragment_balance);
            ft.commit();
            if (map == null) {
                Log.i("onBackpresed", "mapa nulo..");
            } else {
                Log.i("onBackpresed", "mapa OK..");
            }
        } else if (historial.isAdded()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(historial);
            ft.commit();

        } else if (fragmentChat.isAdded()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(fragmentChat);
            ft.commit();
        } else if (frame_detalles_alertas.getVisibility() == View.VISIBLE) {
            View v = findViewById(R.id.frame_detalles);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 1f, -v.getHeight()));
            animator.setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {
                    frame_detalles_alertas.setVisibility(View.INVISIBLE);
                    ETAgregarDestino.setVisibility(View.INVISIBLE);
                    animation.removeAllListeners();
                }
            });
            animator.start();
        } else {

            if (isConectado()) {
                toast("permancerás conectado en segundo plano");
            }
            try {
                finishAffinity();
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            fragment_perfil = new Fragment_perfil();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_cont, fragment_perfil, Fragment_perfil.TAG);
            ft.commit();

        } else if (id == R.id.nav_balance) {
            if (codigo != null) {
                Bundle bundle = new Bundle();
                bundle.putString(Constantes.BD_CODIGO_MENSAJERO, codigo);
                fragment_balance = new Fragment_balance();
                fragment_balance.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_cont, fragment_balance, Fragment_balance.TAG);
                ft.commit();
            } else {
                if (id_mensajero != null) {
                    DatosMensajero(id_mensajero);
                }
            }

        } else if (id == R.id.nav_cerrar) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿ Seguro desea cerrar sesión ?")
                    .setCancelable(true)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (!isConectado()) {
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    } else {
                        toast("Primero debes desconectarte");
                        dialogInterface.dismiss();
                    }

                }
            }).show();


        } else if (id == R.id.nav_invitar) {

            String codigo = "";
            if(mensajero!=null)codigo = mensajero.getCodigo_referido();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            final String URL_DESCARGA = "http://cort.as/-7d6j";
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hola éste es mi código de referido " +
                    "de Mensajero: \n" + codigo + "\n descargala aqui...\n" + URL_DESCARGA);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_historial) {
            historial = new Historial();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_cont, historial, Historial.TAG);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

            miUbicacion();

        int hora = 0;
        Calendar horadeldia = Calendar.getInstance();
        hora = horadeldia.get(Calendar.HOUR_OF_DAY);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (isConectado()) {
                    InsertarAlertaServidor(latLng, MainActivity.this);
                }
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() != null) {
                    VerDetalleAlerta(marker, MainActivity.this);
                    AlertasMapa alertasMapa = (AlertasMapa) marker.getTag();
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(alertasMapa.getLat(),
                            alertasMapa.getLng()), 19));
                    marker.showInfoWindow();
                }
                return true;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getTag() != null) {
                    VerDetalleAlerta(marker, MainActivity.this);
                    AlertasMapa alertasMapa = (AlertasMapa) marker.getTag();
                    View v = findViewById(R.id.frame_detalles);
                    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -v.getHeight()));
                    animator.setDuration(300);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                            frame_detalles_alertas.setVisibility(View.INVISIBLE);
                            ETAgregarDestino.setVisibility(View.INVISIBLE);
                            animation.removeAllListeners();
                        }
                    });
                    animator.start();
                    Ruta(pocisionInicial, new LatLng(alertasMapa.getLat(), alertasMapa.getLng()));
                }
            }
        });
        if (hora <= 5 || hora >= 18) {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.stylo_noche));
        } else {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(MainActivity.this, R.raw.stylo_gris));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mapa_movido_po_el_usuario = false;
                    miUbicacion();
                    return true;
                }
            });
        }

        map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                lottie_alerta_servicio.setVisibility(View.INVISIBLE);

                if (i == REASON_GESTURE) {
                    mapa_movido_po_el_usuario = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        View v = findViewById(R.id.frame_detalles);
                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -v.getHeight()));
                        animator.setDuration(300);
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation, boolean isReverse) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation, boolean isReverse) {
                                frame_detalles_alertas.setVisibility(View.INVISIBLE);
                                ETAgregarDestino.setVisibility(View.INVISIBLE);
                                animation.removeAllListeners();
                            }
                        });
                        animator.start();
                    } else {
                        frame_detalles_alertas.setVisibility(View.INVISIBLE);
                        ETAgregarDestino.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View v = findViewById(R.id.frame_detalles);
                    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -v.getHeight()));
                    animator.setDuration(300);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                            frame_detalles_alertas.setVisibility(View.INVISIBLE);
                            ETAgregarDestino.setVisibility(View.INVISIBLE);
                            animation.removeAllListeners();
                        }
                    });
                    animator.start();
                } else {
                    frame_detalles_alertas.setVisibility(View.INVISIBLE);
                    ETAgregarDestino.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void PosicionCamara(GoogleMap map, Location location) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(19)// Sets the orientation of the camera to east
                .bearing(location.getBearing())
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void activarLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled) {
            Intent intentSetings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intentSetings);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
    }

    public void obtenerDireccion(LatLng location, Marker marker) {
        if (location.latitude != 0.0 && location.longitude != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                );
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    Direccion = (address.getAddressLine(0));
                    if (Direccion != null) {
                        marker.setTitle(Direccion.replace("Popayán, Cauca, Colombia", " "));
                        marker.showInfoWindow();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        if (marcador != null) marcador.remove();
        try {
            marcador = map.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title(Direccion)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_foreground)));
        } catch (Exception e) {
            e.printStackTrace();
            marcador = map.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title(Direccion));
        }

    }

    private void agregarAlertaMapa(AlertasMapa alertasMapa) {
        LatLng coordenadas = new LatLng(alertasMapa.getLat(), alertasMapa.getLng());
        Marker marcador = map.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(alertasMapa.getTipo_alerta()));
        marcador.setTag(alertasMapa);
        markers.add(marcador);
        switch (alertasMapa.getTipo_alerta()) {
            case Constantes.ALERTA_720:
                marcador.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.cono));
                break;
            case Constantes.ALERTA_SERVICIOS:
                marcador.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.multitud));
                obtenerDireccion(coordenadas, marcador);
                if (alertasMapa.getNombre_creador().equals(mensajero.getNombre())) {
                    Log.i(Constantes.ALERTAS_MAPA, "alerta agregada por el mismo mensajero");
                } else {
                    if (alertasMapa.getCodigo_mensajero_servicio() == null) {
                        lottie_alerta_servicio.setVisibility(View.VISIBLE);
                        lottie_alerta_servicio.playAnimation();
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 18));
                        mapa_movido_po_el_usuario = true;
                        MediaPlayer sonidoservicio = MediaPlayer.create(this, R.raw.servicio_nuevo);
                        sonidoservicio.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.setLooping(false);
                                mediaPlayer.start();
                            }
                        });
                    }
                }

                break;
            case Constantes.ALERTA_VARADA:
                marcador.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.varada));
                break;
            case Constantes.ALERTA_CALLE_CERRADA:
                marcador.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.calle_cerrada));
                break;
            case Constantes.ALERTA_ACCIDENTE:
                marcador.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.accidente));
                break;

        }
    }

    private void ActualizarUbicacion(Location location) {
        if (location != null) {
            if (SERVICIO_ACTIVO) {
                lat_actualzada = location.getLatitude();
                lng_actualizada = location.getLongitude();
                pocisionFinal = new LatLng(lat_actualzada, lng_actualizada);
                //agregarMarcador(lat_actualzada, lng_actualizada);
                pocisionInicial = new LatLng(lat_actualzada, lng_actualizada);
                if (map != null && !mapa_movido_po_el_usuario) {
                    PosicionCamara(map, location);
                }
            } else {
                lat = location.getLatitude();
                lng = location.getLongitude();
                pocisionInicial = new LatLng(lat, lng);
                LatLng coordenadas = new LatLng(lat, lng);

                if (map != null && !mapa_movido_po_el_usuario) {
                    PosicionCamara(map, location);
                }
            }
        }
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            ActualizarUbicacion(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            String mensaje = "GPS Desactivado";
            activarLocation();
            toast(mensaje);
        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int PEDIR_PERMISO_LOCATION = 101;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PEDIR_PERMISO_LOCATION);
            return;
        } else {
            //comprovamos el estado del gps
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertaNoGps();
            } else {
                //permiso de localizacion

                try {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    pocisionInicial = new LatLng(location.getLatitude(), location.getLongitude());
                    ActualizarUbicacion(location);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    toast("Error de ubicacion, reinicie la app");
                    //reiniciarApp(this,null);
                }

            }

        }

    }

    public void toast(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void DatosMensajero(final String id_mensajero) {
        //solicitamos token del mensjaero
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setTitle("Cargando...");
        builder.setMessage("Si ésto tarda más de 10 segundos por favor verifica tu conexión a internet y vuelve a abrir la app");
        builder.setCancelable(false);
        builder.show();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        token = instanceIdResult.getToken();
                        final Query query = database.child(Constantes.BD_MENSAJERO_ESPECIAL).orderByChild(Constantes.BD_ID_MENSAJERO).equalTo(id_mensajero);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() == 1) {
                                    for (DataSnapshot snap:
                                            dataSnapshot.getChildren()) {
                                        mensajero = snap.getValue(Mensajeros.class);
                                    }
                                    codigo = mensajero.getCodigo();
                                    placa = mensajero.getPlaca();
                                    estado_mensajero = mensajero.getEstado();
                                    mensajero.setToken(token);
                                    TextView nombre = findViewById(R.id.texto_nombre);
                                    TextView correo = findViewById(R.id.texto_correo);
                                    ImageView imagen_perfil = findViewById(R.id.imagen_perfil);

                                    nombre.setText(mensajero.getNombre());
                                    correo.setText(mensajero.getEmail());

                                        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getDisplayName() == null) {
                                            FirebaseUser user = mAuth.getCurrentUser();

                                            user.updateEmail(mensajero.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(mensajero.getNombre())
                                                    .build();

                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("update profile", "User profile updated.");
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Log.i("get nombre", "nombre NO nulo");
                                        }
                                    String pathFoto = Constantes.URL_FOTO_PERFIL_CONDUCTOR + mensajero.getCodigo();

                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageReference = storage.getReference().child(pathFoto + "/foto_perfil");


                                    GlideApp.with(MainActivity.this)
                                            .load(storageReference)
                                            .into(imagen_perfil);

                                    //guardamos los datos del mensajero en las preferencias
                                    Gson gson = new Gson();
                                    String json = gson.toJson(mensajero);//convertimos el objeto a json
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(Constantes.BD_MENSAJERO_ESPECIAL, json);
                                    editor.apply();
                                    builder.dismiss();

                                    Log.i("estado mensajero", estado_mensajero);
                                    IniciarOSolicitarCompletar(estado_mensajero);

                                    }

                             }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                toast("verifica tu conexión a internet");
                                builder.dismiss();
                            }
                    });
                        }
                });
        }

    public static void reiniciarApp(Activity actividad, @Nullable Boolean cancelar) {
        Log.i("reiniciando", actividad.getLocalClassName());
        Intent intent = new Intent(actividad, MainActivity.class);
        intent.setAction(Constantes.ACTION_CONECTAR);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (cancelar != null) {
            intent.putExtra(Constantes.ESTADO_CANCELADO, cancelar);

        }
        //finalizamos la actividad actual
        try {
            actividad.finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
            actividad.finish();
        }
        //llamamos a la actividad
        actividad.startActivity(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        try {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (fragmentBloqueado.isAdded() || documentosFragm.isAdded()) {
            if (isConectado()) aSwitchEstado.setChecked(false);
            aSwitchEstado.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.INVISIBLE);
        } else {
            aSwitchEstado.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }

        ActionCalcularViaje = false;
        codigo = sharedPref.getString(Constantes.BD_CODIGO, null);
        SERVICIO_ACTIVO = sharedPref.getBoolean(Constantes.SERVICIO_ACTIVO, false);
        id_pedido_aceptado = sharedPref.getString(Constantes.BD_ID_PEDIDO_ACEPTADO, null);
        if (!dir_viaje) {
            dir_viaje = sharedPref.getBoolean(Constantes.ACTION_INICIAR_CARRERA, false);
        }
        if (!dir_recoger) {
            dir_recoger = sharedPref.getBoolean(Constantes.ACTION_RECOGER, false);
        }
        ActionServicioApp = sharedPref.getString(Constantes.BD_TIPO_PEDIDO, Constantes.SERVICIO_GENERICO);
        if (id_pedido_aceptado != null) {
            pedidoQuery = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
            pedidoQuery.addValueEventListener(listener_pedido_aceptado);
        }

        if (codigo != null) {
            isConnectToDatabase(codigo);
        }
        if (SERVICIO_ACTIVO) {
            bindService(new Intent(MainActivity.this, ServicioCarrera.class)
                    , mServiceConnection, Context.BIND_AUTO_CREATE);
            BotonIniciarCarrera.setText("TERMINAR CARRERA");
            layout_pendientes.setVisibility(View.INVISIBLE);
        }
        sessionId = UUID.randomUUID().toString();
        frame_detalles_alertas.setVisibility(View.INVISIBLE);

    }

    public void isConnectToDatabase(@NonNull String codigo) {
        DatabaseReference currenMensajero = database.child(Constantes.BD_MENSAJERO_ESPECIAL_CONECTADO)
                .child(codigo);
        currenMensajero.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    setConectado(true);
                    if (mensajero == null) {
                        mensajero = dataSnapshot.getValue(Mensajeros.class);
                    }
                    aSwitchEstado.setChecked(true);
                    Log.i("esta conectado ", "yes");
                } else {
                    setConectado(false);
                    aSwitchEstado.setChecked(false);
                    Log.i("esta conectado ", "false");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                aSwitchEstado.setChecked(false);
                setConectado(false);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("on stop()", "dir_recoger = " + dir_recoger);
        Log.i("OnStop()", "dir_viaje= " + dir_viaje);
        if (dir_viaje) dir_recoger = false;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constantes.BD_ID_PEDIDO_ACEPTADO, id_pedido_aceptado);
        editor.putBoolean(Constantes.SERVICIO_ACTIVO, SERVICIO_ACTIVO);
        editor.putString(Constantes.BD_CODIGO, codigo);
        editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
        editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
        editor.putString(Constantes.BD_TIPO_PEDIDO, ActionServicioApp);
        editor.apply();
        map.clear();
        cronometro.stop();
        cronometro.setBase(SystemClock.elapsedRealtime());
        if (alertasQuery != null) {
            alertasQuery.removeEventListener(listenerAlertasMapa);
        }

        if (refPedidosPendientes != null) {
            pedidos_pendientes.clear();
            refPedidosPendientes.removeEventListener(listenerPedidosPendientes);
        }

        if (pedidoQuery != null && listener_pedido_aceptado != null) {
            pedidoQuery.removeEventListener(listener_pedido_aceptado);
        }

        if (servicioEnlazado) {
            unbindService(mServiceConnection);
            servicioEnlazado = false;
        }
    }


    @Override
    protected void onDestroy() {
        // toast("on destoy");
        Log.i("On Destroy()", "dir_recoger= " + dir_recoger);
        Log.i("OnDestroy()", "dir_viaje= " + dir_viaje);
        if (dir_viaje) dir_recoger = false;
        if (sharedPref != null) {
            SharedPreferences.Editor editor = sharedPref.edit();
            //editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, Conectado);
            editor.putBoolean(Constantes.SERVICIO_ACTIVO, SERVICIO_ACTIVO);
            editor.putString(Constantes.BD_CODIGO, codigo);
            editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
            editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
            editor.putString(Constantes.BD_TIPO_PEDIDO, ActionServicioApp);
            editor.apply();
        }

        super.onDestroy();

    }

    @Override
    protected void onResume() {
        setConectado(isConectado());

        receptorMensajesServidor = new ReceptorMensajesServidor();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constantes.ACTION_CONFIRMAR_VALOR_VIAJE);
        intentFilter.addAction(Constantes.ACTION_MENSAJE_CHAT);
        intentFilter.addAction(Constantes.ACTION_ACTUALIZAR);
        intentFilter.addAction(Constantes.SERVICIO_NUEVO);
        registerReceiver(receptorMensajesServidor, intentFilter);

        Gson gson = new Gson(); //Instancia Gson.
        String json = sharedPref.getString(Constantes.BD_MENSAJERO_ESPECIAL, null);
        mensajero = gson.fromJson(json, Mensajeros.class);

        if (documentosFragm != null) {
            if (documentosFragm.isAdded()) {
                aSwitchEstado.setVisibility(View.INVISIBLE);
            }
        }
        if (fragmentBloqueado != null) {
            if (fragmentBloqueado.isAdded()) {
                aSwitchEstado.setVisibility(View.INVISIBLE);
            }
        }


        if (map != null) {
                miUbicacion();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        // toast("on pause");
        if (isConectado()) {
            cronometro.stop();
            cronometro.setBase(SystemClock.elapsedRealtime());
        }

        try {
            //guardamos los datos del mensajero en las preferencias
            Gson gson = new Gson();
            String json = gson.toJson(mensajero);//convertimos el objeto a json
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constantes.BD_MENSAJERO_ESPECIAL, json);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (receptorMensajesServidor != null) {
            unregisterReceiver(receptorMensajesServidor);
        }
        super.onPause();
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
                .maxConsumerCount(1)//up to 3 consumers at a time
                .loadFactor(1)//3 jobs per consumer
                .consumerKeepAlive(60)//wait 2 minute
                .build();
        jobManager = new JobManager(configuration);
    }

    public void toastrecibido(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    private void IniciarOSolicitarCompletar(String estado) {

        switch (estado) {
            case Constantes.ESTADO_ACTIVO:
                Log.i(Constantes.BD_ESTADO_MENSAJERO, Constantes.ESTADO_ACTIVO);
                aSwitchEstado.setVisibility(View.VISIBLE);
                aSwitchEstado.setEnabled(true);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case Constantes.ESTADO_BLOQUEADO:
                try {
                    Log.i(Constantes.BD_ESTADO_MENSAJERO, Constantes.ESTADO_BLOQUEADO);
                    ETAgregarDestino.setVisibility(View.INVISIBLE);
                    aSwitchEstado.setVisibility(View.INVISIBLE);
                    aSwitchEstado.setEnabled(false);
                    toolbar.setVisibility(View.INVISIBLE);
                    drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            drawer.closeDrawer(GravityCompat.START);
                        }

                        @Override
                        public void onDrawerOpened(View drawerView) {

                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {

                        }

                        @Override
                        public void onDrawerStateChanged(int newState) {

                        }
                    });
                    fragmentBloqueado = new FragmentBloqueado();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_cont, fragmentBloqueado, FragmentBloqueado.TAG);
                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    reiniciarApp(this, null);
                }
                break;
            case Constantes.ESTADO_SUBIR_IMAGENES:
                toolbar.setVisibility(View.INVISIBLE);
                drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        drawer.closeDrawer(GravityCompat.START);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });
                Log.i(Constantes.BD_ESTADO_MENSAJERO, Constantes.ESTADO_SUBIR_IMAGENES);
                if (codigo != null) {

                    try {
                        documentosFragm = new EnviarDocumentosFragm(codigo);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.main_cont, documentosFragm, EnviarDocumentosFragm.TAG);
                        ft.commit();
                        aSwitchEstado.setVisibility(View.INVISIBLE);
                        aSwitchEstado.setEnabled(false);
                        ETAgregarDestino.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        reiniciarApp(this, null);
                    }
                } else {
                    finish();
                }

                break;
            case Constantes.ESTADO_VERIFICAR:
                try {
                    Log.i(Constantes.BD_ESTADO_MENSAJERO, Constantes.ESTADO_BLOQUEADO);
                    fragmentBloqueado = new FragmentBloqueado();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main, fragmentBloqueado, FragmentBloqueado.TAG);
                    ft.commit();
                    ETAgregarDestino.setVisibility(View.INVISIBLE);
                    aSwitchEstado.setVisibility(View.INVISIBLE);
                    aSwitchEstado.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    reiniciarApp(this, null);
                }
                break;
            default:

                break;
        }

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public boolean isConectado() {
        return Conectado;
    }

    public void setConectado(boolean conectado) {
        Conectado = conectado;
    }

    public boolean DatosActivos() {

        //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring?hl=es-419

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }


    public Boolean HayConexionInternet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ComprobarConexion(Context context) {
        if (!DatosActivos()) {
            AlertaNoInternet();
        } else if (!HayConexionInternet()) {
            Log.i("sininternet", "sin conexion a internet");
        }
    }

    private void AlertaNoInternet() {

        AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El acceso a internet está desactivado y es necesario para el uso de la aplication. ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alert = builder.create();
        alert.show();

    }

    private void AlertaNoGps() {

        AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Es necesario activar el GPS y/o configurarlo en modo de alta precisión " +
                "con Wi-fi y Redes móviles. ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btiniciarcarrera:
                if (isConectado()) {
                    String texto_boton = BotonIniciarCarrera.getText().toString();

                    switch (texto_boton) {
                        case "INICIAR CARRERA":
                            dir_recoger = false;
                            FAB_cancelar_servicio.hide();
                            text_cancelar.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("¿Iniciar Viaje?")
                                    .setCancelable(false)
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if (ActionServicioApp.equals(Constantes.BD_PEDIDO_ESPECIAL)) {
                                                try {
                                                    if (isOnline(MainActivity.this)) {
                                                        currentUserDB = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
                                                        currentUserDB.child(Constantes.BD_ESTADO_PEDIDO).setValue(Constantes.ESTADO_VIAJE_INICIADO)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        toast("viaje iniciado correctamente");
                                                                        Intent IntentCarreraNueva = new Intent(MainActivity.this, ServicioCarrera.class);
                                                                        IntentCarreraNueva.putExtra("lat_ini", lat);
                                                                        IntentCarreraNueva.putExtra("log_ini", lng);
                                                                        IntentCarreraNueva.setAction(Constantes.ACTION_INICIAR_CARRERA);
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                            startForegroundService(IntentCarreraNueva);
                                                                        } else {
                                                                            startService(IntentCarreraNueva);
                                                                        }
                                                                        bindService(IntentCarreraNueva, mServiceConnection, Context.BIND_AUTO_CREATE);
                                                                        SERVICIO_ACTIVO = true;
                                                                        cronometro.setBase(SystemClock.elapsedRealtime());
                                                                        cronometro.start();
                                                                        BotonIniciarCarrera.setText("TERMINAR CARRERA");
                                                                        if (map != null) {
                                                                            map.clear();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        toast("Verifique su conexión a internet e intente nuevamente");
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    toast("Ha ocurrido un error, intente más tarde");
                                                }
                                            } else {
                                                Intent IntentCarreraNueva = new Intent(MainActivity.this, ServicioCarrera.class);
                                                IntentCarreraNueva.putExtra("lat_ini", lat);
                                                IntentCarreraNueva.putExtra("log_ini", lng);
                                                IntentCarreraNueva.setAction(Constantes.ACTION_INICIAR_CARRERA);
                                                startService(IntentCarreraNueva);
                                                bindService(IntentCarreraNueva, mServiceConnection, Context.BIND_AUTO_CREATE);
                                                SERVICIO_ACTIVO = true;
                                                cronometro.setBase(SystemClock.elapsedRealtime());
                                                cronometro.start();
                                                BotonIniciarCarrera.setText("TERMINAR CARRERA");
                                                if (map != null) {
                                                    map.clear();
                                                }

                                            }
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();

                            break;
                        case "TERMINAR CARRERA":
                            if (ActionServicioApp.equals(Constantes.BD_PEDIDO_ESPECIAL)) {

                                AlertDialog.Builder builderTerm = new AlertDialog.Builder(MainActivity.this);
                                builderTerm.setMessage("¿Terminar Viaje?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (isOnline(MainActivity.this)) {

                                                    try {
                                                        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                                                        View view = inflater.inflate(R.layout.layout_calculando, null);
                                                        dialog_calculando.setContentView(view);
                                                        dialog_calculando.setCancelable(false);
                                                        dialog_calculando.show();
                                                        //distancia en metros
                                                        Double dist = servicioCarrera.getDistancia();
                                                        //tiempo den minutos
                                                        long tiempo = servicioCarrera.getTiempoTranscurrido();
                                                        Log.i("terminar viaje", "tiempo " + tiempo + "distancia " + dist);
                                                        final String id_pedido = pedido_aceptado.getId_pedido();
                                                        String codigo_mensajero = mensajero.getCodigo();
                                                        String id_usuario = pedido_aceptado.getId_usuario();
                                                        Calendar c = Calendar.getInstance();
                                                        int hora_del_dia = c.get(Calendar.HOUR_OF_DAY);

                                                        TerminarViaje(tiempo, dist, id_pedido, codigo_mensajero, id_usuario, hora_del_dia)
                                                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<String> task) {
                                                                        if(task.isSuccessful()){
                                                                            Log.i("terminar viaje", "OK");
                                                                            toast("Servicio terminado exitosamente");
                                                                            servicioEnlazado = false;
                                                                            SERVICIO_ACTIVO = false;
                                                                            cronometro.stop();
                                                                            cronometro.setBase(SystemClock.elapsedRealtime());
                                                                            textoDistancia.setText("");
                                                                            BotonIniciarCarrera.setText("INICIAR CARRERA");
                                                                            texto_num_cliente.setText("Teléfono: ");
                                                                            texto_dir_cliente.setText("Dirección: ");
                                                                            texto_nom_cliente.setText("Nombre: ");
                                                                            textoDistancia.setText("Distancia Recorrida: ");
                                                                            textoValor.setText("Valor: ");
                                                                            map.clear();
                                                                            points = null;
                                                                            FAB_maps.hide();
                                                                            FAB_wase.hide();
                                                                            FAB_cancelar_servicio.hide();
                                                                            text_cancelar.setVisibility(View.INVISIBLE);
                                                                            dir_viaje = false;
                                                                            dir_recoger = false;
                                                                            SharedPreferences.Editor editor = sharedPref.edit();
                                                                            editor.putBoolean(Constantes.ACTION_INICIAR_CARRERA, dir_viaje);
                                                                            editor.putBoolean(Constantes.SERVICIO_ACTIVO, SERVICIO_ACTIVO);
                                                                            editor.putBoolean(Constantes.ACTION_RECOGER, dir_recoger);
                                                                            editor.apply();
                                                                            Intent IntentTerminarCarrera = new Intent(MainActivity.this, ServicioCarrera.class);
                                                                            stopService(IntentTerminarCarrera);
                                                                            unbindService(mServiceConnection);
                                                                        } else {
                                                                            toast("Algo salió mal!");
                                                                            dialog_calculando.dismiss();
                                                                        }
                                                                    }
                                                                });

                                                    } catch (Exception e) {
                                                        Log.i("terminar viaje", e.getLocalizedMessage());
                                                        toast("Ha ocurrido un error, intente mas tarde");
                                                        dialog_calculando.dismiss();
                                                    }

                                                    ActionServicioApp = Constantes.SERVICIO_GENERICO;
                                                    pedido_aceptado = null;
                                                    try {
                                                        sharedPref.edit().remove(Constantes.BD_PEDIDO_ESPECIAL).apply();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    toast("verifique su conexión a internet e intente nuevamente");
                                                }


                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                            } else {

                                try {
                                    AlertDialog.Builder builderTerm = new AlertDialog.Builder(MainActivity.this);
                                    builderTerm.setMessage("¿Terminar Viaje?" + " Valor: $" + valor)
                                            .setCancelable(false)
                                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent IntentTerminarCarrera = new Intent(MainActivity.this, ServicioCarrera.class);
                                                    stopService(IntentTerminarCarrera);
                                                    unbindService(mServiceConnection);
                                                    servicioEnlazado = false;
                                                    SERVICIO_ACTIVO = false;
                                                    cronometro.stop();
                                                    cronometro.setBase(SystemClock.elapsedRealtime());
                                                    textoDistancia.setText("");
                                                    BotonIniciarCarrera.setText("INICIAR CARRERA");
                                                    texto_num_cliente.setText("Teléfono: ");
                                                    texto_dir_cliente.setText("Dirección: ");
                                                    texto_nom_cliente.setText("Nombre: ");
                                                    textoDistancia.setText("Distancia Recorrida: ");
                                                    textoValor.setText("Valor: ");
                                                    map.clear();
                                                    points = null;
                                                    FAB_maps.hide();
                                                    FAB_wase.hide();
                                                    FAB_cancelar_servicio.hide();
                                                    text_cancelar.setVisibility(View.INVISIBLE);
                                                    dir_viaje = false;
                                                    dir_recoger = false;
                                                    ActionServicioApp = Constantes.SERVICIO_GENERICO;
                                                    pedido_aceptado = null;
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    toast("Ha ocurrido un error, intente más tarde");
                                }
                            }

                            break;

                    }


                } else {
                    toast("primero debes conectarte");
                }
                break;
            case R.id.boton_info:

                if (layout_info.getHeight() == 0) {

                    frame_carrera_activa.startAnimation(hacia_arriba);
                    layout_info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    boton_info.setBackground(getDrawable(android.R.drawable.arrow_down_float));
                } else {

                    boton_info.setBackground(getDrawable(android.R.drawable.arrow_up_float));
                    frame_carrera_activa.startAnimation(hacia_abajo);
                    layout_info.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                }
                break;
            case R.id.floating_maps:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + pocisionFinal.latitude + "," + pocisionFinal.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    toast("No tienes instalada la app");
                }
                break;
            case R.id.floating_waze:
                Uri waseuri = Uri.parse("waze://?ll=" + pocisionFinal.latitude + "," + pocisionFinal.longitude + "&navigate=yes");
                Intent waseintent = new Intent(Intent.ACTION_VIEW, waseuri);
                if (waseintent.resolveActivity(getPackageManager()) != null) {
                    startActivity(waseintent);
                } else {
                    toast("No tienes instalada la app");
                }
                break;
            case R.id.fabChat:
                fragmentChat = new FragmentChat();
                Bundle bundle = new Bundle();
                bundle.putString(Constantes.BD_NOMBRE_USUARIO, mensajero.getNombre());
                bundle.putString(Constantes.BD_ID_PEDIDO, pedido_aceptado.getId_pedido());
                fragmentChat.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_cont, fragmentChat, FragmentChat.TAG);
                ft.commit();
                break;
            case R.id.ETAgregarDestino:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ETAgregarDestino.getWindowToken(), 0);
                if (isConectado()) {
                    try {

                        if (Popayan == null) {
                            //cargar el cuadrante de popayan
                            Popayan = new LatLngBounds.Builder().
                                    include(new LatLng(2.419813, -76.659250))
                                    .include(new LatLng(2.494589, -76.556597)).build();
                        }

                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                                .build();
                        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .setFilter(typeFilter)
                                .setBoundsBias(Popayan)
                                .build(this);
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                    } catch (GooglePlayServicesNotAvailableException e) {
                    }
                } else {
                    toast("No estás conectado");
                }
                break;
            case R.id.texto_tel_cliente:
                Log.i("Click telefono", "click ");
                try {
                    if (pedido_aceptado != null) {
                        Intent llamar = new Intent(Intent.ACTION_DIAL);
                        llamar.setData(Uri.parse("tel:" + pedido_aceptado.getTelefono()));
                        startActivity(llamar);
                    } else {
                        toast("Telefono vacío");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("Telefono vacío");
                }
                break;
            case R.id.num_recoger_pasajero:
                Log.i("Click telefono", "click ");
                if (pedido_aceptado == null) {
                    if (id_pedido_aceptado != null) {
                        pedidoQuery = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(id_pedido_aceptado);
                        pedidoQuery.addValueEventListener(listener_pedido_aceptado);

                    }
                }
                if (pedido_aceptado.getTelefono() != null) {
                    Intent llamar = new Intent(Intent.ACTION_DIAL);
                    llamar.setData(Uri.parse("tel:" + pedido_aceptado.getTelefono()));
                    startActivity(llamar);
                } else {
                    toast("Ha ocurrido un error, intente más tarde");
                }

                break;
            case R.id.floating_cancelar_servicio:
                AlertDialog.Builder builderTerm = new AlertDialog.Builder(MainActivity.this);
                builderTerm.setMessage("¿Seguro desea cancelar Viaje?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Log.i("click en si", "click");
                                    currentUserDB = database.child(Constantes.BD_PEDIDO_ESPECIAL).child(pedido_aceptado.getId_pedido());
                                    currentUserDB.child(Constantes.BD_ESTADO_PEDIDO).setValue(Constantes.ESTADO_CANCELADO);
                                    stopService(new Intent(MainActivity.this, ServicioCarrera.class));
                                    reiniciarApp(MainActivity.this, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    toast("Ha ocurrido un error intente nuevamente");
                                    dialogInterface.dismiss();

                                }

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Log.i("click en no", "click");
                    }
                }).show();
                break;
            case R.id.floating_avisar_llegue:
                if (pedido_aceptado != null) {

                    ChatServicio(Constantes.CONFIRMAR_LLEGADA, pedido_aceptado.getToken());
                    stopService(new Intent(MainActivity.this, ServicioCarrera.class));

                    Intent iniciar_carrera = new Intent(this, MainActivity.class);
                    iniciar_carrera.setAction(Constantes.ACTION_INICIAR_CARRERA);
                    startActivity(iniciar_carrera);
                    try {
                        finishAffinity();
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }

                }

                break;

        }
    }

    private String obtenerDireccionesURL(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String sensor = "sensor=false";

        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" +
                getResources().getString(R.string.google_maps_key_PROD);

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creamos una conexion http
            urlConnection = (HttpURLConnection) url.openConnection();

            // Conectamos
            urlConnection.connect();

            // Leemos desde URL
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
            Log.i("error de Conexion", "sin internet");

        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //some code....
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
            case MotionEvent.ACTION_MOVE:
                float orientacion = motionEvent.getOrientation();

                break;
            default:
                break;
        }
        return true;
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("ERRORALOBTENERINFODELWS", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);

        }
    }

    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result != null) {
                points = null;

                lineOptions = new PolylineOptions();

                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();

                    List<HashMap<String, String>> path = result.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        if (j == 0) {
                            pocisionInicial = new LatLng(lat, lng);
                        } else if (j == path.size() - 1) {
                            pocisionFinal = new LatLng(lat, lng);
                        }

                        points.add(position);

                    }

                    lineOptions.addAll(points);
                    lineOptions.width(15);
                    lineOptions.jointType(JointType.ROUND);
                    lineOptions.startCap(new RoundCap());
                    lineOptions.endCap(new RoundCap());
                    lineOptions.color(Color.rgb(41, 135, 189));

                    if (frame_recoger_pasajero.getVisibility() == View.INVISIBLE) {
                        FAB_maps.show();
                        FAB_wase.show();
                    }
                }
                if (lineOptions != null && map != null) {
                    mapa_movido_po_el_usuario = false;
                    map.addPolyline(lineOptions);
                    LatLngBounds rutazoom = new LatLngBounds.Builder()
                            .include(pocisionInicial)
                            .include(pocisionFinal)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(rutazoom, 15));

                }
            }

        }
    }

    public class DirectionsJSONParser {

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;


            try {

                jRoutes = jObject.getJSONArray("routes");

                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();

                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString((list.get(l)).latitude));
                                hm.put("lng", Double.toString((list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }

            return routes;
        }

        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

    public class DownloadTaskDistancia extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("ERRORALOBTENERINFODELWS", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTaskDistancia parserTask = new ParserTaskDistancia();

            parserTask.execute(result);


        }
    }

    public class ParserTaskDistancia extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... jsonData) {

            JSONObject jObject = null;


            try {
                jObject = new JSONObject(jsonData[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            String distanciatexto = "";
            String tiempotexto = "";
            int distancia = 0;
            int tiempo = 0;

            try {
                JSONArray rowsArray = result.getJSONArray("rows");
                Log.i("Jsonelement", rowsArray.toString());
                JSONObject rowsOBJ = null;
                rowsOBJ = rowsArray.getJSONObject(0);
                JSONArray elementArray = rowsOBJ.getJSONArray("elements");
                //para la distancia
                JSONObject elementOBJ = elementArray.getJSONObject(0);
                JSONObject distance = elementOBJ.getJSONObject("distance");
                //para el tiempo
                JSONObject tiempoOBJ = elementOBJ.getJSONObject("duration");

                Log.i("Jsondistance", distance.toString());
                distanciatexto = distance.getString("text");
                distancia = distance.getInt("value");
                tiempotexto = tiempoOBJ.getString("text");
                tiempo = tiempoOBJ.getInt("value");

                Tiempo = tiempo;
                Distancia = distancia;

                //esto es cuando se recibe una carrera de la app, para verificar la distancia a la que
                //se enucuentra el cliente
                if (ActionServicioNuevo) {
                    try {
                        Log.i("parserdist", "servicio nuevo");
                        ServicioNuevo.putExtra(Constantes.TIEMPO, Tiempo);
                        ServicioNuevo.putExtra(Constantes.DISTANCIA, Distancia);
                        ServicioNuevo.putExtra(Constantes.BD_POSICION_EN_LA_LISTA, posicion_en_la_lista);
                        ServicioNuevo.putExtra(Constantes.ID_LISTA, id_lista);
                        startActivity(ServicioNuevo);
                        ActionServicioNuevo = false;
                        posicion_en_la_lista = null;
                        id_lista = null;
                        try {
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                            finish();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                } else if (ActionCalcularViaje) {
                    //esto es para calcular un viaje a realizar
                    Log.i("ACTIONCALCULARVIAJE", "OK");
                    ActionCalcularViaje = false;
                    textoDistancia.setText("Distancia aprox: " + Distancia / 1000 + " km");
                    valor = (int) (((Distancia / 1000) * (530 * modificadorDeTarifaSegunLaHora())) + (Tiempo / 1000 * (120 * modificadorDeTarifaSegunLaHora())) + 1800);
                    if (valor <= 3300) valor = 3300;
                    textoValor.setText("Valor aprox: $" + valor);
                    cronometro.setOnChronometerTickListener(null);
                    cronometro.setBase(SystemClock.elapsedRealtime() - Tiempo * 1000);
                }


            } catch (Exception e) {
                e.printStackTrace();
                //esto es cuando se recibe una carrera de la app, para verificar la distancia a la que
                //se enucuentra el cliente
                if (ActionServicioNuevo) {
                    try {
                        ServicioNuevo.putExtra(Constantes.TIEMPO, 0.5);
                        ServicioNuevo.putExtra(Constantes.DISTANCIA, 0.5);
                        ServicioNuevo.putExtra(Constantes.BD_POSICION_EN_LA_LISTA, posicion_en_la_lista);
                        ServicioNuevo.putExtra(Constantes.ID_LISTA, id_lista);
                        startActivity(ServicioNuevo);
                        ActionServicioNuevo = false;
                        posicion_en_la_lista = null;
                        id_lista = null;
                        try {
                            finishAffinity();
                        } catch (Exception lle) {
                            lle.printStackTrace();
                            finish();
                        }
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                } else if (ActionCalcularViaje) {
                    //esto es para calcular un viaje a realizar
                    Log.i("ACTIONCALCULARVIAJE", "OK");
                    ActionCalcularViaje = false;
                    toast("Ha ocurrido un error, intenta nuevamente");
                }
            }

            if (result != null) {
                Log.i("resultJson", result.toString());
            }

        }
    }

    // crear URl para la solicitud de distancia y tiempo
    private String obtenerdistanciaURL(LatLng origin, LatLng dest) {

        String str_origin = origin.latitude + "," + origin.longitude;

        String str_dest = dest.latitude + "," + dest.longitude;

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + str_origin + "&destinations="
                + str_dest + "&key=" +
                getResources().getString(R.string.google_maps_key_PROD);

        return url;
    }

    public void Distancia(LatLng posInicial, LatLng posFinal) {
        String urldistancia = obtenerdistanciaURL(posInicial, posFinal);
        DownloadTaskDistancia downloadTaskDistancia = new DownloadTaskDistancia();
        downloadTaskDistancia.execute(urldistancia);
    }

    public void Ruta(LatLng inicial, LatLng fin) {
        String url = obtenerDireccionesURL(inicial, fin);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    public void ServicioRecibido(String id_pedido) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_PEDIDO_ESPECIAL)
                .child(id_pedido);

        Query query = database.orderByKey();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pedidos pedido = dataSnapshot.getValue(Pedidos.class);
                ServicioNuevo = new Intent(MainActivity.this, ServicioNuevoActivity.class);
                try {
                    Double lat = 1.0, lng = 1.0;
                    lat = pedido.getLat_dir_inicial();
                    lng = pedido.getLong_dir_inicial();
                    LatLng pocisioncliente = new LatLng(lat, lng);
                    ServicioNuevo.putExtra(Constantes.BD_ID_PEDIDO, pedido.getId_pedido());
                    ServicioNuevo.putExtra(Constantes.BD_DIR_INICIAL, pedido.getDir_inicial());
                    ServicioNuevo.putExtra(Constantes.LAT_INI, lat);
                    ServicioNuevo.putExtra(Constantes.LNG_INI, lng);
                    Distancia(pocisionInicial, pocisioncliente);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public double modificadorDeTarifaSegunLaHora() {

        double modificador;
        Calendar c = Calendar.getInstance();
        int Hora = c.get(Calendar.HOUR_OF_DAY);
        if (Hora <= 5) {
            modificador = 1.15;
        } else if (Hora >= 20) {
            modificador = 1.3;
        } else {
            modificador = 1.1;
        }
        return modificador;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                ETAgregarDestino.setText(place.getName());
                pocisionFinal = place.getLatLng();
                ActionCalcularViaje = true;
                try {
                    Ruta(pocisionInicial, pocisionFinal);
                    Distancia(pocisionInicial, pocisionFinal);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    toast("No se pudo cargar la ruta");
                }
            } else if (requestCode == PERMISO_OVERLAYS) {
                if (!Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, PERMISO_OVERLAYS);
                } else {
                    Intent mantenerConectado = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                    mantenerConectado.putExtra(Constantes.BD_CODIGO_MENSAJERO, codigo);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Log.i("OncompleteListener()", "starforegroundservice");
                        startForegroundService(mantenerConectado);
                    } else {
                        Log.i("OncompleteListener()", "starservice");
                        startService(mantenerConectado);
                    }
                }
            }
        }
    }

    public static float getImageRotation(Uri path, Context context) {
        try {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};

            Cursor cursor = context.getContentResolver().query(path, projection, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            cursor.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0f;
        }

        return 0f;
    }

    private Task<String> ChatServicio(String mensaje, String token_usuario) {
        Map<String, Object> data = new HashMap<>();
        data.put("clave", Constantes.CONFIRMAR_LLEGADA);
        data.put("text", mensaje);
        data.put("token_usuario", token_usuario);
        data.put("tipo_servicio", Constantes.SERVICIO_MENSAJERO_GO);

        return funtions
                .getHttpsCallable("ChatServicio")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String result = (String) task.getResult().getData();
                        Log.i("ChatServicio", result);
                        return result;
                    }
                });
    }

    private Task<String> TerminarViaje(double tiempo, double distancia, String id_pedido,
                                       String codigo_mensajero, String id_usuario, int hora_del_dia) {
        Map<String, Object> data = new HashMap<>();

        token = mensajero.getToken();
        data.put("tiempo", tiempo);
        data.put("distancia", distancia);
        data.put("id_usuario", id_usuario);
        data.put("codigo_mensajero", codigo_mensajero);
        data.put("id_pedido", id_pedido);
        data.put("token", token);
        data.put("hora_del_dia", hora_del_dia);


        return funtions
                .getHttpsCallable("TerminarViaje")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String result = (String) task.getResult().getData();
                        // Log.i("ChatServicio", result);
                        return result;
                    }
                });
    }

    //metodo para crear canales de notificaciones
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CrearCanalesDeNOtificaciones(NotificationManager notificationManager) {


        try {
            Uri sonidoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tin);            //-------- canal para el servicio conectado --------------------
            NotificationChannel canalEstadoConectado =
                    new NotificationChannel(Constantes.ID_CANAL_CONECTADO, Constantes.NOMBRE_CANAL_CONECTADO
                            , NotificationManager.IMPORTANCE_DEFAULT);
            canalEstadoConectado.setLightColor(Color.MAGENTA);
            canalEstadoConectado.setSound(null, null);
            canalEstadoConectado.setBypassDnd(false);
            canalEstadoConectado.enableLights(true);
            canalEstadoConectado.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canalEstadoConectado);
            //------- canal creado ---------------

            //-------- canal para el servicio desconectar --------------------
            NotificationChannel canalDesconestar =
                    new NotificationChannel(Constantes.ID_CANAL_DESCONECTAR, Constantes.NOMBRE_CANAL_DESCONECTAR
                            , NotificationManager.IMPORTANCE_HIGH);
            canalDesconestar.setLightColor(Color.MAGENTA);
            canalDesconestar.setSound(sonidoUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            canalDesconestar.setBypassDnd(true);
            canalDesconestar.setImportance(NotificationManager.IMPORTANCE_HIGH);
            canalDesconestar.enableVibration(true);
            canalDesconestar.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canalDesconestar);
            //------- canal creado ---------------

            //-------- canal para el servicio carrera --------------------
            NotificationChannel canalServicioCarrera =
                    new NotificationChannel(Constantes.ID_CANAL_SERVICIO_CARRERA, Constantes.NOMBRE_CANAL_SERVICIO_CARRERA
                            , NotificationManager.IMPORTANCE_DEFAULT);
            canalServicioCarrera.setLightColor(Color.MAGENTA);
            canalServicioCarrera.setSound(null, null);
            canalServicioCarrera.setBypassDnd(false);
            canalServicioCarrera.enableVibration(false);
            canalServicioCarrera.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canalServicioCarrera);
            //------- canal creado ---------------
            //-------- canal para el servicio conectado --------------------
            NotificationChannel canalDefault =
                    new NotificationChannel(Constantes.ID_CANAL_DEFAULT, Constantes.NOMBRE_CANAL_DEFAULT
                            , NotificationManager.IMPORTANCE_DEFAULT);
            canalDefault.setLightColor(Color.MAGENTA);
            canalDefault.setSound(sonidoUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            canalDefault.setBypassDnd(false);
            canalDefault.enableLights(true);
            canalDefault.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canalDefault);
            //------- canal creado ---------------

            //-------- canal para el servicio desconectar --------------------
            NotificationChannel canalChat =
                    new NotificationChannel(Constantes.ID_CANAL_CHAT, Constantes.NOMBRE_CANAL_CHAT
                            , NotificationManager.IMPORTANCE_HIGH);
            canalChat.setLightColor(Color.MAGENTA);
            canalChat.setSound(sonidoUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            canalChat.setBypassDnd(true);
            canalChat.setImportance(NotificationManager.IMPORTANCE_HIGH);
            canalChat.enableVibration(true);
            canalChat.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canalChat);
            //------- canal creado ---------------
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void MostrarDetalles(String valor_viaje, String valor_cobrar, String descuentos_u, String saldos_u) {
        try {
            LayoutInflater inflater = this.getLayoutInflater();
            View v = inflater.inflate(R.layout.layout_valor_viaje, null);
            dialog_terminar_viaje.setContentView(v);

            Button volver = v.findViewById(R.id.boton_confirmar_term_viaje);
            TextView texto_valor_a_cobrar = v.findViewById(R.id.term_viaje_valor);
            TextView texto_valor_total = v.findViewById(R.id.term_viaje_total);
            TextView texto_saldo_u = v.findViewById(R.id.term_viaje_saldo_u);
            TextView texto_descuentos_u = v.findViewById(R.id.term_viaje_descuentos_u);

            texto_valor_a_cobrar.setText(valor_cobrar);
            texto_valor_total.setText("$ " + valor_viaje);
            texto_descuentos_u.setText("$ " + descuentos_u);
            texto_saldo_u.setText("$ " + saldos_u);

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_terminar_viaje.dismiss();
                    toast("servicio terminado con éxito");
                }
            });

            try {
                dialog_terminar_viaje.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void MostrarActualizar() {
        try {
            //creando el dialogo de actualizacion
            dialog_Actualizar = new Dialog(this, R.style.Theme_AppCompat_DialogWhenLarge);
            dialog_Actualizar.setCancelable(true);
            LayoutInflater inflater = this.getLayoutInflater();
            View v = inflater.inflate(R.layout.layout_actualizar, null);
            Button cerrar = v.findViewById(R.id.cerrar_actualizar);
            Button actualizar = v.findViewById(R.id.actualizar);
            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_Actualizar.dismiss();
                }
            });
            actualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPlayStore();
                    dialog_Actualizar.dismiss();
                }
            });
            dialog_Actualizar.setContentView(v);
            dialog_Actualizar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ReceptorMensajesServidor extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("RECEPTOR", "MENSAJE RECIBIDO");

            String action = "action";

            if (intent != null) {
                action = intent.getAction();
            }

            if (action != null) {
                switch (action) {
                    case Constantes.ACTION_CONFIRMAR_VALOR_VIAJE:
                        dialog_calculando.dismiss();
                        Bundle bundle = intent.getExtras();
                        String valor_cobrar = "0";
                        String valor_total = "0";
                        String descuentos_u = "0";
                        String saldo_u = "0";
                        if (bundle != null) {
                            valor_cobrar = bundle.getString("valor_cobrar");
                            valor_total = bundle.getString("valor_viaje");
                            descuentos_u = bundle.getString("descuentos_usuario");
                            saldo_u = bundle.getString("saldo_pendiente_usuario");
                        }
                        MostrarDetalles(valor_total, valor_cobrar, descuentos_u, saldo_u);
                        break;
                    case Constantes.ACTION_ACTUALIZAR:
                        Log.i("ACTION_ACTUALIZAR", "HAY UNA ACTUALÑIZACION DISPONIBLE");
                        MostrarActualizar();
                        break;
                    case Constantes.ACTION_MENSAJE_CHAT:
                        if (!fragmentChat.isAdded()) {
                            if (pedido_aceptado != null) {
                                fragmentChat = new FragmentChat();
                                Bundle bundle1 = new Bundle();
                                bundle1.putString(Constantes.BD_NOMBRE_USUARIO, mensajero.getNombre());
                                bundle1.putString(Constantes.BD_ID_PEDIDO, pedido_aceptado.getId_pedido());
                                fragmentChat.setArguments(bundle1);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.main_cont, fragmentChat, FragmentChat.TAG);
                                ft.commit();
                            }
                        }
                        break;
                    case Constantes.SERVICIO_NUEVO:
                        Log.i("ACTIONBroastas", "PEDIDO NUEVO");
                        String id_pedido = intent.getStringExtra(Constantes.BD_ID_PEDIDO);
                        posicion_en_la_lista = intent.getStringExtra(Constantes.BD_POSICION_EN_LA_LISTA);
                        id_lista = intent.getStringExtra(Constantes.ID_LISTA);
                        Log.i("broadcast Id_pedido", id_pedido);
                        layout_pendientes.setVisibility(View.INVISIBLE);
                        ActionServicioNuevo = true;
                        ServicioRecibido(id_pedido);
                        dir_viaje = false;
                        dir_recoger = false;
                        SharedPreferences.Editor editor1 = sharedPref.edit();
                        editor1.putBoolean(Constantes.BD_ESTADO_MENSAJERO, true);
                        editor1.apply();
                        break;
                }
            }

        }
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private Switch.OnCheckedChangeListener checkedChangeListener = new Switch.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {

            if (isChecked) {
                if (isOnline(MainActivity.this)) {
                    if (isMyServiceRunning(ServicioEstadoConectado.class)) {
                        Log.i("is checket", "el servicio ya estaba corriendo");
                        buttonView.setText(Constantes.ESTADO_CONECTADO);
                        pedidos_pendientes.clear();
                        if (id_pedido_aceptado != null ) {
                            layout_pendientes.setVisibility(View.INVISIBLE);
                            refPedidosPendientes.removeEventListener(listenerPedidosPendientes);
                        } else {
                            layout_pendientes.setVisibility(View.VISIBLE);
                            refPedidosPendientes.limitToLast(100).addChildEventListener(listenerPedidosPendientes);
                            texto_num_cliente.setText("Teléfono: ");
                            texto_dir_cliente.setText("Dirección: ");
                            texto_nom_cliente.setText("Nombre: ");
                            textoDistancia.setText("Distancia Recorrida: ");
                            textoValor.setText("Valor: ");
                        }
                    } else {
                        Log.i("is checket", "el servicio NO estaba corriendo");
                        Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animacion_switch);
                        buttonView.setAnimation(anim);
                        buttonView.setText("Conectando...");
                        buttonView.animate();

                        if (mensajero != null) {
                            //aqui vamos a poner a la escucha de alertas en el mapa
                            alertasQuery = database.child(Constantes.ALERTAS_MAPA);
                            alertasQuery.addChildEventListener(listenerAlertasMapa);
                            pedidos_pendientes.clear();
                            miUbicacion();
                            mensajero.setLat_dir_ini(lat);
                            mensajero.setLgn_dir_ini(lng);
                            //aqui se registran los datos del ususario en la base de datos
                            codigo = mensajero.getCodigo();
                            Log.i("switch conectar ", codigo + "");
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnSuccessListener(MainActivity.this,
                                            new OnSuccessListener<InstanceIdResult>() {
                                                @Override
                                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                                    token = instanceIdResult.getToken();
                                                    if (mensajero != null) {
                                                        mensajero.setToken(token);
                                                        if (pedido_aceptado != null) {
                                                            if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_CANCELADO)) {
                                                                mensajero.setOcupado(false);
                                                            } else if (pedido_aceptado.getEstado_pedido().equals(Constantes.ESTADO_TERMINADO)) {
                                                                mensajero.setOcupado(false);
                                                            } else {
                                                                mensajero.setOcupado(true);
                                                            }
                                                        }
                                                        database.child(Constantes.BD_MENSAJERO_ESPECIAL).child(codigo)
                                                                .child(Constantes.BD_TOKEN).setValue(token);
                                                        currentUserDB = database.child(Constantes.BD_MENSAJERO_ESPECIAL_CONECTADO).child(codigo);
                                                        keyconectado = currentUserDB.getKey();
                                                        SimpleDateFormat hora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                        Calendar calendar = Calendar.getInstance();
                                                        mensajero.setHora_conexion(hora.format(calendar.getTime()));
                                                        //guardamos los datos del mensajero en las preferencias
                                                        Gson gson = new Gson();
                                                        String json = gson.toJson(mensajero);//convertimos el objeto a json
                                                        SharedPreferences.Editor editor = sharedPref.edit();
                                                        editor.putString(Constantes.BD_MENSAJERO_ESPECIAL, json);
                                                        editor.apply();
                                                        Log.i("mensajeroconectado", json);
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                            if (!Settings.canDrawOverlays(MainActivity.this)) {
                                                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                                                        Uri.parse("package:" + getPackageName()));
                                                                startActivityForResult(intent, PERMISO_OVERLAYS);
                                                            } else {

                                                                currentUserDB.setValue(mensajero, new DatabaseReference.CompletionListener() {
                                                                    @Override
                                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                                                        if (databaseError == null) {
                                                                            buttonView.setText(Constantes.ESTADO_CONECTADO);
                                                                            buttonView.clearAnimation();
                                                                            if (id_pedido_aceptado != null ) {
                                                                                layout_pendientes.setVisibility(View.INVISIBLE);
                                                                                refPedidosPendientes.removeEventListener(listenerPedidosPendientes);
                                                                            } else {
                                                                                layout_pendientes.setVisibility(View.VISIBLE);
                                                                                refPedidosPendientes.limitToLast(100).addChildEventListener(listenerPedidosPendientes);
                                                                            }
                                                                            Log.i("OncompleteListener()", "el servicio no estaba corriendo");
                                                                            Intent mantenerConectado = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                                                                            mantenerConectado.putExtra(Constantes.BD_CODIGO_MENSAJERO, codigo);

                                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                                Log.i("OncompleteListener()", "starforegroundservice");
                                                                                startForegroundService(mantenerConectado);
                                                                            } else {
                                                                                Log.i("OncompleteListener()", "starservice");
                                                                                startService(mantenerConectado);
                                                                            }

                                                                            // frame_carrera_activa.setVisibility(View.VISIBLE);
                                                                            contador = 0;
                                                                        } else {
                                                                            buttonView.setChecked(false);
                                                                            layout_pendientes.setVisibility(View.INVISIBLE);
                                                                            Log.i("error databaseconectado 3503", databaseError.getDetails());
                                                                            if (isMyServiceRunning(ServicioEstadoConectado.class)) {
                                                                                Log.i("OncompleteListener()", "el servicio ya estaba corriendo pero no se pudo volver a escribir " +
                                                                                        "en la base de datos, quiza sea porque no hay internet");
                                                                                Intent IntentCarreraNueva = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                                                                                stopService(IntentCarreraNueva);
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        } else {
                                                            currentUserDB.setValue(mensajero, new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                                                    if (databaseError == null) {
                                                                        buttonView.setText(Constantes.ESTADO_CONECTADO);
                                                                        buttonView.clearAnimation();
                                                                        if (id_pedido_aceptado != null ) {
                                                                            layout_pendientes.setVisibility(View.INVISIBLE);
                                                                            refPedidosPendientes.removeEventListener(listenerPedidosPendientes);
                                                                        } else {
                                                                            refPedidosPendientes.limitToLast(100).addChildEventListener(listenerPedidosPendientes);
                                                                            layout_pendientes.setVisibility(View.VISIBLE);
                                                                        }
                                                                        Log.i("OncompleteListener()", "el servicio no estaba corriendo");
                                                                        Intent mantenerConectado = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                                                                        mantenerConectado.putExtra(Constantes.BD_CODIGO_MENSAJERO, codigo);
                                                                        Log.i("OncompleteListener()", "starservice");
                                                                        startService(mantenerConectado);
                                                                        contador = 0;
                                                                    } else {
                                                                        buttonView.setChecked(false);
                                                                        layout_pendientes.setVisibility(View.INVISIBLE);
                                                                        Log.i("error databaseconectado 3530", databaseError.getMessage());
                                                                        if (isMyServiceRunning(ServicioEstadoConectado.class)) {
                                                                            Log.i("OncompleteListener()", "el servicio ya estaba corriendo pero no se pudo volver a escribir " +
                                                                                    "en la base de datos, quiza sea porque no hay internet");
                                                                            Intent IntentCarreraNueva = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                                                                            stopService(IntentCarreraNueva);
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }

                                            });

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Por favor vuelve a iniciar sesión")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    }).setCancelable(false).show();
                        }
                    }

                } else {
                    toast("Verifique su conexión a internet");
                    setConectado(false);
                    buttonView.setChecked(false);
                    buttonView.setText(Constantes.ESTADO_DESCONECTADO);
                    buttonView.clearAnimation();
                    layout_pendientes.setVisibility(View.INVISIBLE);
                }
            } else {
                if (map != null) {
                    map.clear();
                }
                setConectado(false);
                buttonView.setChecked(false);
                buttonView.setText(Constantes.ESTADO_DESCONECTADO);
                buttonView.clearAnimation();
                ETAgregarDestino.setVisibility(View.INVISIBLE);
                layout_pendientes.setVisibility(View.INVISIBLE);
                //aqui se registran los datos del ususario en la base de datos
                if (codigo != null) {
                    currentUserDB = database.child(Constantes.BD_MENSAJERO_ESPECIAL_CONECTADO)
                            .child(codigo);
                    Log.i("SET CONECTADO", "quitar de la lista");
                    currentUserDB.removeValue();
                    refPedidosPendientes.removeEventListener(listenerPedidosPendientes);
                }
                Intent IntentCarreraNueva = new Intent(MainActivity.this, ServicioEstadoConectado.class);
                stopService(IntentCarreraNueva);
                frame_carrera_activa.setVisibility(View.INVISIBLE);

            }


        }
    };

    String SugerenciadescripcionAlerta = "Agrega una descripción";
    String tipo_alerta;

    public void InsertarAlertaServidor(final LatLng latLng, MainActivity mainActivity) {

        //creando el dialogo confirmacion
        final Dialog dialog = new Dialog(mainActivity, R.style.Theme_AppCompat_DialogWhenLarge);
        dialog.setCancelable(true);
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_agregar_alerta, null);
        dialog.setContentView(v);

        final RadioButton RBpolicia = v.findViewById(R.id.rBpolicia);
        final RadioButton RBayuda = v.findViewById(R.id.rBayuda);
        final RadioButton RBaccidente = v.findViewById(R.id.rBaccidente);
        final RadioButton RBcallecerrada = v.findViewById(R.id.rBcallecerrada);
        final RadioButton RBmultitud = v.findViewById(R.id.rBmultitud);
        final TextView descripcion = v.findViewById(R.id.text_descripcion_alerta);
        final EditText ETdescp = v.findViewById(R.id.ETdescripcionAlerta);
        final Button agregar = v.findViewById(R.id.button_agregar_alerta);
        final TextInputLayout inputnombreusuario = v.findViewById(R.id.textInputDetallenombreUsuario);
        final TextInputEditText textonombreusuario = v.findViewById(R.id.ETnombreusuarioagregaralerta);

        RadioButton.OnCheckedChangeListener checkedChangeListenerRB = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.rBpolicia:
                        if (buttonView.isChecked()) {
                            RBayuda.setChecked(false);
                            RBaccidente.setChecked(false);
                            RBcallecerrada.setChecked(false);
                            RBmultitud.setChecked(false);
                            SugerenciadescripcionAlerta = "*Puedes agregar el sentido Norte/Sur Sur/Norte u otra información de valor";
                            descripcion.setText(SugerenciadescripcionAlerta);
                            tipo_alerta = Constantes.ALERTA_720;
                            inputnombreusuario.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case R.id.rBaccidente:
                        if (buttonView.isChecked()) {
                            RBpolicia.setChecked(false);
                            RBayuda.setChecked(false);
                            RBcallecerrada.setChecked(false);
                            RBmultitud.setChecked(false);
                            SugerenciadescripcionAlerta = "*Puedes informar si hay via, si está totalmente cerrado el paso u otra información de valor";
                            descripcion.setText(SugerenciadescripcionAlerta);
                            tipo_alerta = Constantes.ALERTA_ACCIDENTE;
                            inputnombreusuario.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case R.id.rBcallecerrada:
                        if (buttonView.isChecked()) {
                            RBpolicia.setChecked(false);
                            RBayuda.setChecked(false);
                            RBaccidente.setChecked(false);
                            RBmultitud.setChecked(false);
                            SugerenciadescripcionAlerta = "*Puedes agregar el motivo del cierre si lo conoces";
                            descripcion.setText(SugerenciadescripcionAlerta);
                            tipo_alerta = Constantes.ALERTA_CALLE_CERRADA;
                            inputnombreusuario.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case R.id.rBmultitud:
                        if (buttonView.isChecked()) {
                            RBpolicia.setChecked(false);
                            RBayuda.setChecked(false);
                            RBaccidente.setChecked(false);
                            RBcallecerrada.setChecked(false);
                            SugerenciadescripcionAlerta = "*Puedes agregar información adicional, una dirección exacta, o cualquier otra que ayude al mensajero a contactar al usuario";
                            descripcion.setText(SugerenciadescripcionAlerta);
                            tipo_alerta = Constantes.ALERTA_SERVICIOS;
                            inputnombreusuario.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.rBayuda:
                        if (buttonView.isChecked()) {
                            RBpolicia.setChecked(false);
                            RBaccidente.setChecked(false);
                            RBcallecerrada.setChecked(false);
                            RBmultitud.setChecked(false);
                            SugerenciadescripcionAlerta = "*Puedes agregar una pequeña descripción de lo que necesitas, ejemplo: corriente, gasolina, un empujón, etc..";
                            descripcion.setText(SugerenciadescripcionAlerta);
                            tipo_alerta = Constantes.ALERTA_VARADA;
                            inputnombreusuario.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }
        };


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                AlertasMapa alertasMapa = new AlertasMapa(latLng.latitude, latLng.longitude,
                        Constantes.ALERTA_720, calendar.getTimeInMillis(), mensajero.getNombre());
                alertasMapa.setTIME_ZONE(calendar.getTimeZone().getID());
                Log.i("time zone", alertasMapa.getTIME_ZONE());
                if (ETdescp.getText() != null) {
                    String desp = ETdescp.getText().toString();
                    alertasMapa.setDescripcion(desp);
                } else {
                    String desp = "No se agregó una descripción";
                    alertasMapa.setDescripcion(desp);
                }
                if (tipo_alerta.equals(Constantes.ALERTA_SERVICIOS)) {
                    if (textonombreusuario.getText() != null && isNumeroValido(textonombreusuario.getText().toString())) {
                        alertasMapa.setNumero_usuario(textonombreusuario.getText().toString());
                        alertasMapa.setTipo_alerta(tipo_alerta);
                        DatabaseReference dataAlerta = database.child(Constantes.ALERTAS_MAPA).push();
                        String id_alerta = dataAlerta.getKey();
                        alertasMapa.setId_alerta(id_alerta);
                        dataAlerta.setValue(alertasMapa).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    toast("Alerta agregada");
                                    dialog.dismiss();
                                } else {
                                    toast("Upps! por favor intenta más tarde");
                                }
                            }
                        });
                    } else {
                        toast("debes agregar un número válido para que puedan contactar al usuario");
                    }
                } else {
                    alertasMapa.setTipo_alerta(tipo_alerta);
                    DatabaseReference dataAlerta = database.child(Constantes.ALERTAS_MAPA).push();
                    String id_alerta = dataAlerta.getKey();
                    alertasMapa.setId_alerta(id_alerta);
                    dataAlerta.setValue(alertasMapa).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                toast("Alerta agregada");
                                dialog.dismiss();
                            } else {
                                toast("Upps! por favor intenta más tarde");
                            }
                        }
                    });

                }

            }
        });


        RBaccidente.setOnCheckedChangeListener(checkedChangeListenerRB);
        RBayuda.setOnCheckedChangeListener(checkedChangeListenerRB);
        RBcallecerrada.setOnCheckedChangeListener(checkedChangeListenerRB);
        RBmultitud.setOnCheckedChangeListener(checkedChangeListenerRB);
        RBpolicia.setOnCheckedChangeListener(checkedChangeListenerRB);
        dialog.show();


    }

    private boolean isNumeroValido(String numero) {
        return numero.length() == 10;
    }

    public void VerDetalleAlerta(final Marker marker, MainActivity mainActivity) {
        //creando el dialogo confirmacion
        View v = findViewById(R.id.frame_detalles);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -v.getHeight(), 0f));
        animator.setDuration(250);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                frame_detalles_alertas.setVisibility(View.VISIBLE);
                ETAgregarDestino.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                super.onAnimationEnd(animation);
            }
        });
        animator.start();
        if (animator.isStarted()) {
            frame_detalles_alertas.setVisibility(View.VISIBLE);
            ETAgregarDestino.setVisibility(View.INVISIBLE);
        }
        final TextView texthoradeinicio = v.findViewById(R.id.text_detalle_hora_de_inicio);
        final TextView textdescripcion = v.findViewById(R.id.text_detalle_descripcion_alerta);
        final TextView texttipoalerta = v.findViewById(R.id.text_detalle_tipo_alerta);
        final TextView textduracion = v.findViewById(R.id.text_detalle_tiempo_de_vida);
        final TextView textonombre = v.findViewById(R.id.text_detalle_creado_por);
        final ImageView imageView = v.findViewById(R.id.imagen_alerta);
        final TextView texto_numero = v.findViewById(R.id.textdetallenumerousuario);
        final TextView texto_mensajero = v.findViewById(R.id.text_nombre_mensajero_servicio);
        final Button botonTomarServicio = v.findViewById(R.id.boton_detalle_tomar_servicio);
        final ConstraintLayout layout = v.findViewById(R.id.layout_servicio_alerta);
        final ConstraintLayout layoutParent = v.findViewById(R.id.constraintveralerta);
        final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout
                .LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, 0);
        final FrameLayout.LayoutParams paramsFrame = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        final ConstraintSet constraintSet = new ConstraintSet();
        final ConstraintLayout.LayoutParams paramsVer = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        constraintSet.connect(R.id.layout_servicio_alerta, ConstraintSet.TOP, textdescripcion.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.layout_servicio_alerta, ConstraintSet.START, R.id.constraintveralerta, ConstraintSet.START, 0);
        constraintSet.connect(R.id.layout_servicio_alerta, ConstraintSet.BOTTOM, R.id.constraintveralerta, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(R.id.layout_servicio_alerta, ConstraintSet.END, R.id.constraintveralerta, ConstraintSet.END, 0);
        constraintSet.constrainHeight(R.id.layout_servicio_alerta, ConstraintSet.WRAP_CONTENT);

        final AlertasMapa alertasMapa = (AlertasMapa) marker.getTag();
        Calendar calendar = Calendar.getInstance();
        Calendar calendarAhora = Calendar.getInstance();
        long ahora = calendarAhora.getTimeInMillis();
        calendar.setTimeInMillis(alertasMapa.getHora_de_inicio());
        long tiempo_duracion = ahora - calendar.getTimeInMillis();
        SimpleDateFormat horam = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String hora = horam.format(calendar.getTime());
        String descrip = alertasMapa.getDescripcion();
        if (descrip == null) {
            descrip = "No se adjuntó una descripción";
        }
        String tipo_alerta = alertasMapa.getTipo_alerta();
        long duracion = alertasMapa.getTiempo_de_vida();
        final long dif = duracion - tiempo_duracion;
        String durac = " Termina en " + dif / 1000 / 60 + " minutos";

        textonombre.setText(alertasMapa.getNombre_creador());
        textduracion.setText(durac);
        textdescripcion.setText(descrip);
        texthoradeinicio.setText(hora);
        texttipoalerta.setText(tipo_alerta);
        switch (tipo_alerta) {
            case Constantes.ALERTA_720:
                imageView.setImageResource(R.mipmap.cono);
                layout.setVisibility(View.INVISIBLE);
                layout.setLayoutParams(layoutParams);
                break;
            case Constantes.ALERTA_SERVICIOS:
                imageView.setImageResource(R.mipmap.multitud);
                layout.setVisibility(View.VISIBLE);
                layout.setLayoutParams(paramsVer);
                layoutParent.setLayoutParams(paramsFrame);
                layoutParent.setConstraintSet(constraintSet);
                constraintSet.applyTo(layoutParent);

                texto_numero.setText(alertasMapa.getNumero_usuario());
                if (alertasMapa.getNombre_mensajero_que_toma_servicio() != null) {
                    if (!alertasMapa.getCodigo_mensajero_servicio().equals(codigo)) {
                        texto_numero.setClickable(false);
                    }
                    botonTomarServicio.setVisibility(View.INVISIBLE);
                    texto_mensajero.setText(alertasMapa.getNombre_mensajero_que_toma_servicio());
                } else {
                    texto_numero.setClickable(true);
                    botonTomarServicio.setVisibility(View.VISIBLE);
                    texto_mensajero.setText("disponible");
                    botonTomarServicio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertasMapa.setCodigo_mensajero_servicio(codigo);
                            alertasMapa.setNombre_mensajero_que_toma_servicio(mensajero.getNombre());
                            alertasMapa.setTiempo_de_vida(dif + 900000);
                            database.child(Constantes.ALERTAS_MAPA).child(alertasMapa.getId_alerta())
                                    .setValue(alertasMapa).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        toast("Servicio tomado correctamente");
                                        View v = findViewById(R.id.frame_detalles);
                                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,
                                                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 1f, -v.getHeight()));
                                        animator.setDuration(300);
                                        animator.addListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation, boolean isReverse) {
                                                frame_detalles_alertas.setVisibility(View.INVISIBLE);
                                                ETAgregarDestino.setVisibility(View.VISIBLE);
                                                layout_pendientes.setVisibility(View.INVISIBLE);
                                                animation.removeAllListeners();
                                            }
                                        });
                                        animator.start();
                                        Ruta(pocisionInicial, new LatLng(alertasMapa.getLat(), alertasMapa.getLng()));
                                        miUbicacion();
                                    } else {
                                        toast("Intenta nuevamente");
                                    }
                                }
                            });
                        }
                    });

                    texto_numero.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent llamar = new Intent(Intent.ACTION_DIAL);
                            llamar.setData(Uri.parse("tel:" + alertasMapa.getNumero_usuario()));
                            startActivity(llamar);
                        }
                    });
                }
                break;
            case Constantes.ALERTA_VARADA:
                imageView.setImageResource(R.mipmap.varada);
                layout.setVisibility(View.INVISIBLE);
                layout.setLayoutParams(layoutParams);
                break;
            case Constantes.ALERTA_CALLE_CERRADA:
                imageView.setImageResource(R.mipmap.calle_cerrada);
                layout.setVisibility(View.INVISIBLE);
                layout.setLayoutParams(layoutParams);
                break;
            case Constantes.ALERTA_ACCIDENTE:
                imageView.setImageResource(R.mipmap.accidente);
                layout.setVisibility(View.INVISIBLE);
                layout.setLayoutParams(layoutParams);
                break;

        }

    }

    private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}





























