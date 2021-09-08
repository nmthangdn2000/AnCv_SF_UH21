package thang.com.wref.MainScreen.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import thang.com.wref.R
import thang.com.wref.databinding.DiseaseItemBinding

abstract class DiseaseAdapter(
) : RecyclerView.Adapter<DiseaseAdapter.ViewHolder>() {

    private var diseasesList: ArrayList<HashMap<String, String>> = ArrayList();

    abstract fun handleItemClick (diseaseName: String, plantName: String);

    companion object {
        val TAG: String = DiseaseAdapter::class.java.simpleName;
    }

    class ViewHolder(
            val binding: DiseaseItemBinding,
            hasDisease: Boolean,
            val parent: ViewGroup
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            if (hasDisease) {
                binding.cvDiseaseItem
                        .setCardBackgroundColor(ResourcesCompat
                                .getColor(parent.resources, R.color.cs_light_danger, null));

                binding.txtDiseaseName.setTextColor(ResourcesCompat
                        .getColor(parent.resources, R.color.cs_danger, null));

                binding.txtPercent.setTextColor(ResourcesCompat
                        .getColor(parent.resources, R.color.cs_danger, null));
            } else {
                binding.cvDiseaseItem.setCardBackgroundColor(ResourcesCompat
                        .getColor(parent.resources, R.color.cs_light_success, null));

                binding.txtDiseaseName.setTextColor(ResourcesCompat
                        .getColor(parent.resources, R.color.cs_success, null));

                binding.txtPercent.setTextColor(ResourcesCompat
                        .getColor(parent.resources, R.color.cs_success, null));
            }
        }
    };

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DiseaseItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false);

        val hasDisease = when(viewType) {
            1 -> true
            0 -> false
            else -> false
        }

        return ViewHolder(binding, hasDisease, parent);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diseaseInfo: HashMap<String, String> = diseasesList[position];

        holder.binding.txtDiseaseName.text = diseaseInfo["diseaseName"];
        holder.binding.txtPlantName.text = diseaseInfo["plantName"];
        holder.binding.txtPercent.text = "${diseaseInfo["percent"]} %";

        Log.d(TAG, "Add item ${diseaseInfo}");

        holder.binding.root.setOnClickListener { view ->
            handleItemClick(diseaseInfo["diseaseName"]!!, diseaseInfo["plantName"]!!);
            Log.d(TAG, "Item ${position} clicked!");
        }
    }

    override fun getItemCount(): Int {
        return diseasesList.size;
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position);

        return when(diseasesList[position]["diseaseName"]?.contains("khỏe mạnh", ignoreCase = true)) {
            true -> 0
            false -> 1
            else -> 1
        }
    }

    fun addItem(item: HashMap<String, String>) {
        diseasesList.add(item);
        this.notifyItemInserted(diseasesList.size - 1);
    }

    fun replaceList(newList: ArrayList<HashMap<String, String>>) {
        diseasesList = newList;
        this.notifyDataSetChanged();
    }

    fun clear() {
        diseasesList.clear();
        this.notifyDataSetChanged();
    }
}