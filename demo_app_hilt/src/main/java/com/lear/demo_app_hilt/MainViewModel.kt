package com.lear.demo_app_hilt

import androidx.lifecycle.ViewModel
import com.lear.demo_app_hilt.di.Repository
import com.lear.demo_app_hilt.di.Truck
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    @Inject
    lateinit var truck: Truck

    fun doDelivery() {
        truck.deliver(repository.getCargo())
    }

}