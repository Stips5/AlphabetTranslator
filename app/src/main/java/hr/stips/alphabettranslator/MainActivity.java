package hr.stips.alphabettranslator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button prevoditelj;
    Button igraPrevodenja;
    ImageButton postavke;
    ImageButton about;
    public int language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restorePref();      //ucitavanje spremljenih podataka
        languageSetter(language);       //postavljanje elemenata
        contentInit();          //inicijalizacija elemenata
        networkUseEnable();     //omogucuje koristenje mreze
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intentPrevoditelj = new Intent(MainActivity.this,PrevoditeljActivity.class);
        final Intent intentIgraPrevodenja = new Intent(MainActivity.this,VjezbaPrevodenjaActivity.class);
        final Intent intentPostavke = new Intent(MainActivity.this,PostavkeActivity.class);
        final Intent intentAbout = new Intent(MainActivity.this,AboutActivity.class);
        
        prevoditelj.setOnClickListener(new Button.OnClickListener() {public void onClick(View v)
               {
                   startActivity(intentPrevoditelj);
               }
            }
            );

        igraPrevodenja.setOnClickListener(new Button.OnClickListener() {public void onClick(View v)
               {
                   startActivity(intentIgraPrevodenja);
               }
        }
        );

        postavke.setOnClickListener(new Button.OnClickListener() {public void onClick(View v)
               {
                    startActivity(intentPostavke);
               }
        }
        );

        about.setOnClickListener(new Button.OnClickListener() {public void onClick(View v)
               {
                startActivity(intentAbout);
               }
        }
        );

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
//
//        Toast toast = Toast.makeText(getApplicationContext(), R.string.restartApp, Toast.LENGTH_SHORT);
//        toast.show();
    }

    private void contentInit()
    {
//        setContentView(R.layout.activity_main_layout);
        setContentView(R.layout.activity_main_layout_modern);
        setTheme(R.style.AppTheme);

        prevoditelj = findViewById(R.id.buttonPrevoditeljID);
        igraPrevodenja = findViewById(R.id.buttonIgraPrevodenjaID);
        postavke = findViewById(R.id.buttonPostavkeID);
        about = findViewById(R.id.buttonAboutID);
    }

    private void restorePref() {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        language = pref.getInt("language", 1);
    }

    private void networkUseEnable()
    {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void restart(Intent intent)
    {
        startActivity(intent);
        finish();
    }
}

// TODO: 12.09.16. Ispravit activity kad prominim jezik i vratim se u menu i kliknem back vrati me na stari jezik jer ne zavrsi proslu activity
//TODO: shrink apk size https://developer.android.com/studio/build/shrink-code.html