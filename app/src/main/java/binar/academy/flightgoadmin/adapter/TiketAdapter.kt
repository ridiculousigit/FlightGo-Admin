package binar.academy.flightgoadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.flightgoadmin.databinding.CardTiketBinding
import binar.academy.flightgoadmin.model.tiket.TiketResponse
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import com.bumptech.glide.Glide

class TiketAdapter(
    private val context: Context,
    private var listTiketItem: List<TiketResponseItem>,
    private val onClick : onClickInterface
    ) : RecyclerView.Adapter<TiketAdapter.ViewHolder>(){
    class ViewHolder(var binding: CardTiketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardTiketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val currentList = listTiketItem[position]
            tvDepart.text = currentList.bandaraAsal
            tvDepart.text = currentList.bandaraAsal
            tvArrival.text = currentList.bandaraTujuan
            tvKodeDepart.text = currentList.kodeNegaraAsal
            tvKodeArrival.text = currentList.kodeNegaraTujuan
            tvTimeDepart.text = currentList.depatureTime
            tvFlightType.text = currentList.jenisPenerbangan
            tvPrice.text = buildString {
                append("Rp. ")
                append(currentList.price.toString())
            }
        }

        holder.binding.root.setOnClickListener {
            onClick.onItemClick(listTiketItem[position])
        }
    }

    override fun getItemCount(): Int = listTiketItem.size

    interface onClickInterface{
        fun onItemClick(list: TiketResponseItem)
    }
}