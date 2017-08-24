package appwork.almayce.easyboil

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by almayce on 19.08.17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface TimerView : MvpView {
    fun startVisibility()
    fun cancelVisibility()
    fun setTime(mins: Int, secs: Int)
    fun initTimer()
}