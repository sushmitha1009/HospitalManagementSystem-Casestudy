export interface Report {
  reportType: string;
  description: string;
  date: string;
  doctor: {
    doctorID: number;
    fullName?: string;       // optional
    specialty?: string;      // optional
  };
  patient: {
    patientId: number;
    fullName?: string;       // optional
  };
}
