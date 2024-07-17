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
        // Update on user interaction
        updateLastActiveTime()
    }

    // Store last user interaction time
    private var lastActiveTime = 0L
    // Inactivity threshold in milliseconds (1 minute)
    private val THRESHOLD = 60000L

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
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Session Expired")
        builder.setMessage("Your session has been inactive for a while. Do you want to remain logged in?")
        builder.setPositiveButton("Stay Logged In") { dialog, _ ->
            dialog.dismiss()
            // Reset inactivity timer
            updateLastActiveTime()
        }
        builder.setNegativeButton("Log Out") { dialog, _ ->
            auth.signOut()
            redirectToLogin()
            dialog.dismiss()
        }
        // Prevent dismissal by tapping outside
        builder.setCancelable(false)
        builder.show()
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
}