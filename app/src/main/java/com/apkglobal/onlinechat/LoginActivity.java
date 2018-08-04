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

public class LoginActivity extends AppCompatActivity {
Button btn;
EditText et1,et2;
TextView tv;
String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=(Button)findViewById(R.id.btn);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        tv=(TextView)findViewById(R.id.tv);
        Firebase.setAndroidContext(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = et1.getText().toString();
                pass = et2.getText().toString();
                String url = "https://chatapp-2429a.firebaseio.com/users.json";

                StringRequest stringRequest=new StringRequest(0, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.has(user)) {
                                Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
/*checking user and pass from server
* put user and pass into userDetails class
* */
                            else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                UserDetails.username= user;
                                UserDetails.password= pass;
                                startActivity(new Intent(LoginActivity.this, UserActivity.class));
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }

        });
    }
}
