package com.apkglobal.onlinechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText etuser,etpass;
    Button btn1;
    TextView tv1;
    String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etuser=(EditText)findViewById(R.id.etuser);
        etpass=(EditText)findViewById(R.id.etpass);
        Firebase.setAndroidContext(this);
        btn1=(Button)findViewById(R.id.btn1);
        tv1=(TextView)findViewById(R.id.tv1);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=etuser.getText().toString();
                pass=etpass.getText().toString();
                String url="https://chatapp-2429a.firebaseio.com/users.json";
                StringRequest stringRequest=new StringRequest(0, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Firebase reference = new Firebase("https://chatapp-2429a.firebaseio.com/users");
                        try{
                            JSONObject obj=new JSONObject(response);
//
//                                    if user is not present then create new user
                            if(!obj.has(user)) {
                                reference.child(user).child("password").setValue(pass);
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
//                                        check if user is already present then make toast
                            } else {
                                Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}
