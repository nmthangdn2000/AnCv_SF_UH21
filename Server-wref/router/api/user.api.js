const express = require('express');
const router = express.Router();
const controller = require('../../controllers/user.controller');

// router.get('/infor', controller.getInfUser)

// router.put('/put-infor', controller.editUser)

// router.delete('/delete-user', controller.deleteUser)

router.get('/checklogin', controller.checkLogin);

module.exports = router;
