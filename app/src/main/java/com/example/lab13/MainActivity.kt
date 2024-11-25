package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//Felipe Hebert Ochoa Patiñp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedScreen()
        }
    }
}

@Composable
fun AnimatedScreen() {
    // Estado para controlar el tamaño y el color del cuadro
    var isClicked by remember { mutableStateOf(false) }

    // Estado para alternar entre modo claro y oscuro
    var isDarkMode by remember { mutableStateOf(false) }

    // Animación de tamaño y color para el cuadro
    val size by animateDpAsState(
        targetValue = if (isClicked) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing) // Animación personalizada
    )

    val color by animateColorAsState(
        targetValue = if (isClicked) Color.Green else Color.Blue,
        animationSpec = tween(durationMillis = 800) // Animación personalizada
    )

    // Animación de la visibilidad y movimiento del botón
    var isButtonVisible by remember { mutableStateOf(true) }
    val offsetX by animateDpAsState(
        targetValue = if (isButtonVisible) 0.dp else 300.dp,
        animationSpec = tween(durationMillis = 500)
    )

    // Transición de contenido para el modo claro/oscuro
    val backgroundColor = if (isDarkMode) Color.Black else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black

    // Diseño de la pantalla
    Surface(modifier = Modifier.fillMaxSize(), color = backgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(16.dp)
        ) {
            // Cuadro que cambia de tamaño y color
            Box(
                modifier = Modifier
                    .size(size)
                    .background(color)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón que se mueve y desaparece
            AnimatedVisibility(
                visible = isButtonVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500)) + slideOutHorizontally(animationSpec = tween(500))
            ) {
                Button(onClick = { isButtonVisible = !isButtonVisible }) {
                    Text("Desaparecer/Mover Botón")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para alternar el modo oscuro/claro
            Button(
                onClick = {
                    isDarkMode = !isDarkMode
                },
                modifier = Modifier.offset(x = offsetX)
            ) {
                Text("Cambiar Modo (Claro/Oscuro)", color = textColor)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para cambiar el tamaño y color del cuadro
            Button(onClick = {
                isClicked = !isClicked
            }) {
                Text("Cambiar Tamaño y Color")
            }
        }
    }
}


