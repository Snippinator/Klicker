package alexandru.ciocea;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter<T> extends ArrayAdapter  {
	
	FontClass fontClass = new FontClass();
	
	public MyArrayAdapter(Context context, List<T> items) {

        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, items);
    }

    public MyArrayAdapter(Context context, T[] items) {

        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        fontClass.setFont(textView);

        return view;
    }
}
