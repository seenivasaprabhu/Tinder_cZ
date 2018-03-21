package com.cogzidel.www.tinder_cz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mobileNumber: String = ""
    var verificationID: String = ""
    var token_: String = ""


    private var sharedNumber: String? = null



    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    var sharedprf:SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
//        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mDatabaseReference = mDatabase!!.getReference()!!.child("Users")
        //mAuth = FirebaseAuth.getInstance()

        //SharedPreference
        sharedprf = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        mDatabaseReference?.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {


                var s = snapshot.value

                sharedNumber = sharedprf?.getString("mobileNumber","")

                println("TestBed 1 : ${snapshot.key}")
                println("TestBed 2 : ${snapshot.childrenCount}")
                println("TestBed 3 : ${snapshot.child(sharedNumber).key}")
                println("qqqqqqqqqqqqqqqqqqqqqq"+s)

                //snapshot.key

            }

            override fun onCancelled(error: DatabaseError) {}
        })


//
//        sharedprf.edit().putString("mobileNumber",mobileNumber).apply()

        sharedNumber = sharedprf?.getString("mobileNumber","")





        btnSignIn.setOnClickListener{

            mobileNumber = etNumber.text.toString()

               if (mobileNumber.length > 0 && sharedNumber==mobileNumber) {

                    progressBar.visibility = View.VISIBLE


                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    println("wwwwwwwwwwwwwwwwwwwww" + sharedNumber + "" + mobileNumber)
                }
                    else if (mobileNumber.length > 0){

                    loginTask()


                   // shared preference Store value
//                    val sharedprf = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//
//                    sharedprf.edit().putString("mobileNumber",mobileNumber).apply()
//
//                    println("11111111111111111111111"+mobileNumber)
            }
            else {
                etNumber.setError("Enter valid phone number")
            }
        }
    }

    private fun loginTask() {

        var mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                if (credential != null) {
                    signInWithPhoneAuthCredential(credential)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                progressBar.visibility = View.GONE
                toast("Invalid phone number or verification failed.")
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(verificationId, token)
                progressBar.visibility = View.GONE
                verificationID = verificationId.toString()
                token_ = token.toString()

                etNumber.setText("")

                etNumber.setHint("Enter OTP ")
                btnSignIn.setText("Verify OTP")

                btnSignIn.setOnClickListener {
                    progressBar.visibility = View.VISIBLE
                    verifyAuthentication(verificationID, etNumber.text.toString())
                }

                Log.e("Login : verificationId ", verificationId)
                Log.e("Login : token ", token_)

            }

            override fun onCodeAutoRetrievalTimeOut(verificationId: String?) {
                super.onCodeAutoRetrievalTimeOut(verificationId)
                progressBar.visibility = View.GONE
                // toast("Time out")
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,            // Phone number to verify
                60,                  // Timeout duration
                TimeUnit.SECONDS,        // Unit of timeout
                this,                // Activity (for callback binding)
                mCallBacks);

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this@LoginActivity, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            val user = task.getResult().getUser()
                            progressBar.visibility = View.GONE
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            // Data base phone store
                            //println("aaaaaaaaaaaa"+mobileNumber)
//                            val currentUserDb = mDatabaseReference!!.child("Users").child(mobileNumber)
//                            currentUserDb.child("name").setValue("abc")

                            //Shared Preference Store Value
//                            sharedprf = PreferenceManager.getDefaultSharedPreferences(applicationContext)

                            sharedprf?.edit()?.putString("mobileNumber",mobileNumber)?.apply()

                            //println("11111111111111111111111"+mobileNumber)

                        } else {
                            if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                                progressBar.visibility = View.GONE
                                toast("Invalid OPT")
                            }
                        }
                    }
                })
    }

    private fun verifyAuthentication(verificationID: String, otpText: String) {

        val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, otpText) as PhoneAuthCredential
        signInWithPhoneAuthCredential(phoneAuthCredential)
    }
}


