package com.stg.inqueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

public class writeToSDCard {
	
	private static final String TAG = "MEDIA";

	public void outputTextFile(String number) throws IOException{
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/inQueue/data");
		if(!(dir.exists())){
			dir.mkdirs();
		}
		File file = new File(dir, "phoneNumber.txt");
		if(!(file.exists())){
			try {
				FileOutputStream f = new FileOutputStream(file);
				PrintWriter pw = new PrintWriter(f, false);
				pw.println(number);
				pw.flush();
				pw.close();
				f.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "******* File not found. Did you" +
		                " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
			}
		}
	}
}
