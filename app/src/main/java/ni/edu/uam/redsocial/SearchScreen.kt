package ni.edu.uam.redsocial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.redsocial.ui.theme.*

@Composable
fun SearchScreen(isDarkMode: Boolean) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf("Para ti", "UX/UI", "Fotografía", "Viajes", "Tecnología")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Explorar",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Barra de Búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar creadores, posts o tags...", color = DarkTextSecondary) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = OliveGreen) },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OliveGreen,
                unfocusedBorderColor = DarkTextSecondary.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Categorías (Chips)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEachIndexed { index, category ->
                val isSelected = index == 0
                Surface(
                    color = if (isSelected) OliveGreen else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp),
                    border = if (!isSelected) BorderStroke(1.dp, DarkTextSecondary.copy(alpha = 0.3f)) else null
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) DeepPurple else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contenedor de contenido sugerido
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("El contenido en tendencia aparecerá aquí", color = DarkTextSecondary)
        }

        Spacer(Modifier.height(80.dp)) // Espacio para la BottomBar
    }
}