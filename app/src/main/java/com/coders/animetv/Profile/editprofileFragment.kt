package com.coders.animetv.Profile


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coders.animetv.Models.Users
import com.coders.animetv.R
import com.coders.animetv.Utilz.EventBusData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_editprofile.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class editprofileFragment : Fragment() {
    lateinit var gelenKullanici: Users
    // rasgele bir sayı oluşturuyor resim seçme için
    val resimSec = 100
    //Fİrebase bilgileri
    lateinit var mDatabase: DatabaseReference
    lateinit var mStorageRef: StorageReference

    // Eklnen resmi kaydeyme
    var profileResimUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_editprofile, container, false)

        //firebase tanımlama
        mDatabase = FirebaseDatabase.getInstance().reference
        mStorageRef = FirebaseStorage.getInstance().reference
        //firebase tanımlama SON
        //eventBus ile çekilen kullanıcı bilgilerini yazdırma
        setupUsersInfo(view)
        //profil değiştir butonuna basınca galeriden resim seçtir
        changeProfilePicBtn(view)

        //yapılan değişiklikleri kaydetme
        saveChanges(view)

        return view
    }

    // kaydet tuşuna basınca verileri kaydetme DB ye update işlemi //
    private fun saveChanges(view: View?) {
        view!!.button2.setOnClickListener {
            //kullanici ismi var mı diye bakar eğer yok ise kaydeder var ise hata mesajı çıkarır //

            if (!gelenKullanici.user_nickname.equals(view.changeUsernameInput.text.toString()))
                mDatabase.child("users").child("typeC").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //kullanici var mı kontrolu //
                        var kullaniciNickNameVarMi = false
                        //kullanici var mı kontrolu SON //
                        for (ab in p0.children) {
                            val readedUsers = ab.getValue(Users::class.java)!!.user_nickname
                            if (readedUsers.equals(view.changeUsernameInput.text.toString())) {
                                Toast.makeText(
                                    activity,
                                    "This user name is exist",
                                    Toast.LENGTH_LONG
                                ).show()
                                kullaniciNickNameVarMi = true
                                break
                            }

                        }
                        if (!kullaniciNickNameVarMi) {
                            mDatabase.child("users").child("typeC").child(gelenKullanici.user_id!!)
                                .child("user_nickname")
                                .setValue(view.changeUsernameInput.text.toString())
                        }
                    }
                })
            //kullanici ismi var mı diye bakar eğer yok ise kaydeder var ise hata mesajı çıkarır SON //

            //Resim Ekleme KIsmı //
            if (profileResimUri != null) {
                // resim büyük gelirse progress bar firebase den //
                val dialogYukleniyor = PicUplaodingFragment()
                dialogYukleniyor.show(activity!!.supportFragmentManager,"Yukleniyor Fragmenti")
                // resim büyük gelirse progress bar firebase den SON//

                // resmi firebase storageye yükleme kısmı path
                 mStorageRef.child("ProfilePics")
                    .child(gelenKullanici.user_id!!).child(profileResimUri!!.lastPathSegment!!).putFile(profileResimUri!!)
                    // başarılı olursa buraya gelicek
                    .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot> {
                        override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                            if (p0.isSuccessful) {
                                dialogYukleniyor.dismiss()
                            }

                        }

                    })
                    //hata olursa burası çalışcak
                    .addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(p0: Exception) {

                        }

                    })
            }
            //Resim Ekleme KIsmı path SON//

        }
    }
    // kaydet tuşuna basınca verileri kaydetme DB ye update işlemi SON//

    // galeriden resim seçmesini sağlıyor //
    private fun changeProfilePicBtn(view: View?) {
        view!!.editProfilePic.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(intent, resimSec)
        }
    }

    // galeriden resim seçmesini sağlıyor SON//
    //galeriden seçilen resim bir result yani sonuç döndürüyor o sonuç kısmı //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == resimSec && resultCode == Activity.RESULT_OK && data?.data != null) {
            profileResimUri = data.data
        }
    }
    //galeriden seçilen resim bir result yani sonuç döndürüyor o sonuç kısmı SON//


    private fun setupUsersInfo(view: View?) {
        view!!.newEmailInput.setText(gelenKullanici.user_email)
        view.changeUsernameInput.setText(gelenKullanici.user_nickname)
    }


    ////////////////////   EVENT BUS //////////////////////
    @Subscribe(sticky = true)
    internal fun kullaniciBilgileriniAl(kulaniciBilgileri: EventBusData.kullaniciBilgileriniGonder) {
        gelenKullanici = kulaniciBilgileri.kullaniciTumBilgileri!!
    }

    //Profile Activity sayfasından anlık alınan veri olursa olmazsa //
    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
    //Profile Activity sayfasından anlık alınan veri olursa olmazsa SON//
    ////////////////////   EVENT BUS  SON  //////////////////////

}
