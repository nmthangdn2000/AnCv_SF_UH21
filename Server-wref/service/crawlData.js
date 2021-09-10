const fetch = require("node-fetch");
const crops = require("../models/crops");
const translate = require("translate-google");

// When you have your own Client ID and secret, put down their values here:
const translateText = async (doc) => {
  return translate("I speak Chinese", { to: "vi" })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      console.error(err);
    });
};
fetch(
  "http://harvesthelper.herokuapp.com/api/v1/plants?api_key=0fbac648e0125b04d13657661b7bebe8"
)
  .then((response) => response.json())
  .then(async (data) => {
    for (let i = 10; i < 11; i++) {
      const nameCrops = await translateText(data[i].name);
      console.log(nameCrops);
      //   const description = await translateText(data[i].description);
      //   const optimal_sun = await translateText(data[i].optimal_sun);
      //   const optimal_soil = await translateText(data[i].optimal_soil);
      //   const planting_considerations = await translateText(
      //     data[i].planting_considerations
      //   );
      //   const when_to_plant = await translateText(data[i].when_to_plant);
      //   const growing_from_seed = await translateText(data[i].growing_from_seed);
      //   const transplanting = await translateText(data[i].transplanting);
      //   const spacing = await translateText(data[i].spacing);
      //   const watering = await translateText(data[i].watering);
      //   const feeding = await translateText(data[i].feeding);
      //   const other_care = await translateText(data[i].other_care);
      //   const diseases = await translateText(data[i].diseases);
      //   const pests = await translateText(data[i].pests);
      //   const harvesting = await translateText(data[i].harvesting);
      //   const storage_use = await translateText(data[i].storage_use);

      //   const newCrops = await new crops({
      //     name: nameCrops,
      //     description: description,
      //     optimal_sun: optimal_sun,
      //     optimal_soil: optimal_soil,
      //     planting_considerations: planting_considerations,
      //     when_to_plant: when_to_plant,
      //     growing_from_seed: growing_from_seed,
      //     transplanting: transplanting,
      //     spacing: spacing,
      //     watering: watering,
      //     feeding: feeding,
      //     other_care: other_care,
      //     diseases: diseases,
      //     pests: pests,
      //     harvesting: harvesting,
      //     storage_use: storage_use,
      //     image_url: data[0].image_url,
      //   });
      //   await newCrops
      //     .save()
      //     .then((data) => console.log("ok"))
      //     .catch((err) => console.log(err));
    }
  })
  .catch((err) => console.log(err));
//   console.log(data.text)
