package br.ucs.android.stockapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.model.GPS;
import br.ucs.android.stockapplication.model.Item;
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

    private Button buttonSalvar, buttonLimpar;
    private ImageView buttonBuscar, buttonCamera;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leitura, container, false);

        latitudeTela = view.findViewById(R.id.etLatitudeLeitura);
        longitudeTela = view.findViewById(R.id.etLongitudeLeitura);

        try {
            latitudeTela.setText(String.format("%.10f", gps.getLatitude()));
            longitudeTela.setText(String.format("%.10f", gps.getLongitude()));
        }
        catch (Exception e) {}

        buttonBuscar = view.findViewById(R.id.btnBuscar);
        buttonCamera = view.findViewById(R.id.btnCamera);
        buttonLimpar = view.findViewById(R.id.btnLimpar);
        buttonSalvar = view.findViewById(R.id.btnSalvar);
        codigoTela = view.findViewById(R.id.etCodigo);
        descricaoTela = view.findViewById(R.id.etDescricaoLeitura);
        quantidadeTela = view.findViewById(R.id.etQuantidadeLeitura);
        unMedidaTela = view.findViewById(R.id.etUnidadeLeitura);

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarItem();
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(getContext(), ScanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);*/

                IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(LeituraFragment.this);

                scanIntegrator.setPrompt("Para acionar o flash utilize o botão de aumentar volume");
                scanIntegrator.setBeepEnabled(true);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan();

            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparTela();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validaInformacoesTela()){
                    return;
                }

                if(bd == null) bd = new BDSQLiteHelper(getContext());

                Leitura leitura = new Leitura();

                Item item = bd.getItem(codigoTela.getText().toString());
                if(item == null) {
                    item = new Item();
                    item.setCodigo(codigoTela.getText().toString());
                    item.setDescricao(descricaoTela.getText().toString());
                    item.setUnidade(unMedidaTela.getText().toString());
                    bd.addItem(item);
                }
                leitura.setItem(item);

                leitura.setQuantidade(Double.parseDouble(quantidadeTela.getText().toString().replace(',', '.')));



                try {
                    if(latitudeTela.getText().toString().isEmpty()) {
                        latitudeTela.setText(String.format("%.10f", gps.getLatitude()));
                        longitudeTela.setText(String.format("%.10f", gps.getLongitude()));
                        Thread.sleep(100);
                    }
                }
                catch (InterruptedException ie) {}
                catch (Exception e) {}

                if(!latitudeTela.getText().toString().isEmpty()) {
                    leitura.setLatitude(Double.parseDouble(latitudeTela.getText().toString().replace(',', '.')));
                    leitura.setLongitude(Double.parseDouble(longitudeTela.getText().toString().replace(',', '.')));
                }
                Date dateObj = Calendar.getInstance().getTime();
                leitura.setData(dateObj);

                bd.addLeitura(leitura);

                limparTela();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            codigoTela.setText(scanResult.getContents());
            buscarItem();
        }
    }

    private void buscarItem() {
        quantidadeTela.setEnabled(true);

        if(bd == null) bd = new BDSQLiteHelper(getContext());
        if(gps == null) gps = new GPS();

        String codigo = codigoTela.getText().toString();

        if(!codigo.isEmpty()) {
            Item item = bd.getItem(codigo);
                if(item != null) {
                carregaItem(item);
            }
            else {
                codigo = retiraZeros(codigo);
                item = bd.getItem(codigo);
                if(item != null) {
                    codigoTela.setText(codigo);
                    carregaItem(item);
                }
                else {
                    Toast.makeText(getContext(), "Código não encontrado, informe descrição e unidade para inserir", Toast.LENGTH_LONG).show();

                    codigoTela.setEnabled(true);
                    descricaoTela.setEnabled(true);
                    unMedidaTela.setEnabled(true);
                    descricaoTela.requestFocus();
                }
            }

        }

    }

    private void carregaItem(Item item) {

        descricaoTela.setText(item.getDescricao());
        unMedidaTela.setText(item.getUnidade());

        codigoTela.setEnabled(false);
        descricaoTela.setEnabled(false);
        unMedidaTela.setEnabled(false);
        quantidadeTela.requestFocus();
    }

    private String retiraZeros(String valor) {
        String retorno = "0";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) != '0') {
                retorno = valor.substring(i);
                break;
            }
        }
        return retorno;
    }

    private void limparTela() {

        codigoTela.setText(null);
        descricaoTela.setText(" ");
        quantidadeTela.setText(" ");
        unMedidaTela.setText(" ");

        codigoTela.setEnabled(true);
        descricaoTela.setEnabled(false);
        quantidadeTela.setEnabled(false);
        unMedidaTela.setEnabled(false);


        codigoTela.requestFocus();
    }

    private boolean validaInformacoesTela(){
        if(codigoTela.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Produto não informado!", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*
        if(!bd.verifyExistItem(codigoTela.getText().toString())){
            Toast.makeText(getContext(), "Produto não cadastrado!", Toast.LENGTH_SHORT).show();
            return false;
        }*/

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