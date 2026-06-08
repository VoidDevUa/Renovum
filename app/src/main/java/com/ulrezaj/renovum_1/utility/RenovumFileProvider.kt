package com.ulrezaj.renovum_1.utility

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object RenovumFileProvider {
	/**
	 * Відкриває файл через зовнішній додаток (наприклад, Word / Google Документи)
	 */
	fun openFile(context: Context, file: File) {
		try {
			val uri =
				FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
			val intent = Intent(Intent.ACTION_VIEW).apply {
				setDataAndType(
					uri,
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
				)
				addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
				addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			}
			context.startActivity(intent)
		} catch (e: Exception) {
			L.e("FileActionDialog: Не вдалося відкрити файл", e)
			Handler(Looper.getMainLooper()).post {
				Toast.makeText(context, "Немає додатка для відкриття файлів Word", Toast.LENGTH_SHORT).show()
			}
		}
	}

	/**
	 * Викликає системне вікно "Поділитися"
	 */
	fun shareFile(context: Context, file: File) {
		try {
			val uri =
				FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
			val intent = Intent(Intent.ACTION_SEND).apply {
				type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
				putExtra(Intent.EXTRA_STREAM, uri)
				addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
			}
			context.startActivity(Intent.createChooser(intent, "Надіслати кошторис через:"))
		} catch (e: Exception) {
			L.e("FileActionDialog: Помилка при спробі поділитися файлом", e)
		}
	}

	/**
	 * Дублює файл у публічну теку /Documents/Renovum для доступу через провідник.
	 * Повертає true у разі успіху, false — якщо виникла помилка.
	 */
	fun saveToPublicDocuments(context: Context, file: File): Boolean {
		return try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				val contentValues = ContentValues().apply {
					put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
					put(
						MediaStore.MediaColumns.MIME_TYPE,
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
					)
					put(
						MediaStore.MediaColumns.RELATIVE_PATH,
						"${Environment.DIRECTORY_DOCUMENTS}/Renovum"
					)
				}
				val contentResolver = context.contentResolver
				val uri = contentResolver.insert(
					MediaStore.Files.getContentUri("external"),
					contentValues
				)

				if (uri != null) {
					contentResolver.openOutputStream(uri).use { mediaOut ->
						file.inputStream().use { input ->
							mediaOut?.let { input.copyTo(it) }
						}
					}
					true
				} else {
					false
				}
			} else {
				val documentsDir =
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
				val renovumDir = File(documentsDir, "Renovum")
				if (!renovumDir.exists()) renovumDir.mkdirs()

				val publicFile = File(renovumDir, file.name)
				file.inputStream().use { input ->
					FileOutputStream(publicFile).use { output ->
						input.copyTo(output)
					}
				}
				true
			}
		} catch (e: Exception) {
			L.e("FileActionDialog: Не вдалося зберегти у публічну теку", e)
			false
		}
	}
}