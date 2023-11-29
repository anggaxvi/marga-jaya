package com.example.myapplication.ui.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.FetchResult
import com.example.myapplication.data.pref.Preferences
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.Factory
import com.example.myapplication.ui.fragment.HomeFragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: Preferences
    private val loginViewModel: LoginViewModel by viewModels { Factory(this) }
    private var email: String? = null
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var buttonLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        edEmail = binding.edEmaillog
        edPassword = binding.edPassLog
        val layEmail = binding.emaillogLay
        val layPass = binding.passlogLay

        buttonLogin = binding.buttonlog


        pref = Preferences(this)

        binding.registdlu.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        buttonLogin.setOnClickListener {

            if (validateEmail(edEmail, layEmail) && validatePassword(edPassword, layPass)) {

                email = edEmail.text.toString()
                val pass = edPassword.text.toString()

                loginViewModel.userLogin(email!!, pass).observe(this) {
                    if (it != null) {
                        when (it) {
                            is FetchResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is FetchResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                loginResult(it.data)

                            }

                            is FetchResult.Error -> {
                                binding.progressBar.visibility = View.GONE
                                dialogGagal()
//                                Toast.makeText(this, " ${it.error}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }



        edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validateEmail(edEmail, layEmail)

            }

        })

        edPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword(edPassword, layPass)

            }

        })


    }

    private fun loginResult(data: LoginResponse) {
        if (data.success == false) {

            dialogGagal()

//            AlertDialog.Builder(this).apply {
//                setTitle("Gagal")
//                setMessage("Proses login gagal bolo.")
//                setNegativeButton("Oke", null)
//                create()
//                show()
//            }

        } else {
            pref.saveToken(data.data?.token.toString())
            pref.saveEmail(email.toString())

            dialogSukses()

//            AlertDialog.Builder(this).apply {
//                setTitle("Sukses")
//                setMessage("Proses login berhasil bolo.")
//                setPositiveButton("Lanjut") { _, _ ->
//                    val i = Intent(this@LoginActivity, MainActivity::class.java)
//                    i.putExtra(MainActivity.EXTRA_NAME, email)
//                    startActivity(i)
//                    finish()
//                }
//                create()
//                show()
//            }
        }

    }

    //buttoncustom
    private fun setEnableButton() {

        buttonLogin.isEnabled =  edEmail.text.isNotEmpty() && edPassword.text.isNotEmpty()


    }

    //validasi
    private fun validateEmail(edEmail: EditText, layEmail: TextInputLayout): Boolean {
        return when {
            edEmail.text.toString().trim().isEmpty() -> {
                layEmail.error = "Email tidak boleh kosong"
                false
            }


            !Patterns.EMAIL_ADDRESS.matcher(edEmail.text.toString().trim()).matches() -> {
                layEmail.error = "Email harus unik dan valid"
                false
            }


            else -> {
                layEmail.error = null
                true
            }
        }
    }

    private fun validatePassword(edPass: EditText, layPass: TextInputLayout): Boolean {
        return when {
            edPass.text.toString().trim().isEmpty() -> {
                layPass.error = "Password tidak boleh kosong"
                false
            }

            edPass.text.toString().trim().length < 8 -> {
                layPass.error = "Password harus lebih dari 8 karakter"
                false

            }

            else -> {
                layPass.error = null
                true
            }
        }
    }


    //dialog
    private fun dialogSukses(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog)

        val next = dialog.findViewById<ImageButton>(R.id.btn_sukses)
        next.setOnClickListener {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            i.putExtra(HomeFragment.EXTRA_NAME, email)
            startActivity(i)
        }

        dialog.show()
    }

    private fun dialogGagal(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_failed)

        val close = dialog.findViewById<ImageButton>(R.id.btn_gagal)
        close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}