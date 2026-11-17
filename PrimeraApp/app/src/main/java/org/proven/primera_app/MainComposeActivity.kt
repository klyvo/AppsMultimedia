package org.proven.primera_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.proven.primera_app.ui.theme.PrimeraappTheme

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeraappTheme { TrileroGame() }
        }
    }
}

@Composable
fun TrileroGame() {
    var isStarted by remember { mutableStateOf(false) }
    var cubs by remember { mutableIntStateOf(2) }
    var posBall by remember { mutableIntStateOf(1) }
    var selected by remember { mutableStateOf<Int?>(null) }
    var msg by remember { mutableStateOf("Prem Start per començar!") }

    fun startNewRound() {
        posBall = (1..cubs).random()
        selected = null
        msg = "Cubiletes: $cubs. ¡Adivina!"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.fondo), contentScale = ContentScale.Crop),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(msg, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))

            if (!isStarted) {
                Button(onClick = {
                    cubs = 2
                    isStarted = true
                    startNewRound()
                }) {
                    Text("START", fontSize = 22.sp)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(minOf(cubs, 3)),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(cubs) { i ->
                        val id = i + 1
                        val img = when {
                            selected == null -> R.drawable.cubilete
                            id == posBall -> R.drawable.levantaok
                            id == selected -> R.drawable.levantafail
                            else -> R.drawable.cubilete
                        }
                        Image(
                            painter = painterResource(img),
                            contentDescription = null,
                            modifier = Modifier
                                .size(90.dp)
                                .padding(4.dp)
                                .clickable(enabled = selected == null) {
                                    selected = id
                                    msg = if (id == posBall) "¡Has ganado! +1 Cubilete" else "¡Has perdido!"
                                }
                        )
                    }
                }

                if (selected != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    if (selected == posBall) {
                        Button(onClick = {
                            cubs++
                            startNewRound()
                        }) {
                            Text("Siguiente Nivel (${cubs + 1} cubos)", fontSize = 18.sp)
                        }
                    } else {
                        Button(onClick = {
                            isStarted = false
                            msg = "Prem Start per començar!"
                        }) {
                            Text("Volver al Inicio", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}
