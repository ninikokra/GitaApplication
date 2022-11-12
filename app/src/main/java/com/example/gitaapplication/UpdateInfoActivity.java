package com.example.gitaapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gitaapplication.Data.DBClient;
import com.example.gitaapplication.Data.Users;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInfoActivity extends AppCompatActivity {

    EditText fNameUP,emailUP,locationUP,mobUP,genderUP,careerUP,dateUP;
    CircleImageView imageUP;
    ImageButton dateUPButton,retakeButton;
    Button  updateInfoButton;

    final int CAMERA_INTENT = 25;
    Bitmap bmpImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        fNameUP = findViewById(R.id.updateFNameET);
        emailUP = findViewById(R.id.updateEmailET);
        locationUP = findViewById(R.id.updateLocationET);
        mobUP = findViewById(R.id.updateMobET);
        genderUP = findViewById(R.id.updateGender);
        careerUP = findViewById(R.id.updateCareerET);
        dateUP = findViewById(R.id.updateDateET);
        imageUP = findViewById(R.id.updateCircleIMG);
        dateUPButton = findViewById(R.id.updateDateButton);
        retakeButton = findViewById(R.id.retakeButton);
        updateInfoButton = findViewById(R.id.updateInfoButton);


        Users usersData = (Users) getIntent().getSerializableExtra("users");
        loadTask(usersData);

        dateUPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInfoActivity.this, (android.R.style.Theme_Holo_Dialog),
                        ((view, year, month, dayOfMonth) ->
                                dateUP.setText(year + "/" + (month + 1) + "/" + dayOfMonth)), 1991, 1, 1);
                datePickerDialog.show();
            }
        });

        retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_INTENT);
            }
        });


        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsers(usersData);
            }
        });

        //updateName(usersData);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 25){
            bmpImg = (Bitmap) data.getExtras().get("data");
            imageUP.setImageBitmap(bmpImg);

        }
    }


    private void loadTask(Users users) {
        fNameUP.setText(users.getName());
        emailUP.setText(users.getEmail());
        dateUP.setText(users.getDateBirth());
        locationUP.setText(users.getLocation());
        genderUP.setText(users.getGender());
        mobUP.setText(users.getMobNum());
        careerUP.setText(users.getCareer());
        imageUP.setImageBitmap(ImageBitmapConverter.convertByte2Image(users.getImage()));




    }

    private void updateUsers(Users users) {
        String sName  = fNameUP.getText().toString().trim();
        String sEmail  = emailUP.getText().toString().trim();
        String sDate  = dateUP.getText().toString().trim();
        String sLocation  = locationUP.getText().toString().trim();
        String sGender  = genderUP.getText().toString().trim();
        String sMob  = mobUP.getText().toString().trim();
        String sCareer = careerUP.getText().toString().trim();
        imageUP.setImageBitmap(ImageBitmapConverter.convertByte2Image(users.getImage()));

        //String sImage  = Arrays.toString(users.getImage(ImageBitmapConverter.convertImageToByteArray(bitmap)));



        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                users.setName(sName);
                users.setGender(sGender);
                users.setLocation(sLocation);
                users.setDateBirth(sDate);
                users.setMobNum(sMob);
                users.setEmail(sEmail);
                users.setCareer(sCareer);
                users.getImage();


                DBClient.getInstance(getApplicationContext()).getAppDataBase()
                        .usersDAO()
                        .update(users);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(getApplicationContext(), "Data Successfully Updated! ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateInfoActivity.this,FinallyHappyActivity.class));
            }
        }
        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();
    }
}