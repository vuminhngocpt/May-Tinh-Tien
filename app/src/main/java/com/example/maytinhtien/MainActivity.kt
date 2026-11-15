package com.example.maytinhtien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MayTinhTienScreen()
                }
            }
        }
    }
}

@Composable
fun MayTinhTienScreen() {
    var donGia by remember { mutableStateOf("") }
    var soLuong by remember { mutableStateOf("1") }
    var tongTien by remember { mutableStateOf(0L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MÁY TÍNH TIỀN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = donGia,
            onValueChange = { donGia = it },
            label = { Text("Đơn giá (VNĐ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = soLuong,
            onValueChange = { soLuong = it },
            label = { Text("Số lượng") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val gia = donGia.toLongOrNull() ?: 0L
                val sl = soLuong.toLongOrNull() ?: 0L
                tongTien = gia * sl
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("TÍNH TỔNG TIỀN", fontSize = 16.sp)
        }

        Divider()

        Text(
            text = "Tổng cộng: %,d VNĐ".format(tongTien),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
