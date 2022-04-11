package com.adaint.daftar_teman

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLihat.setOnClickListener { lihatData() }
        BtnSimpan.setOnClickListener { simpan() }
        loqout.setOnClickListener {logout()}



    }

    private fun lihatData(){
        intent = Intent(this, ShowDataTeman::class.java)
        startActivity(intent)
        finish()
    }
    private fun simpan(){
        val getRefence : DatabaseReference

        var auth = FirebaseAuth.getInstance()
        val getUserId = auth!!.currentUser!!.uid
        val database = FirebaseDatabase.getInstance()

        var nama : String = txtNama.text.toString()
        var alamat : String = txtAlamat.text.toString()
        var no_hp : String = txtnohp.text.toString()

        getRefence = database.reference


        if(isEmpty(nama)|| isEmpty(alamat)|| isEmpty(no_hp)){
            Toast.makeText(this,"Data tidak boleh kosong",Toast.LENGTH_SHORT).show()
        }else{
                getRefence.child("Admin").child(getUserId).child("DataTeman").push()
                    .setValue(Data_teman(alamat,nama,no_hp))
                    .addOnCompleteListener(this){
                        txtNama.setText("")
                        txtAlamat.setText("")
                        txtnohp.setText("")
                        Toast.makeText(this,"Data Berhasil disimpan",Toast.LENGTH_SHORT).show()
                    }
        }
    }

    private fun logout(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

}