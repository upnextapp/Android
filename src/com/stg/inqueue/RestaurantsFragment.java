package com.stg.inqueue;

import java.util.ArrayList;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestaurantsFragment extends ListFragment {
	public ArrayList<String> restaurantsArrayList;
	public ArrayAdapter<String> restaurantsArrayAdapter;
	// called when the parent Activity is created
	@Override
	public void onActivityCreated(Bundle savedInstanceStateBundle) {
		super.onActivityCreated(savedInstanceStateBundle);

		// create ArrayList to save city names
		restaurantsArrayList = new ArrayList<String>();
		
		restaurantsArrayList.add("iHop");
		restaurantsArrayList.add("Olive Garden");
		restaurantsArrayList.add("Cheescake Factory");

		// set the Fragment's ListView adapter
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.restaurants_list, restaurantsArrayList));

		ListView thisListView = getListView(); // get the Fragment's ListView
		restaurantsArrayAdapter = (ArrayAdapter<String>) getListAdapter();

		// allow only one city to be selected at a time
		thisListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		thisListView.setBackgroundColor(Color.WHITE); // set background color
	} // end method onActivityCreated
}
