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
import ni.edu.uam.redsocial.R
import ni.edu.uam.redsocial.ui.theme.*



@Composable
fun SocialPostCard(isDarkMode: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.profile_placeholder), null, Modifier.size(40.dp).clip(CircleShape))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("María González", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                    Text("Hace 2 horas", fontSize = 12.sp, color = DarkTextSecondary)
                }
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.MoreHoriz, null, tint = DarkTextSecondary)
            }
            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(R.drawable.post_imdage), // Asegúrate del nombre de tu imagen
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(12.dp))
            Text("Nada como una buena vista para inspirarse 📸 ✨ 💜", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = DarkTextSecondary.copy(alpha = 0.2f), thickness = 0.5.dp)
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Favorite, null, tint = Color(0xFF6B4B6B), modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("1.2K", fontSize = 12.sp, color = DarkTextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.ChatBubbleOutline, null, tint = DarkTextSecondary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("128", fontSize = 12.sp, color = DarkTextSecondary)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Share, null, tint = ButtonGreenDark, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("56", fontSize = 12.sp, color = DarkTextSecondary)
                }
            }
        }
    }
}