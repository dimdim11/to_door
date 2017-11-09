package com.example.dima.a2_door;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TheProduct extends AppCompatActivity {
    private PaintView paintView;
    private StorageReference storageRef;
    private StorageReference mountainsRef;
    private StorageReference mountainImagesRef;
    private EditText date;
    private EditText customer;
    private EditText address;
    private EditText phone;
    private EditText deliver;
    private EditText prod;
    private EditText gov;
    private EditText more;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_product);
        this.setTitle("prod1");
        date = (EditText)findViewById(R.id.editText7);
        customer = (EditText)findViewById(R.id.editText5);
        address = (EditText)findViewById(R.id.editText6);
        phone = (EditText)findViewById(R.id.editText8);
        deliver = (EditText)findViewById(R.id.editText9);
        prod = (EditText)findViewById(R.id.editText11);
        gov = (EditText)findViewById(R.id.editText10);
        more = (EditText)findViewById(R.id.editText12);
        paintView = (PaintView)findViewById(R.id.paintView);

        date.setText(getIntent().getExtras().getString("date"));
        customer.setText(getIntent().getExtras().getString("customer"));
        address.setText(getIntent().getExtras().getString("address"));
        phone.setText(getIntent().getExtras().getString("phone"));
        deliver.setText(getIntent().getExtras().getString("deliver"));
        prod.setText(getIntent().getExtras().getString("prod"));
        gov.setText(getIntent().getExtras().getString("gov"));
        more.setText(getIntent().getExtras().getString("more"));

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        onButtonClickListener();
    }

    public Bitmap takeScreenshot() {
        View rootView = (View)findViewById(R.id.paintView).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mountainsRef = storageRef.child("bbb.jpg");
        mountainImagesRef = storageRef.child("image/bbb.jpg");
        mountainsRef.getName().equals(mountainImagesRef.getName());
        mountainsRef.getPath().equals(mountainImagesRef.getPath());

        paintView.setDrawingCacheEnabled(true);
        paintView.buildDrawingCache();
        bitmap = paintView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    public void onButtonClickListener() {
        button = (Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(TheProduct.this);
                build.setMessage("המוצר נמסר בהצלחה?").setCancelable(false)
                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           Bitmap bitmap = takeScreenshot();
                            try {
                                saveBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    })
                    .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                AlertDialog alert = build.create();
                alert.setTitle("סיכום מסירת מוצר");
                alert.show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_for_signature, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
