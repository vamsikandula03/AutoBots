const newLocal = 'onlinetraining';
const db = {
     users:[
        { id: 45393711, name: 'Vamsi', role: 'admin', credits: 0, email: 'vamsi@example.com', phone: '123-456-7890' },
        { id: 45393712, name: 'Kamalakar', role: 'manager', credits: 1920, email: 'kamalakar@example.com', phone: '234-567-8901' },
        { id: 45393713, name: 'Kushal', role: 'manager', credits: 1970, email: 'kushal@example.com', phone: '345-678-9012' },
        { id: 45393714, name: 'Kushi', role: 'employee', credits: 0, email: 'kushi@example.com', phone: '456-789-0123' },
        { id: 45393715, name: 'Ankitha', role: 'employee', credits: 0, email: 'ankitha@example.com', phone: '567-890-1234' },
        { id: 45393716, name: 'Kavya', role: 'employee', credits: 0, email: 'kavya@example.com', phone: '678-901-2345' },
        { id: 45393717, name: 'Porush', role: 'employee', credits: 0, email: 'porush@example.com', phone: '789-012-3456' },
        { id: 45393718, name: 'Rahul', role: 'employee', credits: 0, email: 'rahul@example.com', phone: '890-123-4567' },
    ],
    
   rooms : [
        {
            id: 1,
            name: 'Room A',
            bookings: [
                { date: '2024-08-12', time: '11:00 AM - 1:00 PM', status: 'Booked' },
                { date: '2024-08-13', time: '3:00 PM - 6:00 PM', status: 'Booked' }
            ],
            amenities: {
                projector: 1,
                wifi: 1,
                seating: 20,
                whiteboard: 1,
                coffeeMachine: 1,
                waterDispenser: 1,
                tv: 0
            },
            types: ['classroomtraining', 'conference call'],
            cost: (1 * 5) + (1 * 10) + (1 * 15) + (1 * 5) + (1 * 10) + (1 * 5) + (0 * 10) + 20
        },
        {
            id: 2,
            name: 'Room B',
            bookings: [],
            amenities: {
                projector: 1,
                wifi: 1,
                seating: 15,
                whiteboard: 1,
                coffeeMachine: 0,
                waterDispenser: 0,
                tv: 1
            },
            types: ["online training", 'business', "classroomtraining"],
            cost: (1 * 5) + (1 * 10) + (0 * 15) + (1 * 5) + (0 * 10) + (0 * 5) + (1 * 10) + 20
        },
        {
            id: 3,
            name: 'Room C',
            bookings: [
                { date: '2024-08-12', time: '11:00 AM - 1:00 PM', status: 'Booked' },
                { date: '2024-08-13', time: '3:00 PM - 6:00 PM', status: 'Booked' }
            ],
            amenities: {
                projector: 2,
                wifi: 1,
                seating: 40,
                whiteboard: 1,
                coffeeMachine: 2,
                waterDispenser: 1,
                tv: 1
            },
            types: ['classroomtraining', 'conference call', "business"],
            cost: (2 * 5) + (1 * 10) + (1 * 15) + (1 * 5) + (2 * 10) + (1 * 5) + (1 * 10) + 20
        },
        {
            id: 4,
            name: 'Room D',
            bookings: [],
            amenities: {
                projector: 0,
                wifi: 1,
                seating: 15,
                whiteboard: 1,
                coffeeMachine: 0,
                waterDispenser: 1,
                tv: 0
            },
            types: ['online training', 'business'],
            cost: (0 * 5) + (1 * 10) + (0 * 15) + (1 * 5) + (0 * 10) + (1 * 5) + (0 * 10) + 20
        },
        {
            id: 5,
            name: 'Room E',
            bookings: [
                { date: '2024-08-12', time: '11:00 AM - 1:00 PM', status: 'Booked' },
                { date: '2024-08-13', time: '3:00 PM - 6:00 PM', status: 'Booked' },
                { date: '2024-08-16', time: '9:00 AM - 12:00 PM', status: 'Booked' }
            ],
            amenities: {
                projector: 1,
                wifi: 1,
                seating: 20,
                whiteboard: 1,
                coffeeMachine: 1,
                waterDispenser: 1,
                tv: 0
            },
            types: ['classroomtraining', "conference call"],
            cost: (1 * 5) + (1 * 10) + (1 * 15) + (1 * 5) + (1 * 10) + (1 * 5) + (0 * 10) + 20
        },
        {
            id: 6,
            name: 'Room F',
            bookings: [],
            amenities: {
                projector: 1,
                wifi: 1,
                seating: 4,
                whiteboard: 1,
                coffeeMachine: 0,
                waterDispenser: 1,
                tv: 0
            },
            types: ['online training', 'business'],
            cost: (1 * 5) + (1 * 10) + (0 * 15) + (1 * 5) + (0 * 10) + (1 * 5) + (0 * 10) + 0
        },
        {
            id: 7,
            name: 'Room G',
            bookings: [
                { date: '2024-08-12', time: '11:00 AM - 1:00 PM', status: 'Booked' },
                { date: '2024-08-13', time: '3:00 PM - 6:00 PM', status: 'Booked' }
            ],
            amenities: {
                projector: 1,
                wifi: 1,
                seating: 10,
                whiteboard: 1,
                coffeeMachine: 1,
                waterDispenser: 1,
                tv: 1
            },
            types: ['classroomtraining', 'conference call'],
            cost: (1 * 5) + (1 * 10) + (1 * 15) + (1 * 5) + (1 * 10) + (1 * 5) + (1 * 10) + 10
        },
        {
            id: 8,
            name: 'Room H',
            bookings: [],
            amenities: {
                projector: 0,
                wifi: 1,
                seating: 15,
                whiteboard: 1,
                coffeeMachine: 0,
                waterDispenser: 0,
                tv: 0
            },
            types: ['online training', 'business'],
            cost: (0 * 5) + (1 * 10) + (0 * 15) + (1 * 5) + (0 * 10) + (0 * 5) + (0 * 10) + 20
        }
    ],// Check the updated rooms with costs
    
    requests: [
        { id: 1, title: 'Quarterly Review', manager: 'Kushal', employees: ["Ankitha","Kavya"], date: '2024-08-12', time: '11:00 AM - 1:00 PM', status: 'pending', room: 'Room A' },
        { id: 2, title: 'Project Planning', manager: 'Kamalakar', employees: ["Porush","Rahul"], date: '2024-08-13', time: '3:00 PM - 6:00 PM', status: 'approved', room: 'Room B' },
        { id: 3, title: 'Team Meeting', manager: 'Kushal', employees: ["Kavya","Kushi","Ankitha"], date: '2024-08-15', time: '10:00 AM - 12:00 PM', status: 'approved', room: 'Room A' },
        { id: 4, title: 'Project Updates', manager: 'Kamalakar', employees: ["Porish","Rahul","Kushi"], date: '2024-08-15', time: '10:00 AM - 12:00 PM', status: 'rejected', room: 'Room A' },
        
    ]
};
const users = [
    { psid: '1', password: 'admin', role: 'admin' },
    { psid: '2', password: 'manager', role: 'manager' },
    { psid: '3', password: 'employee', role: 'employee' }
];