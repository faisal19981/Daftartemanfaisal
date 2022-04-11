package com.adaint.daftar_teman

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show_data_teman.*

class ShowDataTeman : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Data_teman>
    lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data_teman)


        var auth = FirebaseAuth.getInstance()
        val getUserId = auth!!.currentUser!!.uid.toString()
        val database = FirebaseDatabase.getInstance()

        val getRefence : DatabaseReference
        getRefence = database.reference

        ref = getRefence.child("Admin").child(getUserId).child("DataTeman")

        list = mutableListOf()
//        listView = findViewById(R.id.rv_listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){

                    list.clear()
                    for (h in p0.children){
//                        Log.i("firebase", "Got value ${h.getValue(data_teman::class.java)}")
                        var user = h.getValue(Data_teman::class.java)
                        user!!.key = h.key
                        list.add(user!!)
                    }

                    ShowData(list)
//                    val adapter = Adapter(this@ShowDataTeman,R.layout.list_data,list)
//                    listView.adapter = adapter
                }
            }
        })
    }

    private fun ShowData(Data_tmn: List<Data_teman>){
        rv_listView.layoutManager = LinearLayoutManager(this)
        rv_listView.adapter = Adapter(this, Data_tmn){

            var kondisi = it.kondisi
            if(kondisi == "edit"){
                editData(it.key.toString(), it.nama.toString(),it.alamat.toString(), it.no_hp.toString())
            }
            if(kondisi == "hapus"){
                hapusDb(it.key.toString())
            }

        }
    }
    private fun hapusDb( id : String  ){
        var auth = FirebaseAuth.getInstance()
        val getUserId = auth!!.currentUser!!.uid
        val mydatabase = FirebaseDatabase.getInstance().getReference("Admin")
            .child(getUserId).child("DataTeman")
        mydatabase.child(id).removeValue()

    }

    private fun editData(id: String, nama : String, alamat : String, no_hp : String){
        //ini kolom
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.edit_data, null)
        val addDialog = AlertDialog.Builder(this)

        var nameEdit = v.findViewById<EditText>(R.id.txtNama)
        var alamatEdit =v.findViewById<EditText>(R.id.txtAlamat)
        var no_hpEdit =v.findViewById<EditText>(R.id.txtNo_hp)

        nameEdit?.setText(nama)
        alamatEdit?.setText(alamat)
        no_hpEdit?.setText(no_hp)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            editApi(id, nameEdit.text.toString(),alamatEdit.text.toString(),no_hpEdit.text.toString())
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->

        }
        addDialog.create()
        addDialog.show()

    }

    private fun  editApi(id: String, nama : String, alamat : String, no_hp : String){
        val getRefence : DatabaseReference

        var auth = FirebaseAuth.getInstance()
        val getUserId = auth!!.currentUser!!.uid
        val database = FirebaseDatabase.getInstance()

        getRefence = database.reference

        val dataEdit = Data_teman(alamat,nama,no_hp)

        getRefence.child("Admin").child(getUserId)
            .child("DataTeman").child(id).setValue(dataEdit).addOnCompleteListener {
                Toast.makeText(this,"Berhasil Update",Toast.LENGTH_SHORT).show()
            }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
