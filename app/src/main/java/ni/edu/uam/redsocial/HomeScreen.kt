package ni.edu.uam.redsocial

import android.net.Uri // <--- IMPORTANTE
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.redsocial.SocialPostCard
import ni.edu.uam.redsocial.Post

@Composable
fun HomeScreen(
    posts: List<Post>,
    isDarkMode: Boolean,
    profileImageUri: Uri? // <--- LA PANTALLA RECIBE LA VARIABLE AQUÍ
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Text(
            text = "Inicio",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(posts) { post ->
                SocialPostCard(
                    post = post,
                    isDarkMode = isDarkMode,
                    authorProfileImageUri = profileImageUri // <--- Y SE LA PASA A LA TARJETA AQUÍ
                )
            }
        }
    }
}