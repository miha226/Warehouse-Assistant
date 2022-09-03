package warehouse.assistant.data.remote.dto

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import warehouse.assistant.data.Constants
import warehouse.assistant.data.DateFormater
import warehouse.assistant.domain.model.AuthorizedUser
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthImpl @Inject constructor(
    private val storage:FirebaseStorage
) {
    private var auth: FirebaseAuth = Firebase.auth

    companion object{
        private val userPlaceholder = AuthorizedUser("errorMail","error","worker",1)
        val user = mutableStateOf(userPlaceholder)


        fun putUser(userS:AuthorizedUser){
            user.value=userS
        }

        fun getUser():AuthorizedUser{
            return user.value
        }
        fun signOut(){
            Firebase.auth.signOut()
        }
    }

    fun signIn(email:String, password:String, context: Context,callback: ((Boolean) -> Unit)){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(context, "Authentication success.",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }.continueWith {
                if(isUserLoggedIn()){callback(true)}
                else{callback(false)}
            }
    }

    fun registerUser(email: String,password: String,context: Context,username:String,callback: ((Boolean) -> Unit)) {
       auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(context, "Authentication success.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    val userInfo = AuthorizedUser(user?.email.toString(),username,"worker",DateFormater.getCurrentMillis())
                    storage.db.collection(Constants.USERS).document(user?.email.toString())
                        .set(userInfo).addOnSuccessListener {
                            if(isUserLoggedIn()){callback(true)}
                                else{callback(false)} }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut(){
       auth.signOut()
    }

    fun isUserLoggedIn():Boolean{
        if(auth.currentUser != null){return true}
        return false
    }



    fun getUserEmail():String{
        if(isUserLoggedIn()){
           return auth.currentUser?.email.toString()
        }
        return "no user email"
    }


}