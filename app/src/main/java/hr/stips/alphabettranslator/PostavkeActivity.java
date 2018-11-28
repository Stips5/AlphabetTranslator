package hr.stips.alphabettranslator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class PostavkeActivity extends AppCompatActivity {

    RadioButton radioRijec1;
    RadioButton radioRijec2;
    RadioButton radioRijec3;
    RadioButton radioRijecN;
    Switch themeMenu;
    ImageButton croLang;
    ImageButton engLang;
    ImageButton srbLang;
    public int language;        //HR->1 ENG->2
    public int wordLength;
    public Boolean themePick; //false default true dark theme
    public Boolean themeChanged;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restorePref();
        themeSetter();
        contentInit();
        languageSelected(language);
    }

    @Override
    protected void onStart() {
        super.onStart();

        switch (wordLength) {
            case 1:
                radioRijec1.setChecked(true);
                break;
            case 2:
                radioRijec2.setChecked(true);
                break;
            case 3:
                radioRijec3.setChecked(true);
                break;
            default:
                radioRijecN.setChecked(true);
                break;
        }


        radioRijec1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioRijec1.setChecked(true);
                radioRijec2.setChecked(false);
                radioRijec3.setChecked(false);
                radioRijecN.setChecked(false);
                wordLength = 1;
                savingPrefLength(wordLength);
            }
        });

        radioRijec2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioRijec1.setChecked(false);
                radioRijec2.setChecked(true);
                radioRijec3.setChecked(false);
                radioRijecN.setChecked(false);
                wordLength = 2;
                savingPrefLength(wordLength);
            }
        });

        radioRijec3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioRijec1.setChecked(false);
                radioRijec2.setChecked(false);
                radioRijec3.setChecked(true);
                radioRijecN.setChecked(false);
                wordLength = 3;
                savingPrefLength(wordLength);
            }
        });

        radioRijecN.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioRijec1.setChecked(false);
                radioRijec2.setChecked(false);
                radioRijec3.setChecked(false);
                radioRijecN.setChecked(true);
                wordLength = 4;
                savingPrefLength(wordLength);
            }
        });

        themeMenu.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View view) {
                themePick = themeMenu.isChecked();
                savingPrefTheme(themePick);
                Intent intent = new Intent(PostavkeActivity.this, PostavkeActivity.class);
                restart(intent);
            }
        });

        croLang.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (language == 1) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.vec_je_na_jeziku, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    language = 1;
                    savingPrefLanguage(language);
                    setLocale("hr");
                    themeChanged = true;
                    Toast.makeText(getApplicationContext(), R.string.Hrvatski, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostavkeActivity.this, MainActivity.class);
                    restart(intent);
                }
            }
        });

        engLang.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language == 2) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.vec_je_na_jeziku, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    language = 2;
                    savingPrefLanguage(language);
                    setLocale("en");
                    themeChanged = true;
                    Toast.makeText(getApplicationContext(), R.string.Engleski, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostavkeActivity.this, MainActivity.class);
                    restart(intent);
                }
            }
        });

        srbLang.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language == 3) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.vec_je_na_jeziku, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    language = 3;
                    savingPrefLanguage(language);
                    setLocale("sr");
                    themeChanged = true;
                    Toast.makeText(getApplicationContext(), R.string.srpski, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostavkeActivity.this, MainActivity.class);
                    restart(intent);
                }
            }
        });

    }

    private void savingPrefLength(int wordLength) {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("wordLength", wordLength);
        editor.apply();
    }

    private void savingPrefLanguage(int language) {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("language", language);
        editor.apply();
    }

    private void savingPrefTheme(Boolean themePick) {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("tema", themePick);
        editor.apply();
    }

    private void restorePref() {
        SharedPreferences pref = getSharedPreferences("settings", 0);
        wordLength = pref.getInt("wordLength", 1);
        themePick = pref.getBoolean("tema", false);
        language = pref.getInt("language", 1);
    }

    private void themeSetter() {
        if (!themePick) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.MyDarkTheme);
        }
    }

    private void languageSelected(int language) {
        if (language == 1)
        {
            croLang.setAlpha((float) 1);
            engLang.setAlpha((float) 0.5);
            srbLang.setAlpha((float) 0.5);
        }
        else if (language == 2)
        {
            engLang.setAlpha((float) 1);
            croLang.setAlpha((float) 0.5);
            srbLang.setAlpha((float) 0.5);
        }
        else if (language == 3)
        {
            srbLang.setAlpha((float) 1);
            croLang.setAlpha((float) 0.5);
            engLang.setAlpha((float) 0.5);
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void contentInit()
    {
        setContentView(R.layout.postavke_activity_layout);
        setTitle(R.string.postavke);

        radioRijec1 = (RadioButton) findViewById(R.id.radioButton1Rijec);
        radioRijec2 = (RadioButton) findViewById(R.id.radioButton2Rijeci);
        radioRijec3 = (RadioButton) findViewById(R.id.radioButton3Rijeci);
        radioRijecN = (RadioButton) findViewById(R.id.radioButtonNRijeci);
        themeMenu = (Switch) findViewById(R.id.switchTheme);
        themeMenu.setChecked(themePick);
        croLang = (ImageButton) findViewById(R.id.imageButtonCroatian);
        engLang = (ImageButton) findViewById(R.id.imageButtonEnglish);
        srbLang = (ImageButton) findViewById(R.id.imageButtonSerbian);
    }
    private void restart(Intent intent)
    {
        startActivity(intent);
        finish();
    }

}
