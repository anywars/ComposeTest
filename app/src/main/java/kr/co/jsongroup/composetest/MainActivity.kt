package kr.co.jsongroup.composetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import kr.co.jsongroup.composetest.ui.theme.ComposeTestTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm = viewModels<CommonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.value.version()
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    VersionInfo(vm.value.version.value)
                }
            }
        }
    }
}


@Composable
fun VersionInfo(version: Version?) {

    Column {
        Text(text = version?.version ?: "")

        Row {
            Text(text = version?.osType ?: "")
            Spacer(Modifier.size(10.dp))
            Text(text = version?.marketUrl ?: "")
        }
    }

}



@HiltViewModel
class CommonViewModel @Inject constructor(
    private val service: CommonAPIService
): ViewModel() {
    val TAG = CommonViewModel::class.java.simpleName

    val version = mutableStateOf<Version?>(null)

    fun version() {
        service.version(Version(version = "0.0.0", osType = "AND"))
            .subscribeOn(Schedulers.io())
            .subscribe({
                version.value = it.payload
            }, { e ->
                version.value = Version("x.x.x", "AND")
                Log.e(TAG, "", e)
            })
    }

}



@Preview(showBackground = true, name = "test")
@Composable
fun DefaultPreview() {
    val version = Version("0.0.0", "AND", "memo","market://details?id=test")

    ComposeTestTheme {
        Surface(color = MaterialTheme.colors.background) {
            VersionInfo(version)
        }
    }
}