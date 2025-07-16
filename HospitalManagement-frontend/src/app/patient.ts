export type Gender = 'Male' | 'Female' | 'Other';

export interface Patient {
  patientId: number;
  fullName: string;
  dateOfBirth: string;
  gender: Gender;
  contactNumber: string;
  email: string;
  passwordHash: string;
  medicalHistory: string;
  registrationDate: string;
}
