window.onload = () => {

  initiateSubscribeDataService();
  initiateSubscribeAdvertisementService();
}

const initiateSubscribeDataService = () => {
  // Initiate data subscription field
  const subscribeDataSummaryElements = {
    selectedProvinces: {
      title: document.querySelector("#dataPrice #selectedProvincesTitle"),
      details: document.querySelector("#dataPrice #selectedProvincesDetails")
    },
    selectedDatatypes: {
      title: document.querySelector("#dataPrice #selectedDatatypesTitle"),
      details: document.querySelector("#dataPrice #selectedDatatypesDetails")
    },
    summary: document.querySelector("#dataPrice .summary")
  }

  const calculateSummary = (numSelectedProvinces) => {
    let discount = 0;
    if (numSelectedProvinces > 10)
      discount = 5;

    const result = numSelectedProvinces * 85 * (100 - discount) / 100;
    return result.toFixed(2);
  }

  const provincePaths = Array.from(document.querySelectorAll("#dataPrice .VietNamMap > path"));
  const provinceListElement = document.querySelector("#dataPrice .provinceChooser");
  const provinceList = initiateProvinceChooser(
    provincePaths, 
    provinceListElement, 
    subscribeDataSummaryElements, 
    calculateSummary
  );

  const datatypeElements = Array.from(document.querySelectorAll("#dataPrice .datatypesChooser > option"));
  const datatypeList = initiateDatatypeChooser(datatypeElements, subscribeDataSummaryElements);
}

const initiateSubscribeAdvertisementService = () => {
  // Initiate data subscription field
  const subscribeAdvertisementSummaryElements = {
    selectedProvinces: {
      title: document.querySelector("#advertisementPrice .selectedProvincesTitle"),
      details: document.querySelector("#advertisementPrice .selectedProvincesDetails")
    },

    selectedAdvertiseType: {
      title: document.querySelector("#advertisementPrice .selectedAdvertiseTypesTitle"),
      details: document.querySelector("#advertisementPrice .selectedAdvertiseTypesDetails")
    },

    advertiseType: document.querySelector("#advertisementPrice .advertiseTypesChooser"),
    customerApproachArea: document.querySelector("#advertisementPrice .public"),
    customerApproachTypesChooser: document.querySelector("#advertisementPrice .customerApproachTypesChooser"),

    customerIndividual: document.querySelector("#advertisementPrice .individual"),

    summary: document.querySelector("#advertisementPrice .summary"),
    submitButton: document.querySelector("#advertisementPrice .submitButton")
  }

  let currentNumSelectedProvinces = 0;
  const calculateSummary = (numSelectedProvinces) => {

    // Backup
    currentNumSelectedProvinces = numSelectedProvinces;

    let k = 0;

    switch (subscribeAdvertisementSummaryElements.advertiseType.value) {
      case "Cộng đồng":

      switch (subscribeAdvertisementSummaryElements.customerApproachTypesChooser.value){
        case "0-99 người (29$/tháng)":
          k = 29;
          break;
        case "100-299 người (59$/tháng)":
          k = 59;
          break;
        case "300-499 người (69$/tháng)":
          k = 69;
          break;
        case "500-1000 người (89$/tháng)":
          k = 89;
          break;
        default:
          k = 0;
          break;
      }
      break;

      case "Đối tượng cụ thể":
        break;
    }

    return numSelectedProvinces * k;
  }

  const handleAdvertiseTypesChanged = (event) => {

    subscribeAdvertisementSummaryElements.selectedAdvertiseType.title.innerText = `Quảng cáo theo hình thức ${
      subscribeAdvertisementSummaryElements.advertiseType.value
    }`;

    switch (subscribeAdvertisementSummaryElements.advertiseType.value) {
      case "Cộng đồng":
        subscribeAdvertisementSummaryElements.customerApproachArea.classList.remove("d-none");
        subscribeAdvertisementSummaryElements.customerIndividual.classList.add("d-none");
        subscribeAdvertisementSummaryElements.summary.parentElement.parentElement.classList.remove("d-none");
        subscribeAdvertisementSummaryElements.submitButton.innerText = "Đăng kí";

        subscribeAdvertisementSummaryElements.selectedAdvertiseType.details.innerText = `Số lượng tiếp cận: ${
          subscribeAdvertisementSummaryElements.customerApproachTypesChooser.value
        }`
        break;

      case "Đối tượng cụ thể":
        subscribeAdvertisementSummaryElements.customerApproachArea.classList.add("d-none");
        subscribeAdvertisementSummaryElements.customerIndividual.classList.remove("d-none");
        subscribeAdvertisementSummaryElements.summary.parentElement.parentElement.classList.add("d-none");
        subscribeAdvertisementSummaryElements.submitButton.innerText = "Liên hệ";

        subscribeAdvertisementSummaryElements.selectedAdvertiseType.details.innerText = "Vui lòng liên hệ với chúng tôi để tìm hiều thêm.";
        break;
    }

    updateSummary(subscribeAdvertisementSummaryElements, calculateSummary(currentNumSelectedProvinces))
  }

  const provincePaths = Array.from(document.querySelectorAll("#advertisementPrice .VietNamMap > path"));
  const provinceListElement = document.querySelector("#advertisementPrice .provinceChooser");
  const provinceList = initiateProvinceChooser(
    provincePaths, 
    provinceListElement, 
    subscribeAdvertisementSummaryElements,
    calculateSummary
  );

  subscribeAdvertisementSummaryElements.advertiseType.onchange = handleAdvertiseTypesChanged;
  subscribeAdvertisementSummaryElements.customerApproachTypesChooser.onchange = handleAdvertiseTypesChanged;
}

const initiateProvinceChooser = (provincePaths, provinceListElement, summaryElements, calculateSummary) => {

  const provinceList = new Array();

  // initiate
  provincePaths
    .sort((provincePath1, provincePath2) => provincePath1.id > provincePath2.id ? 1 : -1)
    .forEach(provincePath => {
      const value = provincePath.getAttribute("data-name");
      const provinceOption = document.createElement("option");
      provinceOption.value = value;
      provinceOption.innerText = value;

      // Make binding list
      provinceList.push({
        name: provincePath.getAttribute("data-name"),
        pathElement: provincePath,
        optionElement: provinceOption
      })

      // Add to View
      provinceListElement.appendChild(provinceOption);

      return provinceList;
    })

  // Make binding list
  provinceList.forEach(province => {
    const optionElement = province.optionElement;
    optionElement.onmousedown = (event) => {
      event.preventDefault();

      const value = optionElement.value;
      const province = findProvince(value, provinceList);
      handleClickProvince(province, provinceList, summaryElements, calculateSummary);
    }
  })

  // Handle VietNam map
  provinceList.forEach(province => {
    province.pathElement.onclick = (event) => {

      const province = findProvince(event.target.getAttribute("data-name"), provinceList);
      handleClickProvince(province, provinceList, summaryElements, calculateSummary);
    }
  });
}

const initiateDatatypeChooser = (datatypeListElement, summaryElements) => {
  
  // Handle datatype selected
  const datatypeList = new Array();
  datatypeListElement.forEach(datatypeOption => {
      datatypeList.push({
        optionElement: datatypeOption
    })

    datatypeOption.onmousedown = (event) => {
      event.preventDefault();

      handleDatatypeSelected(datatypeOption, datatypeList, summaryElements);
    }
  });

  return datatypeList;
}

const handleClickProvince = (province, provinceList, summaryElements, calculateSummary) => {
  province.pathElement.classList.toggle("active");
  province.optionElement.selected = !province.optionElement.selected;

  const selectedProvinces = provinceList.filter(item => item.optionElement.selected);

  updateSelectProvinceSummary(selectedProvinces, summaryElements);

  const summary = calculateSummary(selectedProvinces.length);
  updateSummary(summaryElements, summary);
}

const updateSelectProvinceSummary = (selectedProvinces, summaryElements) => {

  let title = "Chưa có tỉnh nào được chọn";
  let details = "Nhấn vào Danh Sách các tỉnh hoặc Bản Đồ để chọn."

  if (selectedProvinces.length) {
    title = `${selectedProvinces.length} tỉnh được chọn`;
    details = selectedProvinces.map(province => province.name).join(", ");
  }

  summaryElements.selectedProvinces.title.innerText = title;
  summaryElements.selectedProvinces.details.innerText = details;
}

const findProvince = (value, provinceList) => {
  return provinceList.find(item => item.name == value);
}

const handleDatatypeSelected = (datatype, datatypeList, summaryElements) => {
  datatype.selected = !datatype.selected;

  const selectedDatatypes = datatypeList.filter(item => item.optionElement.selected);
  updateSelectDatatypeSummary(selectedDatatypes, summaryElements);
}

const updateSelectDatatypeSummary = (selectedDatatypes, summaryElements) => {

  let title = "Chưa có Loại dữ liệu nào được chọn";
  let details = "Nhấn vào Danh Sách các Loại dữ liệu để chọn."

  if (selectedDatatypes.length) {
    title = `${selectedDatatypes.length} loại dữ liệu được chọn`;
    details = selectedDatatypes.map(datatype => datatype.optionElement.value).join(", ");
  }

  summaryElements.selectedDatatypes.title.innerText = title;
  summaryElements.selectedDatatypes.details.innerText = details;
}

const updateSummary = (summaryElements, summary) => {
  summaryElements.summary.innerText = `${summary}$`;
}

const handleSubscribeButtonSubmitted = (event) => {

}