package ramirez.ruben.closetvirtual.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import ramirez.ruben.closetvirtual.R
import java.io.OutputStream

object ImageExport {
    fun saveDrawablesToGallery(context: Context) {
        val drawables = listOf(
            R.drawable.tacones,
            R.drawable.sombrero,
            R.drawable.blusa_rosa,
            R.drawable.bolsa_negra,
            R.drawable.blusa_formal,
            R.drawable.tenis_blancos,
            R.drawable.blusa_deportiva,
            R.drawable.gorra_deportiva,
            R.drawable.pantalon_formal,
            R.drawable.short_deportivo,
            R.drawable.chaqueta_vinipiel,
            R.drawable.pantalon_vinipiel,
            R.drawable.sombrero_vinipiel,
            R.drawable.pantalon_mezclilla
        )

        drawables.forEach { resId ->
            try {
                val bitmap = BitmapFactory.decodeResource(context.resources, resId)
                val fileName = context.resources.getResourceEntryName(resId) + ".png"
                saveBitmapToGallery(context, bitmap, fileName)
            } catch (e: Exception) {
                Log.e("ImageExport", "Error guardando imagen $resId", e)
            }
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/ClosetVirtual")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            val outputStream: OutputStream? = resolver.openOutputStream(it)
            outputStream?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(it, contentValues, null, null)
            }
        }
    }
}
