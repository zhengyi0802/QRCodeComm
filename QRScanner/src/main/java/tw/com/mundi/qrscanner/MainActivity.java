package tw.com.mundi.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.result_url);
        IntentIntegrator scanner = new IntentIntegrator(this);
        scanner.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanner = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanner != null) {
            String code = scanner.getContents(); // 掃描得到的字串
            String format = scanner.getFormatName(); // 掃描的格式, 如 QR_CODE
            mTextView.setText(code);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
