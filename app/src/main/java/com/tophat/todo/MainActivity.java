
package com.tophat.todo;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.tophat.todo.tools.LinLayID;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.tophat.todo.databinding.ActivityMainBinding;
import com.tophat.todo.database.Fetcher;
import com.tophat.todo.database.Task;
import android.view.View;
import com.tophat.todo.R;
import com.tophat.todo.tools.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
// TODO: make it so if there is nothing in database, the screen shows "No tasks at hand"

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Fetcher fetcher;
    private ListAdapter adapter;
   private TextView text;
   private ArrayList<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(R.layout.activity_main);
        fetcher=new Fetcher(this);
        tasks=fetcher.getAllTasks();
        text=(TextView)findViewById(R.id.emptytext);
        
        textControl();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
    public void putData() {
    	
        ListView lv=(ListView) findViewById(R.id.list);
        
//        for(Task t : tasks) {
//        	names.add(t.getTitle());
//        }
        tasks=fetcher.getAllTasks();
        adapter=new ListAdapter(MainActivity.this,tasks);
        lv.setAdapter(adapter);
        
//        lv.setOnItemClickListener(new OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            
//        }
//    });
    }
    public void showPopup(View view) {
    	LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setAnimationStyle(R.style.Animation);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button but=popupView.findViewById(R.id.saver);
       Spinner menu=populateMenu(popupView);
        but.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
                addEntry(
                  ((EditText)popupView.findViewById(R.id.newstf)).getText().toString(),
               menu.getSelectedItem().toString()
                );
            }
            
        });
        // dismiss the popup window when touched
    //startActivity(new Intent(this, Stuff.class));
    }
    public void addEntry(String title, String urgency){
        Integer urg;
        switch(urgency){
            case "Urgent":
            urg=R.color.Urgent;
            break;
            case "Normal":
            urg=R.color.Normal;
            break;
            default:
            urg=R.color.Unimportant;
            break;
        }
        fetcher.insertTask(title,"", urg);
        Task newtask=new Task(title, "", urg, fetcher.getLatestId());
        tasks.add(newtask);
        adapter.notifyDataSetChanged();
        if (tasks.size()==1){
            text.setVisibility(View.GONE);
        }
        
    }
    public Spinner populateMenu(View popup) {
    	Spinner dropdown = popup.findViewById(R.id.spinner1);
//create a list of items for the spinner.
String[] items = new String[]{"Urgent", "Normal", "Unimportant"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
dropdown.setAdapter(adapt);
        return dropdown;
    }
    public void deleteEntry(View v) {
        LinLayID layout=(LinLayID)(v.getParent());
    	fetcher.deleteTask(layout.id);
      // updater();
        
        
        tasks.removeIf(task -> task.getId()==layout.id);
        adapter.notifyDataSetChanged();
        if(tasks.size()==0){
            text.setVisibility(View.VISIBLE);
        }
    }
    
    public void update(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
    public void updater() {
    	tasks.clear();
        adapter.notifyDataSetChanged();
        textControl();
    }
    public void textControl() {
    	if (fetcher.numberOfRows()==0){
            text.setVisibility(View.VISIBLE);
        }
        
            putData();
    }
}
