package com.mk.busystuff.zxm.fp_kaklkulator_mk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Spinner cmbPol;

    EditText txtGodini;
    EditText txtSklekovi;
    EditText txtStomacni;
    EditText txtTrcanje;

    TextView lblPoeniSklek;
    TextView lblPoeniStomacni;
    TextView lblPoeniTrcanje;

    TextView lblNormaSklek;
    TextView lblNormaStomacni;
    TextView lblNormaTrcanje;

    TextView lblProsek;
    TextView lblOcenaVrednost;

    String Pol;

    //boolean firstStart=true; //trebase da go koristam vo onitemselected za da ne prave nisto koga prvpat se povikuva
    // posle startot na aplikacijata

    int minSklek;
    int minStom;
    int minTrc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Pol="";

        // spinner za pol i edittext za godini
        cmbPol=(Spinner) findViewById(R.id.cmbGender);
        txtGodini=(EditText)findViewById(R.id.txtGodini);
        txtGodini.addTextChangedListener(new InnerCustomWatcher("godini"));

        txtGodini.requestFocus();

        // Edit textovi
        txtSklekovi=(EditText)findViewById(R.id.txtSklek);
        txtStomacni=(EditText)findViewById(R.id.txtStomacni);
        txtTrcanje=(EditText)findViewById(R.id.txtTrcanje);

        txtSklekovi.addTextChangedListener(new InnerCustomWatcher("sklekovi"));
        txtStomacni.addTextChangedListener(new InnerCustomWatcher("stomacni"));
        txtTrcanje.addTextChangedListener(new InnerCustomWatcher("trcanje"));

        // labeli za poeni
        lblPoeniTrcanje=(TextView)findViewById(R.id.lblPoeniTrcanje);
        lblPoeniSklek=(TextView)findViewById(R.id.lblPoeniSklek);
        lblPoeniStomacni=(TextView)findViewById(R.id.lblPoeniStomacni);

        //labeli za normi
        lblNormaSklek=(TextView)findViewById(R.id.lblNormaSklek);
        lblNormaStomacni=(TextView)findViewById(R.id.lblNormaStomacni);
        lblNormaTrcanje=(TextView)findViewById(R.id.lblNormaTrcanje);

        //labeli za prosek i ocena
        lblProsek=(TextView)findViewById(R.id.lblProsek);
        lblOcenaVrednost=(TextView)findViewById(R.id.lblOcenaVrednost);

        // nastan koga se izvrse selekcija vo komboto ---------------------------------
        cmbPol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                if (!firstStart) { //ispituvam firstStart za da ne mi vrse selkcija uste na start
                    //Pol = cmbPol.getSelectedItem().toString();
                 GetMinimums();
                }
                firstStart=false;
                 */
                GetMinimums();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //----------------------------------------------------------------------------
    }

    /**
     *= pri promena na pol ili godini se citat normite i se zapisuvat vo labelite =
      */
    void GetMinimums(){

        String pols=cmbPol.getSelectedItem().toString();

        int pol=1;// 1 za zensko, 0 za masko

        int starosna_grupa=0;

        int age=0;

        String age_s=txtGodini.getText().toString();

        try {
            age=Integer.valueOf(age_s);
        } catch (Exception ex){
            age=0;
        }

        starosna_grupa=AgeGroup(age);

        if (pols.equals("М")) pol = 0;//ako e maski pol pol=0


        String [] pointsFiles=new String[3];//mi vrakja iminja na falovi od koi treba
                                            //da citam poeni za masko ili zensko

        pointsFiles=GetPointsFiles(pol);

        // =========== zemam minimumi i gi prikazuvam===============================
        // (starosna_grupa 0-9, fileSklek="msklek.csv",context) - primer parametri
        minSklek = Minimum.GetMinPushUP(starosna_grupa, pointsFiles[0], MainActivity.this);
        lblNormaSklek.setText(String.valueOf(minSklek));
        minStom = Minimum.GetMinSitUP(starosna_grupa, pointsFiles[1], MainActivity.this);
        lblNormaStomacni.setText(String.valueOf(minStom));
        minTrc = Minimum.GetMinRun(starosna_grupa, pointsFiles[2], MainActivity.this);
        String trc = String.valueOf(minTrc);
        String timeSt =  trc.substring(0, 2) +  trc.substring(2, 4);
        lblNormaTrcanje.setText(String.valueOf(timeSt));
        //==========================================================================

        GetPoints();
    }

    void GetPoints(){

        // zemi pol, starost od kombo i txt, odredi starosna grupa i fajlovi za citanje na poeni
        String pols=cmbPol.getSelectedItem().toString();
        int pol=1;// 1 za zensko, 0 za masko
        int starosna_grupa=0;
        int age=0;
        String age_s=txtGodini.getText().toString();
        try {
            age=Integer.valueOf(age_s);
        } catch (Exception ex){
            age=0;
        }
        starosna_grupa=AgeGroup(age);
        if (pols.equals("М")) pol = 0;//ako e maski pol pol=0
        String [] pointsFiles=new String[3];//mi vrakja iminja na falovi od koi treba
        //da citam poeni za masko ili zensko
        pointsFiles=GetPointsFiles(pol);

        // zemi rezultati od txt
        // GetPushUpsCalc(String rezultat,int sgrupa,String fileSklek , Context ctx){
        int rezSklek=0;
        int rezStom=0;
        int rezTrc=0;

        // zemi poeni za sklekovi, stomacni i trcanje
        rezSklek=Presmetka.GetPushUpsCalc(txtSklekovi.getText().toString(),starosna_grupa,pointsFiles[0],this);
        rezStom=Presmetka.GetSitUpsCalc(txtStomacni.getText().toString(),starosna_grupa,pointsFiles[1],this);
        rezTrc=Presmetka.GetRunCalc(txtTrcanje.getText().toString(),starosna_grupa,pointsFiles[2],this);


        //System.out.println("SKLEKOVI ----------- "+String.valueOf(rezSklek));
        lblPoeniSklek.setText(String.valueOf(rezSklek));
        //System.out.println("Stomacni ----------- "+String.valueOf(rezStom));
        lblPoeniStomacni.setText(String.valueOf(rezStom));
        //System.out.println("Trcanje ----------- "+String.valueOf(rezTrc));
        lblPoeniTrcanje.setText(String.valueOf(rezTrc));

        float vkPoeni=(float)(rezSklek+rezStom+rezTrc);

        float prosek=0.0f;

        prosek=vkPoeni/3.0f;

        BigDecimal bd=new BigDecimal(String.valueOf(prosek));// samo zaradi zaokruzuvanje na 2 decimali

        bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);// samo zaradi zaokruzuvanje na 2 decimali

        if (prosek==0.0f) {
            lblProsek.setText("____");
            lblOcenaVrednost.setText("____");
        }   else {
            lblProsek.setText(String.valueOf(rezSklek + rezStom + rezTrc) + "/3=" + bd.toString());
            lblOcenaVrednost.setText(Presmetka.GetGrade(prosek));
        }

    }

    //============= ODREDUVANJE NA STAROSNA GRUPA ===========================================

    /** STAROSNA GRUPA
     * @param age
     * @return
     */
    private int AgeGroup(int age){
        int group=0;
        if (age<=21) group=1; //17-21
        if ((age>=22)&&(age<=26)) group=2; //22-26
        if ((age>=27)&&(age<=31)) group=3; //27-31
        if ((age>=32)&&(age<=36)) group=4; //32-36
        if ((age>=37)&&(age<=41)) group=5; //37-41
        if ((age>=42)&&(age<=46)) group=6; //42-46
        if ((age>=47)&&(age<=51)) group=7; //47-51
        if ((age>=52)&&(age<=56)) group=8; //52-56
        if ((age>=57)&&(age<=61)) group=9; //56-61
        if (age>=62) group=10; //62+
        return group;
    }
    //========================================================

    /**=========== odreduvanje na fajl za citanje na poeni =========================
     *
     * @param pol
     * @return
     */
    //=========== posebni se fajlovite za poeni za polovite =======================
    String [] GetPointsFiles(int pol){
        String fileSklek = "";
        String fileStom = "";
        String fileTrc = "";
        String [] pointsFiles=new String[3];
        switch (pol) {//odreduvam od koj normi (fajl) ke citam
            case 0://za masko
                pointsFiles[0] = "msklek.csv";//fileSklek = "msklek.csv";
                pointsFiles[1] = "mstomacni.csv";
                pointsFiles[2]= "mtrcanje.csv";
                break;
            case 1://za zensko
                pointsFiles[0] = "zsklek.csv";//fileSklek = "msklek.csv";
                pointsFiles[1] = "zstomacen.csv";
                pointsFiles[2]= "ztrcanje.csv";
                break;
            default:
                pointsFiles[0] = "msklek.csv";//fileSklek = "msklek.csv";
                pointsFiles[1] = "mstomacni.csv";
                pointsFiles[2]= "mtrcanje.csv";
                break;
        }

        return pointsFiles;
    }
    //=========== odreduvanje na fajl za citanje na poeni END ======================

// =================== MENITO ======================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.the_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // TODO: save data
                return true;

            case R.id.action_delete:

                ClearAll();
                return true;
            case R.id.action_settings:
                // TODO: settings
                return true;
            case R.id.action_help:
                // TODO: HELP
                return true;
            case R.id.action_about:
                // TODO: about
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    // =================== MENITO END ==================================================

    /** Clear all boxes :)
     *
     */
    void ClearAll(){
        txtGodini.setText("");
        txtSklekovi.setText("");
        txtStomacni.setText("");
        txtTrcanje.setText("");
        lblProsek.setText("____");
        lblOcenaVrednost.setText("____");
        lblNormaStomacni.setText("");
        lblNormaSklek.setText("");
        lblNormaTrcanje.setText("");
    }


    //################# INNER CLASS TEXTEATCHER (TextOnChange) ################################
    class InnerCustomWatcher implements TextWatcher {

        private boolean mWasEdited = false;
        private String viewId;// da mi cuva imeto na EditText sto se menuva
        /*
        kako parametar dobiva ime na EditText ili nesto za da znaeme koj e
         moze namesto string da bide int pa so switch da se raboti vo metodot afterTextChanged
         */
        InnerCustomWatcher(String _viewId){
            this.viewId= _viewId;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get entered value (if required)
            //String enteredValue  = s.toString();
            //String newValue = "new value";

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (viewId.equals("godini")){
                GetMinimums();//lblPoeniTrcanje.setText(s);
            } else GetPoints();
        }
    }
//################# INNER CLASS TEXTEATCHER (TextOnChange) END #############################
}

