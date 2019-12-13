function fetchPlayers () {
    fetch(devCheck('/players'))
        .then(response => response.json())
        .then(data => {
            data.players.forEach(player => {
                var option = document.createElement("option");
                option.text = player.name;
                option.value = player.email;
                var selectTeam1 = document.getElementById("selectTeam1");
                var selectTeam2 = document.getElementById("selectTeam2");
                selectTeam1.appendChild(option.cloneNode(true));
                selectTeam2.appendChild(option.cloneNode(true));
            })
        })
}

function submitScore () {
    var team1 = getSelectValues(document.getElementById("selectTeam1"));
    var team2 = getSelectValues(document.getElementById("selectTeam2"));
    var team1Score = document.getElementById("scoreTeam1").value;
    var team2Score = document.getElementById("scoreTeam2").value;
    
    window.alert(JSON.stringify({
        team1 : team1,
        team2 : team2,
        team1Score : team1Score,
        team2Score : team2Score
    }));
    /*
    fetch('endpointToSave', {
        method: 'POST',
        body: JSON.stringify({
            team1 : team1,
            team2 : team2,
            team1Score : team1Score,
            team2Score : team2Score
        })
    }).then(response => {
        window.alert(response);
        if (response.status === 200) {
            //display success
            //clear the fields
        } else {
            //display error
        }
    });
    */
}

function getSelectValues(select) {
    var result = [];
    var options = select && select.options;
    var opt;
  
    for (var i=0, iLen=options.length; i<iLen; i++) {
      opt = options[i];
  
      if (opt.selected) {
        result.push(opt.value || opt.text);
      }
    }
    return result;
}

function devCheck(path) {
    if(location.protocol === "file:") {
        return "http://localhost:8080" + path;
    }
    return path;
}
