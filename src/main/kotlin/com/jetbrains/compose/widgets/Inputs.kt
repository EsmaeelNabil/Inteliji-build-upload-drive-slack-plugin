package com.jetbrains.compose.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Inputs() {

    var renameApk by remember { mutableStateOf(false) }
    var newApkName by remember { mutableStateOf("") }

    var slackWebHook by remember { mutableStateOf("") }

    var notifySlack by remember { mutableStateOf(true) }
    var generateNewApkFirst by remember { mutableStateOf(true) }
    var cleanFirst by remember { mutableStateOf(true) }


    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Rename generated Apk ")
            Checkbox(
                checked = renameApk,
                modifier = Modifier.padding(8.dp),
                onCheckedChange = { renameApk = !renameApk }
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Generate new Debug Apk ")
            Checkbox(
                checked = generateNewApkFirst,
                modifier = Modifier.padding(8.dp),
                onCheckedChange = { generateNewApkFirst = !generateNewApkFirst }
            )
        }

        AnimatedVisibility(generateNewApkFirst) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Clean Previous Build ")
                Checkbox(
                    checked = cleanFirst,
                    modifier = Modifier.padding(8.dp),
                    onCheckedChange = { cleanFirst = !cleanFirst }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Notify Slack")
            Checkbox(
                checked = notifySlack,
                modifier = Modifier.padding(8.dp),
                onCheckedChange = { notifySlack = !notifySlack }
            )
        }


        // login to drive
        // upload to drive

        // enter slack web hock
        // save slack web hock
        // maybe offer to save to list and choose from

        AnimatedVisibility(renameApk) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "",
                        Modifier.clickable {
                            newApkName = ""
                        }
                    )
                },
                value = newApkName,
                onValueChange = { newApkName = it },
                label = { Text("rename apk to :") }
            )
        }

        TextField(
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                    Modifier.clickable {
                        slackWebHook = ""
                    }
                )
            },
            value = slackWebHook,
            onValueChange = { slackWebHook = it },
            label = { Text("Slack Channel webhook") }
        )

        Button(
            onClick = { saveWebHook(slackWebHook) },
            modifier = Modifier.padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "save web hock",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = "Save")
        }



        Button(
            onClick = { start() },
            modifier = Modifier.padding(8.dp).weight(0.2f),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Start",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = "Start")
        }


    }
}

fun start() {
    GlobalScope.launch(Dispatchers.IO) {
        Runtime.getRuntime().exec("./gradlew assemble")
    }
}

fun saveWebHook(slackWebHook: String = "") {

}
