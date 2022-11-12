package com.example.gitaapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gitaapplication.Data.DBClient;
import com.example.gitaapplication.Data.Users;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

   // private static final int PERMISSION_CODE = 1234;
    //private static final int CAPTURE_CODE = 1001;
    //Uri imageUri;
    EditText infoFNameET, infoLocationET,infoMobNumET,infoDateET,infoEmailET;
    Button infoTakePhotoBtn, infoSaveButton;
    ImageButton infoDateButton,locationImgButton;
    Spinner spinner;
    CircleImageView infoCircleImageView;
    Bitmap bmpImg;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    Users users;
    String spinnerItem;
    RadioGroup radioGroup;
    String gender;


    final int CAMERA_INTENT = 23;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        infoFNameET = findViewById(R.id.infoFNameET);
        infoLocationET = findViewById(R.id.infoLocationET);
        infoMobNumET = findViewById(R.id.infoMobNumberET);
        infoEmailET = findViewById(R.id.infoEmailET);
        infoDateET = findViewById(R.id.infoDateET);

        radioGroup = findViewById(R.id.radioGroup);


        infoTakePhotoBtn = findViewById(R.id.takePicButton);
        infoSaveButton = findViewById(R.id.infoSaveButton);
        infoDateButton = findViewById(R.id.dateInfoButton);
        locationImgButton = findViewById(R.id.idLocationIMG);

        spinner = findViewById(R.id.infoSpinner);
        infoCircleImageView = findViewById(R.id.circleImg);
        bmpImg = null;

        arrayList = new ArrayList<>();
        arrayList.add("Career..");
        arrayList.add("Musician");
        arrayList.add("Developer");
        arrayList.add("Doctor");
        arrayList.add("Engineer");
        arrayList.add("Architect");
        arrayList.add("Lawyer");
        arrayList.add("Economist");
        arrayList.add("Teacher");
        arrayList.add("Painter");
        arrayList.add("Actor");
        arrayList.add("Digital Marketing");
        arrayList.add("Another.. ");

        arrayAdapter = new ArrayAdapter(UserInfoActivity.this, R.layout.spinner_item, arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                spinnerItem = arrayList.get(position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        infoDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserInfoActivity.this, (android.R.style.Theme_Holo_Dialog),
                        ((view, year, month, dayOfMonth) ->
                                infoDateET.setText(year + "/" + (month + 1) + "/" + dayOfMonth)), 1991, 1, 1);
                datePickerDialog.show();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = findViewById(i);

                gender = radioButton.getText().toString();

            }
        });

        infoTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_INTENT);



               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{ 
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }*/


                }

        });

        infoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInfo saveInfo = new SaveInfo();
                saveInfo.execute();
                Intent intent = new Intent(UserInfoActivity.this, AllUsersActivity.class);
                startActivity(intent);
            }
        });
    }



      @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           /* switch (requestCode){
                case CAMERA_INTENT:
                    bmpImg = (Bitmap) data.getExtras().get("data");
                    if (bmpImg != null){
                        infoCircleImageView.setImageBitmap(bmpImg);
                    }else{
                        Toast.makeText(this, "Image null", Toast.LENGTH_SHORT).show();
                    }break;
            }*/
          if(requestCode == 23){
              bmpImg = (Bitmap) data.getExtras().get("data");
              infoCircleImageView.setImageBitmap(bmpImg);

          }
    }


    class SaveInfo extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                users = new Users();
             /*   if (infoFNameET.getText().toString().isEmpty() ||
                        infoEmailET.getText().toString().isEmpty() ||
                        infoMobNumET.getText().toString().isEmpty() ||
                        spinnerItem == null || infoDateET.getText().toString().isEmpty() ||
                        infoLocationET.getText().toString().isEmpty() ||
                        gender == null || bmpImg == null){
                    //Toast.makeText(UserInfoActivity.this, "Please fill all Data", Toast.LENGTH_SHORT).show();
                }else {*/
                    users.setName(infoFNameET.getText().toString());
                    users.setEmail(infoEmailET.getText().toString());
                    users.setMobNum(infoMobNumET.getText().toString());
                    users.setCareer(spinnerItem);
                    users.setDateBirth(infoDateET.getText().toString());

                    users.setLocation(infoLocationET.getText().toString());
                    users.setGender(gender);
                    users.setImage(ImageBitmapConverter.convertImageToByteArray(bmpImg));
                    DBClient.getInstance(getApplicationContext()).getAppDataBase().usersDAO().insert(users);


                return null;

            }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(getApplicationContext(), "Data Successfully Saved! ", Toast.LENGTH_LONG).show();
        }
    }

    }

     /*   private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(camIntent,CAPTURE_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length >0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }

    }
*/
   /* @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            infoCircleImageView.setImageURI(imageUri);
        }
    }*/
