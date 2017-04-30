package forsk.in.tryjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import wrapper.FacultyWrapper;

public class MainActivity extends AppCompatActivity {

    LinearLayout scrollBase;
    Button btnDownload;
    String raw_JSON = "";

    // Params, Progress, Result
    class CustomAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Task before the background task
            Toast.makeText(MainActivity.this, "onPreExecute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = readJSONFromInternet(params[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Task after the background task
            Toast.makeText(MainActivity.this, "onPostExecute", Toast.LENGTH_LONG).show();
            showFormattedJSON(result);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollBase = (LinearLayout)findViewById(R.id.scrollBase);
        btnDownload = (Button) findViewById(R.id.button1);

        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //try {
                    //String raw_JSON = utils.getStringFromRaw(MainActivity.this, R.raw.faculty_profile_code);
                    //tv.setText(raw_JSON);
                    //showParsedJSON(raw_JSON);
                    //raw_JSON = readJSONFromInternet("http://demo5392516.mockable.io/faculty_json");
                    //showFormattedJSON(raw_JSON);
                    new CustomAsyncTask().execute("http://demo5392516.mockable.io/faculty_json");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            raw_JSON = readJSONFromInternet("http://demo5392516.mockable.io/faculty_json");


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showFormattedJSON(raw_JSON);
                                }
                            });
                        }
                    }).start();

//                } catch (android.os.NetworkOnMainThreadException e) {
//                    e.printStackTrace();
//                    tv.setText(e.getMessage());
//                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showParsedJSON(String raw_JSON) {

        try {

            JSONArray facultyArray = new JSONArray(raw_JSON);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; facultyArray.length() > i; i++) {

                JSONObject facultyObj = facultyArray.getJSONObject(i);

                sb.append("##############"+(i+1)+"###############");
                sb.append(System.getProperty("line.separator"));

                sb.append("first_name ->");
                sb.append(System.getProperty("line.separator"));


                sb.append("    "+facultyObj.getString("first_name"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

                sb.append("last_name ->");
                sb.append(System.getProperty("line.separator"));

                sb.append("    "+facultyObj.getString("last_name"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

                sb.append("photo ->");
                sb.append(System.getProperty("line.separator"));

                sb.append("    "+facultyObj.getString("photo"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

                sb.append("department ->");
                sb.append(System.getProperty("line.separator"));

                sb.append("    "+facultyObj.getString("department"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

                sb.append("research_area ->");
                sb.append(System.getProperty("line.separator"));

                sb.append("    "+facultyObj.getString("research_area"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

                JSONObject interest_areas = facultyObj.getJSONObject("interest_areas");

                sb.append("interest_areas ->");
                sb.append(System.getProperty("line.separator"));

                sb.append("        "+interest_areas.getString("subject_1"));
                sb.append(System.getProperty("line.separator"));

                sb.append("        "+interest_areas.getString("subject_2"));
                sb.append(System.getProperty("line.separator"));

                sb.append("        "+interest_areas.getString("subject_3"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));


                JSONObject contact_details = facultyObj.getJSONObject("contact_details");

                sb.append("contact_details ->");
                sb.append(System.getProperty("line.separator"));


                sb.append("        "+contact_details.getString("phone"));
                sb.append(System.getProperty("line.separator"));

                sb.append("        "+contact_details.getString("email"));
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));

            }

            sb.append("#############################");

            tv.setText(sb);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showFormattedJson(String raw_json) {

        ArrayList<FacultyWrapper> facultyWrapperArrayList = utils.pasreLocalFacultyJson(raw_json);


        for (FacultyWrapper facultyWrapper : facultyWrapperArrayList) {

            TextView tv =  new TextView(MainActivity.this);
            tv.setText(facultyWrapper.getFirst_name());
            scrollBase.addView(tv);
        }

    }
//
//    sb.append("#############################");
//
//    tv.setText(sb);

    public String readJSONFromInternet(String url) {
        String response = "";
        try {
            InputStream is = utils.openHttpConnection(url);
            response = utils.convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}

