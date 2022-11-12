package com.example.gitaapplication;

import static com.airbnb.lottie.LottieProperty.COLOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.gitaapplication.Data.DBClient;
import com.example.gitaapplication.Data.Users;

import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        GetData getData = new GetData();
        getData.execute();




    }
    class GetData extends AsyncTask<Void,Void, List<Users>>{

        @Override
        protected List<Users> doInBackground(Void... voids) {
            List<Users> allUsers = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDataBase()
                    .usersDAO()
                    .getAll();

            return allUsers;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(List<Users> users) {
            super.onPostExecute(users);
            UsersRVAdapter adapter = new UsersRVAdapter(AllUsersActivity.this,users);
            recyclerView.setAdapter(adapter);
            adapter.refreshList(users);
            adapter.notifyDataSetChanged();

        }
    }

}