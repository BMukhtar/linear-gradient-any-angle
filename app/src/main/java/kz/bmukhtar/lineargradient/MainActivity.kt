package kz.bmukhtar.lineargradient


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kz.bmukhtar.lineargradient.ui.theme.LinearGradientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinearGradientTheme {
                // A surface container using the 'background' color from the theme
                Gradients()
            }
        }
    }
}

@Composable
fun Gradients() {
    val angles = mutableListOf(0f, 22.5f, 45f, 60f, 90f, 135f, 180f, 225f, 270f, 315f, 360f, 390f)
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(angles) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GradientBox(it)
                    Text(modifier = Modifier.padding(10.dp), text = "angle: $it")
                }
            }
        }
    }
}

@Composable
private fun GradientBox(angle: Float) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(
                Brush.linearGradient(
                    0.1205f to Color(0xff01579b),
                    0.8785f to Color(0xffffab91),
                    angleInDegrees = angle,
                )
            )
            .size(150.dp, 100.dp)
    )
}

