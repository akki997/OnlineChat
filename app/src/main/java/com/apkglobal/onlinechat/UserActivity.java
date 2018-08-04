package com.apkglobal.onlinechat;
import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.util.ArrayList;
        import java.util.Iterator;
public class UserActivity extends AppCompatActivity {
    ListView lv;

    ArrayList<String>myarrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lv= (ListView)findViewById(R.id.lv);


        String url = "https://chatapp-2429a.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(UserActivity.this);
        rQueue.add(request);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith= myarrayList.get(position);
                startActivity(new Intent(UserActivity.this, ChatActivity.class));
            }
        });
    }
    public void doOnSuccess(String s){
        try{
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String mykey = "";
            while(i.hasNext()){
                mykey = i.next().toString();
                if(!mykey.equals(UserDetails.username)) {
                    myarrayList.add(mykey);
                    lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myarrayList));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

