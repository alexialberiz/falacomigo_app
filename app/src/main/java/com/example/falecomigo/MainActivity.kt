package com.example.falecomigo

import android.os.Bundle
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.falecomigo.ui.theme.FaleComigoTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaleComigoTheme {
                var showSplash by remember { mutableStateOf(true) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        SplashScreen {
                            showSplash = false
                        }
                    } else {
                        FaleComigoScreen()
                    }
                }
            }
        }
    }
}

// Splash screen com gradiente e balão de pensamento
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val gradiente = Brush.verticalGradient(
        listOf(
            Color(0xFF4B1248),
            Color(0xFF801336),
            Color(0xFFC72C41)
        )
    )

    LaunchedEffect(Unit) {
        delay(2500)
        onTimeout()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradiente),
            contentAlignment = Alignment.Center
        ) {
            ThoughtBubbleLogo(modifier = Modifier.size(150.dp))
        }
    }
}

// Logo balão de pensamento simples
@Composable
fun ThoughtBubbleLogo(modifier: Modifier = Modifier) {
    val beige = Color(0xFFF5F5DC)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Círculo principal (o balão)
        drawCircle(
            color = beige,
            radius = w.coerceAtMost(h) / 2 * 0.7f,
            center = center
        )

        // Cauda do balãozinho (triângulo arredondado)
        val path = Path().apply {
            moveTo(center.x - w * 0.15f, center.y + h * 0.25f)
            lineTo(center.x, center.y + h * 0.40f)
            lineTo(center.x + w * 0.15f, center.y + h * 0.25f)
            close()
        }
        drawPath(path, color = beige)
    }
}

// Tela principal do app
@Composable
fun FaleComigoScreen() {
    val perguntasPorCategoria = mapOf(
        "Reflexivas" to listOf(
            "Qual a sua melhor lembrança?",
            "Se você pudesse ter um superpoder, qual seria?",
            "Você prefere saber o futuro ou mudar o passado?",
            "Qual foi o momento mais corajoso da sua vida?"
        ),
        "Engraçadas" to listOf(
            "Qual a coisa mais engraçada que já te aconteceu?",
            "Se fosse um animal, qual seria?",
            "Qual seria o título da sua autobiografia?",
            "Se sua vida fosse um filme, qual seria o gênero?"
        ),
        "Relacionamentos" to listOf(
            "Você acredita em destino?",
            "Se pudesse jantar com qualquer pessoa, viva ou morta, quem seria?",
            "Qual seu maior sonho no momento?",
            "Você prefere praia ou montanha?"
        )
    )

    var categoriaSelecionada by remember { mutableStateOf("Reflexivas") }
    var pergunta by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val perguntas = perguntasPorCategoria[categoriaSelecionada] ?: emptyList()
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.click_sound) }

    val gradiente = Brush.verticalGradient(
        listOf(
            Color(0xFF4B1248),
            Color(0xFF801336),
            Color(0xFFC72C41)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradiente)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "💬 Fale Comigo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botão de categoria
        Box {
            Button(
                onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Categoria: $categoriaSelecionada", color = Color(0xFF801336))
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                perguntasPorCategoria.keys.forEach { categoria ->
                    DropdownMenuItem(onClick = {
                        categoriaSelecionada = categoria
                        pergunta = ""
                        expanded = false
                    }) {
                        Text(categoria)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Pergunta com card animado
        AnimatedVisibility(
            visible = pergunta.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Card(
                backgroundColor = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pergunta,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Botão principal
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                pergunta = perguntas.random()
                mediaPlayer.start()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                "Me pergunte algo!",
                color = Color(0xFF801336),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
