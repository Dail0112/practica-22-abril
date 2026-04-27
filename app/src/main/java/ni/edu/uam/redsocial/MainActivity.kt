package ni.edu.uam.redsocial

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ni.edu.uam.redsocial.CreatePostContent
import ni.edu.uam.redsocial.CustomBottomBar
import ni.edu.uam.redsocial.Post
import ni.edu.uam.redsocial.HomeScreen
import ni.edu.uam.redsocial.NotificationsScreen
import ni.edu.uam.redsocial.ProfileScreen
import ni.edu.uam.redsocial.SearchScreen
import ni.edu.uam.redsocial.ui.theme.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val systemInDark = isSystemInDarkTheme()

            // --- ESTADOS GLOBALES ---
            var isDarkMode by remember { mutableStateOf(systemInDark) }
            var selectedTab by remember { mutableIntStateOf(0) }
            var showSheet by remember { mutableStateOf(false) }

            // Este es el estado que guarda tu foto de perfil elegida
            var profileImageUri by remember { mutableStateOf<Uri?>(null) }

            // Lista de posts que se actualiza en tiempo real
            val postList = remember {
                mutableStateListOf(
                    Post(
                        contentText = "Nada como una buena vista para inspirarse 📸 ✨ 💜",
                        imageRes = R.drawable.post_imdage, // Verifica que el nombre sea correcto en tu carpeta res/drawable
                        timeAgo = "Hace 2 horas"
                    )
                )
            }

            // --- CONFIGURACIÓN DE COLORES ---
            val themeColors = if (isDarkMode) {
                darkColorScheme(
                    primary = OliveGreen,
                    background = DarkBgScaffold,
                    surface = DarkBgSurface,
                    onBackground = DarkTextPrimary,
                    onSurface = DarkTextPrimary
                )
            } else {
                lightColorScheme(
                    primary = DeepTeal,
                    background = LightCream,
                    surface = Color.White,
                    onBackground = DarkCharcoal,
                    onSurface = DarkCharcoal
                )
            }

            MaterialTheme(colorScheme = themeColors) {
                Scaffold(
                    bottomBar = {
                        CustomBottomBar(selectedTab, isDarkMode) { index ->
                            // El índice 2 es el botón central (+), abre el creador de posts
                            if (index == 2) showSheet = true else selectedTab = index
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    // --- NAVEGACIÓN ENTRE PANTALLAS ---
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        when (selectedTab) {
                            0 -> HomeScreen(
                                posts = postList,
                                isDarkMode = isDarkMode,
                                profileImageUri = profileImageUri // Pasamos la foto a los posts del Inicio
                            )
                            1 -> SearchScreen(
                                isDarkMode = isDarkMode
                            )
                            3 -> NotificationsScreen(
                                isDarkMode = isDarkMode
                            )
                            4 -> ProfileScreen(
                                isDarkMode = isDarkMode,
                                posts = postList,
                                profileImageUri = profileImageUri, // Pasamos la foto al Perfil
                                onProfileImageSelected = { newUri ->
                                    profileImageUri = newUri // Actualizamos la foto globalmente
                                },
                                onThemeToggle = { isDarkMode = !isDarkMode }
                            )
                        }
                    }

                    // --- VENTANA EMERGENTE (CREAR POST) ---
                    if (showSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false },
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 8.dp,
                            dragHandle = { BottomSheetDefaults.DragHandle(color = OliveGreen) },
                            modifier = Modifier.imePadding()
                        ) {
                            CreatePostContent(
                                onPostCreated = { text, uri ->
                                    // Añadimos el nuevo post arriba de la lista
                                    postList.add(0, Post(contentText = text, imageUri = uri))

                                    val msg = if (uri != null) "¡Publicado con foto!" else "¡Publicado!"
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

                                    showSheet = false
                                },
                                onDismiss = { showSheet = false }
                            )
                        }
                    }
                }
            }
        }
    }
}