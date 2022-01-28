package mx.kodemia.appdenombre2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_enviar = findViewById<Button>(R.id.btn_enviar)
        val tv_respuesta = findViewById<TextView>(R.id.tv_respuesta)
        val tiet_nombre = findViewById<TextInputEditText>(R.id.tiet_nombre)

        btn_enviar.setOnClickListener {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = "https://api.genderize.io?name="+tiet_nombre.text
            val request = JsonObjectRequest(Request.Method.GET,url,null,{
                response ->
                Log.d(TAG,response.toString())
                val json = JSONObject(response.toString())
                val genero = when(json["gender"]){
                    "male" -> "Hombre"
                    "female" -> "Mujer"
                    else -> "No se que eres"
                }
                tv_respuesta.visibility = View.VISIBLE
                tv_respuesta.text = getString(R.string.tv_respuesta,genero)
            },{
                error ->
                Log.e(TAG,error.toString())
            })
            queue.add(request)
        }
    }
}