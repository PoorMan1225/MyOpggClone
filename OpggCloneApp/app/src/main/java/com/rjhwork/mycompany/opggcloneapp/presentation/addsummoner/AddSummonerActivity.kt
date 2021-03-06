package com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.databinding.ActivityAddSummonerBinding
import com.rjhwork.mycompany.opggcloneapp.extension.hideEndDrawable
import com.rjhwork.mycompany.opggcloneapp.extension.showEndDrawable
import org.koin.android.scope.ScopeActivity

class AddSummonerActivity : ScopeActivity(), AddSummonerContract.View {

    override val presenter: AddSummonerContract.Presenter by inject()

    private val binding: ActivityAddSummonerBinding by lazy {
        ActivityAddSummonerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    private fun initViews() = with(binding) {
        addSummonerEditText.hideEndDrawable()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun bindViews() = with(binding) {
        closeImageView.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }

        addSummonerEditText.addTextChangedListener { edit ->
            if (edit.isNullOrEmpty()) {
                addSummonerEditText.hideEndDrawable()
            } else {
                addSummonerEditText.showEndDrawable(
                    ContextCompat.getDrawable(
                        this@AddSummonerActivity,
                        R.drawable.ic_baseline_cancel_24
                    )
                )
            }
        }

        addSummonerEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP && (v as EditText).compoundDrawables[2] != null) {
                if (event.rawX >= v.right - v.compoundDrawables[2].bounds.width()) {
                    v.text.clear()
                }
                true
            }
            false
        }

        addSummonerEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = (v as EditText).text

                if (text.isNullOrEmpty().not()) {
                    presenter.fetchSummonerProfileData(text.toString())
                }
            }
            false
        }
    }

    override fun showDialog(name: String) {
        AlertDialog.Builder(this)
            .setTitle("'${name}' ???????????? ???????????? ????????????.")
            .setPositiveButton("??????") { _, _ ->

            }
            .setCancelable(false)
            // ??????????????? ?????? ??????. ????????? ???????????? ???????????? ????????? ?????????
            .show()
    }

    override fun showDialog() {
        binding.progressBar.isVisible = true
    }

    override fun dismissDialog() {
        binding.progressBar.isVisible = false
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.sliding_down)
    }

    override fun finishActivityAnimation() {
        finish()
        overridePendingTransition(
            R.anim.sliding_right_and_fade_out_stay,
            R.anim.sliding_right_and_fade_out
        )
    }
}