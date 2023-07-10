package br.ucs.android.stockapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.main.PhotoActivity;
import br.ucs.android.stockapplication.model.GPS;
import br.ucs.android.stockapplication.model.Leitura;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeituraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeituraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BDSQLiteHelper bd;

    private Leitura leitura;

    private GPS gps = new GPS();

    private ImageButton buttonCamera;

    private Button buttonSalvar;

    private EditText codigoTela;
    private EditText descricaoTela;
    private EditText quantidadeTela;
    private EditText unMedidaTela;
    private EditText latitudeTela;
    private EditText longitudeTela;

    public LeituraFragment() { }

    public LeituraFragment(BDSQLiteHelper bd, GPS gps) {
        this.bd = bd;
        this.gps = gps;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeituraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeituraFragment newInstance(String param1, String param2) {
        LeituraFragment fragment = new LeituraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leitura, container, false);

        setTextoGPSFields(view);

        buttonCamera = view.findViewById(R.id.imageButton);
        buttonSalvar = view.findViewById(R.id.btnSalvar);
        codigoTela = view.findViewById(R.id.etCodigo);
        descricaoTela = view.findViewById(R.id.etmlDescricao);
        quantidadeTela = view.findViewById(R.id.etQuantidade);
        unMedidaTela = view.findViewById(R.id.etUnidade);
        latitudeTela = view.findViewById(R.id.etLatitude);
        longitudeTela = view.findViewById(R.id.etLongitude);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validaInformacoesTela()){
                    return;
                }

                Leitura leitura = new Leitura();
                leitura.setLatitude(Double.parseDouble(latitudeTela.getText().toString()));
                leitura.setLongitude(Double.parseDouble(longitudeTela.getText().toString()));
                Date dateObj = Calendar.getInstance().getTime();
                leitura.setData(dateObj);
                leitura.setItem(bd.getItem(view.findViewById(R.id.etCodigo).toString()));
                TextInputEditText quantidade = (TextInputEditText) view.findViewById(R.id.tietQuantidade);
                leitura.setQuantidade(Double.parseDouble(quantidade.getText().toString()));
                bd.addLeitura(leitura);
            }
        });

        return view;
    }

    private void setTextoGPSFields(View view){
        TextView textResult = view.findViewById(R.id.etLongitude);
        textResult.setText(gps.getLongitude().toString());

        textResult = view.findViewById(R.id.etLatitude);
        textResult.setText(gps.getLatitude().toString());
    }

    private boolean validaInformacoesTela(){
        if(codigoTela.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Produto não informado!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!bd.verifyExistItem(codigoTela.getText().toString())){
            Toast.makeText(getContext(), "Produto não cadastrado!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(descricaoTela.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Descrição não informada!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(quantidadeTela.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Quantidade não informada!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(unMedidaTela.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Unidade de medida não informada!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}