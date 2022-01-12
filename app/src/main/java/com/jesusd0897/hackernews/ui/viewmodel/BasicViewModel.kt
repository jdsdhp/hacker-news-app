package com.jesusd0897.hackernews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal abstract class BasicViewModel : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    internal val isLoading: LiveData<Boolean> = _isLoading
    protected fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private val _errorMessage: MutableLiveData<String?> by lazy { MutableLiveData() }
    internal val errorMessage: LiveData<String?> = _errorMessage
    protected fun setErrorMessage(error: String) {
        _errorMessage.value = error
        _errorMessage.value = null
    }

    private val _infoMessage: MutableLiveData<String?> by lazy { MutableLiveData() }
    internal val infoMessage: LiveData<String?> = _infoMessage
    protected fun setInfoMessage(message: String) {
        _infoMessage.value = message
        _infoMessage.value = null
    }

    private val _successMessage: MutableLiveData<String?> by lazy { MutableLiveData() }
    internal val successMessage: LiveData<String?> = _successMessage
    protected fun setSuccessMessage(message: String) {
        _successMessage.value = message
        _successMessage.value = null
    }

}