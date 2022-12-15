package binar.academy.flightgoadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.flightgoadmin.databinding.CardTiketBinding
import binar.academy.flightgoadmin.model.tiket.TiketResponse
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import com.bumptech.glide.Glide

class TiketAdapter(private var listTiketItem: List<TiketResponseItem>) : RecyclerView.Adapter<TiketAdapter.ViewHolder>(){
    class ViewHolder(var binding: CardTiketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardTiketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDepart.text = listTiketItem[position].bandaraAsal
        holder.binding.tvArrival.text = listTiketItem[position].bandaraTujuan
        holder.binding.tvKodeDepart.text = listTiketItem[position].kodeNegaraAsal
        holder.binding.tvKodeArrival.text = listTiketItem[position].kodeNegaraTujuan
        holder.binding.tvTimeDepart.text = listTiketItem[position].depatureTime
        //holder.binding.tvFlightNo.text = listTiketItem[position].
        holder.binding.tvClassFlight.text = listTiketItem[position].jenisPenerbangan
        holder.binding.tvPrice.text = buildString {
            append("Rp. ")
            append(listTiketItem[position].price.toString())
        }
        Glide.with(holder.itemView).load(listTiketItem[position].imageProduct).into(holder.binding.imgFlight)
    }

    override fun getItemCount(): Int = listTiketItem.size

    fun setData(data : ArrayList<TiketResponseItem>){
        this.listTiketItem = data
    }
}