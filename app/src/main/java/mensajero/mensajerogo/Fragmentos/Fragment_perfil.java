package mensajero.mensajerogo.Fragmentos;


import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import mensajero.mensajerogo.Constantes.Constantes;
import mensajero.mensajerogo.Constantes.GlideApp;
import mensajero.mensajerogo.Constantes.Mensajeros;
import mensajero.mensajerogo.Constantes.Vehiculo;
import mensajero.mensajerogo.LoginActivity;
import mensajero.mensajerogo.MainActivity;
import mensajero.mensajerogo.R;
import mensajero.mensajerogo.Servicios.ServicioEstadoConectado;

public class Fragment_perfil extends Fragment implements View.OnClickListener{
    public static final String TAG = "Mi Perfil";
    public SharedPreferences preferences;
    private TextInputEditText ET_nombre,ET_numero,ET_email, ET_carro;
    private Button cerrar_sesion,cambiar_numero;
    private String nombre, email, telefono,codigo,nuevo_telefono, carro;
    private TextView cambiar_carro;
    private ImageView image_perfil;
    private EditText ETnumero_nuevo;
    private Dialog dialog_cambiar_numero;
    private DatabaseReference database;
    private boolean numero_exixte;
    private ProgressBar progressBar;
    private RatingBar ratingBarCal;


    public Fragment_perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        //iniciar vistas
        progressBar = v.findViewById(R.id.progressBarPerfil);
        cambiar_numero = v.findViewById(R.id.boton_cambiar);
        cambiar_numero.setOnClickListener(this);
        cambiar_carro = v.findViewById(R.id.text_cambiar_vehículo);
        cambiar_carro.setOnClickListener(this);
        ratingBarCal = v.findViewById(R.id.ratingCal_perfil);
        ET_email = v.findViewById(R.id.perfil_email);
        ET_email.setFocusable(false);
        ET_nombre = v.findViewById(R.id.perfil_nombre);
        ET_nombre.setFocusable(false);
        ET_numero = v.findViewById(R.id.perfil_telefono);
        ET_numero.setFocusable(false);
        ET_carro = v.findViewById(R.id.perfil_carro);
        ET_carro.setFocusable(false);
        image_perfil = v.findViewById(R.id.imagen_perfil);
        cerrar_sesion = v.findViewById(R.id.boton_cerrar_sesion);
        cerrar_sesion.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance().getReference()
                .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_MENSAJERO_ESPECIAL);

        Gson gson = new Gson(); //Instancia Gson.
        String json = preferences.getString(Constantes.BD_MENSAJERO_ESPECIAL, null);
        Mensajeros mensajero = gson.fromJson(json, Mensajeros.class);

        if (mensajero!=null) {
            ET_email.setText(mensajero.getEmail());
            ET_numero.setText(mensajero.getTelefono());
            ET_nombre.setText(mensajero.getNombre());
            String pathFoto = Constantes.URL_FOTO_PERFIL_CONDUCTOR+mensajero.getCodigo();
            Vehiculo vehiculo = mensajero.getCarro();
            carro = vehiculo.getMarca() + " "+vehiculo.getModelo_vehiculo()+ " color: "+vehiculo.getColor()+
                    " placa: "+vehiculo.getPlaca();
            ET_carro.setText(carro);
            codigo = mensajero.getCodigo();
            ratingBarCal.setRating(mensajero.getCalificacion());

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference().child(pathFoto+"/foto_perfil");


            Glide.with(getActivity())
                    .load(storageReference)
                    .into(image_perfil);
        }else{
            if(MainActivity.isOnline(getActivity())){
                String id_user = FirebaseAuth.getInstance().getUid();

                if (id_user!=null) {
                    datos(id_user);
                }else{
                    Log.i("iduser perfil",id_user + "este es el id");
                }
            }else{
                Toast.makeText(getActivity(),"Verifique su conexión a internet",Toast.LENGTH_LONG).show();
                ET_numero.setText("000 000 0000");
                ET_nombre.setText("Nombre");
                ET_email.setText("minombre@ejemplo.com");
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boton_cerrar_sesion:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Cerrar Sesión?")
                        .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //cerrar la sesion del usuario actual.

                                if (isMyServiceRunning(ServicioEstadoConectado.class)) {
                                    Toast.makeText(getActivity(),"Primero debes desconectarte",Toast.LENGTH_LONG).show();
                                    dialogInterface.dismiss();
                                } else {
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                }
                            }
                        })
                        .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.boton_cambiar:
                try {
                    //creando el dialogo de actualizacion
                    dialog_cambiar_numero = new Dialog(getActivity(), R.style.Theme_AppCompat_DialogWhenLarge);
                    dialog_cambiar_numero.setCancelable(true);
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View v = inflater.inflate(R.layout.layout_cambiar_numero, null);
                    Button cambiar = v.findViewById(R.id.boton_cambiar_numero_dialog);
                    ETnumero_nuevo = v.findViewById(R.id.texto_numero_nuevo);
                    cambiar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!ETnumero_nuevo.getText().toString().isEmpty()) {
                                nuevo_telefono = ETnumero_nuevo.getText().toString();
                                if(isNumeroValid(nuevo_telefono)) {
                                    dialog_cambiar_numero.dismiss();
                                    //debemos aqui escribir el nuevo numero en la base de datos y
                                    //mirar como cambiar el id de usuario para saber si podemos el el login reescribirlo
                                    //ya no estará disponible hasta que se envíe el codigo
                                    try {
                                        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity())
                                                .setCancelable(true)
                                                .setMessage("¿ Está seguro de cambiar el número ?")
                                                .setTitle("Proceso irreversible!")
                                                .setIcon(R.drawable.ic_imagen_alerta)
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (codigo!=null) {
                                                            if (!isMyServiceRunning(ServicioEstadoConectado.class)) {
                                                                progressBar.setVisibility(View.VISIBLE);
                                                                final Query query = database.orderByKey();
                                                                query.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot snapshot :
                                                                                dataSnapshot.getChildren()) {
                                                                            Mensajeros mensajero = snapshot.getValue(Mensajeros.class);
                                                                            try {
                                                                                if (mensajero.getTelefono() != null) {
                                                                                    if (mensajero.getTelefono().equals(nuevo_telefono)) {
                                                                                        numero_exixte = true;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            } catch (NullPointerException e) {
                                                                                e.printStackTrace();
                                                                            }

                                                                        }
                                                                        if(numero_exixte){
                                                                            Toast.makeText(getActivity(),"El número de telefono ya existe en otra cuenta",Toast.LENGTH_LONG).show();
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                        }else{
                                                                            database.child(codigo).child(Constantes.BD_TELEFONO_USUARIO)
                                                                                    .setValue(nuevo_telefono).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                                                                        FirebaseAuth.getInstance().getCurrentUser().delete().addOnSuccessListener(
                                                                                                new OnSuccessListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(Void aVoid) {
                                                                                                        FirebaseAuth.getInstance().signOut();
                                                                                                        startActivity(new Intent(getActivity(),LoginActivity.class));
                                                                                                        progressBar.setVisibility(View.INVISIBLE);

                                                                                                    }
                                                                                                }
                                                                                        );
                                                                                    }

                                                                                }
                                                                            });
                                                                        }
                                                                        query.removeEventListener(this);
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                        progressBar.setVisibility(View.INVISIBLE);

                                                                    }
                                                                });

                                                            } else {
                                                                Toast.makeText(getActivity(),"Primero debes desconectarte",Toast.LENGTH_LONG).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getActivity(),"Ocurrió un error por favor intenta más tarde.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        builder1.show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(),"Ocurrió un error por favor intenta más tarde.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getActivity(),"Número no válido", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getActivity(),"Escriba un número de telefono", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog_cambiar_numero.setContentView(v);
                    dialog_cambiar_numero.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("click bumero","click");
                break;
            case R.id.text_cambiar_vehículo:
                try {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setMessage("Volverás a la pantalla para cargar las imágenes del vehículo")
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (codigo!=null) {
                                        if (!isMyServiceRunning(ServicioEstadoConectado.class)) {
                                            database = FirebaseDatabase.getInstance().getReference()
                                                    .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_MENSAJERO_ESPECIAL)
                                                    .child(codigo).child(Constantes.BD_ESTADO_MENSAJERO);

                                            database.setValue(Constantes.ESTADO_SUBIR_IMAGENES).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    startActivity(new Intent(getActivity(),LoginActivity.class));
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getActivity(),"Primero debes desconectarte",Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),"Ocurrió un error por favor intenta más tarde.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    builder1.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Ocurrió un error por favor intenta más tarde.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);
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

    public void datos(String id){
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference currenUser = firebaseDatabase.getReference()
                .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_MENSAJERO_ESPECIAL)
                .child(id);
        final Query query = currenUser.orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue()!=null) {
                    Mensajeros mensajero = dataSnapshot.getValue(Mensajeros.class);
                    nombre = mensajero.getNombre();
                    telefono = mensajero.getTelefono();
                    email = mensajero.getEmail();
                    ET_numero.setText(telefono);
                    ET_nombre.setText(nombre);
                    ET_email.setText(email);
                    String pathFoto = Constantes.URL_FOTO_PERFIL_CONDUCTOR+mensajero.getCodigo();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReference().child(pathFoto+"/foto_perfil");
                    GlideApp.with(getActivity())
                            .load(storageReference)
                            .into(image_perfil);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private boolean isNumeroValid(String numero) {
        //TODO: Replace this with your own logic
        return numero.length() == 10;
    }

    }
