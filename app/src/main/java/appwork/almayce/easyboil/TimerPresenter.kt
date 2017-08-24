package appwork.almayce.easyboil

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by almayce on 22.08.17.
 */
@InjectViewState
class TimerPresenter : MvpPresenter<TimerView>() {

    private var m = 0
    private var s = 0

    private var alarm = Alarm()
    private var isTicking = false
    private var player = MediaPlayer()

    private lateinit var afd: AssetFileDescriptor

    private lateinit var context: Context
    private lateinit var disposable: Disposable

    fun onCreate(context: Context) {
        this.context = context
        afd = context.assets.openFd("mus.mp3")
        player.setDataSource(
                afd.getFileDescriptor(),
                afd.getStartOffset(),
                afd.getLength());
        player.setLooping(true)
        player.prepare()
    }

    fun start(mins: Int) {
        m = mins
        s = 0

        viewState.startVisibility()
        isTicking = true
        alarm.setAlarm(context, mins)

        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .compose(SchedulersTransformer())
                .subscribe({ t ->
                    viewState.setTime(m, s)
                    s--

                    if (s == -1) {
                        s = 59
                        if (m > 0) m--
                        else {
                            timeIsOver()
                            disposable.dispose()
                        }
                    }
                })
        player.start()
    }

    fun cancel() {
        viewState.cancelVisibility()
        viewState.initTimer()
        alarm.cancelAlarm(context)
        player.pause()
        disposable.dispose()
        isTicking = false
    }

    fun timeIsOver() {
        viewState.cancelVisibility()
        viewState.initTimer()
        player.pause()
        isTicking = false
    }

    fun onResume() {
        if (!player.isPlaying && isTicking)
            player.start()
    }

    fun onPause() {
        if (player.isPlaying)
            player.pause()
    }
}