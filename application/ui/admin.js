// Function to load All Users content
function loadAllUsers() {
    let content = `<h3>All Users</h3>
                   <input class="form-control mb-3" id="userSearch" type="text" placeholder="Search Users...">
                   <div class="mb-3">
                       <input type="file" id="importFile" style="display: none;" onchange="importUsers(event)">
                       <button class="btn btn-secondary mr-2" onclick="document.getElementById('importFile').click()">Import Users</button>
                       <button class="btn btn-primary" onclick="addUser()">Add User</button>
                   </div>
                   <ul class="list-group" id="userList">`;

                   db.users.forEach(user => {
                    content += `
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-md-3"><strong>Name:</strong> ${user.name}</div>
                                <div class="col-md-2"><strong>Role:</strong> ${user.role.charAt(0).toUpperCase() + user.role.slice(1)}</div>
                                <div class="col-md-2"><strong>Credits:</strong> ${user.credits}</div>
                                <div class="col-md-3"><strong>Email:</strong> ${user.email}</div>
                                <div class="col-md-2"><strong>Phone:</strong> ${user.phone}</div>
                            </div>
                        </li>
                    `;
                });

    content += '</ul>';
    document.getElementById('content').innerHTML = content;

    // Search functionality for users
    document.getElementById('userSearch').addEventListener('input', function () {
        const query = this.value.toLowerCase();
        const filteredUsers = db.users.filter(user => user.name.toLowerCase().includes(query));
        renderUserList(filteredUsers);
    });
}

// Function to render user list based on search
function renderUserList(filteredUsers) {
    let listContent = '';
    filteredUsers.forEach(user => {
        listContent += `<li class="list-group-item">${user.name} - ${user.role.charAt(0).toUpperCase() + user.role.slice(1)}</li>`;
    });
    document.getElementById('userList').innerHTML = listContent;
}

// Function to load All Meeting Rooms content
function loadAllRooms() {
    let content = `<h3>All Meeting Rooms</h3>
                   <input class="form-control mb-3" id="roomSearch" type="text" placeholder="Search Meeting Rooms...">
                   <div class="mb-3">
                       <button class="btn btn-primary" onclick="showAddRoomForm()">Add Meeting Room</button>
                   </div>
                   <div class="row" id="roomList">`;

    db.rooms.forEach(room => {
        content += `<div class="col-md-4">
                        <div class="card" onclick="viewRoomDetails(${room.id})" style="cursor: pointer;">
                            <div class="card-body">
                                <h5 class="card-title">${room.name}</h5>
                                <p class="card-text">Bookings: ${room.bookings.length}</p>
                                <p class="card-text">Status: ${room.bookings.length > 0 ? 'Booked' : 'Available'}</p>
                            </div>
                        </div>
                    </div>`;
    });

    content += '</div>';
    document.getElementById('content').innerHTML = content;

    // Search functionality for rooms
    document.getElementById('roomSearch').addEventListener('input', function () {
        const query = this.value.toLowerCase();
        const filteredRooms = db.rooms.filter(room => room.name.toLowerCase().includes(query));
        renderRoomList(filteredRooms);
    });
}
function showAddRoomForm() {
    let content = `<h3>Add New Meeting Room</h3>
                   <form id="addRoomForm">
                       <div class="form-group">
                           <label for="roomName">Room Name:</label>
                           <input type="text" class="form-control" id="roomName" required>
                       </div>
                       <div class="form-group">
                           <label>Amenities:</label>
                           <div class="form-row">
                               <div class="col">
                                   <label for="projector">Projector:</label>
                                   <input type="number" class="form-control" id="projector" value="0" min="0" onchange="updateCost()">
                               </div>
                               <div class="col">
                                   <label for="wifi">WiFi:</label>
                                   <input type="number" class="form-control" id="wifi" value="0" min="0" onchange="updateCost()">
                               </div>
                               <div class="col">
                                   <label for="seating">Seating:</label>
                                   <input type="number" class="form-control" id="seating" value="0" min="0" onchange="updateCost()">
                               </div>
                           </div>
                           <div class="form-row mt-2">
                               <div class="col">
                                   <label for="whiteboard">Whiteboard:</label>
                                   <input type="number" class="form-control" id="whiteboard" value="0" min="0" onchange="updateCost()">
                               </div>
                               <div class="col">
                                   <label for="coffeeMachine">Coffee Machine:</label>
                                   <input type="number" class="form-control" id="coffeeMachine" value="0" min="0" onchange="updateCost()">
                               </div>
                               <div class="col">
                                   <label for="waterDispenser">Water Dispenser:</label>
                                   <input type="number" class="form-control" id="waterDispenser" value="0" min="0" onchange="updateCost()">
                               </div>
                               <div class="col">
                                   <label for="tv">TV:</label>
                                   <input type="number" class="form-control" id="tv" value="0" min="0" onchange="updateCost()">
                               </div>
                           </div>
                       </div>
                       <div class="form-group">
                           <label for="cost">Cost:</label>
                           <input type="text" class="form-control" id="cost" value="0" readonly>
                       </div>
                       <button type="submit" class="btn btn-success">Submit</button>
                       <button type="button" class="btn btn-secondary" onclick="loadAllRooms()">Back</button>
                   </form>`;

    document.getElementById('content').innerHTML = content;

    // Attach form submission handler
    document.getElementById('addRoomForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent form from submitting the traditional way
        addRoom();
    });
}
function updateCost() {
    const projectorCost = 5;
    const wifiCost = 10;
    const seatingCost = 10; // Example cost, change as needed
    const whiteboardCost = 5;
    const coffeeMachineCost = 10;
    const waterDispenserCost = 5;
    const tvCost = 10;

    const projectorCount = parseInt(document.getElementById('projector').value);
    const wifiCount = parseInt(document.getElementById('wifi').value);
    const seatingCount = parseInt(document.getElementById('seating').value);
    const whiteboardCount = parseInt(document.getElementById('whiteboard').value);
    const coffeeMachineCount = parseInt(document.getElementById('coffeeMachine').value);
    const waterDispenserCount = parseInt(document.getElementById('waterDispenser').value);
    const tvCount = parseInt(document.getElementById('tv').value);

    let cost = (projectorCount * projectorCost) +
               (wifiCount * wifiCost) +
               (seatingCount > 5 ? 20 : seatingCount > 10 ? 40 : 10) + // Example seating cost
               (whiteboardCount * whiteboardCost) +
               (coffeeMachineCount * coffeeMachineCost) +
               (waterDispenserCount * waterDispenserCost) +
               (tvCount * tvCost);

    document.getElementById('cost').value = cost;
}

function addRoom() {
    const roomName = document.getElementById('roomName').value;
    const amenities = {
        projector: parseInt(document.getElementById('projector').value),
        wifi: parseInt(document.getElementById('wifi').value),
        seating: parseInt(document.getElementById('seating').value),
        whiteboard: parseInt(document.getElementById('whiteboard').value),
        coffeeMachine: parseInt(document.getElementById('coffeeMachine').value),
        waterDispenser: parseInt(document.getElementById('waterDispenser').value),
        tv: parseInt(document.getElementById('tv').value)
    };
    const cost = parseInt(document.getElementById('cost').value);

    const newRoom = {
        id: db.rooms.length + 1,
        name: roomName,
        bookings: [],
        amenities: amenities,
        cost: cost
    };

    db.rooms.push(newRoom);
    loadAllRooms();
}


// Function to render room list based on search
function renderRoomList(filteredRooms) {
    let listContent = '';
    filteredRooms.forEach(room => {
        listContent += `<div class="col-md-4">
                            <div class="card" onclick="viewRoomDetails(${room.id})" style="cursor: pointer;">
                                <div class="card-body">
                                    <h5 class="card-title">${room.name}</h5>
                                    <p class="card-text">Bookings: ${room.bookings.length}</p>
                                    <p class="card-text">Status: ${room.bookings.length > 0 ? 'Booked' : 'Available'}</p>
                                </div>
                            </div>
                        </div>`;
    });
    document.getElementById('roomList').innerHTML = listContent;
}

// Function to view room details
function viewRoomDetails(roomId) {
    const room = db.rooms.find(r => r.id === roomId);
    let content = `<h3>${room.name} Details</h3>     
    <button class="btn btn-primary btn-sm">
        <i class="bi bi-pencil"></i> Edit
    </button>
    <p><strong>Amenities:</strong></p>
    <ul>
        <li>Projector: ${room.amenities.projector}</li>
        <li>WiFi: ${room.amenities.wifi }</li>
        <li>Seating Capacity: ${room.amenities.seating}</li>
        <li>Whiteboard: ${room.amenities.whiteboard }</li>
        <li>Coffee Machine: ${room.amenities.coffeeMachine}</li>
        <li>Water Dispenser: ${room.amenities.waterDispenser}</li>
        <li>TV: ${room.amenities.tv}</li>
    </ul>
    <p><strong>Cost:</strong>  <b>${room.cost} credits</b></p>
    <p><strong>Bookings:</strong></p>
    <input class="form-control mb-3" id="bookingSearch" type="text" placeholder="Search Bookings...">
    <div id="bookingList">`;


    room.bookings.forEach(booking => {
        content += `<div class="booking-status card mb-2">
                        <div class="card-body">
                            <p><strong>Date:</strong> ${booking.date}</p>
                            <p><strong>Time:</strong> ${booking.time}</p>
                            <p><strong>Status:</strong> ${booking.status}</p>
                        </div>
                    </div>`;
    });

    content += '<button class="btn btn-secondary back-button" onclick="loadAllRooms()">Back</button>';
    document.getElementById('content').innerHTML = content;

    // Search functionality for bookings
    document.getElementById('bookingSearch').addEventListener('input', function () {
        const query = this.value.toLowerCase();
        const filteredBookings = room.bookings.filter(booking => booking.date.toLowerCase().includes(query) || booking.time.toLowerCase().includes(query) || booking.status.toLowerCase().includes(query));
        renderBookingList(filteredBookings);
    });
}

// Function to render booking list based on search
function renderBookingList(filteredBookings) {
    let listContent = '';
    filteredBookings.forEach(booking => {
        listContent += `<div class="booking-status card mb-2">
                            <div class="card-body">
                                <p><strong>Date:</strong> ${booking.date}</p>
                                <p><strong>Time:</strong> ${booking.time}</p>
                                <p><strong>Status:</strong> ${booking.status}</p>
                            </div>
                        </div>`;
    });
    document.getElementById('bookingList').innerHTML = listContent;
}

// Function to load All Meeting Room Requests content
function loadRoomRequests() {
    let content = `<h3>All Meeting Room Requests</h3>
                   <input class="form-control mb-3" id="requestSearch" type="text" placeholder="Search Requests...">
                   <div id="requestList">`;

    db.requests.forEach(request => {
        content += `<div class="col-md-6">
                        <div class="card request-card ${request.status === 'approved' ? 'bg-success' : request.status === 'rejected' ? 'bg-danger' : ''}" id="request-${request.id}">
                            <div class="card-body">
                                <h5 class="card-title">${request.title}</h5>
                                <p class="card-text">Room: ${request.room}</p>
                                <p class="card-text">Manager: ${request.manager}</p>
                                <p class="card-text">Employees: ${request.employees.join(', ')}</p>
                                <p class="card-text">Date: ${request.date}</p>
                                <p class="card-text">Time: ${request.time}</p>
                                <button class="btn btn-success btn-sm" onclick="approveRequest(${request.id})">Approve</button>
                                <button class="btn btn-danger btn-sm ml-2" onclick="rejectRequest(${request.id})">Reject</button>
                            </div>
                        </div>
                    </div>`;
    });

    content += '</div>';
    document.getElementById('content').innerHTML = content;

    // Search functionality for requests
    document.getElementById('requestSearch').addEventListener('input', function () {
        const query = this.value.toLowerCase();
        const filteredRequests = db.requests.filter(request => request.title.toLowerCase().includes(query) ||
            request.room.toLowerCase().includes(query) ||
            request.manager.toLowerCase().includes(query) ||
            request.employees.some(emp => emp.toLowerCase().includes(query)));
        renderRequestList(filteredRequests);
    });
}

// Function to render request list based on search
function renderRequestList(filteredRequests) {
    let listContent = '';
    filteredRequests.forEach(request => {
        listContent += `<div class="col-md-6">
                            <div class="card request-card ${request.status === 'approved' ? 'bg-success' : request.status === 'rejected' ? 'bg-danger' : ''}" id="request-${request.id}">
                                <div class="card-body">
                                    <h5 class="card-title">${request.title}</h5>
                                    <p class="card-text">Room: ${request.room}</p>
                                    <p class="card-text">Manager: ${request.manager}</p>
                                    <p class="card-text">Employees: ${request.employees.join(', ')}</p>
                                    <p class="card-text">Date: ${request.date}</p>
                                    <p class="card-text">Time: ${request.time}</p>
                                    <button class="btn btn-success btn-sm" onclick="approveRequest(${request.id})">Approve</button>
                                    <button class="btn btn-danger btn-sm ml-2" onclick="rejectRequest(${request.id})">Reject</button>
                                </div>
                            </div>
                        </div>`;
    });
    document.getElementById('requestList').innerHTML = listContent;
}

// Function to handle approving requests
function approveRequest(requestId) {
    const requestCard = document.getElementById(`request-${requestId}`);
    requestCard.classList.remove('bg-danger');
    requestCard.classList.add('bg-success');
    db.requests.find(req => req.id === requestId).status = 'approved';
}

// Function to handle rejecting requests
function rejectRequest(requestId) {
    const requestCard = document.getElementById(`request-${requestId}`);
    requestCard.classList.remove('bg-success');
    requestCard.classList.add('bg-danger');
    db.requests.find(req => req.id === requestId).status = 'rejected';
}

// Event listeners for navigation
document.getElementById('navAllUsers').addEventListener('click', loadAllUsers);
document.getElementById('navAllRooms').addEventListener('click', loadAllRooms);
document.getElementById('navRoomRequests').addEventListener('click', loadRoomRequests);

// Load All Users by default
loadAllUsers();
