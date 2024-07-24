const galleriesElementUkr = document.getElementById("galleries-ukr"),
    galleriesElementEng = document.getElementById("galleries-eng");

galleriesElementUkr.onclick = fileHandle;
galleriesElementEng.onclick = fileHandle;

const elements = [
    document.getElementById("image-schema-ukr"),
    document.getElementById("image-schema-eng"),
    document.getElementById("image-banner-ukr"),
    document.getElementById("image-banner-eng")
];
elements.forEach(e => {
    e.onclick = fileHandle;
})

let languageFlag, imageSchema, imageBanner, urlCeo, numberHall;
let array = new Array(5);
let fileSchema = null;
let fileBanner = null;

let cinemaId = document.getElementById("cinemaId");
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
        urlCeo = document.getElementById(`urlCeo-eng`).value;
        numberHall = document.getElementById(`numberHall-eng`).value;

    } else if (languageCode === 'eng') {
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
        numberHall = document.getElementById(`numberHall-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;
    document.getElementById(`numberHall-${languageCode}`).value = numberHall;

    if (imageSchema !== undefined && imageSchema !== null) {
        document.getElementById(`image-schema-download-${languageCode}`).src = imageSchema;
    } else {
        if (fileSchema !== undefined && fileSchema !== null) {
            document.getElementById(`image-schema-download-${languageCode}`).src = URL.createObjectURL(fileSchema);
        } else {
            document.getElementById(`image-schema-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
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
}

function getFormObject(language) {
    let formObject = new FormData();
    formObject.append("descriptionUkr", $("#description-ukr").val());
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());

    formObject.append("descriptionEng", $("#description-eng").val());
    formObject.append("titleCeoEng", $("#titleCeo-eng").val());
    formObject.append("keywordsCeoEng", $("#keywordsCeo-eng").val());
    formObject.append("descriptionCeoEng", $("#descriptionCeo-eng").val());

    formObject.append("urlCeo", $(`#urlCeo-${languageFlag}`).val());
    formObject.append("number", $(`#numberHall-${languageFlag}`).val());

    if (fileSchema != null) {
        formObject.append("fileSchema", fileSchema);
    }
    if (fileBanner != null) {
        formObject.append("fileBanner", fileBanner);
    }
    for (let i = 0; i < array.length; i++) {
        if (array[i].file != null) {
            formObject.append("imagesMultipart", array[i].file);
        }
    }
    formObject.append("cinemaId", $(`#cinemaId`).val());

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
        } else if (type === "schemaDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-schema-download-" + languageFlag);
            fileSchema = null;
            imageSchema = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === "schemaDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-schema-" + languageFlag);
            console.log(inputElement);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-schema-download-" + languageFlag);
                console.log(imageElement);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileSchema = inputElement.files[0];
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


