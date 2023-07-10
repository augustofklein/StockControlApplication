package br.ucs.android.stockapplication.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import br.ucs.android.stockapplication.R;
import br.ucs.android.stockapplication.model.Capture;

public class PhotoActivity extends AppCompatActivity {
    private MaterialButton btnScan;
    private IntentResult intentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leitura);

        IntentIntegrator intentIntegrator = new IntentIntegrator(PhotoActivity.this);

        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult.getContents() != null){
            TextView textResult = findViewById(R.id.etCodigo);
            textResult.setText(intentResult.getContents());
        }else{
            Toast.makeText(this, "Erro na leitura!", Toast.LENGTH_SHORT).show();
        }

    }
}
