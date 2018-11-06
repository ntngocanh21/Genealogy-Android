package com.senior.project.genealogy.view.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.BaseActivity;
import com.senior.project.genealogy.view.activity.login.LoginActivity;
import com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment.DetailBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment.BranchFragment;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;
import com.senior.project.genealogy.view.fragment.familyTree.ShowFamilyTreeFragment.FamilyTreeFragment;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragment;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;
import com.senior.project.genealogy.view.fragment.search.SearchFragment.SearchFragment;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_home)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view_home)
    NavigationView mNavigationView;


    /**
     * Apply Dagger Here
     * Create GenealogyModule, GenealogyComponent.
     */
    @Override
    public void distributedDaggerComponents() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected void initAttributes() {
        updateTitleBar("Search");
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(this);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        Fragment mFragment = new SearchFragment();
        pushFragment(PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.home_container);
    }

    public enum PushFrgType {
        REPLACE, ADD
    }

    public void pushFragment(PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            if (type == PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
                ft.disallowAddToBackStack();
                ft.commitAllowingStateLoss();
            } else if (type == PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.disallowAddToBackStack();
                ft.commit();
            }
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.search) {
            Fragment mFragment = new SearchFragment();
            pushFragment(PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.home_container);
        } else if (id == R.id.genealogies) {
            Fragment mFragment = new GenealogyFragment();
            pushFragment(PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.home_container);
        } else if (id == R.id.branches) {
            Fragment mFragment = new BranchFragment();
            pushFragment(PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.home_container);
        } else if (id == R.id.familyTree) {
            Fragment mFragment = new FamilyTreeFragment();
            pushFragment(PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.home_container);
        } else if (id == R.id.notification) {
//            DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance("");
//            dialogNodeFragment.show(getSupportFragmentManager(), null);
        } else if (id == R.id.signout) {
            saveAccount("", "");
            showActivity(LoginActivity.class);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateTitleBar(String title){
        mToolbar.setTitle(title);
    }

    public String getCurrentTitleBar() {
        if (mToolbar != null)
            return mToolbar.getTitle().toString();
        return "";
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        //getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    /**
     * When click button Back. Event click work in Activity. So it means GenealogyActivity is finished.
     * So finish every nested fragment. For example: MapFragment.
     * We will use interface to listen event click Back in Activity and handle it in nested Fragment.
     */

    public interface HomeInterface {
        boolean isExistedNestedFrag();
    }

    private HomeInterface mHomeInterface;

    public void attachFragInterface(HomeInterface _interface) {
        mHomeInterface = _interface;
    }

//    @Override
//    public void onBackPressed() {
//        if(!mHomeInterface.isExistedNestedFrag()) {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            for(int i = 0; i<fm.getFragments().size(); i++){
                if(fm.getFragments().get(i) instanceof DetailGenealogyFragment){
                    updateTitleBar(getString(R.string.frg_view_genealogy));
                }
                if(fm.getFragments().get(i) instanceof GenealogyFragment){
                    updateTitleBar(getString(R.string.frg_my_genealogy));
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void saveAccount(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_KEY.USERNAME,username);
        editor.putString(Constants.SHARED_PREFERENCES_KEY.PASSWORD,password);
        editor.apply();
    }
}
