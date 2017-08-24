package appwork.almayce.easyboil

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.View
import appwork.almayce.easyboil.databinding.ActivityMainBinding
import com.arellomobile.mvp.MvpAppCompatActivity
import io.almayce.dev.intenta.adapter.CustomAdapter
import java.util.*

class MainActivity : MvpAppCompatActivity(), CustomAdapter.ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomAdapter
    private var list = ArrayList<String>()
    private var dm = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        adapter = CustomAdapter(this, list, dm.heightPixels / 6)
        adapter.setClickListener(this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
    }

    fun getArray(id: Int): ArrayList<String> {
        return resources.getStringArray(id).toCollection(ArrayList<String>())
    }

    fun getResColor(id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    private var descript = String()
    fun onMainItemClick(view: View) {
        descript = view.contentDescription.toString()
        var i = 0
        list.clear()

        when {
            descript == "eggs" -> i = getResColor(R.color.color_egg)
            descript == "pasta" -> i = getResColor(R.color.color_pasta)
            descript == "groats" -> i = getResColor(R.color.color_groat)
            descript == "fish" -> i = getResColor(R.color.color_fish)
            descript == "meat" -> i = getResColor(R.color.color_meat)
            descript == "veg" -> i = getResColor(R.color.color_veg)
            descript == "sea" -> i = getResColor(R.color.color_sea)
            descript == "shroom" -> i = getResColor(R.color.color_shroom)
            descript == "other" -> i = getResColor(R.color.color_other)
        }
        adapter.itemBackgroundColor = i

        when {
            descript == "eggs" -> list.addAll(getArray(R.array.eggs))
            descript == "pasta" -> list.addAll(getArray(R.array.pasta))
            descript == "groats" -> list.addAll(getArray(R.array.groats))
            descript == "fish" -> list.addAll(getArray(R.array.fish))
            descript == "meat" -> list.addAll(getArray(R.array.meat))
            descript == "veg" -> list.addAll(getArray(R.array.veg))
            descript == "sea" -> list.addAll(getArray(R.array.sea))
            descript == "shroom" -> list.addAll(getArray(R.array.shroom))
            descript == "other" -> list.addAll(getArray(R.array.other))
        }
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(view: View, position: Int) {
        var backId = R.drawable.egg
        when {
            descript == "eggs" -> backId = R.drawable.egg
            descript == "pasta" -> backId = R.drawable.pasta
            descript == "groats" -> backId = R.drawable.groat
            descript == "fish" -> backId = R.drawable.fish
            descript == "meat" -> backId = R.drawable.meat
            descript == "veg" -> backId = R.drawable.veg
            descript == "sea" -> backId = R.drawable.sea
            descript == "shroom" -> backId = R.drawable.shroom
            descript == "other" -> backId = R.drawable.other
        }

        var targetIntent = Intent(this, TimerActivity::class.java)
        targetIntent.putExtra("background", backId)
        targetIntent.putExtra("ingred", list.get(position).split("^")[0])
        targetIntent.putExtra("timer", Integer.valueOf(view.contentDescription.toString()))
        targetIntent.putExtra("color", adapter.itemBackgroundColor)
        startActivity(targetIntent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
