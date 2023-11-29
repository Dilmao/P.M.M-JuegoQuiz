package com.iessanalbertomagno.dam2.juegoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iessanalbertomagno.dam2.juegoquiz.data.listaPreguntas
import com.iessanalbertomagno.dam2.juegoquiz.data.mapaPreguntas
import com.iessanalbertomagno.dam2.juegoquiz.ui.theme.JuegoQuizTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuegoQuizTheme {
                for (pregunta in mapaPreguntas) {
                    listaPreguntas.add(pregunta.key)
                }
                listaPreguntas.shuffle()
                listaPreguntas.add("¡¡Felicidades por completar el juego!!")
                BodyContent()
            }
        }
    }
}


@Composable
fun BodyContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        //Declaracion de variables
        var respuesta by rememberSaveable { mutableStateOf("") }
        var numPartida by rememberSaveable { mutableStateOf(0) }
        var puntuacion by rememberSaveable { mutableStateOf(0) }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Puntuación")
                Text(text = " $puntuacion", modifier = Modifier.padding(end = 15.dp))
            }
        }

        //Cuerpo del codigo
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Imagen: Mando consola
            Image(
                painter = painterResource(id = R.drawable.mando),
                contentDescription = "Mando consola"
            )

            //Pregunta
            Text(text = listaPreguntas.get(numPartida), Modifier.padding(20.dp))

            if (numPartida < (listaPreguntas.size - 1)) {
                //El usuario introduce la respuesta
                TextField(value = respuesta, onValueChange = { respuesta = it })

                //Boton validar
                Button(onClick = {
                    if (mapaPreguntas.get(listaPreguntas.get(numPartida))
                            .equals(respuesta, ignoreCase = true)
                    ) {
                        Toast.makeText(context, "Respuesta correcta", Toast.LENGTH_SHORT).show()
                        puntuacion += 10
                    } else {
                        Toast.makeText(
                            context,
                            "Respuesta incorrecta, la respuesta correcta es: ${
                                mapaPreguntas.get(listaPreguntas.get(numPartida))
                            }",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    respuesta = ""
                    numPartida++

                    if (numPartida == 10) {
                        println("Pregunta final")
                    }
                }) {
                    Text(text = "Validar")
                }
            } else {
                Text(text = "Puntuación: $puntuacion/100")
            }
        }
    }
}