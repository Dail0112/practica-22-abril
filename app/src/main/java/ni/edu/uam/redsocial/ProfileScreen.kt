package ni.edu.uam.redsocial

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ni.edu.uam.redsocial.R
import ni.edu.uam.redsocial.SocialPostCard
import ni.edu.uam.redsocial.Post
import ni.edu.uam.redsocial.ui.theme.*

@Composable
fun ProfileScreen(
    isDarkMode: Boolean,
    posts: List<Post>,
    profileImageUri: Uri?, // Recibimos la foto de perfil
    onProfileImageSelected: (Uri) -> Unit, // Función para avisar que se eligió una foto
    onThemeToggle: () -> Unit
) {
    // Preparar el lanzador de la galería
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onProfileImageSelected(uri)
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        HeaderSection(
            isDarkMode = isDarkMode,
            profileImageUri = profileImageUri,
            onImageClick = {
                // Al hacer clic, abrimos la galería buscando solo imágenes
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onThemeToggle = onThemeToggle
        )

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Diseñadora UX/UI apasionada por crear\nexperiencias increíbles. Café, viajes y fotografía.",
                color = if (isDarkMode) DarkTextPrimary else DarkCharcoal,
                textAlign = TextAlign.Center, fontSize = 14.sp, modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem(posts.size.toString(), "Publicaciones", OliveGreen)
                StatItem("2.4K", "Seguidores", if(isDarkMode) ButtonGreenDark else DeepTeal)
                StatItem("560", "Siguiendo", PinkAcent)
            }
            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}, modifier = Modifier.weight(1.2f).height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = if(isDarkMode) ButtonGreenDark else DeepTeal), shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.PersonAddAlt1, null, modifier = Modifier.size(18.dp)); Text(" Seguir", fontSize = 14.sp)
                }
                Button(onClick = {}, modifier = Modifier.weight(1.2f).height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = if(isDarkMode) ButtonPurpleDark else DeepPurple), shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, modifier = Modifier.size(16.dp)); Text(" Mensaje", fontSize = 14.sp)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        ProfileTabs()
        Spacer(Modifier.height(16.dp))

        // ... dentro de ProfileScreen
        posts.forEach { post ->
            SocialPostCard(
                post = post,
                isDarkMode = isDarkMode,
                authorProfileImageUri = profileImageUri // <--- Pasar URI
            )
        }
        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun HeaderSection(
    isDarkMode: Boolean,
    profileImageUri: Uri?,
    onImageClick: () -> Unit,
    onThemeToggle: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().height(290.dp)) {
        // Fondo curvo
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height * 0.65f)
                cubicTo(size.width * 0.8f, size.height * 0.95f, size.width * 0.2f, size.height * 0.55f, 0f, size.height * 0.75f)
                close()
            }
            drawPath(path, color = OliveGreen)
        }

        // Botones superiores
        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null, tint = DeepPurple, modifier = Modifier.background(Color.White.copy(0.2f), CircleShape).padding(8.dp))
            IconButton(onClick = onThemeToggle) { Icon(if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode, null, tint = DeepPurple) }
        }

        // Información del Perfil (Avatar cliqueable)
        Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally) {

            // CONTENEDOR DE LA FOTO DE PERFIL
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(3.dp, OliveGreen, CircleShape)
                    .clickable { onImageClick() }, // Hacerlo cliqueable
                contentAlignment = Alignment.Center
            ) {
                // Si el usuario eligió una foto, usar AsyncImage
                if (profileImageUri != null) {
                    AsyncImage(
                        model = profileImageUri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Si no, mostrar el placeholder por defecto
                    Image(
                        painterResource(R.drawable.profile_placeholder),
                        contentDescription = "Foto de perfil por defecto",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Un pequeño icono semi-transparente para indicar que se puede cambiar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                )
                Icon(Icons.Default.PhotoCamera, contentDescription = "Cambiar foto", tint = Color.White.copy(alpha = 0.7f))
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("María González", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = if(isDarkMode) DarkTextPrimary else DeepTeal)
                Icon(Icons.Filled.Stars, null, tint = OliveGreen, modifier = Modifier.size(18.dp))
            }
            Text("@mariagonzalez", fontSize = 14.sp, color = PinkAcent)
        }
    }
}

// ... (StatItem y ProfileTabs se mantienen igual) ...

@Composable
fun StatItem(count: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 12.sp, color = DarkTextSecondary)
    }
}

@Composable
fun ProfileTabs() {
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.GridView, null, tint = OliveGreen)
                Spacer(Modifier.height(12.dp))
                Box(modifier = Modifier.width(40.dp).height(2.dp).background(OliveGreen))
            }
            Icon(Icons.Default.BookmarkBorder, null, tint = DarkTextSecondary)
            Icon(Icons.Default.PersonOutline, null, tint = DarkTextSecondary)
        }
        HorizontalDivider(color = DarkTextSecondary.copy(alpha = 0.2f), thickness = 1.dp)
    }
}