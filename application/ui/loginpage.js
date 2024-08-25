
document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const psid = document.getElementById('psid').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    
  

    const user = db.users.find(user => user.psid === psid && user.password === password);

    if (user) {
        errorMessage.textContent = '';
        switch (user.role) {
            case 'admin':
                window.location.href = 'admin.html';
                break;
            case 'manager':
                window.location.href = 'manager.html';
                break;
            case 'employee':
                window.location.href = 'employee.html';
                break;
        }
    } else {
        errorMessage.textContent = 'Invalid PSID or Password';
    }
});
