const insertData = (newBody, data) => {
    let result = JSON.parse(data);
    for (let item of result) {
        let newRow = newBody.insertRow();
        // if (result.indexOf(item) >= 4 * currentPage) {
            for (let index of ['id', 'ownerid', 'name', 'description', 'subscribers']) {
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

const insertSomeData = (newBody, data) => {
    let result = JSON.parse(data);
    for (let item of result) {
        let newRow = newBody.insertRow();
        // if (result.indexOf(item) >= 4 * currentPage) {
        for (let index of ['name', 'description']) {
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
const getChannelsForName = () => {
    let ownerName = $('#ownerName').val();
    let body = $(".table tbody").eq(0);
    let newBody = document.createElement("tbody");
    $.ajax({
        type: 'GET',
        url: "http://localhost/webLabs/exam6_2020/Controller.php",
        data: {
            action: 'getForOwner',
            ownerName: ownerName
        },
        success: (data) => {
            insertData(newBody, data);
        }
    })
    body.replaceWith(newBody)
}

const subscribe = () => {
    let channelId = $('#channelId').val();
    $.ajax({
        type: "GET",
        url: 'http://localhost/webLabs/exam6_2020/Controller.php',
        data: {
            action: 'subscribeToChannel',
            channelId: channelId
        },
        success: () => {
            getSubscribedChannels();
        }
    })
}
const getSubscribedChannels = () => {
    let body = $(".table tbody").eq(1);
    let newBody = document.createElement("tbody");
    $.ajax({
        type: 'GET',
        url: 'http://localhost/webLabs/exam6_2020/Controller.php',
        data: {
            action: 'getSubscribesForUser'
        },
        success: (data) => {
            insertSomeData(newBody, data)
        }
    })
    body.replaceWith(newBody)
}
$(document).ready(() => {
    $('#getOwnerNameButton').click(() => {
        getChannelsForName()
    })
    getSubscribedChannels();
    $('#subscribe').click(() => {
        subscribe()
    })
})