package com.bhavaniprasad.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhavaniprasad.recyclerview.adapter.CustomAdapter;
import com.bhavaniprasad.recyclerview.model.RepoResponse;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.bhavaniprasad.recyclerview.model.UserDetails;
import com.bhavaniprasad.recyclerview.viewmodel.RepoViewModel;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.http.Url;

public class RepoDetails extends AppCompatActivity {
    public TextView username,fullname,reponame,projectlink;
    public ImageView imageView;
    List<RepoResponse.owner> list;
    String url;
    private RepoViewModel repoViewModel;
    private ArrayList<RepoResponse> repodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        url="https://www.github.com/";
        reponame=findViewById(R.id.reponame);
        username=findViewById(R.id.username);
        fullname=findViewById(R.id.fullname);
        imageView=findViewById(R.id.repoavatar);
        projectlink=findViewById(R.id.projectlink);
        list= new ArrayList<>();
//        Bundle bundle = getIntent().getExtras();
//        repoUser=bundle.getString("username");
//        repoDesc=bundle.getString("repodesc");

        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);

//        projectlink.setMovementMethod(LinkMovementMethod.getInstance());
        projectlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Webview.class);
                startActivityForResult(intent, 1);
            }
        });


        repoViewModel.getMutableLiveData(this).observe(this, new Observer<ArrayList<RepoResponse>>() {
            @Override
            public void onChanged(ArrayList<RepoResponse> repositories) {
                repodata = repositories;
                if (repodata.size() > 0) {
                    list.add(repodata.get(0).getOwner());
                    reponame.setText(repodata.get(0).getName());
                    fullname.setText(repodata.get(0).getFull_name());
                    try {
                        URL url=new URL(list.get(0).avatar_url);
                        Picasso.with(imageView.getContext()).load(list.get(0).avatar_url).into(imageView);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }





}
