package br.ucs.android.stockapplication.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.main.ItemActivity;
import br.ucs.android.stockapplication.model.Item;
import br.ucs.android.stockapplication.model.ItemLista;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private BDSQLiteHelper bd;
    private List<ItemLista> itens;
    private int rowLayout;
    private Context context;

    public ItemAdapter(List<ItemLista> itens, int rowLayout, Context context, BDSQLiteHelper bd) {
        this.itens = itens;
        this.rowLayout = rowLayout;
        this.context = context;
        this.bd = bd;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        ItemLista item = itens.get(position);

        holder.tvCodigo.setText(item.getCodigo());
        holder.tvQuantidade.setText(item.getQuantidadeLida().toString() + " de " + item.getQuantidade().toString() + " " + item.getUnidade() + "(s)");
        holder.tvDescricao.setText(item.getDescricao());

        double percent = item.getQuantidadeLida() / item.getQuantidade();

        int cor = R.color.white;
        if(percent == 0) {
            cor = R.color.gray_zerado;
        }
        else if (percent < 0.8) {
            cor = R.color.red_faltando;
        }
        else if (percent < 1) {
            cor = R.color.yellow_quase;
        }
        else if(percent == 1) {
            cor = R.color.green_ok;
        }
        else if(percent > 1) {
            cor = R.color.blue_sobrando;
        }

        holder.tvCor.setBackgroundResource(cor);

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        LinearLayout itensLayout;
        TextView tvCodigo, tvQuantidade, tvDescricao, tvCor;

        public ItemViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            itensLayout = (LinearLayout) v.findViewById(R.id.item_layout);
            tvCodigo = (TextView) v.findViewById(R.id.tvCodigoItem);
            tvQuantidade = (TextView) v.findViewById(R.id.tvQuantidadeItem);
            tvDescricao = (TextView) v.findViewById(R.id.tvDescricaoItem);
            tvCor = (TextView) v.findViewById(R.id.tvCor);
        }

        @Override
        public void onClick(View view) {
            Item item = itens.get(getLayoutPosition());
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("ID", item.getId());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }


        @Override
        public boolean onLongClick(View view) {
            Item i = itens.get(getLayoutPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle("Apagar Item")
                    .setMessage("Deseja excluir o Item selecionado?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            bd.deleteItem(i.getId());
                            itens.remove(getLayoutPosition());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


            return true;
        }

    }

}