package hr.stips.alphabettranslator;

import android.util.Log;
import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;

class latToCir extends VjezbaPrevodenjaActivity {

    static Vector<Pair<String,String>> lat_cir_dict = initVector();

    public static class Data{
        String data;
        int length;

        Data()
        {
            data = " ";
            length = 0;
        }
    }

//    public String translate(String inputData, String outputData, int mode)
//    {
//        switch(mode)
//        {
//            case 1:
//            {
//                outputData = translaterLatToCir(inputData);
//                break;
//            }
//            case 2:
//            {
//                outputData = translaterCirToLat(inputData);
//                break;
//            }
//        }
//        return outputData;
//    }

    static Vector<Pair<String,String>> initVector()
    {
        Vector<Pair<String,String>>lat_cir_dict = new Vector<>();
        lat_cir_dict.add(new Pair<>("a","а"));
        lat_cir_dict.add(new Pair<>("b","б"));
        lat_cir_dict.add(new Pair<>("c","ц"));
        lat_cir_dict.add(new Pair<>("č","ч"));
        lat_cir_dict.add(new Pair<>("ć","ћ"));
        lat_cir_dict.add(new Pair<>("d","д"));
        lat_cir_dict.add(new Pair<>("dž","џ"));
        lat_cir_dict.add(new Pair<>("đ","ђ"));
        lat_cir_dict.add(new Pair<>("e","е"));
        lat_cir_dict.add(new Pair<>("f","ф"));
        lat_cir_dict.add(new Pair<>("g","г"));
        lat_cir_dict.add(new Pair<>("h","х"));
        lat_cir_dict.add(new Pair<>("i","и"));
        lat_cir_dict.add(new Pair<>("j","ј"));
        lat_cir_dict.add(new Pair<>("k","к"));
        lat_cir_dict.add(new Pair<>("l","л"));
        lat_cir_dict.add(new Pair<>("lј","љ"));
        lat_cir_dict.add(new Pair<>("m","м"));
        lat_cir_dict.add(new Pair<>("n","н"));
        lat_cir_dict.add(new Pair<>("nj","њ"));
        lat_cir_dict.add(new Pair<>("o","о"));
        lat_cir_dict.add(new Pair<>("p","п"));
        lat_cir_dict.add(new Pair<>("r","р"));
        lat_cir_dict.add(new Pair<>("s","с"));
        lat_cir_dict.add(new Pair<>("š","ш"));
        lat_cir_dict.add(new Pair<>("t","т"));
        lat_cir_dict.add(new Pair<>("u","у"));
        lat_cir_dict.add(new Pair<>("v","в"));
        lat_cir_dict.add(new Pair<>("z","з"));
        lat_cir_dict.add(new Pair<>("ž","ж"));
        return lat_cir_dict;
    }

    static String translaterLatToCir(String data) {
        /*ne more ic u for petlju jer nije jednostavno preslikavanje
            latinica ide a,b,c,č,ć,d...
            ćirilica a,b,v,g,d,đ,e,ž...
        */
//        final String russianAlphabetLowercase = "а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я";
//        final String russianAlphabetUppercase = "А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я";

        String translation = "";
        char[] dataChar = data.toCharArray();

        //kompleksnost je O(n^2) al su relativno male rici
        //mogla bi bit sa jednom for petlje tako da ima dva string pa usporediva clanove
        for (int i = 0; i < dataChar.length; i++) {
            for (int j = 0; j < lat_cir_dict.size(); j++) {
                if(lat_cir_dict.get(j).first.equals(String.valueOf(dataChar[i])))
                    if(Character.isUpperCase(dataChar[i]))
                        //zapravo je bitno samo prvo slovo da je vece od "vise komponentnih slova"
                        translation += Character.toUpperCase(lat_cir_dict.get(j).second.charAt(0));
                    else
                        translation += lat_cir_dict.get(j).second;
            }
        }
        return translation;
    }
//
//    static String translaterLatENToRUCir(String data) {
//        final String russianAlphabetLowercase = "а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я";
//        final String russianAlphabetUppercase = "А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я";
//        final String englishAlphabetLowercase = "а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я";
//        final String englisAlphabetUppercase =  "A B V G D E";
//
//        final String serbianAlphabetLowercase = "1234567890абцчћдџ ђефгхијклљ мнњ опрсштувзж ,.()";
//        final String serbianAlphabetUppercase = "1234567890АБЦЧЋДЏ ЂЕФГХИЈКЛЉ МНЊ ОПРСШТУВЗЖ ,.()";
//        final String expectedUppercase =        "1234567890ABCČĆDDŽĐEFGHIJKLLЈMNNJOPRSŠTUVZŽ ,.()";
//        final String expectedLowercase =        "1234567890abcčćddžđefghijkllјmnnjoprsštuvzž ,.()";
//        //   0 1 2 3 4 5 6  7 8 9 0 1 2 3 4 5 16 7 8 19 0 1 2 3 4 5 6 7 8 9
//        //   A B C Č Ć D DŽ Đ E F G H I J K L LJ M N NJ O P R S Š T U V Z Ž
//        //   A Б Ц Ч Ћ Д Џ  Ђ Е Ф Г Х И Ј К Л Љ  М Н Њ  О П Р С Ш Т У В З Ж
//        //   0  1   2   3   4   5   67  8   9   10  11  12  13  14  15  16 1718 19  20 2122 23  24  25  26  27  28  29  30  31  32
//        //   A  B   C   Č   Ć   D   DŽ  Đ   E   F   G   H   I   J   K   L   LJ  M   N   NJ  O   P   R   S   Š   T   U   V   Z   Ž
//        //   0  1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29
//        //   A  Б   Ц   Ч   Ћ   Д   Џ   Ђ   Е   Ф   Г   Х   И   Ј   К   Л   Љ   М   Н   Њ   О   П   Р   С   Ш   Т   У   В   З   Ж
//        final char[] cirilicaLowercase = serbianAlphabetLowercase.toCharArray();
//        final char[] cirilicaUppercase = serbianAlphabetUppercase.toCharArray();
//        final char[] latinicaLowercase = expectedLowercase.toCharArray();
//        final char[] latinicaUppercase = expectedUppercase.toCharArray();
//        char[] dataLatinica;
//        char[] dataCirilica;
//        boolean flag = false;
//        int dest = 0;
//
//        if (data.equals(" ") )
//        {
//            return " ";
//        }
//
//        dataLatinica = data.toCharArray();
//        dataLatinica = Arrays.copyOf(dataLatinica,dataLatinica.length+1);
//        dataCirilica = new char[dataLatinica.length+2];
//
//        for (int i = 0; i < dataLatinica.length; i++, dest++)
//        {
//            if (i==0)
//            {
//                dest=0;
//            }
//            if (dataLatinica[i] == ' ')
//            {
//                dataCirilica[i] = ' ';
//            }
//            if (dataLatinica[i] == ',')
//            {
//                dataCirilica[i] = ',';
//            }
//            if (dataLatinica[i] > 48 && dataLatinica[i] < 57 )
//            {
//                dataCirilica[i] = dataLatinica[i];
//            }
//
//            if (Character.isLowerCase(dataLatinica[i]))
//            {
//                for (int j = 0; j < latinicaLowercase.length; j++)
//                {
//                    if (dataLatinica[i] == 'd' && dataLatinica[i+1] == 'ž')
//                    {
//                        dataCirilica[dest] = cirilicaLowercase[16];       //dž  6 7  -> 6
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if(dataLatinica[i] == 'l' && dataLatinica[i+1] == 'j')
//                    {
//                        dataCirilica[dest] = cirilicaLowercase[27];       //lj  17 18 -> 17
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if(dataLatinica[i] == 'n' && dataLatinica[i+1] == 'j')
//                    {
//                        dataCirilica[dest] = cirilicaLowercase[31];        //nj  21 22 -> 21
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if (dataLatinica[i] == latinicaLowercase[j])
//                    {
//                        if (flag)
//                        {
//                            dataCirilica[dest] = cirilicaLowercase[j];
//                            break;
//                        }
//                        else
//                        {
//                            dataCirilica[i] = cirilicaLowercase[j];
//                            flag=false;
//                            break;
//                        }
//                    }
//                }
//            }
//            else if (Character.isUpperCase(dataLatinica[i]))
//            {
//                for (int j = 0; j < latinicaLowercase.length; j++)
//                {
//                    if (dataLatinica[i] == 'D' && dataLatinica[i+1] == 'Ž')
//                    {
//                        dataCirilica[dest] = cirilicaUppercase[16];       //DŽ  6 7  -> 6
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if(dataLatinica[i] == 'L' && dataLatinica[i+1] == 'J')
//                    {
//                        dataCirilica[dest] = cirilicaUppercase[27];       //LJ  17 18 -> 17
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if(dataLatinica[i] == 'N' && dataLatinica[i+1] == 'J')
//                    {
//                        dataCirilica[dest] = cirilicaUppercase[31];        //NJ  21 22 -> 21
//                        flag = true;
//                        i++;
//                        break;
//                    }
//                    else if (dataLatinica[i] == latinicaUppercase[j])
//                    {
//                        if (flag)
//                        {
//                            dataCirilica[dest] = cirilicaUppercase[j];
//                            break;
//                        }
//                        else
//                        {
//                            dest=i;
//                            dataCirilica[dest] = cirilicaUppercase[j];
//                            flag=false;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        String fine = new String(dataCirilica);
//        fine = fine.substring(0,fine.length()-3);
//
//        return fine;
//    }
//

    static String translaterCirToLat(String data) {
        char[] dataChar = data.toCharArray();
        String translation = "";

        for (int i = 0; i < dataChar.length; i++) {
            for (int j = 0; j < lat_cir_dict.size(); j++) {
                if(lat_cir_dict.get(j).second.equals(String.valueOf(dataChar[i])))
                    if(Character.isUpperCase(dataChar[i]))
                        //zapravo je bitno samo prvo slovo da je vece od "vise komponentnih slova"
                        translation += Character.toUpperCase(lat_cir_dict.get(j).first.charAt(0));
                    else
                        translation += lat_cir_dict.get(j).first;
            }
        }

        return translation;
    }

    static String filter(Data podaci, int brojTrazenihRijeci) {

        int brojRijeci;
        char str[];
        String[] realData = new String[4];
        String temp[];
        StringBuilder builder = new StringBuilder();
        StringBuilder usableData = new StringBuilder();
        final String expectedLowercase = "abcčćddžđefghijkllјmnnjoprsštuvzž ";

        char lowercase[] = expectedLowercase.toCharArray();

        str = podaci.data.toCharArray();

        for (int i = 0; i < str.length; i++) {

            for (int j = 0; j < expectedLowercase.length(); j++) {
                if (Character.toLowerCase(str[i]) == lowercase[j]) {            //provjerava u malim slovima jeli ispravan jezik
                    usableData.append(str[i]);              //dodaje na string
                    break;
                }
            }
        }

        if (usableData.indexOf(" ") == 0) {             //brise ' ' isprid stringa
            usableData.delete(0, usableData.indexOf(" "));
        }
        podaci.data = usableData.toString();       //pritvara podatke u string
        temp = podaci.data.split(" ");        //djeli ga po rijecima
        brojRijeci = temp.length;  //broji koliko stringova ima tmp je string array

        if (brojRijeci >= 1) {
            realData[0] = temp[0];
        }
        if (brojRijeci >= 2) {
            realData[1] = temp[0] + " " + temp[1];
        }
        if (brojRijeci >= 3) {
            realData[2] = temp[0] + " " + temp[1] + " " + temp[2];
        }
        if (brojRijeci > 3)
        {
            realData[3] = builder.toString();
        }

        switch (brojTrazenihRijeci) {
            case 1:
                return realData[0];
            case 2:
                return realData[1];
            case 3:
                return realData[2];
            default:
                return realData[3];
        }

    }

    public static class WebDataGet {

        static String getHTML(int lengthNeeded) {

            String url = "https://hr.wikipedia.org/wiki/Posebno:Slu%C4%8Dajna_stranica";
            Document doc;
            Data podaci = new Data();
            String tmp[];
            podaci.data = " ";

            do {
                try {
                    doc = Jsoup.connect(url).get();

                    if (doc == null) {
                        Log.d("Error","getting source check your internet connection");
                    }

                    Element body = doc.body();
                    Elements selectedBody = body.select("p > b");       //uzima prvo sta je boldano
                    podaci.data = selectedBody.toString();
                    podaci.data = Jsoup.parse(podaci.data).text();

                    tmp = podaci.data.split(" ");
                    podaci.length = tmp.length;     //broji koliko je rijeci ucita
                    if(podaci.length > 2)
                    {
                        if (tmp[0].equals(tmp[1]))
                        {
                            podaci.length--;
                        }
                    }

                } catch (IOException e) {
                    Log.d("Error","Getting data with WebDataGet.getHTML()",e);
                }

            }while(podaci.data == null || podaci.data.isEmpty() || podaci.length < lengthNeeded || podaci.data.length() < 3);

            if (podaci.data.equals(" "))
            {
                return " ";
            }

            podaci.data = filter(podaci,lengthNeeded);
            return podaci.data;
        }
    }
}