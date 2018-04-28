import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import com.google.firebase.database.DataSnapshot
import com.w9jds.marketbot.classes.models.Region
import io.reactivex.subjects.BehaviorSubject

class RegionAdapter(private val host: Activity, private val listener: BehaviorSubject<DataSnapshot>) : BaseAdapter(), SpinnerAdapter {

    private val inflater: LayoutInflater = host.layoutInflater
    private var items: List<DataSnapshot> = emptyList()

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Region? {
        return items[position].getValue(Region::class.java)
    }

    override fun getViewTypeCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int): Long {
        val region = items[position].getValue(Region::class.java)
        return region?.region_id?.toLong() ?: position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        return items.count()
    }

}