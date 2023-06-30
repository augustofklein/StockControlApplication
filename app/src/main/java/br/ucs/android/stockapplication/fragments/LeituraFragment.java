package br.ucs.android.stockapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;

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

    public LeituraFragment() { }

    public LeituraFragment(BDSQLiteHelper bd) {
        this.bd = bd;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leitura, container, false);
    }
}