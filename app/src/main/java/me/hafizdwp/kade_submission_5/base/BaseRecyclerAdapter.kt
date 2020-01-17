package me.hafizdwp.kade_submission_5.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author hafizdwp
 * 01/12/2019
 **/
abstract class BaseRecyclerAdapter<MODEL>
    : RecyclerView.Adapter<BaseRecyclerAdapter<MODEL>.BaseViewHolder>() {

    @get:LayoutRes
    abstract val bindItemLayoutRes: Int?
    abstract val mListItem: List<MODEL>

    /**
     * Default binding layout function
     * */
    abstract fun onBind(itemView: View, model: MODEL, position: Int)

    /**
     * Custom binding layout (currently with extra param layoutPosition)
     *
     * Override this useOnBindCustom and set it to true
     * Override onBindCustom after it
     * */
    open val useOnBindCustom: Boolean = false

    open fun onBindCustom(itemView: View, model: MODEL, layoutPosition: Int) {}

    /**
     * Override this if you want to return item count
     * from different way
     * */
    open fun onGetItemCount(): Int {
        return mListItem.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(bindItemLayoutRes
                ?: 0, parent, false)

        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(mListItem[position])
    }

    override fun getItemCount(): Int {
        return onGetItemCount()
    }

    inner class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: MODEL) {
            if (!useOnBindCustom)
                onBind(view, model, adapterPosition)
            else
                onBindCustom(view, model, layoutPosition)
        }
    }
}