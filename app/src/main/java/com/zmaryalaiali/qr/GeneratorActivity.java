package com.zmaryalaiali.qr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class GeneratorActivity extends AppCompatActivity {

    private Button btnScanQRCode;
    private TextView tvScannedResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnScanQRCode.setOnClickListener(v -> startQRCodeScanner());


    }

    private void startQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                tvScannedResult.setText("Scanned Result: " + result.getContents());
            } else {
                tvScannedResult.setText("Scan canceled");
            }
        }
    }
}


                // QR code generator simple

//   btnGenerateQRCode.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        generateQRCode("Hi zmaryalia ali from kandahar");
//        }
//        });
//        }
//
//private void generateQRCode(String data) {
//        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//        try {
//        Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 500, 500);
//        imageView.setImageBitmap(bitmap);
//        } catch (Exception e) {
//        e.printStackTrace();
//        }