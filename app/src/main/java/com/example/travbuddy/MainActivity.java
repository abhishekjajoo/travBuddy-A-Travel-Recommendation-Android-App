package com.example.travbuddy;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button predict;
    TextView result;


    String[] items =  {"AMBER PALACE",
            "CITY PALACE JAIPUR",
            "JANTAR MANTAR JAIPUR",
            "HAWA MAHAL",
            "ALBERT HALL MUSEUM (CENTRAL MUSEUM)",
            "NAHARGARH FORT",
            "BIRLA TEMPLE JAIPUR",
            "JAL MAHAL",
            "KING EDWARD MEMORIAL",
            "SISODIA RANI PALACE AND GARDEN",
            "VIDYADHAR GARDEN",
            "CENTRAL PARK JAIPUR INDIA",
            "ANOKHI MUSEUM OF HAND PRINTING",
            "GOVIND DEVJI TEMPLE",
            "MOTI DOONGRI GANESH TEMPLE",
            "DIGAMBER JAIN MANDIR JAIPUR",
            "GALTAJI",
            "STATUE CIRCLE",
            "RAM NIWAS GARDEN",
            "ZOOLOGICAL GARDEN JAIPUR",
            "KANAK VRINDAVAN",
            "AMAR JAWAN JYOTI JAIPUR",
            "MAHARANI KI CHHATRI JAIPUR",
            "NAHARGARH BIOLOGICAL PARK",
            "JAWAHAR KALA KENDRA",
            "RAJ MANDIR",
            "SAMBHAR LAKE",
            "AKSHARDHAM TEMPLE JAIPUR",
            "JAGAT SHIROMANI TEMPLE",
            "JAWAHAR CIRCLE",
            "MUSEUM OF GEM AND JEWELLERY",
            "JHALANA SAFARI PARK IN JAIPUR",
            "MASALA CHOWK",
            "AMRAPALI MUSEUM",
            "MUSEUM OF LEGACIES JAIPUR INDIA",
            "CHOKHI DHANI",
            "ELEFANTASTIC ELEPHANT FARM",
            "PANNA MEENA KI BAOLI",
            "JOHARI BAZAAR",
            "BAPU BAZAAR",
            "WORLD TRADE PARK",
            "GARH GANESH MANDIR",
            "MUKESH ART GALLERY",
            "ANGEL RESORT AND AMUSEMENT WATERPARK",
            "B M BIRLA AUDITORIUM",
            "KHOLE KE HANUMAN JI TEMPLE, JAIPUR",
            "SKYFUT",
            "GATORE KI CHHATRI JAIPUR",
            "RUSIRANI VILLAGE JAIPUR",
            "DEER PARK JAIPUR",
            "RAMGARH LAKE",
            "ST ANDREWS CHURCH JAIPUR",
            "JAMVA RAMGARH WILDLIFE SANCTUARY",
            "JWALA MATA MANDIR JAIPUR",
            "JAMWAY MATA MANDIR",
            "LAXMI NARAYAN TEMPLE JAIPUR",
            "DARGAH BURHANUDDIN BABA JAIPUR",
            "KULISH SMIRITI VAN JAIPUR",
            "DHAMMA THALI VIPASSANA MEDITATION CENTRE",
            "CHANDLAI LAKE",
            "SHRI GOPINATH TEMPLE JAIPUR",
            "DOLLS MUSEUM",
            "PEACOCK GARDEN JAIPUR",
            "ELEMENTS SHOPPING JAIPUR",
            "CRYSTAL MALL JAIPUR",
            "HANDICRAFT HAVELI JAIPUR",
            "SUNRISE DREAM WORLD AMUSEMENT PARK",
            "BRETHREN CHURCH JAIPUR",
            "MI ROAD",
            "BHANGARH FORT",
            "SKY WALTZ BALLOON SAFARI",};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    String url="https://travbuddy-project.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        final String[] item = new String[1];
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 item[0] = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item[0],Toast.LENGTH_SHORT).show();
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley

             StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {

                                try {
                                    //JSONObject jsonObject = new JSONObject(response);
                                    //JSONObject jsonObject=new JSONObject(response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("recommended places");
                                    int n=data.length();
                                    String str=data.substring(1,n-1);
                                    String[] arrOfStr = str.split(",");
                                    result.setText(arrOfStr[0]+"\n\n\n"+arrOfStr[1]+"\n\n\n"+arrOfStr[2]+"\n\n\n"+arrOfStr[3]+"\n\n\n"+arrOfStr[4]);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("place",item[0]);


                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });


    }
}