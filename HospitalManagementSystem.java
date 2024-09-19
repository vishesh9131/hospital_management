package hospital_management;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Patient {
    private int id;
    private String name;
    private int age;

    public Patient(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Doctor {
    private int id;
    private String name;
    private String specialization;

    public Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}

class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private String date;

    public Appointment(int id, Patient patient, Doctor doctor, String date) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }
}

public class HospitalManagementSystem {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Patient> patients = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();
        int patientIdCounter = 1;
        int doctorIdCounter = 1;
        int appointmentIdCounter = 1;

        while (true) {
            System.out.println("Hospital Management System Menu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Schedule Appointment");
            System.out.println("4. View Appointments");
            System.out.println("5. Search Patient");
            System.out.println("6. Search Doctor");
            System.out.println("7. Update Patient");
            System.out.println("8. Update Doctor");
            System.out.println("9. Cancel Appointment");
            System.out.println("10. Exit");
            System.out.print("Select an option (1-10): ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addPatient(patients, patientIdCounter);
                    patientIdCounter++;
                    break;
                case 2:
                    addDoctor(doctors, doctorIdCounter);
                    doctorIdCounter++;
                    break;
                case 3:
                    scheduleAppointment(patients, doctors, appointments, appointmentIdCounter);
                    appointmentIdCounter++;
                    break;
                case 4:
                    viewAppointments(appointments);
                    break;
                case 5:
                    searchPatientByName(patients);
                    break;
                case 6:
                    searchDoctorByName(doctors);
                    break;
                case 7:
                    updatePatient(patients);
                    break;
                case 8:
                    updateDoctor(doctors);
                    break;
                case 9:
                    cancelAppointment(appointments);
                    break;
                case 10:
                    System.out.println("Exiting the Hospital Management System.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please select between 1 and 10.");
            }
        }
    }

    private static void addPatient(List<Patient> patients, int patientId) {
        scanner.nextLine();
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        patients.add(new Patient(patientId, name, age));
        System.out.println("Patient added successfully.");
    }

    private static void addDoctor(List<Doctor> doctors, int doctorId) {
        scanner.nextLine();
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter doctor specialization: ");
        String specialization = scanner.nextLine();
        doctors.add(new Doctor(doctorId, name, specialization));
        System.out.println("Doctor added successfully.");
    }

    private static void scheduleAppointment(List<Patient> patients, List<Doctor> doctors, List<Appointment> appointments, int appointmentId) {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter appointment date: ");
        String date = scanner.nextLine();

        Patient patient = findPatientById(patients, patientId);
        Doctor doctor = findDoctorById(doctors, doctorId);

        if (patient != null && doctor != null) {
            if (!isAppointmentConflict(appointments, patientId, doctorId, date)) {
                appointments.add(new Appointment(appointmentId, patient, doctor, date));
                System.out.println("Appointment scheduled successfully.");
            } else {
                System.out.println("Appointment conflict detected. Doctor or Patient already has an appointment at this time.");
            }
        } else {
            System.out.println("Patient or doctor not found.");
        }
    }

    private static boolean isAppointmentConflict(List<Appointment> appointments, int patientId, int doctorId, String date) {
        for (Appointment appointment : appointments) {
            if ((appointment.getPatient().getId() == patientId || appointment.getDoctor().getId() == doctorId) && appointment.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    private static void viewAppointments(List<Appointment> appointments) {
        System.out.println("Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Patient: " + appointment.getPatient().getName());
            System.out.println("Doctor: " + appointment.getDoctor().getName() + " (" + appointment.getDoctor().getSpecialization() + ")");
            System.out.println("Date: " + appointment.getDate());
            System.out.println();
        }
    }

    private static void searchPatientByName(List<Patient> patients) {
        scanner.nextLine();
        System.out.print("Enter patient name to search: ");
        String name = scanner.nextLine();
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                System.out.println("Patient ID: " + patient.getId() + ", Name: " + patient.getName() + ", Age: " + patient.getAge());
                return;
            }
        }
        System.out.println("Patient not found.");
    }

    private static void searchDoctorByName(List<Doctor> doctors) {
        scanner.nextLine();
        System.out.print("Enter doctor name to search: ");
        String name = scanner.nextLine();
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                System.out.println("Doctor ID: " + doctor.getId() + ", Name: " + doctor.getName() + ", Specialization: " + doctor.getSpecialization());
                return;
            }
        }
        System.out.println("Doctor not found.");
    }

    private static void updatePatient(List<Patient> patients) {
        System.out.print("Enter patient ID to update: ");
        int id = scanner.nextInt();
        Patient patient = findPatientById(patients, id);
        if (patient != null) {
            scanner.nextLine();
            System.out.print("Enter new patient name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new patient age: ");
            int newAge = scanner.nextInt();
            patients.set(patients.indexOf(patient), new Patient(id, newName, newAge));
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void updateDoctor(List<Doctor> doctors) {
        System.out.print("Enter doctor ID to update: ");
        int id = scanner.nextInt();
        Doctor doctor = findDoctorById(doctors, id);
        if (doctor != null) {
            scanner.nextLine();
            System.out.print("Enter new doctor name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new doctor specialization: ");
            String newSpecialization = scanner.nextLine();
            doctors.set(doctors.indexOf(doctor), new Doctor(id, newName, newSpecialization));
            System.out.println("Doctor updated successfully.");
        } else {
            System.out.println("Doctor not found.");
        }
    }

    private static void cancelAppointment(List<Appointment> appointments) {
        System.out.print("Enter appointment ID to cancel: ");
        int id = scanner.nextInt();
        Appointment appointment = findAppointmentById(appointments, id);
        if (appointment != null) {
            appointments.remove(appointment);
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private static Patient findPatientById(List<Patient> patients, int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    private static Doctor findDoctorById(List<Doctor> doctors, int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    private static Appointment findAppointmentById(List<Appointment> appointments, int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null;
    }
}