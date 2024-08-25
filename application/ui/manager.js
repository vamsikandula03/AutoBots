loggedInManager="Kamalakar"
// Function to load Book Meeting Room content
function loadBookMeetingRoom() {
    let content = `<h3>Book Meeting Room</h3>
                   <div class="booking-type">
                       <div class="form-group">
                           <label for="roomType">Select Room Type:</label>
                           <div id="roomType">
                               <div class="form-check">
                                   <input class="form-check-input" type="radio" name="roomType" id="classroomtraining" value="classroomtraining">
                                   <label class="form-check-label" for="classroomtraining">Classroom Training</label>
                               </div>
                               <div class="form-check">
                                   <input class="form-check-input" type="radio" name="roomType" id="onlineTraining" value="online training">
                                   <label class="form-check-label" for="onlineTraining">Online Training</label>
                               </div>
                               <div class="form-check">
                                   <input class="form-check-input" type="radio" name="roomType" id="conferenceCall" value="conference call">
                                   <label class="form-check-label" for="conferenceCall">Conference Call</label>
                               </div>
                               <div class="form-check">
                                   <input class="form-check-input" type="radio" name="roomType" id="business" value="business">
                                   <label class="form-check-label" for="business">Business</label>
                               </div>
                               <div class="form-check">
                                   <input class="form-check-input" type="radio" name="roomType" id="all" value="all" checked>
                                   <label class="form-check-label" for="all">All</label>
                               </div>
                           </div>
                       </div>
                       <div id="roomList" class="row"></div>
                   </div>
                   <div id="roomDetails" class="meeting-details"></div>
                   <button class="btn btn-secondary back-button" onclick="loadBookMeetingRoom()">Back</button>`;

    document.getElementById('content').innerHTML = content;

    // Event listeners for room type filters
    document.querySelectorAll('input[name="roomType"]').forEach(radio => {
        radio.addEventListener('change', function() {
            loadRoomsByType(this.value);
        });
    });

    // Load all rooms by default
    loadRoomsByType('all');
}

// Example function to find the manager and update credits
function updateCredits() {
    // Assume the logged-in user has ID 45393712 (you can replace it with actual logic to get the current user ID)
    const currentUserId = 45393712;

    // Find the manager from the db
    const manager = db.users.find(user => user.id === currentUserId && user.role === 'manager');

    // Check if the manager was found
    if (manager) {
        // Update the credits in the navbar
        document.getElementById('credits').innerHTML = manager.credits;
    } else {
        console.error('Manager not found');
    }
}

// Call the function to update credits on page load or when appropriate
updateCredits();





// Function to display meeting request form
function showMeetingRequestForm(roomId) {
    const room = db.rooms.find(r => r.id === roomId);
    const allTimes = ['9:00 AM - 11:00 AM', '11:00 AM - 1:00 PM', '1:00 PM - 3:00 PM', '3:00 PM - 5:00 PM'];
    const unavailableTimes = room.bookings.map(booking => booking.time);
    const availableTimes = allTimes.filter(time => !unavailableTimes.includes(time));

    let content = `<h3>Request Meeting in ${room.name}</h3>
                   <form id="meetingForm">
                       <div class="form-group">
                           <label for="meetingTheme">Theme of Meeting:</label>
                           <input type="text" class="form-control" id="meetingTheme" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingUsers">Add Users:</label>
                           <select multiple class="form-control" id="meetingUsers" required>`;

    db.users.forEach(user => {
        if (user.role === 'employee') { // Only show employees
            content += `<option value="${user.id}">${user.name}</option>`;
        }
    });

    content += `</select>
                       </div>
                       <div class="form-group">
                           <label for="meetingDate">Date:</label>
                           <input type="date" class="form-control" id="meetingDate" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingTime">Time:</label>
                           <select class="form-control" id="meetingTime" required>`;

    availableTimes.forEach(time => {
        content += `<option value="${time}">${time}</option>`;
    });

    content += `</select>
                       </div>
                       <button type="submit" class="btn btn-primary">Request</button>
                   </form>
                   <button class="btn btn-secondary back-button" onclick="loadBookMeetingRoom()">Back</button>`;

    document.getElementById('content').innerHTML = content;

    // Handle form submission
    document.getElementById('meetingForm').addEventListener('submit', function(event) {
        event.preventDefault();

        // Get form values
        const theme = document.getElementById('meetingTheme').value;
        const users = Array.from(document.getElementById('meetingUsers').selectedOptions).map(option => option.text);
        const date = document.getElementById('meetingDate').value;
        const time = document.getElementById('meetingTime').value;

        if (theme && users.length > 0 && date && time) {
            // Here you would send the data to the server
            alert(`Meeting Request Submitted!\n\nTheme: ${theme}\nUsers: ${users.join(', ')}\nDate: ${date}\nTime: ${time}`);

            // Reload the booking page or reset form
            loadBookMeetingRoom();
        } else {
            alert('Please fill out all fields.');
        }
    });
}




// Function to view room details and show amenities with a request button


// Function to display meeting request form
function showMeetingRequestForm(roomId) {
    const room = db.rooms.find(r => r.id === roomId);
    const allTimes = ['9:00 AM - 11:00 AM', '11:00 AM - 1:00 PM', '1:00 PM - 3:00 PM', '3:00 PM - 5:00 PM'];
    const unavailableTimes = room.bookings.map(booking => booking.time);
    const availableTimes = allTimes.filter(time => !unavailableTimes.includes(time));

    let content = `<h3>Request Meeting in ${room.name}</h3>
                   <form id="meetingForm">
                       <div class="form-group">
                           <label for="meetingTheme">Theme of Meeting:</label>
                           <input type="text" class="form-control" id="meetingTheme" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingUsers">Add Users:</label>
                           <select multiple class="form-control" id="meetingUsers" required>`;

    db.users.forEach(user => {
        if (user.role === 'employee') { // Only show employees
            content += `<option value="${user.id}">${user.name}</option>`;
        }
    });

    content += `</select>
                       </div>
                       <div class="form-group">
                           <label for="meetingDate">Date:</label>
                           <input type="date" class="form-control" id="meetingDate" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingTime">Time:</label>
                           <select class="form-control" id="meetingTime" required>`;

    availableTimes.forEach(time => {
        content += `<option value="${time}">${time}</option>`;
    });

    content += `</select>
                       </div>
                       <button type="submit" class="btn btn-primary">Request</button>
                   </form>
                   <button class="btn btn-secondary back-button" onclick="loadBookMeetingRoom()">Back</button>`;

    document.getElementById('content').innerHTML = content;

    // Handle form submission
    document.getElementById('meetingForm').addEventListener('submit', function(event) {
        event.preventDefault();

        // Get form values
        const theme = document.getElementById('meetingTheme').value;
        const users = Array.from(document.getElementById('meetingUsers').selectedOptions).map(option => option.text);
        const date = document.getElementById('meetingDate').value;
        const time = document.getElementById('meetingTime').value;

        if (theme && users.length > 0 && date && time) {
            // Add request to db.requests
            const newRequest = {
                id: db.requests.length + 1, // Incremental ID
                title: theme,
                manager: 'Manager One', // Hardcoded for example; replace with actual manager's name
                employees: users,
                date: date,
                time: time,
                status: 'pending' // Default status
            };

            db.requests.push(newRequest);

            alert(`Meeting Request Submitted!\n\nTheme: ${theme}\nUsers: ${users.join(', ')}\nDate: ${date}\nTime: ${time}`);

            // Reload the booking page or reset form
            loadBookMeetingRoom();
        } else {
            alert('Please fill out all fields.');
        }
    });
}

// Function to load rooms based on selected type
function loadRoomsByType(type) {
    let filteredRooms = type === 'all' ? db.rooms : db.rooms.filter(room => room.types.includes(type));
    let roomList = document.getElementById('roomList');
    roomList.innerHTML = '';

    filteredRooms.forEach(room => {
        roomList.innerHTML += `<div class="col-md-4">
                                    <div class="card" onclick="viewRoomDetails(${room.id})" style="cursor: pointer;">
                                        <div class="card-body">
                                            <h5 class="card-title">${room.name}</h5>
                                            <p class="card-text">Seating Capacity: ${room.amenities.seating}</p>
                                            <p class="card-text">Bookings: ${room.bookings.length}</p>
                                        </div>
                                    </div>
                                </div>`;
    });
}

// Function to load scheduled meetings
function loadMeetingsScheduled() {
    let content = `<h3>Meetings Scheduled</h3>
                   <div class="btn-group mb-3">
                       <button class="btn btn-outline-primary" onclick="filterRequests('approved')">Approved</button>
                       <button class="btn btn-outline-warning" onclick="filterRequests('pending')">Pending</button>
                       <button class="btn btn-outline-danger" onclick="filterRequests('rejected')">Rejected</button>
                   </div>
                   <div id="requestsList"></div>
                   <button class="btn btn-secondary back-button" onclick="loadBookMeetingRoom()">Back</button>`;

    document.getElementById('content').innerHTML = content;

    filterRequests('approved'); // Load approved requests by default
}

// Function to filter and display requests
function filterRequests(status) {
    let filteredRequests = db.requests.filter(req => req.status === status & req.manager == loggedInManager);
    let requestsList = document.getElementById('requestsList');
    requestsList.innerHTML = '';

    filteredRequests.forEach(request => {
        requestsList.innerHTML += `<div class="card mb-3 ${request.status === 'approved' ? 'bg-success text-white' : request.status === 'pending' ? 'bg-warning text-dark' : 'bg-danger text-white'}">
                                        <div class="card-body">
                                            <h5 class="card-title">${request.title}</h5>
                                            <p class="card-text"><strong>Room Name:</strong> ${request.room}</p>
                                            <p class="card-text"><strong>Manager:</strong> ${request.manager}</p>
                                            <p class="card-text"><strong>Employees:</strong> ${request.employees.join(', ')}</p>
                                            <p class="card-text"><strong>Date:</strong> ${request.date}</p>
                                            <p class="card-text"><strong>Time:</strong> ${request.time}</p>
                                        </div>
                                    </div>`;
    });
}

// Initial load of Book Meeting Room
loadBookMeetingRoom();


// Function to load users into checkboxes
function loadUsersCheckboxes() {
    const userCheckboxes = document.getElementById('userCheckboxes');
    userCheckboxes.innerHTML = '';

    db.users.forEach(user => {
        if (user.role === 'employee' || user.role === 'manager') {
            userCheckboxes.innerHTML += `<div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="user${user.id}" value="${user.id}">
                                            <label class="form-check-label" for="user${user.id}">${user.name}</label>
                                          </div>`;
        }
    });
}

// Function to filter checkboxes based on search input
function filterUserCheckboxes(query) {
    const checkboxes = document.querySelectorAll('#userCheckboxes .form-check');
    checkboxes.forEach(checkbox => {
        const label = checkbox.querySelector('label').textContent.toLowerCase();
        const isVisible = label.includes(query.toLowerCase());
        checkbox.style.display = isVisible ? 'block' : 'none';
    });
}

// Function to add meeting request to the data
function addMeetingRequest() {
    const theme = document.getElementById('meetingTheme').value;
    const date = document.getElementById('meetingDate').value;
    const time = document.getElementById('meetingTime').value;

    const selectedUsers = Array.from(document.querySelectorAll('#userCheckboxes .form-check-input:checked'))
                               .map(checkbox => checkbox.value);

    const requestId = db.requests.length ? db.requests[db.requests.length - 1].id + 1 : 1;
    const newRequest = {
        id: requestId,
        title: theme,
        manager: 'Manager One', // Replace with the logged-in manager's name
        employees: selectedUsers.map(id => db.users.find(user => user.id === parseInt(id)).name),
        date: date,
        time: time,
        status: 'pending'
    };

    db.requests.push(newRequest);
}
function viewRoomDetails(roomId) {
    const room = db.rooms.find(r => r.id === roomId);
    let details = `<h3>${room.name} Details</h3>
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
    `;
    let content = `<h3>Book Meeting in ${room.name}</h3>
    <p>Details of the room:</p>
${details}
    
                   <form id="meetingForm">
                       <div class="form-group">
                           <label for="meetingTheme">Theme of Meeting:</label>
                           <input type="text" class="form-control" id="meetingTheme" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingUsers">Add Users:</label>
                           <input type="text" class="form-control" id="userSearch" placeholder="Search users">
                           <div id="userList" class="mt-2"></div>
                       </div>
                       <div class="form-group">
                           <label for="meetingDate">Date:</label>
                           <input type="date" class="form-control" id="meetingDate" required>
                       </div>
                       <div class="form-group">
                           <label for="meetingTime">Time:</label>
                           <select class="form-control" id="meetingTime">`;

    const unavailableTimes = room.bookings.map(booking => booking.time);
    const allTimes = ['9:00 AM - 11:00 AM', '11:00 AM - 1:00 PM', '1:00 PM - 3:00 PM', '3:00 PM - 5:00 PM'];
    allTimes.forEach(time => {
        if (!unavailableTimes.includes(time)) {
            content += `<option value="${time}">${time}</option>`;
        }
    });

    content += `</select>
                       </div>
                       <button type="submit" class="btn btn-primary">Submit Request</button>
                   </form>
                   <button class="btn btn-secondary back-button" onclick="loadBookMeetingRoom()">Back</button>`;

    document.getElementById('content').innerHTML = content;

    populateUserList(); // Populate user list

    // Handle form submission
    document.getElementById('meetingForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const theme = document.getElementById('meetingTheme').value;
        const date = document.getElementById('meetingDate').value;
        const time = document.getElementById('meetingTime').value;
        const selectedUsers = Array.from(document.querySelectorAll('input[name="user"]:checked')).map(cb => cb.value);
        
        db.requests.push({
            id: db.requests.length + 1,
            title: theme,
            manager: 'Manager One', // Change to dynamic manager name if available
            employees: selectedUsers,
            date: date,
            time: time,
            status: 'pending',
            room: room.name
        });
        
        alert('Meeting request submitted.');
        loadBookMeetingRoom(); // Reload the booking page
    });
}

// Function to populate user list with checkboxes
function populateUserList() {
    const userList = document.getElementById('userList');
    userList.innerHTML = '';

    db.users.forEach(user => {
        if (user.role === 'employee') {
            userList.innerHTML += `<div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="user" id="user${user.id}" value="${user.name}">
                                        <label class="form-check-label" for="user${user.id}">${user.name}</label>
                                    </div>`;
        }
    });

    // Add search functionality
    document.getElementById('userSearch').addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        document.querySelectorAll('#userList .form-check').forEach(item => {
            const name = item.querySelector('label').textContent.toLowerCase();
            item.style.display = name.includes(searchTerm) ? '' : 'none';
        });
    });
}




// Event listeners for navigation
document.getElementById('navBookMeetingRoom').addEventListener('click', loadBookMeetingRoom);
document.getElementById('navMeetingsScheduled').addEventListener('click', loadMeetingsScheduled);
