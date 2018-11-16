package jhu.oose.group18.foodmate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //ato store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;

    private int layout;

    public ListAdapter(Activity context, int layout, String[] nameArrayParam, String[] infoArrayParam, Integer[] imageIDArrayParam){

        super(context, layout, nameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;
        this.layout = layout;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(layout, null,true);

        if (layout == R.layout.restaurant_row){
            TextView nameTextField = (TextView) rowView.findViewById(R.id.main_name);
            TextView infoTextField = (TextView) rowView.findViewById(R.id.main_category);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.restaurant_logo);

            //this code sets the values of the objects to values from the arrays
            nameTextField.setText(nameArray[position]);
            infoTextField.setText(infoArray[position]);
            imageView.setImageResource(imageIDarray[position]);
        }else if(layout == R.layout.message_row){
            TextView nameTextField = (TextView) rowView.findViewById(R.id.mssg_username);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.user_logo);

            //this code sets the values of the objects to values from the arrays
            nameTextField.setText(nameArray[position]);
            imageView.setImageResource(imageIDarray[position]);
        }else if(layout == R.layout.recommendation_row){
            TextView nameTextField = (TextView) rowView.findViewById(R.id.rec_name);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.rec_logo);

            //this code sets the values of the objects to values from the arrays
            nameTextField.setText(nameArray[position]);
            imageView.setImageResource(imageIDarray[position]);
//            TextView nameTextField = (TextView) rowView.findViewById(R.id.mssg_username);
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.user_logo);
//
//            //this code sets the values of the objects to values from the arrays
//            nameTextField.setText(nameArray[position]);
//            imageView.setImageResource(imageIDarray[position]);
        }


        return rowView;

    };



}
