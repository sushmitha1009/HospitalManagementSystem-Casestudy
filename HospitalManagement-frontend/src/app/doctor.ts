export interface Doctor {
  doctorID?: number;
  fullName: string;
  specialty: DoctorCategory;
  experienceYears: number;
  qualification: string;
  designation: string;
  contactNumber: string;
  email: string;
  passwordHash: string;
}

export type DoctorCategory =
  | 'Cardiology'
  | 'Orthopedics'
  | 'Pediatrics'
  | 'Neurology'
  | 'Dermatology'
  | 'GeneralMedicine'
  | 'Anesthesia'
  | 'Psychiatry'
  | 'ENT';
