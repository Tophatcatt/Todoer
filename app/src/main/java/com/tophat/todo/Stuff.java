package com.tophat.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.tophat.todo.MainActivity;
public class Stuff extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stuff);
       findViewById(R.id.butt).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                loly(arg0);
            }
            
        });
    }
    public void loly(View v) {
    	Intent i=new Intent(this, MainActivity.class);
        Button b=(Button)v;
        b.setText("Clocked");
        startActivity(i);
    }
}
