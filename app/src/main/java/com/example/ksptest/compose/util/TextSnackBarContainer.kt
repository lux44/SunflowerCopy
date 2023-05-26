package com.example.ksptest.compose.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextSnackBarContainer(
    snackBarText:String,
    showSnackBar:Boolean,
    onDismissSnackBar : () -> Unit,
    modifier: Modifier = Modifier,
    snackBarHostState : SnackbarHostState = remember {
        SnackbarHostState()
    },
    content : @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()

        val onDismissState by rememberUpdatedState(onDismissSnackBar)
        LaunchedEffect(showSnackBar, snackBarText) {
            if (showSnackBar) {
                try {
                    snackBarHostState.showSnackbar(
                        message = snackBarText,
                        duration = SnackbarDuration.Short
                    )
                } finally {
                    onDismissState
                }
            }
        }

        MaterialTheme(shapes = Shapes()) {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .systemBarsPadding()
                    .padding(all = 8.dp),
            ) {
                Snackbar(it)
            }
        }
    }
}