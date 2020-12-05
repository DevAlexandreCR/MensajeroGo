package mensajero.mensajerogo.Constantes;

import android.util.Log;

import mensajero.mensajerogo.Servicios.OnViajeChangeListener;

public class BooleanViaje  implements OnViajeChangeListener{
    public BooleanViaje() {
    }

    @Override
    public void OnViajeChangeState(Boolean viaje) {
        if(viaje){
            Log.i("OnViajeChangeState", "viaje= " +viaje);
        }else{
            Log.i("OnViajeChangeState", "viaje= " +viaje);
        }
    }
}
