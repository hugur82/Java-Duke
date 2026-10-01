import {
  apiClient,
  clearStoredCredentials,
  setStoredCredentials,
} from "./apiClient";

export interface CurrentEmployee {
  employeeId: number;
  firstName: string;
  lastName: string;
  email: string;
  role: "ADMIN" | "EMPLOYEE";
  status: "ACTIVE" | "DISABLED";
  createdAt: string;
  lastLoginAt: string | null;
}

export async function login(
  username: string,
  password: string,
): Promise<CurrentEmployee> {
  setStoredCredentials(username, password);

  const response = await apiClient("/api/auth/me");

  if (!response.ok) {
    clearStoredCredentials();
    throw new Error("Invalid email or password.");
  }

  return response.json();
}

export function logout(): void {
  clearStoredCredentials();
}
