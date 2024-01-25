package com.example.rp_2024

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import com.example.rp_2024.ui.theme.RP_2024Theme
import java.util.Locale

class MainActivity : ComponentActivity() , View.OnClickListener {

    lateinit var btn : Button
    lateinit var text : EditText
    lateinit var posun : EditText
    lateinit var vysledek : TextView
    lateinit var ap : TextView
    lateinit var a : TextView
    lateinit var smer : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.sifruj)
        text = findViewById(R.id.text)
        posun = findViewById(R.id.posun)
        vysledek = findViewById(R.id.vysledek)
        ap = findViewById(R.id.posunuta)
        a = findViewById(R.id.abeceda)
        smer = findViewById(R.id.smer)

        btn.setOnClickListener(this)
        posun.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){

            } else {
                var p = 0

                if(posun.text == null){
                    p = 0
                } else {
                    p = posun.text.toString().toInt()
                }
                vypisPosunutou(p)
            }


        }

        smer.setOnCheckedChangeListener { _, isChecked ->
            var p = 0
            if(posun.text == null){
                p = 0
            } else {
                p = posun.text.toString().toInt()
            }
            vypisPosunutou(p)
        }

        /*  setContent {
              RP_2024Theme {
                  // A surface container using the 'background' color from the theme
                  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                      Greeting("Android")
                  }
              }
          } */

    }
    override fun onClick(v : View?) {
        var p = 0
        var t = ""
        if(posun.text == null){
             p = 0
        } else {
             p = posun.text.toString().toInt()
        }
        if(text.text == null){
             t = ""
        } else {
             t = text.text.toString()
        }
        vypisPosunutou(p)
        vysledek.text = posun(t, p);
    }

    fun vypisPosunutou (p: Int){

        if(posun.text != null && posun.text.toString() != "") {
            a.text = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
            ap.text = posun("a b c d e f g h i j k l m n o p q r s t u v w x y z", p)
        } else {
            a.text = ""
            ap.text = ""
        }
    }
    fun posun(s: String, p: Int): String {
        var alphabet = "abcdefghijklmnopqrstuvwxyz"
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
                result += ch;
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



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RP_2024Theme {
        Greeting("Android")
    }
}