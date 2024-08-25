
        // Event listeners for navigation links
        document.getElementById('navScheduledMeetings').addEventListener('click', loadScheduledMeetings);

        // Function to load scheduled meetings for the employee
        function loadScheduledMeetings() {
            const employeeName = "Ankitha"; // Change this dynamically based on logged-in employee
            let content = `<h3>Scheduled Meetings for ${employeeName}</h3>
                           <div id="meetingsList"></div>
                           <button class="btn btn-secondary back-button" onclick="backToHome()">Back</button>`;

            document.getElementById('content').innerHTML = content;

            displayScheduledMeetings(employeeName);
        }

        // Function to display scheduled meetings assigned to the employee
        function displayScheduledMeetings(employeeName) {
            let meetingsList = document.getElementById('meetingsList');
            meetingsList.innerHTML = '';

            const employeeMeetings = db.requests.filter(request => request.employees.includes(employeeName));

            if (employeeMeetings.length === 0) {
                meetingsList.innerHTML = '<p>No meetings scheduled.</p>';
            } else {
                employeeMeetings.forEach(meeting => {
                    meetingsList.innerHTML += `<div class="card mb-3">
                                                    <div class="card-body">
                                                        <h5 class="card-title">${meeting.title}</h5>
                                                        <p class="card-text">Room name: ${meeting.room}</p>
                                                        <p class="card-text">Manager: ${meeting.manager}</p>
                                                        <p class="card-text">Date: ${meeting.date}</p>
                                                        <p class="card-text">Time: ${meeting.time}</p>
                                                        <p class="card-text">Status: ${meeting.status}</p>
                                                    </div>
                                                </div>`;
                });
            }
        }

        // Function to handle back to home page
        function backToHome() {
            document.getElementById('content').innerHTML = `<h3>Welcome to the Employee Dashboard</h3>
                                                            <p>Please click on "Scheduled Meetings" to view your meetings.</p>`;
        }