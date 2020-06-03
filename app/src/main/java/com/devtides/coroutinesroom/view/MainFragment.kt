package com.devtides.coroutinesroom.view


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signoutBtn.setOnClickListener { onSignout() }
        deleteUserBtn.setOnClickListener { onDelete() }
        usernameTV.text = LoginState.user?.username
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.signout.observe(this, Observer {
            Toast.makeText(activity, "Signed Out", Toast.LENGTH_LONG).show()
            goToSignupScreen()
        })
        viewModel.userDeleted.observe(this, Observer {
            Toast.makeText(activity, "User deleted", Toast.LENGTH_LONG).show()
            goToSignupScreen()
        })
    }

    private fun goToSignupScreen() {

        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("")
                .setMessage("Vc tem certeza de deletar o usuario")
                .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                    viewModel.onDeleteUser()
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}
