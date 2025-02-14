package com.pale_cosmos.helu

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.pale_cosmos.helu.util.myUtil
import kotlinx.android.synthetic.main.activity_friend_view.*
import java.lang.Exception


class FriendViewActivity : AppCompatActivity() {
    lateinit var nickname: String
    lateinit var uid: String
    lateinit var phone: String
    var reference: StorageReference? = null
    lateinit var info: Friends
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_friend_view)
        window.setFeatureDrawableResource(Window.FEATURE_NO_TITLE, android.R.drawable.ic_dialog_alert)

        info = myUtil.popDataHolder(intent.getStringExtra("info")) as Friends
        uid = info.key
        reference = FirebaseStorage.getInstance().getReference("profile/$uid.png")


        nickname = info.nickname
        vvw.text = nickname
        phone = info.phone

        GlideApp.with(applicationContext)
            .load(reference)
            .override(130, 130)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(akal)

        bbbs1.setOnClickListener {
            val tend = Intent()
            tend.putExtra("info",myUtil.putDataHolder(info))
            tend.putExtra("image",intent.getStringExtra("image"))
            setResult(201735,tend)
            finish()
        }
        bbbs2.setOnClickListener {
            TedPermission.with(this)
                .setPermissionListener(object : PermissionListener {


                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        Toast.makeText(applicationContext, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionGranted() {
                        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phone")))
                    }
                })
                .setPermissions(
                    Manifest.permission.CALL_PHONE
                ) // 확인할 권한을 다중 인자로 넣어줍니다.
                .check()

        }
        bbbs3.setOnClickListener{
            try {
                var tt = Intent(Intent.ACTION_VIEW, Uri.parse("way_w3w://start/${Integer.toString(Integer.parseInt(phone)%999999)}"))
                startActivity(tt)
            }catch(e:Exception)
            {
                e.printStackTrace()
                Toast.makeText(applicationContext,"W3W를 설치해주세요.",Toast.LENGTH_SHORT).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.jungh0.w3w_map")))
            }


        }
    }

    override fun onBackPressed() {
        finish()
        return
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }

    }
}