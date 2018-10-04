package com.senior.project.genealogy.view.activity.genealogy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.search.SearchActivity;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;

public class GenealogyActivity extends AppCompatActivity implements GenealogyView, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genealogy);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_genealogy);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setTitle("My genealogies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view_genealogy);
        mNavigationView.setNavigationItemSelectedListener(this);


//        Bundle bundle = new Bundle();
//        bundle.putString("token", getToken());
        Fragment mFragment = new GenealogyFragment();
//        mFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.genealogy_container, mFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.search) {
            showActivity(SearchActivity.class);
        } else if (id == R.id.genealogies) {
            showActivity(GenealogyActivity.class);
        } else if (id == R.id.branches) {

        } else if (id == R.id.familyTree) {

        } else if (id == R.id.notification) {

        } else if (id == R.id.signout) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

//    private String getToken(){
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("token","");
//        return token;
//    }

}
