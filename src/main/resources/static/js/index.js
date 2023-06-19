// JavaScript-Code für die Interaktion mit dem Frontend
const url = 'http://localhost:8080';

// DOM-Elemente abrufen
const createBtn = document.getElementById('createBtn');
const createSoftwareBtn = document.getElementById('createSoftwareBtn');
const createForm = document.getElementById('createForm');
const softwareTable = document.getElementById('softwareTableBody');
const teamFilter = document.getElementById('teamFilter');
const updateForm = document.getElementById('updateForm');
const approveDelete = document.getElementById('approveDelete');

const updateSoftwareId = document.getElementById('updateSoftwareId');
const updateSoftwareName = document.getElementById('updateSoftwareName');
const updateSoftwareVersion = document.getElementById('updateSoftwareVersion');
const updateTeamId = document.getElementById('updateTeamId');
const updateProjectId = document.getElementById('updateProjectId');
const updateStatus = document.getElementById('updateStatus');

const softwareId = document.getElementById('softwareId');
const softwareName = document.getElementById('softwareName');
const softwareVersion = document.getElementById('softwareVersion');
const teamId = document.getElementById('teamId');
const projectId = document.getElementById('projectId');
const status = document.getElementById('status');

const detailsSoftwareId = document.getElementById('detailsSoftwareId');
const detailsSoftwareName = document.getElementById('detailsSoftwareName');
const detailsSoftwareVersion = document.getElementById('detailsSoftwareVersion');
const detailsTeam = document.getElementById('detailsTeam');
const detailsProject = document.getElementById('detailsProject');
const detailsStatus = document.getElementById('detailsStatus');

// Event Listener hinzufügen
createBtn.addEventListener('click', handleCreateFormSubmit);
softwareTable.addEventListener('click', handleTableButtonClick);
createSoftwareBtn.addEventListener('click', openCreatePopup);
teamFilter.addEventListener('change', filterSoftwareByTeam);
updateForm.addEventListener('submit', handleUpdateFormSubmit);


document.addEventListener('DOMContentLoaded', () => {
    loadTeamsAndPopulateFilter();
    getSoftwareList('all');
});

document.getElementById('createProjectSubmitBtn').addEventListener('click', function(event) {
    event.preventDefault();
    const projectName = document.getElementById('projectName').value;
    createProject(projectName);
    $('#createProjectModal').modal('hide');
    getSoftwareList('all');
});

document.getElementById('addMemberSubmitBtn').addEventListener('click', function(event) {
    event.preventDefault();
    $('#addMemberModal').modal('hide');
});

document.getElementById('updateBtn').addEventListener('click', function(event) {
    event.preventDefault();
    handleUpdateFormSubmit(event)
    clearUpdatePopup();
    $('#updateSoftwareModal').modal('hide');
    getSoftwareList('all');
});

document.getElementById('createBtn').addEventListener('click', function(event) {
    event.preventDefault();
    handleCreateFormSubmit(event)
    clearCreatePopup();
    $('#createSoftwareModal').modal('hide');
    getSoftwareList('all');
});

document.getElementById('approveDelete').addEventListener('click', function(event) {
    deleteSoftware(approveDelete.dataset.softwareId);
    $('#approveSoftwareDeleteModal').modal('hide');
    getSoftwareList('all');
});

async function fetchTeams() {
    try {
        const response = await fetch(url + '/team');
        const data = await response.json();
        let newData = [];
        data.forEach((team) => {
            if (!newData.find((t) => t.teamId === team.teamId)) {
                newData.push(team);
            }
        });
        return newData;
    } catch (error) {
        console.error('Fehler beim Abrufen der Teams:', error);
        return [];
    }
}

async function fetchProjects() {
    try {
        const response = await fetch(url + '/project');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Fehler beim Abrufen der Projects:', error);
        return [];
    }
}

function populateTeamFilter(teams) {
    const teamFilter = document.getElementById('teamFilter');
    teamFilter.innerHTML = '';
    const allOption = document.createElement('option');
    allOption.value = 'all';
    allOption.textContent = 'Alle';
    teamFilter.appendChild(allOption);

    teams.forEach((team) => {
        const option = document.createElement('option');
        option.value = team.teamId;
        option.textContent = team.teamName;
        teamFilter.appendChild(option);
    });
}

function populateUpdateTeam(teams) {
    const teamSelect = document.getElementById('updateTeamId');
    teamSelect.innerHTML = '';

    teams.forEach((team) => {
        const option = document.createElement('option');
        option.value = team.teamId;
        option.textContent = team.teamName;
        teamSelect.appendChild(option);
    });
}

function populateUpdateProject(projects) {
    const projectSelect = document.getElementById('updateProjectId');
    projectSelect.innerHTML = '';
    // Optionen für jedes Team hinzufügen
    projects.forEach((project) => {
        const option = document.createElement('option');
        option.value = project.projectId;
        option.textContent = project.projectName;
        projectSelect.appendChild(option);
    });
}

async function loadTeamsAndPopulateFilter() {
    const teams = await fetchTeams();
    populateTeamFilter(teams);
}

async function loadTeamsAndPopulateProjectUpdateSelcet() {
    const projects = await fetchProjects();
    populateUpdateProject(projects);
}

async function loadTeamsAndPopulateTeamUpdateSelcet() {
    const teams = await fetchTeams();
    populateUpdateTeam(teams);
}

function populateCreateTeam(teams) {
    const teamSelect = document.getElementById('teamId');
    teamSelect.innerHTML = '';

    teams.forEach((team) => {
        const option = document.createElement('option');
        option.value = team.teamId;
        option.textContent = team.teamName;
        teamSelect.appendChild(option);
    });
}

function populateCreateProject(projects) {
    const projectSelect = document.getElementById('projectId');
    projectSelect.innerHTML = '';
    projects.forEach((project) => {
        const option = document.createElement('option');
        option.value = project.projectId;
        option.textContent = project.projectName;
        projectSelect.appendChild(option);
    });
}


async function loadTeamsAndPopulateProjectCreateSelcet() {
    const projects = await fetchProjects();
    populateCreateProject(projects);
}

async function loadTeamsAndPopulateTeamCreateSelcet() {
    const teams = await fetchTeams();
    populateCreateTeam(teams);
}



function openCreatePopup(event) {
    event.preventDefault();
    loadTeamsAndPopulateTeamCreateSelcet();
    loadTeamsAndPopulateProjectCreateSelcet();
}

function clearCreatePopup() {
    softwareId.value = '';
    softwareName.value = '';
    softwareVersion.value = '';
}

function handleCreateFormSubmit(event) {
    event.preventDefault();
    const formData = {
        softwareId: createForm.elements['softwareId'].value,
        softwareName: createForm.elements['softwareName'].value,
        softwareVersion: createForm.elements['softwareVersion'].value,
        teamId: createForm.elements['teamId'].value,
        projectId: createForm.elements['projectId'].value,
        status: createForm.elements['status'].value
    };

    createSoftware(formData);
}

function handleTableButtonClick(event) {
    if (event.target.classList.contains('view-button')) {
        let softwareId = event.target.dataset.softwareId;
        getSoftwareDetails(softwareId);
    } else if (event.target.classList.contains('update-button')) {
        openUpdatePopup(event);
    } else if (event.target.classList.contains('delete-button')) {
        approveDelete.dataset.softwareId = event.target.dataset.softwareId;
    } else if (event.target.classList.contains('fa-gear')) {
        openUpdatePopupIconClick(event);
    }
}

function filterSoftwareByTeam(event) {
    const selectedTeamId = teamFilter.value;
    getSoftwareList(selectedTeamId);
}

function openUpdatePopup(event) {
    console.log("Update");
    let element = event.target.parentElement.parentElement
    updateSoftwareId.value = element.children[1].textContent;
    updateSoftwareName.value = element.children[2].textContent;
    updateSoftwareVersion.value = element.children[3].textContent;
    loadTeamsAndPopulateProjectUpdateSelcet();
    loadTeamsAndPopulateTeamUpdateSelcet();
}
function openUpdatePopupIconClick(event) {
    let element = event.target.parentElement.parentElement.parentElement
    updateSoftwareId.value = element.children[1].textContent;
    updateSoftwareName.value = element.children[2].textContent;
    updateSoftwareVersion.value = element.children[3].textContent;
    loadTeamsAndPopulateProjectUpdateSelcet();
    loadTeamsAndPopulateTeamUpdateSelcet();
}

function clearUpdatePopup() {
    updateSoftwareId.value = '';
    updateSoftwareName.value = '';
    updateSoftwareVersion.value = '';
}

function handleUpdateFormSubmit(event) {
    event.preventDefault();
    const formData = {
        softwareId: updateForm.elements['updateSoftwareId'].value,
        softwareName: updateForm.elements['updateSoftwareName'].value,
        softwareVersion: updateForm.elements['updateSoftwareVersion'].value,
        teamId: updateForm.elements['updateTeamId'].value,
        projectId: updateForm.elements['updateProjectId'].value,
        status: updateForm.elements['updateStatus'].value
    };

    updateSoftware(formData);
}

function getSoftwareList(teamId) {
    fetch(url + `/software`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            console.log(teamId);
            if (teamId !== 'all') {
                let results = [];
                for (let i = 0; i < data.length; i++) {
                    console.log(data[i]);
                    if (data[i].team.teamId.toString() === teamId.toString()){
                        results.push(data[i]);
                    }
                }
                console.log(results);
                displaySoftwareList(results);
            } else {
                displaySoftwareList(data);
            }
        })
        .catch(error => {
            console.error('Fehler beim Abrufen der Softwareliste:', error);
        });
}

function createSoftware(formData) {
    fetch(url + '/software', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(function (response) {
            getSoftwareList('all')
        })
        .catch(error => {
            console.error('Fehler beim Erstellen des Softwareobjekts:', error);
        });

}

function getSoftwareDetails(softwareId) {
    fetch(url + `/software/admin/${softwareId}`)
        .then(response => response.json())
        .then(data => {
            displaySoftwareDetails(data);
        })
        .catch(error => {
            console.error('Fehler beim Abrufen der Softwaredetails:', error);
        });
}

// Funktion zum Aktualisieren eines Softwareobjekts
function updateSoftware(formData) {
    // HTTP-Anfrage an den Server senden und Softwareobjekt aktualisieren
    console.log(formData);
    fetch(url + `/software/admin/${formData.softwareId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(function (response) {
            // Erfolgreich aktualisiertes Softwareobjekt anzeigen
            getSoftwareList('all')
        })
        .catch(error => {
            console.error('Fehler beim Aktualisieren des Softwareobjekts:', error);
        });

}

// Funktion zum Löschen eines Softwareobjekts
function deleteSoftware(softwareId) {
    // HTTP-Anfrage an den Server senden und Softwareobjekt löschen
    fetch(url + `/software/admin/${softwareId}`, {
        method: 'DELETE'
    })
        .then(() => {
            // Softwareobjekt erfolgreich gelöscht
            getSoftwareList("all");
        })
        .catch(error => {
            console.error('Fehler beim Löschen des Softwareobjekts:', error);
        });
}

// Funktionen zum Anzeigen der Daten im Frontend

// Funktion zum Anzeigen der Softwareliste in der Tabelle
function displaySoftwareList(softwareList) {
    // Tabelle leeren
    softwareTable.innerHTML = '';
    // Softwareobjekte in die Tabelle einfügen
    softwareList.forEach(software => {
        const row = document.createElement('tr');

        const viewButtonCell = document.createElement('td');
        const viewButton = document.createElement('button');
        viewButton.innerHTML = '<i class="fa fa-folder"></i> View';
        viewButton.classList.add('view-button');
        viewButton.classList.add('btn');
        viewButton.dataset.toggle = 'modal';
        viewButton.dataset.target = '#detailSoftwareModal';
        viewButton.dataset.softwareId = software.softwareId;
        viewButtonCell.appendChild(viewButton);
        row.appendChild(viewButtonCell);


        const softwareIdCell = document.createElement('td');
        softwareIdCell.textContent = software.softwareId;
        row.appendChild(softwareIdCell);

        const softwareNameCell = document.createElement('td');
        softwareNameCell.textContent = software.softwareName;
        row.appendChild(softwareNameCell);

        const softwareVersionCell = document.createElement('td');
        softwareVersionCell.textContent = software.softwareVersion;
        row.appendChild(softwareVersionCell);

        const teamIdCell = document.createElement('td');
        teamIdCell.textContent = software.team.teamName;
        teamIdCell.dataset.teamId = software.team.teamId;
        row.appendChild(teamIdCell);

        const projectIdCell = document.createElement('td');
        projectIdCell.textContent = software.project.projectName;
        projectIdCell.dataset.projectId = software.project.projectId;
        row.appendChild(projectIdCell);

        const statusCell = document.createElement('td');
        statusCell.textContent = software.status;
        row.appendChild(statusCell);


        const updateButtonCell = document.createElement('td');
        const updateButton = document.createElement('button');
        updateButton.innerHTML = '<i class="fa fa-gear"></i>';
        updateButton.classList.add('update-button');
        updateButton.classList.add('btn');
        updateButton.dataset.toggle = 'modal';
        updateButton.dataset.target = '#updateSoftwareModal';
        updateButton.dataset.softwareId = software.softwareId;
        updateButtonCell.appendChild(updateButton);
        row.appendChild(updateButtonCell);

        const deleteButtonCell = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.innerHTML = '<i class="fa fa-trash"></i>';
        deleteButton.classList.add('delete-button');
        deleteButton.classList.add('btn');
        deleteButton.dataset.toggle = 'modal';
        deleteButton.dataset.target = '#approveSoftwareDeleteModal';
        deleteButton.dataset.softwareId = software.softwareId;
        deleteButtonCell.appendChild(deleteButton);
        row.appendChild(deleteButtonCell);

        softwareTable.appendChild(row);
    });
}

// Funktion zum Anzeigen der Softwaredetails im Popup
function displaySoftwareDetails(software) {
    // Details im Popup anzeigen
    detailsSoftwareId.textContent = software.softwareId;
    detailsSoftwareName.textContent = software.softwareName;
    detailsSoftwareVersion.textContent = software.softwareVersion;
    detailsProject.textContent = software.project.projectName;
    detailsTeam.textContent = software.team.teamName;
    detailsStatus.textContent = software.status;
    detailsPopup.style.display = 'block';
}
// Funktion zum Erstellen eines neuen Projekts
function createProject(projectName) {
    let formData = {
        projectName: projectName
    };
    fetch(url + '/project', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .catch(error => {
            console.error('Fehler beim Erstellen des Projectobjects:', error);
        });
    console.log('Neues Projekt erstellt:', projectName);
    loadTeamsAndPopulateProjectCreateSelcet()
}


function createTeam(teamName, budget, teamMembers) {
    // API-Aufruf, um das Team zu erstellen
    const teamData = {
        teamName: teamName,
        budget: budget
    };

    fetch(url + '/team', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(teamData)
    })
        .then(response => response.json())
        .then(team => {
            console.log('Neues Team erstellt:', team);
            // Jedes Teammitglied einzeln per API speichern und mit der Team-ID verknüpfen
            teamMembers.forEach(teamMember => {
                const memberData = {
                    name: teamMember.name,
                    firstname: teamMember.firstName,
                    joinDate: new Date().toISOString(),
                    teamId: team.toString()
                };
                console.log(memberData);
                fetch(url + '/teamMember', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(memberData)
                })
                    .then(response => response.json())
                    .then(member => {
                        console.log('Teammitglied erstellt:', member);
                    })
                    .catch(error => {
                        console.error('Fehler beim Erstellen des Teammitglieds:', error);
                    });
            });
        })
        .then(() => {
            loadTeamsAndPopulateFilter();
            loadTeamsAndPopulateTeamCreateSelcet();
        })
        .catch(error => {
            console.error('Fehler beim Erstellen des Teams:', error);
        });
}

// Event-Handler für das Absenden des Create Team Formulars
document.getElementById('createTeamSubmitBtn').addEventListener('click', function(event) {
    event.preventDefault();
    const teamName = document.getElementById('teamName').value;
    const budget = document.getElementById('teamBudget').value;
    const teamMembersList = document.getElementById('teamMembersList');
    const teamMembers = Array.from(teamMembersList.children).map(member => {
        const name = member.getAttribute('data-member-name');
        const firstName = member.getAttribute('data-member-firstname');
        return { name, firstName };
    });
    console.log(teamMembers);
    createTeam(teamName, budget, teamMembers);
    $('#createTeamModal').modal('hide');
});

// Event-Handler für das Öffnen des Add Member Modals
$('#addMemberModal').on('show.bs.modal', function() {
    document.getElementById('addMemberForm').reset();
});

// Event-Handler für das Schließen des Add Member Modals
$('#addMemberModal').on('hidden.bs.modal', function() {
    const memberName = document.getElementById('memberName').value;
    const memberFirstName = document.getElementById('memberFirstName').value;
    if (memberName && memberFirstName) {
        const teamMembersList = document.getElementById('teamMembersList');
        const listItem = document.createElement('li');
        listItem.className = 'list-group-item';
        listItem.setAttribute('data-member-name', memberName);
        listItem.setAttribute('data-member-firstname', memberFirstName);
        listItem.innerText = `${memberFirstName} ${memberName}`;
        teamMembersList.appendChild(listItem);
    }
});