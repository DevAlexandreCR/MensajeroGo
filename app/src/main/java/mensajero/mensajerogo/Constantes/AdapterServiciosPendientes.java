package mensajero.mensajerogo.Constantes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import mensajero.mensajerogo.MainActivity;
import mensajero.mensajerogo.R;

public class AdapterServiciosPendientes  extends RecyclerView.Adapter<AdapterServiciosPendientes.PedidosViewHolder>{

    List<Pedidos> pedidos;
    Context context;
    SharedPreferences preferences;

    public AdapterServiciosPendientes(){
    }

    public AdapterServiciosPendientes(List<Pedidos> pedidos, Context context) {

        this.pedidos = pedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_servicios_pendientes,parent,false);
        PedidosViewHolder holder = new PedidosViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Pedidos pedido = pedidos.get(position);

        holder.texto_fecha.setText(getTimeOffSetServicio(pedido.getDate(), pedido.getFecha_pedido()));

        holder.texto_direccion.setText(pedido.getDir_inicial());
        holder.texto_nombre.setText(pedido.getNombre());


        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true)
                            .setTitle("Tomar Servicio")
                            .setMessage("¿ Desea tomar éste servicio ?")
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Mensajeros mensajero;
                                    Gson gson = new Gson(); //Instancia Gson.
                                    String json = preferences.getString(Constantes.BD_MENSAJERO_ESPECIAL, null);
                                    mensajero = gson.fromJson(json, Mensajeros.class);
                                    final String codigo = mensajero.getCodigo();
                                    final String token_conductor = mensajero.getToken();


                                    final DatabaseReference databaseasignarmensajero = FirebaseDatabase.getInstance().getReference()
                                            .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_PEDIDO_ESPECIAL)
                                            .child(pedido.getId_pedido());
                                    databaseasignarmensajero.child(Constantes.BD_ESTADO_PEDIDO).setValue(Constantes.ESTADO_EN_CURSO)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    databaseasignarmensajero.child(Constantes.BD_CODIGO_MENSAJERO).setValue(codigo);
                                                    databaseasignarmensajero.child(Constantes.TOKEN_CONDUCTOR).setValue(token_conductor);
                                                    Intent pedidoAceptado = new Intent(context, MainActivity.class);
                                                    pedidoAceptado.setAction(Constantes.ACTION_PEDIDO_ACEPTADO);
                                                    pedidoAceptado.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    pedidoAceptado.putExtra(Constantes.BD_ID_PEDIDO,pedido.getId_pedido());
                                                    context.startActivity(pedidoAceptado);
                                                }
                                            });

                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }catch (Exception e){

                }
            }
        });
    }

    public String getTimeOffSetServicio(@Nullable long time, String fecha){

        if (time <=0) return fecha;

        long ahora = new Date().getTime();
        String tiempo = "hace ";
        long offset = ahora - time;



        if(offset < 3600000) {
            long t = (offset/1000)/60;
            tiempo = "hace " + t + "min";
        } else if(offset < 86400000) {
            long t = (offset/1000)/60/60;
            tiempo = "hace " + t + "horas";
        } else {
            tiempo = fecha;
        }

        return tiempo;
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class PedidosViewHolder extends RecyclerView.ViewHolder {

        TextView texto_fecha,texto_direccion, texto_distancia, texto_nombre;
        Button aceptar;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            texto_direccion = itemView.findViewById(R.id.text_direccion_pendiente);
            texto_distancia = itemView.findViewById(R.id.text_distancia_pendiente);
            texto_fecha = itemView.findViewById(R.id.text_fecha_pendiente);
            aceptar = itemView.findViewById(R.id.button_aceptar_pendiente);
            texto_nombre = itemView.findViewById(R.id.text_nombre_pendiente);
        }
    }
}
