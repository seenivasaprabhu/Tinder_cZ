package com.cogzidel.www.tinder_cz

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


private val TAG = "CreateAccountActivity"
private var nicktName: String? = null
private var lastName: String? = null
private var Email: String? = null
private var Password: String? = null
private var Gender: String? = null
private var Age: String? = null

private var sharedNumber: String? = null


private var mProgressBar: ProgressDialog? = null



class MainActivity : AppCompatActivity() {
    ////////////************
    companion object {
        //a constant to track the file chooser intent
        private val PICK_IMAGE_REQUEST = 234
    }

    ///////////////


    private var etNickName: EditText? = null
    //private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etGender: EditText? = null
    private var etAge: EditText? = null
    private var btnCreateAccount: Button? = null
    private var btn_choose_file: ImageButton? = null


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var imageReference: StorageReference? = null
    private var fileRef: StorageReference? = null
    private var fileUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SharedPreference Data Retrive
        val sharedprf = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedNumber = sharedprf.getString("mobileNumber","")

        //println("22222222222222"+sharedNumber)



        initialise()
    }

    private fun initialise() {
        etNickName = findViewById<View>(R.id.et_first_name) as EditText
        //etLastName = findViewById<View>(R.id.et_last_name) as EditText
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        etGender = findViewById<View>(R.id.et_gender) as EditText
        etAge = findViewById<View>(R.id.et_age) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_register) as Button
        //btn_choose_file = findViewById<View>(R.id.btn_choose_file) as ImageButton
        mProgressBar = ProgressDialog(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
///////////////////***********

//        /////////


        btnCreateAccount!!.setOnClickListener { createNewAccount() }
        //btn_choose_file!!.setOnClickListener { showChoosingFile() }
    }

    ////////////************
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            btn_choose_file?.setImageBitmap(bitmap)
        }
    }
    private fun showChoosingFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    ///////////////

    private fun createNewAccount() {





        nicktName = etNickName?.text.toString()
        //lastName = etLastName?.text.toString()
        Email = etEmail?.text.toString()
        //Password = etPassword?.text.toString()
        Age = etAge?.text.toString()
        Gender = etGender?.text.toString()


        if (!TextUtils.isEmpty(nicktName) /*&& !TextUtils.isEmpty(lastName)*/
                && !TextUtils.isEmpty(Email) /*&& !TextUtils.isEmpty(Password)*/) {

//
            println("please notify")
//            mAuth!!.createUserWithEmailAndPassword(Email!!, Password!!).addOnCompleteListener(this) { task ->
//                mProgressBar!!.hide()
//                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    //Verify Email
                    // verifyEmail();
                    //update user profile information
                    //println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"+phoneNumber)
                    val currentUserDb = mDatabaseReference!!.child(sharedNumber)
                    currentUserDb.child("nickName").setValue(nicktName)
                    //currentUserDb.child("lastName").setValue(lastName)
                    currentUserDb.child("email").setValue(Email)
                    currentUserDb.child("age").setValue(Age)
                    currentUserDb.child("gender").setValue(Gender)
//                    currentUserDb.child("url").setValue(fileUri)

                    updateUserInfoAndUI()
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(this@MainActivity, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                }
//            }


        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }

        ///////////////////////************************
        /////////////////////////////////////**********************8
    }

    private fun updateUserInfoAndUI() {

        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
}