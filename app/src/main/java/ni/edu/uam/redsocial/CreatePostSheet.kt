package ni.edu.uam.redsocial

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ni.edu.uam.redsocial.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostContent(onPostCreated: (String, Uri?) -> Unit, onDismiss: () -> Unit) {
    var postText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } // Estado para la imagen

    // Lanzador del selector de fotos de Android
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nueva publicación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = postText,
            onValueChange = { postText = it },
            placeholder = { Text("¿Qué estás pensando?", color = DarkTextSecondary) },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (selectedImageUri == null) 150.dp else 100.dp), // Se hace más pequeño si hay foto
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OliveGreen,
                unfocusedBorderColor = DarkTextSecondary.copy(alpha = 0.3f),
                cursorColor = OliveGreen
            )
        )

        // PREVISUALIZACIÓN DE LA IMAGEN SELECCIONADA
        if (selectedImageUri != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                // AsyncImage de Coil para cargar la foto
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Misma altura que la imagen por defecto
                        .clip(RoundedCornerShape(15.dp)), // Mismos bordes
                    contentScale = ContentScale.Crop // Recorte perfecto
                )

                // Botón flotante para quitar la foto
                IconButton(
                    onClick = { selectedImageUri = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Quitar foto", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Fila de herramientas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    // Al hacer clic, abrimos la galería
                    photoPickerLauncher.launch(
                        androidx.activity.result.PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Image, contentDescription = null, tint = OliveGreen)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = if (selectedImageUri == null) "Agregar foto" else "Cambiar foto",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${postText.length}/280",
                fontSize = 12.sp,
                color = DarkTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (postText.isNotBlank() || selectedImageUri != null) {
                    onPostCreated(postText, selectedImageUri) // Ahora pasamos ambos datos
                    onDismiss()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepTeal,
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            // El botón se activa si hay texto o si hay imagen
            enabled = postText.isNotBlank() || selectedImageUri != null
        ) {
            Text("Publicar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}