package tw.com.mundi.qrscanner3;

import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean mFlash;
    private ZXingScannerView mScannerView;

    private ZXingScannerView.ResultHandler mResultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            Log.d("MainActivity", "handleResult");
            mScannerView.resumeCameraPreview(mResultHandler); //重新进入扫描二维码
            Log.d("MainActivity", "Scanned url = " + result.getText());
            Log.d("MainActivity", "Scanned format = " + result.getBarcodeFormat());
            Toast.makeText(getApplicationContext(), "内容=" + result.getText() + ",格式=" + result.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate");
        mScannerView = (ZXingScannerView) findViewById(R.id.scannerView);
        mScannerView.setResultHandler(mResultHandler);

        findViewById(R.id.light).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlash();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");

        mScannerView.setResultHandler(mResultHandler);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
        mScannerView.stopCamera();
    }

    private void toggleFlash() {
        Log.d("MainActivity", "toggleFlash");
        mFlash = !mFlash;
        mScannerView.setFlash(mFlash);
    }

}
