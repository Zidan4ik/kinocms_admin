let urlCeo, languageFlag, phone1, phone2;
let status = false;

changeLanguage('ukr');
function changeLanguage(language) {
    languageFlag = language;
    getDataFromInputs(languageFlag);
    assignDataInputs(languageFlag);
}

function getDataFromInputs(languageCode) {
    if (languageCode === 'ukr') {
        phone1 = document.getElementById(`phone1-eng`).value;
        phone2 = document.getElementById(`phone2-eng`).value;
        urlCeo = document.getElementById(`urlCeo-eng`).value;
    } else if (languageCode === 'eng') {
        phone1 = document.getElementById(`phone1-ukr`).value;
        phone2 = document.getElementById(`phone2-ukr`).value;
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`phone1-${languageCode}`).value = phone1;
    document.getElementById(`phone2-${languageCode}`).value = phone2;
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;
    document.getElementById(`statusSwitch`).checked = status;
}

function getFormObject() {
    let formObject = new FormData();
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());
    formObject.append("seoTextUkr", $("#seoText-ukr").val());

    formObject.append("titleCeoEng", $("#titleCeo-eng").val());
    formObject.append("keywordsCeoEng", $("#keywordsCeo-eng").val());
    formObject.append("descriptionCeoEng", $("#descriptionCeo-eng").val());
    formObject.append("seoTextEng", $("#seoText-eng").val());

    formObject.append("phone1", phone1);
    formObject.append("phone2", phone2);
    formObject.append("urlCeo", urlCeo);
    formObject.append("status", status);
    return formObject;
}

document.getElementById('statusSwitch').addEventListener('change', function () {
    if (this.checked) {
        status = true;
    } else {
        status = false;
    }
});
