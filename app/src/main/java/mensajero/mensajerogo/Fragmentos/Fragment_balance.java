package mensajero.mensajerogo.Fragmentos;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import mensajero.mensajerogo.Constantes.Constantes;
import mensajero.mensajerogo.Constantes.Mensajeros;
import mensajero.mensajerogo.MainActivity;
import mensajero.mensajerogo.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_balance extends Fragment  {

    public static final String TAG = "Balances";
    private TextView text_saldo,text_viajes,text_ingreso, text_saldo_mensajeroGo, codigo_ref, copiar;
    private DatabaseReference database;
    private Mensajeros mensajero;
    private int viajes;
    private double saldo,ingresos;
    private ArrayList<Double> listavaloresviajes;

    public Fragment_balance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_balance, container, false);
        database = FirebaseDatabase.getInstance().getReference()
                .child(Constantes.BD_GERENTE).child(Constantes.BD_ADMIN).child(Constantes.BD_MENSAJERO_ESPECIAL);

        mensajero = new Mensajeros();

        text_ingreso = v.findViewById(R.id.textingreso);
        text_saldo = v.findViewById(R.id.textsaldo);
        text_viajes = v.findViewById(R.id.textviajes);
        text_saldo_mensajeroGo = v.findViewById(R.id.saldo_mensajeroGo);
        codigo_ref = v.findViewById(R.id.text_codigo_referido);
        copiar = v.findViewById(R.id.copiar_cod);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(MainActivity.isOnline(getActivity())){
            getDatos();
        }else{
            Toast.makeText(getActivity(),"Verifique su conexión a internet",Toast.LENGTH_LONG).show();
        }

        copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = codigo_ref.getText().toString();
                ClipData clip = ClipData.newPlainText("text", codigo);
                ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(MainActivity.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(),"copiado",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDatos(){

        String codigo = getArguments().getString(Constantes.BD_CODIGO_MENSAJERO,null);

        Query query = database.child(codigo).child(Constantes.BD_PEDIDO_ESPECIAL);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    viajes = (int)dataSnapshot.getChildrenCount();
                    text_viajes.setText(String.valueOf(viajes));
                }else{
                    ingresos  = 0;
                    viajes = 0;
                    text_viajes.setText(viajes+"");
                    text_ingreso.setText(ingresos+ "");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query = database.child(codigo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()!=null){

                    mensajero = dataSnapshot.getValue(Mensajeros.class);
                    if (mensajero!=null) {
                        saldo = mensajero.getSaldo();
                        ingresos = mensajero.getIngreso();
                        text_ingreso.setText("$ "+ingresos);
                        codigo_ref.setText(mensajero.getCodigo_referido());

                        if (saldo<0) {
                            saldo = saldo * -1;
                            text_saldo.setText(String.valueOf(saldo));
                        }else{
                            text_saldo_mensajeroGo.setText("$ " + saldo);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}































