package com.example.design.restoffowner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout=findViewById(R.id.facebookshimmer);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        bottomNavigationView=findViewById(R.id.main_bottomnavigation);
        databaseReference.child("OwnerUser").child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       if(dataSnapshot.child("user_type").getValue().toString().equals("Stationary"))
                       {
                            bottomNavigationView.inflateMenu(R.menu.stationarymenu);
                           getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Stationary_home()).commit();
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }
                       else if(dataSnapshot.child("user_type").getValue().toString().equals("Restaurant"))
                       {
                           bottomNavigationView.inflateMenu(R.menu.restaurantmenu);
                           getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Restaurant_home()).commit();
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }
                       else if(dataSnapshot.child("user_type").getValue().toString().equals("BeautyParlour"))
                       {
                           bottomNavigationView.inflateMenu(R.menu.beautyparlourmenu);
                           getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Beautyparlour_home()).commit();
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }
                       else if(dataSnapshot.child("user_type").getValue().toString().equals("ClothShop"))
                       {
                           bottomNavigationView.inflateMenu(R.menu.clothmenu);
                           getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Clothshop_home()).commit();
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }
                       else if(dataSnapshot.child("user_type").getValue().toString().equals("Gym"))
                       {
                           bottomNavigationView.inflateMenu(R.menu.gymmenu);
                          getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,new Gym_home()).commit();
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }
                       else {
                           Toast.makeText(MainActivity.this, "Some thing is wrong Login Again", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(MainActivity.this,User_loginactivity.class));
                           shimmerFrameLayout.stopShimmer();
                           shimmerFrameLayout.setVisibility(View.GONE);
                           bottomNavigationView.setVisibility(View.VISIBLE);
                       }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "jnsjnsjndjnsd", Toast.LENGTH_SHORT).show();
                    }
                });


        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment setFragment=null;
                    switch (menuItem.getItemId()) {
                        case R.id.stationary_home:
                            setFragment = new Stationary_home();
                            break;
                        case R.id.stationary_additeam:
                            setFragment = new Stationary_additeam();
                            break;
                        case R.id.stationary_requestofcoopns:
                            setFragment = new Stationary_requestforcoopn();
                            break;
                        case R.id.restaurant_home:
                            setFragment = new Restaurant_home();
                            break;
                        case R.id.restaurant_additeam:
                            setFragment = new Restaurant_additeam();
                            break;
                        case R.id.restaurant_requestofcoopns:
                            setFragment = new Restaurant_requestforcoopn();
                            break;
                        case R.id.beautyparlour_home:
                            setFragment = new Beautyparlour_home();
                            break;
                        case R.id.beautyparlour_additeam:
                            setFragment = new Beautyparlour_additeam();
                            break;
                        case R.id.beautyparlour_requestofcoopns:
                            setFragment = new Beautyparlour_requestforcoopn();
                            break;
                        case R.id.gym_home:
                            setFragment = new Gym_home();
                            break;
                        case R.id.gym_additeam:
                            setFragment = new Gym_additeam();
                            break;
                        case R.id.gym_requestofcoopns:
                           setFragment = new Gym_requestforcoopn();
                            break;
                        case R.id.cloth_home:
                            setFragment = new Clothshop_home();
                            break;
                        case R.id.cloth_additeam:
                            setFragment = new Clothshop_additeam();
                            break;
                        case R.id.cloth_requestofcoopns:
                            setFragment = new Clothshop_requestforcoopn();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_framlayout,setFragment).commit();


                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.userprofile :
                startActivity(new Intent(MainActivity.this,UserProfile.class));
                finish();
                break;
            case R.id.setting:
                break;
            case R.id.logout:
              firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this,User_loginactivity.class));
                break;
        }
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
}
