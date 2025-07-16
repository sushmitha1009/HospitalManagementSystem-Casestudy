export interface Appointment {
  id?: number;
  patientId: number;
  doctorID: number;
  appointmentDate: string;
  reason: string;
  status: string;
}
