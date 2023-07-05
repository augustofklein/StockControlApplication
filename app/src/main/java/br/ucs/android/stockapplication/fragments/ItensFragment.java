package br.ucs.android.stockapplication.fragments;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
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
import br.ucs.android.stockapplication.main.ItemActivity;
import br.ucs.android.stockapplication.model.Item;

/**
 * A fragment representing a list of Items.
 */
public class ItensFragment extends Fragment {

    private BDSQLiteHelper bd;

    public RecyclerView listaItens;
    public FloatingActionButton fabNovoItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItensFragment(BDSQLiteHelper bd) {
        this.bd = bd;
    }
    public ItensFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_itens, container, false);


        Context context = view.getContext();
        listaItens = (RecyclerView) view.findViewById(R.id.list);
        listaItens.setLayoutManager(new LinearLayoutManager(context));
        listaItens.setAdapter(new ItemAdapter(bd.getAllItens(), R.layout.item_layout, context, bd));

        fabNovoItem = view.findViewById(R.id.fabAdicionarItem);
        fabNovoItem.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ItemActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        listaItens.setAdapter(new ItemAdapter(bd.getAllItens(), R.layout.item_layout, getContext(), bd));

    }
}