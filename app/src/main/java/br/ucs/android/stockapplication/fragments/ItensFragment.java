package br.ucs.android.stockapplication.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.adapter.ItemAdapter;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.model.Item;

/**
 * A fragment representing a list of Items.
 */
public class ItensFragment extends Fragment {

    private BDSQLiteHelper bd;

    public RecyclerView listaItens;
    public FloatingActionButton fabNovoItem;

    public ConstraintLayout clItem;

    public TextView tvIdItem;
    public TextInputEditText etCodigo, etDescricao, etQuantidade, etUnidade;
    public Button bSalvar;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItensFragment(BDSQLiteHelper bd) {
        this.bd = bd;
    }
    public ItensFragment() { }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItensFragment newInstance(int columnCount) {
        ItensFragment fragment = new ItensFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itens, container, false);

        clItem = (ConstraintLayout) view.findViewById(R.id.clItem);
        clItem.setVisibility(View.GONE);

        tvIdItem = (TextView) view.findViewById(R.id.tvIdItem);
        etCodigo = (TextInputEditText) view.findViewById(R.id.tietCodigo);
        etDescricao = (TextInputEditText) view.findViewById(R.id.tietDescricao);
        etUnidade = (TextInputEditText) view.findViewById(R.id.tietUnidade);
        etQuantidade = (TextInputEditText) view.findViewById(R.id.tietQuantidade);

        Context context = view.getContext();
        listaItens = (RecyclerView) view.findViewById(R.id.list);
        listaItens.setLayoutManager(new LinearLayoutManager(context));
        listaItens.setAdapter(new ItemAdapter(bd.getAllItens(), R.layout.item_layout, context, bd));



        fabNovoItem = view.findViewById(R.id.fabAdicionarItem);
        fabNovoItem.setOnClickListener(view1 -> {
            listaItens.setBackgroundColor(Color.GRAY);
            clItem.setVisibility(View.VISIBLE);
            tvIdItem.setText("0");
        });


        bSalvar = (Button) view.findViewById(R.id.bSalvarItem);
        bSalvar.setOnClickListener(view1 -> salvarItem());




        return view;
    }

    public void carregarItem(Item item)
    {
        tvIdItem.setText(item.getId());
        etCodigo.setText(item.getCodigo());
        etDescricao.setText(item.getDescricao());
        etUnidade.setText(item.getUnidade());
        etQuantidade.setText(item.getQuantidade().toString());
        listaItens.setBackgroundColor(Color.GRAY);
        clItem.setVisibility(View.VISIBLE);
    }

    public void salvarItem()
    {
        Item item = new Item();
        item.setCodigo(etCodigo.getText().toString());
        item.setDescricao(etDescricao.getText().toString());
        item.setUnidade(etUnidade.getText().toString());
        item.setQuantidade(Double.parseDouble(etQuantidade.getText().toString()));

        item.setId(Integer.parseInt(tvIdItem.getText().toString()));
        if(item.getId() == 0) {
            bd.addItem(item);
        }
        else {
            bd.updateItem(item);
        }

        listaItens.setAdapter(new ItemAdapter(bd.getAllItens(), R.layout.item_layout, this.getContext(), bd));
        listaItens.setBackgroundColor(Color.WHITE);
        clItem.setVisibility(View.GONE);


    }

}