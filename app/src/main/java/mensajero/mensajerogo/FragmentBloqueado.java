package mensajero.mensajerogo;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 */
public class FragmentBloqueado extends Fragment {

    public static final String  TAG = "FragmentBlloqueado";


    public FragmentBloqueado() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_bloqueado, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
