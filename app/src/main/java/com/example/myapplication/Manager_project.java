package com.example.myapplication;

import android.content.ContentUris;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Model.Teacher;
import com.example.myapplication.fragments.Setting.UserSettingFragment;
import com.example.myapplication.fragments.homework.FragmentHomeWork;
import com.example.myapplication.fragments.jingle.FragmentJingle;
import com.example.myapplication.fragments.jingle.JingleTypeFragment;
import com.example.myapplication.fragments.jingle.NavJingleFragment;
import com.example.myapplication.fragments.note.FragmentNote;
import com.example.myapplication.fragments.rozklad.FragmentRozklad;

import java.io.IOException;

import com.example.myapplication.Model.Subject;
import com.example.myapplication.data.DatabaseHandler;
import com.example.myapplication.fragments.teachers.FragmentTeacher;

public class Manager_project extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, JingleTypeFragment.OnArticleSelectedListener {
   private DatabaseHandler dbh;
   private FragmentRozklad frameRozklad;
   private FragmentHomeWork frameHomework;
   private FragmentNote frameNote;
   private UserSettingFragment settingFragment;
   private NavJingleFragment navJingleFragment;
   private FragmentTeacher fragmentTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        frameRozklad = new FragmentRozklad();
        frameHomework = new FragmentHomeWork();
        settingFragment = new UserSettingFragment();
        frameNote = new FragmentNote();
        navJingleFragment = new NavJingleFragment();
        fragmentTeacher = new FragmentTeacher();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-----------------------------------------------------------------------------------
        dbh=new DatabaseHandler(this);

       try{

         //  dbh.updateDataBase();
           dbh.createDataBase();
       }catch (IOException ex){
           Toast.makeText(this, "Unable to create database", Toast.LENGTH_SHORT).show();
           //throw new Error("Unable to create database");
           ex.printStackTrace();
       }
//        try{dbh.openDataBase();
//
//        }catch (SQLException sex){
//            throw sex;
//        }


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manager_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_rozklad) {
            transaction.replace(R.id.container,frameRozklad);

        } else if (id == R.id.nav_homework) {
            transaction.replace(R.id.container,frameHomework);

        } else if (id == R.id.nav_note) {
            transaction.replace(R.id.container,frameNote);

        } else if (id == R.id.nav_setting) {
            transaction.replace(R.id.container, settingFragment);
        } else if (id == R.id.nav_teacher) {
                transaction.replace(R.id.container,fragmentTeacher);


        } else /*if (id == R.id.test_bd) {//только для разработки

            Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
            startActivity(dbmanager);

        } else */if (id == R.id.nav_jingle) {
            transaction.replace(R.id.container,navJingleFragment);

        }
        transaction.commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onArticleSelected(Uri articleUri) {
        long typeId = ContentUris.parseId(articleUri);
        FragmentJingle fragment = FragmentJingle.newInstance(typeId);
        // fragment = new FragmentJingle();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_jingle,fragment).commit();
    }
}
