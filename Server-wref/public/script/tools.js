window.onload = () => {
  initiateDroughtPrediction();
};

const initiateDroughtPrediction = () => {
  // Initiate data subscription field
  const dataSummaryElements = {
    selectedProvince: {
      title: document.querySelector("#droughtPrediction #selectedProvinceTitle"),
      details: document.querySelector("#droughtPrediction #selectedDistricts"),
    },
    prediction: {
      averageSPI: document.querySelector("#droughtPrediction #averageSPI"),
      averageNDVI: document.querySelector("#droughtPrediction #averageNDVI"),
    },
    summary: document.querySelector("#droughtPrediction .summary"),
  };

  const provincePaths = Array.from(document.querySelectorAll("#droughtPrediction .VietNamMap > path"));
  const provinceListElement = document.querySelector("#droughtPrediction .provinceChooser");

  initiateProvinceChooser(provincePaths, provinceListElement, dataSummaryElements);

  document.querySelector("#droughtPrediction button").onclick = (event) =>
    handleButtonClicked(event.target, dataSummaryElements);
};

const initiateProvinceChooser = (provincePaths, provinceListElement, summaryElements) => {
  const provinceList = new Array();

  // initiate
  provincePaths
    .sort((provincePath1, provincePath2) => (provincePath1.id > provincePath2.id ? 1 : -1))
    .forEach((provincePath) => {
      const value = provincePath.getAttribute("data-name");
      const provinceOption = document.createElement("option");
      provinceOption.value = value;
      provinceOption.innerText = value;

      // Make binding list
      provinceList.push({
        name: provincePath.getAttribute("data-name"),
        pathElement: provincePath,
        optionElement: provinceOption,
      });

      // Add to View
      provinceListElement.appendChild(provinceOption);
    });

  // Make binding list
  provinceList.forEach((province) => {
    const optionElement = province.optionElement;
    optionElement.onmousedown = (event) => {
      event.preventDefault();

      const value = optionElement.value;
      const province = findProvince(value, provinceList);
      handleClickProvince(province, provinceList, summaryElements);
    };
  });

  // Handle VietNam map
  provinceList.forEach((province) => {
    province.pathElement.onclick = (event) => {
      const province = findProvince(event.target.getAttribute("data-name"), provinceList);
      handleClickProvince(province, provinceList, summaryElements);
    };
  });
};

const handleClickProvince = (province, provinceList, summaryElements) => {
  const selectedProvince = provinceList.find((item) => item.optionElement.selected);

  if (selectedProvince) {
    selectedProvince.optionElement.selected = false;
    selectedProvince.pathElement.classList.remove("active");
  }

  province.pathElement.classList.add("active");
  province.optionElement.selected = true;

  updateSelectProvinceSummary(province, summaryElements);
};

const updateSelectProvinceSummary = (province, summaryElements) => {
  summaryElements.selectedProvince.title.innerText = province.name;
  summaryElements.selectedProvince.details.innerText = "Các nơi có nguy cơ bị hạn hán: *";
  summaryElements.prediction.averageSPI.innerText = "Chỉ số SPI trung bình: *";
  summaryElements.prediction.averageNDVI.innerText = "Chỉ số NDVI trung bình: *";
};

const findProvince = (value, provinceList) => {
  return provinceList.find((item) => item.name == value);
};

const handleDatatypeSelected = (datatype, datatypeList, summaryElements) => {
  datatype.selected = !datatype.selected;

  const selectedDatatypes = datatypeList.filter((item) => item.optionElement.selected);
  updateSelectDatatypeSummary(selectedDatatypes, summaryElements);
};

const updateSelectDatatypeSummary = (selectedDatatypes, summaryElements) => {
  let title = "Chưa có Loại dữ liệu nào được chọn";
  let details = "Nhấn vào Danh Sách các Loại dữ liệu để chọn.";

  if (selectedDatatypes.length) {
    title = `${selectedDatatypes.length} loại dữ liệu được chọn`;
    details = selectedDatatypes.map((datatype) => datatype.optionElement.value).join(", ");
  }

  summaryElements.selectedDatatypes.title.innerText = title;
  summaryElements.selectedDatatypes.details.innerText = details;
};

const handleButtonClicked = (button, summaryElements) => {
  if (summaryElements.selectedProvince.title.innerText == "Các địa điểm có nguy cơ hạn hán cao") {
    Swal.fire("Bạn chưa chọn tỉnh thành để dự đoán!");
    return;
  }

  button.innerText = "Đang dự đoán...";
  button.style.opacity = 0.8;

  setTimeout(() => {
    button.innerText = "Dự đoán";
    button.style.opacity = 1;
    summaryElements.selectedProvince.details.innerText = `Các nơi có nguy cơ bị hạn hán: ${123}`;
    summaryElements.prediction.averageSPI.innerText = `Chỉ số SPI trung bình: ${1.6}`;
    summaryElements.prediction.averageNDVI.innerText = `Chỉ số NDVI trung bình: ${0.24}`;
  }, 1500);
};
