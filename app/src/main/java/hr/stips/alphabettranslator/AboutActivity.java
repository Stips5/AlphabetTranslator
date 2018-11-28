package hr.stips.alphabettranslator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity{

    Button feedback;
    Button rateApp;
    public int language;
    public Boolean themePick; //false default true dark theme

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restorePref();
        themeSetter();
        languageSetter(language);
        contentInit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        feedback.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myEmail[] = {"smedak95@gmail.com"};
                String feedbackSubject = "LatToCir Feedback";
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,myEmail);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,feedbackSubject);
                startActivity(Intent.createChooser(emailIntent,"Help me to improve app with mail:"));
            }
        });

        rateApp.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.notOnMarketJet, Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    private void restorePref()
    {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        language = pref.getInt("language", 1);
        themePick = pref.getBoolean("tema",false);
    }

    private void themeSetter()
    {
        if (!themePick)
        {
            setTheme(R.style.latToCirNormal);
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
        setContentView(R.layout.about_layout);
        setTitle(R.string.about);

        feedback = (Button)findViewById(R.id.buttonFeedbackSend);
        rateApp = (Button)findViewById(R.id.buttonRateApp);
    }
}
