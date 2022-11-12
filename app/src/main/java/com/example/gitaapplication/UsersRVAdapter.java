package com.example.gitaapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitaapplication.Data.DBClient;
import com.example.gitaapplication.Data.Users;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UsersRVAdapter extends RecyclerView.Adapter<UsersRVAdapter.RVViewHolder> {

    Context context;
    List<Users> usersList;


    public UsersRVAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersRVAdapter.RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_items_list,parent,false);

        return new UsersRVAdapter.RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRVAdapter.RVViewHolder holder, int position) {
        Users users = usersList.get(position);
        holder.nameCVTV.setText(users.getName());
        holder.mobNumCVTV.setText(users.getMobNum());
        holder.locationCVTV.setText(users.getLocation());
        holder.genderCVTV.setText(users.getGender());
        holder.careerCVTV.setText(users.getCareer());
        holder.dateCVTV.setText(users.getDateBirth());
        holder.profileImgCVTV.setImageBitmap(ImageBitmapConverter.convertByte2Image(users.getImage()));
        holder.emailCVTV.setText(users.getEmail());


    }
  /*  public void setItems (List<Users> users){
        this.usersList = users;
    }*/

    public void refreshList(List<Users> usersList){
        this.usersList = usersList;

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class RVViewHolder extends RecyclerView.ViewHolder {
        TextView nameCVTV, emailCVTV,locationCVTV,mobNumCVTV,dateCVTV,genderCVTV,careerCVTV;
        CircleImageView profileImgCVTV;
        ImageButton deleteIMGButton,updateIMGButton,settingIMGButton;
        public RVViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCVTV = itemView.findViewById(R.id.nameCVET);
            emailCVTV = itemView.findViewById(R.id.emailCVET);
            mobNumCVTV = itemView.findViewById(R.id.mobNumCVET);
            locationCVTV = itemView.findViewById(R.id.locationCVET);
            dateCVTV = itemView.findViewById(R.id.dateCVET);
            careerCVTV = itemView.findViewById(R.id.careerCVET);
            genderCVTV = itemView.findViewById(R.id.genderCVET);
            profileImgCVTV = itemView.findViewById(R.id.userCVIMG);
            deleteIMGButton = itemView.findViewById(R.id.deleteImageButton);
            updateIMGButton = itemView.findViewById(R.id.updateImageButton);
            settingIMGButton = itemView.findViewById(R.id.settingsImageButton);


            settingIMGButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Users usersInfo = usersList.get(getAbsoluteAdapterPosition());
                    Intent intent = new Intent(context,SettingsActivity.class);
                    intent.putExtra("users",usersInfo);
                    context.startActivity(intent);

                }
            });

            deleteIMGButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you Sure You Want To Delete Your User? ");
                    builder.setIcon(R.drawable.ic_question);

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Users usersInfo = usersList.get(getAbsoluteAdapterPosition());
                            deleteTask(usersInfo);
                            refreshList(usersList);


                            /*adapter = new RVAdapter(RecyclerViewActivity.this, list);
                            rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();*/
                           // ((Activity)context).finish();



                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            updateIMGButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you Sure You Want To Update Your User? ");
                    builder.setIcon(R.drawable.ic_question);
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Users usersInfo = usersList.get(getAbsoluteAdapterPosition());

                            Intent intent = new Intent(context,UpdateInfoActivity.class);
                            intent.putExtra("users", usersInfo);

                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        }


    }

    private void deleteTask(Users users) {

        class DeleteTask extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DBClient.getInstance(context.getApplicationContext()).getAppDataBase()
                        .usersDAO()
                        .delete(users);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(context.getApplicationContext(), "Data Deleted! ", Toast.LENGTH_SHORT).show();

            }
        }
        DeleteTask dt = new DeleteTask();
        dt.execute();

    }


}
