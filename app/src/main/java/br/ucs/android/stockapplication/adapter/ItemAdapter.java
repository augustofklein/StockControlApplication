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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.main.ItemActivity;
import br.ucs.android.stockapplication.model.Item;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

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
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.codigo.setText(itens.get(position).getCodigo());
        holder.quantidade.setText(itens.get(position).getQuantidade().toString() + itens.get(position).getUnidade());
        holder.descricao.setText(itens.get(position).getDescricao());



    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        LinearLayout itensLayout;
        TextView codigo;
        TextView quantidade;
        TextView descricao;

        public ItemViewHolder(View v) {
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