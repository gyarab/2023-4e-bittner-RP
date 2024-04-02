package com.example.rp_2024


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.rp_2024.databinding.ActivityCaesarsCipherBinding

//Jednoduchá aktivita na posouvání abecedy
//Hlavně posloužila k tomu, že jsem se na ní naučil základy kotlinu a Android strudia
class CaesarsCipherActivity : DrawerBaseActivity(), View.OnClickListener {

    //late init promněnné jsou inicializovány později
    private lateinit var activityCaesarsCipherBinding : ActivityCaesarsCipherBinding
    private lateinit var btn : Button
    private lateinit var text : EditText
    private lateinit var posun : EditText
    private lateinit var vysledek : TextView
    private lateinit var ap : TextView
    private lateinit var a : TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    lateinit var smer : Switch

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //binding poskytuje přistup k prvkům layoutu
        activityCaesarsCipherBinding = ActivityCaesarsCipherBinding.inflate(layoutInflater)

        setContentView(activityCaesarsCipherBinding.root)
        allocateActivityTitle("Caesarova Šifra")

        //tady používám zastaralý postup
        //správně by bylo binding.sifruj    ve zbytku aplikace to tak je
        btn = findViewById(R.id.sifruj)
        text = findViewById(R.id.text)
        posun = findViewById(R.id.posun)
        vysledek = findViewById(R.id.vysledek)
        ap = findViewById(R.id.posunuta)
        a = findViewById(R.id.abeceda)
        smer = findViewById(R.id.smer)
        if(savedInstanceState == null){
            text.setText("JE TO NULL")
        } else {
            text.setText("NENI TO NULL")
        }
        //text.setText(savedInstanceState?.getString("TEXT"))
        posun.setText(savedInstanceState?.getString("POSUN"))
        vysledek.text = savedInstanceState?.getString("VYSLEDEK")
        var p = 0
        if(posun.text != null && posun.text.toString() != ""){
            p = posun.text.toString().toInt()
        }
        vypisPosunutou(p)

        btn.setOnClickListener(this)
        posun.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                p = 0

                if(posun.text != null && posun.text.toString() != ""){
                    p = posun.text.toString().toInt()
                }
                vypisPosunutou(p)
            }


        }

        smer.setOnCheckedChangeListener { _, isChecked ->
            p = 0
            if(posun.text != null && posun.text.toString() != ""){
                p = posun.text.toString().toInt()
            }
            vypisPosunutou(p)
        }

    }

     
    //pokus o uchování dat při ukončení aktivity
    override fun onSaveInstanceState(outState: Bundle) {
            outState.putString("POSUN", posun.text.toString())
            outState.putString("TEXT", text.text.toString())
            outState.putString("VYSLEDEK", vysledek.text.toString())
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState)
    }

    //pokud o obnovení dat při znovu otevření aktivity
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text.setText(savedInstanceState.getString("TEXT"))
        posun.setText(savedInstanceState.getString("POSUN"))
        vysledek.text = savedInstanceState.getString("VYSLEDEK")
        var p = 0
        if(posun.text != null && posun.text.toString() != ""){
            p = posun.text.toString().toInt()
        }
        vypisPosunutou(p)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onClick(v : View?) {
        v?.hideKeyboard()
        var p = 0
        var t = ""
        if(posun.text != null && posun.text.toString() != ""){
             p = posun.text.toString().toInt()
        }
        if(text.text != null && text.text.toString() != ""){
             t = text.text.toString()
        }
        vypisPosunutou(p)
        vysledek.text = posun(t, p)
    }

    fun vypisPosunutou (p: Int){

        if(posun.text != null && posun.text.toString() != "") {
            a.text = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
            ap.text = posun("a b c d e f g h i j k l m n o p q r s t u v w x y z", p)
        } else {
            a.text = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
            ap.text = posun("a b c d e f g h i j k l m n o p q r s t u v w x y z", 0)
        }
    }
    fun posun(s: String, p: Int): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        var t = s
        var result = ""

        t = t.lowercase()
        t = t.replace('á', 'a', ignoreCase = true)
        t = t.replace('é', 'e', ignoreCase = true)
        t = t.replace('í', 'i', ignoreCase = true)
        t = t.replace('ó', 'o', ignoreCase = true)
        t = t.replace('ú', 'u', ignoreCase = true)
        t = t.replace('ů', 'u', ignoreCase = true)
        t = t.replace('ý', 'y', ignoreCase = true)
        t = t.replace('ž', 'z', ignoreCase = true)
        t = t.replace('ř', 'r', ignoreCase = true)
        t = t.replace('č', 'c', ignoreCase = true)
        t = t.replace('š', 's', ignoreCase = true)
        t = t.replace('ě', 'e', ignoreCase = true)
        t = t.replace('ď', 'd', ignoreCase = true)
        t = t.replace('ť', 't', ignoreCase = true)
        t = t.replace('ň', 'n', ignoreCase = true)
        for(ch in t){
            if(!alphabet.contains(ch)){
                result += ch
            } else {
                var index = alphabet.indexOf(ch)
                if(smer.isChecked){
                    index -= p
                } else {
                    index += p
                }
                if (index >= 0) {
                    index %= alphabet.length
                } else {
                    index %= alphabet.length
                    index = index + alphabet.length
                }
                result += alphabet[index]
            }
        }
        return result
    }



}

