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
import androidx.compose.ui.tooling.preview.Preview
import com.example.rp_2024.ui.theme.RP_2024Theme

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
                vypisPosunutou()
            }


        }

        smer.setOnCheckedChangeListener { _, isChecked ->
            vypisPosunutou()
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
        var p = posun.text.toString().toInt()
        var t = text.text.toString()

        vypisPosunutou()
        vysledek.text = posun(t, p);
    }

    fun vypisPosunutou (){
        if(posun.text != null && posun.text.toString() != "") {
            a.text = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
            ap.text = posun(
                "a b c d e f g h i j k l m n o p q r s t u v w x y z",
                posun.text.toString().toInt()
            )
        } else {
            a.text = ""
            ap.text = ""
        }
    }
    fun posun(t: String, p: Int): String {
        var alphabet = "abcdefghijklmnopqrstuvwxyz"

        var result = ""
        t.forEach { it.lowercaseChar() }
        t.replace('á', 'a', ignoreCase = true)
        t.replace('é', 'e', ignoreCase = true)
        t.replace('í', 'i', ignoreCase = true)
        t.replace('ó', 'o', ignoreCase = true)
        t.replace('ú', 'u', ignoreCase = true)
        t.replace('ů', 'u', ignoreCase = true)
        t.replace('ý', 'y', ignoreCase = true)
        t.replace('ž', 'z', ignoreCase = true)
        t.replace('ř', 'r', ignoreCase = true)
        t.replace('č', 'c', ignoreCase = true)
        t.replace('š', 's', ignoreCase = true)
        t.replace('ě', 'e', ignoreCase = true)
        t.replace('ď', 'd', ignoreCase = true)
        t.replace('ť', 't', ignoreCase = true)
        t.replace('ň', 'n', ignoreCase = true)
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