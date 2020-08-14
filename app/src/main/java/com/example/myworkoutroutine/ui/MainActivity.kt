package com.example.myworkoutroutine.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myworkoutroutine.navigation.NavigationIconClickListener
import com.example.myworkoutroutine.R
import com.example.myworkoutroutine.database.entity.Exercise
import com.example.myworkoutroutine.database.entity.ToolbarImageEntity
import com.example.myworkoutroutine.database.repo.MuscleGroupRepo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.backdrop.*
import java.io.ByteArrayOutputStream

//second commit

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        const val TAG = "MainActivity"
        lateinit var currentDay: String
        private val EXTERNAL_STORAGE_CODE = 1
        private val GALLERY_REQUEST_CODE = 2
        private val CAMERA_PICK_CODE = 3
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setSupportActionBar(toolbar)
        context = applicationContext
        navController = findNavController(R.id.nav_host_fragment)
        currentDay = getString(R.string.monday_day_1)

        initNavigationIcon()
        initNavigationItems()

        addAllExercisesToDatabase()

        setToolbarBackgroundIfCan()
        collapsing_toolbar.setOnClickListener {
            pickImageFromDialog()
        }

    }

    private fun initNavigationIcon() {
        toolbar.setNavigationOnClickListener (
            NavigationIconClickListener(
                this,
                product_grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_menu
                ),
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_close_24
                )
            )
        )
    }

    private fun initNavigationItems() {
        monday_button.setOnClickListener {
            currentDay = getString(R.string.monday_day_1)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
        tuesday_button.setOnClickListener {
            currentDay = getString(R.string.tuesday_day_2)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
        wednesday_button.setOnClickListener {
            currentDay = getString(R.string.wednesday_day_3)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
        thursday_button.setOnClickListener {
            currentDay = getString(R.string.thursday_day_4)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
        friday_button.setOnClickListener {
            currentDay = getString(R.string.friday_day_5)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
        saturday_button.setOnClickListener {
            currentDay = getString(R.string.saturday_day_6)
            navController.navigate(R.id.action_firstDayFragment_to_firstDayFragment)
        }
    }

    private fun addExercise(exerciseName: String, muscleName: String,
                            drawable: Drawable?, durationSec: Int) {
        val image: Drawable = drawable ?: getDrawable(R.drawable.ic_launcher_background)!!
        val bitmap: Bitmap = image.toBitmap()
        val stream: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData: ByteArray = stream.toByteArray()

        val exercise = Exercise(
            exerciseName,
            muscleName,
            durationSec,
            bitmapData
        )

        MuscleGroupRepo(this).addExercise(exercise)

    }

    private fun addAllExercisesToDatabase() {
        //abs
        addExercise("Two Up One Down",
            getString(R.string.abs),
            getDrawable(R.drawable.two_up_one_down_icon),
            60
            )
        addExercise("Seated Abs Circles",
            getString(R.string.abs),
            getDrawable(R.drawable.seated_abs_circle_icon),
            60
            )
        addExercise("Twisted Mountain Climbers",
            getString(R.string.abs),
            getDrawable(R.drawable.twisted_mountain_climbers_icon),
            60
        )
        addExercise("Starfish Crunches",
            getString(R.string.abs),
            getDrawable(R.drawable.starfish_crunches_icon),
            30
        )
        addExercise("Abs Scissors",
            getString(R.string.abs),
            getDrawable(R.drawable.abs_scissors_icon),
            60
        )
        addExercise("Knee Tucks",
            getString(R.string.abs),
            getDrawable(R.drawable.knee_tucks_icon),
            60
        )
        addExercise("Jackknife",
            getString(R.string.abs),
            getDrawable(R.drawable.jackknife_icon),
            60
            )
        addExercise("Marching plank",
            getString(R.string.abs),
            getDrawable(R.drawable.marching_plank_icon),
            60
        )
        addExercise("Abs Bicycle",
            getString(R.string.abs),
            getDrawable(R.drawable.abs_bicycle_icon),
            60
        )
        addExercise("Hanging Legs Lift",
            getString(R.string.abs),
            getDrawable(R.drawable.hanging_legs_lift_icon),
            60
            )


        //chest
        addExercise("Bench Press",
            getString(R.string.chest),
            getDrawable(R.drawable.benchpress_icon),
            0
        )
        addExercise("Incline Bench Press",
            getString(R.string.chest),
            getDrawable(R.drawable.incline_bench_press_icon),
            0
            )
        addExercise("Dips",
            getString(R.string.chest),
            getDrawable(R.drawable.dips_icon),
            0
            )
        //triceps
        addExercise("Barbell Triceps Extension",
            getString(R.string.triceps),
            getDrawable(R.drawable.barbell_tricep_extension_icon),
            0
            )
        addExercise("Bench Dips",
            getString(R.string.triceps),
            getDrawable(R.drawable.bench_dips_icon),
            0
            )
        //legs
        addExercise("Kettlebell Squats",
            getString(R.string.legs),
            getDrawable(R.drawable.kettlebell_squats_icon),
            0
        )
        addExercise("Walking Lunges",
            getString(R.string.legs),
            getDrawable(R.drawable.walking_lunges_icon),
            0
        )
        //back
        addExercise("Pull Ups",
            getString(R.string.back),
            getDrawable(R.drawable.pull_ups_icon),
            0
            )
        addExercise("Inclined Pull Ups",
            getString(R.string.back),
            getDrawable(R.drawable.inclined_pull_ups_icon),
            0
        )
        addExercise("Bent Over Barbell Row",
            getString(R.string.back),
            getDrawable(R.drawable.bent_over_barbell_row_icon),
            0
        )
        addExercise("HyperExtension",
            getString(R.string.back),
            getDrawable(R.drawable.hyperextension_icon),
            0
            )
        //biceps
        addExercise("Bicep Curls",
            getString(R.string.biceps),
            getDrawable(R.drawable.bicep_curls_icon),
            0
        )
        addExercise("Chin Ups",
            getString(R.string.biceps),
            getDrawable(R.drawable.chin_ups_icon),
            0
        )
        //shoulders
        addExercise("Seated Bent Over Dumbbell",
            getString(R.string.shoulders),
            getDrawable(R.drawable.seated_bent_over_dumbbell_icon),
            0
            )
        addExercise("Overhead Press",
            getString(R.string.shoulders),
            getDrawable(R.drawable.overhead_press_icon),
            0
        )

    }

    private fun pickPictureFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun checkPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            askPermission()
            return false
        } else {
            return true
        }

    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), EXTERNAL_STORAGE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission was denied", Toast.LENGTH_SHORT).show()
                } else {
                    pickPictureFromGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "On Activity Result: data: $data")

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val imageUri: Uri = data?.data!!
                    MuscleGroupRepo(this).insertToolbarImage(
                        ToolbarImageEntity(
                            0,
                            imageUri.toString()
                        )
                    )
                    setImageToCollapsingToolbar(imageUri)
                }
                CAMERA_PICK_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val imageUri = fromBitmapToUri(this, imageBitmap)
                    Log.d(TAG, "Camera pick code data: $data")
                    Log.d(TAG, "Camera pick code imageBitmap: $imageBitmap")
                    Log.d(TAG, "Camera pick code imageBitmap: $imageUri")
                    MuscleGroupRepo(this).insertToolbarImage(
                        ToolbarImageEntity(
                            0,
                            imageUri.toString()
                        )
                    )
                    setImageToCollapsingToolbar(imageUri)
                }
            }
        }

//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val imageUri: Uri = data?.data!!
//            MuscleGroupRepo(this).insertToolbarImage(
//                ToolbarImageEntity(
//                    0,
//                    imageUri.toString()
//                )
//            )
//            setImageToCollapsingToolbar(imageUri)
//        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setImageToCollapsingToolbar(image: Uri?) {
        Log.d(TAG, "SetImageCollapsingToolbar. image: $image")
        if (image == null) return

        val inputStream = contentResolver.openInputStream(image)
        val drawable = Drawable.createFromStream(inputStream, image.toString())
        collapsing_toolbar.background = drawable
    }

    private fun pickImageFromDialog() {
        val items = arrayOf<CharSequence>("Gallery", "Camera", "Default")
        val dialog = AlertDialog.Builder(this)
        dialog
            .setTitle("Pick from")
            .setCustomTitle(View.inflate(this, R.layout.dailog_title, null))
            .setItems(items, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (items[which]) {
                        items[0] -> {
                             if (checkPermission()) pickPictureFromGallery()
                         }
                        items[1] -> {
                            startCameraActivity()
                        }
                        items[2] -> {
                            collapsing_toolbar.background = ContextCompat.getDrawable(context, R.drawable.beast_mode)
                            MuscleGroupRepo(context).deleteToolbarImage()
                        }
                    }
                }

            })
        dialog.create().show()
    }

    private fun setToolbarBackgroundIfCan() {
        val toolbarImageList = MuscleGroupRepo(this).getToolbarImage()
        if (!toolbarImageList.isEmpty()) {
            val imageUri: Uri = toolbarImageList[0].uri.toUri()
            val inputStream = contentResolver.openInputStream(imageUri)
            val drawable = Drawable.createFromStream(inputStream, imageUri.toString())
            collapsing_toolbar.background = drawable
        }
    }

    private fun startCameraActivity() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_PICK_CODE)
    }

    private fun fromBitmapToUri(context: Context, imageBitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            imageBitmap,
            "Background",
            null)
        return Uri.parse(path)
    }

}