const insertData = (newBody, data) => {
    let result = JSON.parse(data);
    for (let item of result) {
        let newRow = newBody.insertRow();
        // if (result.indexOf(item) >= 4 * currentPage) {
        for (let index of ['id', 'URL', 'numberOfDocuments']) {
            let newCol = newRow.insertCell();
            let newText = document.createTextNode(item[index]);
            newCol.appendChild(newText);
        }
        newBody.append(newRow);
        // }
        // if (result.indexOf(item) >= 4 * currentPage + 3) {
        //     break;
        // }
    }
}

const insertDocData = (newBody, data) => {
    let result = JSON.parse(data);
    for (let item of result) {
        let newRow = newBody.insertRow();
        // if (result.indexOf(item) >= 4 * currentPage) {
        for (let index of ['id', 'idWebSite', 'name', "keyword1", "keyword2", "keyword3", "keyword4", "keyword5"]) {
            let newCol = newRow.insertCell();
            let newText = document.createTextNode(item[index]);
            newCol.appendChild(newText);
        }
        newBody.append(newRow);
        // }
        // if (result.indexOf(item) >= 4 * currentPage + 3) {
        //     break;
        // }
    }
}
//
// const getChannelsForName = () => {
//     let ownerName = $('#ownerName').val();
//     let body = $(".table tbody").eq(0);
//     let newBody = document.createElement("tbody");
//     $.ajax({
//         type: 'GET',
//         url: "http://localhost/Ex6_2020/Controller.php",
//         data: {
//             action: 'getForOwner',
//             ownerName: ownerName
//         },
//         success: (data) => {
//             insertData(newBody, data);
//         }
//     })
//     body.replaceWith(newBody)
// }

const getAllWebsites = () => {
    let body = $(".table tbody").eq(0);
    let newBody = document.createElement("tbody");
    $.ajax({
        type: 'GET',
        url: 'http://localhost/webLabs/exam7_2020/Controller.php',
        data: {
            action: 'getAllWebsites'
        },
        success: (data) => {
            insertData(newBody, data);
        }
    })
    body.replaceWith(newBody);
}


const getWebsitesWith3Keywords = () => {
    let body = $(".table tbody").eq(1);
    let newBody = document.createElement("tbody");
    let keywords = $("#keywords").val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost/webLabs/exam7_2020/Controller.php',
        data: {
            action: 'getWith3Keys',
            keywords
        },
        success: (data) => {
            insertDocData(newBody, data);
        }
    })
    body.replaceWith(newBody);
}
const updateDoc = () => {
    let id = $("#documentId").val();
    let k1 = $("#k1").val();
    let k2 = $("#k2").val();
    let k3 = $("#k3").val();
    let k4 = $("#k4").val();
    let k5 = $("#k5").val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost/webLabs/exam7_2020/Controller.php',
        data: {
            action: 'update',
            id,
            k1,
            k2,
            k3,
            k4,
            k5
        }
    })

}
$(document).ready(() => {
    getAllWebsites();
    $("#setKey").click(() => {
        updateDoc();
    })

    $("#getWithKey").click(() => {
        getWebsitesWith3Keywords();
    } )
})