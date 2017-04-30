package forsk.in.tryjson;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import wrapper.FacultyWrapper;

/**
 * Created by student on 08-04-2016.
 */
public class utils {
    private final static String TAG = utils.class.getSimpleName();

    /**
     * Method to convert the Input stream to string.
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String convertStreamToString(InputStream is) throws IOException {
        // Converting input stream into string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while (i != -1) {
            baos.write(i);
            i = is.read();
        }
        return baos.toString();
    }


    /**
     * Method to read the json file from RAW folder.
     *
     * @param context
     * @param resourceId
     * @return
     * @throws IOException
     */
    public static String getStringFromRaw(Context context, int resourceId)
            throws IOException {
        // Reading File from resource folder
        Resources r = context.getResources();
        InputStream is = r.openRawResource(resourceId);
        String statesText = convertStreamToString(is);
        is.close();

        Log.d(TAG, statesText);

        return statesText;
    }

    public static ArrayList<FacultyWrapper> pasreLocalFacultyJson(String json_string) {

        ArrayList<FacultyWrapper> mFacultyDataList = new ArrayList<FacultyWrapper>();
        try {
            // Converting multipal json data (String) into Json array
            JSONArray facultyArray = new JSONArray(json_string);
            Log.d(TAG, facultyArray.toString());
            // Iterating json array into json objects
            for (int i = 0; facultyArray.length() > i; i++) {

                // Extracting json object from particular index of array
                JSONObject facultyJsonObject = facultyArray.getJSONObject(i);

                // Design patterns
                FacultyWrapper facultyObject = new FacultyWrapper(facultyJsonObject);

                printObject(facultyObject);

                mFacultyDataList.add(facultyObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mFacultyDataList;
    }

    public static void printObject(FacultyWrapper obj) {
        // Operator Overloading
        Log.d(TAG, "First Name : " + obj.getFirst_name());
        Log.d(TAG, "Last Name : " + obj.getLast_name());
        Log.d(TAG, "Photo : " + obj.getPhoto());
        Log.d(TAG, "Department : " + obj.getDepartment());
        Log.d(TAG, "research_area : " + obj.getResearch_area());

        for (Object s : obj.getInterest_areas()) {
            Log.d(TAG, "Interest Area : " + s);
        }
        for (Object s : obj.getContact_details()) {
            Log.d(TAG, "Contact Detail : " + s);
        }
    }

    public static InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }


}
