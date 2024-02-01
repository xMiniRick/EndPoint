package com.example.endpointactivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class DetalleElementoActivity : AppCompatActivity() {

    private lateinit var tituloTextView: TextView
    private lateinit var imagenImageView: ImageView
    private lateinit var autorTextView: TextView
    private lateinit var contenidoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_elemento)

        tituloTextView = findViewById(R.id.tituloTextView)
        imagenImageView = findViewById(R.id.imagenImageView)
        autorTextView = findViewById(R.id.autorTextView)
        contenidoTextView = findViewById(R.id.contenidoTextView)


        val elementoId = intent.getIntExtra("elementoId", -1)

        if (elementoId != -1) {
            obtenerDetallesElemento(elementoId)
        }
    }

    private fun obtenerDetallesElemento(elementoId: Int) {
        val url = "https://notigram.com/wp-json/wp/v2/posts/$elementoId?_embed"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // Procesar la respuesta y actualizar las vistas
                val titulo = response.getJSONObject("title").getString("rendered")
                val autor = response.getJSONObject("_embedded")
                    .getJSONArray("author")
                    .getJSONObject(0)
                    .getString("name")
                val contenido = response.getJSONObject("content").getString("rendered")

                tituloTextView.text = titulo
                autorTextView.text = "Autor: $autor"
                contenidoTextView.text = contenido

            },
            Response.ErrorListener { error ->
                // Manejar errores de la solicitud
                error.printStackTrace()
            })

        // Agregar la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(request)
    }
}
