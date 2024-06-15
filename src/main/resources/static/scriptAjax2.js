const galleriesElement = document.getElementById("galleries");
let array = new Array();
window.addEventListener("DOMContentLoaded", () => {
    const request = new XMLHttpRequest();
    request.open("GET", "/admin/film/data/add");
    request.setRequestHeader("Content-type", "application/json; charset=utf-8");
    request.send();
    request.addEventListener("load", function () {
        if (request.status == 200) {
            let data = JSON.parse(request.response);
            let i = 0;
            console.log(data);
            array = data;
            createBlock(data, i);
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

            // PREPARE FORM DATA
            var formData = {
                film: {
                    title: $("#titleFilm").val(),
                    description: $("#descriptionFilm").val(),
                },
                galleries: array
                // galleries : array
            }

            let formObject = new FormData();

            formObject.append("titleFilm", $("#titleFilm").val());
            formObject.append("descriptionFilm", $("#descriptionFilm").val());

            formObject.append("images", array);

            // for (let i = 0; i < array.length; i++) {
            //     let id = document.getElementById(`input-file-${i}`);
            //     let selectedFiles = id.files;
            //     for (let i = 0; i < selectedFiles.length; i++) {
            //         formObject.append("files[]", selectedFiles[i]);
            //     }
            // }

            // for (let i = 0; i < array.length; i++) {
            //     formObject.append(array[i].name,array[i]);
            // }
            // formObject.append("galleries",array);                                //1

            // for (let i = 0; i < array.length; i++) {
            //     formObject.append(`galleries`,$(`#input-file-${i}`).val())       //2
            // }

            // $(".galleries input[type='file']").each(function(index, input) {     //3 no recommend
            //     if (input.files && input.files[0]) {
            //         formObject.append("galleries", input.files[0]);
            //     }
            // });

            // array.forEach((gallery, index) => {                                  //4 data is present
            //     // formObject.append(`galleries[${index}].id`, gallery.id);
            //     // formObject.append(`galleries[${index}].path`, gallery.path);
            //     // formObject.append(`galleries[${index}].type`, gallery.type);
            //     // formObject.append(`galleries[${index}].link`, gallery.link);
            //     // if (gallery.file) {
            //     //     formObject.append(`galleries[${index}].file`, gallery.file);
            //     // }
            // });

            // array.forEach((gallery, index) => {
            //     formObject.append(`galleries[${index}].id`, gallery.id);
            //     formObject.append(`galleries[${index}].link`, gallery.link);
            //     if (gallery.file) {
            //         formObject.append(`galleries[${index}].file`, gallery.file);
            //     }
            // });

            // DO POST
            $.ajax({
                type: "post",
                // contentType: "application/json",
                // dataType: 'json',
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

        }

    });


function createBlock(response, i) {
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
                                  data-type="delete">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file" data-index="${i}" data-type="download">
                                  Добавити</label>
                                  <input type="file" accept="image/jpeg, image/png"
                                  id="input-file-${i}" style="display: none" name="galleries"/>
                              </div>`;
        document.querySelector('.galleries').appendChild(block);
        i++;
    });
}

galleriesElement.onclick = function (event) {
    if (event.target.dataset.index) {
        const index = event.target.dataset.index;
        const type = event.target.dataset.type;
        if (type === 'delete') {
            array.splice(index, 1);
        } else if (type === 'download') {
            const inputField = `input-file-${index}`;
            const inputElement = document.getElementById(inputField);
            inputElement.onchange = function () {
                const imageId = `image-download-${index}`;
                const imageElement = document.getElementById(imageId);
                array[index].link = imageElement.src = URL.createObjectURL(inputElement.files[0]);
                // array[index].link = URL.createObjectURL(inputElement.files[0]);
                // array[index].file = inputElement.files[0];
                console.log(array[index].link)
            }
            inputElement.click();
        }
        render();
        console.log(type + ' : ' + index);
    }
};

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
                                  data-type="delete">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file" data-index="${index}" data-type="download">
                                  Добавити</label>
                                  <input type="file" accept="image/jpeg, image/png" id="input-file-${index}" style="display: none"
                                  name="galleries"/>
                              </div>
                     </div>`
};