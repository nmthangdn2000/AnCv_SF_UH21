package thang.com.wref.CameraPredictionScreen.Prediction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.bumptech.glide.Glide
import thang.com.wref.CameraPredictionScreen.Adapters.PesticideAdapter
import thang.com.wref.databinding.ActivityDiseaseDetailBinding

class DiseaseDetail : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseDetailBinding;
    private lateinit var pesticideAdapter: PesticideAdapter;

    companion object {
        val TAG: String = DiseaseDetail::class.java.simpleName;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        binding = ActivityDiseaseDetailBinding.inflate(layoutInflater);
        setContentView(binding.root);

        var diseaseData: DiseaseJson? = null;

        // Header
        intent.extras?.apply {

            val plantName = getString("plantName");
            val diseaseName = getString("diseaseName");

            binding.txtDiseaseName.text = diseaseName;
            binding.txtPlantName.text = plantName;

            diseaseData = getDiseaseData("${plantName}_${diseaseName}");
        }

        // Pesticides Recycle View
        val pesticidesList = parsePesticidesData(diseaseData?.pesticide!!);
        pesticideAdapter = PesticideAdapter(pesticidesList, baseContext);

        val layoutManager = LinearLayoutManager(this);
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL;
        binding.rvPesticidesList.layoutManager = layoutManager;

        binding.rvPesticidesList.adapter = pesticideAdapter;

        // Back Button
        binding.rltBack.setOnClickListener {
            Log.d("Detail", "123");
            finish();
        }
    }

    private fun parsePesticidesData(pesticide: Array<Pesticide>) : ArrayList<HashMap<String, String>> {

        val result: ArrayList<HashMap<String, String>> = ArrayList<HashMap<String, String>>();

        pesticide.forEach {
            val pesticide = HashMap<String, String>();

            pesticide["name"] = it.name;
            pesticide["quality"] = it.quality;
            pesticide["price"] = it.price;
            pesticide["weight"] = it.weight;
            pesticide["image"] = it.image;
            pesticide["id"] = it.id;

            result.add(pesticide);
        }

        return result;
    }

    class Pesticide (
        val name: String,
        val quality: String,
        val weight: String,
        val price: String,
        val image: String,
        val id: String
    )

    class DiseaseJson(
        val diseaseId: String,
        val diseaseName: String,
        val cause: String,
        val symptom: String,
        val prevention: String,
        val image: String,
        val pesticide: Array<Pesticide>
    );

    private fun getDiseaseData(diseaseId: String): DiseaseJson {

        val data = Klaxon()
                .parseArray<DiseaseJson>(assets.open("disease.json"));

        var result: DiseaseJson? = null;

        data?.let { diseaseData ->
            val disease: DiseaseJson = diseaseData.find{
                it.diseaseId == diseaseId;
            }!!;

            result = disease;

            binding.txtSymptom.text = disease.symptom;
            binding.txtPrevention.text = disease.prevention;
            binding.txtCause.text = disease.cause;
            Glide.with(baseContext).load(disease.image).into(binding.imgDisease);
        }

        return result!!;
    }
}