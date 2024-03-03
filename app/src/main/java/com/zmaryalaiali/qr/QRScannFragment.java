package com.zmaryalaiali.qr;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Hashtable;


public class QRScannFragment extends Fragment {
    private Context mContext;
    private ImageView ivScanner;
    Button btnImage, btnCamera;


    private DecoratedBarcodeView barcodeView;

    private EditText etScanText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_scann, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnImage = view.findViewById(R.id.btn_image);
        btnCamera = view.findViewById(R.id.btn_camera);
        etScanText = view.findViewById(R.id.et_qrCode_scan_text);
        barcodeView = view.findViewById(R.id.qrcode_scanner);
        ivScanner = view.findViewById(R.id.iv_qrcode_scan);

        barcodeView.decodeSingle(callback);

        btnCamera.setOnClickListener(v -> {
            Toast.makeText(mContext, "start QR code please again", Toast.LENGTH_SHORT).show();
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery intent
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 102);
            }
        });

    }


    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            if (result != null) {
                barcodeView.setVisibility(View.GONE);
                ivScanner.setVisibility(View.VISIBLE);
                ivScanner.setImageBitmap(result.getBitmap());
                etScanText.setText(result.getText());
                Toast.makeText(mContext, result.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

//    private void startQRCodeScanner() {
//
//        ScanOptions  options = new ScanOptions();
//        options.setPrompt("Volume up to flash on");
//        options.setBeepEnabled(true);
//        options.setOrientationLocked(true);
//        options.setCaptureActivity(MyOption.class);
//        qr.launch(options);
//        Toast.makeText(mContext, "clicked me", Toast.LENGTH_SHORT).show();
//    }
//
//   private ActivityResultLauncher<ScanOptions> qr = registerForActivityResult(new ScanContract(),result->{
//       if (result!= null){
//           Toast.makeText(mContext, result.getContents(), Toast.LENGTH_SHORT).show();
//       }
//    });

    // when image selected
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(image));
                Result result = decodeQRCode(bitmap);
                if (result != null){

                    barcodeView.setVisibility(View.GONE);
                    ivScanner.setVisibility(View.VISIBLE);
                    ivScanner.setImageBitmap(bitmap);
                    etScanText.setText(result.getText());

                }
                else
                Toast.makeText(mContext, "cancel", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "Not hi", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "not selecting ", Toast.LENGTH_SHORT).show();
        }

    }


    private Result decodeQRCode(Bitmap bitmap) {
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        MultiFormatReader reader = new MultiFormatReader();
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            return reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}