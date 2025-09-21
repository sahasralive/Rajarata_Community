package com.thedev.rajaratacommunity.Frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Adapters.NotificationAdapter;
import com.thedev.rajaratacommunity.Models.NotificationData;
import com.thedev.rajaratacommunity.NotificationPageActivity;
import com.thedev.rajaratacommunity.R;

import java.util.ArrayList;


public class NotiFrag extends Fragment {
    RecyclerView notiRview;
    ArrayList<NotificationData> notifications=new ArrayList<>();

    public NotiFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_noti, container, false);
        notiRview=v.findViewById(R.id.notiRview);
        notiRview.setHasFixedSize(true);
        loadData();
        return v;
    }

    private void loadData() {
        notiRview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseDatabase.getInstance().getReference("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!notifications.isEmpty()){
                    notifications.clear();
                }
                for (DataSnapshot ds:snapshot.getChildren()){
                    NotificationData data=ds.getValue(NotificationData.class);
                    notifications.add(data);
                }
                NotificationAdapter adapter=new NotificationAdapter(getContext(),notifications);
                notiRview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}