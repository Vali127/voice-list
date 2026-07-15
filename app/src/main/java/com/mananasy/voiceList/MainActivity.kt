package com.mananasy.voiceList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.mananasy.voiceList.core.ui.MainScaffold
import com.mananasy.voiceList.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    MainScaffold(navController = navController) { padding ->
                        AppNavGraph(
                            navController = navController,
                            modifier = androidx.compose.ui.Modifier.padding(padding)
                        )
                    }
                }
            }
        }
    }
}