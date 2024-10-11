const galleriesElement = document.getElementById("galleries-ukr"),
    imageMainElement = document.getElementById("image-main-ukr"),
    galleriesElement2 = document.getElementById("galleries-eng"),
    imageMainElement2 = document.getElementById("image-main-eng"),
    btnBackVersionUkr = document.querySelector(".back-version-ukr"),
    btnBackVersionEng = document.querySelector(".back-version-eng");

let languageFlag;

let array = new Array(5);
let genres = [];
let mainImage;
let fileImageMain = null;
let dateStart, dateEnd, linkTrailer,
    urlCeo, marks, time, year, budget;

let dataRollBack;
createGallery(array);

function setNullInField() {
    array = new Array(5);
    genres = [];
    fileImageMain = null;
    dateStart = '';
    dateEnd = '';
    linkTrailer = '';
    urlCeo = '';
    marks = '';
    time = '';
    year = '';
    budget = '';

    const title = document.getElementById(`titleFilm-${languageFlag}`);
    title.value = '';
    const description = document.getElementById(`descriptionFilm-${languageFlag}`);
    description.value = '';
    const titleCEO = document.getElementById(`titleCeo-${languageFlag}`);
    titleCEO.value = '';
    const keywordsCEO = document.getElementById(`keywordsCeo-${languageFlag}`);
    keywordsCEO.value = '';
    const descriptionCEO = document.getElementById(`descriptionCeo-${languageFlag}`);
    descriptionCEO.value = '';
}

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
        dateStart = document.getElementById(`dateStart-eng`).value;
        dateEnd = document.getElementById(`dateEnd-eng`).value;
        marks = document.getElementById(`marksFilm-eng`).value;
        let selectedOptions = document.getElementById(`genresFilm-eng`).selectedOptions;
        genres = [];
        for (let i = 0; i < selectedOptions.length; i++) {
            genres.push(selectedOptions[i].value);
        }
        time = document.getElementById(`timeFilm-eng`).value;
        year = document.getElementById(`yearFilm-eng`).value;
        budget = document.getElementById(`budgetFilm-eng`).value;
        linkTrailer = document.getElementById(`linkTrailer-eng`).value;
        urlCeo = document.getElementById(`urlCeo-eng`).value;
    } else if (languageCode === 'eng') {
        dateStart = document.getElementById(`dateStart-ukr`).value;
        dateEnd = document.getElementById(`dateEnd-ukr`).value;
        marks = document.getElementById(`marksFilm-ukr`).value;
        let selectedOptions = document.getElementById(`genresFilm-ukr`).selectedOptions;
        genres = [];
        for (let i = 0; i < selectedOptions.length; i++) {
            genres.push(selectedOptions[i].value);
        }
        time = document.getElementById(`timeFilm-ukr`).value;
        year = document.getElementById(`yearFilm-ukr`).value;
        budget = document.getElementById(`budgetFilm-ukr`).value;
        linkTrailer = document.getElementById(`linkTrailer-ukr`).value;
        urlCeo = document.getElementById(`urlCeo-ukr`).value;
    }
}

function assignDataInputs(languageCode) {
    document.getElementById(`dateStart-${languageCode}`).value = dateStart;
    document.getElementById(`dateEnd-${languageCode}`).value = dateEnd;
    document.getElementById(`marksFilm-${languageCode}`).value = marks;
    document.getElementById(`timeFilm-${languageCode}`).value = time;
    document.getElementById(`yearFilm-${languageCode}`).value = year;
    document.getElementById(`budgetFilm-${languageCode}`).value = budget;
    document.getElementById(`linkTrailer-${languageCode}`).value = linkTrailer;
    document.getElementById(`urlCeo-${languageCode}`).value = urlCeo;

    if (mainImage !== undefined && mainImage !== null) {
        document.getElementById(`image-main-download-${languageCode}`).src = mainImage;
    } else {
        if (fileImageMain !== undefined && fileImageMain !== null) {
            document.getElementById(`image-main-download-${languageCode}`).src = URL.createObjectURL(fileImageMain);
        } else {
            document.getElementById(`image-main-download-${languageCode}`).src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
        }
    }
    $(`#genresFilm-${languageCode}`).selectpicker('val', genres);
}

function getFormObject(language) {
    let formObject = new FormData();
    formObject.append("titleFilmUkr", $("#titleFilm-ukr").val());
    formObject.append("descriptionFilmUkr", $("#descriptionFilm-ukr").val());
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());

    formObject.append("titleFilmEng", $("#titleFilm-eng").val());
    formObject.append("descriptionFilmEng", $("#descriptionFilm-eng").val());
    formObject.append("titleCeoEng", $("#titleCeo-eng").val());
    formObject.append("keywordsCeoEng", $("#keywordsCeo-eng").val());
    formObject.append("descriptionCeoEng", $("#descriptionCeo-eng").val());

    formObject.append("dateStart", $(`#dateStart-${languageFlag}`).val());
    formObject.append("dateEnd", $(`#dateEnd-${languageFlag}`).val());
    formObject.append("marks", $(`#marksFilm-${languageFlag}`).val());
    formObject.append("genres", $(`#genresFilm-${languageFlag}`).val());
    formObject.append("linkTrailer", $(`#linkTrailer-${languageFlag}`).val());
    formObject.append("durationTime", $(`#timeFilm-${languageFlag}`).val());
    formObject.append("year", $(`#yearFilm-${languageFlag}`).val());
    formObject.append("budget", $(`#budgetFilm-${languageFlag}`).val());
    formObject.append("urlCeo", $(`#urlCeo-${languageFlag}`).val());

    if (fileImageMain != null) {
        formObject.append("fileImage", fileImageMain);
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
        } else if (type === "imageDelete-" + languageFlag) {
            const imageElement = document.getElementById("image-main-download-" + languageFlag);
            fileImageMain = null;
            mainImage = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

        } else if (type === "imageDownload-" + languageFlag) {
            const inputElement = document.getElementById("btn-download-image-" + languageFlag);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-main-download-" + languageFlag);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                fileImageMain = inputElement.files[0];

                inputElement.value = "";
            }
        }
        render();
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
const TagifyCustomInlineSuggestionEl_UKR = document.querySelector("#marksFilm-ukr");
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


function rollBackForm(object) {
    dateStart = object.dateStart;
    dateEnd = object.dateEnd;
    marks = object.marks;
    genres = object.genres;
    time = object.time;
    year = object.year;
    budget = object.budget;
    linkTrailer = object.linkTrailer;
    urlCeo = object.urlCeo;
    array = object.galleries;
    mainImage = object.mainImage;
    document.getElementById(`titleFilm-ukr`).value = object.titleFilmUkr;
    document.getElementById(`titleFilm-eng`).value = object.titleFilmEng;
    document.getElementById(`descriptionFilm-ukr`).value = object.descriptionFilmUkr;
    document.getElementById(`descriptionFilm-eng`).value = object.descriptionFilmEng;
    document.getElementById(`titleCeo-eng`).value = object.titleCeoEng;
    document.getElementById(`titleCeo-ukr`).value = object.titleCeoUkr;
    document.getElementById(`keywordsCeo-ukr`).value = object.keywordsCeoUkr;
    document.getElementById(`keywordsCeo-eng`).value = object.keywordsCeoEng;
    document.getElementById(`descriptionCeo-ukr`).value = object.descriptionCeoUkr;
    document.getElementById(`descriptionCeo-eng`).value = object.descriptionCeoEng;
}
