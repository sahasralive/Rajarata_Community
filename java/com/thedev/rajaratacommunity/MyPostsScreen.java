package com.thedev.rajaratacommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.PostAdapter;
import com.thedev.rajaratacommunity.Models.Post;
import com.thedev.rajaratacommunity.Models.QA;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MyPostsScreen extends AppCompatActivity {
    String email;
    ArrayList<Post> posts = new ArrayList<>();
    PostAdapter adapter;
    RecyclerView MypostRview;
    ArrayList<Post> myposts=new ArrayList<>();
    ElasticImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts_screen);
        Paper.init(this);
        email=Paper.book().read("email");
        MypostRview=findViewById(R.id.MypostRview);
        back=findViewById(R.id.back);
        MypostRview.setHasFixedSize(true);
        MypostRview.setLayoutManager(new GridLayoutManager(this,2));

        getPosts();

        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void getPosts() {
        FirebaseDatabase.getInstance().getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    posts.add(ds.getValue(Post.class));
                }

                for (Post post : posts){
                    if (post.getAuther().equals(email)){
                        myposts.add(post);
                    }
                }
                adapter=new PostAdapter(MyPostsScreen.this, myposts);
                MypostRview.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}