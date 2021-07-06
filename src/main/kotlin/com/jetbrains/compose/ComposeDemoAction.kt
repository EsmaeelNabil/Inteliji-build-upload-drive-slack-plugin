package com.jetbrains.compose

import androidx.compose.desktop.ComposePanel
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.jetbrains.compose.theme.WidgetTheme
import com.jetbrains.compose.widgets.*
import java.awt.Dimension
import javax.swing.JComponent


/**
 * @author Konstantin Bulenkov
 */
class ComposeDemoAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        DemoDialog(e.project).show()
    }

    class DemoDialog(project: Project?) : DialogWrapper(project) {
        init {
            title = "Demo"
            init()
        }

        override fun createCenterPanel(): JComponent {
            val dialog = this
            return ComposePanel().apply {
                preferredSize = Dimension(300, 200)
                setContent {
                    ComposeSizeAdjustmentWrapper(
                        window = dialog,
                        panel = this,
                        preferredSize = IntSize(300, 200)
                    ) {
                        WidgetTheme(darkTheme = true) {
                            Surface(modifier = Modifier.fillMaxSize()) {
                                val scrollState = rememberScrollState()
                                Column(
                                    modifier = Modifier.fillMaxSize().scrollable(scrollState, Orientation.Vertical),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Inputs()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
