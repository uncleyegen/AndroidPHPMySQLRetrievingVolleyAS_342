package com.example.androidphpmysqlretrievingvolleyas_342;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fname, lname, email, phone, confirm_password, id_editText, passwordEditText;
    private Button mSignUp;

    // Volley variables
    private StringRequest mStringRequest;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = findViewById(R.id.fname_editText);
        lname = findViewById(R.id.lname_editText);
        email = findViewById(R.id.email_editText);
        phone = findViewById(R.id.phone_editText);
        confirm_password = findViewById(R.id.confirm_password_editText);
        mSignUp = findViewById(R.id.sign_up_button);
        id_editText = findViewById(R.id.user_id_editText);
        passwordEditText = findViewById(R.id.user_password_editText);



//        String variable for UI widget data

        final String firstName = fname.getText().toString();
        final String lastName = lname.getText().toString();
        final String useremail = email.getText().toString();
        final String userPhone = phone.getText().toString();
        String password = passwordEditText.getText().toString();
        String user_id = id_editText.getText().toString();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Add method to create a user

                createUser(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),id_editText.getText().toString(),phone.getText().toString(),passwordEditText.getText().toString());


            }
        });
    }

    private void createUser(final String fname, final String lname, final String email, final String id, final String phone, final String password){

        mRequestQueue = Volley.newRequestQueue(SignUpActivity.this);
        // Progress
        mSignUp.setText("Creating User...");

        mStringRequest = new StringRequest(Request.Method.POST, getBaseUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success.equals("1")) {


                        Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_LONG).show();
                        mSignUp.setText("Sign Up");
                    }

                } catch (JSONException e) {

                    Toast.makeText(SignUpActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    mSignUp.setText("Sign Up");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                mSignUp.setText("Sign Up");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("email",email);
                params.put("id_number",id);
                params.put("phone",phone);
                params.put("password",password);

                return params;
            }
        };


        mStringRequest.setShouldCache(false);
        mRequestQueue.add(mStringRequest);
    }


    private String getBaseUrl (){
        return "http://"+getResources().getString(R.string.machine_ip_address)+"/AndroidPHPMySQLRetrievingVolley__TechWithWalter_API/sign_up.php";
    }


}
