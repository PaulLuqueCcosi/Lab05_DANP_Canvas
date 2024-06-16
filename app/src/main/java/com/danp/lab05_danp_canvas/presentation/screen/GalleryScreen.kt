package com.danp.lab05_danp_canvas.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danp.lab05_danp_canvas.R
import com.danp.lab05_danp_canvas.presentation.viewmodel.GalleryViewModel

@Composable
fun Gallery(viewModel: GalleryViewModel) {
    val showDialog = viewModel.showDialog
    val circlePosition = viewModel.circlePosition
    val circleRadius = 70f

    val colorBackground = Color.LightGray
    val (screenWidth, screenHeight) = getScreenDimensions()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
    ) {
        IconBox(viewModel)
        NewPositionButton(viewModel, screenWidth, screenHeight, circleRadius)
        DrawingCanvas(circlePosition, circleRadius)
    }

    if (showDialog) {
        NocheEstrelladaDialogScreen(viewModel)
    }
}

@Composable
fun IconBox(viewModel: GalleryViewModel) {
    Box(
        modifier = Modifier
            .padding(60.dp)
            .size(70.dp)
            .clickable {
                viewModel.onClickIconBox()
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Ícono",
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Composable
fun NewPositionButton(viewModel: GalleryViewModel, screenWidth: Float, screenHeight: Float, circleRadius: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Button(
            onClick = {
                val newPosition = viewModel.getRandomPosition(screenWidth, screenHeight, circleRadius)
                viewModel.setNewPositionCircle(newPosition)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "New Position")
        }
    }
}


@Composable
fun DrawingCanvas(circlePosition: Offset, circleRadius: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val rectPadding = 16.dp.toPx()
        val rectWidth = size.width - 2 * rectPadding
        val rectHeight = size.height - 2 * rectPadding
        drawRect(
            color = Color.Black,
            topLeft = Offset(rectPadding, rectPadding),
            size = Size(rectWidth, rectHeight),
            style = Stroke(width = 5f) // Grosor del borde
        )
        drawCircle(
            color = Color.Green,
            radius = circleRadius,
            center = circlePosition
        )
    }
}

@Composable
fun getScreenDimensions(): Pair<Float, Float> {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenHeightFloat = LocalDensity.current.run { screenHeight.toPx() }
    val screenWidth = configuration.screenWidthDp.dp
    val screenWidthFloat = LocalDensity.current.run { screenWidth.toPx() }
    return Pair(screenWidthFloat, screenHeightFloat)
}

