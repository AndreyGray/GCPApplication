package candreyskakunenko.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import candreyskakunenko.myapplication.Adapter.DrawerAdapter;
import candreyskakunenko.myapplication.Helper.FragmentNavigationManager;
import candreyskakunenko.myapplication.Interface.NavigationManager;
import candreyskakunenko.myapplication.model.Menu;

import static candreyskakunenko.myapplication.Helper.ConnectionChecker.isNetworkAvailable;

public class NavActivity extends AppCompatActivity {

    List<Menu> mMenuList;

    private static final int PERMISSION_STORAGE_CODE = 1000;

    RecyclerView rvDrawerMenu;
    DrawerAdapter mDrawerAdapter;
    NavigationManager mNavigationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED | checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED){
            //permission denied, request it
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            //show popup for runtime permission
            requestPermissions(permissions,PERMISSION_STORAGE_CODE);

        }else {
            if(isNetworkAvailable(NavActivity.this)) {

                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

                loadDrawerMenuItems();

                DrawerLayout drawer = findViewById(R.id.drawer_layout);

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                mNavigationManager = FragmentNavigationManager.getmInstance(this);
                if (savedInstanceState == null && mMenuList.size() > 0)
                    selectFirstItemAsDefault();
                //mModel = mModel.get();
                //mMenuList = mModel.getMenu();
            }else {
                mNavigationManager = FragmentNavigationManager.getmInstance(this);
                mNavigationManager.showFragment("error","error");
            }
        }

    }

    private void loadDrawerMenuItems() {
        rvDrawerMenu = findViewById(R.id.nav_recyclerView);
        rvDrawerMenu.setLayoutManager(new LinearLayoutManager(this));
        rvDrawerMenu.setHasFixedSize(true);
        mDrawerAdapter = new DrawerAdapter(mMenuList, NavActivity.this);
        rvDrawerMenu.setAdapter(mDrawerAdapter);
        mMenuList = loadDataTemp();
        mDrawerAdapter.addDrawerMenuList(mMenuList);

    }

    private List<Menu> loadDataTemp() {

            List<Menu> menus= new ArrayList<>();
            try {
                final File menuFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"gcp.json");
                FileInputStream inputStream = new FileInputStream(menuFile);
                String jsonStr = null;
                try {
                    FileChannel fileChannel = inputStream.getChannel();
                    MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

                    jsonStr = Charset.defaultCharset().decode(byteBuffer).toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    inputStream.close();

                }

                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting data JSON Array nodes
                JSONArray menu = jsonObj.getJSONArray("menu");



                // looping through All nodes
                for (int i=0; i<menu.length();i++){
                    JSONObject itemJson = menu.getJSONObject(i);

                    String name = itemJson.getString("name");
                    String function = itemJson.getString("function");
                    String param = itemJson.getString("param");

                    Menu item = new Menu(name,function,param);
                    menus.add(item);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return menus;


    }

    private void selectFirstItemAsDefault() {
        if (mNavigationManager!=null)
        {
            String firstItem = mMenuList.get(0).getName();
            mNavigationManager.showFragment(mMenuList.get(0).getFunction(),mMenuList.get(0).getParam());
            getSupportActionBar().setTitle(firstItem);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onItemSelected(candreyskakunenko.myapplication.model.Menu item) {
        String name = item.getName();
        String func = item.getFunction();
        String param = item.getParam();
        if (mNavigationManager!=null) {
            mNavigationManager.showFragment(func, param);
            getSupportActionBar().setTitle(name);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                //permission granted from popup, perform download
                Intent intent = new Intent(this,StartActivity.class);
                startActivity(intent);
                finish();
            } else {
                //permission denied from popup, show error message
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
