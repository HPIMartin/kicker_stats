function createPlayer () {
    var name = document.getElementById("name").value;
    var mail = document.getElementById("mail").value;
    var password = document.getElementById("password").value;

    fetch(devCheck('/players'), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
           },
        body: JSON.stringify({
            name : name,
            mail : mail,
            password : password
        })
    }).then(response => {
        if (response.status < 300) {
            window.alert("User created!");
            document.location.href = "index.html";
        } else {
            window.alert(response.status);
        }
    });
}

function devCheck(path) {
    if(location.protocol === "file:") {
        return "http://localhost:8080" + path;
    }
    return path;
}
