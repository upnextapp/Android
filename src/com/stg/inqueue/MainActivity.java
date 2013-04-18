package com.stg.inqueue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMob;
import com.stackmob.sdk.api.StackMobOptions;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;
//import com.stg.inqueue.Main_Activity.QueueDialogFragment;

	
public class MainActivity extends FragmentActivity {
	public ArrayList<String> restaurantsArrayList;
	public ArrayAdapter<String> restaurantsAdapter;
	public OnItemClickListener listviewListener;
	private QueueLine queue;
	private String position;
	private User user;
	//protected static final String TASKLIST_KEY = "task_list";
	//protected static final String TASKLIST_RETURN_KEY = "modified_task_list";
	//protected static final String TASKLIST_INDEX = "task_list_index";
	protected static final String LOGGED_IN_USER = "logged_in_user";

	//private TaskListAdapter adapter;

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		StackMobAndroid.init(getApplicationContext(), 0, "f66ba52f-9d96-47a6-97ad-ec4bc95e9687");
		StackMob.getStackMob().getSession().getLogger().setLogging(true);
		//addTaskListButton = (Button) this.findViewById(R.id.add_tasklist_button);
		//addTaskListName = (TextView) this.findViewById(R.id.add_tasklist_text);
		
		// Create an empty line.
		queue = new QueueLine("");
		
		// Create an empty line.
		queue = new QueueLine("");

		setContentView(R.layout.main);
		StackMobAndroid.init(getApplicationContext(), 0,
				"f66ba52f-9d96-47a6-97ad-ec4bc95e9687");

		
		
		// Set up initial lists of restaurants.
		//setupRestaurantList();
		
		// Set up necessary tabs.
		//setupTabs();
		
		if(StackMob.getStackMob().isLoggedIn()) {
			User.getLoggedInUser(User.class, StackMobOptions.depthOf(2), new StackMobQueryCallback<User>() {
				@Override
				public void success(List<User> list) {
					user = list.get(0);
				}

				@Override
				public void failure(StackMobException e) {
					doLogin();
				}
			});
		} else {
			doLogin();
		}

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// testing purpose
		// Toast.makeText(this, getDeviceID(), Toast.LENGTH_LONG).show();
		// Toast.makeText(this, getPhoneNumber(), Toast.LENGTH_LONG).show();
		Task myTask = new Task("Learn more about StackMob", new Date());
		myTask.save();
	}
	
	private void doLogin() {
    	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
    	startActivityForResult(i, 1);
	}
	
	
	private void setupRestaurantList() {
		restaurantsArrayList = new ArrayList<String>();

		// TODO: Make a separate method to add restaurants (possibly through
		// the Internet?) and save the list so that it doesn't continually add
		// it to the list.
		restaurantsArrayList.add("Olive Garden");
		restaurantsArrayList.add("Cheescake Factory");
		restaurantsArrayList.add("Perry's Steakhouse");
		restaurantsArrayList.add("Fogo de Chao");

		// Create an adapter to map the array list of restaurants to the list
		// view.
		restaurantsAdapter = new ArrayAdapter<String>(this,
				R.layout.restaurant_row, restaurantsArrayList);

		// Implement listener for the items on the list view.
		listviewListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// Toast.makeText(getApplicationContext(),
				// restaurantsArrayList.get(position), Toast.LENGTH_SHORT)
				// .show();

				// Display a dialog and ask the user to queue for that
				// particular restaurant.
				// TODO: Make sure that you are in queue for only one line at
				// any given time.
				QueueDialogFragment qdf = new QueueDialogFragment();
				qdf.setRestaurantName(restaurantsArrayList.get(position));
				qdf.setQueue(queue);
				qdf.show(getFragmentManager(), "Queue Prompt");
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String getDeviceID() {
		/*
		 * //String Return_DeviceID =
		 * USERNAME_and_PASSWORD.getString(DeviceID_key,"Guest"); //return
		 * Return_DeviceID;
		 * 
		 * TelephonyManager TelephonyMgr = (TelephonyManager)
		 * getApplicationContext
		 * ().getApplicationContext().getSystemService(Context
		 * .TELEPHONY_SERVICE); String m_szImei = TelephonyMgr.getDeviceId(); //
		 * Requires // READ_PHONE_STATE
		 * 
		 * // 2 compute DEVICE ID String m_szDevIDShort = "35" + // we make this
		 * look like a valid IMEI Build.BOARD.length() % 10 +
		 * Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 +
		 * Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 +
		 * Build.HOST.length() % 10 + Build.ID.length() % 10 +
		 * Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 +
		 * Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 +
		 * Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13 digits //
		 * 3 android ID - unreliable String m_szAndroidID =
		 * Secure.getString(getContentResolver(),Secure.ANDROID_ID); // 4 wifi
		 * manager, read MAC address - requires //
		 * android.permission.ACCESS_WIFI_STATE or comes as null WifiManager wm
		 * = (WifiManager)
		 * getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		 * String m_szWLANMAC = wm.getConnectionInfo().getMacAddress(); // 5
		 * Bluetooth MAC address android.permission.BLUETOOTH required
		 * BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth
		 * adapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		 * String m_szBTMAC = m_BluetoothAdapter.getAddress();
		 * System.out.println("m_szBTMAC "+m_szBTMAC);
		 * 
		 * // 6 SUM THE IDs String m_szLongID = m_szImei + m_szDevIDShort +
		 * m_szAndroidID+ m_szWLANMAC + m_szBTMAC;
		 * System.out.println("m_szLongID "+m_szLongID); MessageDigest m = null;
		 * try { m = MessageDigest.getInstance("MD5"); } catch
		 * (NoSuchAlgorithmException e) { e.printStackTrace(); }
		 * m.update(m_szLongID.getBytes(), 0, m_szLongID.length()); byte
		 * p_md5Data[] = m.digest();
		 * 
		 * String m_szUniqueID = new String(); for (int i = 0; i <
		 * p_md5Data.length; i++) { int b = (0xFF & p_md5Data[i]); // if it is a
		 * single digit, make sure it have 0 in front (proper // padding) if (b
		 * <= 0xF) m_szUniqueID += "0"; // add number to string m_szUniqueID +=
		 * Integer.toHexString(b); } m_szUniqueID = m_szUniqueID.toUpperCase();
		 * 
		 * Log.i("-------------DeviceID------------", m_szUniqueID);
		 * Log.d("DeviceIdCheck",
		 * "DeviceId that generated MPreferenceActivity:"+m_szUniqueID);
		 * 
		 * 
		 * //System.out.println(m_szUniqueID); return m_szUniqueID;
		 * 
		 * }
		 */

		// for prototype purposes, this is fine
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();

		return deviceId;

	}

	public String getPhoneNumber() {

		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmNumber;

		tmNumber = "" + tm.getLine1Number();
		return tmNumber.toString();

	}
//
//	// set up the ActionBar's tabs
//	private void setupTabs() {
//		ActionBar queueActionBar = getActionBar(); // get the ActionBar
//
//		// set ActionBar's navigation mode to use tabs
//		queueActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//		Tab restaurantsTab = queueActionBar.newTab();
//
//		// set the Tab's title
//		restaurantsTab.setText("Restaurants");
//
//		// add the Tab
//		restaurantsTab.setTabListener(queueTabListener);
//
//		// set the Tab's listener
//		queueActionBar.addTab(restaurantsTab);
//
//		Tab positionTab = queueActionBar.newTab();
//		positionTab.setText("Position");
//		positionTab.setTabListener(queueTabListener);
//		queueActionBar.addTab(positionTab);
//	}
//
//	// listen for events generated by the ActionBar Tabs
//	TabListener queueTabListener = new TabListener() {
//		// called when the selected Tab is re-selected
//		@Override
//		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
//		}
//
//		// called when a previously unselected Tab is selected
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
//			// display the information corresponding to the selected Tab
//			if (tab.getPosition() == 1) {
//				setContentView(R.layout.position_in_line);
//				
//				// Get the view for the queue name and position.
//				TextView title = (TextView) findViewById(R.id.restaurant_position_title);
//				TextView position = (TextView) findViewById(R.id.position);
//
//				// If we have not selected a restaurant, display a special
//				// message.
//				// If we selected a restaurant, then set the title and position.
//				if (queue.getName() == "") {
//					title.setText("");
//					position.setText("You are not in line!");
//				} else {
//					title.setText(queue.getName());
//
//					// "Kevin" will need to eventually be replaced with the user
//					// ID.
//					// TODO: Change "Kevin" to a uniqued ID.
//					position.setText(queue.getPosition("Kevin") + " / "
//							+ String.valueOf(queue.size()));
//				}
//			} else {
//				setContentView(R.layout.main);
//				ListView lv = (ListView) findViewById(R.id.restaurants_available_list);
//
//				// Attach the array list adapter to the list view.
//				lv.setAdapter(restaurantsAdapter);
//
//				// Attach the item listener so that it does something when a
//				// restaurant is clicked.
//				lv.setOnItemClickListener(listviewListener);
//				restaurantsAdapter.notifyDataSetChanged();
//			}
//		}
//
//		// called when a tab is unselected
//		@Override
//		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
//		}
//	};

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
			// TODO Auto-generated method stub
			setRetainInstance(true);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Click 'Yes' to queue for " + restaurantName)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									// If it is not the same as the previous
									// line, then make a new line and add in the
									// user (temporarily named Kevin).
									if (queue.getName() != restaurantName) {
										queue = new QueueLine(restaurantName);
										queue.add("Kevin");
									}
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

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
				RestaurantListFragment fragment = new RestaurantListFragment();
				fragment.setAdapter(restaurantsAdapter);
				fragment.setListener(listviewListener);
				args.putInt(RestaurantListFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
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
		private OnItemClickListener listener;
		
		public RestaurantListFragment() {
		}
		
		public void setAdapter(ArrayAdapter<String> a) {
			adapter = a;
		}
		
		public void setListener(OnItemClickListener l) {
			listener = l;
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			QueueDialogFragment qdf = new QueueDialogFragment();
			qdf.show(getActivity().getFragmentManager(), "Queue Prompt");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setRetainInstance(true);
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			ListView lv = (ListView) rootView.findViewById(android.R.id.list);
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
			tv.setText("POSITION GOES HERE");
			//ListView lv = (ListView) rootView.findViewById(android.R.id.list);
			//lv.setAdapter(adapter);
			return rootView;
		}
	}
}
