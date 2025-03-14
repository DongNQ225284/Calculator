package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ans = 0.0
    private var number = 0.0
    private var operator = "null"
    private var flag = true
    private var dot = false
    private var state = "activity"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numbers = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9, binding.btnDot
        )
        for (button in numbers) {
            button.setOnClickListener {
                if (state == "clear") {
                    binding.tvCT.text = ""
                    binding.tvKQ.text = ""
                    state = "activity"
                    operator = "null"
                    dot = false
                }
                if (button.id != R.id.btnDot) {
                    binding.tvKQ.text = binding.tvKQ.text.toString() + button.text.toString()
                } else if (!dot) {
                    if (binding.tvKQ.text.toString().isEmpty()) {
                        binding.tvKQ.text = "0."
                    } else {
                        binding.tvKQ.text = binding.tvKQ.text.toString() + button.text.toString()
                    }
                    dot = true
                }
            }
        }
        val operators = listOf(
            binding.btnPlus, binding.btnSub, binding.btnDiv, binding.btnTimes
        )
        for (button in operators) {
            button.setOnClickListener {
                dot = false
                if (state == "clear") {
                    binding.tvCT.text = ""
                    binding.tvKQ.text = ""
                    state = "activity"
                }
                if (binding.tvKQ.text.toString().isEmpty()) {
                    operator = "null"
                    number = ans
                } else {
                    number = binding.tvKQ.text.toString().toDouble()
                }
                flag = true
                when(operator) {
                    "plus" -> ans += number
                    "sub" -> ans -= number
                    "div" -> {
                        if (number != 0.0) {
                            ans /= number
                        } else {
                            flag = false
                        }
                    }
                    "times" -> ans *= number
                    "null" -> ans = number
                }
                when(button.id) {
                    R.id.btnPlus -> operator = "plus"
                    R.id.btnSub -> operator = "sub"
                    R.id.btnDiv -> operator = "div"
                    R.id.btnTimes -> operator = "times"
                }
                if (flag) {
                    val result: Number = if (ans.rem(1) == 0.0) ans.toInt() else ans
                    binding.tvCT.text = result.toString() + button.text.toString()
                    binding.tvKQ.text = ""
                } else {
                    binding.tvCT.text = "Error!"
                    binding.tvKQ.text = "Cannot divide by zero"
                    state = "clear"
                }
            }
        }
        binding.btnSign.setOnClickListener {
            if (state == "clear") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                state = "activity"
                dot = false
            }
            if (binding.tvKQ.text.toString() != "") {
                var s = binding.tvKQ.text.toString()
                if (s.first() != '-') {
                    binding.tvKQ.text = "-$s"
                } else {
                    s = s.drop(1)
                    binding.tvKQ.text = s
                }
            }
        }
        binding.btnC.setOnClickListener {
            binding.tvCT.text = ""
            binding.tvKQ.text = ""
            ans = 0.0
            state = "activity"
            operator = "null"
            dot = false
        }
        binding.btnCE.setOnClickListener {
            if (state == "clear") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                state = "activity"
                ans = 0.0
            }
            binding.tvKQ.text = ""
            dot = false
        }
        binding.btnBS.setOnClickListener {
            if (state == "clear") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                state = "activity"
                dot = false
            }
            if (binding.tvKQ.text.toString() != "") {
                var s = binding.tvKQ.text.toString()
                if (s.last() == '.') {
                    dot = false
                }
                s = s.dropLast(1)
                binding.tvKQ.text = s
            }
        }
        binding.btnEqual.setOnClickListener {
            if (state == "clear") {
                binding.tvCT.text = ""
                binding.tvKQ.text = ""
                state = "activity"
                dot = false
            }
            val s = binding.tvKQ.text.toString()
            number = s.toDoubleOrNull()?:0.0
            flag = true
            when(operator) {
                "plus" -> ans += number
                "sub" -> ans -= number
                "div" -> {
                    if (number != 0.0) {
                        ans /= number
                    } else {
                        flag = false
                    }
                }
                "times" -> ans *= number
                "null" -> ans = number
            }
            if (s.isEmpty()) {
                binding.tvCT.text = binding.tvCT.text.toString() + "0 ="
            } else if (s.first() != '-') {
                binding.tvCT.text = binding.tvCT.text.toString() + number + "="
            } else {
                binding.tvCT.text = binding.tvCT.text.toString() + "(" + number + ") ="
            }
            if (flag) {
                val result: Number = if (ans.rem(1) == 0.0) ans.toInt() else ans
                binding.tvKQ.text = result.toString()
            } else {
                binding.tvCT.text = "Error!"
                binding.tvKQ.text = "Cannot divide by zero"
            }
            state = "clear"
        }
    }
}