const ThemeAgri = require("../models/themeAgri");
const InforAgri = require("../models/inforAgriculture");

const getAllInfoAgri = async (req, res) => {
  await ThemeAgri.find()
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};

const getAllDataForTheme = async (req, res) => {
  await InforAgri.find({ idTheme: req.params.id })
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};

const getDetailAgri = async (req, res) => {
  await InforAgri.find({ _id: req.params.id })
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};

const postThemeAgri = async (req, res) => {
  let name = req.body.name;
  let slug = ChangeToSlug(name);
  let newThemeAgri = new ThemeAgri({
    name: name,
    slug: slug,
  });
  await newThemeAgri
    .save()
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};
const postItemAgri = async (req, res) => {
  let newInforAgri = new InforAgri({
    idTheme: req.body.theme,
    title: req.body.title,
    url: req.body.url,
    image: req.file.filename,
    created_at: new Date(),
  });
  await newInforAgri
    .save()
    .then((data) => res.send(data))
    .catch((err) => console.log(err));
};
function ChangeToSlug(title) {
  //Đổi chữ hoa thành chữ thường
  slug = title.toLowerCase();

  //Đổi ký tự có dấu thành không dấu
  slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, "a");
  slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, "e");
  slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, "i");
  slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, "o");
  slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, "u");
  slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, "y");
  slug = slug.replace(/đ/gi, "d");
  //Xóa các ký tự đặt biệt
  slug = slug.replace(
    /\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi,
    ""
  );
  //Đổi khoảng trắng thành ký tự gạch ngang
  slug = slug.replace(/ /gi, "-");
  //Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
  //Phòng trường hợp người nhập vào quá nhiều ký tự trắng
  slug = slug.replace(/\-\-\-\-\-/gi, "-");
  slug = slug.replace(/\-\-\-\-/gi, "-");
  slug = slug.replace(/\-\-\-/gi, "-");
  slug = slug.replace(/\-\-/gi, "-");
  //Xóa các ký tự gạch ngang ở đầu và cuối
  slug = "@" + slug + "@";
  slug = slug.replace(/\@\-|\-\@|\@/gi, "");
  //In slug ra textbox có id “slug”
  return slug;
}
module.exports = {
  getAllInfoAgri,
  getAllDataForTheme,
  getDetailAgri,
  postThemeAgri,
  postItemAgri,
};
