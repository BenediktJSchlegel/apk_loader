package com.example.apk_loader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View centerButton = findViewById(R.id.center_button);
        View permissionButton = findViewById(R.id.permission_button);
        View installButton = findViewById(R.id.install_button);

        centerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){switchToOtherApp();}
        });

        permissionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){showAllPermissions();}
        });

        installButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){doStuff();}
        });
    }

    private void requestRuntimePermissions(){
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"READ_EXTERNAL_STORAGE"}, 111);
    }

    private void switchToOtherApp(){
        TextView text_field = findViewById(R.id.apk_input);

        Intent intent = getPackageManager().getLaunchIntentForPackage(text_field.getText().toString());
        startActivity(intent);
    }

    private void doStuff(){
        // Create Uri
        /*File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file1 = new File (downloads + "//payload.apk");//downloads.listFiles()[0];
        Uri contentUri1 = getUriForFile(this, BuildConfig.APPLICATION_ID, file1);
        // Intent to open apk
        Intent intent = new Intent(Intent.ACTION_VIEW, contentUri1);
        intent.setDataAndType(contentUri1, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);*/

        //Intent intent = new Intent(Intent.ACTION_VIEW);

       // File externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //Uri contentUri1 = getUriForFile(this, BuildConfig.APPLICATION_ID, externalStorage);
        //Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() , externalStorage );

      //  Uri u = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", externalStorage);

        //intent.setDataAndType(Uri.fromFile(new File
        //        ( u + "/payload.apk")), "application/vnd.android.package-archive");

        File newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/payload.apk");

        Uri ur = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", newFile);


        Intent promptInstall = new Intent(Intent.ACTION_VIEW).setDataAndType(ur , "application/vnd.android.package-archive");
        promptInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.startActivity(promptInstall);

        //startActivity(promptInstall);
    }

    private final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE=111;
    private final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE=222;
    private final int REQUEST_PERMISSION_REQUEST_INSTALL_PACKAGES=333;
    private final int REQUEST_PERMISSION_REQUEST_DELETE_PACKAGES=444;

    private void showAllPermissions(){
        showExternalStorageRead();
        showExternalStorageWrite();
        showRequestInstall();
        showRequestDelete();
    }

    private void showRequestDelete() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.REQUEST_DELETE_PACKAGES);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.REQUEST_DELETE_PACKAGES)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.REQUEST_DELETE_PACKAGES, REQUEST_PERMISSION_REQUEST_DELETE_PACKAGES);
            } else {
                requestPermission(Manifest.permission.REQUEST_DELETE_PACKAGES, REQUEST_PERMISSION_REQUEST_DELETE_PACKAGES);
            }
        } else {
            Toast.makeText(MainActivity.this, "DELETE (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRequestInstall() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.REQUEST_INSTALL_PACKAGES, REQUEST_PERMISSION_REQUEST_INSTALL_PACKAGES);
            } else {
                requestPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES, REQUEST_PERMISSION_REQUEST_INSTALL_PACKAGES);
            }
        } else {
            Toast.makeText(MainActivity.this, "INSTALL (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExternalStorageRead() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            Toast.makeText(MainActivity.this, "EXTERNAL READ (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExternalStorageWrite() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            } else {
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            Toast.makeText(MainActivity.this, "EXTERNAL WRITE (already) Granted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "READ EXTERNAL Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "READ EXTERNAL Denied!", Toast.LENGTH_SHORT).show();
                }

            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "WRITE EXTERNAL Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "WRITE EXTERNAL Denied!", Toast.LENGTH_SHORT).show();
                }

            case REQUEST_PERMISSION_REQUEST_DELETE_PACKAGES:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "DELETE Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "DELETE Denied!", Toast.LENGTH_SHORT).show();
                }

            case REQUEST_PERMISSION_REQUEST_INSTALL_PACKAGES:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "INSTALL Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "INSTALL Denied!", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }
}