    package com.example.kotlin_database

    import android.content.Context
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.lifecycle.Observer
    import com.example.kotlin_database.ui.theme.KotlindatabaseTheme
    import androidx.activity.viewModels
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.kotlin_database.factory.UserViewModelFactory
    import androidx.activity.viewModels
    import androidx.lifecycle.ViewModelProvider
    import com.example.kotlin_database.database.UserDatabase

    import com.example.kotlin_database.repository.UserRepository
    import com.example.kotlin_database.ui.settings.SettingsFragment
    import com.google.gson.Gson
    import com.google.gson.reflect.TypeToken
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import java.util.UUID

    class MainActivity : AppCompatActivity() {
        private lateinit var userViewModel: UserViewModel
        private lateinit var adapter: UserAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Display SettingsFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .commit()

            // Delete all tables
            val userDatabase = UserDatabase.getDatabase(this)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase.clearAllTables()
                Log.d("MyApp", "Database cleared at app startup")
            }

            // Initialize the repository and ViewModel
            val repository = UserRepository.getRepository(this)
            val viewModelFactory = UserViewModelFactory(repository)
            userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

            // Read JSON from raw and insert users into the database
            val json = readJsonFromRaw()
            val users = parseUsersFromJson(json)
            userViewModel.insertAll(users)

            // Configure the RecyclerView
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
            adapter = UserAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Observe LiveData and update the RecyclerView
            userViewModel.allUsers.observe(this) { users ->
                users?.let {
                    adapter.setUsers(it)
                }
            }
            // Get references to UI elements
            val editTextName = findViewById<EditText>(R.id.edit_text_name)
            val editTextEmail = findViewById<EditText>(R.id.edit_text_email)
            val buttonAddUser = findViewById<Button>(R.id.button_add_user)

            // Handle button click
            buttonAddUser.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()

                if (name.isNotEmpty() && email.isNotEmpty()) {
                    // Create a new User object
                    val newUser = User(name = name, email = email)

                    // Insert user into the database
                    userViewModel.insert(newUser)

                    // Show success message and clear input fields
                    Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show()
                    editTextName.text.clear()
                    editTextEmail.text.clear()
                } else {
                    // Show error message
                    Toast.makeText(this, "Please enter both name and email!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun readJsonFromRaw(): String {
            val inputStream = resources.openRawResource(R.raw.users)
            return inputStream.bufferedReader().use { it.readText() }
        }

        private fun parseUsersFromJson(json: String): List<User> {
            val type = object : TypeToken<List<User>>() {}.type
            val users = Gson().fromJson<List<User>>(json, type)

            // Add unique IDs for users
            return users.map { user ->
                user.copy(id = UUID.randomUUID().toString())
            }
        }
    }


