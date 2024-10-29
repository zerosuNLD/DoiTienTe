package com.example.doitiente

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var etFirstConversion: EditText
    private lateinit var etSecondConversion: EditText
    private lateinit var spinnerFirstConversion: Spinner
    private lateinit var spinnerSecondConversion: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kết nối các thành phần giao diện
        button = findViewById(R.id.button)
        etFirstConversion = findViewById(R.id.et_firstConversion)
        etSecondConversion = findViewById(R.id.et_secondConversion)
        spinnerFirstConversion = findViewById(R.id.spinner_firstConversion)
        spinnerSecondConversion = findViewById(R.id.spinner_secondConversion)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies, // Mảng các lựa chọn
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Gán adapter cho Spinner
        spinnerFirstConversion.adapter = adapter
        spinnerSecondConversion.adapter = adapter

        // Sự kiện lấy giá trị từ Spinner khi nhấn nút
        button.setOnClickListener {
            // Lấy giá trị đã chọn từ spinnerFirstConversion
            val selectedCurrencyFrom = spinnerFirstConversion.selectedItem.toString()
            val selectedCurrencyTo = spinnerSecondConversion.selectedItem.toString()
            val inputText = etFirstConversion.text.toString()

            // Chuyển đổi sang Integer
            val number: Double? = inputText.toDoubleOrNull()

            if (number != null) {
                val convertedValue = onConvert(number, selectedCurrencyFrom, selectedCurrencyTo)
                etSecondConversion.setText(convertedValue.toString())
                // Hiển thị giá trị qua Toast (hoặc xử lý theo nhu cầu)
                Toast.makeText(this, "Chuyển từ $selectedCurrencyFrom sang $selectedCurrencyTo: $convertedValue", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập một số hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertToVND(selectedCurrency: String, amount: Double): Double {
        return when (selectedCurrency) {
            "USD" -> amount * 25345 // Tỷ giá ví dụ
            "EUR" -> amount * 27428 // Tỷ giá ví dụ
            "JPY" -> amount * 166 // Tỷ giá ví dụ
            "GBP" -> amount * 32945
            // Thêm các loại tiền tệ khác tại đây
            else -> amount // Giả sử là VND
        }
    }

    private fun onConvert(input: Double, selectedCurrencyFrom: String, selectedCurrencyTo: String): Double {
        // Chuyển đổi sang VND trước
        val amountInVND = convertToVND(selectedCurrencyFrom, input)
        // Chuyển đổi từ VND sang loại tiền đích
        return when (selectedCurrencyTo) {
            "USD" -> amountInVND / 25345 // Tỷ giá ví dụ
            "EUR" -> amountInVND / 27428 // Tỷ giá ví dụ
            "JPY" -> amountInVND / 166 // Tỷ giá ví dụ
            "GBP" -> amountInVND / 32945
            // Thêm các loại tiền tệ khác tại đây
            else -> amountInVND // Giả sử là VND
        }
    }
}
