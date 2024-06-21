const galleriesElement = document.getElementById("galleries-ukr");
const imageMainElement = document.getElementById("image-main");

const galleriesElement2 = document.getElementById("galleries-eng");
const imageMainElement2 = document.getElementById("image-main-eng");

let array = new Array(5);
let fileImageMain;
let languageFlag;

createGallery(array);

galleriesElement.onclick = fileHandle;
imageMainElement.onclick = fileHandle;

galleriesElement2.onclick = fileHandle;
imageMainElement2.onclick = fileHandle;

changeLanguage('ukr');

function changeLanguage(language) {
    languageFlag = language;
    array = new Array(5);
    createGallery(array);
    if(language === 'ukr'){
        render();
    }else if (language === 'eng'){
        render();
    }
}

$(document).ready(function () {
    // SUBMIT FORM
    $("#filmForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost() {
        let formObject = getFormObject(languageFlag);
        // DO POST
        $.ajax({
            type: "post",
            url: "add",
            data: formObject,
            processData: false,
            contentType: false,
            success: function (response) {
                console.log("Form submitted successfully");
            },
            error: function (error) {
                console.log("Error submitting form:", error);
            }
        });
        // window.location.replace("/admin/films");
    }
});

function getFormObject(language) {
    let formObject = new FormData();
    if (languageFlag === 'ukr') {
        formObject.append("titleFilm", $("#titleFilm").val());
        formObject.append("descriptionFilm", $("#descriptionFilm").val());
        formObject.append("dateStart", $("#dateStart").val());
        formObject.append("dateEnd", $("#dateEnd").val());
        formObject.append("marks", $("#marksFilm").val());
        formObject.append("genres", $("#genresFilm").val());

        if (fileImageMain != null) {
            formObject.append("fileImage", fileImageMain);
        }
        for (let i = 0; i < array.length; i++) {
            if (array[i].file != null) {
                formObject.append("imagesMultipart", array[i].file);
            }
        }

        formObject.append("linkTrailer", $("#linkTrailer").val());

        formObject.append("durationTime", $("#timeFilm").val());
        formObject.append("year", $("#yearFilm").val());
        formObject.append("budget", $("#budgetFilm").val());

        formObject.append("urlCeo", $("#urlCeo").val());
        formObject.append("titleCeo", $("#titleCeo").val());
        formObject.append("keywordsCeo", $("#keywordsCeo").val());
        formObject.append("descriptionCeo", $("#descriptionCeo").val());
    }
    else if (languageFlag === 'eng') {
        formObject.append("titleFilm", $("#titleFilm-eng").val());
        formObject.append("descriptionFilm", $("#descriptionFilm-eng").val());
        formObject.append("dateStart", $("#dateStart-eng").val());
        formObject.append("dateEnd", $("#dateEnd-eng").val());
        formObject.append("marks", $("#marksFilm-eng").val());
        formObject.append("genres", $("#genresFilm-eng").val());

        if (fileImageMain != null) {
            formObject.append("fileImage", fileImageMain);
        }
        for (let i = 0; i < array.length; i++) {
            if (array[i].file != null) {
                formObject.append("imagesMultipart", array[i].file);
            }
        }

        formObject.append("linkTrailer", $("#linkTrailer-eng").val());

        formObject.append("durationTime", $("#timeFilm-eng").val());
        formObject.append("year", $("#yearFilm-eng").val());
        formObject.append("budget", $("#budgetFilm-eng").val());
        formObject.append("urlCeo", $("#urlCeo-eng").val());
        formObject.append("titleCeo", $("#titleCeo-eng").val());
        formObject.append("keywordsCeo", $("#keywordsCeo-eng").val());
        formObject.append("descriptionCeo", $("#descriptionCeo-eng").val());
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
        } else if (type === 'galleries-download-' + languageFlag) {
            const inputElement = document.getElementById(`input-file-${index}-` + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById(`image-download-${index}-` + languageFlag);
                array[index].link = imageElement.src = URL.createObjectURL(inputElement.files[0]);
                array[index].file = inputElement.files[0];
            }
            inputElement.click();
        } else if (type === "imageDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-main-download-" + languageFlag);
            fileImageMain = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

        } else if (type === "imageDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image-" + languageFlag);
            console.log(inputElement);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-main-download-" + languageFlag);
                console.log(imageElement);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImageMain = inputElement.files[0];

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
                return "./uploads/films/" + this.id + "/" + this.name;
            }
        };
    }
}

function render() {
    if(languageFlag === 'ukr'){
        galleriesElement.innerHTML = '';

        for (let i = 0; i < array.length; i++) {
            galleriesElement.insertAdjacentHTML('beforeend', getBlockUkr(array[i], i));
        }
    }else if(languageFlag === 'eng'){
        galleriesElement2.innerHTML = '';
        for (let i = 0; i < array.length; i++) {
            galleriesElement2.insertAdjacentHTML('beforeend', getBlockUkr(array[i], i));
        }
    }
};

function getBlockUkr(object, index) {
    const blockId = `image-download-${index}-${languageFlag}`;
    let nameLabel = languageFlag === 'ukr'? 'Добавити':'Add';
    return `<div class="col-2 justify-content-center">
        <div class="position-relative d-flex justify-content-center">
        <img id="${blockId}"
        src="${object.link != null ? object.link :
        'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg'}"
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
const TagifyCustomInlineSuggestionEl_UKR = document.querySelector("#marksFilm");
const TagifyCustomInlineSuggestionEl_ENG = document.querySelector("#marksFilm-eng");
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
