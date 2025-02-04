package pl.lejdi.kioskexample

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.lejdi.kioskexample.ui.theme.KioskExampleTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val filter = IntentFilter(Intent.ACTION_MAIN)
        filter.addCategory(Intent.CATEGORY_HOME)
        filter.addCategory(Intent.CATEGORY_DEFAULT)

        val activity = ComponentName(this, MainActivity::class.java)
        val dpm = this.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        dpm.addPersistentPreferredActivity(
            DeviceOwnerReceiver.getComponentName(this),
            filter,
            activity
        )

        setContent {
            KioskExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Button(onClick = {
                            val intent = Intent(this@MainActivity, OtherActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(text = "Nav to other actvt")
                        }
                    }
                }
            }
        }

        val mDevicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        mDevicePolicyManager.setLockTaskPackages(DeviceOwnerReceiver.getComponentName(this), arrayOf(packageName))
        startLockTask()
    }
}
