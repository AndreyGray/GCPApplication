package candreyskakunenko.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Objects;

import candreyskakunenko.myapplication.model.Menu;

public class StartActivity extends AppCompatActivity {

   private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle runtime permissions
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED | checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED){
            //permission denied, request it
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            //show popup for runtime permission
            requestPermissions(permissions,PERMISSION_STORAGE_CODE);
        }else {
            //permission already granted, perform download
            
            startDownloading();

        }
        Intent intent = new Intent(this,NavActivity.class);
        startActivity(intent);
        finish();


    }


    private void startDownloading() {
        //url for request
        String url = "https://www.dropbox.com/s/fk3d5kg6cptkpr6/menu.json?dl=1";

        //create download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //allow types of network to download file
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");//set title to download notification
        request.setDescription("Downloading file....");//set description to download notification

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"gcp.json");

        //get downloading service and enqueue file
        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        Objects.requireNonNull(manager).enqueue(request);

        //readJSONFile();

    }

    //handle permission result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                //permission granted from popup, perform download
                startDownloading();
            } else {
                //permission denied from popup, show error message
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
