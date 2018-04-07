package com.example.varadavamsi.femalesecurity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class setnumbers extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String number1 = "number1";
    public static final String number2 = "number2";
    public static final String number3 = "number3";
    public static final String number4 = "number4";

    EditText ed1,ed2,ed3,ed4;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnumbers);
        ed1=(EditText)findViewById(R.id.first);
        ed2=(EditText)findViewById(R.id.second);
        ed3=(EditText)findViewById(R.id.third);
        ed4=(EditText)findViewById(R.id.Final);
        b1=(Button)findViewById(R.id.set);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String n1 = sharedpreferences.getString(number1,"");
        String n2 = sharedpreferences.getString(number2,"");
        String n3 = sharedpreferences.getString(number3,"");
        String n4 = sharedpreferences.getString(number4,"");

        ed1.setText(n1, TextView.BufferType.EDITABLE);
        ed2.setText(n2, TextView.BufferType.EDITABLE);
        ed3.setText(n3, TextView.BufferType.EDITABLE);
        ed4.setText(n4, TextView.BufferType.EDITABLE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first  = ed1.getText().toString();
                String second = ed2.getText().toString();
                String third  = ed3.getText().toString();
                String fourth  = ed4.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(number1, first);
                editor.putString(number2, second);
                editor.putString(number3, third);
                editor.putString(number4, fourth);
                editor.commit();
                Toast.makeText(setnumbers.this,"number set successfully",Toast.LENGTH_LONG).show();

            }
        });

    }
}
