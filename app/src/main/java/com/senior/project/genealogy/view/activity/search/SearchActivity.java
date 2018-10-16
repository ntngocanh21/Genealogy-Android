package com.senior.project.genealogy.view.activity.search;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.activity.genealogy.GenealogyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView, DrawerLayout.DrawerListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.actionBar)
    AppBarLayout mActionBar;

    @BindView(R.id.drawer_search)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view_search)
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        supportActionBar();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void supportActionBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(this);
        toggle.syncState();
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

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
