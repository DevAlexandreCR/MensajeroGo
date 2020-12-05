package mensajero.mensajerogo.Servicios;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import mensajero.mensajerogo.Constantes.AlertasMapa;
import mensajero.mensajerogo.Constantes.Constantes;
import mensajero.mensajerogo.Constantes.Pedidos;
import mensajero.mensajerogo.MainActivity;
import mensajero.mensajerogo.R;

public class ServicioEstadoConectado extends Service {

    public static final String TAG = "SERVICIOESTADOCONECTADO";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    public MainActivity mainActivity;
    private DatabaseReference database;
    private DatabaseReference currenUserBD, refPedidosPendientes;
    public CountDownTimer countDownTimer;
    private WindowManager windowManager;
    private ImageView imagenConectado;
    WindowManager.LayoutParams params;
    private Context context = this;
    private Query queryAlerta;
    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getValue(AlertasMapa.class) != null) {
                AlertasMapa alertasMapa = dataSnapshot.getValue(AlertasMapa.class);
                if (alertasMapa.getTipo_alerta().equals(Constantes.ALERTA_SERVICIOS)){
                    try {
                        ActivityManager activityManager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
                            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                    Log.i("Foreground App", appProcess.processName);
                                    if (appProcess.processName.equals(getApplication().getPackageName())) {
                                        Log.i(TAG, "10 segundos APP EN PRIMER PLANO");
                                    }
                                } else {
                                    Log.i(TAG, appProcess.processName);
                                    Log.i(TAG, "APP EN SEGUNDO PLANO");
                                    Intent intentAlertaMapa = new Intent(ServicioEstadoConectado.this, MainActivity.class);
                                    intentAlertaMapa.setAction(Constantes.ALERTAS_MAPA);
                                    intentAlertaMapa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentAlertaMapa);
                                }
                            }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    private ChildEventListener listenerPedidosPendientes = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Pedidos pedido = dataSnapshot.getValue(Pedidos.class);
            if (pedido != null && pedido.getEstado_pedido() != null ) {
                if(pedido.getEstado_pedido().equals(Constantes.ESTADO_SIN_MOVIL_ASIGNADO) && pedido.getLiberado()){
                    Log.i("chiladd serviucio", "liberado " + pedido.getLiberado());
                }
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Pedidos pedido = dataSnapshot.getValue(Pedidos.class);
            Log.i("change servicio", pedido.getEstado_pedido() + pedido.getLiberado());
            if (pedido != null && pedido.getEstado_pedido() != null) {
                if(pedido.getEstado_pedido().equals(Constantes.ESTADO_SIN_MOVIL_ASIGNADO) && pedido.getLiberado()){
                    Log.i("change servicio", "hacer sonar");
                    Intent intent = new Intent(context, SonidoServicioNuevo.class);
                    intent.setAction(Constantes.ACTION_SERVICIO_PENDIENTE);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                }
            }
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


    public ServicioEstadoConectado() {
    }

    public SharedPreferences sharedPref;
    Double latitud, longitud;
    private Boolean lanzar_servicio_desconectar = false;
    private long TIEMPO_DE_ESPERA_NOTIFICACION_ESTADO_CONECTADO = 28800000; //7200000

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance().getReference()
                .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN);
        mainActivity = new MainActivity();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        queryAlerta = database.child(Constantes.ALERTAS_MAPA);
        refPedidosPendientes = database.child(Constantes.BD_PEDIDO_ESPECIAL);
        //Instanciamos el objeto que queremos adjuntar a WindowsManager
        imagenConectado = new ImageView(this);
        imagenConectado.setImageResource(R.mipmap.boton_conectado);
        imagenConectado.setBackground(getResources().getDrawable(R.drawable.boton_conectado_draw));

        //Aplicamos parametros que necesitemos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             params = new WindowManager.LayoutParams(
                     WindowManager.LayoutParams.WRAP_CONTENT,
                     WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    PixelFormat.TRANSLUCENT);
        }else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        //Posicion donde queremos que se acople
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
        params.x = 0;
        params.y = 0;

        imagenConectado.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if(initialX == params.x && initialY == params.y){
                            v.performClick();
                        }
                            return true;


                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(imagenConectado, params);
                        return true;
                }
                return true;
            }
        });
        imagenConectado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityManager activityManager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        Log.i("Foreground App", appProcess.processName);
                        if (appProcess.processName.equals(getApplication().getPackageName())) {
                            Log.i(TAG, " APP EN PRIMER PLANO");
                            break;
                        }
                    } else {
                        Log.i(TAG, appProcess.processName);
                        Log.i(TAG, "APP EN SEGUNDO PLANO");
                        Intent intent = new Intent(ServicioEstadoConectado.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constantes.ACTION_PEDIDO_ACEPTADO);
        intentFilter.addAction(Constantes.ACTION_TERMINAR_CARRERA);
        registerReceiver(receiver, intentFilter);

        locationRequest = new LocationRequest()
                .setInterval(10000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(5000);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());


        Log.i(TAG, "oncreateservicioconectado");
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        countDownTimer = new CountDownTimer(TIEMPO_DE_ESPERA_NOTIFICACION_ESTADO_CONECTADO, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long segundos = millisUntilFinished / 1000;
                try {
                    ActivityManager activityManager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
                    if (segundos % 1800 == 0) {
                        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                Log.i("Foreground App", appProcess.processName);
                                if (appProcess.processName.equals(getApplication().getPackageName())) {
                                    Log.i(TAG, "10 segundos APP EN PRIMER PLANO");
                                    this.cancel();
                                    this.start();
                                    break;
                                }
                            } else {
                                Log.i(TAG, appProcess.processName);
                                Log.i(TAG, "APP EN SEGUNDO PLANO");
                                //this.start();
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                Log.i(TAG, "on finish quitar de la lista");
                currenUserBD.removeValue();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, false);
                editor.apply();
                lanzar_servicio_desconectar = true;
                stopSelf();
            }
        };
        MostratNotificacionEstado();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String codigo = intent.getStringExtra(Constantes.BD_CODIGO_MENSAJERO);
            Log.i("OnStrartcomand", "codigo= " + codigo);
            currenUserBD = database.child(Constantes.BD_MENSAJERO_ESPECIAL_CONECTADO)
                    .child(codigo);
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult.getLastLocation() != null) {
                        latitud = locationResult.getLastLocation().getLatitude();
                        longitud = locationResult.getLastLocation().getLongitude();
                        try {
                            SimpleDateFormat fecha_pedido = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            Calendar calendar = Calendar.getInstance();
                            currenUserBD.child(Constantes.LAT_DIR_INICIAL).setValue(latitud);
                            currenUserBD.child(Constantes.LGN_DIR_INICIAL).setValue(longitud);
                            currenUserBD.child(Constantes.LAST_LOCATION_HORA).setValue(fecha_pedido.format(calendar.getTime()));
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //Acoplamos el elemento a la pantalla
            windowManager.addView(imagenConectado, params);
            countDownTimer.start();
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            queryAlerta.addChildEventListener(childEventListener);
            refPedidosPendientes.limitToLast(100).addChildEventListener(listenerPedidosPendientes);

        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (imagenConectado != null)
            try {
                windowManager.removeView(imagenConectado);
            }catch (Exception e) {
                e.printStackTrace();
            }

        Log.i(TAG, "SERVICIO DESTRUIDO");
        countDownTimer.cancel();
        Log.i(TAG, "on destroy quitar de la lista");
        if (currenUserBD != null) {
            currenUserBD.removeValue();
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constantes.BD_ESTADO_MENSAJERO, false);
        editor.apply();

        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }

        queryAlerta.removeEventListener(childEventListener);
        refPedidosPendientes.removeEventListener(listenerPedidosPendientes);


        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lanzar_servicio_desconectar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, ServicioDesconectar.class));
            } else {
                startService(new Intent(this, ServicioDesconectar.class));
            }
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    private final IBinder mBinder = new binderServicioEstado();

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }


    public class binderServicioEstado extends Binder {
        public ServicioEstadoConectado getService() {
            return ServicioEstadoConectado.this;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "on low memory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, "on trim memory");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "on task removed");
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    private void MostratNotificacionEstado() {

        Intent intent = new Intent(ServicioEstadoConectado.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Constantes.ACTION_CONECTAR_DESDE_NOTIFICACION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Notification.Builder notificacion = new Notification.Builder(this, Constantes.ID_CANAL_CONECTADO)
                    .setContentText("Estas conectado al servidor")
                    .setContentTitle(Constantes.ESTADO_CONECTADO)
                    .setSmallIcon(R.drawable.logo_carro)
                    .setAutoCancel(false)
                    .setUsesChronometer(true)
                    .setContentIntent(pendingIntent);

            try {
                startForeground(Constantes.NOTIFICATION_ID.FOREGROUND_SERVICE_ESTADO, notificacion.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                    .setContentText("Estas conectado al servidor")
                    .setContentTitle(Constantes.ESTADO_CONECTADO)
                    .setAutoCancel(false)
                    .setSmallIcon(R.drawable.logo_carro)
                    .setUsesChronometer(true)
                    .setContentIntent(pendingIntent);
            try {
                startForeground(Constantes.NOTIFICATION_ID.FOREGROUND_SERVICE_ESTADO, notificacion.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("OnReceiver", "mensaje recibido para detener coundwountimer");
            String Action = intent.getAction();
            if (Action.equals(Constantes.ACTION_PEDIDO_ACEPTADO)) {
                countDownTimer.cancel();
            } else if (Action.equals(Constantes.ACTION_TERMINAR_CARRERA)) {
                countDownTimer.start();
            }
        }
    };


}
