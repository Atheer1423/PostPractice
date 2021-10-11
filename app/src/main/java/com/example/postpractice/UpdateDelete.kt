package com.example.postpractice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDelete : AppCompatActivity() {
    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        var name = findViewById<View>(R.id.name) as EditText
        var location = findViewById<View>(R.id.location) as EditText


        val updatebtn = findViewById<View>(R.id.U) as Button
        val deleteebtn = findViewById<View>(R.id.D) as Button

        updatebtn.setOnClickListener {

            var f = Users.UserDetails(name.text.toString(), location.text.toString())

            addSingleuser(f, onResult = {
                name.setText("")
                location.setText("")
                pk.setText("")

                Toast.makeText(applicationContext, "Update Success!", Toast.LENGTH_SHORT).show();
            })
        }
        deleteebtn.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please wait")
            progressDialog.show()


            if (apiInterface != null) {

                apiInterface?.deleteUser(pk.text.toString().toInt())
                    .enqueue(object : Callback<Void> {

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>


                        ) { pk.setText("")


                            progressDialog.dismiss()
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {

                            Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss()

                        }


                    })

            }
        }
        back.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addSingleuser(f: Users.UserDetails, onResult: () -> Unit) {


        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()


        if (apiInterface != null) {

            apiInterface.updateUser(pk.text.toString().toInt(), f)
                .enqueue(object : Callback<Users.UserDetails> {
                    override fun onResponse(
                        call: Call<Users.UserDetails>,
                        response: Response<Users.UserDetails>

                    ) {

                        onResult()
                        progressDialog.dismiss()
                    }

                    override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                        onResult()
                        Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss()

                    }
                })

        }
    }

    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, user::class.java)
        startActivity(intent)
    }



}