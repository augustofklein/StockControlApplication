package br.ucs.android.stockapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.model.Item;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ArtigoViewHolder> {

    private BDSQLiteHelper bd;
    private List<Item> itens;
    private int rowLayout;
    private Context context;


    public ItemAdapter(List<Item> itens, int rowLayout, Context context, BDSQLiteHelper bd) {
        this.itens = itens;
        this.rowLayout = rowLayout;
        this.context = context;
        this.bd = bd;
    }

    @Override
    public ArtigoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ArtigoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtigoViewHolder holder, final int position) {
        holder.codigo.setText(itens.get(position).getCodigo());
        holder.quantidade.setText(itens.get(position).getDescricao());

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class ArtigoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        LinearLayout itensLayout;
        TextView codigo;
        TextView quantidade;
        TextView descricao;

        public ArtigoViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            itensLayout = (LinearLayout) v.findViewById(R.id.item_layout);
            codigo = (TextView) v.findViewById(R.id.tvCodigoItem);
            quantidade = (TextView) v.findViewById(R.id.tvQuantidadeItem);
            descricao = (TextView) v.findViewById(R.id.tvDescricaoItem);
        }

        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(context, WebviewActivity.class);
//            intent.putExtra("URL", artigos.get(getLayoutPosition()).getUrl());
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);

            //Snackbar.make(view, "Você selecionou " + artigos.get(getLayoutPosition()).getTitle(), Snackbar.LENGTH_SHORT).show();
        }


        @Override
        public boolean onLongClick(View view) {

            return true;
        }



    }


}