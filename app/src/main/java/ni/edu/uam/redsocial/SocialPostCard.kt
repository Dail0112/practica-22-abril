package ni.edu.uam.redsocial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ni.edu.uam.redsocial.R
import ni.edu.uam.redsocial.Post
import ni.edu.uam.redsocial.ui.theme.*
import android.net.Uri

@Composable
fun CustomBottomBar(selectedTab: Int, isDarkMode: Boolean, onTabSelected: (Int) -> Unit) {
    val unselectedColor = if (isDarkMode) Color.White.copy(0.3f) else Color.White.copy(0.5f)
    Box(modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 16.dp, vertical = 20.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(65.dp),
            color = DeepPurple,
            shape = RoundedCornerShape(percent = 50),
            shadowElevation = 10.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onTabSelected(0) }) { Icon(Icons.Filled.Home, null, tint = if(selectedTab == 0) Color.White else unselectedColor) }
                IconButton(onClick = { onTabSelected(1) }) { Icon(Icons.Filled.Search, null, tint = if(selectedTab == 1) OliveGreen else unselectedColor) }
                Surface(
                    color = OliveGreen,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(42.dp),
                    onClick = { onTabSelected(2) }
                ) { Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Add, null, tint = DeepPurple) } }
                IconButton(onClick = { onTabSelected(3) }) { Icon(Icons.Outlined.Notifications, null, tint = if(selectedTab == 3) Color.White else unselectedColor) }
                IconButton(onClick = { onTabSelected(4) }) { Icon(Icons.Outlined.Person, null, tint = if(selectedTab == 4) Color.White else unselectedColor) }
            }
        }
    }
}

@Composable
fun SocialPostCard(
    post: Post,
    isDarkMode: Boolean,
    authorProfileImageUri: Uri? = null // <--- Agregamos esto
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // FOTO DE PERFIL DINÁMICA EN EL POST
                Box(modifier = Modifier.size(40.dp).clip(CircleShape)) {
                    if (authorProfileImageUri != null) {
                        AsyncImage(
                            model = authorProfileImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.profile_placeholder),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))
                Column {
                    Text(post.authorName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                    Text(post.timeAgo, fontSize = 12.sp, color = DarkTextSecondary)
                }
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.MoreHoriz, null, tint = DarkTextSecondary)
            }

            // ... (Resto del código de la imagen del contenido y texto del post igual)
            Spacer(Modifier.height(12.dp))
            if (post.imageUri != null || post.imageRes != null) {
                AsyncImage(
                    model = post.imageUri ?: post.imageRes,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(12.dp))
            }
            Text(post.contentText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            // ... (Resto de la tarjeta igual)
        }
    }
}