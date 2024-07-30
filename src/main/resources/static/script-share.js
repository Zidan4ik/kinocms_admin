const
    imageMainElement = document.getElementById("image-ukr"),
    imageMainElement2 = document.getElementById("image-eng"),
    bannerElement1 = document.getElementById("banner-ukr"),
    bannerElement2 = document.getElementById("banner-eng");

let genres = [];
let dateOfPublication, urlCeo, marks, nameImage, nameBanner, languageFlag;
let status = false;
let fileImage,fileBanner = null;


changeLanguage('ukr');
imageMainElement.onclick = fileHandle;
imageMainElement2.onclick = fileHandle;
bannerElement1.onclick = fileHandle;
bannerElement2.onclick = fileHandle;

function changeLanguage(language) {
    languageFlag = language;
    getDataFromInputs(languageFlag);
    assignDataInputs(languageFlag);
}

function getDataFromInputs(languageCode) {
    if (languageCode === 'ukr') {
        dateOfPublication = document.getElementById(`dateOfPublication-eng`).value;
        marks = document.getElementById(`marks-eng`).value;
        urlCeo = document.getElementById(`urlCeo-eng`).value;
    } else if (languageCode === 'eng') {
        dateOfPublication = document.getElementById(`dateOfPublication-ukr`).value;
        marks = document.getElementById(`marks-ukr`).value;
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`dateOfPublication-${languageCode}`).value = dateOfPublication;
    document.getElementById(`marks-${languageCode}`).value = marks;
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;
    document.getElementById(`statusSwitch`).checked = status;
    if (nameImage !== undefined && nameImage !== null) {
        document.getElementById(`image-download-${languageCode}`).src = nameImage;
    } else {
        if (fileImage !== undefined && fileImage !== null) {
            document.getElementById(`image-download-${languageCode}`).src = URL.createObjectURL(fileImage);
        } else {
            document.getElementById(`image-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }
    if (nameBanner !== undefined && nameBanner !== null) {
        document.getElementById(`banner-download-${languageCode}`).src = nameBanner;
    } else {
        if (fileBanner !== undefined && fileBanner !== null) {
            document.getElementById(`banner-download-${languageCode}`).src = URL.createObjectURL(fileBanner);
        } else {
            document.getElementById(`banner-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }
}

function getFormObject() {
    let formObject = new FormData();
    formObject.append("titleUkr", $("#title-ukr").val());
    formObject.append("descriptionUkr", $("#description-ukr").val());
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());

    formObject.append("titleEng", $("#title-eng").val());
    formObject.append("descriptionEng", $("#description-eng").val());
    formObject.append("titleCeoEng", $("#titleCeo-eng").val());
    formObject.append("keywordsCeoEng", $("#keywordsCeo-eng").val());
    formObject.append("descriptionCeoEng", $("#descriptionCeo-eng").val());

    formObject.append("dateOfPublication", dateOfPublication);
    formObject.append("marks", marks);
    formObject.append("urlCeo", urlCeo);
    formObject.append("status", status);
    if (fileImage != null) {
        formObject.append("fileImage", fileImage);
    }
    if (fileBanner != null) {
        formObject.append("fileBanner", fileBanner);
    }
    return formObject;
}

function fileHandle(event) {
    if (event.target.dataset.index) {
        const index = event.target.dataset.index;
        const type = event.target.dataset.type;
        if (type === "imageDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-download-" + languageFlag);
            fileImage = null;
            nameImage = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

        } else if (type === "imageDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImage = inputElement.files[0];
                inputElement.value = "";
            }
        } else if (type === "bannerDelete-" + languageFlag) {
            const imageElement = document.getElementById("banner-download-" + languageFlag);
            fileBanner = null;
            nameBanner = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

        } else if (type === "bannerDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-banner-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("banner-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileBanner = inputElement.files[0];
                inputElement.value = "";
            }
        }
        console.log(type + ' : ' + index);
    }
}

const TagifyCustomInlineSuggestionEl_UKR = document.querySelector("#marks-ukr");
const TagifyCustomInlineSuggestionEl_ENG = document.querySelector("#marks-eng");
const whitelist = [
    "3D",
    "2D",
    "IMAX",
    "DBOX",
    "18+",
    "16+",
    "12+"
];
let TagifyCustomInlineSuggestion_UKR = new Tagify(TagifyCustomInlineSuggestionEl_UKR, {
    whitelist: whitelist,
    maxTags: 3,
    dropdown: {
        maxItems: 10,
        classname: "tags-inline",
        enabled: 0,
        closeOnSelect: false
    }
});

let TagifyCustomInlineSuggestion_ENG = new Tagify(TagifyCustomInlineSuggestionEl_ENG, {
    whitelist: whitelist,
    maxTags: 3,
    dropdown: {
        maxItems: 10,
        classname: "tags-inline",
        enabled: 0,
        closeOnSelect: false
    }
});

document.getElementById('statusSwitch').addEventListener('change', function () {
    if (this.checked) {
        status = true;
    } else {
        status = false;
    }
});
