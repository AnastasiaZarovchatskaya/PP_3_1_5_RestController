async function fillEditedUserPage(id) {
    const url = "api/admin/" + id
    let page = await fetch(url);

    if (page.ok) {
        let user = await page.json();
        const action = "edit";
        showEditedUser(user,action);
    } else {
        alert(`Error, ${page.status}`)
    }
}
async function fillDeleteUserPage(id) {
    const url = "api/admin/" + id
    let page = await fetch(url);

    if (page.ok) {
        let user = await page.json();
        const action = "delete";
        showEditedUser(user,action);
        await userDelete();
    } else {
        alert(`Error, ${page.status}`)
    }
    console.log();
}


function showEditedUser(user,action) {

    const editIdInput = document.getElementById(action + 'Id');
    const editFirstNameInput = document.getElementById(action + 'FirstName');
    const editLastNameInput = document.getElementById(action + 'LastName');
    const editAgeInput = document.getElementById(action + 'Age');
    const editEmailInput = document.getElementById(action + 'Email');
    const editRoleSelect = document.getElementById(action + 'Role');

    editIdInput.value = user.id;
    editFirstNameInput.value = user.firstName;
    editLastNameInput.value = user.lastName;
    editAgeInput.value = user.age;
    editEmailInput.value = user.username;
    editRoleSelect.innerHTML = '';

    // Добавляем опции для ROLE_USER и ROLE_ADMIN

    const adminOption = document.createElement('option');
    adminOption.value = 'ROLE_ADMIN';
    adminOption.textContent = 'ADMIN';
    editRoleSelect.appendChild(adminOption);

    const userOption = document.createElement('option');
    userOption.value = 'ROLE_USER';
    userOption.textContent = 'USER';
    editRoleSelect.appendChild(userOption);

    user.roles.forEach(role => {
        if (role.authority === 'ROLE_USER') {
            userOption.selected = true;
        }
        if (role.authority === 'ROLE_ADMIN') {
            adminOption.selected = true;
        }
    });
}