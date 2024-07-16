const galleriesElementUkr = document.getElementById("galleries-ukr"),
    galleriesElementEng = document.getElementById("galleries-eng");

galleriesElementUkr.onclick = fileHandle;
galleriesElementEng.onclick = fileHandle;

const elements = [
    document.getElementById("image-logo-ukr"),
    document.getElementById("image-logo-eng"),
    document.getElementById("image-banner-ukr"),
    document.getElementById("image-banner-eng")
];
elements.forEach(e =>{
    e.onclick = fileHandle;
})

let languageFlag,imageLogo,imageBanner,urlCeo,marks;
let array = new Array(5);
let genres = [];
let fileLogo = null;
let fileBanner = null;

createGallery(array);
changeLanguage('ukr');
function changeLanguage(language) {
    languageFlag = language;
    getDataFromInputs(languageFlag);
    assignDataInputs(languageFlag);
    render();
}
function getDataFromInputs(languageCode) {
    if (languageCode === 'ukr') {
        marks = document.getElementById(`marks-eng`).value;
        let selectedOptions = document.getElementById(`genres-eng`).selectedOptions;
        genres = [];
        for (let i = 0; i < selectedOptions.length; i++) {
            genres.push(selectedOptions[i].value);
        }
        urlCeo = document.getElementById(`urlCeo-eng`).value;
    } else if (languageCode === 'eng') {
        marks = document.getElementById(`marks-ukr`).value;
        let selectedOptions = document.getElementById(`genres-ukr`).selectedOptions;
        genres = [];
        for (let i = 0; i < selectedOptions.length; i++) {
            genres.push(selectedOptions[i].value);
        }
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`marks-${languageCode}`).value = marks;
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;

    if (imageLogo !== undefined && imageLogo !== null) {
        document.getElementById(`image-logo-download-${languageCode}`).src = imageLogo;
    } else {
        if (fileLogo !== undefined && fileLogo !== null) {
            document.getElementById(`image-logo-download-${languageCode}`).src = URL.createObjectURL(fileLogo);
        } else {
            document.getElementById(`image-logo-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }

    if (imageBanner !== undefined && imageBanner !== null) {
        document.getElementById(`image-banner-download-${languageCode}`).src = imageBanner;
    } else {
        if (fileBanner !== undefined && fileBanner !== null) {
            document.getElementById(`image-banner-download-${languageCode}`).src = URL.createObjectURL(fileBanner);
        } else {
            document.getElementById(`image-banner-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }
    $(`#genres-${languageCode}`).selectpicker('val', genres);
}

function getFormObject(language) {
    let formObject = new FormData();
    formObject.append("titleUkr", $("#title-ukr").val());
    formObject.append("descriptionUkr", $("#description-ukr").val());
    formObject.append("conditionUkr", $("#condition-ukr").val());
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());

    formObject.append("titleEng", $("#title-eng").val());
    formObject.append("descriptionEng", $("#description-eng").val());
    formObject.append("conditionEng", $("#condition-eng").val());
    formObject.append("titleCeoEng", $("#titleCeo-eng").val());
    formObject.append("keywordsCeoEng", $("#keywordsCeo-eng").val());
    formObject.append("descriptionCeoEng", $("#descriptionCeo-eng").val());

    formObject.append("marks", $(`#marks-${languageFlag}`).val());
    formObject.append("genres", $(`#genres-${languageFlag}`).val());
    formObject.append("urlCeo", $(`#urlCeo-${languageFlag}`).val());

    if (fileLogo != null) {
        formObject.append("fileLogo", fileLogo);
    }
    for (let i = 0; i < array.length; i++) {
        if (array[i].file != null) {
            formObject.append("imagesMultipart", array[i].file);
        }
    }
    return formObject;
}

function fileHandle(event) {
    if (event.target.dataset.index) {
        const index = event.target.dataset.index;
        const type = event.target.dataset.type;
        if (type === 'galleries-delete-' + languageFlag) {
            array[index].file = null;
            array[index].link = null;
            array[index].name = null;
        } else if (type === 'galleries-download-' + languageFlag) {
            const inputElement = document.getElementById(`input-file-${index}-` + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById(`image-download-${index}-` + languageFlag);
                array[index].link = imageElement.src = URL.createObjectURL(inputElement.files[0]);
                array[index].file = inputElement.files[0];
                array[index].name = null;
            }
            inputElement.click();
        } else if (type === "logoDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-logo-download-" + languageFlag);
            fileLogo = null;
            imageLogo = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === "logoDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-logo-" + languageFlag);
            console.log(inputElement);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-logo-download-" + languageFlag);
                console.log(imageElement);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileLogo = inputElement.files[0];
                inputElement.value = "";
            }
        } else if (type === "bannerDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-banner-download-" + languageFlag);
            fileBanner = null;
            imageBanner = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === 'bannerDownload-' + languageFlag) {
            const inputElement = document.getElementById("btn-download-banner-" + languageFlag);
            console.log(inputElement);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-banner-download-" + languageFlag);
                console.log(imageElement);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileBanner = inputElement.files[0];
                inputElement.value = "";
            }
        }
        render();
        console.log(type + ' : ' + index);
    }
}

function createGallery(array) {
    for (let i = 0; i < 5; i++) {
        array[i] = {
            id: 0,
            name: null,
            type: null,
            link: null,
            file: null,
            pathToImage: function () {
                return "/uploads/films/" + this.id + "/" + this.name;
            }
        };
    }
}

function render() {
    if (languageFlag === 'ukr') {
        galleriesElementUkr.innerHTML = '';
        for (let i = 0; i < array.length; i++) {
            galleriesElementUkr.insertAdjacentHTML('beforeend', getBlock(array[i], i));
        }
    } else if (languageFlag === 'eng') {
        galleriesElementEng.innerHTML = '';
        for (let i = 0; i < array.length; i++) {
            galleriesElementEng.insertAdjacentHTML('beforeend', getBlock(array[i], i));
        }
    }
};

function getBlock(object, index) {
    const blockId = `image-download-${index}-${languageFlag}`;
    let nameLabel = languageFlag === 'ukr' ? 'Добавити' : 'Add';
    let linkOnImage;
    if (object.link === null) {
        if (object.name !== null) {
            linkOnImage = object.pathToImage;
        } else {
            linkOnImage = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    } else {
        linkOnImage = object.link;
    }
    return `<div class="col-2 justify-content-center">
        <div class="position-relative d-flex justify-content-center">
        <img id="${blockId}" src="${linkOnImage}"
                                  style="width: 200px;height: 200px;text-align: center">
                                  <button   
                                  type="button" 
                                  class="small-circle position-absolute"
                                  data-index="${index}" 
                                  data-type="galleries-delete-${languageFlag}">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file-${index}-${languageFlag}" data-index="${index}" 
                                  data-type="galleries-download-${languageFlag}">${nameLabel}</label>
                                  <input type="file" accept="image/jpeg, image/png" id="input-file-${index}-${languageFlag}" style="display: none"
                                  name="galleries"/>
                              </div>
                     </div>`
};
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

