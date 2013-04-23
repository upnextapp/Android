package com.stg.inqueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	public interface Callback{
		void onComplete();
		void onFail();
	}
	
	public ArrayList<String> restaurantsArrayList;
	public ArrayAdapter<String> restaurantsAdapter;
	public OnItemClickListener listviewListener;
	private QueueLine queue;
	private String position;
	private static Network n;

	// private TaskListAdapter adapter;	
	// url to make request
	private static String url_getRestaurants = "http://ec2-54-244-184-198.us-west-2.compute.amazonaws.com/" +
			"api/list";
	private static String url_enterQueue = "http://ec2-54-244-184-198.us-west-2.compute.amazonaws.com/" +
			"enterQueue";
	
	//JSON node names
	private static String TAG_PHONE = "phone";
	private static String TAG_QUEUES = "queues";
	private static String TAG_ID = "uniqueID";
	private static String TAG_NAME= "name";
	
	//JSON array
	JSONArray business = null;
	
	//Business map
	static LinkedHashMap<String,String> businessMap = new LinkedHashMap<String, String>();
	
	//private TaskListAdapter adapter;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create an empty line.
		queue = new QueueLine("");

		// Set up initial lists of restaurants.
		setupRestaurantList();
		// fetch restaurants
		startAsyncTask();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	
	@Override
	protected void onRestart() {
		//grab restaurants from db and display it again
		startAsyncTask();
	}

	public void startAsyncTask(){
		n = new Network(new Callback(){
			
			@Override
			public void onComplete() {
				try {
					
					JSONObject jObject = n.get(100, TimeUnit.MILLISECONDS);
					Log.i("front_end","grabbed JSON Object");
					
					//make restaurant list
					try{
						if(jObject != null){
							business = jObject.getJSONArray(TAG_QUEUES);
							//has uniqueID and name
							for(int i=0; i < business.length();i++){
								JSONObject j = (JSONObject) business.getJSONObject(i);
								
								//add key, values of business
								String business_name = j.getString(TAG_NAME);
								String business_id = j.getString(TAG_ID);
								
								//put key, values to map
								//TODO: I might have to switch key, value
								businessMap.put(business_name, business_id);
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						//do nothing for now
						Log.d("front_end", businessMap.toString());
						//Toast.makeText(getApplicationContext(), "async done", Toast.LENGTH_SHORT).show();
						
						// Set up necessary tabs.
						setupTabs();
						
						// Set up initial lists of restaurants.
						setupRestaurantList();
						//setContentView(R.layout.main);
						
						mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

						// Set up the ViewPager with the sections adapter.
						mViewPager = (ViewPager) findViewById(R.id.pager);
						mViewPager.setAdapter(mSectionsPagerAdapter);

					}
				
				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.i("front_end","interrupt");
				} catch (ExecutionException e) {
					e.printStackTrace();
					Log.i("front_end","Execution");
				} catch (TimeoutException e) {
					e.printStackTrace();
					Log.i("front_end","timeout");
				}
			}

			@Override
			public void onFail() {
				// TODO: what should it do when it fails?
				
			}
			
		});
		
		//executes asynctask
		n.execute();
		
	}
	
	@Override
	public void onBackPressed() {
		// do nothing. won't go back to splash screen
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			// add setting method later
			// showPreferencesActivity();
			return true;
		case R.id.menu_about:
			// add about method later
			// logOff();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setupRestaurantList() {
		restaurantsArrayList = new ArrayList<String>();
		
		for(Map.Entry<String, String> e: businessMap.entrySet()){
			restaurantsArrayList.add(e.getKey());
		}
		
		Log.d("front_end", restaurantsArrayList.toString());
		 
		// Create an adapter to map the array list of restaurants to the list view.
		restaurantsAdapter = new ArrayAdapter<String>(this,
				R.layout.restaurant_row, restaurantsArrayList);
	}

	// set up the ActionBar's tabs
	private void setupTabs() {
		ActionBar queueActionBar = getActionBar(); // get the ActionBar
	}

	@SuppressLint("ValidFragment")
	public static class QueueDialogFragment extends DialogFragment {
		private String restaurantName;
		private QueueLine queue;

		public QueueDialogFragment() {
		}

		public void setRestaurantName(String name) {
			this.restaurantName = name;
		}

		public void setQueue(QueueLine q) {
			this.queue = q;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			setRetainInstance(true);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Click 'Yes' to queue for " + restaurantName + "!")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
						
								@Override
								public void onClick(DialogInterface dialog,	int which) {
									// If it is not the same as the previous
									// line, then make a new line and add in the
									// user (temporarily named Kevin).
									
									// TODO:this is where we make a JSON post request.
									if (queue.getName() != restaurantName) {
										queue = new QueueLine(restaurantName);
										queue.add("Kevin");
										
										//here is JSON post call
										SDCard sd = new SDCard();
										String phoneNumber = sd.getNumber();
										String uniqueID = getBusinssID(restaurantName);
										
										JSONObject jObject = new JSONObject();
										try{
											jObject.put(TAG_PHONE, phoneNumber);
											jObject.put(TAG_QUEUES,uniqueID);
										}catch(Exception e){
											e.printStackTrace();
										}
										/*
										JSONParser jsonParser = new JSONParser();
										try {
											jsonParser.postRequest(jObject, url_enterQueue);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										*/
									}
								}
							})
							
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).setTitle("Would you like get in line?");
			
			return builder.create();
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Bundle args = new Bundle();
			if (position == 0) {
				RestaurantListFragment rFragment = new RestaurantListFragment();
				rFragment.setAdapter(restaurantsAdapter);
				rFragment.setQueue(queue);
				rFragment.setRestaurantsArrayList(restaurantsArrayList);
				args.putInt(RestaurantListFragment.ARG_SECTION_NUMBER,
						position + 1);
				rFragment.setArguments(args);
				return rFragment;
			} else {
				PositionFragment pFragment = new PositionFragment();
				args.putInt(PositionFragment.ARG_SECTION_NUMBER, position + 2);
				pFragment.setArguments(args);
				return pFragment;
			}
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	@SuppressLint("ValidFragment")
	public static class RestaurantListFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private ArrayAdapter<String> adapter;
		private QueueLine queue;
		private ArrayList<String> restaurantsArrayList;

		public RestaurantListFragment() {
		}

		public void setAdapter(ArrayAdapter<String> a) {
			adapter = a;
		}

		public void setQueue(QueueLine q) {
			this.queue = q;
		}
		
		public void setRestaurantsArrayList(ArrayList<String> l) {
			this.restaurantsArrayList = l;
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			QueueDialogFragment qdf = new QueueDialogFragment();
			qdf.setRestaurantName(restaurantsArrayList.get(position));
			qdf.setQueue(queue);
			qdf.show(getActivity().getFragmentManager(), "Queue Prompt");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setRetainInstance(true);
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			ListView lv = (ListView) rootView.findViewById(android.R.id.list);
			
			/*
			 * CODE TO PARSE RESPONSE AND TRANSLATE TO ADAPTER HERE
			 */
			
			lv.setAdapter(adapter);
			return rootView;
		}
	}

	public static class PositionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private QueueLine queue;

		public PositionFragment() {
		}

		public void setQueue(QueueLine q) {
			this.queue = q;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setRetainInstance(true);
			View rootView = inflater.inflate(R.layout.position_in_line,
					container, false);
			TextView tv = (TextView) rootView.findViewById(R.id.position);
			
			/*
			 * CODE TO PARSE RESPONSE AND TRANSLATE TO DISPLAYABLE MESSAGE GOES HERE
			 */
			
			tv.setText("POSITION GOES HERE");
			return rootView;
		}
	}
	
	public static String getBusinssID(String businessName){
		if(businessMap.containsKey(businessName)){
			return businessMap.get(businessName);
		}
		return null;
	}
}
