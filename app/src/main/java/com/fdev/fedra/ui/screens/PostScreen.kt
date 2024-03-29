package com.fdev.fedra.ui.screens

import android.net.Uri
import android.os.Looper
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.fdev.fedra.ui.theme.bounceClick
import com.google.common.util.concurrent.ListenableFuture

private lateinit var preview: Preview
private lateinit var previewView: PreviewView
private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
private var lensFacing = CameraSelector.LENS_FACING_FRONT
private var torchState: Boolean? = false
private var camera: Camera? = null

@Composable
fun PostScreen() {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singleMediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

//        AsyncImage(
//            model = selectedImageUri,
//            contentDescription = null,
//            modifier = Modifier.fillMaxWidth(),
//            contentScale = ContentScale.Crop
//        )

        CameraPreview()
        BottomControls(singleMediaPickerLauncher)
    }
}

@Composable
fun CameraPreview() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var zoomScale by remember { mutableStateOf(1f) }
    var zoomScaleText by remember { mutableStateOf("") }
    var canShowZoomText by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(5.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            canShowZoomText = false
                            zoomScale = 0f
                            flipCamera(lifecycleOwner)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        zoomScale *= zoom
                        camera?.cameraControl?.setZoomRatio(zoomScale)
                        camera?.cameraInfo?.zoomState?.observe(lifecycleOwner) {
                            zoomScaleText = String.format("%.1f", it.zoomRatio)
                            canShowZoomText = true
                        }
                    }
                },
            shape = RoundedCornerShape(12.dp),
        ) {
            AndroidView(
                factory = { ctx ->
                    previewView = PreviewView(ctx)
                    val executor = ContextCompat.getMainExecutor(ctx)
                    cameraProviderFuture.addListener({
                        startCamera(cameraProviderFuture, lifecycleOwner)
                    }, executor)
                    previewView
                },
                modifier = Modifier.fillMaxSize(),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .zIndex(1f)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = {
                    camera?.apply {
                        torchState?.let {
                            if (lensFacing != CameraSelector.LENS_FACING_FRONT) {
                                cameraControl.enableTorch(!it)
                                torchState = !it
                            }
                        }
                    }
                }) {
                Icon(
                    imageVector = Icons.Filled.Face,
                    tint = Color.White,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.fillMaxSize(0.1f))

            Button(
                modifier = Modifier
                    .fillMaxHeight(0.63f)
                    .fillMaxWidth(0.22f)
                    .bounceClick(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                contentPadding = ButtonDefaults.ContentPadding,
                content = {
                    if (canShowZoomText) {
                        Text(text = "${zoomScaleText}x", color = Color.Black)
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            canShowZoomText = false
                        }, 3500L)
                    }
                },
                onClick = {
                }
            )

            Spacer(modifier = Modifier.fillMaxSize(0.1f))

            IconButton(
                onClick = {
                    canShowZoomText = false
                    zoomScale = 0f
                    flipCamera(lifecycleOwner)
                }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    tint = Color.White,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun BottomControls(singleMediaPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth(0.15f),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                singleMediaPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Select from gallery",
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Video", fontSize = 20.sp)
            Text(text = "Photo", fontSize = 20.sp)
        }
    }
}

private fun flipCamera(
    lifecycleOwner: LifecycleOwner
) {
    lensFacing =
        if (lensFacing == CameraSelector.LENS_FACING_FRONT) CameraSelector.LENS_FACING_BACK
        else CameraSelector.LENS_FACING_FRONT

    startCamera(cameraProviderFuture, lifecycleOwner)
}

private fun startCamera(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner
) {
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val cameraProvider = cameraProviderFuture.get()
    preview = Preview.Builder().build().also {
        it.setSurfaceProvider(previewView.surfaceProvider)
    }

    cameraProvider.unbindAll()
    cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview
    ).also {
        camera = it
        it.cameraInfo.torchState.observe(lifecycleOwner) {
            if (torchState == null) torchState = (it == TorchState.ON)
        }
    }
}