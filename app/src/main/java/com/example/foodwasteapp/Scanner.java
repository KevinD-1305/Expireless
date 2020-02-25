package com.example.foodwasteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Scanner extends AppCompatActivity {

    CameraView camera_View;
    boolean isDetected = false;
    Button btn_start_again;
    private RequestQueue mQueue;
    public static String productName;
    public static String code;

    FirebaseVisionBarcodeDetectorOptions options;
    FirebaseVisionBarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Dexter.withActivity(this)
                .withPermissions(new String[] {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO})
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        setupCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
        mQueue = Volley.newRequestQueue(this);
    }

        private void setupCamera() {
        btn_start_again = (Button)findViewById(R.id.btn_again);
        btn_start_again.setEnabled(!isDetected);
        btn_start_again.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                isDetected = !isDetected;
            }
        });

        camera_View = (CameraView)findViewById(R.id.cameraView);
        camera_View.setLifecycleOwner(this);
        camera_View.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                processImage(getVisionImageFromFrame(frame));
            }
        });

        options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    }

    private void processImage(FirebaseVisionImage image) {
        if(!isDetected)
        {
            detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                            processResult(firebaseVisionBarcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Scanner.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
        if(firebaseVisionBarcodes.size() > 0)
        {
            isDetected = true;
            btn_start_again.setEnabled(isDetected);
            for(FirebaseVisionBarcode item: firebaseVisionBarcodes)
            {
                int value_type = item.getValueType();
                switch (value_type)
                {
                    case FirebaseVisionBarcode.TYPE_PRODUCT:
                    {
                        jsonParse();
                        code = (item.getDisplayValue()); //Change to the name of product
                    }
                    break;
                    case FirebaseVisionBarcode.TYPE_URL:
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                        startActivity(intent);
                    }
                    break;
                    case FirebaseVisionBarcode.TYPE_CONTACT_INFO:
                    {
                        String info = new StringBuilder("Name: ")
                                .append(item.getContactInfo().getName().getFormattedName())
                                .append("\n")
                                .append("Address: ")
                                .append(item.getContactInfo().getAddresses().get(0).getAddressLines()[0])
                                .append("\n")
                                .append("Email: ")
                                .append(item.getContactInfo().getEmails().get(0).getAddress())
                                .toString();
                        createDialog(info);
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }

    private void createDialog(String text) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Product Info:");
        builder.setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext()
                                , Expiration.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
                dialog.show();
    }

    private FirebaseVisionImage getVisionImageFromFrame (Frame frame) {
        byte[] data = frame.getData();
        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setHeight(frame.getSize().getHeight())
                .setWidth(frame.getSize().getWidth())
                //.setRotation(frame.getRotation())
                .build();
        return FirebaseVisionImage.fromByteArray(data,metadata);
    }

    public void jsonParse() {

        String url = "https://world.openfoodfacts.org/api/v0/product/" + code + ".json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject product = response.getJSONObject("product");
                            productName = product.get("product_name").toString();
                            String servingSize = product.get("product_quantity").toString();
                            createDialog(productName + "\n\n" + "Quantity: " + servingSize + "ML/G" + "\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }


}
