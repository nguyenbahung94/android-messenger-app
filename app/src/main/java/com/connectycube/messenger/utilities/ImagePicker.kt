package com.connectycube.messenger.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.connectycube.messenger.R
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import timber.log.Timber
import java.io.File

const val REQUEST_CODE_CHOOSE = 23

fun requestImage(activity: Activity) {
    Matisse.from(activity)
        .choose(MimeType.ofImage(), false)
        .countable(false)
        .capture(true)
        .captureStrategy(
            CaptureStrategy(true, "com.connectycube.messenger.fileprovider")
        )
        .maxSelectable(1)
//                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
        .gridExpectedSize(
            activity.resources.getDimensionPixelSize(R.dimen.grid_expected_size)
        )
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        .thumbnailScale(0.85f)
        .imageEngine(Glide4Engine())
        .setOnSelectedListener { uriList, pathList ->
            // DO SOMETHING IMMEDIATELY HERE
            Timber.d("onSelected= pathList=$pathList")
        }
        .originalEnable(true)
        .maxOriginalSize(10)
//                .autoHideToolbarOnSingleTap(true)
        .setOnCheckedListener(OnCheckedListener { isChecked ->
            // DO SOMETHING IMMEDIATELY HERE
            Timber.d("isChecked= isChecked=$isChecked")
        })
        .forResult(REQUEST_CODE_CHOOSE)
}

fun cropImage(activity: AppCompatActivity, path: String) {
    val ratioX = 3f
    val ratioY = 4f
    val maxWidth = 600
    val maxHeight = 800
    val options = UCrop.Options()
    options.setCircleDimmedLayer(true)
    options.setToolbarColor(activity.resources.getColor(R.color.colorPrimary))
    options.setStatusBarColor(activity.resources.getColor(R.color.colorPrimaryDark))
    options.setToolbarWidgetColor(activity.resources.getColor(R.color.white))
    options.setActiveControlsWidgetColor(activity.resources.getColor(R.color.white))
    options.setActiveWidgetColor(activity.resources.getColor(R.color.colorPrimary))

    val sourceUri = Uri.fromFile(File(path))
    val destinationFileName = "croppedImage.jpg"
    val destination = Uri.fromFile(File(activity.cacheDir, destinationFileName))
    UCrop.of(sourceUri, destination)
        .withOptions(options)
        .withAspectRatio(ratioX, ratioY)
        .start(activity)
}

fun handleCropError(ctx: Context, result: Intent) {
    val cropError = UCrop.getError(result)
    if (cropError != null) {
        Timber.d("handleCropError: $cropError")
        Toast.makeText(ctx, cropError.message, Toast.LENGTH_LONG).show()
    } else {
        Timber.d("handleCropError: unexpected error")
    }
}