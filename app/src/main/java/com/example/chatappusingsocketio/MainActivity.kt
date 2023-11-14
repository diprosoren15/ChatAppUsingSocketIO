package com.example.chatappusingsocketio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappusingsocketio.adapter.MessageAdapter
import com.example.chatappusingsocketio.databinding.ActivityMainBinding
import com.example.chatappusingsocketio.model.MessageEntity
import com.example.chatappusingsocketio.model.TypeMessage
import com.example.chatappusingsocketio.others.Resource
import com.example.chatappusingsocketio.viewmodel.ChatAppViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatAppViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var messages: MutableList<MessageEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ChatAppViewModel::class.java]

        messages = mutableListOf()
        val linearLayoutManager = LinearLayoutManager(this)
        val messageAdapter = MessageAdapter(messages)


        binding.rvChat.apply {
            adapter = messageAdapter
            layoutManager = linearLayoutManager
        }

        viewModel.connect()



        binding.imageButton.setOnClickListener {
            if (binding.editTextText.text.isEmpty() || binding.editTextText.text.isBlank()) {
                return@setOnClickListener
            } else {
                val message = binding.editTextText.text.toString()
                viewModel.sendMessage(message)
                binding.editTextText.text.clear()
            }
        }
        viewModel.textLiveData.observe(this) { event ->
            if (event.hasBeenHandled.not()) {
                event.getContentIfNotHandled()?.let { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                messageAdapter.addMessage(
                                    MessageEntity(
                                        it.id,
                                        it.message,
                                        TypeMessage.TRUE
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            resource.data?.let {
                                messageAdapter.addMessage(
                                    MessageEntity(
                                        it.id,
                                        it.message,
                                        TypeMessage.FALSE
                                    )
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disconnect()
    }

}
