package com.example.abbes.whattsapp;


import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.whatsapp);
        ImageButton button1 = findViewById(R.id.viber);
        ImageButton button2 = findViewById(R.id.contact);


        button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            boolean installed = appInstalledOrNot("com.viber.voip");
            if (installed) {

                BlankFragment2 myfragment = new BlankFragment2();

                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frly, myfragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                Toast.makeText(MainActivity.this, "viber n'est pas installé", Toast.LENGTH_SHORT).show();
            }


        }
    }
});

button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            BlankFragment3 myfragment = new BlankFragment3();

            FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frly, myfragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
});
        button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (ContextCompat.checkSelfPermission(MainActivity.this,
                         Manifest.permission.READ_CONTACTS)
                         != PackageManager.PERMISSION_GRANTED) {
                     requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
                 }


               else{
                     boolean installed = appInstalledOrNot("com.whatsapp");
                     if (installed) {
                         BlankFragment myfragment = new BlankFragment();

                         FragmentManager fragmentManager = getFragmentManager();
                         android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                         transaction.replace(R.id.frly, myfragment);
                         transaction.addToBackStack(null);
                         transaction.commit();
                     } else {
                         Toast.makeText(MainActivity.this, "app n'est pas installé", Toast.LENGTH_SHORT).show();
                     }


                 }
             }
         });


    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
