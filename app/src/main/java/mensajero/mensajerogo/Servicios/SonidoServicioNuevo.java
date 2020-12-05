package mensajero.mensajerogo.Servicios;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import mensajero.mensajerogo.Constantes.Constantes;
import mensajero.mensajerogo.R;

public class SonidoServicioNuevo extends Service {

    private MediaPlayer sonido;

    public SonidoServicioNuevo() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Sonido","servicio creado");

        //reprodicir sonido
        sonido = MediaPlayer.create(this, R.raw.servicio_nuevo);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificar();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction() == null){
            try {
                sonido.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        sonido.setLooping(true);
                        sonido.start();
                        Log.i("Sonido","sonido iniciado");
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        } else if (intent.getAction().equals(Constantes.ACTION_SERVICIO_PENDIENTE)) {
            try {
                sonido.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        sonido.setLooping(false);
                        sonido.start();
                        Log.i("Sonido","sonido iniciado");
                    }
                });
                sonido.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        stopSelf();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sonido.isPlaying()){
            sonido.stop();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void notificar() {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Notification.Builder notificacion = new Notification.Builder(this, Constantes.ID_CANAL_DESCONECTAR)
                    .setContentText("Servicio entrante")
                    .setSmallIcon(R.drawable.logo_carro)
                    .setAutoCancel(true);

            try {
                startForeground(Constantes.NOTIFICATION_ID.FOREGROUND_SERVICE, notificacion.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
