package com.senior.project.genealogy.view.activity.genealogy;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.activity.BaseActivity;
import com.senior.project.genealogy.view.activity.search.SearchActivity;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;

public class GenealogyActivity extends BaseActivity implements GenealogyView, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    /**
     * Apply Dagger Here
     * Create GenealogyModule, GenealogyComponent.
     */
    @Override
    public void distributedDaggerComponents() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_genealogy;
    }

    @Override
    protected void initAttributes() {
        mDrawerLayout = findViewById(R.id.drawer_genealogy);
        mNavigationView = findViewById(R.id.nav_view_genealogy);
        mNavigationView.setNavigationItemSelectedListener(this);

        Fragment mFragment = new GenealogyFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.genealogy_container, mFragment).commit();
        mDrawerLayout.addDrawerListener(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

}
