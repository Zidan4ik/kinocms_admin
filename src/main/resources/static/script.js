// const URL_TO_SERVER = 'admin/film/data/add';
let array = Array();
const galleriesElement = document.getElementById("galleries");
const btnSave = document.getElementById("btn-save");

document.addEventListener("DOMContentLoaded", function (event) {
    fetch('/admin/film/data/add')
        .then(response => response.json())
        .then(data => {
            array = data;
            console.log(array);
            render();
        })
        .catch(error => console.error('Error fetching data:', error));
});

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
                                  th:field="*{unifierFilm.galleries.file}"/>
                              </div>
                     </div>`
};

galleriesElement.onclick = function (event) {
    if (event.target.dataset.index) {
        const index = event.target.dataset.index;
        const type = event.target.dataset.type;
        if (type === 'delete') {
            array.splice(index, 1);
            render();
        } else if (type === 'download') {
            const inputField = `input-file-${index}`;
            const inputElement = document.getElementById(inputField);
            inputElement.onchange = function () {
                const imageId = `image-download-${index}`;
                const imageElement = document.getElementById(imageId);
                imageElement.src = URL.createObjectURL(inputElement.files[0]);
                array[index].link = imageElement.src;
                console.log(array[index].link)
            }
            inputElement.click();
        }

        console.log(type + ' : ' + index);
    }
}


btnSave.onclick = function () {
    let form = new FormData();
    form.append("galleries",JSON.stringify(array))
    fetch('/admin/film/add', {
        method: 'POST',
        headers: {
        // multipart/form-data
            'Content-Type': 'application/json',
        },
        body: form,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(newUserData => {
            // Process the newly created user data
            console.log('New User Data:', newUserData);
        })
        .catch(error => {
            console.error('Error:', error);
        });
};