const galleriesElement = document.getElementById("galleries-ukr"),
    imageMainElement = document.getElementById("image-ukr"),
    galleriesElement2 = document.getElementById("galleries-eng"),
    imageMainElement2 = document.getElementById("image-eng");

let languageFlag;
let array = new Array(5);
let genres = [];
let nameImage;
let fileImage = null;
let dateOfPublication, urlCeo, marks;
let status = null;
createGallery(array);

changeLanguage('ukr');
galleriesElement.onclick = fileHandle;
imageMainElement.onclick = fileHandle;

galleriesElement2.onclick = fileHandle;
imageMainElement2.onclick = fileHandle;

function changeLanguage(language) {
    languageFlag = language;
    getDataFromInputs(languageFlag);
    assignDataInputs(languageFlag);
    render();
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
    formObject.append("status",status);
    if (fileImage != null) {
        formObject.append("fileImage", fileImage);
    }
    for (let i = 0; i < array.length; i++) {
        if (array[i].file != null) {
            formObject.append("galleriesMF", array[i].file);
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
        } else if (type === "imageDelete-" + languageFlag) {
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
                return "/uploads/news/" + this.id + "/" + this.name;
            }
        };
    }
}

function render() {
    if (languageFlag === 'ukr') {
        galleriesElement.innerHTML = '';
        for (let i = 0; i < array.length; i++) {
            galleriesElement.insertAdjacentHTML('beforeend', getBlock(array[i], i));
        }
    } else if (languageFlag === 'eng') {
        galleriesElement2.innerHTML = '';
        for (let i = 0; i < array.length; i++) {
            galleriesElement2.insertAdjacentHTML('beforeend', getBlock(array[i], i));
        }
    }
}

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
                                  type="submit" 
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

document.getElementById('statusSwitch').addEventListener('change', function () {
    if (this.checked) {
        status = true;
    } else {
        status = false;
    }
});
