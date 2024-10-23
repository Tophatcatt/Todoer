package com.tophat.todo.tools;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tophat.todo.R;
import com.tophat.todo.database.Task;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Task> {
    private final Activity context;
    private int prev;
public ListAdapter(Activity context, ArrayList<Task> taskArr){
    super(context,R.layout.listitem , taskArr);
        
    
    this.context=context;
    
}
    
    @Override
    public int getCount() {
        return super.getCount();
    }
    
public View getView(int position, View view, ViewGroup parent) {
    LayoutInflater inflater=context.getLayoutInflater();
    View rowView=inflater.inflate(R.layout.listitem, null,true);
    //this code gets references to objects in the listview_row.xml file
    TextView nameTextField = (TextView) rowView.findViewById(R.id.nameText);
    TextView infoTextField = (TextView) rowView.findViewById(R.id.stuff);
   // View v = rowView.findViewById(R.id.listurg);
     LinLayID layout=(LinLayID)rowView.findViewById(R.id.ident);
    //this code sets the values of the objects to values from the arrays 
    nameTextField.setText(getItem(position).getTitle());
    infoTextField.setText(getItem(position).getDescription());
   // v.setBackgroundColor(taskArray.get(position).getUrgency());
    layout.id=getItem(position).getId();
    layout.row=position;
     Animation animation = AnimationUtils.loadAnimation(getContext(), (position > prev) ? R.anim.blink : R.anim.blinkin);
    rowView.startAnimation(animation);
    prev = position;

    return rowView;

}
}
