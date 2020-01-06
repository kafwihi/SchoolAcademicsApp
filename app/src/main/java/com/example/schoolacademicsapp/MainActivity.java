package com.example.schoolacademicsapp;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.schoolacademicsapp.fragments.home;

import com.example.schoolacademicsapp.fragments.register_studentcopy;

import com.example.schoolacademicsapp.fragments.view;

import com.example.schoolacademicsapp.R;
import com.example.schoolacademicsapp.helper.SQLiteHandler;
import com.example.schoolacademicsapp.helper.SessionManager;

import com.example.schoolacademicsapp.fragments.settings;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;


import android.view.MenuInflater;
import android.view.Menu;

import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
public class MainActivity extends AppCompatActivity {
private DrawerLayout myDrawer;
private ActionBarDrawerToggle toggle;
private Fragment myFragment;
private Class fragClass;


    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDrawer = (DrawerLayout) findViewById(R.id.drawer);
       toggle = new ActionBarDrawerToggle(this, myDrawer, R.string.open,R.string.close);
        myDrawer.addDrawerListener(toggle);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navView);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
    }


    @Override
public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
//  int id = item.getItemId();
    if(toggle.onOptionsItemSelected(item)){
        return true;
    }
    else
    if (item.getItemId() == R.id.logout) {

        logoutUser();
        Toast.makeText(this, "GoodBye", Toast.LENGTH_SHORT).show();
        return true;

    }
    return super.onOptionsItemSelected(item);
    }
/*
*  @Override
    public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()){
        case R.id.logout:
            Toast.makeText(this, "option 1 selected", Toast.LENGTH_SHORT).show();
                    return true;

        case R.id.logout2:
            Toast.makeText(this, "option 2 selected", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);

    }
    }

*/

    public void selectItemDrawer(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.home:
                fragClass = home.class;
                break;
            case R.id.settings:
                fragClass = settings.class;
                break;
            case R.id.register_student:
                fragClass = register_studentcopy.class;
                break;
            case R.id.view:
                fragClass = view.class;
                break;
            default:
                fragClass = home.class;
        }

        try{
              myFragment = (Fragment) fragClass.newInstance();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        FragmentManager fragm = getSupportFragmentManager();
        fragm.beginTransaction().replace(R.id.content,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        myDrawer.closeDrawers();
    }

private void setupDrawerContent(NavigationView nav){
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item){
            selectItemDrawer(item);
            return true;
        }
    });
}

    private void logoutUser() {
        session.setLogin(false);
        session.Logout();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
