package appwork.almayce.easyboil

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import appwork.almayce.easyboil.databinding.ActivityTimerBinding
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter

/**
 * Created by almayce on 19.10.16.
 */

class TimerActivity : MvpAppCompatActivity(), TimerView {

    @InjectPresenter
    lateinit var presenter: TimerPresenter
    private lateinit var binding: ActivityTimerBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        presenter.onCreate(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timer)
        binding.backImage.setImageResource(intent.getIntExtra("background", R.drawable.egg))
        binding.container.setBackgroundColor(intent.getIntExtra("color", R.color.color_egg))
        initUI()
    }

    fun initUI() {
        initTimer()
        val a = resources.getString(R.string.start)
        val b = intent.getStringExtra("ingred").toLowerCase()
        val c = intent.getIntExtra("timer", 0).toString()
        val d = resources.getString(R.string.min)
        val start = a + "\n" + b + "\n" + c + " " + d

        binding.tvStart.text = start
        binding.tvStart.setOnClickListener {
            presenter.start(intent.getIntExtra("timer", 0))
        }
        binding.tvTimerStop.setOnClickListener {
            presenter.cancel()
        }
        binding.tvTimerTitle.setText(intent.getStringExtra("ingred"))
    }

    override fun initTimer() {
        binding.tvTimerMin.text = "--"
        binding.tvTimerSec.text = ""
    }

    override fun startVisibility() {
        binding.tvStart.visibility = View.INVISIBLE
        binding.tvTimerTitle.visibility = View.VISIBLE
        binding.tvTimerStop.visibility = View.VISIBLE
    }

    override fun cancelVisibility() {
        binding.tvStart.visibility = View.VISIBLE
        binding.tvTimerTitle.visibility = View.INVISIBLE
        binding.tvTimerStop.visibility = View.INVISIBLE
    }

    override fun setTime(mins: Int, secs: Int) {
        binding.tvTimerMin.text = mins.toString()
        binding.tvTimerSec.text = secs.toString()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}
