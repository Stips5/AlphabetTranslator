package hr.stips.alphabettranslator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

//1.1 data generated bug fixed higer efficienty, multithreading in data get

public class VjezbaPrevodenjaActivity extends AppCompatActivity {

    Button tipkaProvjeri;
    Button tipkaGeneriraj;
    Spinner dropDownMenu;
    TextView textGeneriraniText;
    EditText textUneseniPrevedeniText;

    public String generiraniPodaciString = " ";
    public String uneseniPodaciString = " ";
    public String tocanOdgovor = " ";
    Boolean dataGenerated;
    public int wordLength;
    public int language;        //HR->1 ENG->2
    public Boolean themePick; //false default true dark theme
    public Boolean connectivityFlag = false;
    TextView prozorcic1;
    TextView prozorcic2;
    int functionality = 0;       //0-> lat->cir , 1-> cir->lat
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restorePref();
        themeSetter();
        internetConnectionSafty();
        languageSetter(language);
        contentInit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        textUneseniPrevedeniText.setEnabled(false);
        tipkaProvjeri.setEnabled(false);

        tipkaGeneriraj.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                textUneseniPrevedeniText.setText(" ");
                textUneseniPrevedeniText.setBackgroundColor(Color.WHITE);
                textUneseniPrevedeniText.setEnabled(true);


                if(checkInternetConnection())
                {
                    generiraniPodaciString = latToCir.WebDataGet.getHTML(wordLength);

                    dataGenerated = !generiraniPodaciString.isEmpty() || generiraniPodaciString.length() > 2;

                    switch(functionality)
                    {
                        case 0:     //latinica hr -> cirilica sr
                        {
                            //vec ima generirani text
                            //prevedeni text za provjeru
                            tocanOdgovor = latToCir.translaterLatToCir(generiraniPodaciString);
                            break;
                        }
                        case 1:
                        {
                            //cirilica sr -> latinica hr

                            //tocan odogovor je vec preveden
                            tocanOdgovor = generiraniPodaciString;

                            //trazeni text se prevodi
                            generiraniPodaciString = latToCir.translaterLatToCir(tocanOdgovor);
                            break;
                        }
                    }

                    tipkaProvjeri.setEnabled(true);
                    Log.e("Generirani stringovi","Generirano i prevedeno");
                    Log.e("Generirano", generiraniPodaciString + "|");
                    Log.e("Tocan",tocanOdgovor + "|+\n");

                    //postavit text u formu
                    textGeneriraniText.setText(generiraniPodaciString);
                }

            }
        });

        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                functionality = position;
                changeFunctionality();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tipkaProvjeri.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                uneseniPodaciString = textUneseniPrevedeniText.getText().toString();

                if(uneseniPodaciString.charAt(uneseniPodaciString.length()-1) == ' ' )
                {
                    uneseniPodaciString = uneseniPodaciString.substring(0,uneseniPodaciString.length()-2);
                }


                if (dataGenerated)
                {
                    if (uneseniPodaciString.contentEquals(tocanOdgovor))
                    {
                        textUneseniPrevedeniText.setBackgroundColor(Color.GREEN);
                    } else
                    {
                        textUneseniPrevedeniText.setBackgroundColor(Color.RED);
                    }
                }
                else
                {
                    textGeneriraniText.setText(R.string.NoInterntConnection);
                }

                Log.e("Provjereni stringovi","Provjereno ");
                Log.e("Generirano", generiraniPodaciString + "|");
                Log.e("Upisano",uneseniPodaciString + "|+\n");

            }
        });


        textUneseniPrevedeniText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                    if (keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        tipkaProvjeri.performClick();
                        return true;
                    }
                return false;
            }
        });
    }

    private void restorePref()
    {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        wordLength = pref.getInt("wordLength", 1);
        themePick = pref.getBoolean("tema",false);
        language = pref.getInt("language",1);
    }

    private boolean checkInternetConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (null == ni) {

            Toast.makeText(this, R.string.NoInterntConnection, Toast.LENGTH_SHORT).show();
            connectivityFlag = false;
            return false;
        }
        else
        {
            connectivityFlag = true;
            return true;
        }
    }

    private void themeSetter()
    {
        if (!themePick)
        {
            setTheme(R.style.AppTheme);
        }
        else
        {
            setTheme(R.style.MyDarkTheme);
        }
    }

    private void internetConnectionSafty()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(R.string.PotrebanJeInternet);
        dlgAlert.setTitle(R.string.InternetConnection);

        final Intent mainActivityIntent = new Intent(VjezbaPrevodenjaActivity.this,MainActivity.class);

        if(checkInternetConnection())
        {
                dlgAlert.setMessage(R.string.PotrosnjaPodataka);
                dlgAlert.setPositiveButton(R.string.Continue,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                dlgAlert.setNegativeButton(R.string.Exit,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(mainActivityIntent);
                            }
                        });
        }
        else
        {
            dlgAlert.setNeutralButton(R.string.WIFIOn, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                }

            });
            dlgAlert.setPositiveButton(R.string.MobileData, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }
            });
            dlgAlert.setNegativeButton(R.string.Exit, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startActivity(mainActivityIntent);
                }
            });
        }

        dlgAlert.setCancelable(false);
        dlgAlert.create().show();

    }

    private void languageSetter(int language)
    {
        if (language == 1)
        {
            setLocale("hr");
        }
        else if (language == 2)
        {
            setLocale("en");
        }
        else if(language == 3)
        {
            setLocale("sr");
        }
    }

    private void setLocale(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void contentInit()
    {
        setContentView(R.layout.vjezba_prevodenja_activity_layout);
        setTitle(R.string.igra_prevodenja);

        tipkaProvjeri = (Button) findViewById(R.id.provjeriButtonID);
        tipkaGeneriraj = (Button) findViewById(R.id.generirajTextButtonID);
        textGeneriraniText = (TextView) findViewById(R.id.generiraniTextID);
        textUneseniPrevedeniText = (EditText) findViewById(R.id.uneseniPrevedeniTextID);
        prozorcic1 = (TextView) findViewById(R.id.prozorcic1Generator);
        prozorcic2 = (TextView) findViewById(R.id.prozorcic2Generator);
        progress = new ProgressDialog(this);

        dropDownMenu = (Spinner) findViewById(R.id.switchFunctionButtonID);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Opcije_prevodenja,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);

    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal==0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void changeFunctionality()
    {
        switch (functionality) {
            case 0:
            prozorcic1.setText(R.string.Latinica);
            prozorcic2.setText(R.string.Ćirilica);
            break;
            case 1:        //ako je trenutni na latinici prebaci ga na latinicu
                prozorcic1.setText(R.string.Ćirilica);
                prozorcic2.setText(R.string.Latinica);
                break;
            default:
                functionality = 0;
                break;
        }
    }
}