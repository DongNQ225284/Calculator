package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ans = 0.0
    private var number = 0.0
    private var error = false
    private var dot = false
    private var operator = "null"
    private var btn = "null"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numbers = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )
        for (button in numbers) {
            button.setOnClickListener {
                if (error || btn == "=") {
                    binding.tvCT.text = ""
                    binding.tvKQ.text = ""
                    operator = "null"
                    error = false
                    dot = false
                    ans = 0.0
                }
                var textKQ = binding.tvKQ.text.toString()
                if (button.id != R.id.btn0 || textKQ.isNotEmpty()) textKQ += button.text.toString()
                binding.tvKQ.text = textKQ
                btn = button.text.toString()
            }
        }
        val operators = listOf(
            binding.btnPlus, binding.btnSub, binding.btnDiv, binding.btnTimes
        )
        for (button in operators) {
            button.setOnClickListener {
                if (error) return@setOnClickListener
                if (btn in listOf("+", "-", "*", "/")) {
                    val res : Number = if(ans.rem(1) == 0.0) ans.toInt() else ans
                    operator = button.text.toString()
                    binding.tvCT.text = res.toString() + operator
                    btn = button.text.toString()
                    dot = false
                    return@setOnClickListener
                }
                var textKQ = binding.tvKQ.text.toString()
                var textCT = binding.tvCT.text.toString()
                number = textKQ.toDoubleOrNull()?:0.0
                when(operator) {
                    "null" -> ans = number
                    "+" -> ans += number
                    "-" -> ans -= number
                    "*" -> ans *= number
                    "/" -> {
                        if (number != 0.0) ans /= number
                        else error = true
                    }
                }

                //val num : Number = if(number.rem(1) == 0.0) number.toInt() else number
                val res : Number = if(ans.rem(1) == 0.0) ans.toInt() else ans
                textCT = res.toString() + button.text.toString()
                textKQ = if (error) "Cannot divide by 0!" else ""
                binding.tvCT.text = textCT
                binding.tvKQ.text = textKQ

                operator = button.text.toString()
                dot = false
                btn = button.text.toString()
            }
        }

        binding.btnDot.setOnClickListener {
            if (error || dot) return@setOnClickListener
            if (btn == "=") {
                dot = true
                error = false
                binding.tvCT.text = ""
                binding.tvKQ.text = "0."
                btn = "dot"
                return@setOnClickListener
            }
            error = false
            dot = true
            var textKQ = binding.tvKQ.text.toString()
            textKQ += if (textKQ.isEmpty()) "0." else "."
            binding.tvKQ.text = textKQ
            btn = "dot"
        }

        binding.btnSign.setOnClickListener {
            if (error) return@setOnClickListener
            if (btn == "=") {
                binding.tvCT.text = ""
            }
            var textKQ = binding.tvKQ.text.toString()
            textKQ = if (textKQ.first() == '-') textKQ.drop(1) else "-$textKQ"
            binding.tvKQ.text = textKQ
            btn = "sign"
        }

        binding.btnEqual.setOnClickListener {
            if (error) {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                operator = "null"
                error = false
                dot = false
                ans = 0.0
                btn = "="
                return@setOnClickListener
            }
            if (btn == "=") {
                number = binding.tvKQ.text.toString().toDoubleOrNull()?:0.0
                val num : Number = if(number.rem(1) == 0.0) number.toInt() else number
                binding.tvCT.text = "$num="
                binding.tvKQ.text = num.toString()
                dot = false
                btn = "="
                return@setOnClickListener
            }
            var textKQ = binding.tvKQ.text.toString()
            var textCT = binding.tvCT.text.toString()
            number = textKQ.toDoubleOrNull()?:0.0
            when(operator) {
                "null" -> ans = number
                "+" -> ans += number
                "-" -> ans -= number
                "*" -> ans *= number
                "/" -> {
                    if (number != 0.0) ans /= number
                    else error = true
                }
            }

            //
            val num : Number = if(number.rem(1) == 0.0) number.toInt() else number
            val res : Number = if(ans.rem(1) == 0.0) ans.toInt() else ans

            textCT = "$textCT$num="
            textKQ = if (error) "Cannot divide by 0!" else res.toString()
            binding.tvCT.text = textCT
            binding.tvKQ.text = textKQ
            operator = "null"
            dot = false
            btn = "="
        }


        binding.btnBS.setOnClickListener {
            if (error || btn == "=") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                operator = "null"
                error = false
                dot = false
                ans = 0.0
                btn = "BS"
                return@setOnClickListener
            }
            var textKQ = binding.tvKQ.text.toString()
            if (textKQ.isNotEmpty()) {
                if (textKQ.last() == '.') dot = false
                textKQ = textKQ.dropLast(1)
            }
            binding.tvKQ.text = textKQ
            btn = "BS"
        }
        binding.btnCE.setOnClickListener {
            if (error || btn == "=") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                operator = "null"
                error = false
                dot = false
                ans = 0.0
                btn = "CE"
                return@setOnClickListener
            }
            binding.tvKQ.text = ""
            dot = false
            btn = "CE"
        }
        binding.btnC.setOnClickListener {
            binding.tvCT.text = ""
            binding.tvKQ.text = ""
            operator = "null"
            error = false
            dot = false
            ans = 0.0
            btn = "C"
        }
    }
}