const express = require('express');
const router = express.Router();
const controller = require('../../controllers/order.controller');

// id user
router.get('/order', controller.getByIdUser);

router.get('/order/:id', controller.getById);

router.post('/order', controller.create);

router.put('/order/:id/status', controller.updateStatus);

router.put('/order/:id/ship', controller.updateShip);

// id shop
router.delete('/order/:id', controller.delete);

module.exports = router;
