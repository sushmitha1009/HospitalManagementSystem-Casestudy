export interface AuthResponse {
  token: string;
  role: string;
  doctorID? : number;
  patientId?: number;
}
