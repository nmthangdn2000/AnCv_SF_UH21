const express = require('express');
const router = express.Router();
const controller = require('../../controllers/category.controller');

router.get('/category', controller.getAll);

router.get('/category/:id', controller.getById);

router.post('/category', controller.create);

// id shop
router.put('/category/:id', controller.update);

// id shop
router.delete('/category/:id', controller.delete);

module.exports = router;
