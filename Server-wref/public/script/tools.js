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

  const provinceList = initiateProvinceChooser(provincePaths, provinceListElement, dataSummaryElements);

  document.querySelector("#droughtPrediction button").onclick = (event) =>
    handleButtonClicked(event.target, provinceList, dataSummaryElements);
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

  return provinceList;
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
  summaryElements.selectedProvince.details.innerText = "C??c n??i c?? nguy c?? b??? h???n h??n: *";
  summaryElements.prediction.averageSPI.innerText = "Ch??? s??? SPI trung b??nh: *";
  summaryElements.prediction.averageNDVI.innerText = "Ch??? s??? NDVI trung b??nh: *";
};

const findProvince = (value, provinceList) => {
  return provinceList.find((item) => item.name == value);
};

const updateSelectDatatypeSummary = (selectedDatatypes, summaryElements) => {
  let title = "Ch??a c?? Lo???i d??? li???u n??o ???????c ch???n";
  let details = "Nh???n v??o Danh S??ch c??c Lo???i d??? li???u ????? ch???n.";

  if (selectedDatatypes.length) {
    title = `${selectedDatatypes.length} lo???i d??? li???u ???????c ch???n`;
    details = selectedDatatypes.map((datatype) => datatype.optionElement.value).join(", ");
  }

  summaryElements.selectedDatatypes.title.innerText = title;
  summaryElements.selectedDatatypes.details.innerText = details;
};

const handleButtonClicked = (button, provinceList, summaryElements) => {
  const provinceChosen = findProvince(summaryElements.selectedProvince.title.innerText, provinceList);

  if (!provinceChosen) {
    Swal.fire("B???n ch??a ch???n t???nh th??nh ????? d??? ??o??n!");
    return;
  }

  button.innerText = "??ang d??? ??o??n...";
  button.style.opacity = 0.8;

  setTimeout(() => {
    button.innerText = "D??? ??o??n";
    button.style.opacity = 1;

    const { pathElement } = provinceChosen;

    summaryElements.selectedProvince.details.innerText = `C??c n??i c?? nguy c?? b??? h???n h??n: ${pathElement.getAttribute(
      "data-districts"
    )}`;
    summaryElements.prediction.averageSPI.innerText = `Ch??? s??? SPI trung b??nh: ${(
      (Math.random() - 0.5) *
      2
    ).toFixed(3)}`;
    summaryElements.prediction.averageNDVI.innerText = `Ch??? s??? NDVI trung b??nh: ${(
      (Math.random() - 0.5) * 0.3 +
      0.4
    ).toFixed(3)}`;
  }, 1500);
};
