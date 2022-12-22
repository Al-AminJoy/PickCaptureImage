package com.alamin.pickcaptureimage

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonCapture: Button
    private lateinit var buttonGallery: Button
    private lateinit var imageUri: Uri

    private val takePictureContract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        imageView.setImageURI(null)
        imageView.setImageURI(imageUri)
    }

    private val pickImageFromGalleryContract = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(null)
        imageView.setImageURI(it)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image)
        buttonCapture = findViewById(R.id.btnCapture)
        buttonGallery = findViewById(R.id.btnPick)

        imageUri = createImageUri()

        buttonCapture.setOnClickListener {
            takePictureContract.launch(imageUri)
        }

        buttonGallery.setOnClickListener {
            pickImageFromGalleryContract.launch("image/*")
        }

    }

    private fun createImageUri(): Uri {
        val image = File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,
            "com.alamin.pickcaptureimage.fileProvider",
            image
        )
    }
}