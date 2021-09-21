package thang.com.wref.CameraPredictionScreen.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import thang.com.wref.MainScreen.Orders.ProductDetailActivity
import thang.com.wref.databinding.PesticideItemBinding

class PesticideAdapter(
        private val pesticidesList: ArrayList<HashMap<String, String>>,
        private val context: Context
) : RecyclerView.Adapter<PesticideAdapter.ViewHolder>() {

    class ViewHolder(
            val binding: PesticideItemBinding,
            val parent: ViewGroup
    ) : RecyclerView.ViewHolder(binding.root);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PesticideItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false);

        return ViewHolder(binding, parent);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = pesticidesList[position];

        viewHolder.apply {
            binding.apply {
                txtPesticideName.text = item["name"];
                txtPesticideQuality.text = item["quality"];
                txtPesticidePrice.text = item["price"];
                txtPesticideWeight.text = item["weight"];
                Glide.with(context).load(item["image"]).into(ivPesticideImg);
                buyNow.setOnClickListener(){
                    val intent = Intent(context ,
                        ProductDetailActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("idProduct", item["id"])
                    context.startActivity(intent)
                };
            }
        }
    }

    override fun getItemCount(): Int {
        return pesticidesList.size;
    }
}