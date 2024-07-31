const galleriesElementUkr = document.getElementById("galleries-ukr"),
    galleriesElementEng = document.getElementById("galleries-eng");

galleriesElementUkr.onclick = fileHandle;
galleriesElementEng.onclick = fileHandle;

const elements = [
    document.getElementById("image1-ukr"),
    document.getElementById("image2-ukr"),
    document.getElementById("image3-ukr"),
    document.getElementById("image1-eng"),
    document.getElementById("image2-eng"),
    document.getElementById("image3-eng"),
    document.getElementById("image-banner-ukr"),
    document.getElementById("image-banner-eng")
];
elements.forEach(e => {
    e.onclick = fileHandle;
})

let languageFlag, nameBanner,nameImage1,nameImage2,nameImage3, urlCeo;
let array = new Array(5);
let fileImage1 = null;
let fileImage2 = null;
let fileImage3 = null;
let fileBanner = null;
let status = false;
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
    } else if (languageCode === 'eng') {
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;
    document.getElementById(`statusSwitch`).checked = status;
    if (nameImage1 !== undefined && nameImage1 !== null) {
        document.getElementById(`image1-download-${languageCode}`).src = nameImage1;
    } else {
        if (fileImage1 !== undefined && fileImage1 !== null) {
            document.getElementById(`image1-download-${languageCode}`).src = URL.createObjectURL(fileImage1);
        } else {
            document.getElementById(`image1-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }

    if (nameImage2 !== undefined && nameImage2 !== null) {
        document.getElementById(`image2-download-${languageCode}`).src = nameImage2;
    } else {
        if (fileImage2 !== undefined && fileImage2 !== null) {
            document.getElementById(`image2-download-${languageCode}`).src = URL.createObjectURL(fileImage2);
        } else {
            document.getElementById(`image2-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }

    if (nameImage3 !== undefined && nameImage3 !== null) {
        document.getElementById(`image3-download-${languageCode}`).src = nameImage3;
    } else {
        if (fileImage3 !== undefined && fileImage3 !== null) {
            document.getElementById(`image3-download-${languageCode}`).src = URL.createObjectURL(fileImage3);
        } else {
            document.getElementById(`image3-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }

    if (nameBanner !== undefined && nameBanner !== null) {
        document.getElementById(`image-banner-download-${languageCode}`).src = nameBanner;
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

    formObject.append("status", status);
    formObject.append("urlCeo", $(`#urlCeo-${languageFlag}`).val());

    if (fileImage1 != null) {
        formObject.append("fileImage1", fileImage1);
    }
    if (fileImage2 != null) {
        formObject.append("fileImage2", fileImage2);
    }
    if (fileImage3 != null) {
        formObject.append("fileImage3", fileImage3);
    }
    if (fileBanner != null) {
        formObject.append("fileBanner", fileBanner);
    }
    for (let i = 0; i < array.length; i++) {
        if (array[i].file != null) {
            formObject.append("filesGalleries", array[i].file);
        }
    }
    for (let i = 0; i < array.length; i++) {
        let object = {
            id: array[i].id,
            name: array[i].name,
            type: array[i].type
        };
        formObject.append("galleriesDTO", JSON.stringify(object));
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
        } else if (type === "image1Delete-" + languageFlag) {
            const imageElement = document.getElementById("image1-download-" + languageFlag);
            fileImage1 = null;
            nameImage1 = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === "image1Download-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image1-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image1-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImage1 = inputElement.files[0];
                inputElement.value = "";
            }
        }
        else if (type === "image2Delete-" + languageFlag) {
            const imageElement = document.getElementById("image2-download-" + languageFlag);
            fileImage2 = null;
            nameImage2 = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === "image2Download-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image2-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image2-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImage2 = inputElement.files[0];
                inputElement.value = "";
            }
        }
        else if (type === "image3Delete-" + languageFlag) {
            const imageElement = document.getElementById("image3-download-" + languageFlag);
            fileImage3 = null;
            nameImage3 = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === "image3Download-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image3-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image3-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImage3 = inputElement.files[0];
                inputElement.value = "";
            }
        }else if (type === "bannerDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-banner-download-" + languageFlag);
            fileBanner = null;
            nameBanner = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";
        } else if (type === 'bannerDownload-' + languageFlag) {
            const inputElement = document.getElementById("btn-download-banner-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-banner-download-" + languageFlag);
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
                return "`/uploads/pages/" + this.id + "/" + this.name;
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

document.getElementById('statusSwitch').addEventListener('change', function () {
    if (this.checked) {
        status = true;
    } else {
        status = false;
    }
});