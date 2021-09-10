const express = require("express");
const router = express.Router();
const controller = require("../../controllers/harvesthelper.controller");

router.get("/harvesthelper/all", controller.getAll);
router.get("/harvesthelper/id/:id", controller.getByID);

module.exports = router;
