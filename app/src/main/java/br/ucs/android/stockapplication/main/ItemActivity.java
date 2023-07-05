package br.ucs.android.stockapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.adapter.ItemAdapter;
import br.ucs.android.stockapplication.database.BDSQLiteHelper;
import br.ucs.android.stockapplication.model.Item;

public class ItemActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;

    public TextView tvIdItem;
    public TextInputEditText etCodigo, etDescricao, etQuantidade, etUnidade;
    public Button bSalvar, bCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        bd = new BDSQLiteHelper(this);

        tvIdItem = (TextView) findViewById(R.id.tvIdItem);
        etCodigo = (TextInputEditText) findViewById(R.id.tietCodigo);
        etDescricao = (TextInputEditText) findViewById(R.id.tietDescricao);
        etUnidade = (TextInputEditText) findViewById(R.id.tietUnidade);
        etQuantidade = (TextInputEditText) findViewById(R.id.tietQuantidade);

        bSalvar = (Button) findViewById(R.id.bSalvarItem);
        bSalvar.setOnClickListener(view1 -> salvarItem());

        bCancelar = (Button) findViewById(R.id.bCancelarItem);
        bCancelar.setOnClickListener(view1 -> finish());



        Intent intent = getIntent();
        final int idItem = intent.getIntExtra("ID", 0);
        tvIdItem.setText(idItem + "");

        if(idItem > 0) {
            Item item = bd.getItem(idItem);
            etCodigo.setText(item.getCodigo());
            etDescricao.setText(item.getDescricao());
            etUnidade.setText(item.getUnidade());
            etQuantidade.setText(item.getQuantidade().toString());

        }

    }

    public void salvarItem()
    {
        if(!etCodigo.getText().toString().isEmpty() && !etDescricao.getText().toString().isEmpty()
                    && !etUnidade.getText().toString().isEmpty() && !etQuantidade.getText().toString().isEmpty() ) {
            Item item = new Item();
            item.setCodigo(etCodigo.getText().toString());
            item.setDescricao(etDescricao.getText().toString());
            item.setUnidade(etUnidade.getText().toString());
            item.setQuantidade(Double.parseDouble(etQuantidade.getText().toString()));

            item.setId(Integer.parseInt(tvIdItem.getText().toString()));
            if (item.getId() == 0) {
                bd.addItem(item);
            } else {
                bd.updateItem(item);
            }
            finish();
        }
        else {
            Toast.makeText(this, "Informe todos os campos para salvar!", Toast.LENGTH_SHORT).show();
        }

    }
}