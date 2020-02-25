package tw.com.mundi.qrscanner2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private Button btnClick;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        btnClick = (Button) findViewById(R.id.btn_click);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    private void initEvent() {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //假如你要用的是fragment进行界面的跳转
                //IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ShopFragment.this).setCaptureActivity(CustomScanAct.class);
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator
                        //.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                        .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                        .setBeepEnabled(true)//是否有掃描成功的滴滴聲
                        .setBarcodeImageEnabled(true)//是否儲存二維碼
                        //.setOrientationLocked(false)//扫描方向固定
                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
            }
        });
    }

    //获取扫描的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanner = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanner != null) {
            String code = scanner.getContents(); // 掃描得到的字串
            String format = scanner.getFormatName(); // 掃描的格式, 如 QR_CODE
            tvResult.setText("Format = [" + format + "] QRCode = [" + code + "]");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

}
