package com.sarps.alumni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sarps.alumni.fragment.About_usFragment;
import com.sarps.alumni.fragment.AccountFragment;
import com.sarps.alumni.fragment.AchievementsFragment;
import com.sarps.alumni.fragment.Connections;
import com.sarps.alumni.fragment.EducationFragment;
import com.sarps.alumni.fragment.ExperienceFragment;
import com.sarps.alumni.fragment.HomePageFragment;
import com.sarps.alumni.fragment.MyProfileFragment;
import com.sarps.alumni.fragment.RaiseFundsFragment;
import com.sarps.alumni.fragment.RaiseFundsListFragments;
import com.sarps.alumni.fragment.SavedRequestFragments;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private static Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    String username, profile_pic;

    LinearLayout ll_header;
    TextView tv_username, tv_email;
    ImageView iv_profile_image;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment f1 = new HomePageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f1).commit();
        //Initializing NavigationView

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        ll_header = (LinearLayout) header.findViewById(R.id.ll_header);
        tv_username = (TextView) header.findViewById(R.id.tv_username);
        tv_email = (TextView) header.findViewById(R.id.tv_email);
        iv_profile_image = (ImageView) header.findViewById(R.id.iv_profile_image);

        String email = pref.getString("email", "DEFAULT");
        username = pref.getString("username", "DEFAULT");
        profile_pic = pref.getString("profile_pic", "DEFAULT");
        tv_username.setText(username);
        tv_email.setText(email);
        Picasso.with(getApplicationContext()).load(profile_pic).into(iv_profile_image);
        System.out.println("profile_pic :-" + profile_pic);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        String role = pref.getString("role", "DEFAULT");
        System.out.println("role :- " + role);
        if (role.equals("exstud")) {
            hideItem_saved();
            hideItem_raise();
        } else {
            hideItem_raiselist();
        }
//        if (role.equals("stud")) {
//            hideItem_experience();
//        }
        visibletootlbar();
        ll_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new HomePageFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).commit();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_profile:
                        getSupportActionBar().setTitle("My Profile");
                        Fragment profg = new MyProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, profg).commit();
                        return true;

                    case R.id.nav_mentor:
                        navigationView.setItemBackgroundResource(R.color.grey);
                        getSupportActionBar().setTitle("Connections");
                        Fragment f1 = new Connections();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f1).commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.nav_raise_funds:
                        getSupportActionBar().setTitle("Raise Money");
                        Fragment f2 = new RaiseFundsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f2).commit();
                        return true;
                    case R.id.nav_raise_funds_list:
                        getSupportActionBar().setTitle("Raise Money");
                        Fragment f3 = new RaiseFundsListFragments();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f3).commit();
                        return true;

                    case R.id.nav_acnt_transaction:
                        getSupportActionBar().setTitle("Account");
                        Fragment f4 = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f4).commit();
                        return true;
                    case R.id.nav_education:
                        getSupportActionBar().setTitle("Education");
                        Fragment f5 = new EducationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f5).commit();
                        return true;

                    case R.id.nav_achieve:
                        getSupportActionBar().setTitle("Achievements");
                        Fragment f6 = new AchievementsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f6).commit();
                        return true;
                    case R.id.nav_experience:
                        getSupportActionBar().setTitle("Experience");
                        Fragment f8 = new ExperienceFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f8).commit();
                        return true;
                    case R.id.nav_about_us:
                        getSupportActionBar().setTitle("About Us");
                        Fragment f9 = new About_usFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f9).commit();
                        return true;
                    case R.id.nav_saved_request:
                        getSupportActionBar().setTitle("Saved Requests");
                        Fragment f10 = new SavedRequestFragments();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, f10).commit();
                        return true;

                    case R.id.nav_Logout:
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
//                        finish();
                        startActivity(new Intent(getApplicationContext(), DiyApproachActivity.class));
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    public static void hidetootlbar() {
        toolbar.setVisibility(View.GONE);
    }

    public static void visibletootlbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    private void hideItem_raise() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_raise_funds).setVisible(false);
    }
    private void hideItem_raiselist() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_raise_funds_list).setVisible(false);
    }

    private void hideItem_saved() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_saved_request).setVisible(false);
    }

    private void hideItem_experience() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_experience).setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }


    }
}
