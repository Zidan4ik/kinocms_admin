const galleriesElement = document.getElementById("galleries");
const imageMainElement = document.getElementById("image-main");

let array = new Array();
let fileImageMain;


window.addEventListener("DOMContentLoaded", () => {
    const request = new XMLHttpRequest();
    request.open("GET", "/admin/film/data/add");
    request.setRequestHeader("Content-type", "application/json; charset=utf-8");
    request.send();
    request.addEventListener("load", function () {
        if (request.status == 200) {
            let data = JSON.parse(request.response);
            console.log(data);
            array = data;
            createBlock(data);
        }
    })
});
$(document).ready(
    function () {
        // SUBMIT FORM
        $("#filmForm").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            ajaxPost();
        });
        function ajaxPost() {
            let formObject = new FormData();
            formObject.append("titleFilm", $("#titleFilm").val());
            formObject.append("descriptionFilm", $("#descriptionFilm").val());
            formObject.append("dateStart", $("#dateStart").val());
            formObject.append("dateEnd", $("#dateEnd").val());
            formObject.append("marks",$("#marksFilm").val());
            formObject.append("genres",$("#genresFilm").val());
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
function createBlock(response) {
    let i = 0;
    response.forEach(item => {
        let block = document.createElement('div');
        block.classList.add('col-2', 'justify-content-center');
        block.innerHTML = `
            <div class="position-relative d-flex justify-content-center">
        <img id="${i}"
        src="${item.link != null ? item.link :
            'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg'}"
                                  style="width: 200px;height: 200px;text-align: center">
                                  <button
                                  type="button"
                                  class="small-circle position-absolute"
                                  data-index="${i}"
                                  data-type="galleries-delete">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file-${i}" data-index="${i}" data-type="galleries-download">
                                  Добавити</label>
                                  <input type="file" accept="image/jpeg, image/png"
                                  id="input-file-${i}" style="display: none" name="galleries"/>
                              </div>`;
        document.querySelector('.galleries').appendChild(block);
        i++;
    });
}
galleriesElement.onclick = fileHandle;
imageMainElement.onclick = fileHandle;
function fileHandle(event) {
    if (event.target.dataset.index) {
        const index = event.target.dataset.index;
        const type = event.target.dataset.type;
        if (type === 'galleries-delete') {
            array[index].file = null;
            array[index].link = null;
        } else if (type === 'galleries-download') {
            const inputElement = document.getElementById(`input-file-${index}`);
            inputElement.onchange = function () {
                const imageElement = document.getElementById(`image-download-${index}`);
                array[index].link = imageElement.src = URL.createObjectURL(inputElement.files[0]);
                array[index].file = inputElement.files[0];
            }
            inputElement.click();
        } else if (type === "imageDelete") {
            const imageElement = document.getElementById("image-main-download");
            fileImageMain = null;
            imageElement.src = "https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

        } else if (type === "imageDownload") {
            const inputElement = document.getElementById("btn-download-image");
            console.log(inputElement);
            inputElement.onchange = function () {
                const imageElement = document.getElementById("image-main-download");
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
function render() {
    galleriesElement.innerHTML = '';

    for (let i = 0; i < array.length; i++) {
        galleriesElement.insertAdjacentHTML('beforeend', getBlock(array[i], i));
    }
};
function getBlock(object, index) {
    const blockId = `image-download-${index}`;
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
                                  data-type="galleries-delete">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file-${index}" data-index="${index}" data-type="galleries-download">
                                  Добавити</label>
                                  <input type="file" accept="image/jpeg, image/png" id="input-file-${index}" style="display: none"
                                  name="galleries"/>
                              </div>
                     </div>`
};

const TagifyCustomInlineSuggestionEl = document.querySelector("#marksFilm");

const whitelist = [
    "3D",
    "2D",
    "IMAX",
    "DBOX",
    "18+",
    "16+",
    "12+"
];

let TagifyCustomInlineSuggestion = new Tagify(TagifyCustomInlineSuggestionEl, {
    whitelist: whitelist,
    maxTags: 3,
    dropdown: {
        maxItems: 10,
        classname: "tags-inline",
        enabled: 0,
        closeOnSelect: false
    }
});