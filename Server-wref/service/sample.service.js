const Sample = require('../models/sample.model');
const { ERROR } = require('../common/constants');

class SampleService {
  async getSample() {
    const samples = await Sample.find().populate('idIntent');
    if (!samples) throw ERROR.CanNotGetSamples;
    return samples;
  }

  // async getSampleByIdIntent() {
  //   const samples = await Sample.findOne().populate('idIntent');
  //   if (!samples) throw ERROR.CanNotGetSamples;
  //   return samples;
  // }

  async createSample(idIntent, data) {
    const newSample = await new Sample({
      idIntent: idIntent,
      content: data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const sample = await newSample.save();
    if (!sample) throw ERROR.CanNotCreateSamples;
  }

  async createMultipleSample(idIntent, data) {
    const arraySample = [];
    data.forEach((d) => {
      const newSample = new Sample({
        idIntent: idIntent,
        content: d,
        create_at: new Date(),
        update_at: new Date(),
      });
      arraySample.push(newSample.save());
    });

    const samples = await Promise.all(arraySample);

    if (!samples) throw ERROR.CanNotCreateSamples;
  }

  async updateSample(data) {
    const updateSamples = await Sample.updateOne({ _id: data.id }, { content: data.content, update_at: new Date() });
    if (!updateSamples || updateSamples.n < 1) throw ERROR.CanNotUpdateSamples;
  }

  async deleteSample(data) {
    const deleteSamples = await Sample.deleteOne({ _id: data });
    if (!deleteSamples || deleteSamples.deletedCount < 1) throw ERROR.CanNotDeleteSamples;
  }
}

module.exports = new SampleService();
