const Intent = require('../models/intent.model');
const { ERROR } = require('../common/constants');

class IntentService {
  async getIntent() {
    const intent = await Intent.find();
    if (!intent) throw ERROR.CanNotGetIntent;
    return intent;
  }

  async createIntent(data, feedback, type, script_entity, script_repeat, script_feedback, api, code, option) {
    const script = createScript(script_entity, script_repeat, script_feedback);
    let scriptCode = {};
    if (api && code) {
      scriptCode.api = api;
      scriptCode.code = code;
      scriptCode.option = option;
    }
    const newIntent = await new Intent({
      intent: data,
      feedback: feedback,
      script,
      type,
      scriptCode,
      create_at: new Date(),
      update_at: new Date(),
    });
    const intent = await newIntent.save();
    if (!intent) throw ERROR.CanNotCreateIntent;
    return intent._id;
  }

  async updateIntent(data) {
    const updateIntent = await Intent.updateOne({ _id: data.id }, { content: data.content, update_at: new Date() });
    if (!updateIntent || updateIntent.n < 1) throw ERROR.CanNotUpdateIntent;
  }

  async deleteIntent(data) {
    const deleteStopword = await Intent.findByIdAndDelete({ _id: data });
    if (!deleteStopword) throw ERROR.CanNotDeleteStopword;
    return deleteStopword._id;
  }
}

module.exports = new IntentService();

function createScript(script_entity, script_repeat, script_feedback) {
  const script = [];
  if (script_entity == '') return script;
  if (!Array.isArray(script_entity)) {
    const data = {
      entity: script_entity,
      repeat: Number(script_repeat),
      feedback: script_feedback,
    };
    script.push(data);
    return script;
  }
  script_entity.forEach((s, index) => {
    const data = {
      entity: s,
      repeat: script_repeat[index],
      feedback: script_feedback[index],
    };
    script.push(data);
  });
  return script;
}
