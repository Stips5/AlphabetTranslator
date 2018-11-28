package hr.stips.alphabettranslator;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.util.Locale;

public class PrevoditeljActivity extends AppCompatActivity{

    Button tipkaPrevedi;
    TextView textPrevedeniText;
    EditText textUneseniTextZaPrevod;
    String uneseniTextString ;
    Spinner dropDownMenu;
    String prevedeniTextString;
    TextView prozorcic1;
    TextView prozorcic2;
    public int language;
    public Boolean themePick = false; //false default true dark theme
    int functionality = 0;
    Typeface ttf;
    AssetManager mngr;
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onCreate(outState);
//
//        outState.putString( textUneseniTextZaPrevod.getText().toString(), uneseniTextString);
//        outState.putString( textPrevedeniText.getText().toString(), prevedeniTextString);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        uneseniTextString = savedInstanceState.getString(uneseniTextString);
//        prevedeniTextString = savedInstanceState.getString(prevedeniTextString);
//        textPrevedeniText.setText(prevedeniTextString);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restorePref();
        themeSetter();      //postavlja temu
        languageSetter(language);       //postavlja jezik
        contentInit();      //inicijalizira vrijednosti

    }

    @Override
    protected void onStart() {
        super.onStart();


        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                functionality = position;
                Log.e("\nOn item select","pozicija" + position);
                changeFunctionality();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            tipkaPrevedi.setOnClickListener(new Button.OnClickListener() {public void onClick(View v)
               {
                   uneseniTextString = textUneseniTextZaPrevod.getText().toString();

                   if(functionality == 0) {
                       prevedeniTextString = latToCir.translaterLatToCir(uneseniTextString);
                       Log.e("Promjena funkcije","Lat to Cir");
                       textPrevedeniText.setText(prevedeniTextString);
                   }
                   else if(functionality == 1)
                   {
                       prevedeniTextString = latToCir.translaterCirToLat(uneseniTextString);
                       Log.e("Promjena funkcije","Cir to Lat");
                       textPrevedeniText.setText(prevedeniTextString);
                   }
                   else if(functionality == 2)
                   {
                       textPrevedeniText.setText(uneseniTextString.toUpperCase());
                       Log.e("Promjena funkcije","Lat to Glag");
                       textPrevedeniText.setTypeface(ttf);
                       ttf = Typeface.createFromAsset(mngr,"fonts/glag.ttf");
                   }
                   else if(functionality == 3)
                   {
                       textPrevedeniText.setText(uneseniTextString);
                       Log.e("Promjena funkcije","Lat to Bos");
                       textPrevedeniText.setTypeface(ttf);
                       ttf = Typeface.createFromAsset(mngr,"fonts/bos1.ttf");
                   }
               }
            }
            );

        //radi to da se keyboard moze spustit sa enter na tipkovnici
        textUneseniTextZaPrevod.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                    if (keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        tipkaPrevedi.performClick();
                        return true;
                    }
                return false;
            }
        });

    }

    private void restorePref()
    {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        language = pref.getInt("language", 1);
        themePick = pref.getBoolean("tema",false);
    }

    private void changeFunctionality()
    {
        //minja vrijednosti text viewa ovisno koja je f-ja odabrana

        switch (functionality) {
            case 0: {
                prozorcic1.setText(R.string.Latinica);
                prozorcic2.setText(R.string.Ćirilica);
                textUneseniTextZaPrevod.setHint(R.string.UnesiTekstNaLatinici);
                textPrevedeniText.setHint(R.string.PrevedenoNaCirilicu);
                Log.e("change functionality", "promjena lat->cir");

                ttf = Typeface.DEFAULT;
                textPrevedeniText.setTypeface(ttf);
                break;
            }
            case 1: {
                prozorcic1.setText(R.string.Ćirilica);
                prozorcic2.setText(R.string.Latinica);
                textUneseniTextZaPrevod.setHint(R.string.UnesiTekstNaĆirilici);
                textPrevedeniText.setHint(R.string.PrevedenoNaLatinicu);
                Log.e("change functionality", "promjena cir->lat");

                ttf = Typeface.DEFAULT;
                textPrevedeniText.setTypeface(ttf);
                break;
            }
            case 2: {
                prozorcic1.setText(R.string.Latinica);
                prozorcic2.setText(R.string.Glagoljica);
                textUneseniTextZaPrevod.setHint(R.string.UnesiTekstNaLatinici);
                textPrevedeniText.setHint(R.string.PrevedenoNaGlagoljicu);
                Log.e("change functionality", "promjena lat->glag");

                ttf = Typeface.createFromAsset(this.getAssets(), "fonts/glag.ttf");
                textPrevedeniText.setTypeface(ttf);
                break;
            }
            case 3: {
                prozorcic1.setText(R.string.Latinica);
                prozorcic2.setText(R.string.Bosančica);
                textUneseniTextZaPrevod.setHint(R.string.UnesiTekstNaLatinici);
                textPrevedeniText.setHint(R.string.PrevedenoNaBosancicu);
                Log.e("change functionality", "promjena lat->bos");

                ttf = Typeface.createFromAsset(this.getAssets(), "fonts/bos1.ttf");
                textPrevedeniText.setTypeface(ttf);
                break;
            }
            default:
                functionality = 0;
                break;
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
        setContentView(R.layout.prevoditelj_activity_layout);
        setTitle(R.string.prevoditelj);

        mngr = this.getAssets();
        tipkaPrevedi = (Button) findViewById(R.id.prevediButtonID);
        textPrevedeniText = (TextView) findViewById(R.id.prevedeniTextID);
        textUneseniTextZaPrevod = (EditText) findViewById(R.id.uneseniTextID);
        prozorcic1 = (TextView) findViewById(R.id.textViewNazivProzorcica1);
        prozorcic2 = (TextView) findViewById(R.id.textViewNazivProzorcica2);
        dropDownMenu = (Spinner) findViewById(R.id.switchFunctionsSpinnerID);


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Opcije_prevodenja,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);
    }
}
