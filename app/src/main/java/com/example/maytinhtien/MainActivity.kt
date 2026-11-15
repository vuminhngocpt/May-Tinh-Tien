package com.example.maytinhtien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Trạng thái lưu chế độ Sáng (false) hay Tối (true)
            var isDarkTheme by remember { mutableStateOf(false) }

            // Áp dụng bảng màu tùy theo chế độ
            val colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()

            MaterialTheme(colorScheme = colorScheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MayTinhTienScreen(
                        isDarkTheme = isDarkTheme,
                        onThemeChanged = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

@Composable
fun MayTinhTienScreen(
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    var donGia by remember { mutableStateOf("") }
    var soLuong by remember { mutableStateOf("1") }
    var tienKhachDua by remember { mutableStateOf("") }
    
    var tongTien by remember { mutableStateOf(0L) }
    var tienThua by remember { mutableStateOf(0L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hàng tiêu đề và công tắc đổi giao diện Sáng / Tối
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MÁY TÍNH TIỀN",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isDarkTheme) "Tối 🌙" else "Sáng ☀️",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeChanged
                )
            }
        }

        // Ô nhập đơn giá
        OutlinedTextField(
            value = donGia,
            onValueChange = { donGia = it },
            label = { Text("Đơn giá (VNĐ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Ô nhập số lượng
        OutlinedTextField(
            value = soLuong,
            onValueChange = { soLuong = it },
            label = { Text("Số lượng") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Ô nhập tiền khách đưa để tính tiền thừa
        OutlinedTextField(
            value = tienKhachDua,
            onValueChange = { tienKhachDua = it },
            label = { Text("Tiền khách đưa (VNĐ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Nút tính toán
            Button(
                onClick = {
                    val gia = donGia.toLongOrNull() ?: 0L
                    val sl = soLuong.toLongOrNull() ?: 0L
                    val khachDua = tienKhachDua.toLongOrNull() ?: 0L
                    
                    tongTien = gia * sl
                    tienThua = khachDua - tongTien
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("TÍNH TOÁN", fontSize = 15.sp)
            }

            // Nút xóa trắng dữ liệu (Reset)
            OutlinedButton(
                onClick = {
                    donGia = ""
                    soLuong = "1"
                    tienKhachDua = ""
                    tongTien = 0L
                    tienThua = 0L
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("XÓA TRẮNG", fontSize = 15.sp)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 4.dp))

        // Hiển thị kết quả tổng tiền
        Text(
            text = "Tổng cộng: %,d VNĐ".format(tongTien),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Hiển thị kết quả tiền thừa trả khách
        Text(
            text = "Tiền thừa trả khách: %,d VNĐ".format(if (tienThua >= 0) tienThua else 0),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (tienThua >= 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
        )
    }
}
