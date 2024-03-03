package com.zmaryalaiali.qr;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.PesRequest;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class QRGeneratFragment extends Fragment {
    private  Context mContext;
    private View mView;
    Button btnQRGenerator;
    ImageView imageViewQR;
 EditText textQR;
    Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_generat, container, false);
    }
    

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   btnQRGenerator = view.findViewById(R.id.btn_qrCode_generate);
   textQR = view.findViewById(R.id.et_qrCode_text);
   imageViewQR = view.findViewById(R.id.iv_qrCode_image);
   
   btnQRGenerator.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Toast.makeText(mContext, "generate me haja", Toast.LENGTH_SHORT).show();
           String text = textQR.getText().toString();
           generateQRCode(text);
       }
   });
    }

    private void generateQRCode(String data) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 500, 500);
            imageViewQR.setImageBitmap(bitmap);

                if (isPermission()){
                    createExternalFolderAndSaveImage(); 
                }
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  private boolean  isPermission(){
        if (!(ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))

            ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 7);

        return true;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)

            Toast.makeText(mContext, "the permission is Granted", Toast.LENGTH_SHORT).show();
    }

    private void createExternalFolderAndSaveImage() {
        
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File folder = Environment.getExternalStoragePublicDirectory("QR");
            try {
                if (!folder.exists()){
                    folder.mkdir();
                    Toast.makeText(mContext, "Successfully create folder", Toast.LENGTH_SHORT).show();
                }
                long imageName = System.currentTimeMillis();
                File file = new File(folder, imageName+"image.png");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    

}