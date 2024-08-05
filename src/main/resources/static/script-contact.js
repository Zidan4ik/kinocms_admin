let contactsTexts = [];
let contactsImages = [];
let array = [];

$(document).ready(function () {
    $('#btnContact').click(function () {
        generateContact();
    });
    document.querySelector(".contactBlocks").addEventListener("change", function () {
        if (event.target && event.target.classList.contains("switch-input")) {
            const checkbox = event.target;
            const dataIndex = checkbox.getAttribute("data-index");
            if (dataIndex) {
                const block = document.getElementById("row" + dataIndex);
                block.remove();
            }
        }
    });
    document.querySelector(".contactBlocks").addEventListener("click", function () {
        if (event.target && event.target.classList.contains("input-add")) {
            const index = event.target.dataset.index;
            const type = event.target.dataset.type;
            if (type === "logoDownload-ukr") {
                const inputElement = document.getElementById("btn-download-logo-ukr-" + index);
                inputElement.value = '';
                inputElement.onchange = function () {
                    const imgElement = document.getElementById("logo-download-ukr-" + index);
                    imgElement.src = URL.createObjectURL(inputElement.files[0]);
                };
            }
            if (type === "logoDelete-ukr") {
                const imgElement = document.getElementById("logo-download-ukr-" + index);
                const inputElement = document.getElementById("btn-download-logo-ukr-" + index);
                imgElement.src = 'https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg';
                inputElement.value = '';
            }
            console.log(type + ' : ' + index);
        }
    });
});

function render(array) {
    if (array != null && array.length !== 0) {
        array.forEach(function (object) {
            generateContact(object.idContact, object);
            console.log(object);
        });
    }
}

let index = 0;

function generateContact(idContact = null, object = null) {
    $('.contactBlocks').append(`
        <div id="row` + index + `" data-id="` + idContact + `"
        style="border: solid 1px black; border-radius: 1%;" data-id="` + idContact + `">
                                            <div class="row mb-3">
                                                <div class="col">
                                                    <label class="form-label" for="title-ukr-` + index + `">Назва кінотеатру:</label>
                                                    <input class="form-control js-input" id="title-ukr-` + index + `"
                                                           style="width: 40%;margin-left: 40px">
                                                </div>
                                                 <div class="col-sm-2 d-flex align-items-center">
                                                    <label class="switch">
                                                            <span class="switch-label"></span>
                                                            <input type="checkbox" class="switch-input" id="remove_row` + index + `" data-index="` + index + `" checked/>
                                                            <span class="switch-toggle-slider">
                                                                <span class="switch-on"></span>
                                                                <span class="switch-off"></span>
                                                            </span>
                                                    </label>
                                                </div>
                                            </div>

                                            <div class="mb-3">
                                                <label class="form-label" for="address-ukr-` + index + `">Адреса:</label>
                                                <textarea class="form-control" rows="3" id="address-ukr-` + index + `"
                                                          style="width:85%; height: 250px;margin-left: 40px"
                                                          placeholder="Кінотеатр 'Золотий Дюк'\\nОдеса проспект Академія Глушко, 11ж"></textarea>
                                            </div>

                                            <div class="row mb-3">
                                                <div class="col-4">
                                                    <label class="form-label" for="coordinates-ukr-` + index + `">Координати:</label>
                                                    <input class="form-control js-input" id="coordinates-ukr-` + index + `"
                                                           placeholder="координати"
                                                           style="width: 85%;margin-left: 40px">
                                                </div>
                                            </div>

                                            <div class="row" id="imageLogo">
                                                <div class="col-6">
                                                    <div class="row">
                                                        <div class="col-3 d-flex align-items-center">
                                                            <label class="form-label">Логотип:</label>
                                                        </div>
                                                        <div class="col image-container">
                                                            <img id="logo-download-ukr-` + index + `"
                                                                 src="https://cdn.vectorstock.com/i/500p/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg"
                                                                 style="height: 150px;width: 300px;">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col d-flex flex-column align-items-center justify-content-center">
                                                    <div class="d-flex gap-5">
                                                        <label class="input-add" for="btn-download-logo-ukr-` + index + `"
                                                               data-index="` + index + `" data-type="logoDownload-ukr">
                                                            Добавити</label>
                                                        <input  id="btn-download-logo-ukr-` + index + `" data-index="` + index + `"
                                                               type="file" style="display: none">

                                                        <btn class="input-add"
                                                             data-index="` + index + `" data-type="logoDelete-ukr">
                                                            Видалити
                                                        </btn>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                      </div>`);
    if (object) {
        console.log("Object server: ", object);
        console.log("index: ", index)
        $("#row" + index).find("#title-ukr-" + index).val(object.titleUkr);
        $("#row" + index).find("#address-ukr-" + index).val(object.addressUkr);
        $("#row" + index).find("#coordinates-ukr-" + index).val(object.coordinatesUkr);
        $("#logo-download-ukr-" + index).attr("src", object.pathLogo);
    }
    index++;
}

saveContacts = function () {
    contactsTexts = [];
    contactsImages = [];
    let index = 0;
    $("#contactBlocksId > div").each(function () {
        let title, address, coordinates, fileImage;
        $(this).find("input, textarea").each(function () {
            if ($(this).attr("id") === "title-ukr-" + index) {
                title = $(this).val();
            } else if ($(this).attr("id") === "btn-download-logo-ukr-" + $(this).attr("data-index")) {
                fileImage = document.getElementById("btn-download-logo-ukr-" + $(this).attr("data-index")).files[0];
            } else if ($(this).attr("id") === "address-ukr-"+index) {
                address = $(this).val();
            } else if ($(this).attr("id") === "coordinates-ukr-"+index) {
                coordinates = $(this).val();
            }
        });
        let idContact = $(this).attr("data-id");
        console.log(idContact);
        let text = {
            idContact: idContact,
            title: title,
            address: address,
            coordinates: coordinates
        }
        let image = {
            file: fileImage
        }
        contactsTexts.push(text);
        contactsImages.push(image);
    });
};

function getFormObject() {
    saveContacts();
    let formObject = new FormData();
    formObject.append("titleCeoUkr", $("#titleCeo-ukr").val());
    formObject.append("keywordsCeoUkr", $("#keywordsCeo-ukr").val());
    formObject.append("descriptionCeoUkr", $("#descriptionCeo-ukr").val());
    formObject.append("urlCeo", $("#urlCeo-ukr").val());
    console.log("Length: " + contactsTexts.length)
    if (contactsTexts.length === 1) {
        console.log("+")
        let objectNull = {idContact: null, titleUkr: null, addressUkr: null, coordinatesUkr: null};
        let emptyFile = new File([], null, {
            type: "text/plain",
        });
        formObject.append("contactsTexts", JSON.stringify(objectNull));
        formObject.append("contactsFiles", emptyFile);
    }
    for (let i = 0; i < contactsTexts.length; i++) {
        let object = {
            idContact: contactsTexts[i].idContact,
            titleUkr: contactsTexts[i].title,
            addressUkr: contactsTexts[i].address,
            coordinatesUkr: contactsTexts[i].coordinates
        };
        formObject.append("contactsTexts", JSON.stringify(object));
    }

    for (let i = 0; i < contactsImages.length; i++) {
        if (contactsImages[i].file != null) {
            formObject.append("contactsFiles", contactsImages[i].file);
        }
    }
    return formObject;
}