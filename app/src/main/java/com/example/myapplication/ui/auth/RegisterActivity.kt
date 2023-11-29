package com.example.myapplication.ui.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Icon
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
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.ui.Factory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var edUsername: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edTelp: EditText
    private lateinit var edConfirm: EditText
    private lateinit var buttonRegis: Button
    private val registerViewModel: RegisterViewModel by viewModels { Factory(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        edUsername = binding.edUsrname
        edEmail = binding.edEmail
        edPassword = binding.edPass
        edTelp = binding.edTel
        edConfirm = binding.edConfirm
        buttonRegis = binding.button


        val layEmail = binding.emailLay
        val layPass = binding.passLay
        val layUser = binding.nameLay
        val layTel = binding.telLay
        val layCon = binding.confirmLay

        setEnableButton()

        binding.logindlu.setOnClickListener {
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()
        }

        buttonRegis.setOnClickListener {
            val name = binding.edUsrname.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPass.text.toString()
            val telp = binding.edTel.text.toString()
            val confirm = binding.edConfirm.text.toString()

            if (validateUsername(edUsername, layUser) && validateEmail(
                    edEmail,
                    layEmail
                ) && validatePassword(edPassword, layPass) && validateTel(
                    edTelp,
                    layTel
                ) && validateConfirm(edConfirm, layCon, edPassword)
            ) {

                registerViewModel.userRegister(name, email, password, telp, confirm).observe(this) {
                    if (it != null) {
                        when (it) {
                            is FetchResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is FetchResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                resultRegister(it.data)
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

        edConfirm.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validateConfirm(edConfirm,layCon,edPassword)

            }

        })

        edUsername.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()

            }

            override fun afterTextChanged(p0: Editable?) {
                validateUsername(edUsername,layUser)


            }

        })

        edEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validateEmail(edEmail,layEmail)

            }

        })

        edPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePassword(edPassword,layPass)

            }

        })

        edTelp.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEnableButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                validateTel(edTelp,layTel)

            }

        })

    }


    private fun resultRegister(data: RegisterResponse) {
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

            dialogSukses()
//            AlertDialog.Builder(this).apply {
//                setTitle("Sukses")
//                setMessage("Proses registrasi berhasil.")
//                setPositiveButton("Lanjut") { _, _ ->
//                    val i = Intent(this@RegisterActivity, LoginActivity::class.java)
//                    startActivity(i)
//                    finish()
//                }
//                create()
//                show()
//            }
        }
    }

    //button custom
    private fun setEnableButton() {

        buttonRegis.isEnabled = edUsername.text.isNotEmpty() && edEmail.text.isNotEmpty() && edPassword.text.isNotEmpty() && edTelp.text.isNotEmpty() && edConfirm.text.isNotEmpty()


    }


    //validasi
    private fun validateUsername(edUsername: EditText, layUser: TextInputLayout): Boolean {
        return when {
            edUsername.text.toString().trim().isEmpty() -> {
                layUser.error = "Username tidak boleh kosong"
                false
            }

            else -> {
                layUser.error = null
                true
            }
        }
    }


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
                layPass.error= "Password tidak boleh kosong"
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


    private fun validateTel(edTel: EditText, layTel: TextInputLayout): Boolean {
        return when {
            edTel.text.toString().trim().isEmpty() -> {
                layTel.error = "Nomor telephone tidak boleh kosong"
                false
            }

            else -> {
                layTel.error = null
                true
            }
        }
    }


    private fun validateConfirm(edCon: EditText, layCon: TextInputLayout, edPass: EditText): Boolean {
        return when {
            edCon.text.toString().trim().isEmpty() -> {
                layCon.error = "Password tidak boleh kosong"
                false
            }

            edCon.text.toString().trim().length < 8 -> {
                layCon.error = "Password harus lebih dari 8 karakter"
                false

            }


            edCon.text.toString().trim() != edPass.text.toString().trim() -> {
                layCon.error = "Password tidak sama"
                false
            }

            else -> {
                layCon.error = null
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
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
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