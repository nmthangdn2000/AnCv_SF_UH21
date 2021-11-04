const Entity = require('../models/entity.model');
const { ERROR } = require('../common/constants');

class EntityService {
  async getEntity() {
    const entity = await Entity.find();
    if (!entity) throw new Error(ERROR.CanNotGetEntity);
    return entity;
  }

  async createEntity(data) {
    const newEntity = await new Entity({
      ...data,
      create_at: new Date(),
      update_at: new Date(),
    });
    const entity = await newEntity.save();
    if (!entity) throw new Error(ERROR.CanNotCreateEntity);
  }

  async update(data) {
    const updateEntity = await Entity.updateOne({ _id: data.id }, { name: data.content, update_at: new Date() });
    if (!updateEntity || updateIntent.n < 1) throw new Error(ERROR.CanNotUpdateEntity);
  }

  async deleteById(id) {
    const deleteEntity = await Entity.deleteOne({ _id: id });
    if (deleteEntity.deletedCount < 1) throw new Error(ERROR.CanNotDeleteEntity);
  }
}

module.exports = new EntityService();
