package ca.georgiancollege.section1_ice9

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgiancollege.section1_ice9.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TVShowViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    private lateinit var dataManager: DataManager
    private val adapter = TVShowListAdapter { tvShow: TVShow ->
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("tvShowId", tvShow.id)
        }
        startActivity(intent)
        updateLastActiveTime()
    }

    // Store last user interaction time
    private var lastActiveTime = 0L
    // Inactivity threshold in milliseconds
    private val THRESHOLD = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firebase Firestore
        FirebaseFirestore.setLoggingEnabled(true)

        // creates an alias for the DataManager instance
        dataManager = DataManager.instance()

        binding.firstRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.firstRecyclerView.adapter = adapter

        // Observe the TVShows LiveData to update the UI
        viewModel.tvShows.observe(this) { tvShows ->
            adapter.submitList(tvShows)
        }

        // Check if the user is logged in on app launch
        checkUserAuthentication()

        // Disable buttons if user not logged in
        disableButtonsIfNotLoggedIn()

        binding.addButton.setOnClickListener {
            if (isUserLoggedIn()) {
                val intent = Intent(this, DetailsActivity::class.java)
                startActivity(intent)
            } else {
                redirectToLogin()
            }
            updateLastActiveTime()
        }

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }
    }

    override fun onResume() {
        super.onResume()
        val currentTime = SystemClock.elapsedRealtime()
        val timeSinceLastActive = currentTime - lastActiveTime
        if (isUserLoggedIn() && timeSinceLastActive > THRESHOLD) {
            handleInactivity()
        }
        updateLastActiveTime()
        viewModel.loadAllTVShows()
    }

    private fun updateLastActiveTime() {
        lastActiveTime = SystemClock.elapsedRealtime()
    }

    private fun handleInactivity() {
        val handler = android.os.Handler()
        val runnable = Runnable {
            if (isUserLoggedIn()) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Session Expired")
                    .setMessage("Your session has been inactive for a while. You will be logged out.")
                    .setPositiveButton("Log Out") { dialog, _ ->
                        auth.signOut()
                        redirectToLogin()
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                builder.show()
            }
        }

        // Schedule the dialog to show after 30 seconds (30000 milliseconds)
        handler.postDelayed(runnable, 30000)
    }


    private fun checkUserAuthentication() {
        if (!isUserLoggedIn()) {
            redirectToLogin()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun disableButtonsIfNotLoggedIn() {
        if (!isUserLoggedIn()) {
            binding.addButton.isEnabled = false
        }
    }
}
