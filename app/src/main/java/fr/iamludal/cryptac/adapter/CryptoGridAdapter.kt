package fr.iamludal.cryptac.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iamludal.cryptac.R
import fr.iamludal.cryptac.databinding.FragmentCardItemBinding
import fr.iamludal.cryptac.viewmodel.MainViewModel

class CryptoGridAdapter(
    viewModel: MainViewModel,
    private val context: Context
) :
    CryptoBaseAdapter<FragmentCardItemBinding>(viewModel, R.layout.fragment_card_item) {

    /**
     * Set the model of the fragment to the corresponding crypto in the local list and set up
     * the click listeners
     * @param holder the ViewHolder
     * @param position the item position in the list
     */
    override fun onBindViewHolder(holder: ViewHolder<FragmentCardItemBinding>, position: Int) {
        holder.binding.model = getItem(position)
        super.onBindViewHolder(holder, position, holder.binding.favorite, getItem(position))
    }

    /**
     * Get the layout manager that corresponds to the adapter
     */
    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)
}
