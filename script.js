let patients = [];
let doctors = {
    1: { name: 'Dr. vishesh', specialization: 'Cardiology' },
    2: { name: 'Dr. vibhan', specialization: 'Neurology' },
    3: { name: 'Dr. adil', specialization: 'Pediatrics' },
    4: { name: 'Dr. ayush', specialization: 'Orthopedics' },
    5: { name: 'Dr. Sachin', specialization: 'Dermatology' },
    6: { name: 'Dr. hasim', specialization: 'Gastroenterology' },
    7: { name: 'Dr. lokesh', specialization: 'Oncology' },
    8: { name: 'Dr. payal', specialization: 'Urology' },
    9: { name: 'Dr. lauren', specialization: 'Endocrinology' },
    10: { name: 'Dr. binod', specialization: 'Rheumatology' }
};
let appointments = [];
let patientIdCounter = 1;
let doctorIdCounter = 11; // Start from 11 since we have 10 prepopulated doctors
let appointmentIdCounter = 1;

const validDoctorName = 'abc';
const validDoctorPassword = '123';

function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
    if (sectionId === 'viewDoctors') {
        viewDoctors();
    }
}

function login(event) {
    event.preventDefault();
    const name = document.getElementById('doctorLoginName').value;
    const password = document.getElementById('doctorLoginPassword').value;

    if (name === validDoctorName && password === validDoctorPassword) {
        document.getElementById('loginSection').style.display = 'none';
        document.getElementById('menu').style.display = 'block';
        showSection('addPatient');
    } else {
        document.getElementById('loginError').style.display = 'block';
    }
}

function addPatient(event) {
    event.preventDefault();
    const name = document.getElementById('patientName').value;
    const age = document.getElementById('patientAge').value;
    patients.push({ id: patientIdCounter++, name, age });
    alert('Patient added successfully.');
    event.target.reset();
}

function addDoctor(event) {
    event.preventDefault();
    const name = document.getElementById('doctorName').value;
    const specialization = document.getElementById('doctorSpecialization').value;
    doctors[doctorIdCounter] = { name, specialization };
    doctorIdCounter++;
    alert('Doctor added successfully.');
    event.target.reset();
    viewDoctors(); // Update the doctors list immediately
}

function scheduleAppointment(event) {
    event.preventDefault();
    const patientId = parseInt(document.getElementById('appointmentPatientId').value);
    const doctorId = parseInt(document.getElementById('appointmentDoctorId').value);
    const date = document.getElementById('appointmentDate').value;

    const patient = patients.find(p => p.id === patientId);
    const doctor = doctors[doctorId];

    if (patient && doctor) {
        if (!appointments.some(a => (a.patient.id === patientId || a.doctor.id === doctorId) && a.date === date)) {
            appointments.push({ id: appointmentIdCounter++, patient, doctor, date });
            alert('Appointment scheduled successfully.');
        } else {
            alert('Appointment conflict detected.');
        }
    } else {
        alert('Patient or doctor not found.');
    }
    event.target.reset();
}

function viewAppointments() {
    const appointmentsList = document.getElementById('appointmentsList');
    appointmentsList.innerHTML = '';
    appointments.forEach(appointment => {
        const div = document.createElement('div');
        div.className = 'list-group-item';
        div.innerHTML = `
            <p><strong>Appointment ID:</strong> ${appointment.id}</p>
            <p><strong>Patient:</strong> ${appointment.patient.name}</p>
            <p><strong>Doctor:</strong> ${appointment.doctor.name} (${appointment.doctor.specialization})</p>
            <p><strong>Date:</strong> ${appointment.date}</p>
        `;
        appointmentsList.appendChild(div);
    });
}

function viewDoctors() {
    const doctorsList = document.getElementById('doctorsList');
    doctorsList.innerHTML = '';
    for (const id in doctors) {
        const doctor = doctors[id];
        const div = document.createElement('div');
        div.className = 'list-group-item';
        div.innerHTML = `
            <p><strong>Doctor ID:</strong> ${id}</p>
            <p><strong>Name:</strong> ${doctor.name}</p>
            <p><strong>Specialization:</strong> ${doctor.specialization}</p>
        `;
        doctorsList.appendChild(div);
    }
}

function searchPatient(event) {
    event.preventDefault();
    const name = document.getElementById('searchPatientName').value.toLowerCase();
    const result = patients.find(p => p.name.toLowerCase() === name);
    const searchPatientResult = document.getElementById('searchPatientResult');
    searchPatientResult.innerHTML = result ? 
        `<div class="alert alert-success"><p><strong>Patient ID:</strong> ${result.id}</p><p><strong>Name:</strong> ${result.name}</p><p><strong>Age:</strong> ${result.age}</p></div>` : 
        '<div class="alert alert-danger">Patient not found.</div>';
}

function searchDoctor(event) {
    event.preventDefault();
    const name = document.getElementById('searchDoctorName').value.toLowerCase();
    const result = Object.values(doctors).find(d => d.name.toLowerCase() === name);
    const searchDoctorResult = document.getElementById('searchDoctorResult');
    searchDoctorResult.innerHTML = result ? 
        `<div class="alert alert-success"><p><strong>Doctor ID:</strong> ${Object.keys(doctors).find(key => doctors[key] === result)}</p><p><strong>Name:</strong> ${result.name}</p><p><strong>Specialization:</strong> ${result.specialization}</p></div>` : 
        '<div class="alert alert-danger">Doctor not found.</div>';
}