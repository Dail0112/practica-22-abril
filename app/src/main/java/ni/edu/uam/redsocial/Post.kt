package ni.edu.uam.redsocial

import android.net.Uri

data class Post(
    val id: Long = System.currentTimeMillis(),
    val authorName: String = "María González",
    val authorHandle: String = "@mariagonzalez",
    val timeAgo: String = "Ahora mismo",
    val contentText: String,
    val imageUri: Uri? = null, // Para fotos de la galería
    val imageRes: Int? = null  // Para fotos por defecto
)