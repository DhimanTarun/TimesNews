package  com.timesnews.app

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.timesnews.app.databinding.DialogNewsDetailsBinding


class NewsDetailsDialogFragment(private val mUrl:String ) : DialogFragment() {


    private var mBinding: DialogNewsDetailsBinding? = null
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogNewsDetailsBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog?.dismiss()
                        return true
                    }
                }
                return false
            }
        })

        mBinding?.backIV?.setOnClickListener {
            dialog?.dismiss()
        }
        mBinding?.newsDetailsWV?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mBinding?.loadingIV?.visibility = View.VISIBLE

            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                mBinding?.loadingIV?.visibility = View.GONE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mBinding?.loadingIV?.visibility = View.GONE

            }
        }
        mBinding?.newsDetailsWV?.clearCache(true)
        mBinding?.newsDetailsWV?.clearHistory()
        mBinding?.newsDetailsWV?.settings?.javaScriptEnabled = true
        mBinding?.newsDetailsWV?.settings?.javaScriptCanOpenWindowsAutomatically = true
        mBinding?.newsDetailsWV?.loadUrl(mUrl)


    }

}

