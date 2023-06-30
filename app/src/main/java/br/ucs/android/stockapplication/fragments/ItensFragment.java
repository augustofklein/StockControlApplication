package br.ucs.android.stockapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.adapter.ItemAdapter;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;

/**
 * A fragment representing a list of Items.
 */
public class ItensFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private BDSQLiteHelper bd;

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
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itens, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ItemAdapter(bd.getAllItens(), R.layout.item_layout, context, bd));
        return view;
    }

    public void novoItem(View view)
    {

    }
}