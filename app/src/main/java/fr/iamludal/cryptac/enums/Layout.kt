package fr.iamludal.cryptac.enums

import androidx.databinding.ViewDataBinding
import fr.iamludal.cryptac.adapter.CryptoBaseAdapter
import fr.iamludal.cryptac.adapter.CryptoGridAdapter
import fr.iamludal.cryptac.adapter.CryptoListAdapter
import kotlin.reflect.KClass

/**
 * Represent a layout for the RecyclerView.
 */
enum class Layout(val adapter: KClass<out CryptoBaseAdapter<out ViewDataBinding>>) {
    GRID(CryptoGridAdapter::class),
    LIST(CryptoListAdapter::class)
}
