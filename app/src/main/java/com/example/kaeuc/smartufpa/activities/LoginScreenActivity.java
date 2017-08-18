package com.example.kaeuc.smartufpa.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.kaeuc.smartufpa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaeuc on 10/5/2016.
 */
public class LoginScreenActivity extends AppCompatActivity {
    public static final String CATEGORY_LOGIN = "smartufpa.CATEGORY_LOGIN";
    public static final String ACTION_LOGIN = "smartufpa.ACTION_LOGIN";
    public static final String TAG = LoginScreenActivity.class.getSimpleName();

    /*private EditText edtUsername;
    private EditText edtPassword;
    private Button btnCreateProfile;*/
    private Button btnLogin;
    private Button btnVisitante;
    //private LoginDAO loginDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate()");
        setContentView(R.layout.login_screen_activity);

        // Views
        /*edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnCreateProfile = (Button) findViewById(R.id.btn_signup);*/
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnVisitante = (Button) findViewById(R.id.btn_visitante);


        // Inicializa o banco de dados dos usuários em um thread para não comprometer a performance
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loginDAO = new LoginDAO(LoginScreenActivity.this);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SQLiteDatabase db = SQLiteDatabase.create(new SQLiteDatabase.CursorFactory() {
                                @Override
                                public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                                    return null;
                                }
                            });
                            loginDAO.onCreate(db);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();*/





    }

    @Override
    protected void onResume() {
        super.onResume();
        // Requisita permissões para Android Marshmallow e devices superiores
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }

        // Cria um Listener para os tres botões, e ele identificará qual botão foi clicado pela sua id
        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == btnLogin.getId()){
                    Toast.makeText(LoginScreenActivity.this, "Em breve essa função será adicionada.", Toast.LENGTH_SHORT).show();


                    // Código de login
                   /* String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    final int checkLogin = loginDAO.checkLogin(username, password);
                    if (checkLogin == 1){
                        Intent intent = new Intent(MapActivity.ACTION_MAP);
                        intent.addCategory(MapActivity.CATEGORY_MAP);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }else if(checkLogin == -1){
                        Toast.makeText(LoginScreenActivity.this, R.string.login_error_user, Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(LoginScreenActivity.this, R.string.login_error_noprofile, Toast.LENGTH_LONG).show();
                    }
                }else if(v.getId() == btnCreateProfile.getId()){
                    Intent intent = new Intent(CreateProfileActivity.ACTION_CREATEPROFILE);
                    intent.addCategory(CreateProfileActivity.CATEGORY_CREATEPROFILE);
                    startActivity(intent);*/
                }else if (v.getId() == btnVisitante.getId()) {
                    Intent intent = new Intent(AppMenuActivity.ACTION_APP_MENU);
                    intent.addCategory(AppMenuActivity.CATEGORY_APP_MENU);
                    startActivity(intent);


                }
            }
        };
        //btnCreateProfile.setOnClickListener(clickListener);
        btnLogin.setOnClickListener(clickListener);
        btnVisitante.setOnClickListener(clickListener);


    }

    // Inicio da checagem de permissões - Métodos retirados do exemplo dado no projeto do OSMdroid
    // OpenStreetMapView > Samples > ExtraSamples > SampleFollowMe
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } // else: We already have permissions, so handle as normal
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION and WRITE_EXTERNAL_STORAGE
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    // All Permissions Granted
                    Toast.makeText(this, "Todas as permissões necessárias foram cedidas.",
                            Toast.LENGTH_SHORT).show();
                } else if (location) {
                    Toast.makeText(this, "A permissão de armazenamento é necessária para armazenar " +
                                    "porções do mapa para reduzir consumo de dados e para uso offline.",
                            Toast.LENGTH_LONG).show();
                } else if (storage) {
                    Toast.makeText(this, "A permissão de localização é necessária para mostrar a sua " +
                            "localização no mapa.", Toast.LENGTH_LONG).show();
                } else { // !location && !storage case
                    // Permission Denied
                    Toast.makeText(this, "A permissão de armazenamento é necessária para armazenar " +
                            "porções do mapa para reduzir consumo de dados e para uso offline." +
                            "\n E a permissão de localização é necessária para mostrar a sua " +
                            "localização no mapa.", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        Log.i(TAG,"onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }
}