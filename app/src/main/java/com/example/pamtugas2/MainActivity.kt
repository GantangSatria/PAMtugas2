package com.example.pamtugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pamtugas2.ui.theme.PAMtugas2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("result/{nim}/{name}/{result}") { backStackEntry ->
            ResultScreen(
                nim = backStackEntry.arguments?.getString("nim") ?: "",
                name = backStackEntry.arguments?.getString("name") ?: "",
                result = backStackEntry.arguments?.getString("result") ?: ""
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    var num1 by remember { mutableStateOf(TextFieldValue()) }
    var num2 by remember { mutableStateOf(TextFieldValue()) }
    var selectedOperator by remember { mutableStateOf("+") }
    val nim = "235150701111036"
    val name = "Satria"

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = num1, onValueChange = { num1 = it }, label = { Text("Angka pertama") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = num2, onValueChange = { num2 = it }, label = { Text("Angka kedua") })
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            listOf("+", "-", "*", "/").forEach { op ->
                Row(modifier = Modifier.padding(8.dp)) {
                    RadioButton(
                        selected = (selectedOperator == op),
                        onClick = { selectedOperator = op }
                    )
                    Text(op, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }

        Button(onClick = {
            val n1 = num1.text.toDoubleOrNull() ?: 0.0
            val n2 = num2.text.toDoubleOrNull() ?: 0.0
            val result = when (selectedOperator) {
                "+" -> n1 + n2
                "-" -> n1 - n2
                "*" -> n1 * n2
                "/" -> if (n2 != 0.0) n1 / n2 else Double.NaN
                else -> 0.0
            }
            navController.navigate("result/$nim/$name/$result")
        }) {
            Text("Hitung")
        }
    }
}

@Composable
fun ResultScreen(nim: String, name: String, result: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nama: $name", style = MaterialTheme.typography.headlineMedium)
        Text(text = "NIM: $nim", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Hasil: $result", style = MaterialTheme.typography.headlineSmall)
    }
}
