const galleriesElement = document.getElementById("galleries");
const savebtn = document.getElementById("save-btn");

let array = new Array();
window.addEventListener("DOMContentLoaded", () => {
    // const url_server = "/admin/film/data/add";
    const url_server = "/admin/film/add";
    const form = document.querySelector("form");

    function req() {
        const request = new XMLHttpRequest();
        request.open("GET", "/admin/film/data/add");
        request.setRequestHeader("Content-type", "application/json; charset=utf-8");
        request.send();
        request.addEventListener("load", function () {
            if (request.status == 200) {
                let data = JSON.parse(request.response);
                let i = 0;
                console.log(data);
                createBlock(data, i)
            }
        })


        // getResource_("/admin/film/data/add")
        //     .then(data => {
        //         createBlock(data.data, i);
        //         array = data.data;
        //         console.log(array)
        //     })
        //     .catch(err => console.error(err));


        // $.ajax({
        //     url: "/admin/film/add",
        //     type: "POST",
        //     contentType: "application/json",
        //     data: JSON.stringify({
        //         array
        //     }),
        //     success: function (response) {
        //         // do something
        //     },
        //     error: function (jqXHR, textStatus, errorThrown) {
        //         // do something
        //     }
        // });
    }

    req();
})

async function getResource_(url) {
    const res = await axios(`${url}`);
    if (res.status !== 200) {
        throw new Error(`Could not fetch ${url}, status: ${res.status}`);
    }
    return res;
}

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
                array[index].file = inputElement.files[0];
                console.log(array[index].link)
            }
            inputElement.click();
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
                                  data-type="delete">&#x2717
                                  </button>
                              </div>
                              <div class="d-flex justify-content-center mt-3">
                                  <label class="input-add" for="input-file" data-index="${index}" data-type="download">
                                  Добавити</label>
                                  <input type="file" accept="image/jpeg, image/png" id="input-file-${index}" style="display: none"
                                  name="galleries""/>
                              </div>
                     </div>`
};

savebtn.addEventListener("click", function () {
    let arrayData = [1, 2, 3, 4, 5];

    let jsonData = JSON.stringify(arrayData);

    $.ajax({
        url: '/admin/film/add',
        type: 'POST',
        contentType: 'application/json',
        data: jsonData,
        success: function(response) {
            console.log(response);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });

    window.location.replace('/admin/films');
})

// async function getResource_(url) {
//     const res = await fetch(`${url}`);
//     if (!res.ok) {
//         throw new Error(`Could not fetch ${url}, status: ${res.status}`);
//     }
//     return await res.json();
// }