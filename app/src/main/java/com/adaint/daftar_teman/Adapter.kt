package com.adaint.daftar_teman


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_list_data.*

class Adapter(private val context: Context, private val items:
List<Data_teman>, private val listener: (Data_teman)-> Unit) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(context, LayoutInflater.from(context).inflate(
            R.layout.activity_list_data,
            parent, false))
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position), listener)
    }
    class ViewHolder(val context: Context, override val containerView : View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(item: Data_teman, listener: (Data_teman) -> Unit) {

            vtxtnama.text = item.nama
            vtxtalamat.text = item.alamat
            vtxtnohp.text = item.no_hp
            mMenus.setOnClickListener{
                popupMenus(it,item,listener)
            }

        }
        private fun popupMenus(v: View, item: Data_teman, listener: (Data_teman) -> Unit) {
            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.ediText->{

                        item.kondisi = "edit"
                        listener(item)
                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(context)
                            .setTitle("Hapus")
                            .setMessage("Kamu yakin mau hapus data ini ?")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                Log.i("coba", "1")
                                item.kondisi = "hapus"
                                listener(item)
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()


                        true
                    }else-> true

                }
            }
            popupMenus.show()
        }

    }
}