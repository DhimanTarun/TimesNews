package com.timesnews.app

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.timesnews.app.databinding.ActivityMainBinding
import com.timesnews.app.interfaces.IRecyclerViewOnClickListener
import com.timesnews.app.model.MostViewedNews
import com.timesnews.app.repository.MostViewedUiState
import com.timesnews.app.viewModels.MostViewedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IRecyclerViewOnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.toolbar)


        val viewModel: MostViewedViewModel =
            ViewModelProvider(this)[MostViewedViewModel::class.java]
        viewModel.getMostViewedNews(7.toString())
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { data ->
                    when (data) {
                        is MostViewedUiState.Loading -> {
                            mBinding.loadingIV.visibility = View.VISIBLE
                        }

                        is MostViewedUiState.Success -> {
                            mBinding.loadingIV.visibility = View.GONE

                            mBinding.mMostViewedNewsAdapter = MostViewedNewsAdapter(
                                (viewModel.uiState.value as MostViewedUiState.Success).news,
                                this@MainActivity
                            )
                        }

                        is MostViewedUiState.Error -> {
                            mBinding.loadingIV.visibility = View.GONE

                        }

                    }


                }
            }
        }

    }


    override fun onclick(value: MostViewedNews.Result?) {

        val mTermsAndConditionDialog = NewsDetailsDialogFragment(value?.url!!)
        mTermsAndConditionDialog.show(supportFragmentManager, mTermsAndConditionDialog.tag)

    }
}