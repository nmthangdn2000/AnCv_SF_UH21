const express = require('express');
const router = express.Router();
const controller = require('../../controllers/plantProtection.controller');

router.get('/plant-protection', controller.getByQuery);

router.get('/plant-protection/:id', controller.getById);

router.post('/plant-protection', controller.create);

// router.put('/plant-protection/:id/status', controller.updateStatus);

// router.put('/plant-protection/:id/ship', controller.updateShip);

// id shop
router.delete('/plant-protection/:id', controller.delete);

module.exports = router;
