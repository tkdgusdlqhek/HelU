package com.pale_cosmos.helu

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.util.Util
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pale_cosmos.helu.util.myUtil
import kotlinx.android.synthetic.main.main_rv_item.view.*

class MainAdapter(items:ArrayList<Friends>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var items = ArrayList<Friends>()
init{
    this.items=items
}
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
//                if (item.photo != null) friendPhoto.setImageBitmap(myUtil.stringToBitmap(item.photo))
                FirebaseDatabase.getInstance().reference.child("users").child(item.key).child("photo").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        friendPhoto.setImageBitmap(myUtil.stringToBitmap(p0.getValue(String::class.java)!!))
                        friendName.text = item.nickname
                        friendUniv.text = item.university
                        friendDepart.text = item.department
                        friendPhone.text = myUtil.phoneToString(item.phone)

                    }
                })

            }
        }
    }

    inner class MainViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.main_rv_item, parent, false)
    ) {
        val friendPhoto = itemView.friendPhotoImg
        val friendName = itemView.nameTv
        val friendUniv = itemView.universityTv
        val friendDepart = itemView.departmentTv
        val friendPhone = itemView.phoneTv
    }
}
