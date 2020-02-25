package tw.com.mundi.otthome;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private ImageView   mQRCodeImage;
    private TextView    mSerialno;
    private String      mSerialNumber;
    private String      mUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSerialno = findViewById(R.id.label_serialno);
        mQRCodeImage = findViewById(R.id.qrcode_image);
        getSerialNumber();
        QRCodeGenerator();
    }

    private void getSerialNumber() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            mSerialNumber = (String) get.invoke(c, "ro.serialno");
            String str = getString(R.string.label_serialno) + mSerialNumber;
            mSerialno.setText(str);
            Log.d(TAG , "serialnumber = " + mSerialNumber);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return;
    }

    private void QRCodeGenerator() {
        String url = getString(R.string.url_prefix);
        url = url + mSerialNumber + "?" + getString(R.string.param_serialno) + "=";
        mUUID = UUID.randomUUID().toString();
        url = url + "&" + getString(R.string.param_uuid) + "=" + mUUID;
        Log.d(TAG, "url = " + url);
        BarcodeEncoder encoder = new BarcodeEncoder();
        try{
            Bitmap bit = encoder.encodeBitmap(url,
                    BarcodeFormat.QR_CODE,500,500);
            mQRCodeImage.setImageBitmap(bit);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }
}

